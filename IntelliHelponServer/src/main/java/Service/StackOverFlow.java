package Service;

import Model.DocType;
import Model.Item;
import Model.ResultEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 王渝 on 2016-10-15.
 * Email : wwangyuu@outlook.com
 * University : University of Electronic Science and Technology of Zhangjiang
 */
public class StackOverFlow implements ILanguageService, IService {

    private static final String STACKOVERFLOW = "http://stackoverflow.com";

    @Override
    public ResultEntity getResult(String keyword, String language) throws IOException {
        Map<String, Item> temp = new HashMap<>();
        TagSearch(keyword, language, temp);
        PlainSearch(keyword, temp);
        return ConvertToResult(temp);
    }

    private ResultEntity ConvertToResult(Map<String, Item> map) {
        ResultEntity en = new ResultEntity();
        en.Append(map.values());
        return en;
    }

    private void TagSearch(String keyword, String language, Map<String, Item> en) throws IOException {
        String request = URLEncoder.encode(keyword + "+[" + language + "]", "utf8");
        String html = NetWorking.GET(STACKOVERFLOW + "/search?q=" + request);
        Extract(en, html);
    }

    private void PlainSearch(String keyword, Map<String, Item> en) throws IOException {
        String html = NetWorking.GET(STACKOVERFLOW + "/search?q=" + keyword);
        Extract(en, html);
    }

    private void Extract(Map<String, Item> en, String html) {
        Document doc = Jsoup.parse(html);
        Elements search_results = doc.getElementsByClass("question-summary");
        for (Element ele : search_results) {
            Element summary = ele.getElementsByClass("summary").first();
            Element result_link = summary.getElementsByClass("result-link").first();
            Element excerpt = summary.getElementsByClass("excerpt").first();

            Element link = result_link.select("a").first();
            String href = link.attr("href");
            String title = link.text();
            String summary_text = excerpt.text();
            String summary_html = excerpt.html();

//            System.out.println("begin---------------------------");
//            System.out.println(href);
//            System.out.println(title);
//            System.out.println(summary_text);
//            System.out.println(summary_html);
//            System.out.println("end-----------------------------");

            Item i = new Item(DocType.StackOverFlow, title, summary_text, STACKOVERFLOW + href);

            if (!en.containsKey(i.getLink())) {
                en.put(i.getLink(), i);
            }
        }
    }

    @Override
    public ResultEntity getResult(String keyword) throws IOException {
        Map<String, Item> temp = new HashMap<>();
        PlainSearch(keyword, temp);
        return ConvertToResult(temp);
    }
}
