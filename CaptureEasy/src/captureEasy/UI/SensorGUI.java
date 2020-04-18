package captureEasy.UI;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import captureEasy.Resources.Library;
import captureEasy.Resources.SharedRepository;
import captureEasy.UI.Components.SettingsPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.FlowLayout;
import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.JOptionPane;

import java.awt.Toolkit;
import java.awt.Font;

public class SensorGUI extends Library{

	/**
	 * 
	 */
	public static int xx,xy,x,y;
	JPopupMenu popupMenu;
	public static JFrame frame;
	File saveicon=new File("Icons/save.png");
	File viewicon=new File("Icons/view.png");
	File documenticon=new File("Icons/document.png");
	File settingicon=new File("Icons/settings.png");
	File powericon=new File("Icons/power.png");
	File playicon=new File("Icons/play.png");
	File pauseicon=new File("Icons/pause.png");
	File deleteicon=new File("Icons/delete.png");
	File menuicon = new File("Icons/menu.png");
	public JLabel label_delete;
	public JLabel lebel_Power;
	public JLabel Label_Pause ;
	public JLabel label_Document ;
	public JLabel label_Save;
	public JLabel label_View;
	ActionGUI act;
	public JLabel label_Settings;


	Dimension size= new Dimension(50,50);
	public JLabel label_Menu;
	public static JLabel label_Count;
	protected int c;
	public JPanel sensor_panel;
	public static boolean clickable=true;
	private JPanel Main_panel = new JPanel();



