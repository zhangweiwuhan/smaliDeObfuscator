package com.zhangwei.ui.jlist;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.DefaultListSelectionModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.zhangwei.smali.api.CommonEntry;
import com.zhangwei.smali.api.FieldEntry;
import com.zhangwei.smali.api.HeadEntry;
import com.zhangwei.smali.api.MethodEntry;
import com.zhangwei.smali.api.SmaliEntry;
import com.zhangwei.ui.jtext.SmaliMemberChanged;
import com.zhangwei.ui.jtree.SmaliLoader;
import com.zhangwei.ui.jtree.SmaliTree;
import com.zhangwei.ui.jtree.SmaliTreeModel;
import com.zhangwei.ui.jtree.TreePane;

public class ListPane extends JPanel implements ActionListener, SmaliEntryChanged, ListSelectionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5157608841700640203L;

    private static final String ACTION_KEY = "theJListAction";
	
	private JList<CommonEntry> jlist;
    private SmaliListDataModel listmodel;
    
    private SmaliEntryChanged JTextDataNotify;
    private SmaliMemberChanged JSmaliMemberNotify;

	private ListSelectionModel listSelectionModel;
	
	private CommonEntry selectedCommonEntry;
    
    public ListPane() {
    	super(new BorderLayout());
    	jlist = new JList<CommonEntry>();
    	listmodel = new SmaliListDataModel();
    	
    	listSelectionModel = jlist.getSelectionModel();
    	listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	listSelectionModel.addListSelectionListener(this);
    	
/*    	jlist.setSelectionModel(listSelectionModel);
    	jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);*/
    	jlist.setModel(listmodel);
    	
        add(new JScrollPane(jlist));
        selectedCommonEntry = null;
        
        Action actionListener = new AbstractAction() {
            /**
			 * 
			 */
			private static final long serialVersionUID = -2993268334156314079L;

			public void actionPerformed(ActionEvent actionEvent) {


			  @SuppressWarnings("unchecked")
			  JList<CommonEntry> source = (JList<CommonEntry>)actionEvent.getSource();
			  selectedCommonEntry = source.getSelectedValue();
			  int selectIndex = source.getSelectedIndex();
			  
			  if(selectedCommonEntry!=null){
				  String s = "NULL";
				  while(s.equals("NULL")){
					  if(selectedCommonEntry.type==1){ //head
						  HeadEntry he = (HeadEntry) selectedCommonEntry;
			              s = (String)JOptionPane.showInputDialog(
			                      ListPane.this,
			                      "Rename class head: " + he.classNameSelf,
			                      "Rename",
			                      JOptionPane.PLAIN_MESSAGE,
			                      null,
			                      null,
			                      he.classNameSelf);
					  }else if(selectedCommonEntry.type==2){ //field
						  FieldEntry fe = (FieldEntry) selectedCommonEntry;
			              s = (String)JOptionPane.showInputDialog(
			                      ListPane.this,
			                      "Rename class field" + fe.classFieldName,
			                      "Rename",
			                      JOptionPane.PLAIN_MESSAGE,
			                      null,
			                      null,
			                      fe.classFieldName);
					  }else if(selectedCommonEntry.type==3){ //method
						  MethodEntry me = (MethodEntry)selectedCommonEntry;
			              s = (String)JOptionPane.showInputDialog(
			                      ListPane.this,
			                      "Rename class method" + me.classConstructorName!=null?me.classConstructorName:me.classMethodName,
			                      "Rename",
			                      JOptionPane.PLAIN_MESSAGE,
			                      null,
			                      null,
			                      me.classConstructorName!=null?me.classConstructorName:me.classMethodName);
					  }
					  
                      if(s==null){
                    	  break;//cancel by user
                      }else if(s.equals("")){
                    	  s="NUll";
                          continue;
                      }
				  }

				  

	              System.out.println("Activated: after: " + s);
	              
                  if(s!=null){
                	  if(selectedCommonEntry.type==1){//head, nothing to do

                	  }else if(selectedCommonEntry.type==2){ //field
                		  FieldEntry fe = (FieldEntry) selectedCommonEntry;
                		  SmaliLoader.getInstance().renameField(selectedCommonEntry.se, fe, s);
                    	  listmodel.Refresh(selectIndex);
                	  }else if(selectedCommonEntry.type==3){ //method
                		  MethodEntry me = (MethodEntry)selectedCommonEntry;
                		  SmaliLoader.getInstance().renameMethod(selectedCommonEntry.se, me, s);
                    	  listmodel.Refresh(selectIndex);
                	  }
                	  

                  }
			  }

            }
          };
        
        KeyStroke F2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0 , true);
        ActionMap actionMap = jlist.getActionMap();
        actionMap.put(ACTION_KEY, actionListener);
        
        InputMap inputMap = jlist.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(F2, ACTION_KEY);
        jlist.setActionMap(actionMap);
    }
    
    public void setSmaliEntryChangedListener(SmaliEntryChanged listener){
    	JTextDataNotify = listener;
    }
    
    public void setSmaliMemberChangedListener(SmaliMemberChanged listener){
    	JSmaliMemberNotify = listener;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void EntryChanged(SmaliEntry newEntry) {
		// TODO Auto-generated method stub
		if(newEntry.isFile){
			listmodel.ChangeSmaliEntry(newEntry);
			
		}else{
			listmodel.ChangeSmaliEntry(null);
		}

		if(JTextDataNotify!=null){
			JTextDataNotify.EntryChanged(newEntry);
		}
		
	}

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if(listmodel!=null && listmodel.getSize()>0){
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
            if (!lsm.isSelectionEmpty()) {
                // Find out which indexes are selected.
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
            			CommonEntry item = listmodel.getElementAt(i);
            			selectedCommonEntry = item;
            			System.out.println("id:" + item.id + ", offset:" + item.offset);
            			if(JSmaliMemberNotify!=null){
            				JSmaliMemberNotify.MemberChanged(item.offset);
            			}
                    }
                }
            }else{
            	selectedCommonEntry = null;
            }
			

		}

	}
}
