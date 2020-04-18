package captureEasy.UI;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.MatteBorder;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PopUp extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int xx,xy;
	public static boolean control=true;
	public static boolean decision;
	public  String Error_filepath="Icons/Icon_Error.png";
	public JButton btnNewButton;
	public JButton btnNo;
	public JTextArea txtrExceptionOccuredPlease;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					PopUp window = new PopUp("ERROR","warning",this.getClass().getName()+"EXCEPTION ON SCREEN SHOYT ","ok","Cance;");
					window.setVisible(true);
					
					//do{Thread.sleep(100);}while(!PopUp.control);
					System.out.println(decision);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	public PopUp(String title,String PopUpType,String text,String OkButton_Text,String cancelButton_Text) 
	{
		control=false;
		try{ActionGUI.dialog.setAlwaysOnTop(false);}catch(Exception e){}
		setResizable(false);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		setUndecorated(true);
		setAlwaysOnTop(true);
		setLocation(screensize.width/2-300, screensize.height/2-300);  
		setBounds(screensize.width/2-300, screensize.height/2-300, 425, 225);
		getContentPane().setLayout(null);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				 xx = e.getX();
			     xy = e.getY();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				
				int x = arg0.getXOnScreen();
	            int y = arg0.getYOnScreen();
	            setLocation(x - xx, y - xy);  
	            setBounds(x - xx, y - xy, 425, 225);
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		panel.setBounds(0, 0, 425, 225);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblError = new JLabel(title);
		lblError.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_1.add(lblError);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.menu);
		panel.add(panel_2, BorderLayout.SOUTH);
		
		btnNewButton = new JButton();
		btnNewButton.setVisible(false);
		if(!OkButton_Text.equalsIgnoreCase(""))
		{
			btnNewButton.setVisible(true);
			btnNewButton.setText(OkButton_Text);
			btnNewButton.setToolTipText("Click");
			btnNewButton.setSelected(true);
		}	
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				try{ActionGUI.dialog.setAlwaysOnTop(false);}catch(Exception e){}
				btnNewButton.setSelected(true);
				control=true;
				decision=true;
				dispose();
			}
		});
		btnNewButton.setBackground(new Color(204, 204, 255));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_2.add(btnNewButton);
		
		btnNo = new JButton();
		
		btnNo.setVisible(false);
		if(!cancelButton_Text.equalsIgnoreCase(""))
		{
			btnNo.setVisible(true);
			btnNo.setText(cancelButton_Text);
			btnNo.setToolTipText("Click");
		}
		
		btnNo.setBackground(new Color(204, 204, 255));
		btnNo.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{ActionGUI.dialog.setAlwaysOnTop(false);}catch(Exception e){}
				dispose();
				control=true;
				decision=false;
				
			}
		});

		panel_2.add(btnNo);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.WEST);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.EAST);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_5.setBackground(Color.WHITE);
		panel.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(null);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_6.setBackground(new Color(255, 255, 204));
		panel_6.setBounds(12, 13, 377, 125);
		if(OkButton_Text.equals("") && cancelButton_Text.equals(""))
		{
			panel_6.setBounds(12, 13, 377, 155);
		}
		panel_5.add(panel_6);
		
		JLabel lblIcon = new JLabel("Icon");
		lblIcon.setLocation(10, 35);
		lblIcon.setSize(50,50);
		
		try {
			String filepath;
			Dimension size = lblIcon.getSize();
			BufferedImage master;
			if(PopUpType.equalsIgnoreCase("Error"))
			{
				master = ImageIO.read(new File(Error_filepath));
			}
			else if(PopUpType.equalsIgnoreCase("Warning"))
			{
				filepath="Icons/warning_icon.png";
				master = ImageIO.read(new File(filepath));
			}
			else
			{
				filepath="Icons/info_icon.png";
				master = ImageIO.read(new File(filepath));
			}
			Image scaled = master.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
	        lblIcon.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			//Log It
			e.printStackTrace();
		}
		panel_6.setLayout(null);
		
		panel_6.add(lblIcon);
		
		JPanel panel_7 = new JPanel();
		panel_7.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_7.setBackground(new Color(255, 255, 204));
		panel_7.setBounds(70, 13, 288, 110);
		panel_6.add(panel_7);
		panel_7.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		txtrExceptionOccuredPlease = new JTextArea();
		txtrExceptionOccuredPlease.setToolTipText(text);
		txtrExceptionOccuredPlease.setRows(5);
		if(PopUpType.equalsIgnoreCase("Error"))
		{
			txtrExceptionOccuredPlease.setForeground(Color.RED);
		}
		else
		{
			txtrExceptionOccuredPlease.setForeground(Color.BLACK);
		}
		txtrExceptionOccuredPlease.setBackground(new Color(255, 255, 204));
		txtrExceptionOccuredPlease.setEditable(false);
		txtrExceptionOccuredPlease.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		txtrExceptionOccuredPlease.setFocusable(false);
		txtrExceptionOccuredPlease.setLineWrap(true);
		txtrExceptionOccuredPlease.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtrExceptionOccuredPlease.setColumns(18);
		int len=text.length();
		
		if(len>120)
		{
			text=text.substring(0, 120)+" "
					+ "...";
		}
		txtrExceptionOccuredPlease.setText(text);
		panel_7.add(txtrExceptionOccuredPlease);
		
		//revalidate();
        setVisible(true); 
        try{getRootPane().setDefaultButton(btnNewButton);}catch(Exception e){}
	}
}
