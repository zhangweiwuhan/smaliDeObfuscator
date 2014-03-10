package com.zhangwei.ui.jtree;

import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.zhangwei.smali.api.SmaliEntry;

public class SmaliTreeModel  implements TreeModel {
	
    private Vector<TreeModelListener> treeModelListeners = new Vector<TreeModelListener>();
	private SmaliEntry smaliRootEntry;

	public SmaliTreeModel(SmaliEntry root) {
		// TODO Auto-generated constructor stub
		this.smaliRootEntry = root;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		treeModelListeners.addElement(l);
	}
	
	
    public void ChangeRoot(Object newRoot) {
    	SmaliEntry oldRoot = smaliRootEntry;
        if (newRoot != null) {
        	smaliRootEntry = (SmaliEntry)newRoot;
        }
        fireTreeStructureChanged(oldRoot);
    }
    
    public void Refresh() {

        //fireTreeNodesChanged(smaliRootEntry);
        fireTreeStructureChanged(smaliRootEntry);
    }

	// //////////////Fire events //////////////////////////////////////////////

	/**
	 * The only event raised by this model is TreeStructureChanged with the root
	 * as path, i.e. the whole tree has changed.
	 */
	protected void fireTreeStructureChanged(SmaliEntry oldRoot) {
		int len = treeModelListeners.size();
		TreeModelEvent e = new TreeModelEvent(this, new Object[] { oldRoot });
		for (TreeModelListener tml : treeModelListeners) {
			tml.treeStructureChanged(e);
		}
	}

	protected void fireTreeNodesChanged(SmaliEntry oldRoot) {
		int len = treeModelListeners.size();
		TreeModelEvent e = new TreeModelEvent(this, new Object[] { oldRoot });
		for (TreeModelListener tml : treeModelListeners) {
			tml.treeNodesChanged(e);
		}
	}
	
	@Override
	public Object getChild(Object parent, int index) {
		// TODO Auto-generated method stub
		SmaliEntry p = (SmaliEntry)parent;
		return p.getChildAt(index);
	}

	@Override
	public int getChildCount(Object parent) {
		// TODO Auto-generated method stub
		SmaliEntry p = (SmaliEntry)parent;
		return p.getChildCount();
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		// TODO Auto-generated method stub
		SmaliEntry p = (SmaliEntry)parent;
		return p.getIndexOfChild((SmaliEntry)child);
	}

	@Override
	public Object getRoot() {
		// TODO Auto-generated method stub
		return smaliRootEntry;
	}

	@Override
	public boolean isLeaf(Object node) {
		// TODO Auto-generated method stub
		SmaliEntry p = (SmaliEntry)node;
		return p.isFile();
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		treeModelListeners.removeElement(l);
	}

	@Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        System.out.println("*** valueForPathChanged : "
                           + path + " --> " + newValue);
    }

}
