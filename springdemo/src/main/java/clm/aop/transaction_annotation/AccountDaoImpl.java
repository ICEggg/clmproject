package clm.aop.transaction_annotation;

import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements IAccountDao {
    public void saveAcount() {
        System.out.println("this is AccountDaoImpl1 de saveAcount");
    }
}
