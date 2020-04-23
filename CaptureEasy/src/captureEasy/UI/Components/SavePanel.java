package captureEasy.UI.Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import captureEasy.Resources.Library;
import captureEasy.Resources.SharedRepository;
import captureEasy.UI.ActionGUI;
import captureEasy.UI.PopUp;
import captureEasy.UI.SensorGUI;

public class SavePanel extends Library implements MouseListener,MouseMotionListener{

	JPanel SavePanel;
	public JPanel SaveScrollPane;
	public JRadioButton rdbtnNewDoc;
	ButtonGroup g = new ButtonGroup();
	public JRadioButton rdbtnExDoc;
	public JRadioButton rdbtnSavePDF;
	public JPanel panel_Input;
	public JPanel panel_1;
	public JLabel lblChooseFile;
	public JButton btnChooseFile;
	public static JCheckBox chckbxOverwriteSelectedFile;
	public JLabel lblEnterFilename;
	public JTextField textField_Filename;
	public JPanel panel_Save_Buttons;
	public JButton exitbtn;
	public static JLabel label_Status;
	public JButton btnDone;
	public static JProgressBar ProgressBar;
	PopUp popup;
	public String existingfilepath;
	String newFileName;


	public JPanel panel_Progress;
	public static JLabel lblUpdatingFiles;
	public JPanel panel_Update;
	public JLabel lblParFol;
	public JTextField textField_ParFol;
	public SavePanel(JTabbedPane TabbledPanel) {

		SavePanel = new JPanel();
		SavePanel.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(0, 0, 0)));
		SavePanel.setBounds(12, 12, 413, 250);
		SavePanel.addMouseListener(this);
		SavePanel.addMouseMotionListener(this);
		SavePanel.setPreferredSize(new Dimension(350, 100));

		SaveScrollPane=new JPanel();
		SaveScrollPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		SaveScrollPane.setBackground(new Color(255, 255, 255));
		SaveScrollPane.setLayout(null);
		SaveScrollPane.setVisible(true);
		SaveScrollPane.setSize(new Dimension(438, 316));
		SaveScrollPane.setBounds(12, 12, 438, 316);

		SaveScrollPane.add(SavePanel);
		SavePanel.setLayout(null);
		JLabel label = new JLabel("Please Select :  ");
		label.setPreferredSize(new Dimension(400, 2));
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label.setBounds(12, 13, 415, 35);
		SavePanel.add(label);

		rdbtnNewDoc = new JRadioButton();
		g.add(rdbtnNewDoc);
		rdbtnNewDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if("true".equalsIgnoreCase(getProperty(PropertyFilePath,"showFolderNameField")))
				{
					lblParFol.setVisible(true);
					textField_ParFol.setVisible(true);
					textField_Filename.setColumns(16);
				}
				else
				{
					lblParFol.setVisible(false);
					textField_ParFol.setVisible(false);
					textField_Filename.setColumns(22);
				}
				panel_Input.setBounds(28, 162, 365, 70);

				rdbtnNewDoc.setEnabled(false);
				rdbtnExDoc.setEnabled(true);
				rdbtnSavePDF.setEnabled(true);

				lblEnterFilename.setVisible(true);
				textField_Filename.setVisible(true);
				textField_Filename.requestFocusInWindow();

				textField_Filename.setText("");
				existingfilepath="";
				newFileName="";
				btnChooseFile.setBackground(Color.WHITE);

				lblChooseFile.setVisible(false);
				btnChooseFile.setVisible(false);
				chckbxOverwriteSelectedFile.setVisible(false);

				btnDone.setEnabled(false);
			}
		});
		rdbtnNewDoc.setText("Add to New Microsoft Word Document");
		rdbtnNewDoc.setSelected(true);
		rdbtnNewDoc.setPreferredSize(new Dimension(400, 25));
		rdbtnNewDoc.setFont(new Font("Tahoma", Font.BOLD, 16));
		rdbtnNewDoc.setFocusable(true);
		rdbtnNewDoc.setBounds(18, 45, 375, 35);
		SavePanel.add(rdbtnNewDoc);
		rdbtnExDoc = new JRadioButton();
		g.add(rdbtnExDoc);
		rdbtnExDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				lblParFol.setVisible(false);
				textField_ParFol.setVisible(false);

				panel_Input.setBounds(28, 162, 365, 70);

				rdbtnNewDoc.setEnabled(true);
				rdbtnExDoc.setEnabled(false);
				rdbtnSavePDF.setEnabled(true);

				lblEnterFilename.setVisible(false);
				textField_Filename.setVisible(false);
				textField_Filename.setText("");
				existingfilepath="";
				newFileName="";
				btnChooseFile.setBackground(Color.WHITE);

				lblChooseFile.setVisible(true);
				btnChooseFile.setVisible(true);
				btnChooseFile.requestFocusInWindow();
				chckbxOverwriteSelectedFile.setVisible(false);

				btnDone.setEnabled(false);
			}
		});
		rdbtnExDoc.setText("Add to Existing Microsoft Word Document");
		rdbtnExDoc.setPreferredSize(new Dimension(390, 25));
		rdbtnExDoc.setFont(new Font("Tahoma", Font.BOLD, 16));
		rdbtnExDoc.setBounds(18, 80, 375, 35);
		SavePanel.add(rdbtnExDoc);

		rdbtnSavePDF = new JRadioButton("Save as PDF");
		g.add(rdbtnSavePDF);
		rdbtnSavePDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{

				if("true".equalsIgnoreCase(getProperty(PropertyFilePath,"showFolderNameField")))
				{
					lblParFol.setVisible(true);
					textField_ParFol.setVisible(true);
					textField_Filename.setColumns(16);
				}
				else
				{
					lblParFol.setVisible(false);
					textField_ParFol.setVisible(false);
					textField_Filename.setColumns(22);
				}
				panel_Input.setBounds(28, 162, 365, 70);
				ActionGUI.dialog.setAlwaysOnTop(true);
				rdbtnNewDoc.setEnabled(true);
				rdbtnExDoc.setEnabled(true);
				rdbtnSavePDF.setEnabled(false);

				lblEnterFilename.setVisible(true);
				textField_Filename.setVisible(true);
				textField_Filename.requestFocusInWindow();

				textField_Filename.setText("");
				existingfilepath="";
				newFileName="";
				btnChooseFile.setBackground(Color.WHITE);
				lblChooseFile.setVisible(false);
				btnChooseFile.setVisible(false);
				chckbxOverwriteSelectedFile.setVisible(false);

				btnDone.setEnabled(false);

			}
		});
		rdbtnSavePDF.setPreferredSize(new Dimension(390, 25));
		rdbtnSavePDF.setFont(new Font("Tahoma", Font.BOLD, 16));
		rdbtnSavePDF.setBounds(18, 115, 130, 35);
		SavePanel.add(rdbtnSavePDF);

		panel_Input = new JPanel();
		panel_Input.setInheritsPopupMenu(true);
		panel_Input.setIgnoreRepaint(true);
		panel_Input.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_Input.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_Input.setBackground(new Color(255, 255, 204));
		panel_Input.setBounds(28, 162, 365, 70);

		SavePanel.add(panel_Input);
		panel_Input.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));


		lblParFol = new JLabel("Parent Folder :");
		lblParFol.setVisible(false);
		panel_Input.add(lblParFol);
		lblParFol.setFont(new Font("Tahoma", Font.BOLD, 16));

		textField_ParFol = new JTextField();
		textField_ParFol.setVisible(false);
		panel_Input.add(textField_ParFol);
		textField_ParFol.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_ParFol.setToolTipText("Enter parent folder name");
		textField_ParFol.setColumns(10);
		textField_ParFol.getDocument().addDocumentListener(new DocumentListener()
		{
			public void changedUpdate(DocumentEvent e) {
			}
			public void insertUpdate(DocumentEvent e) {
				DocumentCheck("Insert");
			}
			public void removeUpdate(DocumentEvent e) {
				DocumentCheck("Remove");
			}
		});

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 204));
		panel_Input.add(panel_1);

		lblChooseFile = new JLabel("Choose File:");
		panel_1.add(lblChooseFile);
		lblChooseFile.setVisible(false);
		lblChooseFile.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnChooseFile = new JButton();
		panel_1.add(btnChooseFile);
		btnChooseFile.setToolTipText("Choose file");
		btnChooseFile.setBackground(Color.WHITE);
		btnChooseFile.setVisible(false);
		btnChooseFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) 
			{
				ActionGUI.dialog.setAlwaysOnTop(false);
				FileDialog fileDialog = new FileDialog(new Frame(), "Choose File");
				fileDialog.setAlwaysOnTop(true);
				fileDialog.setFile("*.docx;*.doc");
				fileDialog.setVisible(true);
				existingfilepath = String.valueOf(fileDialog.getDirectory()) + fileDialog.getFile();
				ActionGUI.dialog.setAlwaysOnTop(true);
				panel_Input.setBounds(28, 162, 365, 70);
				if (existingfilepath.equalsIgnoreCase("nullnull") || existingfilepath.equals("")) 
				{
					btnDone.setEnabled(false);
					chckbxOverwriteSelectedFile.setVisible(false);
					btnChooseFile.setBackground(Color.pink);
					btnChooseFile.requestFocusInWindow();
					ActionGUI.dialog.setAlwaysOnTop(false);
					popup=new PopUp("ERROR","Error","Please choose a file!!","Ok, I understood","");
					lblEnterFilename.setVisible(false);
					textField_Filename.setVisible(false);
					textField_Filename.setText("");
				}
				else 
				{

					File file = new File(existingfilepath);
					if (!file.renameTo(file)) 
					{
						btnChooseFile.setBackground(Color.YELLOW);
						ActionGUI.dialog.setAlwaysOnTop(false);
						popup=new PopUp("WARNING","warning","Selected file '"+file.getName()+"' "
								+ "is open for editing. To overwrite please close the file and select again. Otherwise enter filename to create a copy.",
								"Ok, I understood","");
						chckbxOverwriteSelectedFile.setVisible(true);
						chckbxOverwriteSelectedFile.setSelected(false);
						chckbxOverwriteSelectedFile.setEnabled(false);
						panel_Input.setBounds(28, 153, 380, 92);
						textField_Filename.setColumns(15);
						lblEnterFilename.setVisible(true);
						textField_Filename.setVisible(true);
						textField_Filename.setText("");
						textField_Filename.requestFocusInWindow();
						btnDone.setEnabled(false);
					}
					else 
					{
						chckbxOverwriteSelectedFile.setVisible(true);
						chckbxOverwriteSelectedFile.setSelected(true);
						chckbxOverwriteSelectedFile.setEnabled(true);
						btnChooseFile.setBackground(Color.GREEN);
						panel_Input.setBounds(28, 162, 365, 70);
						if("true".equalsIgnoreCase(getProperty(PropertyFilePath,"ArrangeSSDatewise")))
						{
							textField_Filename.setColumns(22);
						}
						else
						{
							textField_Filename.setColumns(16);
						}
						lblEnterFilename.setVisible(false);
						textField_Filename.setVisible(false);
						textField_Filename.setText("");
						btnDone.setEnabled(true);
					} 
				} 
			}
		});
		btnChooseFile.setMargin(new Insets(2, 2, 2, 2));
		btnChooseFile.setSize(new Dimension(20, 30));
		Dimension size = btnChooseFile.getSize();
		try {
			BufferedImage master = ImageIO.read(new File("Icons/Significon-Attachment-512.png"));
			Image scaled = master.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
			btnChooseFile.setIcon(new ImageIcon(scaled));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		chckbxOverwriteSelectedFile = new JCheckBox("Overwrite selected file");
		chckbxOverwriteSelectedFile.setBackground(new Color(255, 255, 204));
		chckbxOverwriteSelectedFile.setSelected(true);
		chckbxOverwriteSelectedFile.setVisible(false);
		chckbxOverwriteSelectedFile.setFont(new Font("Tahoma", Font.PLAIN, 16));
		chckbxOverwriteSelectedFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxOverwriteSelectedFile.isSelected())
				{
					panel_Input.setBounds(28, 162, 365, 70);
					if("true".equalsIgnoreCase(getProperty(PropertyFilePath,"ArrangeSSDatewise")))
					{
						textField_Filename.setColumns(22);
					}
					else
					{
						textField_Filename.setColumns(16);
					}
					lblEnterFilename.setVisible(false);
					textField_Filename.setVisible(false);
					textField_Filename.setText("");

					btnDone.setEnabled(true);
				}
				else
				{
					panel_Input.setBounds(28, 153, 380, 92);
					textField_Filename.setColumns(15);
					lblEnterFilename.setVisible(true);
					textField_Filename.setVisible(true);
					textField_Filename.setText("");
					textField_Filename.requestFocusInWindow();
					btnDone.setEnabled(false);
				}
			}
		});
		panel_Input.add(chckbxOverwriteSelectedFile);

		lblEnterFilename = new JLabel();
		lblEnterFilename.setText("Filename :");
		panel_Input.add(lblEnterFilename);
		lblEnterFilename.setFont(new Font("Tahoma", Font.BOLD, 16));

		textField_Filename = new JTextField();
		panel_Input.add(textField_Filename);
		textField_Filename.setToolTipText("Enter Filename");
		textField_Filename.setFont(new Font("Tahoma", Font.PLAIN, 16));

		if("true".equalsIgnoreCase(getProperty(PropertyFilePath,"showFolderNameField")))
		{
			textField_Filename.setColumns(16);
		}
		else
		{
			textField_Filename.setColumns(22);
		}
		textField_Filename.getDocument().addDocumentListener(new DocumentListener()
		{
			public void changedUpdate(DocumentEvent e) {
			}
			public void insertUpdate(DocumentEvent e) {
				DocumentCheck("Insert");
			}
			public void removeUpdate(DocumentEvent e) {
				DocumentCheck("Remove");
			}
		});

		panel_Save_Buttons = new JPanel();
		panel_Save_Buttons.setBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_Save_Buttons.setBounds(12, 259, 413, 46);
		panel_Save_Buttons.setLayout(null);
		btnDone = new JButton("Okay");
		btnDone.setMargin(new Insets(2, 5, 2, 5));
		btnDone.setBounds(130, 5, 73, 29);
		btnDone.setBackground(Color.BLUE);
		btnDone.setForeground(Color.BLACK);
		panel_Save_Buttons.add(btnDone);
		SaveScrollPane.add(panel_Save_Buttons);
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if("true".equalsIgnoreCase(getProperty(PropertyFilePath,"showFolderNameField")) && "true".equalsIgnoreCase(getProperty(PropertyFilePath,"setFolderNameMandatory")) && textField_ParFol.getText().replaceAll("\\s", "").equals(""))
				{
					new PopUp("ERROR","error","You have set parent folder name field as mandatory. Please update settings to proceed further","Ok, I understood","").setVisible(true);
					textField_ParFol.setBackground(Color.PINK);
				}
				else
				{
					String value=getProperty(PropertyFilePath,"DocPath");
					if(value==null || value.replaceAll("\\s", "").equals(""))
					{
						new PopUp("ERROR","error","'DocPath' is unavailable in '"+new File(PropertyFilePath).getName()+"'.Please update this property.","Okay","").setVisible(true);
						TabbledPanel.setSelectedIndex(TabbledPanel.getTabCount()-1);
						ActionGUI.settingsPanel.SettingsPane_DocFolderPanel_textField_DocDestFolder.setBackground(Color.PINK);
					}
					else
					{
						ActionPanel.panel_4=null;
						textField_ParFol.setBackground(Color.WHITE);
						SavePanel.setVisible(false);
						panel_Save_Buttons.setVisible(false);
						panel_Progress = new JPanel();
						panel_Progress.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(0, 0, 0)));
						panel_Progress.setBounds(12, 13, 413, 250);
						panel_Progress.setLayout(null);

						JLabel lblProgressInformation = new JLabel("Progress Information");
						lblProgressInformation.setBounds(110, 10, 194, 22);
						lblProgressInformation.setFont(new Font("Tahoma", Font.BOLD, 18));
						panel_Progress.add(lblProgressInformation);

						JLabel lblPleaseWaitWhile = new JLabel("Please wait while we are preparing your document ...");
						lblPleaseWaitWhile.setBounds(18, 45, 377, 20);
						lblPleaseWaitWhile.setFont(new Font("Tahoma", Font.PLAIN, 16));
						panel_Progress.add(lblPleaseWaitWhile);

						//ProgressBar = new JProgressBar();
						ProgressBar =new JCircularProgressBar().makeUI();
						ProgressBar.setBounds(120, 75, 190, 170);

						panel_Update = new JPanel();
						panel_Update.setBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 0, 0)));
						panel_Update.setBounds(12, 259, 413, 46);
						SaveScrollPane.add(panel_Update);
						panel_Update.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

						lblUpdatingFiles = new JLabel();
						panel_Update.add(lblUpdatingFiles);
						lblUpdatingFiles.setFont(new Font("Tahoma", Font.PLAIN, 16));
						panel_Progress.add(ProgressBar);

						panel_Progress.setVisible(true);
						SaveScrollPane.add(panel_Progress);
						new Thread(new Runnable(){

							@Override
							public void run() {
								do
								{
									ProgressBar.setValue(SharedRepository.progress);
									ProgressBar.repaint();
								}while(SharedRepository.progress!=100);
							}  
						}).start();;
						new Thread(new Runnable(){
							@Override
							public void run() {
								if(rdbtnNewDoc.isSelected())
								{
									createNewWord(getProperty(PropertyFilePath,"DocPath"),textField_Filename.getText(),textField_ParFol.getText());
								}
								else if(rdbtnExDoc.isSelected())
								{
									addToExistingWord(existingfilepath,textField_Filename.getText());
								}
								else if(rdbtnSavePDF.isSelected())
								{
									//new PopUp("Information","info","Sorry !! This facility is currently  unavailable. This will save as document","Ok, Fine","").setVisible(true);
									createNewWord(getProperty(PropertyFilePath,"DocPath"),textField_Filename.getText(),textField_ParFol.getText());
								}
								ActionGUI.dialog.dispose();
								ActionGUI.leaveControl=true;
								try{SensorGUI.frame.setAlwaysOnTop(true);}catch(Exception e5){}
							}

						}).start();;
					}
				}
			}
		});


		btnDone.setFont(new Font("Tahoma", Font.BOLD, 16));

		exitbtn = new JButton("Cancel");
		exitbtn.setMargin(new Insets(2, 5, 2, 5));
		exitbtn.setBounds(210, 5, 87, 29);
		exitbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionGUI.dialog.dispose();
				ActionGUI.leaveControl=true;
				try{SensorGUI.frame.setAlwaysOnTop(true);}catch(Exception e5){}
			}
		});
		panel_Save_Buttons.add(exitbtn);
		exitbtn.setFont(new Font("Tahoma", Font.BOLD, 16));

		

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
	public void mouseMoved(MouseEvent arg0) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}



	public void DocumentCheck(String ActionType)  
	{
		try{
			newFileName = textField_Filename.getText();
			if (newFileName.contains(Character.toString('"')) || newFileName.contains("/") || newFileName.contains("\\") || newFileName.contains(":") || newFileName.contains("*") || newFileName.contains("?") || newFileName.contains("<") || newFileName.contains(">") || newFileName.contains("|")) 
			{
				btnDone.setEnabled(false);
				textField_Filename.setBackground(Color.PINK);
				textField_Filename.requestFocusInWindow();
				if(ActionType.equalsIgnoreCase("Insert") && PopUp.control)
					new PopUp("ERROR","error", "A file name can not contain any of the following "
							+ "characters: \\ / : * ? " + Character.toString('"') + " < > | ","Ok, I understood","").setAlwaysOnTop(true);;
			}
			else if ((new File(String.valueOf(subFolders(getProperty(PropertyFilePath,"DocPath"),textField_ParFol.getText())) + "\\" + newFileName + ".docx")).exists()) 
			{
				btnDone.setEnabled(false);
				textField_Filename.setBackground(Color.PINK);
				//textField_Filename.requestFocusInWindow();
				if(ActionType.equalsIgnoreCase("Insert"))
				{
					ActionGUI.dialog.setAlwaysOnTop(false);
					new PopUp("ERROR","error","There is already a file with the same name in "+new File(String.valueOf(subFolders(getProperty(PropertyFilePath,"DocPath"),textField_ParFol.getText())) 
							+ "\\" + newFileName + ".docx").getParentFile()+" folder.","Ok, I understood","").setAlwaysOnTop(true);;

				}

			}
			else 
			{
				btnDone.setEnabled(true);
				textField_Filename.setBackground(Color.WHITE);
				//textField_Filename.requestFocusInWindow();

			}
			if(textField_Filename.getText().equalsIgnoreCase(""))
				btnDone.setEnabled(false);
		}catch(Exception e)
		{}

	}
}
