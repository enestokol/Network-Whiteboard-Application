package server;

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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import javax.swing.Timer;
import javax.swing.border.Border;

public class ServerPanel extends JFrame implements ActionListener, MouseListener, KeyListener {

	protected static Color c;
	protected static int x1 = 0;
	protected static int y1 = 0;
	protected static int x2 = 0;
	protected static int y2 = 0;
	protected static int type = -1;
	protected static long hours;
	protected static long minute;
	protected static long second;
	protected static String formattedTime;
	protected static ArrayList<Points> shapes;
	protected static DefaultListModel<String> studentList;
	protected static String name;
	protected static BorderLayout b;
	protected JPanel jpBoard, jpRightMenu, jpUtils;
	protected GridLayout gl;
	protected JButton Size;
	protected JList<String> jlMemType;
	protected JScrollPane scrollPane;
	protected JLabel ShapeCount;
	protected JMenuBar jMenuBar;
	protected JMenu file_m, shape_m, exit_m, time_m;
	protected JMenuItem tAttendance, rectangle_sbm, circle_sbm, clear_sbm, line_sbm, move_sbm, exit_sbm, start_t,
			stop_t, add_t, restart_t;
	protected static Timer t;
	protected static Shapes shp;
	protected Border border;
	protected int count = 0;
	public static int time = 1;

	public static JLabel timeLeft;
	public static Server nServer;

	public ServerPanel(Server nServer) {
		super("Server Paint APP");
		ServerPanel.nServer = nServer;
		ServerPanel.name = JOptionPane.showInputDialog(this, "Name:");
		ServerPanel.shapes = new ArrayList<Points>();
		ServerPanel.studentList = new DefaultListModel<String>();
		ServerPanel.studentList.addElement(name);
		this.border = BorderFactory.createLineBorder(Color.GRAY, 1);
		ServerPanel.b = new BorderLayout();
		setLayout(ServerPanel.b);

		Utils();
		Menu();
		MenuBar();
		Board();
	}

	public void MenuBar() {
		jMenuBar = new JMenuBar();

		file_m = new JMenu("File");
		shape_m = new JMenu("Shape");
		time_m = new JMenu("Time");
		exit_m = new JMenu("Exit");

		tAttendance = new JMenuItem("Take Attendance");
		tAttendance.addActionListener(this);

		rectangle_sbm = new JMenuItem("Rectangle");
		rectangle_sbm.addActionListener(this);

		line_sbm = new JMenuItem("Line");
		line_sbm.addActionListener(this);

		circle_sbm = new JMenuItem("Circle");
		circle_sbm.addActionListener(this);

		clear_sbm = new JMenuItem("Clear");
		clear_sbm.addActionListener(this);

		exit_sbm = new JMenuItem("Exit");
		exit_sbm.addActionListener(this);

		start_t = new JMenuItem("Start");
		start_t.addActionListener(this);

		stop_t = new JMenuItem("Stop");
		stop_t.addActionListener(this);
		stop_t.setEnabled(false);

		restart_t = new JMenuItem("Continue");
		restart_t.addActionListener(this);
		restart_t.setEnabled(false);

		add_t = new JMenuItem("Add Time");
		add_t.addActionListener(this);
		add_t.setEnabled(false);

		addKeyListener(this);

		file_m.add(tAttendance);
		shape_m.add(rectangle_sbm);
		shape_m.add(circle_sbm);
		shape_m.add(line_sbm);
		shape_m.add(clear_sbm);
		time_m.add(start_t);
		time_m.add(stop_t);
		time_m.add(restart_t);
		time_m.add(add_t);
		exit_m.add(exit_sbm);

		jMenuBar.add(file_m);
		jMenuBar.add(shape_m);
		jMenuBar.add(time_m);
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

		ShapeCount = new JLabel("Count:0");
		jpUtils.add(ShapeCount);

	}

