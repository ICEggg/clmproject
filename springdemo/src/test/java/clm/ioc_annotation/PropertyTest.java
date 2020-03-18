package clm.ioc_annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * junit  单元测试
 * spring整合junit配置
 *      1、导入spirng的junit的jar：spring-test
 *      2、使用junit提供的一个注解把原有的main方法替换了，替换成spring提供的
 *          @Runwith
 *      3、告诉spring的运行器，spring和ioc的创建是基于xml还是注解
 *          @ContextConfiguration
 *              参数：location:指定xml文件的位置，加上classpath关键字，表示类路径下
 *                    classes：指定注解类所在的位置
 *
 *      spring5.x版本，要求junit的jar必须是4.1.2及以上
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class PropertyTest {

    @Test
    public void  testProperty(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);

    }
}
