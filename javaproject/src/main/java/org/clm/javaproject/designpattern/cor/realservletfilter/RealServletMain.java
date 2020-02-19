package org.clm.javaproject.designpattern.cor.realservletfilter;

import java.util.ArrayList;
import java.util.List;

public class RealServletMain {
    public static void main(String[] args) {
        Request request = new Request();
        Response response = new Response();
        FilterChain fc = new FilterChain();
        fc.add(new Filter1());
        fc.add(new Filter2());

        fc.doFilter(request,response);
        System.out.println(request.str+"..."+response.str);

    }
}

interface Filter{
    boolean doFilter(Request request, Response response, FilterChain chain);
}

class Filter1 implements Filter {
    @Override
    public boolean doFilter(Request request, Response response, FilterChain chain) {
        request.str +="--1";
        chain.doFilter(request,response);
        response.str +="**1";
        return false;
    }
}

class Filter2 implements Filter {
    @Override
    public boolean doFilter(Request request, Response response, FilterChain chain) {
        request.str +="--2";
        chain.doFilter(request,response);
        response.str +="**2";
        return false;
    }
}

class FilterChain{
    List<Filter> filters = new ArrayList<>();
    int index = 0;
    public void add(Filter filter){
        filters.add(filter);
    }

    public boolean doFilter(Request request, Response response) {
        if(index == filters.size()) return false;
        Filter f = filters.get(index);
        index++;

        return f.doFilter(request,response,this);
    }
}


class Request{
    String str = "request";
}

class Response{
    String str = "response";
}
