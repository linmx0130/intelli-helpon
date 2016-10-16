package Service;

import Model.DocType;
import Model.Item;
import Model.ResultEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Mengxiao Lin on 2016-10-15.
 * Email : linmx0130@163.com
 */
public class OfficialDocs_CPP implements IService {
    private static final String CPP_DOCS =
            "http://en.cppreference.com/mwiki/index.php?search=";

    @Override
    public ResultEntity getResult(String keyword) throws IOException {
        ResultEntity en = new ResultEntity();
        String html;
        html = NetWorking.GET(CPP_DOCS + keyword);

        Document doc = Jsoup.parse(html);
        Element resultObject = doc.getElementsByClass("mw-search-results").first();
        if (resultObject == null) {
            return en;
        }
        Elements search_results = resultObject.children();
        for (Element ele : search_results) {
            Element result_link = ele.getElementsByTag("a").first();
            String href = result_link.attr("href");
            String title = result_link.text();
            String summary_text = title;
            String summary_html = title;


//            System.out.println(href);
//            System.out.println(title);
//            System.out.println(summary_text);
//            System.out.println(summary_html);
//            System.out.println("---------------------------");


            Item i = new Item(DocType.OfficialDocs, title, summary_text, href);
            en.addItem(i);
        }
        return en;
    }
}
