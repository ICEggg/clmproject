package clm.annotation.dao;

import clm.annotation.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * mybatis针对CRUD有四个注释
 * @SELECT,@INSERT,@UPDATE,@DELETE
 */
@CacheNamespace(blocking = true)//默认false，开始二级缓存:true
public interface IUserDao {
    /*property参数：实体类中的字段名，映射到column：库表中的字段名*/
    @Select("select * from user")
    @Results(id="userMap",value = {
            @Result(id=true,column = "id",property = "id"),
            @Result(column = "username",property = "username"),
            @Result(column = "address",property = "address"),
            @Result(column = "sex",property = "sex"),
            @Result(column = "birthday",property = "birthday"),
            @Result(column = "id",property = "accounts",many = @Many(select = "clm.annotation.dao.IAccountDao.findAccountByUid",fetchType = FetchType.LAZY))
    })
    List<User> findAllUser();

    @Insert("insert into user(username,address,sex,birthday) values (#{username},#{address},#{sex},#{birthday})")
    @ResultMap(value={"userMap"})
    void saveUser(User user);

    @Update("update user set username = #{username},sex = #{sex},birthday = #{birthday} where id = #{id}")
    void updateUser(User user);

    @Delete("delete from user where id = #{id}")
    void deleteUser(Integer id);

    @Select("select * from user where id=#{id}")
    @ResultMap(value={"userMap"})
    User findUserById(Integer id);

}
