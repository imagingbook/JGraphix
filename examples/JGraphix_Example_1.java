import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import imagingbook.jgraphix.DrawFrame;

/**
 * Sample program demonstrating the use of the {@link DrawFrame} class.
 * Shows the use of basic AWT graphics operations. For details see the
 * <a href="https://docs.oracle.com/javase/8/docs/api/index.html?java/awt/Graphics2D.html">
 * Graphics2D API documentation</a>.
 * 
 * @author W. Burger
 * @version 2017-11-18
 */
public class JGraphix_Example_1 {

	public static void main(String[] args) {
		DrawFrame df = DrawFrame.create("MyDrawing", 200, 300);
		Graphics2D g = df.getGraphics2D();
		
		float linewidth = 5.0f;	// line width = 5 pixels
		g.setStroke(new BasicStroke(linewidth));
		
		g.setColor(Color.BLUE);
		g.drawLine(40, 10, 10, 40);
		g.draw(new Line2D.Double(70.2, 10.3, 100.4, 40.5));

		g.fillOval(10, 60, 30, 30);
		g.drawOval(60, 60, 30, 30);
		g.fillRoundRect(110, 60, 30, 30, 10, 10);
		g.drawRoundRect(160, 60, 30, 30, 10, 10);

		g.setColor(Color.GREEN.darker());
		g.fillArc(10, 110, 30, 30, 45, 240);
		g.fillArc(60, 110, 30, 30, 45 + 240, 360 - 240);
		g.fillArc(110, 110, 30, 30, 90, 270);
		g.fillArc(160, 110, 30, 30, 270, 270);

		g.setColor(Color.MAGENTA);
		g.drawArc(10, 160, 30, 30, 45, 240);
		g.drawArc(60, 160, 30, 30, 45 + 240, 360 - 240);
		g.drawArc(110, 160, 30, 30, 90, 270);
		g.drawArc(160, 160, 30, 30, 270, 270);

		g.setColor(Color.ORANGE);
		g.fillPolygon(new int[] { 10, 40, 10, 40 }, new int[] { 210, 210, 240, 240 }, 4);
		g.drawPolygon(new int[] { 60, 90, 60, 90 }, new int[] { 210, 210, 240, 240 }, 4);
		g.drawPolyline(new int[] { 110, 140, 110, 140 }, new int[] { 210, 210, 240, 240 }, 4);
		g.drawPolyline(new int[] { 160, 160, 190, 190 }, new int[] {240, 210, 240, 210 }, 4);

		// Printing texts
		g.setColor(Color.BLACK);
		g.setFont(new Font("Monospaced", Font.PLAIN, 14));
		g.drawString("Drawn with JGraphix", 10, 275);

		df.refresh();

		System.out.println("done.");
	}

}
