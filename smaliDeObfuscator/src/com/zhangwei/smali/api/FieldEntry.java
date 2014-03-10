package com.zhangwei.smali.api;

public class FieldEntry extends CommonEntry {
	//classField = optPadding dirField padding *(accessMode padding) classFieldName COLON classFieldType optPadding [EQ optPadding value] optPadding CRLF [dirEndField optPadding CRLF];
	//classField = *accessMode classFieldName classFieldType
	public FieldEntry(SmaliEntry se, int offset){
		super(se, 2, offset);
	}
	
	
	//eg: .field private c:Ljava/lang/StringBuilder;
	public String classFieldName; // c
	public String classFieldType; // Ljava/lang/StringBuilder;
	
	public void close() {
		// TODO Auto-generated method stub
		super.id = classFieldName + "_" + classFieldType;
	}
	
	@Override
	public void RenameType(String classname_before, String classname_after){
		content = content.replace(classname_before, classname_after);
		classFieldType = classFieldType.replace(classname_before, classname_after);

		super.id = classFieldName + "_" + classFieldType;
		super.content = content;
	}

	public void RenameName(String className, String oldFieldName, String newFieldName, String classFieldType) {
		// TODO Auto-generated method stub
		content = content.replace(" " + oldFieldName + ":" + classFieldType, " " + newFieldName + ":" + classFieldType);
		classFieldName = newFieldName;

		super.id = classFieldName + "_" + classFieldType;
		super.content = content;
		
	}
}
