package com.zhangwei.ui.jtree;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import com.zhangwei.smali.api.SmaliEntry;

public class SmaliTree extends JTree {

	SmaliTreeModel model;
	
    public SmaliTree() {
        //super(new SmaliTreeModel(graphNode));
        
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();

		renderer.setLeafIcon(new ImageIcon("leafIcon.png"));
		renderer.setOpenIcon(new ImageIcon("package.png"));
		renderer.setClosedIcon(new ImageIcon("package.png"));

        setCellRenderer(renderer);
    }
    
    public void changeRoot(SmaliEntry newRoot){
    	((SmaliTreeModel)this.getModel()).ChangeRoot(newRoot);
    }
}
