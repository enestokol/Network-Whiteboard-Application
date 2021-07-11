package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class ClientBoard extends JPanel {

	public ClientBoard() {
		super();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(ClientPanel.c);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (ClientPanel.type != 0) {
			for (int i = 0; i < ClientPanel.shapes.size(); i++) {
				if (ClientPanel.shapes.get(i).type == 1) {
					g2d.drawRect(ClientPanel.shapes.get(i).x1, ClientPanel.shapes.get(i).y1,
							ClientPanel.shapes.get(i).x2 - ClientPanel.shapes.get(i).x1,
							ClientPanel.shapes.get(i).y2 - ClientPanel.shapes.get(i).y1);
				} else if (ClientPanel.shapes.get(i).type == 2) {
					g2d.drawOval(ClientPanel.shapes.get(i).x1, ClientPanel.shapes.get(i).y1,
							((ClientPanel.shapes.get(i).x2 - ClientPanel.shapes.get(i).x1)
									+ ClientPanel.shapes.get(i).y2 - ClientPanel.shapes.get(i).y1) / 2,
							((ClientPanel.shapes.get(i).x2 - ClientPanel.shapes.get(i).x1)
									+ ClientPanel.shapes.get(i).y2 - ClientPanel.shapes.get(i).y1) / 2);
				} else if (ClientPanel.shapes.get(i).type == 3) {
					g2d.drawLine(ClientPanel.shapes.get(i).x1, ClientPanel.shapes.get(i).y1,
							ClientPanel.shapes.get(i).x2, ClientPanel.shapes.get(i).y2);
				}

			}
		}

		ClientPanel.ShapeCount.setText("Count:" + ClientPanel.count);

	}

}
