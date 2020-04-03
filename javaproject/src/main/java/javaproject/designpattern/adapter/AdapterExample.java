package javaproject.designpattern.adapter;

import java.util.LinkedList;
import java.util.List;

/**
 * 例子，我们国家的民用电都是 220V，日本是 110V，而我们的手机充电一般需要 5V，
 * 这时候要充电，就需要一个电压适配器，将 220V 或者 100V 的输入电压变换为 5V 输出
 */
public class AdapterExample {
    private List<DC5Adapter> adapters = new LinkedList<DC5Adapter>();

    public AdapterExample() {
        this.adapters.add(new ChinaPowerAdapter());
        this.adapters.add(new JapanPowerAdapter());
    }

    // 根据电压找合适的变压器
    public DC5Adapter getPowerAdapter(AC ac) {
        DC5Adapter adapter = null;
        for (DC5Adapter ad : this.adapters) {
            if (ad.support(ac)) {
                adapter = ad;
                break;
            }
        }
        if (adapter == null){
            throw new  IllegalArgumentException("没有找到合适的变压适配器");
        }
        return adapter;
    }
    public static void main(String[] args) {
        AdapterExample test = new AdapterExample();
        AC chinaAC = new AC220();
        DC5Adapter adapter = test.getPowerAdapter(chinaAC);
        adapter.outputDC5V(chinaAC);

        // 去日本旅游，电压是 110V
        AC japanAC = new AC110();
        adapter = test.getPowerAdapter(japanAC);
        adapter.outputDC5V(japanAC);
    }



    static interface AC{
        int outputAC();
    }

    static class AC110 implements AC {
        public final int output = 110;

        @Override
        public int outputAC() {
            return output;
        }
    }

    static class AC220 implements AC {
        public final int output = 220;

        @Override
        public int outputAC() {
            return output;
        }
    }

    /**
     * 其中 support() 方法用于检查输入的电压是否与适配器匹配，
     * outputDC5V() 方法则用于将输入的电压变换为 5V 后输出
     */
    static interface DC5Adapter {
        boolean support(AC ac);

        int outputDC5V(AC ac);
    }

    static class ChinaPowerAdapter implements DC5Adapter {
        public static final int voltage = 220;

        @Override
        public boolean support(AC ac) {
            return (voltage == ac.outputAC());
        }

        @Override
        public int outputDC5V(AC ac) {
            int adapterInput = ac.outputAC();
            //变压器...
            int adapterOutput = adapterInput / 44;
            System.out.println("使用ChinaPowerAdapter变压适配器，输入AC:" + adapterInput + "V" + "，输出DC:" + adapterOutput + "V");
            return adapterOutput;
        }
    }

    static class JapanPowerAdapter implements DC5Adapter {
        public static final int voltage = 110;

        @Override
        public boolean support(AC ac) {
            return (voltage == ac.outputAC());
        }

        @Override
        public int outputDC5V(AC ac) {
            int adapterInput = ac.outputAC();
            //变压器...
            int adapterOutput = adapterInput / 22;
            System.out.println("使用JapanPowerAdapter变压适配器，输入AC:" + adapterInput + "V" + "，输出DC:" + adapterOutput + "V");
            return adapterOutput;
        }
    }




}
