package captureEasy.UI.ActionGUIParts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import captureEasy.Library.Library;
import captureEasy.UI.ActionGUI;

public class ManageDocumentPanel extends Library implements MouseListener,MouseMotionListener{

	public JPanel DocumentScrollPane;
	JScrollPane scrollPane;
	JButton btnGo;

	public ManageDocumentPanel(JTabbedPane TabbledPanel)
	{
		{
			DocumentScrollPane = new JPanel();
			DocumentScrollPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			DocumentScrollPane.setBackground(Color.WHITE);
			DocumentScrollPane.setSize(new Dimension(400, 200));
			DocumentScrollPane.addMouseListener(this);
			DocumentScrollPane.addMouseMotionListener(this);
			DocumentScrollPane.setLayout(null);

			JPanel panel = new JPanel();
			panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			panel.setBounds(10, 10, 415, 45);
			DocumentScrollPane.add(panel);
			panel.setLayout(null);

			JRadioButton rdbtnViewDocuments = new JRadioButton("View Documents");
			rdbtnViewDocuments.setFont(new Font("Tahoma", Font.BOLD, 16));
			rdbtnViewDocuments.setBounds(20, 10, 163, 25);
			panel.add(rdbtnViewDocuments);

			JRadioButton rdbtnNewRadioButton = new JRadioButton("Search Documents");
			rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.BOLD, 16));
			rdbtnNewRadioButton.setBounds(215, 10, 179, 25);
			panel.add(rdbtnNewRadioButton);

			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			panel_2.setBounds(10, 68, 415, 237);
			DocumentScrollPane.add(panel_2);
			panel_2.setLayout(null);

			JLabel lblMonth = new JLabel("Month");
			lblMonth.setBounds(30, 10, 45, 20);
			lblMonth.setFont(new Font("Tahoma", Font.PLAIN, 16));
			panel_2.add(lblMonth);

			JComboBox<Object> month = new JComboBox<Object>();
			month.setBounds(85, 10, 85, 22);
			month.setPreferredSize(new Dimension(75, 22));
			panel_2.add(month);

			JLabel lblDay = new JLabel("Day");
			lblDay.setBounds(190, 10, 30, 20);
			lblDay.setFont(new Font("Tahoma", Font.PLAIN, 16));
			panel_2.add(lblDay);

			JComboBox<Object> day = new JComboBox<Object>();
			day.setBounds(225, 10, 85, 22);
			day.setPreferredSize(new Dimension(75, 22));
			panel_2.add(day);

			scrollPane = new JScrollPane();
			scrollPane.setBounds(12, 42, 391, 160);
			panel_2.add(scrollPane);

			JPanel panel_3 = new JPanel();
			scrollPane.setRowHeaderView(panel_3);
			panel_3.setLayout(null);

			btnGo = new JButton("GO");
			btnGo.setBorder(new LineBorder(new Color(0, 0, 0)));
			btnGo.setForeground(Color.BLACK);
			btnGo.setBackground(Color.BLUE);
			btnGo.setBounds(340, 9, 50, 25);
			btnGo.setMargin(new Insets(2, 2, 2, 2));
			btnGo.setFont(new Font("Tahoma", Font.BOLD, 16));
			panel_2.add(btnGo);

			/*JList<? extends E> list = new JList();
		list.
		panel_3.add(list);

		JList<? extends E> list_1 = new JList();
		panel_3.add(list_1);*/
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
	
}
