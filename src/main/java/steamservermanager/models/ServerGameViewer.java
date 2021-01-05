
package steamservermanager.models;

import steamservermanager.Status;

/**
 *
 * @author asu
 */
public final class ServerGameViewer {

    private ServerGame serverGame;

    public ServerGameViewer(ServerGame serverGame) {
        this.serverGame = serverGame;
    }

    public String getId() {
        return serverGame.getId();
    }

    public int getGameId() {
        return serverGame.getGameId();
    }

    public String getServerName() {
        return serverGame.getServerName();
    }

    public String getStartScript() {
        return serverGame.getStartScript();
    }

    public void setStartScript(String startScript) {
        serverGame.setStartScript(startScript);
    }

    public String getGameName() {
        return serverGame.getGameName();
    }

    public Status getStatus() {
        return serverGame.getStatus();
    }
}
