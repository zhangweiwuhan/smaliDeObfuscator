package com.zhangwei.smali.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;

import com.zhangwei.parser.ParserContext;
import com.zhangwei.parser.Rule;
import com.zhangwei.parser.Rule_smali;
import com.zhangwei.parser.Visitor;
import com.zhangwei.ui.jtree.SmaliLoader;

/**
 *  代表一个smali文件
 * */
public class SmaliEntry {
	public static String rootPath = "";
	
	public Vector<SmaliEntry> children; //!isFile 才有效
    public String name;

	public File file;
	public boolean isFile;
	
	//smali = classHeader *(classField / skipLine) *(classMethod / skipLine);
	public HeadEntry classHeader;
	public ArrayList<FieldEntry> entry_field_array;
	public ArrayList<MethodEntry> entry_method_array;
	
	public SmaliEntry(File file, boolean isFile, String name){
		this.file = file;	
		this.isFile = isFile;
		this.name = name;
		this.children = new Vector<SmaliEntry>(); 
	}
	
	/**
	 * 重命名classHeader， entry_field_array， entry_method_array中的结构内容
	 * */
	public void renameClass(String classNameSelf, String s) {
		// TODO Auto-generated method stub
		classHeader.RenameType(classNameSelf, s);
		
		if(entry_field_array!=null && entry_field_array.size()>0){
			for(FieldEntry item : entry_field_array){
				item.RenameType(classNameSelf, s);
			}
		}
		
		if(entry_method_array!=null && entry_method_array.size()>0){
			for(MethodEntry item : entry_method_array){
				item.RenameType(classNameSelf, s);
			}
		}
	}
	
	/**
	 * @param src_className eg: "Lcom/a/b/c;"
	 * @param dst_className eg: "Lcom/d/e/f/g;"
	 * 
	 * @return 是否需要有被替换的，让ui更新
	 * */
	public boolean renameClassContent(String src_className, String dst_className){
		if(isFile){
			//step1 process content:

			String content = getFileContent();
			
			if(content.contains(src_className)){
				/*String newContent = content.replaceAll(Matcher.quoteReplacement(src_className), Matcher.quoteReplacement(dst_className));*/
				String newContent = content.replace(src_className, dst_className);
				setFileContent(newContent);
				return true;
			}else{
				return false;
			}
			
		}else{
			return false;
		}

	}
	
	public boolean renameFieldContent(String className, String oldFieldName, String newFieldName, String classFieldType) {
		// TODO Auto-generated method stub
		if(isFile){
			//step1 process content:

			String content = getFileContent();
			String oldName = /*className + "->" +*/ oldFieldName + ":" + classFieldType;
			String newName = /*className + "->" +*/ newFieldName + ":" + classFieldType;
			
			
			if(className!=null && className.equals(this.classHeader.classNameSelf)){
				if(content.contains(oldFieldName)){
					//content = content.replaceAll(Matcher.quoteReplacement(" " + oldName), Matcher.quoteReplacement(" " + newName));
					content = content.replace(" " + oldName, " " + newName);
					setFileContent(content);
				}
			}
			
			if(content.contains(oldFieldName)){
/*				content = content.replaceAll(
						Matcher.quoteReplacement(" " + className + "->" +oldName), 
						Matcher.quoteReplacement(" " + className + "->" +newName));*/
				content = content.replace(" " + className + "->" +oldName, " " + className + "->" +newName);
				setFileContent(content);
				return true;
			}else{
				return false;
			}
			
		}else{
			return false;
		}
	}
	
	
	public boolean renameMethodContent(String className, String oldMethodName,
			String newMethodName, String classMethodProto) {
		// TODO Auto-generated method stub
		if(isFile){
			//step1 process content:

			String content = getFileContent();
			String oldName = /*className + "->" +*/ oldMethodName + classMethodProto;
			String newName = /*className + "->" +*/ newMethodName + classMethodProto;
			
			
			if(className!=null && className.equals(this.classHeader.classNameSelf)){
				if(content.contains(oldMethodName)){
					//content = content.replaceAll(Matcher.quoteReplacement(" " + oldName), Matcher.quoteReplacement(" " + newName));
					content = content.replace(" " + oldName, " " + newName);
					setFileContent(content);
				}
			}
			
			if(content.contains(oldMethodName)){
/*				content = content.replaceAll(
						Matcher.quoteReplacement(" " + className + "->" +oldName), 
						Matcher.quoteReplacement(" " + className + "->" +newName));*/
				content = content.replace(" " + className + "->" +oldName, " " + className + "->" +newName);
				setFileContent(content);
				return true;
			}else{
				return false;
			}
			
		}else{
			return false;
		}
	}
	
