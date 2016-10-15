package Handler;

import Service.TemplateEngineService;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by 王渝 on 2016-10-15.
 * Email : wwangyuu@outlook.com
 * University : University of Electronic Science and Technology of Zhangjiang
 */
public class StaticPageHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext event) {
        TemplateEngineService.engine.render(event, "/templates/index.ftl", (res) -> {
            if(res.succeeded()) {
                event.response().putHeader("Connection", "keep-alive");
                event.response().putHeader("Content-Type", "text/html;charset=UTF-8");
                event.response().putHeader("Server", "Vert.X");
                event.response().end(res.result());
            } else {
                event.fail(res.cause());
            }
        });
    }
}
