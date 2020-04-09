package com.node.utils;

import com.alibaba.fastjson.JSON;
import com.node.pojo.Content;
import com.sun.org.apache.regexp.internal.RE;
import org.elasticsearch.action.IndicesRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
    @author www.github.com/Acc2020
    @date  2020/4/8
*/
public class HtmlParseUtil {
    // public static void main(String[] args) throws Exception {
    //     HtmlParseUtil.parseJD("vue").forEach(System.out::println);
    //     System.out.println(new HtmlParseUtil().isExistIndex(ESconst.ES_INDEX_JD));
    //
    // }
    public static List<Content> parseJD(String keywords) throws IOException {
        ArrayList<Content> list = new ArrayList<>();
        // 获取请求
        String url = "https://search.jd.com/Search?keyword=" + keywords;
        // 解析网页
        Document document = Jsoup.parse(new URL(url), 30000);
        // 能够使用所有使用 js 中的方法
        Element element = document.getElementById("J_goodsList");
        // System.out.println(element.html());
        // 获取所有的 li 元素
        Elements elements = element.getElementsByTag("li");
        for (Element li : elements) {

            // 页面没有加载出来，默认加载使用 source-data-lazy-img
            // data-img="1" source-data-lazy-img="//img11.360buyimg.com/n7/jfs/t1/37054/1/12327/313637/5d036a5fE35f26e20/f365f2357456269f.jpg
            String img = li.getElementsByTag("img").eq(0).attr("source-data-lazy-img");
            String price = li.getElementsByClass("p-price").eq(0).text();
            String title = li.getElementsByClass("p-name").eq(0).text();
            String shop = li.getElementsByClass("curr-shop").eq(0).text();


            Content content = new Content();
            content.setImg(img);
            content.setPrice(price);
            content.setTitle(title);
            content.setShop(shop);
            // 配置 collapse 为 title
            // content.setCollapse_title(title);
            list.add(content);

        }
        return list;
    }

    // 增加接解析出来的数据到 es 库中

    public Boolean addParseMsg(String keywords) throws IOException {
        List<Content> list = parseJD(keywords);
        System.out.println("========"+list);
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        for (int i = 0; i < list.size(); i++) {
            bulkRequest.add(new IndexRequest(ESconst.ES_INDEX_JD)
            .source(JSON.toJSONString(list.get(i)), XContentType.JSON));
        }
        return true;
    }

}