	/**
	 *  将文本中每行以.method 或  .field开始的行进行public提权
	 * */
	public void upPublic() {
		// TODO Auto-generated method stub
		String content = getFileContent();
		if(content!=null){
			String[] lines = content.split("\n");
			boolean change = false;
			
			if(lines!=null && lines.length>0){
				for(int index=0; index<lines.length; index++){
					String line = lines[index];
					if(line.startsWith(".method ")){
						if(line.contains("protected ")){
							lines[index] = line.replace("protected ", "public ");
							change = true;
						}else if(line.contains("private ")){
							lines[index] = line.replace("private ", "public ");
							change = true;
						}else if(line.contains("public ")){
							//nothing to do
						}else{ //add public
							lines[index] = ".method public " + line.substring(".method ".length());
							change = true;
						}
					}else if(line.startsWith(".field ")){
						if(line.contains("protected ")){
							lines[index] = line.replace("protected ", "public ");
							change = true;
						}else if(line.contains("private ")){
							lines[index] = line.replace("private ", "public ");
							change = true;
						}else if(line.contains("public ")){
							//nothing to do
						}else{ //add public
							lines[index] = ".field public " + line.substring(".field ".length());
							change = true;
						}
					}else if(line.startsWith(".class ")){
						if(line.contains("protected ")){
							lines[index] = line.replace("protected ", "public ");
							change = true;
						}else if(line.contains("private ")){
							lines[index] = line.replace("private ", "public ");
							change = true;
						}else if(line.contains("public ")){
							//nothing to do
						}else{ //add public
							lines[index] = ".class public " + line.substring(".class ".length());
							change = true;
						}
					}else if(line.contains("invoke-direct ")){ 
						if(!line.contains("<init>")){
							lines[index] = line.replace("invoke-direct ", "invoke-virtual ");
							change = true;
						}
					}else if(line.contains("invoke-direct/range ")){ 
						if(!line.contains("<init>")){
							lines[index] = line.replace("invoke-direct/range ", "invoke-virtual/range ");
							change = true;
						}
					}
				}
				
				if(change){
					StringBuilder sb = new StringBuilder();
					for(String line : lines){
						sb.append(line + "\n");
					}
					setFileContent(sb.toString());
				}
			}
			

		}


		
	}
	
	/**
	 * 将该类中出现的src类名或方法，替换为dst的类名或方法
	 * 
	 * @param src_className eg: "Lcom/a/b/c;"
	 * @param dst_className eg: "Lcom/d/e/f/g;"
	 * */
	public boolean renameClassFile(String src_className, String dst_className){
		if(isFile){
			//step1 process content:
			//renameClassVar(src_className, dst_className);
			
			//step2 process file
			File rootDir = findRoot();
			File newFile = getNewFile(rootDir, dst_className); //新的类
			if(newFile!=null){
				File parentOfNew = newFile.getParentFile(); //新的包
				File parentOfOld = file.getParentFile(); //旧的包
				
				SmaliLoader.getInstance().changePackage(this , parentOfOld, parentOfNew);
				file.renameTo(newFile);
				file = newFile; 
				name = file.getName();

				cleanEmptyDir(parentOfOld);
				
				return true;
			}else{
				return false;
			}

		}else{
			return false;
		}

	}
	
