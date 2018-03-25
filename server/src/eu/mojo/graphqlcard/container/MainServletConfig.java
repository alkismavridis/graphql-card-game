package eu.mojo.graphqlcard.container;

import com.google.gson.JsonObject;
import eu.mojo.graphqlcard.util.JsonUtils;

public class MainServletConfig {
    //region FIELDS
    private String resourceBasePath;

    private String appBasePath;
    private String appEventPath;
    private String gameEventPath;
    //endregion


    //region CONSTRUCT
    MainServletConfig(JsonObject job) {
        this.resourceBasePath = JsonUtils.optString(job, "resourceURL", "/resource");

        this.appBasePath = JsonUtils.optString(job, "appURL", "/app");
        this.appEventPath = this.appBasePath;
        this.gameEventPath = this.appBasePath + JsonUtils.optString(job, "gameEventPath", "/game");
    }
    //endregion


    //region GETTERS
    public String getResourceBasePath() {
        return resourceBasePath;
    }

    public String getAppBasePath() {
        return appBasePath;
    }

    public String getAppEventPath() {
        return appEventPath;
    }

    public String getGameEventPath() {
        return gameEventPath;
    }
    //endregion
}
