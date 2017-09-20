package com.example.qyh.joe.utils;

import com.example.qyh.joe.bean.DataBean;
import com.example.qyh.joe.bean.DataDetailBean;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;


public class NewsJsonUtils {

    private final static String TAG = "NewsJsonUtils";

    /**
     * 将获取到的json转换为新闻列表对象
     *
     * @param res
     * @param value
     * @return
     */
    public static List<DataBean> readJsonDataBeans(String res, String value) {
        List<DataBean> beans = new ArrayList<DataBean>();
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObj.get(value);
            if (jsonElement == null) {
                return null;
            }
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 1; i < jsonArray.size(); i++) {
                JsonObject jo = jsonArray.get(i).getAsJsonObject();
                if (jo.has("skipType") && "special".equals(jo.get("skipType").getAsString())) {
                    continue;
                }
                if (jo.has("TAGS") && !jo.has("TAG")) {
                    continue;
                }

                if (!jo.has("imgextra")) {
                    DataBean news = JsonUtils.deserialize(jo, DataBean.class);
                    beans.add(news);
                }
            }
        } catch (Exception e) {
            //  LogUtils.e(TAG, "readJsonDataBeans error" , e);
        }
        return beans;
    }

    public static DataDetailBean readJsonNewsDetailBeans(String res, String docId) {
        DataDetailBean newsDetailBean = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObj.get(docId);
            if (jsonElement == null) {
                return null;
            }
            newsDetailBean = JsonUtils.deserialize(jsonElement.getAsJsonObject(), DataDetailBean.class);
        } catch (Exception e) {
            // LogUtils.e(TAG, "readJsonNewsBeans error" , e);
        }
        return newsDetailBean;
    }

}
