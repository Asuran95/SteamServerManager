package steamcmd;

import java.io.IOException;
import java.util.Scanner;


public class MainSteamCMD {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		SteamCMDBuilder builder = new SteamCMDBuilder();
		
		builder.addListener(new listenerSteamCMD());
		
		SteamCMD steamCMD = builder.build();
		
		steamCMD.start();

		steamCMD.forceInstallDir("G:\\Asuran\\Documents\\GitRepositories\\testemanager\\cstrike1");
		
		steamCMD.loginAnonymous();
		
		steamCMD.appUpdate(205);	
		
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
