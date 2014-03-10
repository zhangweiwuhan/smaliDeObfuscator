package com.zhangwei.utils;

public class StringHelper {
	/**
	 *  @param className eg: Lcom/a/b/c;
	 *  @return c
	 * */
	public static String getShortNameOfSmali(String className){
		String ret = null;
		if(className!=null){
			String sub = className.substring(1, className.length()-1); // com/a/b/c
			String[] array = sub.split("/");
			ret = array[array.length-1];
		}
 
		return ret;
	}

}
