package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class Client extends JPanel {

	protected BorderLayout bl;
	protected GridLayout gl;
	protected JTextArea jta;
	protected JTextField jtf;
	protected JButton jbSend;
	protected JPanel jpSouth;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String srv;
	private Socket myClient;

	public Client(String info) {
		srv = info;
		ChatScreen();
	}

	public void ChatScreen() {

		bl = new BorderLayout();
		setLayout(bl);

		jta = new JTextArea();
		jta.setEditable(false);
		add(new JScrollPane(jta), BorderLayout.CENTER);

		gl = new GridLayout(2, 1);
		jpSouth = new JPanel();
		jpSouth.setLayout(gl);
		add(jpSouth, BorderLayout.SOUTH);

		jtf = new JTextField();
		jpSouth.add(jtf);

		jbSend = new JButton("Send");
		jbSend.setEnabled(false);
		jpSouth.add(jbSend);
		jbSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> message = new ArrayList<String>();
				message.add("message");
				message.add(ClientPanel.name + ":" + jtf.getText());

				ClientPanel.nClient.send(message);
				jtf.setText("");
			}
		});
	}

	public void runClient() {
		try {

			while (true) {
				try {
					connToServer();
					streams();
					processConn();
				} catch (Exception e) {
					dispMessage("Client terminated Conn\n");
				} finally {
					closeConn();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void connToServer() throws IOException {
		dispMessage("Attempting...\n");
		myClient = new Socket(InetAddress.getByName(srv), 12345);

	}

	private void streams() throws IOException {
		oos = new ObjectOutputStream(myClient.getOutputStream());
		oos.flush();

		ois = new ObjectInputStream(myClient.getInputStream());
		dispMessage("Streams");
		ClientPanel.nClient.send(ClientMain.nClientPanel.info);

	}

	private void processConn() throws IOException {
		setButtonEnabled(true);

		while (true) {
			try {
				ArrayList<String> data = (ArrayList<String>) ois.readObject();
				if (data.get(0).equals("message")) {
					dispMessage("\n" + data.get(1));
				} else if (data.get(0).equals("time")) {
					ClientPanel.time = Integer.valueOf(data.get(1));
					if (ClientPanel.time >= 1) {
						ClientPanel.timeLeft.setText(
								"Time Left: " + ClientPanel.formatTime(ClientPanel.time));
					} else {
						ClientPanel.timeLeft.setText("Time is over");
						JOptionPane.showMessageDialog(getParent(), "Time is over");

					}
				} else if (data.get(0).equals("shape")) {
					ClientMain.nClientPanel.shapePaint(data);
					//ClientPanel.count++;
				} else if (data.get(0).equals("tInfo")) {
					ClientPanel.studentList.addElement((data.get(1)));
					ClientPanel.studentList.addElement(ClientPanel.name + "-" + ClientPanel.number);
					ClientMain.nClientPanel.jlMemType.setCellRenderer(new DefaultListCellRenderer() {


						@Override
						public Component getListCellRendererComponent(JList<?> list, Object value, int index,
								boolean isSelected, boolean cellHasFocus) {

							super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

							String name_ = (String) value;

							if (name_ == (data.get(1))) {
								setForeground(Color.red);
							}

							return this;
						}
					});
				} else if (data.get(0).equals("tRaiseAccept")) {
					ClientMain.nClientPanel.raiseHand.setEnabled(Boolean.valueOf((data.get(1))));
				}

			} catch (ClassNotFoundException e) {
				dispMessage("Unknown");
			}
		}
	}

	private void closeConn() {
		dispMessage("Closing Conn");

		try {
			oos.close();
			ois.close();
			myClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void send(ArrayList<String> data) {
		try {
			oos.writeObject(data);
			oos.flush();
			if (data.get(0).equals("message")) {
				dispMessage("\n" + data.get(1));
			}
		} catch (Exception e) {
			// System.out.println("Error");
		}
	}

	private void dispMessage(final String string) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				jta.append(string);

			}
		});

	}

	private void setButtonEnabled(final boolean b) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				jbSend.setEnabled(b);

			}
		});
	}

}
