package clm.annotation.dao;

import clm.annotation.domain.Account;
import clm.annotation.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 *
 */
public interface IAccountDao {

    /*
        一对一
    * FetchType是个枚举，
    *   LAZY：懒加载
        EAGER：立即加载
        DEFAULT：默认给一个加载方式
        one注解：是指定一对一，对应的dao的一个方法
    */
    @Select("select * from account")
    @Results(id="accountMap",value = {
            @Result(id=true,column = "id",property = "id"),
            @Result(column = "uid",property = "uid"),
            @Result(column = "money",property = "money"),
            @Result(column = "uid",property = "user",
                    one=@One(select = "clm.annotation.dao.IUserDao.findUserById",fetchType = FetchType.EAGER))
    })
    List<Account> findAllAccount();

    @Select("select * from account where uid=#{uid}")
    List<Account> findAccountByUid(Integer uid);


}
