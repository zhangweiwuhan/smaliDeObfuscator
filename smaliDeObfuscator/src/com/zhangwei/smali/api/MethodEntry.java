package com.zhangwei.smali.api;

/**
 * classMethod = optPadding dirMethod padding *(accessMode padding) (classConstructorName / clssMethodName) optPadding classMethodProto optPadding CRLF methodBody optPadding dirEndMethod optPadding CRLF;
 * classMethod = *accessMode [classConstructorName] classMethodProto methodBody
 * 
 * */
public class MethodEntry extends CommonEntry {
	
	public MethodEntry(SmaliEntry se, int offset){
		super(se, 3, offset);
	}
	
	//.method public constructor <init>()V
	//.method public final endDocument()V
	public String classConstructorName; //用来标识该函数是否为构造函数,  constructor <init>
	public String classMethodName; //函数名 endDocument
	public String classMethodProto;    //用来表示该函数的原型，参数及返回值 ()V
	
	public void close() {
		// TODO Auto-generated method stub
		if(classConstructorName!=null){
			super.id = classConstructorName + "_" + classMethodProto;
		}else{
			super.id = classMethodName + "_" + classMethodProto;
		}

	}
	
	@Override
	public void RenameType(String classname_before, String classname_after){
		content = content.replace(classname_before, classname_after);
		classMethodProto = classMethodProto.replace(classname_before, classname_after);

		if(classConstructorName!=null){
			super.id = classConstructorName + "_" + classMethodProto;
		}else{
			super.id = classMethodName + "_" + classMethodProto;
		}
		super.content = content;
	}

	public void RenameName(String className, String oldMethodName,
			String newMethodName, String classMethodProto) {
		// TODO Auto-generated method stub
		content = content.replace(" " + oldMethodName + classMethodProto, " " + newMethodName + classMethodProto);
		classMethodName = newMethodName;

		if(classConstructorName!=null){
			super.id = classConstructorName + "_" + classMethodProto;
		}else{
			super.id = classMethodName + "_" + classMethodProto;
		}
		super.content = content;
	}
}
