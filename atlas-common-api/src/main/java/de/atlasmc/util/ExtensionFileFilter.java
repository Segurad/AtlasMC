package de.atlasmc.util;

import java.io.File;
import java.io.FileFilter;

public class ExtensionFileFilter implements FileFilter {

	private final String[] extension;
	private final boolean acceptDirs;
	
	public ExtensionFileFilter(String... extension) {
		this(false, extension);
	}
	
	public ExtensionFileFilter(boolean acceptDirs, String... extension) {
		this.extension = extension;
		this.acceptDirs = acceptDirs;
	}
	
	@Override
	public boolean accept(File pathname) {
		if (pathname.isDirectory()) {
			return acceptDirs;
		}
		String fileName = pathname.getName();
        int i = fileName.lastIndexOf('.');
        if (i > 0 && i < fileName.length() - 1) {
            String extension = fileName.substring(i+1).toLowerCase();
            for (String ex : this.extension) {
            	if (ex.equals(extension))
            		return true;
            }
        }
		return false;
	}

}
