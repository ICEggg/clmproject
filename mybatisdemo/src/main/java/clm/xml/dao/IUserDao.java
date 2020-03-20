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

}
