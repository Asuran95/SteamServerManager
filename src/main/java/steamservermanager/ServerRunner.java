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

public class ServerRunner extends Thread {

    private ServerGame serverGame;
    private String localDir;

    private PtyProcess pty;

    public ServerRunner(ServerGame serverGame) {
        this.serverGame = serverGame;
    }

    @Override
    public void run() {

        String startScript = "Binaries/Win64/KFGameSteamServer.bin.x86_64 KF-BurningParis";

        String serverName = "kf2 server";

        localDir = "/mnt/steamcompat/librarytest";

        List<String> commandBuffer = new ArrayList<>();

        String[] scriptSplit = startScript.split(" ");

        String pathServer = localDir + File.separator + serverName + File.separator + scriptSplit[0];

        commandBuffer.add(pathServer);

        for (int i = 1; i < scriptSplit.length; i++) {

            commandBuffer.add(scriptSplit[i]);
        }

        try {

            this.pty = PtyProcess.exec(commandBuffer.toArray(new String[0]));

            InputStream stdout = pty.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stdout), 1);

            while (true) {
                String out = reader.readLine();

                if (out == null) {
                    break;
                }

                System.out.println(out);

            }

        } catch (IOException e) {
        }

    }
}
