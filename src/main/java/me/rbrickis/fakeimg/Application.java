package me.rbrickis.fakeimg;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import me.rbrickis.fakeimg.lib.FakeImg;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Application {

    // The entry point to the application
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.route("/image/*").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.putHeader("Content-Type", "image/png");
            routingContext.next();
        });

        router.route("/image/").pathRegex("/image/([0-9]{1,3})x([0-9]{1,3})/([0-9a-fA-F]{6})/([0-9a-fA-F]{6})/(.*)/(.*)").handler(routingContext -> {
            int width = Integer.parseInt(routingContext.request().getParam("param0"));
            int height = Integer.parseInt(routingContext.request().getParam("param1"));
            String bgColor = "#" + routingContext.request().getParam("param2");
            String textColor = "#" + routingContext.request().getParam("param3");
            String font = routingContext.request().getParam("param4");
            String text = routingContext.request().getParam("param5");

            FakeImg img = new FakeImg(text, width, height, bgColor, textColor, new Font(font, Font.PLAIN, Math.min(width, height) / 4));
            BufferedImage image = img.renderImage();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            try {
                ImageIO.write(image, "png", baos);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Buffer buffer = Buffer.buffer().appendBytes(baos.toByteArray());
            routingContext.response().end(buffer);
        });

        server.requestHandler(router::accept).listen(8080);
    }

}
