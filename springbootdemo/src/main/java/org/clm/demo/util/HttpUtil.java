package org.clm.demo.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    //get请求
    public String get(String url){
        String content = "";
        try {
            //创建httpclient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //创建Http get请求
            HttpGet httpGet = new HttpGet(url);
            //接收返回值
            CloseableHttpResponse response = httpClient.execute(httpGet);

            System.out.println(response.getStatusLine().getStatusCode());
            if(response.getStatusLine().getStatusCode()==200){
                content = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println("--------->get请求返回值：" + content);
            }
            if(response!=null){
                response.close();
            }
            httpClient.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }



    //带参数的get请求
    public String get_with_param(String url, Map<String,String> parammap){
        try {
            //创建httpClient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //定义http get参数
//            URI uri = new URIBuilder(url).setParameter("categoryId", "4")
//                    .setParameter("page", "1").setParameter("rows", "30").build();

            URIBuilder uriBuilder = new URIBuilder(url);
            for (Map.Entry<String, String> entry : parammap.entrySet()) {
                uriBuilder.setParameter(entry.getKey(),entry.getValue());
            }
            URI uri = uriBuilder.build();
            System.out.println(uri);
            //创建http get请求
            HttpGet httpGet = new HttpGet(uri);

            //接受请求返回的定义
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String content = "";
            if(response.getStatusLine().getStatusCode()==200){
                content = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(content);
            }
            if(response!=null){
                response.close();
            }
            httpClient.close();
            return content;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


    public String post(String url){
        try{
            // 创建Httpclient对象
            CloseableHttpClient httpclient = HttpClients.createDefault();

            // 创建http POST请求
            HttpPost httpPost = new HttpPost(url);
            // 伪装成浏览器
            //httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36");

            CloseableHttpResponse response = httpclient.execute(httpPost);
            // 判断返回状态是否为200
            String content = "";
            if (response.getStatusLine().getStatusCode() == 200) {
                content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }
            if (response != null) {
                response.close();
            }
            httpclient.close();
            return content;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * post表单参数提交
     * @param url
     * @param parammap
     * @return
     */
    public String postWithForm(String url, Map<String,String> parammap){
        try{
            //创建httpclient
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //创建http post
            HttpPost httpPost = new HttpPost(url);
            //以表单格式提交
            httpPost.addHeader("Content-type","application/x-www-form-urlencoded;charset=UTF-8");
            //模拟浏览器设置头
//                httpPost.setHeader(
//                        "User-Agent",
//                        "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36");

            //设置请求数据
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : parammap.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), URLDecoder.decode(entry.getValue(),"UTF-8") ));
            }

            //构建表单
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params);
            //formEntity.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
            //将表达请求放入到httpost
            httpPost.setEntity(formEntity);

            //返回类型
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String content = EntityUtils.toString(response.getEntity(), "utf-8");
            if (response.getStatusLine().getStatusCode() == 200) {
                content = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(content);
                response.close();
                httpClient.close();
            }else{
                throw new Exception();
            }
            response.close();
            httpClient.close();
            return content;
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * post，json参数提交
     * @param url
     * @param json
     * @return
     */
    public String postWithJson(String url, String json) {
        String returnValue = "这是默认返回值，接口调用失败";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try{
            //第一步：创建HttpClient对象
            httpClient = HttpClients.createDefault();

            //第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);

            //第三步：给httpPost设置JSON格式的参数
            StringEntity requestEntity = new StringEntity(json,"utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json;charset=utf-8");
            httpPost.setEntity(requestEntity);

            //第四步：发送HttpPost请求，获取返回值
            returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法

        }
        catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //第五步：处理返回值
        return returnValue;
    }


}
