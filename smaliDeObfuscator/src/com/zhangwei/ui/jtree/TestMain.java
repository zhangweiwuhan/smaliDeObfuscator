package com.zhangwei.ui.jtree;

import java.io.File;

import com.zhangwei.smali.api.MyParser;

public class TestMain {
	private static MyParser myParser;
	
	public static void main(final String args[]) {
/*		try{
			myParser = new MyParser();
			File file = new File("D:\\android\\crack\\guosen3.6\\smali\\com\\a\\a.smali");
			if(file.exists() && file.isFile()){
				myParser.paser(file);
			}
		}catch(Exception e){
			e.printStackTrace();
		}*/
		
		//findRoot("D:\\android\\crack\\guosen3.6\\smali\\android\\support\\v4\\android\\accessibilityservice\\AccessibilityServiceInfoCompat$AccessibilityServiceInfoIcsImpl.smali", "Landroid/support/v4/android/accessibilityservice/AccessibilityServiceInfoCompat$AccessibilityServiceInfoIcsImpl;");
		test1();
	}
	
	public static void test1(){
		//Log.d(TAG, "setStatusByUUID->userid="+userid +" uuid="+uuid+" success="+success);

		String path = "D:\\android\\crack\\test1\\smali-before\\com10\\b\\a\\b.smali";
		
		File ret = new File(path);

/*		if (!ret.getParentFile().exists()) {
			ret.getParentFile().mkdirs();
		}*/

		// 判断目标文件所在的目录是否存在
		if (!ret.getParentFile().exists()) { // 如果目标文件所在的目录不存在，则创建父目录
			System.out.println("目标文件所在目录不存在，准备创建它！");
			if (!ret.getParentFile().mkdirs()) {
				System.out.println("创建目标文件所在目录失败！");
			}
		}
	}
	
	public static String findRoot(String filePath, String className){
		String ret = null;
		String path = filePath.replaceAll(".smali", "");
		String className2 = className.substring(1, className.length()-1); //Lcom/b/a/a;
		int i = path.lastIndexOf(className2);
		ret = path.substring(0, i);
		System.out.println(ret);
		return ret;
	}
}
