package com.zhangwei.smali.api;

public abstract class CommonEntry {
	public String id;
	
	public SmaliEntry se;
	public int type; //1 head  2 field  3 head
	public String content;
	
	public int offset;
	
	public CommonEntry(SmaliEntry se, int type, int offset){
		this.se = se;
		this.type = type;
		this.offset = offset;
	}
	
	//.class public final Lcom/b/f/a;
	//.super Ljava/lang/Object;
	//.method static constructor <clinit>()V
	//.method public static final b(Lcom/b/f/a;)Lcom/b/f/a;	
	//.field public static final c:[I
	public void RenameType(String content_before, String content_after){
		content = content.replace(content_before, content_after);
	}
	
	public String toString(){
		return id;
	}
	
	public String getContent(){
		return content;
	}
}
