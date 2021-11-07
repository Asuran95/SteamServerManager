package steamservermanager.models;

import java.io.Serializable;
import java.util.UUID;
import steamservermanager.Status;

public class ServerGame implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id = UUID.randomUUID().toString();
    private int appID;
    private final String serverName;
    private String startScript;

    private String gameName;
    private Status status;

    public ServerGame(int appID, String serverName, String startScript) {
        this.appID = appID;
        this.serverName = serverName;
        this.startScript = startScript;
    }

    public String getId() {
        return id;
    }

    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public String getServerName() {
        return serverName;
    }

    public String getStartScript() {
        return startScript;
    }

    public void setStartScript(String startScript) {
        this.startScript = startScript;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
