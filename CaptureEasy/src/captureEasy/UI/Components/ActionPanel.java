package captureEasy.UI.Components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import captureEasy.Resources.Library;
import captureEasy.UI.ActionGUI;
import captureEasy.UI.SensorGUI;

public class ActionPanel extends Library implements MouseListener,MouseMotionListener{
	public JPanel ActionPanel;
	JPanel panel_4;
	JRadioButton rdbtnSavePreviousWork;
	JRadioButton rdbtnContinuePreviousWork;
	JRadioButton rdbtnDeletePreviousWork;
	int saveTabIndex=0;

	public ActionPanel(JTabbedPane TabbledPanel) {
		ActionPanel = new JPanel();
		ActionPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		ActionPanel.setBackground(Color.WHITE);
		ActionPanel.setLayout(null);
		ActionPanel.addMouseListener(this);
		ActionPanel.addMouseMotionListener(this);

		panel_4 = new JPanel();
		panel_4.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_4.setBounds(12, 13, 405, 291);
		ActionPanel.add(panel_4);
		panel_4.setLayout(null);

		JTextArea txtrYouHaveNot = new JTextArea();
		txtrYouHaveNot.setEditable(false);
		txtrYouHaveNot.setLineWrap(true);
		txtrYouHaveNot.setWrapStyleWord(true);
		txtrYouHaveNot.setRows(5);
		txtrYouHaveNot.setBackground(UIManager.getColor("CheckBox.background"));
		txtrYouHaveNot.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtrYouHaveNot.setText("You have not saved your previous work        before leaving the application.                                                                             Please select an option to continue");
		txtrYouHaveNot.setBounds(35, 23, 327, 90);
		panel_4.add(txtrYouHaveNot);

		ButtonGroup buttonGroup_2= new ButtonGroup();

		rdbtnSavePreviousWork = new JRadioButton("Save previous work");
		buttonGroup_2.add(rdbtnSavePreviousWork);
		rdbtnSavePreviousWork.setSelected(true);
		rdbtnSavePreviousWork.setFont(new Font("Tahoma", Font.BOLD, 16));
		rdbtnSavePreviousWork.setBounds(66, 130, 187, 25);
		panel_4.add(rdbtnSavePreviousWork);

		rdbtnContinuePreviousWork = new JRadioButton("Continue previous work");
		rdbtnContinuePreviousWork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
				if(TabbledPanel.getTitleAt(saveTabIndex).toString().contains("Save"))
				{
					TabbledPanel.removeTabAt(saveTabIndex);
					saveTabIndex=0;
				}
				}catch(Exception e2){e2.printStackTrace();}
			}
		});
		buttonGroup_2.add(rdbtnContinuePreviousWork);
		rdbtnContinuePreviousWork.setFont(new Font("Tahoma", Font.BOLD, 16));
		rdbtnContinuePreviousWork.setBounds(66, 160, 250, 25);
		panel_4.add(rdbtnContinuePreviousWork);

		rdbtnDeletePreviousWork = new JRadioButton("Delete previous work");
		rdbtnDeletePreviousWork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				if(TabbledPanel.getTitleAt(saveTabIndex).toString().contains("Save"))
				{
					TabbledPanel.removeTabAt(saveTabIndex);
					saveTabIndex=0;
				}
				}catch(Exception e1){e1.printStackTrace();}
			}
		});
		buttonGroup_2.add(rdbtnDeletePreviousWork);
		rdbtnDeletePreviousWork.setFont(new Font("Tahoma", Font.BOLD, 16));
		rdbtnDeletePreviousWork.setBounds(66, 190, 219, 25);
		panel_4.add(rdbtnDeletePreviousWork);

		JButton btnProceed = new JButton("Proceed ");
		ActionGUI.dialog.getRootPane().setDefaultButton(btnProceed);
		btnProceed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnSavePreviousWork.isSelected())
				{
					if(TabbledPanel.getTitleAt(saveTabIndex).toString().contains("Save"))
					{
						TabbledPanel.removeTabAt(saveTabIndex);
						saveTabIndex=0;
					}
					SavePanel savePanel=new SavePanel(TabbledPanel);
					TabbledPanel.addTab("Save", null,savePanel.SaveScrollPane, null);

					TabbledPanel.setTitleAt(TabbledPanel.getTabCount()-1, ActionGUI.PRE_HTML + "Save" + ActionGUI.POST_HTML);
					savePanel.textField_Filename.requestFocusInWindow();
					savePanel.btnDone.setEnabled(false);
					savePanel.exitbtn.setEnabled(false);
					savePanel.rdbtnNewDoc.setEnabled(false);
					saveTabIndex=TabbledPanel.getTabCount()-1;
					TabbledPanel.setSelectedIndex(saveTabIndex);

				}
				else if(rdbtnContinuePreviousWork.isSelected())
				{
					ActionGUI.dialog.dispose();
					ActionGUI.leaveControl=true;
					try{SensorGUI.frame.setAlwaysOnTop(true);}catch(Exception e){}
				}
				else
				{
					Library.c=0;
					updateProperty(TempFilePath,"TempPath",createFolder(System.getProperty("user.dir")+"/CaptureEasy/Temp/"+new Random().nextInt(1000000000)));
					ActionGUI.dialog.dispose();
					ActionGUI.leaveControl=true;
					try{SensorGUI.frame.setAlwaysOnTop(true);}catch(Exception e){}
				}
			}
		});
		btnProceed.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnProceed.setSelected(true);
		btnProceed.setBounds(144, 242, 110, 25);
		panel_4.add(btnProceed);
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

}
