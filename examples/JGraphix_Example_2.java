import java.awt.Color;
import java.awt.Graphics2D;

import imagingbook.jgraphix.DrawFrame;

/**
 * Sample program demonstrating the use of the {@link DrawFrame} class.
 * Shows an animation moving a filled and deforming oval.
 * 
 * @author W. Burger
 * @version 2017-12-05
 */
public class JGraphix_Example_2 {

    public static void main(String[] args) {
	DrawFrame df = DrawFrame.create();
	Graphics2D g = df.getGraphics2D();

	g.setColor(Color.BLUE);

	for (int i = 0; i < 100; i++) {
	    df.clear();
	    g.fillOval(80 + 3 * i, 40 + 2 * i, 30, 30 + i);
	    df.refresh();
	    DrawFrame.sleep(25); // wait for 25 ms
	}

	System.out.println("done");
    }

}
