package clm.aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模拟service层，动态代理实现事务
 */
@Service
@Aspect
public class AccountService implements IAccountService {

    public void add() {
        System.out.println("add");
    }

    public int delete() {
        System.out.println("delete");
        return 0;
    }

    public void update(int i) {
        System.out.println("undate");
    }

    public List<String> find() {
        System.out.println("find");
        return null;
    }
}
