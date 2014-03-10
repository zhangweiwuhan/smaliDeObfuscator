package com.zhangwei.ui;

import java.io.File;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class JavaFileView extends FileView {
	Icon smaliIcon = new ImageIcon("leafIcon.png");

	public String getName(File file) {
		String filename = file.getName();
		if (filename.endsWith(".java")) {
			String name = filename + " : " + file.length();
			return name;
		}
		return null;
	}

	public String getTypeDescription(File file) {
		String typeDescription = null;
		String filename = file.getName().toLowerCase();

		if (filename.endsWith(".smali")) {
			typeDescription = "smali Source";
		} 
		
		return typeDescription;
	}

	public Icon getIcon(File file) {
		if (file.isDirectory()) {
			return null;
		}
		Icon icon = null;
		String filename = file.getName().toLowerCase();
		if (filename.endsWith(".smali")) {
			icon = smaliIcon;
		} 
		
		return icon;
	}
}
