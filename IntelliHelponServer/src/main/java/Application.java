import Model.DocType;
import Model.Item;
import Model.ResultEntity;
import com.alibaba.fastjson.JSON;
import com.sun.istack.internal.NotNull;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by 王渝 on 2016-10-08.
 * Email : wwangyuu@outlook.com
 * University : University of Electronic Science and Technology of Zhangjiang
 */
public class Application {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        Route route = router.route(HttpMethod.GET, "/api/query/");

        //耗时操作
        route.blockingHandler(routingContext -> {
            String language = routingContext.request().getParam("language");
            String keyword = routingContext.request().getParam("keyword");
            //System.out.println(language);
            //System.out.println(keyword);

            //耗时操作
            ResultEntity entity = Query(language, keyword);
            String JSON = Restify(entity);

            routingContext.response()
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(JSON);
        });
        server.requestHandler(router::accept).listen(80);
    }

    private static ResultEntity Query(String lang, String key) {
        String u = "http://stackoverflow.com/search";
        String url = "http://stackoverflow.com/search?q=afas";
        try {
            String html = GET(url);
            //System.out.println(html);
            ResultEntity re = Extract_StackOverFlow(html);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayList<Item> is = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            is.add(new Item(DocType.Github, "sadf", "sadfsaf","asf"));
        }
        return new ResultEntity(is);
    }

    private static String GET(String url) throws IOException {
        return Request.Get(url)
                .execute().returnContent().asString();
    }

    private String GET(String url, @NotNull Map<String, String> params) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        if (params.size() < 1) {
            return null;
        }
        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
        Map.Entry<String, String> v = it.next();
        sb.append(v.getKey()).append("=").append(v.getValue());
        while (it.hasNext()) {
            sb.append("&");
            Map.Entry<String, String> vs = it.next();
            sb.append(v.getKey()).append("=").append(v.getValue());
        }
        return Request.Get(url + sb.toString())
                .execute().returnContent().asString();
    }

    private static ResultEntity Extract_StackOverFlow(String html) {
        Document doc = Jsoup.parse(html);
        Elements search_results = doc.getElementsByClass("question-summary");
        for(Element ele : search_results){
            Element summary = ele.getElementsByClass("summary").first();
            Element result_link = summary.getElementsByClass("result-link").first();
            Element excerpt = summary.getElementsByClass("excerpt").first();

            Element link = result_link.select("a").first();
            String href = link.attr("href");
            String title = link.text();
            String summary_text = excerpt.text();
            String summary_html = excerpt.html();

            System.out.println(href);
            System.out.println(title);
            System.out.println(summary_text);
            System.out.println(summary_html);
            System.out.println("---------------------------");
        }
        return null;
    }

    private static String POSTForm(String url, Map<String, String> values) throws IOException {
        Form form = Form.form();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            form.add(entry.getKey(), entry.getValue());
        }
        return Request.Post(url)
                .bodyForm(form.build())
                .execute().returnContent().asString();
    }

    private static String Restify(ResultEntity en) {
        return JSON.toJSONString(en);
    }

}
