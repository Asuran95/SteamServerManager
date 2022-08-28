
package steamservermanager.serverrunner.interfaces;

public interface ServerMessageDispatcher {
    
	void send(String command);
    
    void stop();
}
