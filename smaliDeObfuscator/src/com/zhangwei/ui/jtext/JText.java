package com.zhangwei.ui.jtext;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.Document;


import com.zhangwei.smali.api.SmaliEntry;
import com.zhangwei.ui.jlist.SmaliEntryChanged;

public class JText extends JPanel implements SmaliEntryChanged, SmaliMemberChanged{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8123960294134045973L;
	
	private JTextArea  jtextArea;
	private SmaliDocument doc;
	JScrollPane scrollPane;
	JScrollBar verticalScrollBar;//= scrollPane.getVerticalScrollBar();
	
	public JText() {
    	super(new BorderLayout());
		jtextArea = new JTextArea();
		doc = new SmaliDocument();
		
		jtextArea.setDocument(doc);
		
		scrollPane = new JScrollPane(jtextArea);
		verticalScrollBar = scrollPane.getVerticalScrollBar();
		
        add(scrollPane);
	}

	@Override
	public void EntryChanged(SmaliEntry newEntry) {
		// TODO Auto-generated method stub
		if(newEntry.isFile()){
			jtextArea.setText(newEntry.getFileContent());
			verticalScrollBar.setValue(0);
		}else{
			jtextArea.setText(null);
		}

	}
	
	@Override
	public void MemberChanged(int offset) {
		// TODO Auto-generated method stub
		String content = jtextArea.getText();
		if(content!=null){

			float percent = (float)offset / content.length();
			verticalScrollBar.setValue((int) (percent*verticalScrollBar.getMaximum()));

		}else{
			verticalScrollBar.setValue(0);
		}

	}
	
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

}
