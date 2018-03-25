package eu.mojo.graphqlcard.container.handlers;

import eu.mojo.graphqlcard.container.AppSession;
import eu.mojo.graphqlcard.container.MainServletConfig;
import eu.mojo.graphqlcard.core.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AppRequestHandler {
    //region FIELDS
    private MainServletConfig config;
    //endregion


    //region CONSTRUCT
    public AppRequestHandler(MainServletConfig config) {
        this.config = config;
    }
    //endregion


    //region HANDLERS
    public void handle(HttpServletRequest req, HttpServletResponse res, String path, AppSession session) throws IOException {
        User user = session==null? null : session.getUser();

        if (path.equals(config.getAppBasePath())) handleAppEvent(req, res, user);
        else if (path.equals(config.getGameEventPath())) handleGame(req, res, user);
        else NotFoundStaticHandler.makeJson(req, res, user);
    }

    private void handleAppEvent(HttpServletRequest req, HttpServletResponse res, User user) throws IOException {
        res.setContentType("application/json");
        res.getWriter().append("{\"hello\":\"world\", \"path\":\"App - Event\"}");
        res.flushBuffer();
    }

    private void handleGame(HttpServletRequest req, HttpServletResponse res, User user) throws IOException {
        res.setContentType("application/json");
        res.getWriter().append("{\"hello\":\"world\", \"path\":\"Game - Event\"}");
        res.flushBuffer();
    }
    //endregion
}
