package javaproject.thread.list;
import java.util.ArrayList;
import java.util.List;

/**
 * 多线程处理同一个list的数据
 */
public class ThreadLIst {

	public static void main(String[] args) {
		int listsize = 17;	//list有多少数据
		int index = 3;	//	启动多少个线程
		//造点数据
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < listsize; i++) {
			list.add(i);
		}


		way(list,index);

	}

	static void way(List<Integer> list ,int index){
		int listsize = list.size();
		if(index > listsize){
			index = listsize;
		}

		int end = 0;
		int basenum = list.size()/index;
		int remaindernum = list.size()%index;
		for (int i = 0; i < index; i++) {
			int start = end;
			end = start + basenum;
			if(i == index-1){
				end = start + basenum + remaindernum;
			}
			//System.out.println(start +"--------"+end);
			myt m = new myt("这是线程"+String.valueOf(i),list,start,end);
			new Thread(m).start();
		}
	}

	static class myt implements Runnable{
		private static List<Integer> list;
		private String threadname;
		private int start;
		private int end;

		public myt(String threadname,List<Integer> list,int start,int end){
			this.threadname = threadname;
			this.list = list;
			this.start = start;
			this.end = end;
		}

		@Override
		public void run() {
			for (int i = start; i < end; i++) {
					System.out.println(threadname+"数据是："+list.get(i));
			}
		}
	}



	
}
