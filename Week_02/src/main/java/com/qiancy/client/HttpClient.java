
package com.qiancy.client;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 *
 *
 * @author qcyki
 * @create 2020/10/28
 * @since 1.0.0
 */
public class HttpClient {

    public static void main (String[]args){
        //设置请求配置信息
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(200)
                .setConnectionRequestTimeout(200)
                .setSocketTimeout(200)
                .build();

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet("http://localhost:8801");
        httpGet.setConfig(requestConfig);
        try {
            CloseableHttpResponse reponse = httpClient.execute(httpGet);
            System.out.println(String.format("rsp code: %s",reponse.getStatusLine().getStatusCode()));
            HttpEntity httpEntity = reponse.getEntity();
            String content = "";
            if (httpEntity != null) {
                content = EntityUtils.toString(httpEntity, "UTF-8");
            }
            System.out.println(String.format("rsp content: %s",content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
