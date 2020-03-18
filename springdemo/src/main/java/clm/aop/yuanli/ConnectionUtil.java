package clm.aop.yuanli;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 连接的工具，用于从数据源中获取一个连接，并且和线程绑定
 */
public class  ConnectionUtil{
    private ThreadLocal<Connection> t1 = new ThreadLocal<Connection>();

    //这个代表数据源，是没有用的，有空了改成mysql的
    private DataSource dataSource;

    //为了spring set注入
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取当前线程上的连接
     * 理论上，当线程用完了，线程和连接要做一个解绑的操作
     * @return
     */
    public Connection getThreadConnection(){
        Connection conn = null;
        try {
            conn = t1.get();
            if(conn == null){
                conn = dataSource.getConnection();
                t1.set(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 连接和线程解绑
     */
    public void removeConnection(){
        t1.remove();
    }


}
