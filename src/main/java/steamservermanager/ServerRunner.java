package steamservermanager;

import steamservermanager.models.ServerGame;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.pty4j.PtyProcess;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import steamservermanager.interfaces.StandardInputInterface;
import steamservermanager.interfaces.StandardOutputInterface;
import steamservermanager.interfaces.ServerProperties;
import steamservermanager.interfaces.ServerRunnerListener;

class ServerRunner extends Thread {

    private ServerGame serverGame;
    private String localDir;

    private PtyProcess pty;
    private StandardOutputInterface listenerStdOut;
    private boolean running = true;
    
    private ServerRunnerListener listener;

    public ServerRunner(ServerGame serverGame, String localDir, ServerRunnerListener listener) {
        this.serverGame = serverGame;
        this.localDir = localDir;
        this.listener = listener;
    }

    @Override
    public void run() {

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

                System.out.println(out);

                if (listenerStdOut != null) {
                    listenerStdOut.onOutput(out);
                }
            }

            running = false;

        } catch (IOException e) {
            running = false;
            listener.onServerException(serverGame);
        } finally {
            pty.destroyForcibly();
            
            serverGame.setStatus(Status.STOPPED);
            listener.onServerStopped(serverGame);
        }
    }

    public ServerProperties getServerProperties() {

        return new ServerProperties() {
            @Override
            public StandardInputInterface setListener(StandardOutputInterface listenerImpl) {
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

    class StandardInputImpl implements StandardInputInterface {

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
    }

    public ServerGame getServerGame() {
        return serverGame;
    }
}
