package clm.service.impl;

import clm.domain.Account;
import clm.service.AccountService;
import org.springframework.stereotype.Service;
import java.util.List;


@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Override
    public List<Account> findAllAccount() {
        return null;
    }

    @Override
    public List<Account> findAccountByUid(Integer uid) {
        return null;
    }






}
