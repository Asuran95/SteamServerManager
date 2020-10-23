package steamservermanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import steamservermanager.exceptions.ServerNameException;

public class SteamServerManager {
	
	private List<ServerGame> library = null;
	private String localLibrary;
	
	private UpdateThread updateThread;
	private UpdateMonitor updateMonitor = new UpdateMonitor();
	

	public SteamServerManager(String localLibrary) {
		this.localLibrary = localLibrary;
		loadLibrary();
		init();
	}
	
	private void init() {
		updateThread = new UpdateThread(updateMonitor, localLibrary);
		updateThread.start();
	}
	
	@SuppressWarnings("unchecked")
	private void loadLibrary() {
		
		File libraryFile = new File(localLibrary + File.separator + "library.bin");
		
		if(!libraryFile.exists()) {
			
			System.out.println("Creating library.bin in " + localLibrary);
			
			library = new ArrayList<>();	
			
			updateLibraryFile();
		} else {
			
			System.out.println("Loading library.bin in " + localLibrary);
				
			FileInputStream f = null;
			try {
				f = new FileInputStream(libraryFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			ObjectInputStream o = null;
			try {
				o = new ObjectInputStream(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				library = (List<ServerGame>) o.readObject();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public void newServerGame(ServerGame serverGame) throws ServerNameException {
		
		for(ServerGame s : library) {
			
			if(s.getServerName().equals(serverGame.getServerName())) {
				throw new ServerNameException();
			}
		}
		
		library.add(serverGame);
		
		updateServerGame(serverGame);
		
	}
	
	public void updateServerGame(ServerGame serverGame) {
		
		//obs: Aqui deve colocar tambem um modo de parar o servidor caso esse comando seja chamado
		
		updateMonitor.addUpdate(serverGame);
		
		updateLibraryFile();
	}
	
	private void updateLibraryFile() {
		FileOutputStream f = null;
		
		try {
			f = new FileOutputStream(new File(localLibrary + File.separator + "library.bin"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ObjectOutputStream o = null;
		try {
			o = new ObjectOutputStream(f);
			o.writeObject(library);
			
			o.close();
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public List<ServerGame> getLibrary() {
		return library;
	}

	public void setLibrary(List<ServerGame> library) {
		this.library = library;
	}

	public String getLocalLibrary() {
		return localLibrary;
	}

	public void setLocalLibrary(String localLibrary) {
		this.localLibrary = localLibrary;
	}	
	
}
