package clm.xml.dao;

import clm.xml.domain.Account;
import clm.xml.domain.User;

import java.util.List;

public interface IAccountDao {

    /**
     * 查询所有账户
     * @return
     */
    List<Account> findAllAccount();

    /**
     * 查询所有账户，带有名称和地址信息
     * @return
     */
    List<User> findAllAccountUsernameAddress();

    /**
     * 查询所有账户，延迟加载
     * @return
     */
    List<Account> findAllAccountLazy();

    /**
     * 根据用户id查账户
     * @return
     */
    List<Account> findAccountByUserId();
}
