package captureEasy.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import captureEasy.Library.Library;
import captureEasy.UI.ActionGUIParts.ActionPanel;
import captureEasy.UI.ActionGUIParts.ManageDocumentPanel;
import captureEasy.UI.ActionGUIParts.SavePanel;
import captureEasy.UI.ActionGUIParts.SettingsPanel;
import captureEasy.UI.ActionGUIParts.ViewPanel;

import java.awt.event.KeyEvent;

public class ActionGUI extends Library  implements ChangeListener,MouseListener,MouseMotionListener
{
	public static JDialog dialog;
	public final JPanel contentPanel = new JPanel();

	public JTabbedPane TabbledPanel;
	public boolean finish=false;
	public static final String PRE_HTML = "<html>"
			+ "<p style=\"text-align: center; "
			+ "margin-top: 10px;"
			+ "margin-bottom: 10px;"
			+ "margin-left: 1px;"
			+ "margin-right: 1px;"
			+ "width: 70px\">";
	public static final String POST_HTML = "</p></html>";
	public ActionPanel actionPanel;
	public SettingsPanel settingsPanel;
	public ManageDocumentPanel documentPanel;
	public ViewPanel viewPanel;
	public SavePanel savePanel;
	List<String> tabs;
	public static int xDialog,yDialog, xyDialog,xxDialog;
	public static boolean leaveControl=true;

	
	@SuppressWarnings({ })
	public ActionGUI(List<String> tabs)
	{
		this.tabs=tabs;
		System.out.println("JOO");
		leaveControl=false;
		dialog=new JDialog();
		dialog.setSize(new Dimension(575, 350));
		dialog.setFont(new Font("Dialog", 1, 20));
		dialog.setAlwaysOnTop(true);
		dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		dialog.setBounds(screensize.width / 2 - 300, screensize.height / 2 - 300, 575, 350);
		dialog.setUndecorated(true);
		dialog.setLocation(screensize.width / 2 - 300, screensize.height / 2 - 300);
		dialog.getContentPane().setLayout(new BorderLayout());
		InputMap im = dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = dialog.getRootPane().getActionMap();

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
		am.put("escape", new AbstractAction() {
			public static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(IsEmpty(TempFolder));
				if((getProperty(PropertyFilePath,"DocPath")!=null) && IsEmpty(TempFolder))
				{
					dialog.dispose();
					leaveControl=true;
				}
			}
		});
		contentPanel.setSize(new Dimension(575, 350));
		contentPanel.setBackground(new Color(127, 255, 212));
		this.contentPanel.setFont(new Font("Tahoma", 0, 18));
		this.contentPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		dialog.getContentPane().add(this.contentPanel, "Center");
		dialog.addMouseListener(this);
		dialog.addMouseMotionListener(this);
		{
			TabbledPanel = new JTabbedPane(JTabbedPane.LEFT);
			TabbledPanel.setSize(new Dimension(551, 324));
			TabbledPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			TabbledPanel.setBounds(12, 13, 551, 324);
			TabbledPanel.setBackground(new Color(255, 255, 255));
			TabbledPanel.setOpaque(true);
			TabbledPanel.setAutoscrolls(true);
			TabbledPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			TabbledPanel.setFont(new Font("Tahoma", Font.BOLD, 16));
			TabbledPanel.setPreferredSize(new Dimension(550, 260));
			TabbledPanel.addMouseListener(this);
			TabbledPanel.addChangeListener(this);
			TabbledPanel.addMouseMotionListener(this);
			contentPanel.setLayout(null);
			contentPanel.add(TabbledPanel);


			for(int i=0;i<tabs.size();i++)
			{
				if(tabs.get(i).equalsIgnoreCase("Save"))
				{
					savePanel=new SavePanel(TabbledPanel);
					TabbledPanel.addTab("Save", null,savePanel.SaveScrollPane, null);
					TabbledPanel.setTitleAt(i, PRE_HTML + "Save" + POST_HTML);
				}
				else if(tabs.get(i).equalsIgnoreCase("View"))
				{
					viewPanel=new ViewPanel(TabbledPanel);
					TabbledPanel.addTab("ViewImages", null, viewPanel.ViewScrollPane, null);
					TabbledPanel.setTitleAt(i, PRE_HTML + "View\nImages" + POST_HTML);
				}
				else if(tabs.get(i).equalsIgnoreCase("Document"))
				{	
					documentPanel=new ManageDocumentPanel(TabbledPanel);
					TabbledPanel.addTab("ManageDocument", null, documentPanel.DocumentScrollPane, null);
					TabbledPanel.setTitleAt(i, PRE_HTML + "Manage\nDocument" + POST_HTML);
				}
				else if(tabs.get(i).equalsIgnoreCase("Settings"))
				{
					settingsPanel=new SettingsPanel(TabbledPanel);
					TabbledPanel.addTab("Settings", null, settingsPanel.SettingsPane, null);
					TabbledPanel.setTitleAt(i, PRE_HTML + "Settings" + POST_HTML);
				}
				else
				{
					actionPanel=new ActionPanel(TabbledPanel);
					TabbledPanel.addTab("", null, actionPanel.ActionPanel, null);
					TabbledPanel.setTitleAt(i, PRE_HTML + "Action" + POST_HTML);
				}

			}
			TabbledPanel.setSelectedIndex(0);
		}

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		xDialog = arg0.getXOnScreen();
		yDialog = arg0.getYOnScreen();
		dialog.setLocation(xDialog - xxDialog, yDialog - xyDialog); 		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		xxDialog = e.getX();
		xyDialog = e.getY();
	}


	@Override
	public void mouseMoved(MouseEvent arg0) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		String tabName=TabbledPanel.getTitleAt(TabbledPanel.getSelectedIndex()).toString();
		if(tabName.contains("Save"))	
		{
			try{
			savePanel.textField_Filename.requestFocusInWindow();
			savePanel.rdbtnNewDoc.setEnabled(false);
			savePanel.btnDone.setEnabled(false);
			dialog.getRootPane().setDefaultButton(savePanel.btnDone);
		}catch(Exception e){}
		}
		else if(tabName.contains("View"))
		{

		}
		else if(tabName.contains("Manage"))
		{

		}
		else if(tabName.contains("Settings"))
		{
			System.out.println("okkkk");

			String path=getProperty(PropertyFilePath,"DocPath");
			settingsPanel.SettingsPane_DocFolderPanel_textField_DocDestFolder.setText(path);
			if(!"".equals(path))
			{
				settingsPanel.SettingsPane_Framepanel_ArrangeSS.setSelected(Boolean.valueOf(getProperty(PropertyFilePath,"ArrangeSSDatewise")));
				settingsPanel.comboBox_ImageFormat.setSelectedItem(getProperty(PropertyFilePath,"ImageFormat"));
				SettingsPanel.lblLocationx.setText("Location : ( "+getProperty(PropertyFilePath,"Xlocation")+" , "+getProperty(PropertyFilePath,"Ylocation")+" )");
			}
			settingsPanel.SettingsPane_Framepanel_AltPrtSc.setSelected(Boolean.valueOf(getProperty(PropertyFilePath,"PrtSCSS")));
			//dialog.getRootPane().setDefaultButton(this.SettingsPane_Btnpanel_SaveBtn);
		}
	}
}