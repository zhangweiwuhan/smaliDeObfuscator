package com.zhangwei.ui.jtree;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class FileTree {

    public static void main(String[] args) {
        new FileTree();
    }

    public FileTree() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        private DefaultTreeModel model;
        private JTree tree;

        public TestPane() {
            setLayout(new BorderLayout());

            tree = new JTree();
            File rootFile = new File(".");
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootFile);
            model = new DefaultTreeModel(root);

            tree.setModel(model);
            tree.setRootVisible(true);
            tree.setShowsRootHandles(true);

            add(new JScrollPane(tree));

            JButton load = new JButton("Load");
            add(load, BorderLayout.SOUTH);

            load.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
                    root.removeAllChildren();
                    model.reload();
                    File rootFile = (File) root.getUserObject();

                    addFiles(rootFile, model, root);

                    tree.expandPath(new TreePath(root));

                }
            });

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        protected void addFiles(File rootFile, DefaultTreeModel model, DefaultMutableTreeNode root) {

            for (File file : rootFile.listFiles()) {
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(file);
                model.insertNodeInto(child, root, root.getChildCount());
                if (file.isDirectory()) {
                    addFiles(file, model, child);
                }
            }

        }
    }
}
