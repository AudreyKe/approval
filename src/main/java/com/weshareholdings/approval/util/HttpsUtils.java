package com.weshareholdings.approval.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Slf4j
public class HttpsUtils {

    /**
     * http post请求
     */
    public static String post(String url, String jsonStr) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        String response = null;
        try {
            post.setHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8");
            StringEntity entity = new StringEntity(jsonStr, "utf-8");
//            s.setContentEncoding("UTF-8");
//            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(entity);
            HttpResponse res = httpclient.execute(post);
            response = EntityUtils.toString(res.getEntity());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    /**
     * 调用第三方数据接口工具类
     * 处理https GET/POST请求
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr, String contentType, int timeout) {
        StringBuffer buffer = null;
        try {
            //创建SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL");
            TrustManager[] tm = {new MyX509TrustManager()};
            //初始化
            sslContext.init(null, tm, new java.security.SecureRandom());
            //获取SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(timeout);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Charset", "utf-8");
            //设置请求数据类型
            conn.setRequestProperty("Content-Type", contentType);
            //设置当前实例使用的SSLSoctetFactory
            conn.setSSLSocketFactory(ssf);

            conn.connect();
            //往服务器端写内容
            try {
                if (null != outputStr) {
                    OutputStream os = conn.getOutputStream();
                    os.write(outputStr.getBytes("utf-8"));
                    os.flush();
                    os.close();
                }
            } catch (Exception e) {
                log.error("向第三方发送请求数据时出现异常！异常信息：【{}】，请求数据：【{}】",
                        new Object[]{e.getStackTrace(), outputStr});
                throw new RuntimeException(e);
            }
            //读取服务器端返回的内容
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            try (BufferedReader br = new BufferedReader(isr)) {
                buffer = new StringBuffer();
                String line = null;
                while ((line = br.readLine()) != null) {
                    buffer.append(line);
                }
            } catch (Exception e) {
                log.error("读取第三方返回数据时出现异常，异常信息：【{}】", e.getStackTrace());
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            log.error("发送第三方请求数据失败，异常信息：【{}】，请求地址：【{}】，请求方式：【{}】，请求参数：【{}】",
                    new Object[]{e.getStackTrace(), requestUrl, requestMethod, outputStr});
            throw new RuntimeException("发送第三方请求数据失败!", e);
        }
        return buffer.toString();
    }


    private static class MyX509TrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    public static void main(String[] args) {
//        String s=httpsRequest("https://www.baidu.com/","POST",null,null,2000);
//        System.out.println(s);
    }
}

