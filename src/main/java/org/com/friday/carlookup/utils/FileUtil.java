package org.com.friday.carlookup.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class FileUtil {
	
	private static final String PROPERTY_FILE_LOCATION = "src/test/resources/UserConfigurations/user.properties";
	
	/**
	 * Utility method to read user.properties file
	 * which has basic details like url,test data file name etc.
	 * @return null
	 * @throws IOException
	 */
	public static Properties readPropertyFile() throws IOException {
		String propertyFileName = PROPERTY_FILE_LOCATION ;
		FileInputStream fis = null;
		Properties properties = null;
		
		try {
			fis = new FileInputStream(propertyFileName);
			properties = new Properties();
			properties.load(fis);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			fis.close();
		}
		return properties;
	}
}
