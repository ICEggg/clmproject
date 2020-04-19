package javaproject.designpattern.adapter;

public class Test {

}

class America{
    int dianya_v = 220;

    public static void main(String[] args) {
        ResultDianya aa = new Adpter();

        Phone phone = new Phone();
        phone.output(aa);
    }
}

class Adpter extends America implements ResultDianya{
    @Override
    public int zhuanhuanhou() {
        return dianya_v/110;
    }

}

interface ResultDianya{
    int zhuanhuanhou();
}

class Phone{
    public void output(ResultDianya adpter){
        System.out.println(adpter.zhuanhuanhou());
    }

}
