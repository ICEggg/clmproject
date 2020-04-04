package javaproject.java.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class AnnotationTest {
    //注解可以显式赋值，如果没有默认值，就必须给注解赋值
    @MyAnnotation(name="aaa")
    public void way(){

    }
}

/**
 * 自定义注解
 *
 * 元注解4个：负责解释其他注解的注解
 * @Target : 可以作用在什么上面，.TYPE可以作用在类上，.METHOD作用在方法上等
 * @Retention：表示需要在什么级别保存该注解信息，描述注解的生命周期（自定义的时候大多都是RUNTIME）
 *      （RUNTIME>CLASS>SOURCE）：（在运行时>在编译后>在源代码）有效
 * @Document 说明该注解是否被包含在javadoc中
 * @Inherited：说明子类可以继承父类中的该注解
 *
 *
 * java一些注解的解释：
 * @Override 重写
 * @SuppressWarnings：镇压警告，隐藏一下不影响的报错，比如黄色的警告等
 */

//表示注解可以用在哪里
@Target({ElementType.TYPE,ElementType.METHOD})
//在什么地方有效
@Retention(value = RetentionPolicy.RUNTIME)
@interface MyAnnotation{
    //如果一个参数都没有，默认有一个value属性
    //注解参数： 参数类型 + 参数名（）；
    String name() default "";   //当加了default，使用该注解的地方，就不用显示的写 name=“aaa”，默认就是“”
    int age() default  0;
    int id() default -1;    //如果默认值是-1，表示不存在

    String[] schools() default {"清华","北大"};
}





















