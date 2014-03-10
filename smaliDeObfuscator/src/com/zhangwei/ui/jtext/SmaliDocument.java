package com.zhangwei.ui.jtext;

import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import com.zhangwei.smali.api.SmaliEntry;
import com.zhangwei.ui.jlist.SmaliEntryChanged;

public class SmaliDocument extends PlainDocument  implements SmaliEntryChanged, Document{

	@Override
	public void EntryChanged(SmaliEntry newEntry) {
		// TODO Auto-generated method stub
		
	}

}