	public void cleanEmptyDir(File parent){
		if(parent.listFiles().length==0){
			File parent_parent = parent.getParentFile();
			parent.delete();
			//SmaliLoader.getInstance().removePackage(parent);
			cleanEmptyDir(parent_parent);
		}
	}
	
	/**
	 * 
	 * 得到新类对应文件的File对象，并创建相应的父级目录
	 * @param cls_name eg: "Lcom/a/b/c;"
	 * */
	public File getNewFile(File root, String cls_name){
		StringBuilder leaf = new StringBuilder();
		leaf.append(root.getAbsolutePath());
		String classNameTmp = cls_name.substring(1, cls_name.length()-1); // com/b/a/a
		String[] classNameArray = classNameTmp.split("/");
		if(classNameArray!=null && classNameArray.length>0){
			for(int i = 0; i<classNameArray.length; i++){
				String item = classNameArray[i];
				leaf.append(File.separator + item);
			}
			leaf.append(".smali");
		}

		File ret = new File(leaf.toString());

/*		if (!ret.getParentFile().exists()) {
			ret.getParentFile().mkdirs();
		}*/

		// 判断目标文件所在的目录是否存在
		if (!ret.getParentFile().exists()) { // 如果目标文件所在的目录不存在，则创建父目录
			System.out.println("目标文件所在目录不存在，准备创建它！");
			if (!ret.getParentFile().mkdirs()) {
				System.out.println("创建目标文件所在目录失败！");
				return null;
			}else{
				//SmaliLoader.getInstance().insertPackage(ret.getParentFile());
			}
		}
		
		return ret;
	}
	
	public File findRoot(){
		File fileOfRoot = null;

		if(classHeader!=null && classHeader.classNameSelf!=null && file!=null && file.isFile()){
			fileOfRoot = file;
			String classNameTmp = classHeader.classNameSelf.substring(1, classHeader.classNameSelf.length()-1); //Lcom/b/a/a;
			String[] classNameArray = classNameTmp.split("/");
			if(classNameArray!=null && classNameArray.length>0){
				for(int i = classNameArray.length-1; i>=0; i--){
					fileOfRoot = fileOfRoot.getParentFile(); //up a level
/*					String item = classNameArray[i];
					String lastItem = fileOfRoot.isFile()?fileOfRoot.getName().replaceAll(".smali", ""):fileOfRoot.getName();
					if(item!=null && item.equals(lastItem)){
						fileOfRoot = fileOfRoot.getParentFile(); //up a level
					}*/
				}
			}
			
		}

		return fileOfRoot;
	}
	
	public CommonEntry getHeader(){
		return (CommonEntry)classHeader;
	}
	
	public ArrayList<CommonEntry>  getMethodList(){
		ArrayList<CommonEntry> ret = new ArrayList<CommonEntry>();
		if(entry_method_array!=null && entry_method_array.size()>0){
			for(CommonEntry item : entry_method_array){
				ret.add(item);
			}
		}
		
		return ret;
	}
	
	public ArrayList<CommonEntry> getFieldList(){
		ArrayList<CommonEntry> ret = new ArrayList<CommonEntry>();
		if(entry_field_array!=null && entry_field_array.size()>0){
			for(CommonEntry item : entry_field_array){
				ret.add(item);
			}
		}
		
		return ret;
	}

	// ------------------------classHeader --------------------- 
	public void new_classHeader(int offset) {
		// TODO Auto-generated method stub
		if(classHeader==null){
			classHeader = new HeadEntry(this, offset);
		}
	}
		
	
	public void add_classHeader_content(String content) {
		// TODO Auto-generated method stub

		classHeader.add_content(content);
	}
	
	public void add_classHeader_classClass(String classClass) {
		// TODO Auto-generated method stub
		classHeader.classClass = classClass;
	}
	
	public void add_classHeader_classNameSelf(String classNameSelf) {
		// TODO Auto-generated method stub
		classHeader.classNameSelf = classNameSelf;
	}
	
