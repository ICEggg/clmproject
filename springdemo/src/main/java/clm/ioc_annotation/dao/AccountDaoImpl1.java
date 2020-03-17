package clm.ioc_annotation.dao;

import org.springframework.stereotype.Repository;

@Repository("accountDao1")
public class AccountDaoImpl1 implements IAccountDao {
    public void saveAcount() {
        System.out.println("this is AccountDaoImpl1 de saveAcount");
    }
}
