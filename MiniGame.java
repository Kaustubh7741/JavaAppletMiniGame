/*
GAME MANUAL

Game elements-
Blue rectangle - This is you.
Red rectangle - The bad guy.
Window border(size) - The limits of mortal realm (Beyond which, it's game over).

Controls (I tried to make it simple)-
Move mouse in any direction (on a flat surface) to move your object down.
Press and hold a click while moving the mouse (again on a flat surface) to move yourself up.

Objective-
Just avoid the bad guy as long as you can.
Oh, and stay in the window.

Notes - Please JDK 8.0 or lower

*/

package project;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

@SuppressWarnings("deprecation")
public class MiniGame extends Applet implements MouseListener,MouseMotionListener{
	
	/*GUIDE FOR VARIABLES (TECHNICAL)
	xlw	-->	Lower X co-ordinate of window (playable area)
	ylw	-->	Lower Y co-ordinate of window
	xhw	-->	Higher X co-ordinate of window
	yhw	--> Higher Y co-ordinate of window
	xlu	-->	Lower X co-ordinate of user object (blue rectangle)
	ylu	-->	Lower Y co-ordinate of user object
	xhu	-->	Higher X co-ordinate of user object
	yhu	-->	Higher Y co-ordinate of user object
	obsxl --> Lower X co-ordinate of obstacle (red rectangle)
	obsxh --> Higher X co-ordinate of obstacle
	obsyl --> Lower Y co-ordinate of obstacle
	obsyh --> Higher Y co-ordinate of obstacle
	intersection --> Variable to determine game over
	score --> Obstacles dodged

	*/
	
	private static final long serialVersionUID  =  1L;
	static final int xlw = 0,ylw = 0,xhw = 800,yhw = 500;
	int xlu,ylu,xhu,yhu;
	int obsxl,obsxh,obsyl,obsyh;
	int intersection = 0;
	static int score = 0;
	//int width = getSize().width;
    // = ]int height = getSize().height;
    
	public void init()
	{
		xlu = 50;
		ylu = 100;
		xhu = 80;
		yhu = 200;
		
		//this.setSize(new Dimension(height,width));*/
		
		addMouseListener(this);
		addMouseMotionListener(this);
		setBackground(Color.black);
		repaint();
	}
	
	public void paint(Graphics g){
		this.setSize(new Dimension(xlw+xhw,ylw+yhw));
		checkintersection();
		if(intersection == 1)
		{
			g.setColor(Color.white);
			g.drawString("GAME OVER!    SCORE: "+(score-1), 100, 100);
			return;
		}
		else
		{
			int width = xhu-xlu,height = yhu-ylu;
			g.setColor(Color.white);
			g.drawRect(xlw, ylw, xhw, yhw);
			g.setColor(Color.blue);
			g.fillRect(xlu, ylu, width, height);
			g.setColor(Color.RED);
			int width_obstacle  =  obsxh - obsxl;
			int height_obstacle  =  obsyh - obsyl;
			g.fillRect(obsxl, obsyl, width_obstacle, height_obstacle);
			if(obsxl != xlw)
			{
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				g.clearRect(obsxl, obsyl, width_obstacle, height_obstacle);
				repaint();
				obsxl -= 2;
				obsxh -= 2;
			}
			else
			{
				score++;
				g.clearRect(obsxl, obsyl, width_obstacle, height_obstacle);
				obsxl = xhw-100;
				obsxh = xhw;
				try {
					trans();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}//end of outermost else
	}
	
	public void start(){
		
		try {
				trans();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void trans() throws InterruptedException
	{
		Random i  =  new Random();
		obsyh  =  i.nextInt(yhw);
		while(obsyh<51)
			obsyh  =  i.nextInt(yhw);
		obsyl  =  obsyh-50;
		repaint();
	}
	
	//CORE LOGIC
	public void checkintersection()
	{
		if(ylu <= ylw || yhu >= yhw)	//checking y of user obj with window
			intersection = 1;
		//now checking user obj with obstacle
		if(xhu >= obsxl && xlu <= obsxh)
		{
			if((yhu >= obsyl && yhu <= obsyh) || (ylu <= obsyh && ylu >= obsyl) || (ylu <= obsyl && yhu >= obsyh))
				intersection = 1;
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseDragged(MouseEvent e) {
		ylu -= 2;
		yhu -= 2;
		//checkintersection();
		if(intersection == 0)
			repaint();		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		ylu += 3;
		yhu += 3;
		//checkintersection();
		if(intersection == 0)
			repaint();
	}
	
	
}
