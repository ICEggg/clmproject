package clm.aop.aspect_annotation;

import java.util.List;

public interface IAccountService {
    void add();
    int delete();
    void update(int i);
    List<String> find();
}