	public SensorGUI()
	{
		try{frame.dispose();}catch(Exception e){}

		frame=new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.setUndecorated(true);
		frame.getContentPane().add(Main_panel);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Icons/desktopIcon.png"));
		frame.setSize(new Dimension(54, 110));
		frame.setAlwaysOnTop(true);

		if(getProperty(PropertyFilePath,"Xlocation")==null ||getProperty(PropertyFilePath,"Ylocation")==null || getProperty(PropertyFilePath,"Xlocation").equals("") || getProperty(PropertyFilePath,"Ylocation").equals(""))
		{
			frame.setLocation(screensize.width-160,screensize.height/2+100);
		}
		else
		{
			frame.setLocation(Integer.parseInt(getProperty(PropertyFilePath,"Xlocation")),Integer.parseInt(getProperty(PropertyFilePath,"Ylocation")));
			frame.setAlwaysOnTop(true);
		}

		frame.setBackground(new Color(0,0,0,0));
		frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				xx = e.getX();
				xy = e.getY();

			}
		});
		frame.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {

				x = arg0.getXOnScreen()- xx;
				y = arg0.getYOnScreen()- xy;
				try{SettingsPanel.lblLocationx.setText("Location : ( "+x+" , "+y+" )");}catch(Exception e){}
				frame.setLocation(x , y );  
			}
		});

		Main_panel.setBounds(0, 0, 54, 110);
		Main_panel.setBackground(Color.WHITE);
		Main_panel.setBorder(null);
		Main_panel.setLayout(null);
		Main_panel.setBackground(new Color(0,0,0,0));


		JPanel button_panel = new JPanel();
		button_panel.setBorder(null);
		button_panel.setBackground(Color.WHITE);
		button_panel.setBounds(0, 115, 54, 438);
		Main_panel.add(button_panel);
		button_panel.setLayout(null);
		button_panel.setBackground(new Color(0,0,0,0));

		Label_Pause = new JLabel();
		Label_Pause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if(Label_Pause.getToolTipText().equalsIgnoreCase("Click Here to Pause"))
				{
					try{
						label_Menu.setEnabled(false);
						Label_Pause.setIcon(new ImageIcon(ImageIO.read(playicon).getScaledInstance(size.width,size.height, java.awt.Image.SCALE_SMOOTH)));
						Label_Pause.setToolTipText("Click Here to Resume");
						SharedRepository.loopControl=false;
					}catch (IOException e3) {Label_Pause.setText("Play");logError(e3,"Exception in Icon loading: Image "+playicon.getPath()+" Not Available");}
				}
				else
				{
					try{	
						label_Menu.setEnabled(true);
						Label_Pause.setIcon(new ImageIcon(ImageIO.read(pauseicon).getScaledInstance(size.width,size.height, java.awt.Image.SCALE_SMOOTH)));
						Label_Pause.setToolTipText("Click Here to Pause");
						SharedRepository.loopControl=true;
						resetClipboard(getProperty(ClipBoardDataFilePath,"ClipTextData"));
					}catch (IOException e3) {Label_Pause.setText("Pause"); logError(e3,"Exception in Icon loading: Image "+pauseicon.getPath()+" Not Available");}
				}
			}
		});
		Label_Pause.setToolTipText("Click Here to Pause");
		Label_Pause.setLocation(1, 55);
		Label_Pause.setSize(new Dimension(50, 50));
		Label_Pause.setBackground(new Color(0,0,0,0));
		Label_Pause.setBackground(Color.WHITE);
		Label_Pause.setSize(50,50);
		button_panel.add(Label_Pause);
		try {
			Label_Pause.setIcon(new ImageIcon(ImageIO.read(pauseicon).getScaledInstance(size.width,size.height, java.awt.Image.SCALE_SMOOTH)));
		} catch (IOException e1) {
			Label_Pause.setText("Pause");logError(e1,"Exception in Icon loading: Image "+pauseicon.getPath()+" Not Available");

		}


		lebel_Power = new JLabel();
		lebel_Power.setBounds(2, 0, 50, 50);
		button_panel.add(lebel_Power);
		lebel_Power.setBackground(new Color(0,0,0,0));
		lebel_Power.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(ActionGUI.leaveControl)
				{
					int dec=JOptionPane.showConfirmDialog(null, "Do you want to exit the application?\n", "Exit Application",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if(dec==0)
					{
						frame.dispose();
						try{ActionGUI.dialog.dispose();}catch(Exception e5){}
						SharedRepository.stopThread=true;
						try {
							GlobalScreen.unregisterNativeHook();
						} catch (NativeHookException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				}
			}
		});
		lebel_Power.setBackground(Color.WHITE);
		lebel_Power.setBorder(null);
		lebel_Power.setToolTipText("Click here to Minimize or Close");
		try{
			lebel_Power.setIcon(new ImageIcon(ImageIO.read(powericon).getScaledInstance(size.width,size.height, java.awt.Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			lebel_Power.setText("Close");logError(e,"Exception in Icon loading: Image "+powericon.getPath()+" Not Available");
		}




		label_Document = new JLabel();
		label_Document.setBounds(1, 275, 50, 50);
		button_panel.add(label_Document);
		label_Document.setBackground(new Color(0,0,0,0));
		label_Document.setToolTipText("Click here to manage documents");
		label_Document.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(ActionGUI.leaveControl)
				{
					List<String> tabs=new ArrayList<String>();
					tabs.add("Document");
					tabs.add("Save");
					tabs.add("View");
					tabs.add("Settings");
					frame.setAlwaysOnTop(false);
					new ActionGUI(tabs);
					ActionGUI.dialog.setVisible(true);
				}
			}
		});
		try {
			label_Document.setIcon(new ImageIcon(ImageIO.read(documenticon).getScaledInstance(size.width,size.height, java.awt.Image.SCALE_SMOOTH)));	
		} catch (IOException e1) {
			label_Document.setText("Documents");logError(e1,"Exception in Icon loading: Image "+documenticon.getPath()+" Not Available");
		}

		label_View = new JLabel("");
		label_View.setBounds(1, 220, 50, 50);
		button_panel.add(label_View);
		label_View.setBackground(new Color(0,0,0,0));
		label_View.setToolTipText("Click here to view screenshot");
		label_View.setPreferredSize(new Dimension(50, 50));
		label_View.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(ActionGUI.leaveControl)
				{
					List<String> tabs=new ArrayList<String>();
					tabs.add("View");
					tabs.add("Save");
					tabs.add("Document");
					tabs.add("Settings");
					frame.setAlwaysOnTop(false);
					new ActionGUI(tabs);	

					ActionGUI.dialog.setVisible(true);
				}
			}
		});
		try {
			label_View.setIcon(new ImageIcon(ImageIO.read(viewicon).getScaledInstance(size.width,size.height, java.awt.Image.SCALE_SMOOTH)));

		} catch (IOException e1) {
			label_View.setText("View");logError(e1,"Exception in Icon loading: Image "+viewicon.getPath()+" Not Available");
		}


		label_Save = new JLabel();
		label_Save.setBounds(1, 165, 50, 50);
		button_panel.add(label_Save);
		label_Save.setBackground(new Color(0,0,0,0));
		label_Save.setToolTipText("Click here to Save");
		label_Save.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(ActionGUI.leaveControl)
				{
					List<String> tabs=new ArrayList<String>();
					tabs.add("Save");
					tabs.add("View");
					tabs.add("Document");
					tabs.add("Settings");
					frame.setAlwaysOnTop(false);
					ActionGUI act=new ActionGUI(tabs);
					ActionGUI.dialog.setVisible(true);
					act.savePanel.textField_Filename.requestFocusInWindow();
					act.savePanel.rdbtnNewDoc.setEnabled(false);
					act.savePanel.btnDone.setEnabled(false);
				}
			}
		});
		try{
			label_Save.setIcon(new ImageIcon(ImageIO.read(saveicon).getScaledInstance(size.width,size.height, java.awt.Image.SCALE_SMOOTH)));

		} catch (IOException e1) {
			label_Save.setText("Save");logError(e1,"Exception in Icon loading: Image "+saveicon.getPath()+" Not Available");
		}


		label_delete = new JLabel();
		label_delete.setBounds(1, 110, 50, 50);
		button_panel.add(label_delete);
		label_delete.setBackground(new Color(0,0,0,0));
		label_delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(ActionGUI.leaveControl)
				{
					int dec=JOptionPane.showConfirmDialog(null, "Are you sure that you want to delete all screenshots?", "Delete screenshots",JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
					if(dec==0)
					{
						Library.c=0;
						updateProperty(TempFilePath,"TempPath",createFolder(System.getProperty("user.dir")+"/CaptureEasy/Temp/"+new Random().nextInt(1000000000)));
					}
				}
			}
		});
		label_delete.setToolTipText("Click Here to Delete");

		try{
			label_delete.setIcon(new ImageIcon(ImageIO.read(deleteicon).getScaledInstance(size.width,size.height, java.awt.Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			label_delete.setText("Delete");logError(e,"Exception in Icon loading: Image "+deleteicon.getPath()+" Not Available");
		}


		sensor_panel = new JPanel();
		//sensor_panel.setBackground(Color.RED);
		sensor_panel.setBackground(new Color(51, 255, 153));
		sensor_panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				x = arg0.getXOnScreen()-xx;
				y = arg0.getYOnScreen()-xy;
				try{SettingsPanel.lblLocationx.setText("Location : ( "+x+" , "+y+" )");}catch(Exception e){}
				frame.setLocation(x, y);
			}
		});
		sensor_panel.setToolTipText("Click here to take screenshots");
		sensor_panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//label_Count.setText(""+(++c)+"");
				if(ActionGUI.leaveControl)
				{
					captureScreen();
				}

			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				xx=arg0.getX();
				xy=arg0.getY();
			}
		});
		sensor_panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		sensor_panel.setBounds(0, 0, 54, 50);
		Main_panel.add(sensor_panel);
		sensor_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		label_Count = new JLabel();
		sensor_panel.add(label_Count);
		label_Count.setFont(new Font("Tahoma", Font.BOLD, 20));

		label_Menu = new JLabel();
		label_Menu.setBackground(Color.WHITE);
		label_Menu.setBounds(2, 55, 50, 50);
		Main_panel.add(label_Menu);
		label_Menu.setBackground(new Color(0,0,0,0));
		label_Menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(label_Menu.isEnabled()){
					if( button_panel.isVisible())
					{
						frame.setSize(new Dimension(54, 110));
						Main_panel.setSize(new Dimension(54, 110));
						button_panel.setVisible(false);
						label_Menu.setToolTipText("Click here to expand");
					}
					else
					{
						frame.setSize(new Dimension(54, 500));
						Main_panel.setSize(new Dimension(54, 500));
						button_panel.setVisible(true);
						label_Menu.setToolTipText("Click here to collapse");
						
					}
					popupMenu = new JPopupMenu();
					popupMenu.setPopupSize(new Dimension(150, 60));

					//addPopup(new JLabel("hhhhiiiiiiiiiiiiiiiii"), popupMenu);
				}

			}
		});
		label_Menu.setToolTipText("Click here to expand");
		button_panel.setVisible(false);
		//////
		try{
			label_Menu.setIcon(new ImageIcon(ImageIO.read(menuicon).getScaledInstance(size.width,size.height, java.awt.Image.SCALE_SMOOTH)));

		} catch (IOException e) {
			label_Menu.setText("Menu");logError(e,"Exception in Icon loading: Image "+menuicon.getPath()+" Not Available");
		}

		/*frame.setSize(new Dimension(54, 500));
		Main_panel.setSize(new Dimension(54, 500));
		button_panel.setSize(new Dimension(54, 500));*/

		label_Settings = new JLabel("");
		label_Settings.setSize(new Dimension(50, 50));
		label_Settings.setBounds(1, 330, 50, 50);
		button_panel.add(label_Settings);
		label_Settings.setBackground(new Color(0,0,0,0));
		label_Settings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(ActionGUI.leaveControl);
				if(ActionGUI.leaveControl)
				{
					List<String> tabs=new ArrayList<String>();
					tabs.add("Settings");
					tabs.add("Save");
					tabs.add("View");
					tabs.add("Document");
					frame.setAlwaysOnTop(false);
					new ActionGUI(tabs);
					ActionGUI.dialog.setVisible(true);
				}

			}
		});
		label_Settings.setToolTipText("Click Here for settings");

		try{
			label_Settings.setIcon(new ImageIcon(ImageIO.read(settingicon).getScaledInstance(size.width,size.height, java.awt.Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			label_Settings.setText("Settings");logError(e,"Exception in Icon loading: Image "+settingicon.getPath()+" Not Available");
		}

	}

	@SuppressWarnings("unused")
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});


	}
}