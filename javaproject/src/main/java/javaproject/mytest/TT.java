package javaproject.mytest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TT {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            list.add(i);
        }

        int threadNum = 10;
        ExecutorService service = Executors.newFixedThreadPool(threadNum);

        int everyList = list.size()/threadNum;
        int result = 0;

        for (int i = 1; i <= threadNum; i++) {

            Future<Integer> future = service.submit(new C(list, (i-1)*everyList, i*everyList));
            System.out.println(future.get());
            result += future.get();

        }
        service.shutdown();
        System.out.println(result);

    }
}
class C implements Callable<Integer> {
    List<Integer> list;
    int start;
    int end;

    public C(List<Integer> list, int start, int end) {
        this.list = list;
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() throws Exception {
        int result = 0;

        for (int i = start; i <= end; i++) {
            result += list.get(i);
        }
        return result;
    }
}
