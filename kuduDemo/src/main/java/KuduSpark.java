import org.apache.kudu.client.*;
import org.apache.kudu.spark.kudu.*;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import scala.Tuple2;
import scala.collection.JavaConverters;
import scala.collection.Seq;
import java.util.*;
import java.util.function.Consumer;

public class KuduSpark {
    private static String kuduMasters;
    private static String tableName;
    private static int tableNumReplicas = Integer.getInteger("tableNumReplicas", 1);

    private static String nameCol = "name";
    private static String idCol = "id";

    static {
        kuduMasters = System.getProperty("kuduMasters", "hdp004.beyondsoft.com:7051");
        tableName = System.getProperty("tableName", "kudu_spark_example");
    }

    public static void main(String[] args) {
        //表的列
        List<String> fieldNameList = new ArrayList<>();
        fieldNameList.add("a");
        fieldNameList.add("b");
        fieldNameList.add("c");

        SparkConf conf = new SparkConf().setMaster("local").setAppName("test");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        SparkSession sparkSession = SparkSession.builder().appName("SparkSQL_demo").master("local").getOrCreate();
        SparkContext sparkContext = sparkSession.sparkContext();



        KuduContext kuduContext = new KuduContext(kuduMasters,sparkContext);

        List<StructField> fields = new ArrayList<>();
        for (String fieldName : fieldNameList) {
            StructField field = DataTypes.createStructField(fieldName, DataTypes.StringType, true);
            fields.add(field);
        }
        StructType schema = DataTypes.createStructType(fields);

        boolean tableIsCreated = false;


        try {
            // Make sure the table does not exist. This is mostly to demonstrate
            // the capabilities of the API. In general, there might be a racing
            // request to create the table coming from elsewhere, so even
            // if tableExists() returned false at this time, the table might appear
            // while createTable() is running below. In the latter case, appropriate
            // Kudu exception will be thrown by createTable().
            if (kuduContext.tableExists(tableName)) {
                throw new RuntimeException(tableName + ": table already exists");
            }

            // Create the table with 3 hash partitions, resulting in 3 tablets,
            // each with the specified number of replicas.
            List<String> idColList = new ArrayList();
            idColList.add(idCol);

            kuduContext.createTable(tableName, schema, JavaConverters.asScalaIteratorConverter(idColList.iterator()).asScala().toSeq(),
                    new CreateTableOptions()
                            .addHashPartitions(idColList, 3)
                            .setNumReplicas(tableNumReplicas));
            tableIsCreated = true;

            // Write to the table.
            System.out.println("writing to table '$tableName'");

            List<StructField> userStructField = new ArrayList<>();
            StructField idField = DataTypes.createStructField("id", DataTypes.IntegerType, true);
            StructField nameField = DataTypes.createStructField("name", DataTypes.StringType, true);
            userStructField.add(idField);
            userStructField.add(nameField);
            StructType userSchema = DataTypes.createStructType(userStructField);

            JavaRDD<User> userJavaRDD = jsc.parallelize(Arrays.asList(new User("userA", 1234),new User("userB", 5678)));
            Dataset<Row> userDF = sparkSession.createDataFrame((List<Row>) userJavaRDD,userSchema);

            kuduContext.insertRows(userDF, tableName);

            // Read from the table using an RDD.
            System.out.println("reading back the rows just written");

            List<String> colList = new ArrayList<>();
            colList.add(nameCol);
            colList.add(idCol);
            Seq<String> readCols = JavaConverters.asScalaIteratorConverter(colList.iterator()).asScala().toSeq();
            JavaRDD<Row> readRDD = kuduContext.kuduRDD(sparkContext, tableName, readCols).toJavaRDD();

            //val userTuple = readRDD.map { case Row(name: String, id:Int) => (name, id) }
            JavaPairRDD<String,String> userTuple = readRDD.mapToPair(new PairFunction<Row, String, String>() {
                @Override
                public Tuple2<String, String> call(Row s) throws Exception {
                    return new Tuple2<>(s.getString(0),s.getString(1));
                }
            });
            userTuple.collect().forEach(new Consumer<Tuple2<String, String>>() {
                @Override
                public void accept(Tuple2<String, String> stringStringTuple2) {

                }
            });

            //userTuple.collect().foreach(println(_))

            // Upsert some rows.
            System.out.println("upserting to table '$tableName'");
            JavaRDD<User> upsertUsersRDD = jsc.parallelize(Arrays.asList(new User("newUserA", 1234),new User("userC", 7777)));

            Dataset<Row> upsertUsersDF = sparkSession.createDataFrame((List<Row>) upsertUsersRDD,userSchema);
            kuduContext.upsertRows(upsertUsersDF, tableName);

            // Read the updated table using SparkSQL.
            Map<String,String> masterTableMap = new HashMap<>();
            masterTableMap.put("kudu.master",kuduMasters);
            masterTableMap.put("kudu.table",tableName);
            DataFrameReader sqlDf = sparkSession.read().options(masterTableMap);
            sqlDf.createOrReplaceTempView(tableName);

            sparkSession.sql("SELECT * FROM $tableName WHERE $idCol > 1000").show();

        } catch (Exception e) {
            // Catch, log and re-throw. Not the best practice, but this is a very
            // simplistic example.
            /*case unknown : Throwable => logger.error(s"got an exception: " + unknown)
                throw unknown*/
        } finally {
            // Clean up.
            if (tableIsCreated) {
                System.out.println("deleting table '$tableName'");
                kuduContext.deleteTable(tableName);
            }
            System.out.println("closing down the session");
            sparkSession.close();
        }


    }

