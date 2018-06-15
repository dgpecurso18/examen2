package mx.unam.dgpe.sample.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class MyController extends AbstractVerticle {
    private static final Logger logger = Logger.getLogger(MyController.class);
    
    public void start(Future<Void> fut) {
        logger.info("Inicializando Vertical");
        Router router = Router.router(vertx);
        //router.route("/*").handler(StaticHandler.create("assets")); // para invocar asi: http://localhost:8080/index.html
        // el directorio "upload-folder" será creado en la misma ubicación que el jar fue ejecutado
        router.route().handler(BodyHandler.create().setUploadsDirectory("upload-folder"));
       router.get("/api/cuantos").handler(this::cuantos); 
        // Create the HTTP server and pass the "accept" method to the request handler.
        vertx.createHttpServer().requestHandler(router::accept).listen(
                config().getInteger("http.port", 8080), result -> {
                    if (result.succeeded()) {
                        fut.complete();
                    } else {
                        fut.fail(result.cause());
                    }
                });        
        
        logger.info("Vertical iniciada !!!");
    }  

    private void cuantos(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        HttpServerRequest request = routingContext.request();
        String num = request.getParam("num");
        String jsonResponse = procesa(String.valueOf(cantidad(Integer.valueOf(num))));
        response.setStatusCode(200).
        putHeader("content-type", "application/json; charset=utf-8").
        end(jsonResponse);
    }

    private String procesa(String decoded) {
        Map<Object, Object> info = new HashMap<>();
        info.put("Resultado", decoded);
        return Json.encodePrettily(info);
    }

    public static Integer cantidad(int num) {
        long X=1;
        X=factorial(num);
        String  len= Long.toString(X);
        return len.length();
     }

     private static Long factorial (int numero) {
       if (numero == 0) return Long.valueOf(1);
        else return Long.valueOf(numero * factorial(numero-1));
     } 

}
