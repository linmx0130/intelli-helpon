import Model.DocType;
import Model.Item;
import Model.Language;
import Model.ResultEntity;
import Service.GitHub;
import Service.OfficialDocs_Java;
import Service.SourceCode_Java;
import Service.StackOverFlow;
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
        server.requestHandler(router::accept).listen(8080);
    }

    private static ResultEntity Query(String lang, String key) {
        ResultEntity r = new ResultEntity();
        Language language = Language.valueOf(lang);
        switch (language) {
            case C:
                //TODO C
                break;
            case CPP:
                //TODO CPP
                break;
            case Java:
                //TODO Parallel
                StackOverFlow stackOverFlow = new StackOverFlow();
                SourceCode_Java sourceCode_java = new SourceCode_Java();
                OfficialDocs_Java officialDocs_java = new OfficialDocs_Java();
                GitHub gitHub = new GitHub();
                try {
                    r.Append(stackOverFlow.getResult(key,lang));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                r.Append(sourceCode_java.getResult(key));
                try {
                    r.Append(officialDocs_java.getResult(key));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                r.Append(gitHub.getResult(key));
                break;
            case JavaScript:
                //TODO JavaScript
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
