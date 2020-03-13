package javaproject.java.yunwei;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.platform.windows.WindowsGlobalMemory;
import oshi.software.os.OperatingSystem;

/**
 * oshi 查看内存，cpu，系统参数等的包
 * 还没找到更好的，暂时用这个
 *
 * (还不会用，api地址：http://oshi.github.io/oshi/apidocs/oshi/hardware/HardwareAbstractionLayer.html)
 */
public class YunWei {

    public static void main(String[] args) {
        System.out.println(YunWei.aa());

    }

    static long aa(){
        WindowsGlobalMemory ww = new WindowsGlobalMemory();
        long aaa = ww.getAvailable();
        return aaa;
    }



}
