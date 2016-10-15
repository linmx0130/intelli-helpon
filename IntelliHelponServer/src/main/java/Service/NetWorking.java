package Service;

import Model.ResultEntity;
import com.sun.istack.internal.NotNull;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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

    public static String GET(String url, String head_key, String head_value) throws IOException {
        return Request.Get(url).addHeader(head_key, head_value)
                .execute().returnContent().asString();
    }

    private static CloseableHttpClient clientforGitHub;

    public static void initGitHub() {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope("api.github.com", 443, "realm"),
                new UsernamePasswordCredentials("linmx0130", "e1d0695fa360b83a476e1c3598b4ca2fc0d9e61b"));
        clientforGitHub = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
    }

    public static String GET_GITHUB(String url) throws IOException {
        if (clientforGitHub==null){
            throw new NullPointerException("Client must be inited before using");
        }
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials("linmx0130", "e1d0695fa360b83a476e1c3598b4ca2fc0d9e61b");
        HttpUriRequest request = new HttpGet(url);
        try {
            request.addHeader(new BasicScheme().authenticate(creds, request));
        } catch (AuthenticationException e) {
            return "";
        }
        CloseableHttpResponse response = clientforGitHub.execute(request);
        return EntityUtils.toString(response.getEntity());
    }


    public static String GET(String url, @NotNull Map<String, String> params) throws IOException {
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
