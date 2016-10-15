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
public class OfficialDocs_JavaScript implements IService {
    private static final String MDN_SEARCH =
            "https://developer.mozilla.org/en-US/search?topic=js&topic=api&topic=webdev&q=";
    @Override
    public ResultEntity getResult(String keyword) throws IOException {
        ResultEntity en = new ResultEntity();
        String html = NetWorking.GET(MDN_SEARCH + keyword);
        Document doc = Jsoup.parse(html);
        Elements search_results = doc.getElementsByClass("result-list").first().children();
        for (Element ele : search_results){
            Element resultItem = ele.getElementsByClass("result-list-item").first();
            Element resultLink = ele.getElementsByTag("h4").first().getElementsByTag("a").first();
            Element summary = ele.getElementsByTag("p").first();

            String title = resultLink.text();
            String link = resultLink.attr("href");
            String summaryText = summary.text();
            String summaryHtml = summary.html();

//            System.out.println("begin---------------------------");
//            System.out.println(link);
//            System.out.println(title);
//            System.out.println(summaryText);
//            System.out.println(summaryHtml);
//            System.out.println("end-----------------------------");

            Item i = new Item(DocType.OfficialDocs, title, summaryText, link);
            en.addItem(i);
        }
        return en;
    }
}
