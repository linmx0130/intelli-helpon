import Model.DocType;
import Model.Item;
import Model.Language;
import Model.ResultEntity;
import Service.*;
import com.alibaba.fastjson.JSON;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

import java.io.IOException;


/**
 * Created by 王渝 on 2016-10-08.
 * Email : wwangyuu@outlook.com
 * University : University of Electronic Science and Technology of Zhangjiang
 */
public class Application {
    public static int PORT = 8080;

    public static void main(String[] args) {
        NetWorking.initGitHub();

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
            ResultEntity entity = Query(language, keyword);
            String JSON = Restify(entity);

            routingContext.response()
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(JSON);
        });
        server.requestHandler(router::accept).listen(PORT, r -> {
            System.out.println("Server started on port " + PORT);
        });
    }

    private static ResultEntity Query(String lang, String key) {
        ResultEntity r = new ResultEntity();
        Language language = Language.valueOf(lang);
        switch (language) {
            case C:
                //TODO C
                StackOverFlow stackOverFlowC = new StackOverFlow();
                SourceCode_C sourceCode_c = new SourceCode_C();
                OfficialDocs_C officialDocs_c = new OfficialDocs_C();
                break;
            case CPP:
                //TODO CPP
                break;
            case Java:
                //TODO Parallel
                StackOverFlow stackOverFlowJava = new StackOverFlow();
                SourceCode_Java sourceCode_java = new SourceCode_Java();
                OfficialDocs_Java officialDocs_java = new OfficialDocs_Java();
                GitHub gitHub = new GitHub();

                try {
                    r.Append(stackOverFlowJava.getResult(key, lang));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                r.Append(sourceCode_java.getResult(key));
                try {
                    r.Append(officialDocs_java.getResult(key));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    r.Append(gitHub.getResult(key, lang));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case JavaScript:
                StackOverFlow stackOverFlowJavaScript = new StackOverFlow();
                OfficialDocs_JavaScript officialDocs_javaScript = new OfficialDocs_JavaScript();
                GitHub gitHubJavaScript = new GitHub();

                try {
                    r.Append(stackOverFlowJavaScript.getResult(key, lang));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    r.Append(gitHubJavaScript.getResult(key, lang));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    r.Append(officialDocs_javaScript.getResult(key));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case UnSpecific:
                //Only provide information for stack and github
                StackOverFlow stackOverFlowUn = new StackOverFlow();
                GitHub gitHubUn = new GitHub();
                try {
                    r.Append(gitHubUn.getResult(key));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    r.Append(stackOverFlowUn.getResult(key));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                //TODO Default
                break;
        }
        return r;
    }


    private static String Restify(ResultEntity en) {
        return JSON.toJSONString(en);
    }

}
