import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import imagingbook.jgraphix.DrawFrame;

/**
 * Sample program demonstrating the use of the {@link DrawFrame} class. Courtesy
 * by Heinz Dobler (originally implemented in Pascal).
 * 
 * @author W. Burger
 * @version 2017-11-21
 */
public class JGraphix_Example_3 {

    public static void main(String[] args) throws InterruptedException {
	DrawFrame df = DrawFrame.create("example3", 800, 600);
	Graphics2D g = df.getGraphics2D();

	int n = 50;

	g.setFont(new Font("Dialog", Font.PLAIN, 16));
	g.drawString("Some Text", 10, 20);

	double incX = (double) df.getW() / n;
	double incY = (double) df.getH() / n;

	g.setStroke(new BasicStroke(1));
	g.setColor(Color.BLACK);
	g.drawOval(50, 50, 200, 100);

	for (int i = 1; i <= n; i++) {
	    int x1 = (int) Math.round(i * incX);
	    int y1 = 0;
	    int x2 = (int) Math.round(incX * (n - i));
	    int y2 = (int) Math.round(i * incY);
	    g.drawLine(x1, y1, x2, y2);
	}

	df.refresh();
    }

}
