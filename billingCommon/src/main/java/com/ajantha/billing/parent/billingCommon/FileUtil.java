package com.ajantha.billing.parent.billingCommon;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;

public class FileUtil 
{
	private static Log log = LogFactory.getLog(FileUtil.class);
	
	private static List<String> backlistedPathAndExtension = new ArrayList<>();
	
	private static List<String> blacklistedSpecialCharInFileName = new ArrayList<>();
	
	private FileUtil(){
		
	}
	/**
	 * Method to check if file exists on classpath
	 * 
	 */
	public static boolean checkIfFileExistsOnClasspath(String filename) {
		boolean exists = false;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
			try (InputStream in = loader.getResourceAsStream(filename)) {
				if (in != null) {
					exists = true;
				}
			} catch (Exception e) {
				log.error("Error in method checkIfFileExistsOnClasspath",e);
				//suppress
			}
		return exists;
	}
	
	
	
	/**
	 * Method to get resource/file from classpath, filesystem, url
	 * 
	 * @param path
	 * @author Dima
	 */
	public static InputStream getResource(String path) {
		return getResource(null, path);
	}
	
	
	
	/**
	 * Method to get resource/file from classpath, filesystem, url
	 * 
	 * @param parent
	 * @param path
	 * @author Dima
	 */
	public static InputStream getResource(String parent, String path) {
		InputStream in = null;
		if (isValidFile(parent) && isValidFile(path)) {
			// Get resource from file system
			if (StringUtils.isNotBlank(parent) && StringUtils.startsWithAny(path, new String[] { "./", "../", ".\\", "..\\", "" })) {
				try {
					in = new FileInputStream(new File(parent, path));
				} catch (Exception e) {
					log.error("Error in method getResource",e);
					//suppress

				}
			} else {
				try {
					FileSystemResourceLoader fsLoader = new FileSystemResourceLoader();
					Resource resource = fsLoader.getResource(path);
					if (resource != null && resource.exists()) {
						in = resource.getInputStream();
					}
				} catch (Exception e) {
					log.error("Error in method getResource",e);
					//suppress
				}
			}

			// Get resource from class path
			if (in == null) {
				try {
					in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
				} catch (Exception e) {
					log.error("Exception in method getResource",e);
					//suppress
				}
			}

		}
		
		return in;
	}
	
	
	
	/**
	 * Method to get folder path of resource/file from classpath, filesystem, url
	 * 
	 * @param path
	 * @author Dima
	 */
	public static String getResourceFolderPath(String path) {
		String resourcePath = null;
		String folderPath = null;
		if (isValidFile(path)) {
			//Get resource from file system
			try {
				FileSystemResourceLoader fsLoader = new FileSystemResourceLoader();
				Resource resource = fsLoader.getResource(path);
				if (resource != null && resource.exists()) {
					resourcePath = resource.getFile().getAbsolutePath();
				}
			} catch (Exception e) {
				log.error("Error in method getResourceFolderPath",e);
				//suppress
			}
			
			//Get resource from class path
			if (StringUtils.isBlank(resourcePath)) {
				try {
					resourcePath = Thread.currentThread().getContextClassLoader().getResource(path).getPath();
				} catch (Exception e) {
					log.error("Exception in method getResourceFolderPath",e);
					//suppress
				}
			}
			
			// Get parent folder path
			if (StringUtils.isNotBlank(resourcePath)) {
				folderPath = new File(resourcePath).getParent();
			}
		}

		return folderPath;
	}
	
	/**
	 * Method to validate the file name. If the file extension given in ESAPI properties doesn't match means it will return false.
	 * 
	 * @param fileName
	 * @return
	 * @author Santhoshkumar Palanisamy
	 * @since 1.0
	 */
	public static boolean isValidFileName(String fileName) {
		Iterator<String> i = backlistedPathAndExtension.iterator();
		fileName = fileName.toLowerCase();
		String ext;
		while (i.hasNext()) {
			ext = i.next();
			if (fileName.endsWith(ext.toLowerCase())) {
				return false;
			}
		}
		Iterator<String> bsc = blacklistedSpecialCharInFileName.iterator();
		String ext2;
		while (bsc.hasNext()) {
			ext2 = bsc.next();
			if (fileName.contains(ext2)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Method to validate the file name. If the file extension given in ESAPI properties doesn't match means it will return false.
	 * 
	 * @param fileName
	 * @return
	 * @author Santhoshkumar Palanisamy
	 * @since 1.0
	 */
	public static boolean isValidFile(String fileName) {
		if (StringUtils.isNotBlank(fileName)) {
			Iterator<String> i = backlistedPathAndExtension.iterator();
			fileName = fileName.toLowerCase();
			String ext;
			while (i.hasNext()) {
				ext = i.next();
				if (fileName.endsWith(ext.toLowerCase())) {
					return false;
				}
			}
			Iterator<String> bsc = blacklistedSpecialCharInFileName.iterator();
			String ext2;
			while (bsc.hasNext()) {
				ext2 = bsc.next();
				if (fileName.contains(ext2)) {
					return false;
				}
			}
		}
		return true;
	}
}