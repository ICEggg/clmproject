package org.clm.demo.mvc.primiary.compositekey;

import lombok.Data;

import java.io.Serializable;

/**
 * 复合主键
 * 一定要实现Serializable接口！！！！！
 */
@Data
public class StandardPrimiaryKey implements Serializable {
    private String id;
    private String version;
}
