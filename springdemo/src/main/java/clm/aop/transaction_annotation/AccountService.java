package clm.aop.transaction_annotation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 模拟service层，动态代理实现事务
 */
@Service(value = "accountService")
@Transactional
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

    /**
     * 根据人物名称，数据库查这个人的存款，一个减钱，一个加钱
     * @param sourceName   转账人
     * @param targetName   收账人
     * @param money 转账钱款
     */
    public void transfer(String sourceName, String targetName, Float money) {

    }

}
