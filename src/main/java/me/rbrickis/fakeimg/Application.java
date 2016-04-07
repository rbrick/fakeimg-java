package me.rbrickis.fakeimg;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import me.rbrickis.fakeimg.lib.FakeImg;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Application {

    // The entry point to the application
    public static void main(String[] args) throws Exception {

        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.route("/image/*").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.putHeader("Content-Type", "image/png");
            routingContext.next();
        });

        router.route("/image/").handler(routingContext -> {
            render(routingContext.response(), new FakeImg());
        });

        router.route("/image/").pathRegex("/image/([0-9]{1,3})x([0-9]{1,3})/(.*)").handler(routingContext -> {
            int width = Integer.parseInt(routingContext.request().getParam("param0"));
            int height = Integer.parseInt(routingContext.request().getParam("param1"));
            String text = routingContext.request().getParam("param2");
            render(routingContext.response(), new FakeImg(text, width, height));
        });

        router.route("/image/").pathRegex("/image/([0-9a-fA-F]{3,6})/([0-9]{1,3})x([0-9]{1,3})/(.*)").handler(routingContext -> {
            String bgColor = routingContext.request().getParam("param0");
            int width = Integer.parseInt(routingContext.request().getParam("param1"));
            int height = Integer.parseInt(routingContext.request().getParam("param2"));
            String text = routingContext.request().getParam("param3");
            render(routingContext.response(), new FakeImg(text, width, height, bgColor, "FFFFFF"));
        });

        router.route("/image/").pathRegex("/image/([0-9]{1,3})x([0-9]{1,3})/([0-9a-fA-F]{3,6})/([0-9a-fA-F]{3,6})/(.*)").handler(routingContext -> {
            int width = Integer.parseInt(routingContext.request().getParam("param0"));
            int height = Integer.parseInt(routingContext.request().getParam("param1"));
            String bgColor = routingContext.request().getParam("param2");
            String textColor = routingContext.request().getParam("param3");
            String text = routingContext.request().getParam("param4");
            render(routingContext.response(), new FakeImg(text, width, height, bgColor, textColor));
        });

        server.requestHandler(router::accept).listen(8080);
    }

    /**
     * Transforms a {@link java.awt.image.BufferedImage} into a {@link io.vertx.core.buffer.Buffer}
     * that can be used by Vert.x
     *
     * @return A buffer containing the provided {@code java.awt.image.BufferedImage image} bytes
     */
    private static Buffer imageToBuffer(BufferedImage image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
        } catch (IOException exception) {
            // Ignored
        }
        return Buffer.buffer().appendBytes(baos.toByteArray());
    }

    private static void render(HttpServerResponse response, FakeImg img) {
        response.end(imageToBuffer(img.renderImage()));
    }
}
