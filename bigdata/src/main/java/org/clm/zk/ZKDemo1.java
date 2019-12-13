package org.clm.zk;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZKDemo1 {
	 private static final String CONNECT_STRING = "hadoop1:2181,hadoop2:2181,hadoop3:2181";
	    //如果zookeeper使用的是默认端口的话，此处可以省略端口号
	    //private static final String CONNECT_STRING = "hadoop1,hadoop2,hadoop3";
	    
	    //设置超时时间
	    private static final int SESSION_TIMEOUT = 5000;
	    
	    public static void main(String[] args) throws Exception {
	        //获取zookeeper的连接
	        //没有配置监听的话，最后一个参数设为null
	        ZooKeeper zk = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, null);
	        
	        
	        
	        //创建一个节点
	        /**
	         * 四个参数path, data, acl, createMode
	         * path:创建节点的绝对路径
	         * data：节点存储的数据
	         * acl:权限控制
	         * createMode：节点的类型----永久、临时    有编号的、没有编号的
	         * 
	         * */
	        //String create = zk.create("/xx", "xx".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
	        //System.out.println(create);//输出的结果是：/xx0000000008
	        
	        /**
	         * 判断节点是否存在
	         * */
	        Stat exists = zk.exists("/xx0000000008", null);
	        if(exists == null) {
	            System.out.println("节点不存在");
	        }else {
	            System.out.println("节点存在");
	        }
	        
	        
	        /**
	         * 查看节点的数据
	         * 
	         * */
	        /*byte[] data = zk.getData("/xx0000000008", false, null);
	        System.out.println(new String(data));*/
	        
	        
	        /**
	         * 修改节点的数据
	         * */
	        /*Stat setData = zk.setData("/xx0000000008", "xyz".getBytes(), -1);
	        if(setData == null) {
	            System.out.println("节点不存在 --- 修改不成功");
	        }else {
	            System.out.println("节点存在 --- 修改成功");
	        }*/
	        
	        
	        /**
	         * 删除节点
	         * */
	        
	        /*zk.delete("/xx0000000008", -1);*/
	        //关闭zookeeper的连接
	        zk.close();
	    }
}
