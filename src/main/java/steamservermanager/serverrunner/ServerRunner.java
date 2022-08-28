package steamservermanager.serverrunner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pty4j.PtyProcessBuilder;
import com.sun.jna.Platform;

import steamservermanager.listeners.ServerGameConsoleListener;
import steamservermanager.models.ServerGame;
import steamservermanager.serverrunner.interfaces.ServerMessageDispatcher;
import steamservermanager.serverrunner.interfaces.ServerProperties;
import steamservermanager.serverrunner.listeners.ServerRunnerListener;
import steamservermanager.utils.Status;

public class ServerRunner extends Thread {

    private final ServerGame serverGame;
    private final String localDir;
    private final ServerRunnerListener listener;

    private Process process;
    private ServerGameConsoleListener listenerStdOut;
    private boolean running = false;

    public ServerRunner(ServerGame serverGame, String localDir, ServerRunnerListener listener) {
        this.serverGame = serverGame;
        this.localDir = localDir;
        this.listener = listener;
    }

    @Override
    public void run() {
        
        running = true;
        
        List<String> commandBuffer = new ArrayList<>();

        String[] scriptSplit = serverGame.getStartScript().split(" ");

        String pathServer = localDir + File.separator + serverGame.getServerName() + File.separator + scriptSplit[0];

        commandBuffer.add(pathServer);

        for (int i = 1; i < scriptSplit.length; i++) {
            commandBuffer.add(scriptSplit[i]);
        }

        try {
        	
        	if (Platform.isLinux()) {
        		PtyProcessBuilder builder = new PtyProcessBuilder(commandBuffer.toArray(new String[0]));
        		
        		this.process = builder.start();
        		
        	} else if (Platform.isWindows()) {
        		PtyProcessBuilder builder = new PtyProcessBuilder(commandBuffer.toArray(new String[0]))
        		        .setConsole(false)
        		        .setUseWinConPty(true);

            	this.process = builder.start();
        	}
        	
            InputStream stdout = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stdout), 1);
            
            serverGame.setStatus(Status.RUNNING);
            listener.onServerStarted(serverGame);
            
            while (true) {
                String out = reader.readLine();

                if (out == null) {
                    break;
                }
                
                if (listenerStdOut != null) {
                    listenerStdOut.onOutput(out);
                }
            }

            running = false;

        } catch (IOException e) {
            running = false;
            serverGame.setStatus(Status.STOPPED);
            listener.onServerException(serverGame);
        }
        
        serverGame.setStatus(Status.STOPPED);
        listener.onServerStopped(serverGame);
    }
    
    public void forceStop(){
        process.destroyForcibly();

        serverGame.setStatus(Status.STOPPED);
        listener.onServerStopped(serverGame);
    }

    public boolean isRunning() {
        return running;
    }
    
    public ServerProperties getServerProperties() {

        return new ServerProperties() {
            @Override
            public ServerMessageDispatcher setListener(ServerGameConsoleListener listenerImpl) {
                listenerStdOut = listenerImpl;
                return new StandardInputImpl();
            }

            @Override
            public ServerGame getServerGame() {
                return serverGame;
            }

            @Override
            public boolean isRunning() {
                return running;
            }
        };
    }

    class StandardInputImpl implements ServerMessageDispatcher {

        @Override
        public void send(String command) {
            try {
                OutputStream stdin = process.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

                writer.write(command + "\n");
                writer.flush();
                
            } catch (IOException ex) {
                Logger.getLogger(ServerRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

		@Override
		public void stop() {
			if(running) {
				forceStop();
			}
		}
    }

    public ServerGame getServerGame() {
        return serverGame;
    }
}
