package com.bigdata.locationweibo.ui.filter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class CSVFileFilter extends FileFilter{

	@Override
	public String getDescription() {return ".csv";}		
	@Override
	public boolean accept(File file) {
		if (file.isDirectory())return true;
	    return file.getName().endsWith(".csv");
	}
	
}
