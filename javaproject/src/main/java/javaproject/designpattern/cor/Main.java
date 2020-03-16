package javaproject.designpattern.cor;

import java.util.ArrayList;
import java.util.List;

/**
 * 责任链  Chain of Responsibility
 *
 */
public class Main {
    public static void main(String[] args) {
       Msg msg = new Msg();
       msg.setMsg("<script>,mashibing.com,大家都是996,:)");

       FilterChain fc = new FilterChain();
       fc.add(new HTMLFilter())
               .add(new SensitiveFilter());
       fc.doFilter(msg);

       FilterChain fc2 = new FilterChain();
       fc2.add(new FaceFilter())
               .add(new UrlFilter());
       fc.add(fc2);

       //这就相当于一个初级的链条,但是每次增加一条链子，就要重新修改代码，扩展性也不好
        //所以要封装成一个FilterChain类，一个链子下，有很多的链条
       /*List<Filter> list = new ArrayList<>();
       list.add(new HTMLFilter());
       list.add(new SensitiveFilter());

        for (Filter filter : list) {
            filter.doFilter(msg);
        }*/

       //普通的方式,这样写扩展性不好
        /*String r = msg.getMsg();
        r=r.replace('<','[');
        r=r.replace('>',']');
        msg.setMsg(r);

        r = r.replace("996","955");
        msg.setMsg(r);
        System.out.println(msg);*/
    }
}

class Msg {
    String name;
    String msg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}

interface Filter{
    boolean doFilter(Msg msg);
}

//html 过滤器
class HTMLFilter implements Filter{
    @Override
    public boolean doFilter(Msg msg) {
        String r = msg.getMsg();
        r=r.replace('<','[');
        r=r.replace('>',']');
        msg.setMsg(r);
        return true;
    }
}
//敏感词过滤器
class SensitiveFilter implements  Filter{
    @Override
    public boolean doFilter(Msg msg) {
        String r = msg.getMsg();
        r = r.replace("996","955");
        msg.setMsg(r);
        System.out.println(msg);
        return false;
    }
}
class FaceFilter implements Filter{
    @Override
    public boolean doFilter(Msg msg) {
        String r = msg.getMsg();
        r.replace(":)","^_^");
        msg.setMsg(r);
        System.out.println(msg);
        return true;
    }
}
//url过滤器
class UrlFilter implements Filter{
    @Override
    public boolean doFilter(Msg msg) {
        String r = msg.getMsg();
        r.replace("mashibing.com","ccccccc.com");
        msg.setMsg(r);
        System.out.println(msg);
        return true;
    }
}



//定义一个链条
class FilterChain implements Filter {
    List<Filter> filters = new ArrayList<>();

    public FilterChain add(Filter f) {
        filters.add(f);
        return this;
    }

    public boolean doFilter(Msg m) {
        for (Filter f : filters) {
            if (!f.doFilter(m)) {
                return false;
            }
        }
        return true;
    }
}
