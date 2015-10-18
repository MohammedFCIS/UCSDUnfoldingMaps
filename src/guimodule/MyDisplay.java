package guimodule;

import processing.core.PApplet;

public class MyDisplay extends PApplet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void setup() {
		size(400,400);
		background(200, 200, 200);
	}


	public void draw() {
		fill(255,255,0);//yellow
		ellipse(200, 200, 390, 390);//the face
		fill(0,0,0);//black
		ellipse(120,130,50,70);//left eye		
		ellipse(270,130,50,70);//right eye
		arc(200,280,75,75,0,PI);//the smile :)
	}

	
	
	
	

}
