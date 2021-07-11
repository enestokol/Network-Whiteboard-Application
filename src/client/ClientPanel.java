package client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class ClientPanel extends JFrame implements ActionListener, KeyListener {

	protected static BorderLayout b;
	protected static Color c;
	protected static String name;
	protected static int number;
	protected static int x1 = 0;
	protected static int y1 = 0;
	protected static int x2 = 0;
	protected static int y2 = 0;
	protected static int type = 0;
	protected Student student;
	protected ArrayList<String> info;
	protected ArrayList<String> atttendance;
	protected static ArrayList<Points> shapes;
	protected static DefaultListModel<String> studentList;
	protected JPanel jpRightMenu, jpUtils,jpCenter;
	protected GridLayout gl;
	protected static JLabel timeLeft, ShapeCount;
	protected JButton raiseHand;
	protected JList<String> jlMemType;
	protected JScrollPane scrollPane;
	protected JMenuBar jMenuBar;
	protected JMenu exit_m;
	protected JMenuItem exit_sbm;
	protected Border border;
	protected static int count = 0;
	protected static int time = 1;
	protected static Client nClient;

	public ClientPanel(Client nClient) {
		super("Student Paint APP");
		ClientPanel.nClient = nClient;
		ClientPanel.name = JOptionPane.showInputDialog(getParent(), "Name:");
		ClientPanel.number = Integer.parseInt(JOptionPane.showInputDialog(getParent(), "ID:"));
		this.student = new Student(name, number);
		this.border = BorderFactory.createLineBorder(Color.GRAY, 1);
		ClientPanel.shapes = new ArrayList<Points>();
		ClientPanel.studentList = new DefaultListModel<String>();

		b = new BorderLayout();
		setLayout(b);

		Utils();
		east();
		MenuBar();
		center();

		info = new ArrayList<String>();
		info.add("stInfo");
		info.add(student.toString());

	}

	public void MenuBar() {
		jMenuBar = new JMenuBar();

		exit_m = new JMenu("Exit");

		exit_sbm = new JMenuItem("Exit");
		exit_sbm.addActionListener(this);

		addKeyListener(this);

		exit_m.add(exit_sbm);

		jMenuBar.add(exit_m);

		add(jMenuBar);
		setJMenuBar(jMenuBar);

	}

	public void Utils() {
		BorderLayout bl;
		GridLayout gl;

		bl = new BorderLayout();
		setLayout(bl);

		gl = new GridLayout(0, 2);
		jpUtils = new JPanel();
		jpUtils.setLayout(gl);
		add(jpUtils);

		timeLeft = new JLabel("Time Left:");
		timeLeft.setBorder(border);
		jpUtils.add(timeLeft);

		raiseHand = new JButton("Hand");
		raiseHand.addActionListener(this);
		jpUtils.add(raiseHand);

	}

	public void east() {
		jpRightMenu = new JPanel(new GridBagLayout());
		add(jpRightMenu, BorderLayout.EAST);

		jpUtils.setPreferredSize(new Dimension(250, 85));
		jpRightMenu.add(jpUtils, new GridBagConstraints(0, 0, 1, 1, 100, 33, GridBagConstraints.NORTH,
				GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));

		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);

		ShapeCount = new JLabel("Count:");
		ShapeCount.setBorder(border);
		ShapeCount.setPreferredSize(new Dimension(250, 15));
		jpRightMenu.add(ShapeCount, new GridBagConstraints(0, 1, 1, 1, 100, 99, GridBagConstraints.NORTH,
				GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));

		jlMemType = new JList<String>(studentList);
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(jlMemType);

		jlMemType.setCellRenderer(new DefaultListCellRenderer() {

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {

				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

				String name_ = (String) value;

				if (name_ == name) {
					setForeground(Color.red);
				}

				return this;
			}
		});

		jlMemType.setLayoutOrientation(JList.VERTICAL);

		scrollPane.setPreferredSize(new Dimension(250, 80));
		jpRightMenu.add(scrollPane, new GridBagConstraints(0, 2, 1, 1, 100, 132, GridBagConstraints.NORTH,
				GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));

		nClient.setPreferredSize(new Dimension(250, 200));
		jpRightMenu.add(nClient, new GridBagConstraints(0, 3, 1, 1, 100, 66, GridBagConstraints.NORTH,
				GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));

	}

	

	public void center() {
		jpCenter = new ClientBoard();
		add(jpCenter, BorderLayout.CENTER);

	}

	public void shapePaint(ArrayList<String> data) {
		Points p = new Points();
		ClientPanel.type = Integer.valueOf(data.get(1));
		ClientPanel.x1 = Integer.valueOf(data.get(2));
		ClientPanel.y1 = Integer.valueOf(data.get(3));
		ClientPanel.x2 = Integer.valueOf(data.get(4));
		ClientPanel.y2 = Integer.valueOf(data.get(5));

		p.type = ClientPanel.type;

		if (type == 1 || type == 2 || type == 3) {

			p.x1 = Integer.valueOf(data.get(2));
			p.y1 = Integer.valueOf(data.get(3));
			p.x2 = Integer.valueOf(data.get(4));
			p.y2 = Integer.valueOf(data.get(5));
			ClientPanel.count++;
			shapes.add(p);

		} else if (type == 0) {
			ClientPanel.count = 0;
			ShapeCount.setText("Count:" + ClientPanel.count);
			shapes.clear();
		}
		repaint();
	}

	public static String formatTime(long seconds) {
		long hours = TimeUnit.SECONDS.toHours(seconds);
		long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
		long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

		final String time;
		if (hours != 0) {
			time = String.format("%02d:%02d:%02d", hours, minute, second);

		} else {
			time = String.format("%02d:%02d", minute, second);
		}

		return time;

	}

	// WindowEvent we;

	public void windowClosing() {
		ArrayList<String> data = new ArrayList<String>();
		int result = JOptionPane.showConfirmDialog(this, "Do you want to Exit ?", "Exit Confirmation : ",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			data.add("stExit");
			data.add(student.toString());
			nClient.send(data);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			System.exit(0);
		}

		else if (result == JOptionPane.NO_OPTION) {
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}

	};

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == raiseHand) {
			atttendance = new ArrayList<String>();
			atttendance.add("stHand");
			atttendance.add(String.valueOf(ClientPanel.name + "-" + ClientPanel.number));
			ClientPanel.nClient.send(this.atttendance);
			raiseHand.setEnabled(false);

		} else if (e.getSource() == exit_sbm) {
			windowClosing();

		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
