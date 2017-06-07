package com.enumaelish.webmagic;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chenyu on 2017/6/6.
 */
public class iptest {
    public static void main(String[] args) {

//        List<String> ips = addip();
//        List<Integer> port = addport();
//        for (int i = 0; i < ips.size(); i++) {
//            // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
//            System.getProperties().setProperty("http.proxyHost", ips.get(i));
//            System.getProperties().setProperty("http.proxyPort", port.get(i).toString());
//            // 判断代理是否设置成功
//            // 发送 GET 请求
//            String x = sendGet(
//                    "http://www.baidu.com",
//                    "");
//            System.out.println(x);
//            // 发送 POST 请求
//            if(x.length() > 0){
//                System.out.println( ips.get(i) + ": " + port.get(i).toString());
//            }
//            String s = httpGet(new HttpHost(ips.get(i), port.get(i) ) );
//            if(s != null){
//                System.out.println( ips.get(i) + ":" + port.get(i));
//            }
//        }
        String s = httpGet(new HttpHost("120.132.71.212", 80 ));
        System.out.println(s);
    }
    public static String httpGet(HttpHost proxy) {
        String content = null;
        DefaultHttpClient httpclient = null;
        try {
            httpclient = new DefaultHttpClient();
            /** 设置代理IP **/
            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);

            HttpGet httpget = new HttpGet("http://www.baidu.com");
            httpget.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,1000*3);  //设置请求超时时间
            httpget.setHeader("Proxy-Authorization","Basic eXVsb3JlOll1bG9yZVByb3h5MjAxMzo=");
            httpget.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79 Safari/537.1");
            httpget.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000*3);
            HttpResponse responses = httpclient.execute(httpget);
            HttpEntity entity = responses.getEntity();
            content = EntityUtils.toString(entity);
        } catch (Exception e) {
        } finally {
            httpclient.getConnectionManager().shutdown();   //关闭连接
        }
        return content;
    }
    private static List<String> addip(){
        List<String> ips = new ArrayList<>();
        try {
            String encoding="UTF8";
            File file=new File("D://1.txt");
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    ips.add(lineTxt);
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return ips;
    }
    private static List<Integer> addport(){
        List<Integer> ips = new ArrayList<>();
        try {
            String encoding="GBK";
            File file=new File("D://2.txt");
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    ips.add(Integer.valueOf(lineTxt));
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return ips;
    }
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.setConnectTimeout(2000);
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