    /**
     * 使用sftmta prep DataFrame定义的模式
     * 创建一个包含3个副本和4个散列分区的Kudu表。
     */
    public void createTable(){
        SparkConf conf = new SparkConf().setMaster("local").setAppName("test");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        SparkSession sparkSession = SparkSession.builder().appName("SparkSQL_demo").master("local").getOrCreate();
        SparkContext sparkContext = sparkSession.sparkContext();

        KuduContext kuduContext = new KuduContext(kuduMasters,sparkContext);


        // Delete the table if it already exists.
        if(kuduContext.tableExists("sfmta_kudu")) {
            kuduContext.deleteTable("sfmta_kudu");
        }

        kuduContext.createTable("sfmta_kudu", sftmta_prep.schema,
                /* primary key */ Seq("REPORT_TIME", "VEHICLE_TAG"),
                new CreateTableOptions()
                        .setNumReplicas(3)
                        .addHashPartitions(Arrays.asList("VEHICLE_TAG"), 4));


    }

    public void loadKuduData(){
        SparkConf conf = new SparkConf().setMaster("local").setAppName("test");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        SparkSession sparkSession = SparkSession.builder().appName("SparkSQL_demo").master("local").getOrCreate();
        SparkContext sparkContext = sparkSession.sparkContext();

        KuduContext kuduContext = new KuduContext(kuduMasters,sparkContext);

        kuduContext.insertRows(sftmta_prep, "sfmta_kudu")
// Create a DataFrame that points to the Kudu table we want to query.
        val sfmta_kudu = spark.read
                .option("kudu.master", "localhost:7051,localhost:7151,localhost:7251")
                .option("kudu.table", "sfmta_kudu")
                // We need to use leader_only because Kudu on Docker currently doesn't
                // support Snapshot scans due to `--use_hybrid_clock=false`.
                .option("kudu.scanLocality", "leader_only")
                .format("kudu").load
        sfmta_kudu.createOrReplaceTempView("sfmta_kudu")
        spark.sql("SELECT count(*) FROM sfmta_kudu").show()
        spark.sql("SELECT * FROM sfmta_kudu LIMIT 5").show()
    }


}
