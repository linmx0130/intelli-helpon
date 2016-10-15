package Service;

import Model.ResultEntity;

/**
 * Created by Mengxiao Lin on 2016-10-15.
 * Email : linmx0130@163.com
 */
public class OfficialDocs_CPP implements IService {
    private static final String CPP_DOCS =
        "http://en.cppreference.com/mwiki/index.php?search=";
    @Override
    public ResultEntity getResult(String keyword) {
        ResultEntity en = new ResultEntity();
        String html = NetWorking.GET(CPP_DOCS + keyword +String_DOCS2);
        Document doc = Jsoup.parse(html);
        Elements resultObject = doc.getElementsByClass("mw-search-results").first();
        if (resultObject == null){
            return en;
        }
        Elements search_results = resultObject.children();
        for (Element ele : search_results) {
            Element result_link = ele.getElementsByTag("a").first();
            String href = link.attr("href");
            String title = link.text();
            String summary_text = title;
            String summary_html = title;
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
