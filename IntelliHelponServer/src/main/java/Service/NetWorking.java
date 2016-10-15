package Service;

import Model.ResultEntity;
import com.sun.istack.internal.NotNull;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by 王渝 on 2016-10-15.
 * Email : wwangyuu@outlook.com
 * University : University of Electronic Science and Technology of Zhangjiang
 */
public class NetWorking {
    public static String GET(String url) throws IOException {
        return Request.Get(url)
                .execute().returnContent().asString();
    }

    public String GET(String url, @NotNull Map<String, String> params) throws IOException {
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

    public static String POSTForm(String url, Map<String, String> values) throws IOException {
        Form form = Form.form();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            form.add(entry.getKey(), entry.getValue());
        }
        return Request.Post(url)
                .bodyForm(form.build())
                .execute().returnContent().asString();
    }
}
