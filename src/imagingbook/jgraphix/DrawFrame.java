package imagingbook.jgraphix;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessControlException;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

/**
 * This class provides a simple setup for performing non-interactive drawing
 * operations in Java. It is intended for educational use and is neither safe nor
 * recommended for real applications. The implementation is based on Swing and AWT 
 * graphics. All standard AWT graphics operations can be used (see the
 * <a href="https://docs.oracle.com/javase/8/docs/api/index.html?java/awt/Graphics2D.html">
 * Graphics2D API documentation</a> for details).
 * The rendered screen graphics can be saved to a PNG file by pressing
 * Ctrl-s (Windows) or Cmd-s (MacOS) on the open window.
 * <p>
 * Typical usage example:
 * <pre>
 * import java.awt.Color;
 * import java.awt.Font;
 * import java.awt.Graphics2D;
 * import java.awt.geom.Line2D;
 * 
 * import imagingbook.jgraphix.DrawFrame;
 * 
 * ...
 * 
 * DrawFrame df = DrawFrame.create();
 * Graphics2D g = df.getGraphics2D();
 *    
 * g.setStroke(new BasicStroke(5));
 * g.setColor(Color.blue);
 *    
 * g.drawLine(40, 10, 10, 40);
 * g.fillOval(10, 60, 30, 30);
 * g.drawOval(60, 60, 30, 30);
 * df.refresh();	// display drawing
 *    
 * df.clear();	// erase drawing
 * g.setColor(Color.green);
 * g.fillRoundRect(110, 60, 30, 30, 10, 10);
 * g.drawRoundRect(160, 60, 30, 30, 10, 10);
 * df.refresh();	// display drawing
 * ...  
 * </pre>
 * 
  * <p>
 * <b>Repository:</b> <a href="https://github.com/imagingbook/JGraphix">
 * https://github.com/imagingbook/JGraphix</a>
 * <br>
 * <b>License:</b> BSD 2-clause "Simplified" License
 * 
 * @author W. Burger
 * @version 2017-11-18
 *
 */
