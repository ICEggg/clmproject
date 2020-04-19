package clm.service;

import clm.domain.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAllAccount();

    List<Account> findAccountByUid(Integer uid);

}
