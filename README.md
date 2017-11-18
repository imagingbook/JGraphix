# JGraphix
Simple setup for performing non-interactive graphics operations in Java.
Requires Java 1.8.

## Installation
Download the JAR file from https://github.com/imagingbook/JGraphix/tree/master/jars
and add it to your Java project (add to build path).

## Typical Usage

````
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import imagingbook.jgraphix.DrawFrame;

...

DrawFrame df = DrawFrame.create();
Graphics2D g = df.getGraphics2D();
   
g.setStroke(new BasicStroke(5));
g.setColor(Color.blue);
   
g.drawLine(40, 10, 10, 40);
g.fillOval(10, 60, 30, 30);
g.drawOval(60, 60, 30, 30);
df.refresh();	// display drawing
 
df.clear();	// erase drawing
g.setColor(Color.green);
g.fillRoundRect(110, 60, 30, 30, 10, 10);
g.drawRoundRect(160, 60, 30, 30, 10, 10);
df.refresh();	// display drawing
...  
````

## Examples
https://github.com/imagingbook/JGraphix/tree/master/examples

## Documentation
https://imagingbook.github.io/JGraphix/javadoc/index.html
