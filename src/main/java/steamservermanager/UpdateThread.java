package steamservermanager;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import steamcmd.SteamCMD;
import steamcmd.SteamCMDListener;

public class UpdateThread extends Thread {

	private UpdateMonitor monitor;
	private String localLibrary;

	public UpdateThread(UpdateMonitor monitor, String localLibrary) {
		this.monitor = monitor;
		this.localLibrary = localLibrary;
	}

	@Override
	public void run() {
		
		SteamCMD steamCmd = null;
		try {
			steamCmd = new SteamCMD(new listenerSteamCMD());
			steamCmd.loginAnonymous();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		while (true) {
			ServerGame updateJob = null;

			synchronized (monitor) {
				updateJob = monitor.getUpdateJob();
			}

			System.out.println("Atualizando " + updateJob.getServerName());
			
			try {
				
				File localDir = new File(localLibrary + File.separator + updateJob.getServerName());
				
				if(!localDir.exists()) {
					localDir.mkdir();
				}
				
				steamCmd.forceInstallDir(localLibrary + File.separator + updateJob.getServerName());
				steamCmd.appUpdate(updateJob.getGameId(), "validate");				
				
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}

			monitor.completedUpdateJob(updateJob);
		}
	}

}

class listenerSteamCMD implements SteamCMDListener {

	@Override
	public void onStdOut(String out) {
		System.out.println(out);
	}

	@Override
	public String onAuthCode() {

		System.out.print("Two-factor code:");
		Scanner sc = new Scanner(System.in);

		String authCode = sc.nextLine();

		sc.close();

		return authCode;
	}

	@Override
	public void onFailedLoginCode() {
		System.err.println("FAILED login with result code Two-factor code mismatch");
	}

	@Override
	public void onInvalidPassword() {
		System.err.println("FAILED login with result code Invalid Password");
	}
}
