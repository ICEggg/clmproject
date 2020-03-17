package clm.ioc_annotation.dao;

import org.springframework.stereotype.Repository;

@Repository("accountDao2")
public class AccountDaoImpl2 implements IAccountDao {
    public void saveAcount() {
        System.out.println("this is AccountDaoImpl2 de saveAcount");
    }
}
