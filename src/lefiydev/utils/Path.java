package lefiydev.utils;

import java.io.File;

public class Path {
	
	public static final String getJavaSystem() {
		return System.getProperty("user.dir") + File.separator + "jre" + File.separator + "zulu-8" + File.separator + "bin" + File.separator + "java";
	}
}