package javaproject.java.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库连接池
 */
public class ConnectionPool {
    List<Connection> cs = new ArrayList<Connection>();
    //连接池的数量
    int size = 10;

    //定义连接池里的最大连接数
    public ConnectionPool(){
        init();
    }

    public void init(){
        //这里恰恰不能使用try-with-resource的方式，
        // 因为这些连接都需要是活的，不要被自动关闭
        try{
            Class.forName("com.mysql.jdbc.Driver");
            for (int i = 0;i<size;i++){
                Connection c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/springbootdb1?characterEncoding=UTF-8",
                        "root", "root");
                cs.add(c);
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //需要连接时，将连接分配出去的方法
    public synchronized Connection getConnection(){
        while(cs.isEmpty()){
            try{
                this.wait();//this表示引用该函数所属类的当前对象
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Connection c = cs.remove(0);
        return c;
    }

    //用完连接后，将连接还给连接池的方法
    public synchronized void returnConnection(Connection c){
        cs.add(c);
        this.notifyAll();
    }
}
