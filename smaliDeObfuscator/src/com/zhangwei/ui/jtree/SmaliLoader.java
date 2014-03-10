package com.zhangwei.ui.jtree;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.ProgressMonitor;
import javax.swing.Timer;
import javax.swing.tree.DefaultMutableTreeNode;

import com.zhangwei.smali.api.FieldEntry;
import com.zhangwei.smali.api.MethodEntry;
import com.zhangwei.smali.api.MyParser;
import com.zhangwei.smali.api.SmaliEntry;
import com.zhangwei.utils.StringHelper;

public class SmaliLoader {
	private static SmaliLoader ins=null;
	public static String rootpath = null;
	
	private ArrayList<File> list;
	private HashMap<String, SmaliEntry> smailMap;
	
	private MyParser myParser;
	
	private ProgressMonitor monitor;
	private int progress;
	private SmaliEntry root;
	
	private SmaliLoader(){
		list = new ArrayList<File>();
		smailMap = new HashMap<String, SmaliEntry>();
		myParser = new MyParser();
	}
	
	public static SmaliLoader getInstance(){
		if(ins==null){
			ins = new SmaliLoader();
		}
		
		return ins;
	}
	
	public void renameClass(SmaliEntry se, String src_className, String dst_className){
		boolean ret = se.renameClassFile(src_className, dst_className);
		
		if(ret){
			for(Entry<String, SmaliEntry>  item: smailMap.entrySet()){
				SmaliEntry se_item = item.getValue();
				se_item.renameClassContent(src_className, dst_className);
			}
			
			se.renameClass(src_className, dst_className);
		}

	}
	
	public void renameField(SmaliEntry se, FieldEntry fe, String s) {
		// TODO Auto-generated method stub
		String className = se.classHeader.classNameSelf; // Lcom/b/a/a;
		String oldFieldName = fe.classFieldName;
		String newFieldName = s;
		for(Entry<String, SmaliEntry>  item: smailMap.entrySet()){
			SmaliEntry se_item = item.getValue();
			se_item.renameFieldContent(className, oldFieldName, newFieldName, fe.classFieldType);
		}
		
		fe.RenameName(className, oldFieldName, newFieldName, fe.classFieldType);
	}
	
	public void renameMethod(SmaliEntry se, MethodEntry me, String s) {
		// TODO Auto-generated method stub
		String className = se.classHeader.classNameSelf; // Lcom/b/a/a;
		String oldMethodName = me.classMethodName;
		String newMethodName = me.classConstructorName!=null?null:s;
		if(newMethodName!=null){
			for(Entry<String, SmaliEntry>  item: smailMap.entrySet()){
				SmaliEntry se_item = item.getValue();
				se_item.renameMethodContent(className, oldMethodName, newMethodName, me.classMethodProto);
			}
			
			me.RenameName(className, oldMethodName, newMethodName, me.classMethodProto);
		}

	}
	
	private void upPublic(SmaliEntry se) {
		// TODO Auto-generated method stub
		se.upPublic();
		
	}
	
	public void autoRename(Component parent){
		if(root!=null && root.children!=null && root.children.size()>0){
			if(smailMap!=null && smailMap.size()>0){
				monitor = new ProgressMonitor(parent, "Renaming Progress", "Getting Started...", 0, smailMap.size());
				progress = 0;
				
				Collection<SmaliEntry> collections = smailMap.values();
				for(SmaliEntry  it : collections){
	            	progress++;
	            	monitor.setProgress(progress);
	            	monitor.setNote("Renamed num:" + progress + "\n file:" + it.file.getAbsolutePath());
	            	if(monitor.isCanceled()){
	            		break;
	            	}
					
	            	if(it.name.replaceAll(".smali", "").length()==1){//only rename the a,b,c,d ...
						String dst_className = it.classHeader.classNameSelf.substring(0, it.classHeader.classNameSelf.length()-1);
						dst_className = dst_className + "_" + StringHelper.getShortNameOfSmali(it.classHeader.classNameSuper) + ";";
						renameClass(it, it.classHeader.classNameSelf, dst_className);
	            	}
	            	
	            	//upPublic(it);

				}
			}
		}
	}
	
	public void autoPublic(Component parent){
		if(root!=null && root.children!=null && root.children.size()>0){
			if(smailMap!=null && smailMap.size()>0){
				monitor = new ProgressMonitor(parent, "Renaming Progress", "Getting Started...", 0, smailMap.size());
				progress = 0;
				
				Collection<SmaliEntry> collections = smailMap.values();
				for(SmaliEntry  it : collections){
	            	progress++;
	            	monitor.setProgress(progress);
	            	monitor.setNote("Up-Public num:" + progress + "\n file:" + it.file.getAbsolutePath());
	            	if(monitor.isCanceled()){
	            		break;
	            	}	            	
	            	upPublic(it);

				}
			}
		}
	}
	

