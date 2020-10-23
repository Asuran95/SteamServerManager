package steamcmd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.pty4j.PtyProcess;

public class SteamCMDThread extends Thread {

	private Semaphore semaphore;
	private List<SteamCMDListener> listeners;
	private PtyProcess pty;

	public SteamCMDThread(Semaphore semaphore, List<SteamCMDListener> listeners, PtyProcess pty) {
		this.semaphore = semaphore;
		this.listeners = listeners;
		this.pty = pty;
	}

	@Override
	public void run() {
		InputStream stdout = pty.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stdout), 1);

		try {
			while (true) {
				String out = reader.readLine();

				if (out == null) {
					semaphore.release();
					break;
				} else if (out.contains("[1m")) {
					// System.err.println("Unlock Sem");
					semaphore.release();
				} else if (out.contains("code from your Steam Guard")
						|| out.contains("This computer has not been authenticated")) {

					String authCode = "";

					for (SteamCMDListener l : listeners) {
						authCode = l.onAuthCode();
					}

					OutputStream stdin = pty.getOutputStream();
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

					writer.write(authCode + "\n");
					writer.flush();

				} else if (out.contains("Two-factor code mismatch") || out.contains("Invalid Login Auth Code")) {
					for (SteamCMDListener l : listeners) {
						l.onFailedLoginCode();
					}
				} else if (out.contains("Invalid Password")) {
					for (SteamCMDListener l : listeners) {
						l.onInvalidPassword();
					}
				} else {
					for (SteamCMDListener l : listeners) {
						l.onStdOut(out);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
