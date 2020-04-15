package captureEasy.UI.ActionGUIParts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.MatteBorder;

import captureEasy.Library.Library;
import captureEasy.UI.ActionGUI;

public class ViewPanel extends Library implements MouseListener,MouseMotionListener{
	public JPanel ViewScrollPane;
	JPanel panel_Image;
	JLabel ImageLabel;
	JLabel label_Prev;
	JLabel label_VisitFolder;
	JLabel Label_FullView;
	JLabel label_Next;
	JLabel label_Delete;
	JPanel panel_Button;
	public ViewPanel(JTabbedPane TabbledPanel)
	{
		ViewScrollPane = new JPanel();
		ViewScrollPane.setSize(new Dimension(434, 319));
		ViewScrollPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		ViewScrollPane.setBackground(new Color(255, 255, 255));
		ViewScrollPane.setLayout(null);
		ViewScrollPane.addMouseListener(this);
		ViewScrollPane.addMouseMotionListener(this);
		{

			{
				panel_Image = new JPanel();
				panel_Image.setSize(new Dimension(410, 250));
				panel_Image.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
				panel_Image.setBounds(12, 13, 410, 250);
				ViewScrollPane.add(panel_Image);
				panel_Image.setLayout(null);
				{
					ImageLabel = new JLabel(" ");
					ImageLabel.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
						}
					});
					try {
						ImageLabel.setIcon(new ImageIcon(ImageIO.read(new File("C:\\Users\\USER\\Desktop\\Icons\\1.png")).getScaledInstance(410,250, java.awt.Image.SCALE_SMOOTH)));
					} catch (IOException e) {

					}
					ImageLabel.setSize(new Dimension(400, 250));
					ImageLabel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
					ImageLabel.setBounds(0, 0, 410, 250);
					panel_Image.add(ImageLabel);
				}
			}
			{
				panel_Button = new JPanel();
				panel_Button.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
				panel_Button.setBounds(12, 272, 410, 35);
				ViewScrollPane.add(panel_Button);
				panel_Button.setLayout(null);
				{
					label_Prev = new JLabel(" ");

					label_Prev.setSize(new Dimension(25, 25));
					label_Prev.setToolTipText("Swipe left");
					label_Prev.setBounds(160, 5, 33, 25);
					panel_Button.add(label_Prev);
					try {
						label_Prev.setIcon(new ImageIcon(ImageIO.read(new File("C:\\Users\\USER\\Desktop\\Icons\\left-arrow.png")).getScaledInstance(25,25, java.awt.Image.SCALE_SMOOTH)));
					} catch (IOException e) {

					}
				}
				{
					Label_FullView = new JLabel(" ");

					Label_FullView.setSize(new Dimension(25, 25));
					Label_FullView.setToolTipText("View Fullscreen");
					Label_FullView.setBounds(195, 5, 33, 25);
					panel_Button.add(Label_FullView);
					try {
						Label_FullView.setIcon(new ImageIcon(ImageIO.read(new File("C:\\Users\\USER\\Desktop\\Icons\\home.png")).getScaledInstance(25,25, java.awt.Image.SCALE_SMOOTH)));
					} catch (IOException e) {

					}
				}
				{
					label_Next = new JLabel(" ");

					label_Next.setSize(new Dimension(25, 25));
					label_Next.setToolTipText("Swipe right");
					label_Next.setBounds(230, 5, 33, 25);
					panel_Button.add(label_Next);
					try {
						label_Next.setIcon(new ImageIcon(ImageIO.read(new File("C:\\Users\\USER\\Desktop\\Icons\\right-arrow.png")).getScaledInstance(25,25, java.awt.Image.SCALE_SMOOTH)));
					} catch (IOException e) {

					}
				}
				{
					label_Delete = new JLabel("");

					label_Delete.setSize(new Dimension(25, 25));
					label_Delete.setToolTipText("Delete this image");
					label_Delete.setBounds(376, 0, 25, 30);
					try {
						label_Delete.setIcon(new ImageIcon(ImageIO.read(new File("C:\\Users\\USER\\Desktop\\Icons\\delete.png")).getScaledInstance(25,25, java.awt.Image.SCALE_SMOOTH)));
					} catch (IOException e) {
					}
					panel_Button.add(label_Delete);
				}
				{
					label_VisitFolder = new JLabel("");
					label_VisitFolder.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent arg0) {
						}
					});
					label_VisitFolder.setSize(new Dimension(25, 25));

					label_VisitFolder.setToolTipText("Visit screenshot folder");
					try {
						label_VisitFolder.setIcon(new ImageIcon(ImageIO.read(new File("C:\\Users\\USER\\Desktop\\Icons\\folder.png")).getScaledInstance(25,25, java.awt.Image.SCALE_SMOOTH)));
					} catch (IOException e) {
					}
					label_VisitFolder.setBounds(12, 5, 25, 25);
					panel_Button.add(label_VisitFolder);
				}
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

}
