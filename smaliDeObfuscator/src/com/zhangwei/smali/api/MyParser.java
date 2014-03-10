package com.zhangwei.smali.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

import com.zhangwei.parser.ParserContext;
import com.zhangwei.parser.ParserException;
import com.zhangwei.parser.Rule;
import com.zhangwei.parser.Rule_smali;
import com.zhangwei.parser.Visitor;

public class MyParser  {
/*	  public static void main(final String args[]) {
		  boolean ret = Paser_file(args[0]);
		  if(ret){
			  System.exit(0);
		  }else{
			  System.exit(-1);
		  }

		  
	  }
	
	public static void Paser_dir(String root_dir){
		try{
			File root = new File(root_dir);
			if(root.exists()){
				MyParser myParser = new MyParser();
				myParser.loadChildren(root);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static boolean Paser_file(String smali_file_path){
		boolean ret = false;
		try{
			File smali_file = new File(smali_file_path);
			if(smali_file.exists()){
				MyParser myParser = new MyParser();
				ret = myParser.paser(smali_file);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ret;
	}*/
	
	public boolean paser(File file){
		boolean ret = false;
		try{
			SmaliEntry smaliEntry = new SmaliEntry(file, true, file.getName());
		    BufferedReader in = new BufferedReader(new FileReader(file));
		    int ch = 0;
		    StringBuffer out = new StringBuffer();
		    while ((ch = in.read()) != -1)
		      out.append((char)ch);

		    in.close();
		    
			ParserContext context = new ParserContext(out.toString(), false);
			Rule rule = null;
			rule = Rule_smali.parse(context);
			
		    if (rule == null)
		    {
		      throw new ParserException(
		        "rule \"" + (String)context.getErrorStack().peek() + "\" failed",
		        context.text,
		        context.getErrorIndex(),
		        context.getErrorStack());
		    }

		    if (context.text.length() > context.index)
		    {
		      ParserException primaryError = 
		        new ParserException(
		          "extra data found",
		          context.text,
		          context.index,
		          new Stack<String>());

		      if (context.getErrorIndex() > context.index)
		      {
		        ParserException secondaryError = 
		          new ParserException(
		            "rule \"" + (String)context.getErrorStack().peek() + "\" failed",
		            context.text,
		            context.getErrorIndex(),
		            context.getErrorStack());

		        primaryError.initCause(secondaryError);
		      }

		      throw primaryError;
		    }
		    
			Visitor visitor = new com.zhangwei.smali.api.MyDisplayer(smaliEntry );
			rule.accept(visitor);
			ret = true;
		} catch (IllegalArgumentException e) {
			System.out.println("argument error: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("io error: " + e.getMessage());
		} catch (ParserException e) {
			System.out.println("parser error: " + e.getMessage());
		}

		return ret;
	}
	
	public boolean paser(SmaliEntry smaliEntry){
		boolean ret = false;
		try{
		    BufferedReader in = new BufferedReader(new FileReader(smaliEntry.file));
		    int ch = 0;
		    StringBuffer out = new StringBuffer();
		    while ((ch = in.read()) != -1)
		      out.append((char)ch);

		    in.close();
		    
			ParserContext context = new ParserContext(out.toString(), false);
			Rule rule = null;
			rule = Rule_smali.parse(context);
			
		    if (rule == null)
		    {
		      throw new ParserException(
		        smaliEntry.file.getAbsolutePath() + ":\n" + 
		        "rule \"" + (String)context.getErrorStack().peek() + "\" failed",
		        context.text,
		        context.getErrorIndex(),
		        context.getErrorStack());
		    }

		    if (context.text.length() > context.index)
		    {
		      ParserException primaryError = 
		        new ParserException(
		          smaliEntry.file.getAbsolutePath() + ":\n" + 
		          "extra data found",
		          context.text,
		          context.index,
		          new Stack<String>());

		      if (context.getErrorIndex() > context.index)
		      {
		        ParserException secondaryError = 
		          new ParserException(
		            smaliEntry.file.getAbsolutePath() + ":\n" + 
		            "rule \"" + (String)context.getErrorStack().peek() + "\" failed",
		            context.text,
		            context.getErrorIndex(),
		            context.getErrorStack());

		        primaryError.initCause(secondaryError);
		      }

		      throw primaryError;
		    }
			
			
			Visitor visitor = new com.zhangwei.smali.api.MyDisplayer(smaliEntry);
			rule.accept(visitor);
			ret = true;
		}catch (IllegalArgumentException e) {
			System.out.println("argument error: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("io error: " + e.getMessage());
		} catch (ParserException e) {
			System.out.println("parser error: " + e.getMessage());
		}

		return ret;
	}
	
/*	private void loadChildren(File parent){
		if(parent==null){
			return;
		}
		
		if(parent.isDirectory()){
			File[] filelist = parent.listFiles();
			if(filelist!=null && filelist.length>0){
				for (File f : filelist) {
					if(f.isFile()){
						//EntryVector 里装的必定是文件或函数
						paser(f);

					}else{
						//递归遍历目录
						loadChildren(f);
					}
					
				}
			}else{
				
			}

		}else{
			//EntryVector 里装的必定是文件或函数， 以后加入smali解析的功能
			
		}
	}*/

}
