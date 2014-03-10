package com.zhangwei.ui.jlist;


import javax.swing.DefaultListModel;

import com.zhangwei.smali.api.CommonEntry;
import com.zhangwei.smali.api.SmaliEntry;

public class SmaliListDataModel extends DefaultListModel<CommonEntry> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 681316880209418688L;
	
    public SmaliListDataModel() {
        super();
        
    }
	
    public SmaliListDataModel(SmaliEntry entry) {
        super();

        addElement(entry.getHeader());
        
        for(CommonEntry item : entry.getFieldList()){
        	addElement(item);
        }
        
        for(CommonEntry item : entry.getMethodList()){
        	addElement(item);
        }

    }
    
    public void ChangeSmaliEntry(SmaliEntry newEntry){
    	removeAllElements();
    	
    	if(newEntry!=null){
            addElement(newEntry.getHeader());
            
            for(CommonEntry item : newEntry.getFieldList()){
            	addElement(item);
            }
            
            for(CommonEntry item : newEntry.getMethodList()){
            	addElement(item);
            }
    	}

    }

	public void Refresh(int selectIndex) {
		// TODO Auto-generated method stub
		super.fireContentsChanged(this, selectIndex, selectIndex);
	}
}
