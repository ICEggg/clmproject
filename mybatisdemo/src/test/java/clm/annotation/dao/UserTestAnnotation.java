package clm.annotation.dao;

import clm.annotation.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class UserTestAnnotation {
    private InputStream in;
    private SqlSession session;
    private IUserDao userDao;

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
        userDao = session.getMapper(IUserDao.class);
    }

    @After
    public void destory() throws IOException{
        session.commit();
        //6、释放资源
        session.close();
        in.close();
    }

    @Test
    public void findAllUser(){
        List<User> users = userDao.findAllUser();
        for (User user : users){
            System.out.println(user);
            System.out.println(user.getAccounts());
        }
    }

    @Test
    public void saveUser(){
        User user = new User();
        user.setAddress("shanghai");
        //user.setBirthday(new Date());
        user.setSex("男");
        user.setUsername("aaa");

        userDao.saveUser(user);
    }

    @Test
    public void updateUser(){
        User user = new User();
        user.setId(49);
        user.setAddress("shanghaiiii");
        user.setBirthday(new Date());
        user.setSex("女");
        user.setUsername("a");

        userDao.updateUser(user);
    }

    @Test
    public void deleteUser(){
        Integer id = 49;
        userDao.deleteUser(id);
    }

    @Test
    public void findUserById(){
        User user = userDao.findUserById(41);
        System.out.println(user);
    }



}
