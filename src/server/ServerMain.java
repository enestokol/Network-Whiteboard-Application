package server;

import java.awt.Dimension;
import javax.swing.JFrame;

public class ServerMain {

	static ServerPanel nServerPanel;

	public static void main(String[] args) {

		Server nServer = new Server();

		nServerPanel = new ServerPanel(nServer);
		nServerPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nServerPanel.setVisible(true);
		nServerPanel.setExtendedState(JFrame.MAXIMIZED_BOTH);
		nServerPanel.setMinimumSize(new Dimension(650, 600));

		nServer.runServer();

	}

}
