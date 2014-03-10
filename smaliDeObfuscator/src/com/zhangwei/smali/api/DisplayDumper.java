package com.zhangwei.smali.api;

public class DisplayDumper {
	private static DisplayDumper ins = null;
	
	private int index;
	private StringBuffer buffer;
	
	private DisplayDumper(){
		index = 0;
		buffer = new StringBuffer();
	}
	
	public static DisplayDumper getInstance(){
		if(ins==null){
			ins = new DisplayDumper();
		}
		
		return ins;
	}
	
	public void reset(){
		index = 0;
		buffer = new StringBuffer();
	}
	
	public int getIndex(){
		return index;
	}
	
	public String getSubStr(int begin, int end){
		String ret = null;
		try{
			ret = buffer.substring(begin, end);
		}catch(StringIndexOutOfBoundsException  e){
			e.printStackTrace();
		}
		
		return ret;

	}
	
	public int writeStr(String str){
		//System.out.print(str);
		if(str!=null){
			buffer.append(str);
			index+=str.length();
		}
		
		return index;

	}
	

}
