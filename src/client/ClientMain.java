package client;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClientMain {

	static ClientPanel nClientPanel;

	public static void main(String[] args) {

		Client nClient = new Client("127.0.0.1");
		ArrayList<String> data = new ArrayList<String>();

		nClientPanel = new ClientPanel(nClient);
		nClientPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nClientPanel.setVisible(true);
		nClientPanel.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				int result = JOptionPane.showConfirmDialog(nClientPanel, "Do you want to Exit ?",
						"Exit Confirmation : ", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					data.add("stExit");
					data.add(nClientPanel.student.toString());
					nClientPanel.nClient.send(data);
					nClientPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}

				else if (result == JOptionPane.NO_OPTION) {
					nClientPanel.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}

			}
		});
		nClientPanel.setExtendedState(JFrame.MAXIMIZED_BOTH);
		nClientPanel.setMinimumSize(new Dimension(650, 600));

		nClient.runClient();

	}
}
