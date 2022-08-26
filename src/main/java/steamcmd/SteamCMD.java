package steamcmd;

import com.pty4j.PtyProcess;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;


public class SteamCMD implements Closeable {

	private PtyProcess pty;
	private Semaphore semaphore = new Semaphore(1);
	private List<SteamCMDListener> listeners = new ArrayList<>();
	private String steamCmdLocal;

	public SteamCMD() throws IOException, InterruptedException {
		startSteamCmd();
	}

	public SteamCMD(String steamCmdLocal) throws IOException, InterruptedException {
		this.steamCmdLocal = steamCmdLocal;
		startSteamCmd();
	}

	public SteamCMD(SteamCMDListener listener) throws IOException, InterruptedException {
		listeners.add(listener);
		startSteamCmd();
	}

	public SteamCMD(SteamCMDListener listener, String steamCmdLocal) throws IOException, InterruptedException {
		this.steamCmdLocal = steamCmdLocal;
		listeners.add(listener);
		startSteamCmd();
	}
	
	public void addListener(SteamCMDListener listener) {
		listeners.add(listener);
	}

	private void startSteamCmd() throws IOException, InterruptedException {

		if (steamCmdLocal == null) {
			String os = System.getProperty("os.name").toLowerCase();

			if (os.contains("linux")) {
				String localDir = "/tmp/steamcmd/";
				String zipSteam = "steamcmd_linux.tar.gz";
				String steamcmdURL = "https://steamcdn-a.akamaihd.net/client/installer/steamcmd_linux.tar.gz";
				steamCmdLocal = localDir + "steamcmd.sh";

				File shellSteamcmd = new File(steamCmdLocal);

				if (!shellSteamcmd.exists()) {
					File localFile = new File(localDir);
					File zipFile = new File(localDir + zipSteam);

					localFile.mkdir();

					URL url = new URL(steamcmdURL);
					ReadableByteChannel rbc = Channels.newChannel(url.openStream());
					FileOutputStream fos = new FileOutputStream(zipFile);
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
					fos.close();
					rbc.close();

					Archiver archiver = ArchiverFactory.createArchiver("tar", "gz");
					archiver.extract(zipFile, localFile);
				}
				
			} else if (os.contains("win")) {
				String localDir = System.getProperty("java.io.tmpdir") + "steamcmd" + File.separator;
				String zipSteam = "steamcmd.zip";
				String steamcmdURL = "https://steamcdn-a.akamaihd.net/client/installer/steamcmd.zip";
				steamCmdLocal = localDir + "steamcmd.exe";

				File shellSteamcmd = new File(steamCmdLocal);

				if (!shellSteamcmd.exists()) {
					File localFile = new File(localDir);
					File zipFile = new File(localDir + zipSteam);

					localFile.mkdir();

					URL url = new URL(steamcmdURL);
					ReadableByteChannel rbc = Channels.newChannel(url.openStream());
					FileOutputStream fos = new FileOutputStream(zipFile);
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
					fos.close();
					rbc.close();

					Archiver archiver = ArchiverFactory.createArchiver("zip");
					archiver.extract(zipFile, localFile);
				}

			}
		}

		String[] commandLines = { steamCmdLocal };

		this.pty = PtyProcess.exec(commandLines);

		Thread steamcmdThread = new SteamCMDThread(semaphore, listeners, pty);

		semaphore.acquire();
		steamcmdThread.start();
		semaphore.acquire();
	}

	public void login(String username, String password) throws InterruptedException, IOException {
		login(username, password, "");
	}

	public void login(String username, String password, String authCode) throws InterruptedException, IOException {

		String loginCommand = "login " + username + " " + password + " " + authCode;

		OutputStream stdin = pty.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

		writer.write(loginCommand + "\n");
		writer.flush();

		semaphore.acquire();
	}

	public void loginAnonymous() throws InterruptedException, IOException {

		String loginCommand = "login anonymous";

		OutputStream stdin = pty.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

		writer.write(loginCommand + "\n");
		writer.flush();

		semaphore.acquire();
	}

	public void forceInstallDir(String dir) throws InterruptedException, IOException {

		String installDirCommand = "force_install_dir " + "\"" + dir + "\"";

		OutputStream stdin = pty.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

		writer.write(installDirCommand + "\n");
		writer.flush();

		semaphore.acquire();
	}

	public void appUpdate(long appId) throws IOException, InterruptedException {
		appUpdate(appId, "");
	}

	public void appUpdate(long appId, String... flags) throws IOException, InterruptedException {

		String appUpdateCommand = "app_update " + appId;

		for (String f : flags) {
			appUpdateCommand += " " + f;
		}

		OutputStream stdin = pty.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

		writer.write(appUpdateCommand + "\n");
		writer.flush();

		semaphore.acquire();
	}

	public void appSetConfig(long appId, String key, String value) throws IOException, InterruptedException {

		String appSetConfigCommand = "app_set_config " + appId + " " + key + " " + value;

		OutputStream stdin = pty.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

		writer.write(appSetConfigCommand + "\n");
		writer.flush();

		semaphore.acquire();
	}

	public void quit() throws InterruptedException, IOException {

		OutputStream stdin = pty.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

		writer.write("quit" + "\n");
		writer.flush();

		semaphore.acquire();
		
		pty.destroyForcibly();
	}

	@Override
	public void close() throws IOException {
		if(pty.isAlive()) {
			try {
				quit();
			} catch (Exception e) {
				throw new IOException(e);
			}
		}
	}
}
