package eu.mojo.graphqlcard.container;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eu.mojo.graphqlcard.container.handlers.AppRequestHandler;
import eu.mojo.graphqlcard.container.handlers.NotFoundStaticHandler;
import eu.mojo.graphqlcard.container.handlers.ResourceRequestHandler;
import eu.mojo.graphqlcard.core.App;
import eu.mojo.graphqlcard.core.AppInitializer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GraphqlCardGameServlet implements Servlet {
    //region FIELDS
    private MainServletConfig config;
    private AppRequestHandler appHandler;
    private ResourceRequestHandler resourceHandler;
    private SessionHolder sessionHolder;
    //endregion



    //region LIFE CYCLE
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("init SERVLET");
        try {
            AppInitializer.start( System.getProperty("hostAppDir") );

            //we will read our the tomcan configuration json, to create our MainServletConfig object...
            Path configFile = App.getConfig().getHostAppDir().resolve("tomcatConfig.json");
            JsonObject job = new JsonParser()
                    .parse( Files.newBufferedReader(configFile))
                    .getAsJsonObject();
            MainServletConfig config = new MainServletConfig(job);


            //done. now initialize our fields.
            this.config = config;
            this.appHandler = new AppRequestHandler(config);
            this.resourceHandler = new ResourceRequestHandler(config);
            this.sessionHolder = new SessionHolder();
        }
        catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    public void destroy() {
        System.out.println("DESTROY SERVLET");
        try { AppInitializer.stop(); }
        catch (Exception e) { e.printStackTrace(); }
    }
    //endregion


    //region GETTERS - INFO
    @Override
    public ServletConfig getServletConfig() {
        System.out.println("getServletConfig SERVLET");
        return null;
    }

    @Override
    public String getServletInfo() {
        System.out.println("getServletInfo SERVLET");
        return null;
    }
    //endregion



    //region REQUEST HANDLING
    /**
     * Entry point for a resource. It just passes the request down a level 2 handler.*/
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest)req;
        HttpServletResponse httpRes = (HttpServletResponse)res;

        String path = httpReq.getServletPath();
        AppSession session = sessionHolder.get( httpReq.getSession().getId() );

        if (path.startsWith(config.getAppBasePath())) appHandler.handle(httpReq, httpRes, path, session);
        else if (path.startsWith(config.getResourceBasePath())) resourceHandler.handle(httpReq, httpRes, path, session);
        else NotFoundStaticHandler.handle(httpReq, httpRes, path, session);
    }
    //endregion
}
