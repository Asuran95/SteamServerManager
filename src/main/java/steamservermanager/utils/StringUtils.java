package steamservermanager.utils;

public class StringUtils {
	
	public static String normalizeStringForDirectoryName(String dirName) {
    	dirName.trim();
    	dirName = dirName.replaceAll(" ", "_");
    	dirName = dirName.replaceAll("\\W+", "").trim();

    	return dirName;
    }

}
