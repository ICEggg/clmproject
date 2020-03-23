package clm.xml.dao;

import clm.xml.domain.QueryVo;
import clm.xml.domain.User;
import java.util.List;

public interface IUserDao {
    //找所有
    List<User> findAll();
    //找一个
    User findUserById(int id);
    //保存
    void saveUser(User user);
    //更新
    void updateUser(User user);
    //删除
    void deleteUser(int id);
    //模糊查询
    List<User> findUserByName(String username);

    //根据queryVo中的条件查询用户
    List<User> findUserByVo(QueryVo vo);

    //根据传入的条件查询,有可能有用户名，或者性别，或者地址，或者都有
    List<User> findUserByCondition(User user);

    //根据in条件查询
    List<User> findUserInIds(QueryVo vo);

    //查找所有用户和对应的账户
    List<User> findAllUserAndAccount();

    //用户和角色的关系
    List<User> findAllUserAndRole();


    //查找所有用户   懒加载
    List<User> findAllUserLazy();

}
