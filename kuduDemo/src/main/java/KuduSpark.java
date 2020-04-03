import org.apache.avro.ipc.specific.Person;
import org.apache.kudu.client.*;
import org.apache.kudu.spark.kudu.*;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.*;
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
    private static String KUDU_MASTERS;
    private static String tableName;
    private static int tableNumReplicas = Integer.getInteger("tableNumReplicas", 1);

    private static String nameCol = "name";
    private static String idCol = "id";
    private KuduContext kuduContext;

    private SparkSession sparkSession;

    static {

        KUDU_MASTERS = System.getProperty("kuduMasters", "hdp004.beyondsoft.com:7051");
        //tableName = "user";
    }

    public KuduSpark(){
        //sparkSession = SparkSession.builder().master("hdp001.beyondsoft.com").appName("kuduexample").getOrCreate();
        sparkSession = SparkSession.builder().master("local[*]").appName("kuduexample").getOrCreate();
        kuduContext = new KuduContext(KUDU_MASTERS, sparkSession.sparkContext());
    }

    public static void main(String[] args) {
        /*String hdfsPath = "D:\\aaa.txt";
        String tableName = "user";
        KuduSpark ks = new KuduSpark();
        *//*StructType schema = ks.createTableSchema();
        ks.createTable(tableName,schema);
        ks.loadData(hdfsPath,schema,tableName);*//*

        ks.readKuduData(tableName);*/
    }

    /**
     * 创建表的schema
     * @return
     */
    public StructType createTableSchema(){

        List<StructField> fields = new ArrayList<>();

        StructField idField = DataTypes.createStructField("id", DataTypes.IntegerType, false);
        fields.add(idField);
        StructField nameField = DataTypes.createStructField("name", DataTypes.StringType, false);
        fields.add(nameField);

        StructType schema = DataTypes.createStructType(fields);
        return schema;
    }

    /**
     * 正式创建表
     */
    public void createTable(String tableName,StructType schema){
        if(kuduContext.tableExists(tableName)) {
            kuduContext.deleteTable(tableName);
        }

        kuduContext.createTable(tableName, schema,
                JavaConverters.asScalaIteratorConverter(Arrays.asList("id", "name").iterator()).asScala().toSeq(),
                new CreateTableOptions()
                        .setNumReplicas(3)
                        .addHashPartitions(Arrays.asList("id"), 2));
        System.out.println("表："+tableName+" 创建成功");
    }

    public void loadData(String hdfsPath,StructType schema,String tableName){
        JavaRDD<User> javaRdd = sparkSession.sparkContext().textFile(hdfsPath,1).toJavaRDD()
                .map((line)->{
                    String name = line.split(",")[0];
                    Integer id = Integer.parseInt( line.split(",")[1]);
                    User user1 = new User(name,id);
                    return user1;
                });

        Dataset<Row> dataset = sparkSession.createDataFrame(javaRdd,User.class);

        /*Dataset<Row> dataset1 = sparkSession.read().csv(hdfsPath).
        dataset1.toDF().show();*/

        kuduContext.insertRows(dataset, tableName);

        /*Dataset dataset = sparkSession.read()
                .option("kudu.master", KUDU_MASTERS)
                .option("kudu.table", "student")
                // We need to use leader_only because Kudu on Docker currently doesn't
                // support Snapshot scans due to `--use_hybrid_clock=false`.
                .option("kudu.scanLocality", "leader_only")
                .format("kudu").load();*/
    }

    public void readKuduData(String tableName){
        List<String> colList = new ArrayList<>();
        colList.add(nameCol);
        colList.add(idCol);
        Seq<String> readCols = JavaConverters.asScalaIteratorConverter(colList.iterator()).asScala().toSeq();
        JavaRDD<Row> readRDD = kuduContext.kuduRDD(sparkSession.sparkContext(), tableName, readCols).toJavaRDD();
        System.out.println(readRDD.count());
        readRDD.foreach((line)->{
            System.out.println(line);
        });

    }


    public void way(){
        //表的列
        List<String> fieldNameList = new ArrayList<>();
        fieldNameList.add("a");
        fieldNameList.add("b");
        fieldNameList.add("c");

        SparkConf conf = new SparkConf().setMaster("local").setAppName("test");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        SparkSession sparkSession = SparkSession.builder().appName("SparkSQL_demo").master("local").getOrCreate();
        SparkContext sparkContext = sparkSession.sparkContext();



        KuduContext kuduContext = new KuduContext(KUDU_MASTERS,sparkContext);

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
            masterTableMap.put("kudu.master",KUDU_MASTERS);
            masterTableMap.put("kudu.table",tableName);
            DataFrameReader sqlDf = sparkSession.read().options(masterTableMap);
            //sqlDf.createOrReplaceTempView(tableName);

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




}
