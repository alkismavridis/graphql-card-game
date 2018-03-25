package eu.mojo.graphqlcard.container.handlers;

import eu.mojo.graphqlcard.container.AppSession;
import eu.mojo.graphqlcard.container.MainServletConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourceRequestHandler {

    //region FIELDS
    private MainServletConfig config;
    //endregion


    //region CONSTRUCT
    public ResourceRequestHandler(MainServletConfig config) {
        this.config = config;
    }
    //endregion


    /**
     * Level 2 handler.
     * Handles all 404 cases. No matter if they are an html, or a Json, or a JS file.
     * */
    public void handle(HttpServletRequest req, HttpServletResponse res, String path, AppSession session) throws IOException {
        res.setContentType("text/plain");
        res.getWriter().append("resource asked:" + path);
        res.flushBuffer();
    }
}
