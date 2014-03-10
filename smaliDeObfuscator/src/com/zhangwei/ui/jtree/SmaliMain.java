package com.zhangwei.ui.jtree;

import javax.swing.*;
import javax.swing.filechooser.FileView;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.zhangwei.ui.JavaFileView;
import com.zhangwei.ui.jlist.ListPane;
import com.zhangwei.ui.jtext.JText;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

public class SmaliMain {
	public static final String AUTO_RENAME = "Auto-rename";
	public static final String AUTO_PUBLIC = "Auto-public";
	public static final String CLOSE = "Close";
	public static final String OPEN = "Open";
	
	static JFrame frame;

	public static void main(final String args[]) {

		// MyParser.Paser_dir(args[0]);
		Runnable runner = new Runnable() {
			public void run() {
				try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
				
				String title = ("JTree Sample");
				frame = new JFrame(title);

				TreePane treePane = new TreePane();

				JMenuBar menuBar = new JMenuBar();
				JMenu fileMenu = new JMenu("File");
				fileMenu.setLayout(new GridLayout(3, 3));
				fileMenu.setMnemonic(KeyEvent.VK_F);
				menuBar.add(fileMenu);

				// File->Open, O - Mnemonic
				JMenuItem openMenuItem = new JMenuItem(OPEN, KeyEvent.VK_O);
				openMenuItem.addActionListener(treePane);
				fileMenu.add(openMenuItem);
				
		        // Separator
		        fileMenu.addSeparator();
		        
				// File->Open, O - Mnemonic
				JMenuItem autoMenuItem = new JMenuItem( AUTO_RENAME, KeyEvent.VK_A);
				autoMenuItem.addActionListener(treePane);
				fileMenu.add(autoMenuItem);
				
		        // Separator
		        fileMenu.addSeparator();
		        
				// File->Open, O - Mnemonic
				JMenuItem publicMenuItem = new JMenuItem( AUTO_PUBLIC, KeyEvent.VK_U);
				publicMenuItem.addActionListener(treePane);
				fileMenu.add(publicMenuItem);
				
		        // Separator
		        fileMenu.addSeparator();
				
		        // File->Close, C - Mnemonic
		        JMenuItem closeMenuItem = new JMenuItem(CLOSE, KeyEvent.VK_C);
		        closeMenuItem.addActionListener(treePane);
		        fileMenu.add(closeMenuItem);

				// UIManager.put("Tree.leafIcon", new
				// ImageIcon("leafIcon.png"));
				// UIManager.put("Tree.closedIcon", new
				// ImageIcon("package.png"));
				// UIManager.put("Tree.openIcon", new ImageIcon("package.png"));
				UIManager.put("Tree.collapsedIcon", new ImageIcon("collapsedIcon.png"));
				UIManager.put("Tree.expandedIcon", new ImageIcon("expandedIcon.png"));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				JPanel jp = new JPanel(new GridBagLayout());
		        addComponent(jp, treePane, 0, 0, 1, 0,
		                GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		        
		        ListPane listPane = new ListPane();
		        listPane.setVisible(true);
		        treePane.addSmaliEntryChangedListener(listPane);
		        
		        JText textPane = new JText();
		        textPane.setVisible(true);
		        
		        treePane.addSmaliEntryChangedListener(textPane);
		        listPane.setSmaliMemberChangedListener(textPane);

		        addComponent(jp, listPane, 1, 0, 1, 0,
		                GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		        
		        addComponent(jp, textPane, 2, 0, 2, 0,
		                GridBagConstraints.CENTER, GridBagConstraints.BOTH);
				
				frame.add(jp, BorderLayout.CENTER);
//
		        frame.add(menuBar, BorderLayout.NORTH);

				frame.setSize(600, 600);
				frame.setLocation(650, 20);
				frame.setVisible(true);
			}
		};
		
		
		EventQueue.invokeLater(runner);
	}
	
	private static final Insets insets = new Insets(0,0,0,0);
	
	  private static void addComponent(Container container, Component component,
		      int gridx, int gridy, int gridwidth, int gridheight, int anchor,
		      int fill) {
		    GridBagConstraints gbc = new GridBagConstraints(gridx, gridy,
		      gridwidth, gridheight, 1.0, 1.0, anchor, fill, insets, 0, 0);
		    container.add(component, gbc);
		  }

}
