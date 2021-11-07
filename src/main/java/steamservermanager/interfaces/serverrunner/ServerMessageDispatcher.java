
package steamservermanager.interfaces.serverrunner;

public interface ServerMessageDispatcher {
    void send(String command);
    void stop();
}
