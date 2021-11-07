package steamservermanager;

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

import com.pty4j.PtyProcess;

import steamservermanager.interfaces.serverrunner.ServerGameMessageReceiver;
import steamservermanager.interfaces.serverrunner.ServerMessageDispatcher;
import steamservermanager.interfaces.serverrunner.ServerProperties;
import steamservermanager.interfaces.serverrunner.ServerRunnerListener;
import steamservermanager.models.ServerGame;

class ServerRunner extends Thread {

    private final ServerGame serverGame;
    private final String localDir;
    private final ServerRunnerListener listener;

    private PtyProcess pty;
    private ServerGameMessageReceiver listenerStdOut;
    private boolean running = false;

    public ServerRunner(ServerGame serverGame, String localDir, ServerRunnerListener listener) {
        this.serverGame = serverGame;
        this.localDir = localDir;
        this.listener = listener;
    }

    @SuppressWarnings("deprecation")
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
            this.pty = PtyProcess.exec(commandBuffer.toArray(new String[0]));

            InputStream stdout = pty.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stdout), 1);
            
            listener.onServerStart(serverGame);
            serverGame.setStatus(Status.RUNNING);
            
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
            listener.onServerException(serverGame);
        } finally {
            forceStop();
        }
    }
    
    public void forceStop(){
        pty.destroyForcibly();
            
        serverGame.setStatus(Status.STOPPED);
        listener.onServerStopped(serverGame);
    }

    public boolean isRunning() {
        return running;
    }
    
    public ServerProperties getServerProperties() {

        return new ServerProperties() {
            @Override
            public ServerMessageDispatcher setListener(ServerGameMessageReceiver listenerImpl) {
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
                OutputStream stdin = pty.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

                System.out.println(command);

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
