package com.zhangwei.smali.api;

public class HeadEntry extends CommonEntry{
	//classHeader = *skipLine classClass *skipLine classSuper *skipLine [classSource] *skipLine *classImplements *skipLine;
    //classHeader = classClass classSuper
	public String classClass;
	public String classSuper;  //.super Lcom/b/a/b;
	public String classNameSelf; // Lcom/b/a/a;
	public String classNameSuper; // Lcom/b/a/b;
	
	public HeadEntry(SmaliEntry se, int offset){
		super(se, 1, offset);
	}
	
	public void add_content(String content) {
		// TODO Auto-generated method stub
		super.content = content;
	}

	public void close() {
		// TODO Auto-generated method stub
		super.id = classClass;
	}
	
	@Override
	public void RenameType(String classname_before, String classname_after){
		classClass = classClass.replace(classname_before, classname_after);
		content = content.replace(classname_before, classname_after);
		classNameSelf = classNameSelf.replace(classname_before, classname_after);

		super.id = classClass;
		super.content = content;
	}
}
