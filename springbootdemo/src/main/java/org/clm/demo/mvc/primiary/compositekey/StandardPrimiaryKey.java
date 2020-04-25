package org.clm.demo.mvc.primiary.compositekey;

import lombok.Data;

import java.io.Serializable;

/**
 * 联合主键，要把主键信息定义一个类，
 * 一定要实现Serializable接口
 */
@Data
public class StandardPrimiaryKey implements Serializable {
    private String id;
    private String version;
}
