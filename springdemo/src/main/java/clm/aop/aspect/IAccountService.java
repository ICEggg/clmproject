package clm.aop.aspect;

import java.util.List;

public interface IAccountService {
    void add();
    int delete();
    void update(int i);
    List<String> find();
}
