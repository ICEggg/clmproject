package org.clm.demo.mvc.primiary.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置信息实体
 * 和StaticUtil相关
 */
@Component
public class StaticEntity {
    private String port;

    public String getPort() {
        return port;
    }

    //@Value("${testport}")
    public void setPort(String port) {
        this.port = port;
    }
}
