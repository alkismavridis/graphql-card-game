package eu.mojo.graphqlcard.container.handlers;

import eu.mojo.graphqlcard.container.AppSession;
import eu.mojo.graphqlcard.core.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NotFoundStaticHandler {
    public static void handle(HttpServletRequest req, HttpServletResponse res, String url, AppSession session) throws IOException {
        res.setContentType("text/plain");
        res.setStatus(404);
        res.getWriter().append("Page not found:" + url);
        res.flushBuffer();
    }

    public static void makeJson(HttpServletRequest req, HttpServletResponse res, User user) throws IOException {
        res.setContentType("application/json");
        res.setStatus(404);
        res.getWriter().append("{\"message\":\"Page not found\"}");
        res.flushBuffer();
    }
}
