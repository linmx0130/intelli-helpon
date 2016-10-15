import Model.Language;
import Model.ResultEntity;
import Service.*;
import Handler.StaticPageHandler;
import com.alibaba.fastjson.JSON;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;


import java.io.IOException;
import java.util.concurrent.CountDownLatch;


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

        TemplateEngineService.engine = FreeMarkerTemplateEngine.create();

        Router router = Router.router(vertx);
        Route route = router.route(HttpMethod.GET, "/api/query/");

        //耗时操作
        route.blockingHandler(routingContext -> {


            String language = routingContext.request().getParam("language");
            String keyword = routingContext.request().getParam("keyword");
            //System.out.println(language);
            //System.out.println(keyword);

            startTime = System.currentTimeMillis();
            System.out.println("-------------[Query Begin]-------------");
            ResultEntity entity = Query(language, keyword);
            System.out.println("--------------[Query End]--------------");
            System.out.println("Time Total       " + (System.currentTimeMillis() - startTime) + "ms");

            String JSON = Restify(entity);

            routingContext.response()
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(JSON);
        });

        StaticHandler staticHandler = StaticHandler.create("static").setFilesReadOnly(true).setCachingEnabled(true);
        router.get("/static/*").handler(staticHandler);
        router.route("/presenter/").handler(new StaticPageHandler());
        router.route("/").handler(routingContext -> {
            routingContext
                    .response()
                    .putHeader("content-type", "text/plain")
                    .end("无可奉告");
        });

        server.requestHandler(router::accept).listen(PORT, r -> {
            System.out.println("Server started on port " + PORT);
        });
    }

    private static long currentTime;
    private static long startTime;

    private static ResultEntity Query(String lang, String key) {

        ResultEntity r = new ResultEntity();
        Language language = Language.valueOf(lang);
        System.out.println("Language:" + lang);
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
//                CountDownLatch latch = new CountDownLatch(3);
//                try {
//                    latch.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                StackOverFlow stackOverFlowJava = new StackOverFlow();
                SourceCode_Java sourceCode_java = new SourceCode_Java();
                OfficialDocs_Java officialDocs_java = new OfficialDocs_Java();
                GitHub gitHub = new GitHub();

                try {
                    System.out.println("[StackOverFlow]  begin");
                    currentTime = System.currentTimeMillis();
                    r.Append(stackOverFlowJava.getResult(key, lang));
                    long now = System.currentTimeMillis();
                    System.out.println("[StackOverFlow]  end                 " + (now - currentTime) + "ms");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                r.Append(sourceCode_java.getResult(key));
                try {
                    System.out.println("[OfficialDocs]  begin");
                    currentTime = System.currentTimeMillis();
                    r.Append(officialDocs_java.getResult(key));
                    long now = System.currentTimeMillis();
                    System.out.println("[OfficialDocs]  end                 " + (now - currentTime) + "ms");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("[GitHub]  begin");
                    currentTime = System.currentTimeMillis();
                    r.Append(gitHub.getResult(key, lang));
                    long now = System.currentTimeMillis();
                    System.out.println("[GitHub]  end                 " + (now - currentTime) + "ms");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case JavaScript:
                StackOverFlow stackOverFlowJavaScript = new StackOverFlow();
                OfficialDocs_JavaScript officialDocs_javaScript = new OfficialDocs_JavaScript();
                GitHub gitHubJavaScript = new GitHub();

                try {
                    System.out.println("[StackOverFlow]  begin");
                    currentTime = System.currentTimeMillis();
                    r.Append(stackOverFlowJavaScript.getResult(key, lang));
                    long now = System.currentTimeMillis();
                    System.out.println("[StackOverFlow]  end                 " + (now - currentTime) + "ms");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("[OfficialDocs]  begin");
                    currentTime = System.currentTimeMillis();
                    r.Append(officialDocs_javaScript.getResult(key));
                    long now = System.currentTimeMillis();
                    System.out.println("[OfficialDocs]  end                 " + (now - currentTime) + "ms");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("[GitHub]  begin");
                    currentTime = System.currentTimeMillis();
                    r.Append(gitHubJavaScript.getResult(key, lang));
                    long now = System.currentTimeMillis();
                    System.out.println("[GitHub]  end                 " + (now - currentTime) + "ms");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("[OfficialDocs]  begin");
                    currentTime = System.currentTimeMillis();
                    r.Append(officialDocs_javaScript.getResult(key));
                    long now = System.currentTimeMillis();
                    System.out.println("[OfficialDocs]  end                 " + (now - currentTime) + "ms");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case UnSpecific:
                //Only provide information for stack and github
                StackOverFlow stackOverFlowUn = new StackOverFlow();
                GitHub gitHubUn = new GitHub();
                try {
                    System.out.println("[StackOverFlow]  begin");
                    currentTime = System.currentTimeMillis();
                    r.Append(gitHubUn.getResult(key));
                    long now = System.currentTimeMillis();
                    System.out.println("[StackOverFlow]  end                 " + (now - currentTime) + "ms");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("[StackOverFlow]  begin");
                    currentTime = System.currentTimeMillis();
                    r.Append(stackOverFlowUn.getResult(key));
                    long now = System.currentTimeMillis();
                    System.out.println("[StackOverFlow]  end                 " + (now - currentTime) + "ms");
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
