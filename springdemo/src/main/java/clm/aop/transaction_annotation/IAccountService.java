package clm.aop.transaction_annotation;

import java.util.List;

public interface IAccountService {
    void add();
    int delete();
    void update(int i);
    List<String> find();
    void transfer(String sourceName, String targetName, Float money);
}
