package clm.xml.dao;

import clm.xml.domain.Account;
import clm.xml.domain.QueryVo;
import clm.xml.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MybtisTest {

    private InputStream in;
    private SqlSession session;
    private IUserDao userDao;
    private IAccountDao accountDao;

    @Before
    public void init() throws IOException {
        //1、读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2、创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //3、使用工厂生产SqlSession对象
        session = factory.openSession();
        //4、使用SqlSession创建Dao接口的代理对象
        userDao = session.getMapper(IUserDao.class);
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
    public void findAll(){
        //5、使用代理对象执行方法
        List<User> users = userDao.findAll();
        for (User user : users){
            System.out.println(user);
        }
    }

    @Test
    public void saveUser(){
        User user = new User();
        user.setAddress("shanghai");
        user.setBirthday(new Date());
        user.setSex("nan");
        user.setUsername("aaa");

        userDao.saveUser(user);
        //因为是手动提交事务，所以要手动提交（这一部分提到公共部分，@After）
        //session.commit();
    }

    @Test
    public void updateUser(){
        User user = new User();
        user.setId(50);
        user.setAddress("shanghaiiii");
        user.setBirthday(new Date());
        user.setSex("nannnn");
        user.setUsername("a");

        userDao.updateUser(user);
    }

    @Test
    public void deleteUser(){
        userDao.deleteUser(50);
    }

    @Test
    public void findUserById(){
        User user = userDao.findUserById(41);
        System.out.println(user);
    }

    @Test
    public void findUserByName(){
        List<User> list = userDao.findUserByName("%老%");
        for (User user:list){
            System.out.println(user);
        }
    }

    @Test
    public void findUserByVo(){
        QueryVo vo = new QueryVo();
        User u = new User();
        u.setUsername("%老%");

        vo.setUser(u);


        List<User> list = userDao.findUserByVo(vo);
        for (User user:list){
            System.out.println(user);
        }
    }


    @Test
    public void findUserByCondition(){
        User u = new User();
        u.setUsername("老王");
        u.setSex("女");
        List<User> users = userDao.findUserByCondition(u);
        for (User user : users){
            System.out.println(user);
        }
    }

    /**
     * 根据in条件查询
     * foreach标签的使用
     */
    @Test
    public void findUserInIds(){
        QueryVo vo = new QueryVo();
        List<Integer> list = new ArrayList<Integer>();
        list.add(41);
        list.add(42);
        list.add(43);
        vo.setIds(list);

        List<User> users = userDao.findUserInIds(vo);
        for (User user :users){
            System.out.println(user);
        }
    }

    /**
     * 获取所有账户
     */
    @Test
    public void findAllAccount(){
        List<Account> list = accountDao.findAllAccount();
        for (Account account :list){
            System.out.println(account);
            System.out.println(account.getUser());
        }
    }

}
