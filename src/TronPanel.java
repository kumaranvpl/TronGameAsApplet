import java.awt.*;
import java.applet.*;

public class TronPanel extends Applet implements Runnable
{
int p1SpeedX = 0;
int p1SpeedY = 0;
int p1X = 10;
int p1Y = 10;
int appletWidth; 
int appletHeight;
Image Buffer;
Graphics gBuffer;

public void init()
{
appletWidth = size().width;
appletHeight = size().height;

Buffer=createImage(appletWidth,appletHeight);
gBuffer=Buffer.getGraphics();
}

public void start ()
{

Thread th = new Thread (this);

th.start ();
}

public void stop()
{

}

public void destroy()
{

}

public boolean keyDown (Event e, int key)
{

if (key == Event.LEFT)
{

p1SpeedX = -1;
p1SpeedY = 0;
}

else if (key == Event.RIGHT)
{

p1SpeedX = 1;
p1SpeedY = 0;
}

else if (key == Event.UP)
{
p1SpeedY = -1;
p1SpeedX = 0;
}
else if(key == Event.DOWN)
{
p1SpeedY = 1;
p1SpeedX = 0;
}
else
{

System.out.println ("Charakter: " + (char)
key + " Integer Value: " + key);
}

return true;
}

public void run ()
{

Thread.currentThread().setPriority
(Thread.MIN_PRIORITY);


while (true)
{

if (p1X > appletWidth)
{

p1X = 0;
}

else if (p1X < 0)
{

p1X = appletWidth;
}

if (p1Y > appletHeight)
{

p1Y = 0;
}

else if (p1Y < 0)
{

p1Y = appletHeight;
}


p1X += p1SpeedX;
p1Y += p1SpeedY;


repaint();

try
{

Thread.sleep (5);
}
catch (InterruptedException ex)
{

}


Thread.currentThread().setPriority
(Thread.MAX_PRIORITY);
}
}


public void paint(Graphics g)
{

gBuffer.setColor(Color.red);
gBuffer.fillOval(p1X, p1Y, 2 * 5, 2 * 5);

g.drawImage (Buffer,0,0, this);
}

}