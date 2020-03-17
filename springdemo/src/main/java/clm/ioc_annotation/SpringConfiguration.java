package clm.ioc_annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，和bean.xml是一样的
 *
 * Configuration
 *      作用：指定当前类是一个配置类
 *  ComponentScan
 *      作用：用于通过注释指定spring在创建容器时要扫描的包
 *      属性：value：和basePackages是一样的，用于指定创建容器时要扫描的包
 *
 * 使用该配置类，就相当于配置bean.xml
 */
@Configuration
@ComponentScan(basePackages = "clm.ioc_annotation")
public class SpringConfiguration {
}
