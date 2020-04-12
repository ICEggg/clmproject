package org.clm.demo.util;

import org.clm.demo.mvc.primiary.entity.StaticEntity;

/**
 * static静态方法和静态块，调用yml文件里的配置
 */
public class StaticUtil {
    private static String port;

    static{
        StaticEntity portEntity = SpringContextUtil.getBean(StaticEntity.class);
        port = portEntity.getPort();
        System.out.println("------------------------------"+port);
    }

    public static void testUtil() {
        System.out.println(port);
    }
}
