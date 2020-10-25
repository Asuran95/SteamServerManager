package steamservermanager;

import steamservermanager.models.ServerGame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LibraryFileHelper {

    private List<ServerGame> library;
    private String localDir;

    public LibraryFileHelper(String localDir) {
        this.localDir = localDir;
    }

    @SuppressWarnings("unchecked")
    public List<ServerGame> loadLibraryFromDisk() {
        File libraryFile = new File(localDir + File.separator + "library.bin");

        if (!libraryFile.exists()) {

            System.out.println("Creating library.bin in " + localDir);

            library = new ArrayList<>();

            updateLibraryFile();
        } else {

            System.out.println("Loading library.bin in " + localDir);

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

        return library;
    }

    public void updateLibraryFile() {
        FileOutputStream f = null;

        try {
            f = new FileOutputStream(new File(localDir + File.separator + "library.bin"));
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
}
