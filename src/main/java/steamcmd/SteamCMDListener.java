package steamcmd;

public interface SteamCMDListener {
	
	void onStdOut(String out);
	String onAuthCode();
	void onFailedLoginCode();
	void onInvalidPassword();
	
}
