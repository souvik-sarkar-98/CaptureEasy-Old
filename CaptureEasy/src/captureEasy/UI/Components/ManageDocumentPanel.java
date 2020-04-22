package captureEasy.UI.Components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import captureEasy.Resources.Library;
import captureEasy.UI.ActionGUI;
import captureEasy.UI.PopUp;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import java.awt.event.MouseAdapter;

public class ManageDocumentPanel extends Library implements MouseListener,MouseMotionListener,TreeSelectionListener{

	

    /** currently selected File. */
    public File currentFile;

    /** Main GUI container */
    public JPanel gui;

    

    /** Directory listing */
    /** Table model for File[]. */
    public FileTableModel fileTableModel;
    public boolean cellSizesSet = false;
    public int rowIconPadding = 6;

    /* File controls. */
    public JButton openFile;
    public JButton printFile;
    public JButton editFile;

    /* File details. */
    public JLabel fileName;
    public JTextField path;
    public JLabel date;
    public JLabel size;
    public JCheckBox readable;
    public JCheckBox writable;
    public JCheckBox executable;
    public JRadioButton isDirectory;
    public JRadioButton isFile;

    
	public JPanel DocumentScrollPane;
	public static List<String> monthList=new ArrayList<String>();
	public static List<String> dayList=new ArrayList<String>();
	public static ArrayList<ArrayList<File>> Filelist=new ArrayList<ArrayList<File>>();
	File files;
	public FileSystemView fileSystemView;
	public Desktop desktop;
	public JTable table;
	public ListSelectionListener listSelectionListener;
	public DefaultTreeModel treeModel;
	public JTree tree;
	public JPanel panel_View;
	public ManageDocumentPanel(JTabbedPane TabbledPanel)
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
		}
		{
			DocumentScrollPane = new JPanel();
			DocumentScrollPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			DocumentScrollPane.setBackground(Color.WHITE);
			DocumentScrollPane.setSize(new Dimension(437, 315));
			DocumentScrollPane.addMouseListener(this);
			DocumentScrollPane.addMouseMotionListener(this);
			DocumentScrollPane.setLayout(null);

			JPanel panel_Selection = new JPanel();
			panel_Selection.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			panel_Selection.setBounds(10, 10, 372, 40);
			DocumentScrollPane.add(panel_Selection);
			panel_Selection.setLayout(null);

			JRadioButton rdbtnViewDocuments = new JRadioButton("View Documents");
			rdbtnViewDocuments.setSelected(true);
			rdbtnViewDocuments.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rdbtnViewDocuments.setSelected(true);// temporary
				}
			});
			rdbtnViewDocuments.setFont(new Font("Tahoma", Font.BOLD, 16));
			rdbtnViewDocuments.setBounds(15, 8, 163, 25);
			panel_Selection.add(rdbtnViewDocuments);

			JRadioButton rdbtnNewRadioButton = new JRadioButton("Search Documents");
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new PopUp("Information","info","Sorry !! This facility is currently  unavailable.","Ok, Fine","").setVisible(true);
					rdbtnNewRadioButton.setSelected(false);
				}
			});
			rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.BOLD, 16));
			rdbtnNewRadioButton.setBounds(185, 8, 180, 25);
			panel_Selection.add(rdbtnNewRadioButton);

			panel_View = new JPanel();
			panel_View.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			panel_View.setBounds(10, 57, 415, 245);
			DocumentScrollPane.add(panel_View);
			fileSystemView = FileSystemView.getFileSystemView();
			desktop = Desktop.getDesktop();

			listSelectionListener = new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent lse) {
					int row = table.getSelectionModel().getLeadSelectionIndex();
					    setFileDetails( ((FileTableModel)table.getModel()).getFile(row) );
				}
			};
			File RFile=new File (getProperty(PropertyFilePath,"DocPath"));
			// the File tree
			DefaultMutableTreeNode root = new DefaultMutableTreeNode();
			treeModel = new DefaultTreeModel(root);

			TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent tse){
					DefaultMutableTreeNode node =(DefaultMutableTreeNode)tse.getPath().getLastPathComponent();
					showChildren(node);
					  setFileDetails((File)node.getUserObject());
				
				}
			};

			// show the file system roots.
			 File[] roots =new SingleRootFileSystemView(RFile).getRoots();
			//File[] roots = fileSystemView.getFiles(RFile, false);
			for (File fileSystemRoot : roots) {
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);
				root.add( node );
				File[] files = fileSystemView.getFiles(fileSystemRoot, true);
				for (File file : files) {
					if (file.isDirectory()) {
						node.add(new DefaultMutableTreeNode(file));
					}
				}
				//
			}
			
			

			// details for a File
			JPanel fileMainDetails = new JPanel(new BorderLayout(4,2));
			fileMainDetails.setBorder(new EmptyBorder(0,6,0,6));

			JPanel fileDetailsLabels = new JPanel(new GridLayout(0,1,2,2));
			fileMainDetails.add(fileDetailsLabels, BorderLayout.WEST);
			panel_View.setLayout(null);

			JSplitPane splitPane_View = new JSplitPane();
			splitPane_View.setBounds(5, 5, 405, 235);
			splitPane_View.setDividerLocation(120);
			panel_View.add(splitPane_View);

			JScrollPane scrollPane_Tree = new JScrollPane();
			splitPane_View.setLeftComponent(scrollPane_Tree);

			tree = new JTree(treeModel);
			tree.setRootVisible(false);
			scrollPane_Tree.setViewportView(tree);
			tree.setShowsRootHandles(true);
			tree.setVisibleRowCount(15);
			tree.setRowHeight(20);
			tree.setFont(new Font("Tahoma", Font.PLAIN, 16));
			tree.setLayout(null);
			

			table = new JTable();
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setAutoCreateRowSorter(true);
			table.setShowVerticalLines(false);
			table.getSelectionModel().addListSelectionListener(listSelectionListener);
			JScrollPane ScrollPane_Table = new JScrollPane(table);
			splitPane_View.setRightComponent(ScrollPane_Table);
			
			JPanel panel_Exit = new JPanel();
			panel_Exit.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			panel_Exit.setBounds(390, 10, 35, 40);
			DocumentScrollPane.add(panel_Exit);
			panel_Exit.setLayout(null);
			
			JLabel lblCross = new JLabel("");
			lblCross.setLocation(8, 10);
			panel_Exit.add(lblCross);
			lblCross.setSize(new Dimension(20, 20));

			lblCross.setToolTipText("Exit");
			try {
				lblCross.setIcon(new ImageIcon(ImageIO.read(new File("Icons\\Btn_exit.png")).getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH)));
			} catch (IOException e) {
				
			}
			lblCross.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if(lblCross.isEnabled())
					{
						ActionGUI.dialog.dispose();
						ActionGUI.leaveControl=true;
					}
				}
			});
		//	Dimension d = ScrollPane_Table.getPreferredSize();
			tree.addTreeSelectionListener(treeSelectionListener);
			tree.setCellRenderer(new FileTreeCellRenderer());
			
			JPanel panel = new JPanel();
			panel.setBounds(145, 136, 267, 91);
			DocumentScrollPane.add(panel);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Loading ...");
			lblNewLabel.setBounds(100, 25, 66, 16);
			panel.add(lblNewLabel);
			
			JProgressBar progressBar = new JProgressBar();
			progressBar.setBounds(28, 45, 211, 14);
			panel.add(progressBar);
			tree.expandRow(0);
		}
		showRootFile();
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		ActionGUI.xDialog = arg0.getXOnScreen();
		ActionGUI.yDialog = arg0.getYOnScreen();
		ActionGUI.dialog.setLocation(ActionGUI.xDialog - ActionGUI.xxDialog, ActionGUI.yDialog - ActionGUI.xyDialog); 		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		ActionGUI.xxDialog = e.getX();
		ActionGUI.xyDialog = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		// TODO Auto-generated method stub
		String node = arg0.getNewLeadSelectionPath().getLastPathComponent().toString();
		System.out.println("KK"+node);

	}
	
	
    public void showRootFile() {
        // ensure the main files are displayed
        tree.setSelectionInterval(0,0);
    }

    public TreePath findTreePath(File find) {
        for (int ii=0; ii<tree.getRowCount(); ii++) {
            TreePath treePath = tree.getPathForRow(ii);
            Object object = treePath.getLastPathComponent();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)object;
            File nodeFile = (File)node.getUserObject();

            if (nodeFile==find) {
                return treePath;
            }
        }
        // not found!
        return null;
    }

    public void showErrorMessage(String errorMessage, String errorTitle) {
        JOptionPane.showMessageDialog(
            panel_View,
            errorMessage,
            errorTitle,
            JOptionPane.ERROR_MESSAGE
            );
    }

    public void showThrowable(Throwable t) {
        t.printStackTrace();
        JOptionPane.showMessageDialog(
            panel_View,
            t.toString(),
            t.getMessage(),
            JOptionPane.ERROR_MESSAGE
            );
        panel_View.repaint();
    }

    /** Update the table on the EDT */
    public void setTableData(final File[] files) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (fileTableModel==null) {
                    fileTableModel = new FileTableModel();
                    table.setModel(fileTableModel);
                }
                table.getSelectionModel().removeListSelectionListener(listSelectionListener);
                fileTableModel.setFiles(files);
                table.getSelectionModel().addListSelectionListener(listSelectionListener);
                if (!cellSizesSet) {
                    Icon icon = fileSystemView.getSystemIcon(files[0]);

                    // size adjustment to better account for icons
                    table.setRowHeight( icon.getIconHeight()+rowIconPadding );

                    setColumnWidth(0,-1);
                    setColumnWidth(1,-5);
                    setColumnWidth(2,-1);
                    setColumnWidth(3,60);
                    table.getColumnModel().getColumn(3).setMaxWidth(120);
                    setColumnWidth(4,-1);
                    setColumnWidth(5,-1);
                    setColumnWidth(6,-1);
                    setColumnWidth(7,-1);
                    setColumnWidth(8,-1);
                    setColumnWidth(9,-1);

                    cellSizesSet = true;
                }
            }
        });
    }

    public void setColumnWidth(int column, int width) {
        TableColumn tableColumn = table.getColumnModel().getColumn(column);
        if (width<0) {
            // use the preferred width of the header..
            JLabel label = new JLabel( (String)tableColumn.getHeaderValue() );
            Dimension preferred = label.getPreferredSize();
            // altered 10->14 as per camickr comment.
            width = (int)preferred.getWidth()+14;
        }
        tableColumn.setPreferredWidth(width);
        tableColumn.setMaxWidth(width);
        tableColumn.setMinWidth(width);
    }

    /** Add the files that are contained within the directory of this node.
    Thanks to Hovercraft Full Of Eels for the SwingWorker fix. */
    public void showChildren(final DefaultMutableTreeNode node) {
        tree.setEnabled(false);
       // progressBar.setVisible(true);
       // progressBar.setIndeterminate(true);

        SwingWorker<Void, File> worker = new SwingWorker<Void, File>() {
            @Override
            public Void doInBackground() {
                File file = (File) node.getUserObject();
                if (file.isDirectory()) {
                    File[] files = fileSystemView.getFiles(file, true); //!!
                    if (node.isLeaf()) {
                        for (File child : files) {
                            if (child.isDirectory()) {
                                publish(child);
                            }
                        }
                    }
                    setTableData(files);
                }
                return null;
            }

            @Override
            protected void process(List<File> chunks) {
                for (File child : chunks) {
                    node.add(new DefaultMutableTreeNode(child));
                }
            }

            @Override
            protected void done() {
               // progressBar.setIndeterminate(false);
               // progressBar.setVisible(false);
                tree.setEnabled(true);
            }
        };
        worker.execute();
    }

    /** Update the File details view with the details of this File. */
    public void setFileDetails(File file) {
   System.out.println(file);
        currentFile = file;
       

        

        panel_View.repaint();
    }
    }

