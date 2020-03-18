package clm.ioc_annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * 配置类，和bean.xml是一样的
 *
 *  @Configuration
 *      作用：指定当前类是一个配置类
 *      细节：当配置类作为AnnotationConfigApplicationContext对象创建参数时，该注解可以不写
 *  @ComponentScan
 *      作用：用于通过注释指定spring在创建容器时要扫描的包
 *      属性：value：和basePackages是一样的，用于指定创建容器时要扫描的包
 *  @import
 *      作用：用于导入其他的配置类
 *      属性：value：用于指定其他配置类的字节码
 *      当我们使用Import的注解后，有Import注解的类就是父配置类，而导入的都是子配置类
 * @PropertySource
 *      作用：用于指定properties文件的位置
 *      属性：value：指定文件的名称和路径
 *              关键字：classpath：表示类路径下
 *
 * 使用该配置类，就相当于配置bean.xml
 */
@Configuration
@ComponentScan(basePackages = "clm.ioc_annotation")
@Import(JdbcConfig.class)
@PropertySource(value = "classpath:jdbcConfig.properties")
public class SpringConfiguration {
}
