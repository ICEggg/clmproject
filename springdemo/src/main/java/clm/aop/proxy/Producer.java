package clm.aop.proxy;

/**
 * 生产者
 */
public class Producer implements IProducer{
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
