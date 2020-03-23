package clm.annotation.dao;

import clm.annotation.domain.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AccountTestAnnotation {
    private InputStream in;
    private SqlSession session;
    private IAccountDao accountDao;

    @Before
    public void init() throws IOException {
        //1、读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfigAnnotation.xml");
        //2、创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //3、使用工厂生产SqlSession对象
        session = factory.openSession();
        //4、使用SqlSession创建Dao接口的代理对象
        accountDao = session.getMapper(IAccountDao.class);
    }

    @After
    public void destory() throws IOException{
        session.commit();
        //6、释放资源
        session.close();
        in.close();
    }

    @Test
    public void findAllAccount(){
        List<Account> accounts = accountDao.findAllAccount();
        for (Account account : accounts){
            System.out.println(account);
            System.out.println(account.getUser());
        }
    }




}