	public void Menu() {
		jpRightMenu = new JPanel(new GridBagLayout());
		add(jpRightMenu, BorderLayout.EAST);

		jpUtils.setPreferredSize(new Dimension(250, 75));
		jpRightMenu.add(jpUtils, new GridBagConstraints(0, 0, 1, 1, 100, 33, GridBagConstraints.NORTH,
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
		jlMemType.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = jlMemType.locationToIndex(e.getPoint());
					if (index == nServer.raisedIndex) {
						String stName = ((String) studentList.get(nServer.raisedIndex))
								.replaceAll("[\\u270B\\uFFFD\\uFE0F\\u203C\\u3010]", "");
						studentList.setElementAt(stName, nServer.raisedIndex);
						ArrayList<String> data = new ArrayList<String>();
						data.add("tRaiseAccept");
						data.add("true");
						nServer.send(data);
					}
				}

			}
		});

		scrollPane.setPreferredSize(new Dimension(250, 80));
		jpRightMenu.add(scrollPane, new GridBagConstraints(0, 2, 1, 1, 100, 132, GridBagConstraints.NORTH,
				GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));

		nServer.setPreferredSize(new Dimension(250, 200));
		jpRightMenu.add(nServer, new GridBagConstraints(0, 3, 1, 1, 100, 66, GridBagConstraints.NORTH,
				GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));

	}

	public void Board() {
		jpBoard = new ServerBoard();
		jpBoard.addMouseListener(this);
		jpBoard.setBackground(Color.white);
		add(jpBoard, BorderLayout.CENTER);
		jpBoard.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (type > 0) {
					ArrayList<String> data = new ArrayList<String>();
					data.add("shape");
					data.add(String.valueOf(type));
					Points p = new Points();
					x2 = e.getX();
					y2 = e.getY();
					p.type = type;
					p.x1 = x1;
					p.y1 = y1;
					p.x2 = x2;
					p.y2 = y2;
					if (type == 1 || type == 2 || type == 3) {
						data.add(String.valueOf(x1));
						data.add(String.valueOf(y1));
						data.add(String.valueOf(x2));
						data.add(String.valueOf(y2));
						shapes.add(p);
					}

					nServer.send(data);
					count++;
					ShapeCount.setText("Count:" + count);
					repaint();
				}

			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (time >= 1) {
					if (type > 0) {
						x1 = e.getX();
						y1 = e.getY();
					}
				} else {
					JOptionPane.showMessageDialog(getParent(), "Time is over");
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

	}

	public static String formatTime(long seconds) {
		hours = TimeUnit.SECONDS.toHours(seconds);
		minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
		second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

		if (hours != 0) {
			formattedTime = String.format("%02d:%02d:%02d", hours, minute, second);

		} else {
			formattedTime = String.format("%02d:%02d", minute, second);
		}

		return formattedTime;

	}

	public void timerAct() {
		t = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> data = new ArrayList<String>();
				data.add("time");
				data.add(String.valueOf(time));

				timeLeft.setText("Time Left: " + formatTime(time));
				nServer.send(data);
				if (time == 0) {
					t.stop();
					timeLeft.setText("Time is over");
					JOptionPane.showMessageDialog(getParent(), "Time is over");
					add_t.setEnabled(false);
				}

				time--;

			}
		});
		t.setInitialDelay(0);
		t.start();
		start_t.setEnabled(false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {

		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

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

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == rectangle_sbm) {
			shp = Shapes.RECTANGLE;
			ServerPanel.type = shp.getType();
			JOptionPane.showMessageDialog(this, "Click panel to create rectangle");

		} else if (e.getSource() == circle_sbm) {
			shp = Shapes.CIRCLE;
			ServerPanel.type = shp.getType();
			JOptionPane.showMessageDialog(this, "Click panel to create circle");

		} else if (e.getSource() == line_sbm) {
			shp = Shapes.LINE;
			ServerPanel.type = shp.getType();
			JOptionPane.showMessageDialog(this, "Click panel to create Line");
		} else if (e.getSource() == clear_sbm) {
			type = 0;
			ArrayList<String> data = new ArrayList<String>();
			data.add("shape");
			data.add(String.valueOf(ServerPanel.type));
			data.add(String.valueOf(0));
			data.add(String.valueOf(0));
			data.add(String.valueOf(0));
			data.add(String.valueOf(0));
			nServer.send(data);
			shapes.clear();
			repaint();
			this.count = 0;
			ShapeCount.setText("Count:" + count);

		} else if (e.getSource() == tAttendance) {
			Path out = Paths.get("attendance.txt");
			ArrayList<String> arr = new ArrayList<String>();
			for (int i = 0; i < studentList.getSize(); i++) {
				arr.add((String) studentList.get(i));
			}
			try {
				Files.write(out, arr, Charset.defaultCharset());
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} else if (e.getSource() == start_t) {
			ServerPanel.time = (Integer.parseInt(
					JOptionPane.showInputDialog(getParent(), "Course Time (Please write in term of minute.)"))) * 60;
			timerAct();
			stop_t.setEnabled(true);
			add_t.setEnabled(true);
		}

		else if (e.getSource() == stop_t) {
			if (t.isRunning()) {
				t.stop();
			}
			restart_t.setEnabled(true);
			stop_t.setEnabled(false);

		}

		else if (e.getSource() == restart_t) {
			t.restart();
			stop_t.setEnabled(true);
			restart_t.setEnabled(false);

		} else if (e.getSource() == add_t) {
			if (t.isRunning()) {
				t.stop();
				int time = Integer.parseInt(JOptionPane.showInputDialog(getParent(),
						"How many more minutes would you like to extend the course ?"));
				ServerPanel.time = ServerPanel.time + (time * 60);
				timerAct();
				ServerPanel.timeLeft.setOpaque(true);
				ServerPanel.timeLeft.setForeground(Color.red);

			} else {
				int time = Integer.parseInt(JOptionPane.showInputDialog(getParent(),
						"How many more minutes would you like to extend the course ?"));
				ServerPanel.time = ServerPanel.time + (time * 60);
				timerAct();
			}
			add_t.setEnabled(false);
			stop_t.setEnabled(true);

		} else if (e.getSource() == exit_sbm) {
			System.exit(0);

		}
	}

}