	public void add_classHeader_classSuper(String classSuper) {
		// TODO Auto-generated method stub
		classHeader.classSuper = classSuper;
	}
	
	public void add_classHeader_classNameSuper(String classNameSuper) {
		// TODO Auto-generated method stub
		classHeader.classNameSuper = classNameSuper;
	}

	public void close_classHeader() {
		// TODO Auto-generated method stub
		classHeader.close();
	}
	
	
	// ---------------      classField ---------------------   
	public void new_classField(int offset){
		if(entry_field_array==null){
			entry_field_array = new ArrayList<FieldEntry>();
		}
		
		entry_field_array.add(new FieldEntry(this, offset));
	}

	public void add_classField_content(String content) {
		// TODO Auto-generated method stub
		FieldEntry last = entry_field_array.get(entry_field_array.size()-1);
		last.content = content;
	}
	
	public void put_classField_Name(String classFieldName) {
		// TODO Auto-generated method stub
		FieldEntry last = entry_field_array.get(entry_field_array.size()-1);
		last.classFieldName = classFieldName;
	}
	
	public void put_classField_Type(String classFieldType) {
		// TODO Auto-generated method stub
		FieldEntry last = entry_field_array.get(entry_field_array.size()-1);
		last.classFieldType= classFieldType;
	}
	
	public void close_classField() {
		// TODO Auto-generated method stub
		FieldEntry last = entry_field_array.get(entry_field_array.size()-1);
		last.close();
	}

	// ---------------      classMethod ---------------------   
	public void new_classMethod(int offset) {
		// TODO Auto-generated method stub
		if(entry_method_array==null){
			entry_method_array = new ArrayList<MethodEntry>();
		}
		
		entry_method_array.add(new MethodEntry(this, offset));
	}

	public void add_classMethod_content(String content) {
		// TODO Auto-generated method stub
		MethodEntry last = entry_method_array.get(entry_method_array.size()-1);
		last.content = content;
	}

	public void put_classMethod_classConstructorName(String classConstructorName) {
		// TODO Auto-generated method stub
		MethodEntry last = entry_method_array.get(entry_method_array.size()-1);
		last.classConstructorName = classConstructorName;
	}

	public void put_classMethod_classMethodProto(String classMethodProto) {
		// TODO Auto-generated method stub
		MethodEntry last = entry_method_array.get(entry_method_array.size()-1);
		last.classMethodProto = classMethodProto;
	}

	public void put_classMethod_classMethodName(String classMethodName) {
		// TODO Auto-generated method stub
		MethodEntry last = entry_method_array.get(entry_method_array.size()-1);
		last.classMethodName = classMethodName;
	}
	
	public void close_classMethod() {
		// TODO Auto-generated method stub
		MethodEntry last = entry_method_array.get(entry_method_array.size()-1);
		last.close();
	}
	
	public String toString() {
		return name;
	}

/*	public String getName() {
		if (isFile) {
			return name;
		} else {
			String packageName = file.getAbsolutePath();
			return packageName.replace(rootPath, "").replace("\\", ".");
		}

	}*/

	public Object getChildAt(int index) {
		// TODO Auto-generated method stub
		return (SmaliEntry)children.elementAt(index);
	}

	public int getChildCount() {
		// TODO Auto-generated method stub
		return children.size();
	}

	public int getIndexOfChild(SmaliEntry child) {
		// TODO Auto-generated method stub
		return children.indexOf(child);
	}

	public boolean isFile() {
		// TODO Auto-generated method stub
		return isFile;
	}



	public String getFileContent(){
		try{
		    BufferedReader in = new BufferedReader(new FileReader(file));
		    int ch = 0;
		    StringBuffer out = new StringBuffer();
		    while ((ch = in.read()) != -1)
		      out.append((char)ch);

		    in.close();
		    return out.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	public String setFileContent(String newContent){
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(file, false));

		    out.write(newContent);

		    out.close();
		    return out.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
















}
