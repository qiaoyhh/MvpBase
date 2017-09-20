package com.example.qyh.joe.commons;

/**
 * Description : 接口API的URL
 */
public interface Urls {

    //http://c.m.163.com/nc/article/headline/T1348647909107/0-5.html  头条

    int PAGE_SIZE = 20;

    String HOST = "http://c.m.163.com/";
    String END_URL = "-" + PAGE_SIZE + ".html";
    String END_DETAIL_URL = "/full.html";
    // 头条
    String TOP_URL = HOST + "nc/article/headline/";
    String TOP_ID = "T1348647909107";
    // 新闻详情
    String NEW_DETAIL = HOST + "nc/article/";

    String COMMON_URL = HOST + "nc/article/list/";

    // 汽车
    String CAR_ID = "T1348654060988";
    // 笑话
    String JOKE_ID = "T1350383429665";
    // nba
    String NBA_ID = "T1348649145984";

    // 图片
    String IMAGES_URL = "http://api.laifudao.com/open/tupian.json";

    // 天气预报url
    String WEATHER = "http://wthrcdn.etouch.cn/weather_mini?city=";

    //百度定位
    String INTERFACE_LOCATION = "http://api.map.baidu.com/geocoder";

    //==================================
    //妹子图片API
    String BASE_MEIZITU_URL = "http://www.mzitu.com/";
}
