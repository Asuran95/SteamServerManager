package steamcmd;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.pty4j.PtyProcess;
import com.pty4j.PtyProcessBuilder;


public class SteamCMD implements Closeable {

	private Process process;
	private Semaphore semaphore = new Semaphore(1);
	private List<SteamCMDListener> listeners = new ArrayList<>();
	private Thread steamCMDThread;
	private PtyProcessBuilder processBuilder;
	
	public SteamCMD(PtyProcessBuilder processBuilder, List<SteamCMDListener> listeners) {
		this.processBuilder = processBuilder;
		this.listeners = listeners;
	}
	
	public void addListener(SteamCMDListener listener) {
		listeners.add(listener);
	}
	
	public void start() {
		
		if (steamCMDThread == null || !steamCMDThread.isAlive()) {
			this.process = startSteamCMD(processBuilder);
		}
	}
	
	private Process startSteamCMD(PtyProcessBuilder processBuilder) {
		PtyProcess process = null;
		
		try {
			process = processBuilder.start();
			
			this.semaphore = new Semaphore(1);
			
			this.steamCMDThread = new SteamCMDThread(semaphore, listeners, process);
			
			getSemaphore();
			
			this.steamCMDThread.start();
			
			getSemaphore();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return process;
	}


	public void login(String username, String password) {
		login(username, password, "");
	}

	public void login(String username, String password, String authCode) {
		String loginCommand = "login " + username + " " + password + " " + authCode;

		sendCommand(loginCommand);
	}

	public void loginAnonymous() {
		String loginCommand = "login anonymous";

		sendCommand(loginCommand);
	}
	
	public void logout() {
		String logoutCommand = "logout";

		sendCommand(logoutCommand);
	}

	public void forceInstallDir(String dir) {
		String installDirCommand = "force_install_dir " + "\"" + dir + "\"";

		sendCommand(installDirCommand);
	}

	public void appUpdate(long appId) {
		appUpdate(appId, "");
	}

	public void appUpdate(long appId, String... flags) {
		String appUpdateCommand = "app_update " + appId;

		for (String f : flags) {
			appUpdateCommand += " " + f;
		}

		sendCommand(appUpdateCommand);
	}

	public void appSetConfig(long appId, String key, String value) {
		String appSetConfigCommand = "app_set_config " + appId + " " + key + " " + value;
		
		sendCommand(appSetConfigCommand);
	}

	public void quit() {
		String logoutCommand = "quit";
		
		sendCommand(logoutCommand);
		
		process.destroyForcibly();
	}
	
	private void getSemaphore() {
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sendCommand(String command) {
		OutputStream stdin = process.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

		try {
			writer.write(command + "\n");
			writer.flush();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
		
		getSemaphore();
	}

	@Override
	public void close() throws IOException {
		if(process.isAlive()) {
			try {
				quit();
			} catch (Exception e) {
				throw new IOException(e);
			}
		}
	}
}
