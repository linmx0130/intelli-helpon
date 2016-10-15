package Service;

import Model.DocType;
import Model.Item;
import Model.ResultEntity;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 王渝 on 2016-10-15.
 * Email : wwangyuu@outlook.com
 * University : University of Electronic Science and Technology of Zhangjiang
 */
public class GitHub implements ILanguageService,IService {

    private static final String GITHUBSEARCH = "https://api.github.com/search/code";
    private static final String GITHUB = "https://github.com";
    @Override
    public ResultEntity getResult(String keyword, String language) throws IOException {
        ResultEntity en = new ResultEntity();
        //String json = NetWorking.GET(GITHUBSEARCH+"?q="+keyword+"+language:"+
         //       language.toLowerCase(),"Accept","application/vnd.github.v3.text-match+json");
        String json = NetWorking.GET_GITHUB(GITHUBSEARCH+"?q="+keyword+"+language:"+
                language.toLowerCase());
        System.out.print(json);
        Extract(en,json);
        return en;
    }

    private void Extract(ResultEntity en, String json) {
        gitapientity entity = JSON.parseObject(json,gitapientity.class);
        for (gitapientityitem i : entity.items){
            Item ii = new Item(DocType.Github,i.repository.full_name,i.path,i.html_url);
            en.addItem(ii);
        }
    }

    @Override
    public ResultEntity getResult(String keyword) throws IOException {
        ResultEntity en = new ResultEntity();
        //String json = NetWorking.GET(GITHUBSEARCH+"?q="+keyword+"+language:"+
        //       language.toLowerCase(),"Accept","application/vnd.github.v3.text-match+json");
        String json = NetWorking.GET_GITHUB(GITHUBSEARCH+"?q="+keyword);
        System.out.print(json);
        Extract(en,json);
        return en;
    }
}


class gitapientity{
    int total_count;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<gitapientityitem> getItems() {
        return items;
    }

    public void setItems(List<gitapientityitem> items) {
        this.items = items;
    }

    boolean incomplete_results;
    List<gitapientityitem> items;
}

class gitapientityitem{
    String name;
    String path;
    String sha;
    String url;
    String git_url;
    String html_url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGit_url() {
        return git_url;
    }

    public void setGit_url(String git_url) {
        this.git_url = git_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public gitapientityrepo getRepository() {
        return repository;
    }

    public void setRepository(gitapientityrepo repository) {
        this.repository = repository;
    }

    gitapientityrepo repository;
}

class gitapientityrepo{
    int id;
    String name;
    String full_name;
    String html_url;
    String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}