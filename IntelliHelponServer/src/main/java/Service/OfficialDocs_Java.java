package Service;

import Model.ResultEntity;
import Model.DocType;
import Model.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
/**
 * Created by Mengxiao Lin on 2016-10-15.
 * Email : linmx0130@163.com
 */
public class OfficialDocs_Java implements IService {
    private static final String JAVA_DOCS =
        "http://docs.oracle.com/apps/search/search.jsp?category=java&q=";
    @Override
    public ResultEntity getResult(String keyword) {
        ResultEntity en = new ResultEntity();
        String html = NetWorking.GET(JAVA_DOCS + keyword);
        Document doc = Jsoup.parse(html);
        Elements search_results = doc.getElementsByClass("srch-result");
        for (Element ele : search_results) {
            Element summary = summary.getElementsByTag("p").first();
            Element result_link = summary.getElementsByClass("topictitle").first();

            Element link = result_link.select("a").first();
            String href = link.attr("href");
            String title = link.text();
            String summary_text = summary.text();
            String summary_html = summary.html();

            System.out.println(href);
            System.out.println(title);
            System.out.println(summary_text);
            System.out.println(summary_html);
            System.out.println("---------------------------");

            Item i = new Item(DocType.OfficialDocs, title, summary_text, href);
            en.addItem(i);
        }
        return en;
    }
}