public class DrawFrame extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;

	static {
		String nativeLF = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(nativeLF);
		} catch (Exception e) { }
		JFrame.setDefaultLookAndFeelDecorated(true);
		JComponent.setDefaultLocale(Locale.US);
	}
	
	private static DrawFrame theOne = null;	// there can only be one instance (singleton)
	
	public static int DEFAULT_WIDTH = 600;
	public static int DEFAULT_HEIGHT = 400;
	public static String DEFAULT_TITLE = DrawFrame.class.getSimpleName();
	public static double DEFAULT_STROKE_WIDTH = 2.0;
	
	public static Color BACKGROUND_COLOR = Color.WHITE;
	public static Color FOREGROUND_COLOR = Color.BLACK;
	
	private final DrawPanel panel;
	
	/**
	 * Creates and returns a new {@link DrawFrame} instance or the existing
	 * (singleton) instance if already existent. 
	 * Since only one instance of {@link DrawFrame} is permitted, this method should
	 * be called only once.
	 * @param title The title of the frame (displayed in the window's top bar)
	 * @param width The width of the actual drawing area.
	 * @param height The height of the actual drawing area.
	 * @return An instance of {@link DrawFrame}.
	 */
	public static DrawFrame create(String title, int width, int height) {
		if (theOne == null) {
			theOne = new DrawFrame(title, width, height);
		}
		else {
			System.err.println(DrawFrame.class.getSimpleName() +
					".create(): only one Graphix instance allowed!");
		}
		return theOne;
	}
	
	/**
	 * Creates and returns a new {@link DrawFrame} instance or the existing
	 * (singleton) instance if already existent. 
	 * {@link DEFAULT_TITLE},  {@link DEFAULT_WIDTH} and  {@link DEFAULT_HEIGHT}
	 * are used to specify the title and size of the actual drawing area.
	 * Since only one instance of {@link DrawFrame} is permitted, this method should
	 * be called only once.
	 * @return An instance of {@link DrawFrame}.
	 */
	public static DrawFrame create() {
		return create(DEFAULT_TITLE, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	private DrawFrame(String title, int width, int height) {
		setTitle(title);
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new DrawPanel(width, height);
		add(panel);
		pack();
		panel.makeBufferedImage();	
		setVisible(true);
		addKeyListener(this);
	}
	
	/**
	 * Retrieves the {@link Graphics2D} instance for drawing into
	 * this {@link DrawFrame}.
	 * @return The {@link Graphics2D} instance.
	 */
	public Graphics2D getGraphics2D() {
		return panel.g;
	}
	
	// -------------------------------------------------------------------
	
	class DrawPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private BufferedImage image = null;
		protected Graphics2D g = null;
		private final Dimension preferredSize;

	    public DrawPanel(int width, int height) {
	    	preferredSize = new Dimension(width, height);
	        setBackground(BACKGROUND_COLOR);
	    }
	    
	    void makeBufferedImage() {
	    	image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
	    	g = image.createGraphics();
	    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setStroke(new BasicStroke((float) DEFAULT_STROKE_WIDTH));
			clear();
			g.setColor(FOREGROUND_COLOR);
	    }

	    public Dimension getPreferredSize() {
	        return preferredSize;
	    }

	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);       
	        g.drawImage(image, 0, 0, null);
	    }
	    
	    private void clear() {
	    	Color c = g.getColor();
	    	g.setColor(getBackground());
	    	g.fillRect(0, 0, getWidth(), getHeight());
	    	g.setColor(c);
	    }
	    
	    
	}
	
	/**
	 * Returns the width of the actual (inner) drawing area.
	 * @return The width of the drawing area.
	 */
	public int getW() {
		return panel.getWidth();
	}
	
	/**
	 * Returns the height of the actual (inner) drawing area.
	 * @return The height of the drawing area.
	 */
	public int getH() {
		return panel.getHeight();
	}
	
	/**
	 * Clears the drawing area and fills it with the specified
	 * {@link BACKGROUND_COLOR}.
	 */
	public void clear() {
		panel.clear();
	}

	/**
	 * Displays the current graphics in the screen window of this 
	 * {@link DrawFrame}. Note that all graphics are initially drawn
	 * to a hidden image, which is not automatically displayed.
	 */
	public void refresh() {
		panel.repaint();
	}
	
	// -------------------------------------------------------------------------------	

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	// KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
	// KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());

	@Override
	public void keyPressed(KeyEvent e) {
		// Windows: CTRL-S (e.isControlDown())
		// MacOS: CTRL-S (e.isMetaDown())
	    if ((e.isControlDown() || e.isMetaDown()) && e.getKeyCode() == KeyEvent.VK_S) {
	    	File f = selectFile();
	    	if (f != null) {
	    		saveTo(f);
	    	}
	    }
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	// -------------------------------------------------------------------------------	
	
	private File selectFile() {
		JFileChooser fc = null;
		try {
			fc = new JFileChooser();
			fc.setFileFilter(new FileFilter() {

				@Override
				public boolean accept(File f) {
					return f.isDirectory() || f.getName().endsWith(".png");
				}

				@Override
				public String getDescription() {
					return "Portable Network Graphic file";
				}
			});
		} catch (AccessControlException ace) {  }
		
		String suggestedFileName = this.getTitle() + ".png";
		Path localPath = Paths.get("./");
		fc.setCurrentDirectory(localPath.toFile());
		fc.setSelectedFile(new File(suggestedFileName));
		int ret = fc.showSaveDialog(this);
		if (ret == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();
		}
		else
			return null;
	}
	
	/**
	 * Saves the current graphics as PNG raster image.
	 * @param file A {@link File} instance specifying the path of the PNG file.
	 * @return {@code true} if the operation was successful, {@code false} otherwise.
	 */
    public boolean saveTo(File file) {
    	boolean success = false;
        try {
			success = ImageIO.write(panel.image, "PNG", file);
		} catch (IOException e) {
			System.err.println("Could not write file " + file.getAbsolutePath());
		}
        if (success)
        	System.out.println("Drawing saved to " + file.getAbsolutePath());
        return success;
    }

}
