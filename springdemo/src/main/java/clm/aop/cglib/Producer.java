package clm.aop.cglib;

import clm.aop.proxy.IProducer;

/**
 * 生产者
 */
public class Producer{
    /**
     * 销售
     * @param money
     */
    public void saleProduct(float money){
        System.out.println("卖东西，并拿到钱 ： "+money);
    }

    /**
     * 售后
     * @param money
     */
    public void afterService(float money){
        System.out.println("售后服务，并拿到钱 ： "+money);
    }
}