	public void loadRoot(Component parent, SmaliEntry root){
		this.root = root;
		String root_path = root.file.getAbsolutePath();
		
		if(root_path!=null ){
			if(!root_path.endsWith("\\")){
				root_path = root_path + '\\';
			}
		}else{
			return ;
		}
		
		SmaliEntry.rootPath = root_path;
		
		if(list!=null){
			list.clear();
		}

		if(smailMap!=null){
			smailMap.clear();
		}
		
		//
		loadChildren(root.file);
		
		try{
			if(list.size()>0){
				monitor = new ProgressMonitor(parent, "Loading Progress", "Getting Started...", 0, list.size());

				progress = 0;
				
	            for (File file : list) {
	            	progress++;
	            	monitor.setProgress(progress);
	            	monitor.setNote("Loaded num:" + progress + "\n file:" + file.getAbsolutePath());
	            	if(monitor.isCanceled()){
	            		break;
	            	}
	            	
                	
                	SmaliEntry packageEntry = null;
                	SmaliEntry smaliEntry = new SmaliEntry(file, true, file.getName());
                	boolean Parse_success = myParser.paser(smaliEntry);
                	if(Parse_success){
                		packageEntry = findEntry(root, file.getParentFile()); //查找该节点的父节点，即包
                		
                		//add non-leaf package if not exist
    	            	if(packageEntry==null){
    	            		String packageName = getPackageName(file.getParentFile());
    	            		packageEntry = new SmaliEntry(file.getParentFile(), false, packageName);
    	            		root.children.add(packageEntry);
    	            	}
    	            	
    	            	//add leaf
    	            	packageEntry.children.add(smaliEntry);
    	            	smailMap.put(file.getAbsolutePath(), smaliEntry);
                	}else{
                		break; //abort if has error in parsing
                	}

               
	            }

			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void loadChildren(File parent){
		if(parent==null){
			return;
		}
		
		if(parent.isDirectory()){
			File[] filelist = parent.listFiles();
			if(filelist!=null && filelist.length>0){
				for (File f : filelist) {
					if(f.isFile() && f.getName().endsWith(".smali")){
						//EntryVector 里装的必定是文件或函数
						list.add(f);

					}else{
						//递归遍历目录
						loadChildren(f);
					}
					
				}
			}
		}
	}

	public void insertPackage(File packageFile) {
		// TODO Auto-generated method stub
		SmaliEntry packageEntry = findEntry(root, packageFile);
		
		if(packageEntry==null){//not found, insert
			String packageName = getPackageName(packageFile);
    		packageEntry = new SmaliEntry(packageFile, false, packageName);
    		root.children.add(packageEntry);
    		//smailMap.put(packageEntry.file.getAbsolutePath(), packageEntry);
    	}
	}

	public void removePackage(File packageFile) {
		// TODO Auto-generated method stub
		SmaliEntry packageEntry = findEntry(root, packageFile);
		
		if(packageEntry!=null && packageEntry.children.size()==0){ //empty package can remove
			root.children.remove(packageEntry);
		}

	}

	public void changePackage(SmaliEntry son, File parentOfOld, File parentOfNew) {
		// TODO Auto-generated method stub
		insertPackage(parentOfNew);
		
		SmaliEntry newParent = findEntry(root, parentOfNew);
		SmaliEntry oldParent = findEntry(root, parentOfOld);
		
		newParent.children.add(son);
		oldParent.children.remove(son);
		
		
		removePackage(parentOfOld);
	}
	
	public SmaliEntry findEntry(SmaliEntry root, File file){
    	SmaliEntry ret = null;
		for(SmaliEntry v : root.children){
    		if(v.file.getAbsolutePath()!=null && v.file.getAbsolutePath().equals(file.getAbsolutePath())){
    			ret  = v;
    			break;
    		}
    	}
		
		return ret; 
	}
	
	public static String getPackageName(File packageFile){
		String pathOfParent = packageFile.getAbsolutePath();
		if(!pathOfParent.endsWith("\\")){
			pathOfParent = pathOfParent + '\\';
		}
		
		String packageName = pathOfParent.replace(SmaliEntry.rootPath, "").replace("\\", ".");
		
		if(packageName.equals("")){
			packageName = ".";
		}else if(packageName.endsWith(".")){
			packageName = packageName.substring(0, packageName.length()-1);
		}
		
		return packageName;
	}





}
