package clm.ioc.service;

import java.util.*;

/**
 * 复杂数据类型 依赖注入 测试类
 */
public class AccountServiceImpl3 implements IAccountService{
    private String[] myStr;
    private List<String> myList;
    private Set<String> mySet;
    private Map<String,String> myMap;
    private Properties myProps;

    public void setMyStr(String[] myStr) {
        this.myStr = myStr;
    }

    public void setMyList(List<String> myList) {
        this.myList = myList;
    }

    public void setMySet(Set<String> mySet) {
        this.mySet = mySet;
    }

    public void setMyMap(Map<String, String> myMap) {
        this.myMap = myMap;
    }

    public void setMyProps(Properties myProps) {
        this.myProps = myProps;
    }

    public void saveCount() {
        System.out.println("AccountServiceImpl3  saveCount方法执行了  ");
    }

    @Override
    public String toString() {
        return "AccountServiceImpl3{" +
                "myStr=" + Arrays.toString(myStr) +
                ", myList=" + myList +
                ", mySet=" + mySet +
                ", myMap=" + myMap +
                ", myProps=" + myProps +
                '}';
    }
}
