package server;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Server extends JPanel {

	BorderLayout bl;
	GridLayout gl;
	JTextArea jta;
	JTextField jtf;
	JButton jbSend;
	JPanel jpSouth;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private ServerSocket server;
	private Socket conn;
	protected int raisedIndex;

	public Server() {
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
				message.add(ServerPanel.name + ":" + jtf.getText());
				ServerPanel.nServer.send(message);
				jtf.setText("");
			}
		});
	}

	public void runServer() {
		try {
			server = new ServerSocket(12345, 100);

			while (true) {
				try {
					waitConn();
					streams();
					processConn();
				} catch (Exception e) {
					dispMessage("\nServer terminated Conn\n");

				} finally {
					closeConn();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void waitConn() throws IOException {
		dispMessage("Please wait...\n");
		conn = server.accept();
		dispMessage("Connection Received\n");

	}

	private void streams() throws IOException {
		oos = new ObjectOutputStream(conn.getOutputStream());
		oos.flush();

		ois = new ObjectInputStream(conn.getInputStream());
		dispMessage("Streams");
		ArrayList<String> data = new ArrayList<String>();
		data.add("tInfo");
		data.add(ServerPanel.name);
		ServerPanel.nServer.send(data);

	}

	private void processConn() throws IOException {
		setButtonEnabled(true);
		while (true) {
			try {
				ArrayList<String> data = (ArrayList<String>) ois.readObject();
				if (data.get(0).equals("message")) {
					dispMessage("\n" + data.get(1));
				} else if (data.get(0).equals("stInfo")) {
					ServerPanel.studentList.addElement(data.get(1));
				} else if (data.get(0).equals("stHand")) {
					raisedIndex = ServerPanel.studentList.indexOf(data.get(1));
					ServerPanel.studentList.setElementAt(data.get(1) + "\u270B", raisedIndex);
				} else if (data.get(0).equals("stExit")) {
					int index = ServerPanel.studentList.indexOf(data.get(1));
					dispMessage("\n" + data.get(1) + " left the course");
					ServerPanel.studentList.remove(index);
				}

			} catch (ClassNotFoundException e) {
				dispMessage("Unknown");
			}
		}

	}

	private void closeConn() {
		dispMessage("Terminating Conn\n");
		try {
			oos.close();
			ois.close();
			conn.close();
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
			// System.out.println("Error1: " + e.toString());
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
