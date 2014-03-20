import java.applet.*;
import java.awt.*;


public class Tron extends Applet implements Runnable
{
	
	private Thread	 m_Tron = null;

	private final int SIZE_X=320;
	private final int SIZE_Y=240;
	private final int NUM_PLAYERS=4;
	private int first_paint;
	private byte[] map;

	private int[] px;
	private int[] py;
	private int[] pdir;
	private int[] palive;
	private int[] pcolor;

	
	public Tron()
	{
		int i;

		map=new byte[SIZE_X*SIZE_Y];
		for(i=0; i<SIZE_X*SIZE_Y; i++) map[i]=0;
		for(i=0; i<SIZE_X; i++) {
			map[i]=1;
			map[SIZE_X*(SIZE_Y-1)+i]=1;
		}
		for(i=0; i<SIZE_Y; i++) {
			map[i*SIZE_X]=1;
			map[i*SIZE_X+SIZE_X-1]=1;
		}
		px=new int[NUM_PLAYERS];
		py=new int[NUM_PLAYERS];
		pdir=new int[NUM_PLAYERS];
		palive=new int[NUM_PLAYERS];
		pcolor=new int[NUM_PLAYERS];
		px[0]=100;
		py[0]=120;
		pdir[0]=1;
		px[1]=220;
		py[1]=120;
		pdir[1]=3;
		px[2]=160;
		py[2]=60;
		pdir[2]=2;
		px[3]=160;
		py[3]=180;
		pdir[3]=0;
		for(i=0; i<NUM_PLAYERS; i++) {
			palive[i]=1;
		}
		pcolor[0]=0xffffff;
		pcolor[1]=0xff0000;
		pcolor[2]=0x00ff00;
		pcolor[3]=0x0000ff;

		first_paint=1;
	}

	// APPLET INFO SUPPORT:
	//		The getAppletInfo() method returns a string describing the applet's
	// author, copyright date, or miscellaneous information.
    //--------------------------------------------------------------------------
	public String getAppletInfo()
	{
		return "Name: Tron\r\n" +
		       "Author: Kumaran\r\n";
	}


	
	public void init()
	{
        
    	resize(SIZE_X,SIZE_Y);

		enable();
		requestFocus();
	}

	
	public void destroy()
	{
		
	}

	
	public void paint(Graphics g)
	{
		if(first_paint==1) {
			g.setColor(new Color(0x0));
			g.fillRect(0,0,SIZE_X,SIZE_Y);
			g.setColor(new Color(0xffffff));
			g.drawRect(0,0,SIZE_X-1,SIZE_Y-1);
			first_paint=0;
		}
	}

	public void update(Graphics g) {
		paint(g);
	}

	
	public void start()
	{
		if (m_Tron == null)
		{
			m_Tron = new Thread(this);
			m_Tron.start();
		}
		
	}
	
	
	public void stop()
	{
		if (m_Tron != null)
		{
			m_Tron.stop();
			m_Tron = null;
		}

		
	}

	
	public void run()
	{
		int i=-1,tx=-1,ty=-1,alive_cnt;
		int[] dx={0,1,0,-1};
		int[] dy={-1,0,1,0};
		Graphics g=getGraphics();

		while (true)
		{
			try
			{
				alive_cnt=0;
				for(i=0; i<NUM_PLAYERS; i++) {
					if(palive[i]==1) {
						if(i>0) DoAI(i);
						tx=px[i]+dx[pdir[i]];
						ty=py[i]+dy[pdir[i]];
						if((tx<0) || (tx>=SIZE_X) || (ty<0) || (ty>=SIZE_Y) || 
							(map[tx+ty*SIZE_X]!=0)) {
							palive[i]=0;
						} else {
							px[i]=tx;
							py[i]=ty;
							map[tx+ty*SIZE_X]=(byte)(i+1);
						}
					}
					if(palive[i]==1) {
						alive_cnt++;
						g.setColor(new Color(pcolor[i]));
						g.drawLine(px[i],py[i],px[i],py[i]);
					}
				}
				if(palive[0]==0) {
					g.setColor(new Color(0xff7070));
					g.drawString("You Lose!",50,120);
					repaint();
					stop();
				} else if(alive_cnt==1) {
					g.setColor(new Color(0x7070ff));
					g.drawString("You Win!",50,120);
					repaint();
					stop();
				}

				repaint();
				Thread.sleep(25);
			}
			catch (InterruptedException e)
			{
				
				g.drawString("IException!",50,100);
				repaint();
				stop();
			}
			catch (Exception e) {
				g.drawString("Exception:"+e.toString()+"\n",50,100);
				repaint();
			}
		}
	}

	private void DoAI(int pnum) {
		int[] dx={0,1,0,-1};
		int[] dy={-1,0,1,0};
		int tdir;

		if(palive[pnum]!=1) return;
		if(map[(px[pnum]+dx[pdir[pnum]])+(py[pnum]+dy[pdir[pnum]])*SIZE_X]!=0) {
			if(Math.random()>0.5) {
				tdir=(pdir[pnum]+1)%4;
				if(map[(px[pnum]+dx[tdir])+(py[pnum]+dy[tdir])*SIZE_X]==0)
					pdir[pnum]=tdir;
				else
					pdir[pnum]=(pdir[pnum]+3)%4;
			} else {
				tdir=(pdir[pnum]+3)%4;
				if(map[(px[pnum]+dx[tdir])+(py[pnum]+dy[tdir])*SIZE_X]==0)
					pdir[pnum]=tdir;
				else
					pdir[pnum]=(pdir[pnum]+1)%4;
			}
		} else if(Math.random()>0.985) {
			if(Math.random()>0.5) {
				tdir=(pdir[pnum]+1)%4;
				if(map[(px[pnum]+dx[tdir])+(py[pnum]+dy[tdir])*SIZE_X]==0)
					pdir[pnum]=tdir;
			} else {
				tdir=(pdir[pnum]+3)%4;
				if(map[(px[pnum]+dx[tdir])+(py[pnum]+dy[tdir])*SIZE_X]==0)
					pdir[pnum]=tdir;
			}
		}
	}

	public boolean keyDown(Event evt,int nKey) {
		if(evt.key==106) pdir[0]=(pdir[0]+3)%4;
		if(evt.key==107) pdir[0]=(pdir[0]+1)%4;
		if(evt.key==Event.LEFT) pdir[0]=(pdir[0]+3)%4;
		if(evt.key==Event.RIGHT) pdir[0]=(pdir[0]+1)%4;

		return(true);
	}

	private void ResizeImage() {
		resize(SIZE_X,SIZE_Y);
	}

}