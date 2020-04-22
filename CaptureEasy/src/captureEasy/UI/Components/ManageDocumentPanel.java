package captureEasy.UI.Components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;

import captureEasy.Resources.Library;
import captureEasy.UI.ActionGUI;
import captureEasy.UI.PopUp;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.MouseAdapter;

public class ManageDocumentPanel extends Library implements MouseListener,MouseMotionListener,TreeSelectionListener{

	public JPanel DocumentScrollPane;
	public static List<String> monthList=new ArrayList<String>();
	public static List<String> dayList=new ArrayList<String>();
	public static ArrayList<ArrayList<File>> Filelist=new ArrayList<ArrayList<File>>();
	File files;
	private FileSystemView fileSystemView;
	private Desktop desktop;
	private JTable table;
	private ListSelectionListener listSelectionListener;
	private DefaultTreeModel treeModel;
	private JTree tree;
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

			JPanel panel_View = new JPanel();
			panel_View.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			panel_View.setBounds(10, 57, 415, 245);
			DocumentScrollPane.add(panel_View);
			fileSystemView = FileSystemView.getFileSystemView();
			desktop = Desktop.getDesktop();

			listSelectionListener = new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent lse) {
					int row = table.getSelectionModel().getLeadSelectionIndex();
					//    setFileDetails( ((FileTableModel)table.getModel()).getFile(row) );
				}
			};

			// the File tree
			DefaultMutableTreeNode root = new DefaultMutableTreeNode();
			treeModel = new DefaultTreeModel(root);

			TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent tse){
					DefaultMutableTreeNode node =
							(DefaultMutableTreeNode)tse.getPath().getLastPathComponent();
					//  showChildren(node);
					//  setFileDetails((File)node.getUserObject());
				}
			};

			// show the file system roots.
			File[] roots = fileSystemView.getRoots();
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
			Dimension d = ScrollPane_Table.getPreferredSize();
			tree.addTreeSelectionListener(treeSelectionListener);
			// tree.setCellRenderer(new FileTreeCellRenderer());
			tree.expandRow(0);
		}
	}
	protected void addFiles(File rootFile, DefaultTreeModel model, DefaultMutableTreeNode root) {

		for (File file : rootFile.listFiles()) {
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(file.getName());
			child.add(new DefaultMutableTreeNode("blue"));
			model.insertNodeInto(child, root, rootFile.listFiles().length);
			if (file.isDirectory()) {
				addFiles(file, model, child);
			}
		}

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
}
