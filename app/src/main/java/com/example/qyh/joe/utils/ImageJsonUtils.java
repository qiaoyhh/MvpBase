package com.example.qyh.joe.utils;

import com.example.qyh.joe.bean.MeiziTu;
import com.example.qyh.joe.bean.ThreeDataBean;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ImageJsonUtils {

    private final static String TAG = "ImageJsonUtils";

    private static volatile ImageJsonUtils mCache;

    private ImageJsonUtils()
    {

    }

    public static ImageJsonUtils getInstance()
    {

        if (mCache == null)
        {
            synchronized (ImageJsonUtils.class)
            {
                if (mCache == null)
                {
                    mCache = new ImageJsonUtils();
                }
            }
        }

        return mCache;
    }
    /**
     * 将获取到的json转换为图片列表对象
     * @param res
     * @return
     */
    public static List<ThreeDataBean> readJsonThreeDataBeans(String res) {
        List<ThreeDataBean> beans = new ArrayList<ThreeDataBean>();
        try {
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(res).getAsJsonArray();
            for (int i = 1; i < jsonArray.size(); i++) {
                JsonObject jo = jsonArray.get(i).getAsJsonObject();
                ThreeDataBean news = JsonUtils.deserialize(jo, ThreeDataBean.class);
                beans.add(news);
            }
        } catch (Exception e) {
        }
        return beans;
    }
    /**
     * 解析自拍妹子Html
     *
     * @param html
     * @param type
     * @return
     */
    public List<MeiziTu> parserMeiziTuByAutodyne(String html, String type) {
        List<MeiziTu> list = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        System.out.println("doc========"+doc);
        Elements p = doc.getElementsByTag("p");
        MeiziTu meiziTu;
        Element img;
        for (int i = 0; i < 15; i++)
        {
            meiziTu = new MeiziTu();
            img = p.get(i).select("img").first();
            String src = img.attr("src");
            String title = img.attr("alt");
            meiziTu.setOrder(i);
            meiziTu.setType(type);
            meiziTu.setWidth(0);
            meiziTu.setHeight(0);
            meiziTu.setImageurl(src);
            System.out.println("图片URL=========="+src);
            meiziTu.setTitle(title);
            list.add(meiziTu);
        }
        return list;
    }

}
