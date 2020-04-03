package javaproject.designpattern.cor.servletfiltercor;


import java.util.ArrayList;
import java.util.List;

/**
 * 责任链模拟servlet的filterchain
 * 对请求和响应进行过滤，request和response
 * 比如request请求过来，调用过滤器顺序是1,2,3；response返回回去过滤器顺序是3,2,1
 *
 */
public class ServletMain {
    public static void main(String[] args) {
        Request request = new Request();
        Response response = new Response();
        FilterChain fc = new FilterChain();
        fc.add(new Filter1());

        FilterChain fc2 = new FilterChain();
        fc2.add(new Filter2());

        fc.add(fc2);

        fc.doFilter(request,response,fc);
        System.out.println(request.str+"..."+response.str);

    }
}

interface Filter{
    boolean doFilter(Request request, Response response, FilterChain chain);
}

class Filter1 implements Filter {
    @Override
    public boolean doFilter(Request request, Response response,FilterChain chain) {
        request.str +="--1";
        chain.doFilter(request,response,chain);
        response.str +="**1";
        return false;
    }
}
class Filter2 implements Filter {
    @Override
    public boolean doFilter(Request request, Response response,FilterChain chain) {
        request.str +="--2";
        chain.doFilter(request,response,chain);
        response.str +="**2";
        return false;
    }
}

class FilterChain implements Filter {
    List<Filter> filters = new ArrayList<>();
    int index = 0;
    public void add(Filter filter){
        filters.add(filter);
    }
    @Override
    public boolean doFilter(Request request,Response response,FilterChain chain) {
        if(index == filters.size()) return false;
        Filter f = filters.get(index);
        index++;

        return f.doFilter(request,response,chain);
    }
}




class Request{
    String str = "request";
}
class Response{
    String str = "response";
}




