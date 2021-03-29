package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CFrame extends JPanel implements ActionListener{
	
	ArrayList<Person> people = new ArrayList<Person>();
	ArrayList<Point> points = new ArrayList<Point>();
	int time = 0;
	
	public static void main(String[] arg) {
		
		CFrame c = new CFrame();
	}
	
	public void paint(Graphics g) {
		
		time += 16;
		points.add(new Point(time/16, Person.numInfected));
		
		//Super here will force the refresh of the page
		super.paintComponent(g);
		for(Person p: people) {
			p.paint(g);
			//System.out.println(Integer.toString(p.x));
			for(int i=0; i < people.size(); i++) {
				for(int j = i+1; j < people.size(); j++) {
					people.get(i).collision(people.get(j));
				}
			}
		}
		
	}
	

	public CFrame() {
		
		//JFarme
		JFrame frame = new JFrame("Social Distance Simulation");
		frame.setSize(1500,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		for(int i = 0; i < 250; i++) {
			people.add(new Person());
		}
		
		//Timer for animation, every x seconds will invoke the actionPerformed method
		Timer t = new Timer(16, this);
		t.start();
		
		//Add the actual CFrame class to JFrame
		frame.add(this);
		
		frame.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		repaint();
		
	}
	
	
}
