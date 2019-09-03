package com.lix.pushmessage.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class MyHttpClient {
    private static String urlEmailTo = "http://47.102.153.125:9002/getPE?port=10000";
    //private static String urlWarningInfo = "http://47.102.153.125:9002/getInfo";
    public static void main(String[] args) {
        new MyHttpClient().getEmailTo("陕西天龙输变电建设有限公司");
    }
    public JSONArray getEmailTo(String param) {
        HttpClient hc = HttpClients.createDefault();
        HttpGet get = new HttpGet(urlEmailTo + "&target=" + param);
        String result = "";
        try {
            HttpResponse re = hc.execute(get);
            result = EntityUtils.toString(re.getEntity(), "utf-8");
        } catch (IOException e) {
            System.out.println("接口请求失败:" + e.getMessage());
        }
        JSONObject json = new JSONObject();
        try {
            json = JSON.parseObject(result);
        } catch (Exception e) {
            System.out.printf("json格式有误: %s \n接口返回的结果为result：%s \n", json.toString(), result);
            return null;
        }
        return json.getJSONArray("message_methods");
    }
}
