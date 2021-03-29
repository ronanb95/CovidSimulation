package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.RenderingHints;


public class Person {
	
	//location
	int x, y;
	//velocity
	int vx, vy;
	//status, 0 = fine, 1 = infected, 2 = recovered, 3 = vaccinated
	int status = 0;
	//Recovery time
	int recoveryTime;
	//Vaccine
	int timeUntilVaccine;
	//Social Distance being practiced, 0.5 == 50% of population, 0.8 == 80% of population
	double amountDistancing = 0.8;
	
	
	static int numInfected = 0;
	static int totalInfections = 0;
	static int numVaccinated = 0;
	static int numRecovered = 0;
	
	
	public Person() {
		//Multiply the result by range + 1 and add the min
		x = (int)(Math.random()*1490+0);
		y = (int)(Math.random()*750+1);
		
		//8% of people are infected 
		if(Math.random() < .08) {
			status = 1;
		}	
		
		if (Math.random() > amountDistancing) {
			vx = (int)(Math.random()*(10+1) +-5);
			vy = (int)(Math.random()*(10+1) +-5);
		}
		
		//7 - 5 seconds
		recoveryTime = (int)(Math.random()*(7000-5000+1) + 5000);
		
		//30 - 5 seconds
		timeUntilVaccine = (int)(Math.random()*(30000-5000+1) + 5000);
	}
	
	public void collision(Person p2) {
		//Using rectangle as built in collision detection in class
		Rectangle per1 = new Rectangle(this.x, this.y, 10, 10);
		Rectangle per2 = new Rectangle(p2.x, p2.y, 10, 10);
		
		if(per1.intersects(per2)) {
			
			if(this.status == 1 && p2.status == 0) {
				p2.status = 1;
				numInfected++;
				totalInfections++;
			} else if(this.status == 0 && p2.status == 1) {
				this.status = 1;
				numInfected++;
				totalInfections++;
			}
		}
	}
	
	
	public void paint(Graphics g) {
		switch(status) {
			case 0:
				g.setColor(Color.LIGHT_GRAY);
				break;
			case 1:
				g.setColor(Color.RED);
				break;
			case 2:
				g.setColor(Color.BLUE);
				break;
			case 3:
				g.setColor(Color.GREEN);
		}
		
		timeUntilVaccine -= 16;
		if(status == 1 && timeUntilVaccine <= 0) {
			status = 3;
			numVaccinated++;
		} else if (status == 2 && timeUntilVaccine <= 0) {
			status = 3;
			numVaccinated++;
		}
		
		if(status == 1) {
			recoveryTime -= 16;
			if(recoveryTime <= 0) {
				status = 2;
				numRecovered ++;
				numInfected --;
			}
		}
		
		x += vx;
		y += vy;
		
		if(x < 0 || x >= 1490) {
			vx *= -1;
		}
		if(y < 0 || y >= 750) {
			vy *= -1;
		}
		
		
		
		
		g.fillOval(x, y, 10, 10);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK);
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawString("Number of infected: "+ numInfected,20,20); 
        g2.drawString("Number of recovered: "+ numRecovered,20,40); 
        g2.drawString("Number of vaccinated: "+ numVaccinated,20,60); 
        g2.drawString("Total Infections Over Time: "+ totalInfections,20,100);
	}
	
	
}
