package server;

import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.LayoutManager;
import java.awt.RenderingHints;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.util.Arrays;

import javax.swing.JPanel;

public class ServerBoard extends JPanel {

	public ServerBoard() {
		super();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(ServerPanel.c);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (ServerPanel.type != 0) {
			for (int i = 0; i < ServerPanel.shapes.size(); i++) {
				if (ServerPanel.shapes.get(i).type == 1) {
					g2d.drawRect(ServerPanel.shapes.get(i).x1, ServerPanel.shapes.get(i).y1,
							ServerPanel.shapes.get(i).x2 - ServerPanel.shapes.get(i).x1,
							ServerPanel.shapes.get(i).y2 - ServerPanel.shapes.get(i).y1);
				} else if (ServerPanel.shapes.get(i).type == 2) {
					g2d.drawOval(ServerPanel.shapes.get(i).x1, ServerPanel.shapes.get(i).y1,
                            ((ServerPanel.shapes.get(i).x2-ServerPanel.shapes.get(i).x1)+ServerPanel.shapes.get(i).y2-ServerPanel.shapes.get(i).y1)/2,
                            ((ServerPanel.shapes.get(i).x2-ServerPanel.shapes.get(i).x1)+ServerPanel.shapes.get(i).y2-ServerPanel.shapes.get(i).y1)/2);
				} else if (ServerPanel.shapes.get(i).type == 3) {
					g2d.drawLine(ServerPanel.shapes.get(i).x1, ServerPanel.shapes.get(i).y1,
							ServerPanel.shapes.get(i).x2, ServerPanel.shapes.get(i).y2);
				}

			}
		}

	}

}