/** A TableModel to hold File[]. */
class FileTableModel extends AbstractTableModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public File[] files;
    public FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    public String[] columns = {
        "Icon",
        "File",
        "Path/name",
        "Size",
        "Last Modified",
        "R",
        "W",
        "E",
        "D",
        "F",
    };

    FileTableModel() {
        this(new File[0]);
    }

    FileTableModel(File[] files) {
        this.files = files;
    }

    public Object getValueAt(int row, int column) {
        File file = files[row];
        switch (column) {
            case 0:
                return fileSystemView.getSystemIcon(file);
            case 1:
                return fileSystemView.getSystemDisplayName(file);
            case 2:
                return file.getPath();
            case 3:
                return file.length();
            case 4:
                return file.lastModified();
            case 5:
                return file.canRead();
            case 6:
                return file.canWrite();
            case 7:
                return file.canExecute();
            case 8:
                return file.isDirectory();
            case 9:
                return file.isFile();
            default:
                System.err.println("Logic Error");
        }
        return "";
    }

    public int getColumnCount() {
        return columns.length;
    }

    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0:
                return ImageIcon.class;
            case 3:
                return Long.class;
            case 4:
                return Date.class;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return Boolean.class;
        }
        return String.class;
    }

    public String getColumnName(int column) {
        return columns[column];
    }

    public int getRowCount() {
        return files.length;
    }

    public File getFile(int row) {
        return files[row];
    }

    public void setFiles(File[] files) {
        this.files = files;
        fireTableDataChanged();
    }
}



/** A TreeCellRenderer for a File. */
class FileTreeCellRenderer extends DefaultTreeCellRenderer {

    /**
	 * 
	 */
	public static final long serialVersionUID = 1L;

	public FileSystemView fileSystemView;

    public JLabel label;

    FileTreeCellRenderer() {
        label = new JLabel();
        label.setOpaque(true);
        fileSystemView = FileSystemView.getFileSystemView();
    }

    @Override
    public Component getTreeCellRendererComponent(
        JTree tree,
        Object value,
        boolean selected,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        File file = (File)node.getUserObject();
        label.setIcon(fileSystemView.getSystemIcon(file));
        label.setText(fileSystemView.getSystemDisplayName(file));
        ///label.setToolTipText(file.getPath());

        if (selected) {
            label.setBackground(backgroundSelectionColor);
            label.setForeground(textSelectionColor);
        } else {
            label.setBackground(backgroundNonSelectionColor);
            label.setForeground(textNonSelectionColor);
        }

        return label;
    }
}