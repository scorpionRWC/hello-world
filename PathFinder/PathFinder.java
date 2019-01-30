import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;
import java.util.Scanner;
import java.io.*;
import java.util.*;

public class PathFinder extends JFrame {
	java.util.List<Point2D> pointSet;
	java.util.List<Line2D> lineSet;
	double knownBestPathLength, currentPathLength;
	Point2D topPoint, rightPoint, bottomPoint, leftPoint;
	boolean readyToPaint;
	private final int FrameX = 1000, FrameY = 600;
    private Scanner scan = new Scanner(System.in);

	public PathFinder(String fileName){

		pointSet = new ArrayList<Point2D>();
		lineSet = new ArrayList<Line2D>();		
        setSize(FrameX,FrameY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
		readyToPaint = false;
		if(fileName.equals("RandomCities.txt")){
			readyToPaint = true;
		}
		try{
			 Scanner inFile = new Scanner(new FileInputStream(fileName));

			 knownBestPathLength = inFile.nextInt();

			 topPoint = rightPoint = bottomPoint = leftPoint = new Point2D.Double(inFile.nextDouble(),inFile.nextDouble());
			 pointSet.add(leftPoint);

			 while(inFile.hasNext())
			 {
			 	Point2D temp = new Point2D.Double(inFile.nextDouble(),inFile.nextDouble()); 
			 	pointSet.add(temp);
			 	if(temp.getY() > topPoint.getY()){
			 		topPoint = temp;
			 	}
			 	if(temp.getX() > rightPoint.getX()){
			 		rightPoint = temp;
			 	}
			 	if(temp.getY() < bottomPoint.getY()){
			 		bottomPoint = temp;
			 	}
			 	if(temp.getX() < leftPoint.getX()){
			 		leftPoint = temp;
			 	}
			 }

			 inFile.close();

			 System.out.println("Successful File Reading");
			 System.out.println("Point Count: " + pointSet.size());
		 }
		 catch(FileNotFoundException fnfe){
		 	System.out.println("File not found!!");
		 } 

		 // for(Point2D p : pointSet){
		 // 	System.out.println(p);
		 // }

		 // System.out.println("Top: " + topPoint);
		 // System.out.println("Right: " + rightPoint);
		 // System.out.println("Bottom: " + bottomPoint);
		 // System.out.println("Left: " + leftPoint);
		 // System.out.println("Press Enter");
		 // scan.nextLine();
		 //initializeEdges();
		 findOuterPoints();

	   		// Use this to debug one step at time
	   		/////////////////////////
		 //    System.out.println("Press Enter");
	  //  		readyToPaint = true;
			// repaint();
	  //  		scan.nextLine();
	   		////////////////////////

		currentPathLength = 0;
		for(Line2D l : lineSet){
			currentPathLength += l.getP1().distance(l.getP2());
		}
		 findSmallestStretches();
		 results();
		 repaint();
	}

	public void findOuterPoints(){
		lineSet.addAll(findTopRightArc());

	   		// Use this to debug one step at time
	   		/////////////////////////
		 //    System.out.println("Press Enter");
	  //  		readyToPaint = true;
			// repaint();
	  //  		scan.nextLine();
	   		////////////////////////

		lineSet.addAll(findBottomRightArc());

	   		// Use this to debug one step at time
	   		/////////////////////////
		 //    System.out.println("Press Enter");
	  //  		readyToPaint = true;
			// repaint();
	  //  		scan.nextLine();
	   		////////////////////////

		lineSet.addAll(findBottomLeftArc());

	   		// Use this to debug one step at time
	   		/////////////////////////
		 //    System.out.println("Press Enter");
	  //  		readyToPaint = true;
			// repaint();
	  //  		scan.nextLine();
	   		////////////////////////

		lineSet.addAll(findTopLeftArc());
	}

	// public void initializeEdges(){
	// 	lineSet.add(new Line2D.Double(topPoint,rightPoint));
	// 	lineSet.add(new Line2D.Double(rightPoint,bottomPoint));
	// 	lineSet.add(new Line2D.Double(bottomPoint,leftPoint));
	// 	lineSet.add(new Line2D.Double(leftPoint,topPoint));
	// 	pointSet.remove(topPoint);
	// 	pointSet.remove(rightPoint);
	// 	pointSet.remove(bottomPoint);
	// 	pointSet.remove(leftPoint);
	// 	currentPathLength = 0;
	// 	for(Line2D l : lineSet){
	// 		currentPathLength += l.getP1().distance(l.getP2());
	// 	}
	// }

	public void findSmallestStretches(){
		System.out.println("Stretching");
		while(!pointSet.isEmpty()){

	   		// Use this to debug one step at time
	   		/////////////////////////
		 //    System.out.println("Press Enter");
	  //  		readyToPaint = true;
			// repaint();
	  //  		scan.nextLine();
	   		////////////////////////
	   		
			double shortestPath = currentPathLength * 2;
			Line2D bestLine = null;
			Point2D bestPoint = null;

			for(Line2D l : lineSet){
				double currentLineLength = l.getP1().distance(l.getP2());
				for(Point2D p : pointSet){
					double dist1 = l.getP1().distance(p);
					double dist2 = l.getP2().distance(p);
					double tempLength = currentPathLength - currentLineLength + dist1 + dist2;
					if(tempLength < shortestPath){
						shortestPath = tempLength;
						bestLine = l;
						bestPoint = p;
					}
				}
			}

			currentPathLength = shortestPath;

			lineSet.add(new Line2D.Double(bestLine.getP1(), bestPoint));
			lineSet.add(new Line2D.Double(bestPoint, bestLine.getP2()));
			lineSet.remove(bestLine);
			pointSet.remove(bestPoint);
			System.out.println("Point Count: " + pointSet.size());
		}
	}

	public void results(){
		System.out.println("Num lines: " + lineSet.size());
		System.out.println("Known Best Length: " + knownBestPathLength);
		System.out.println("My Found Length: " + currentPathLength);
		double percentDifference = (currentPathLength - knownBestPathLength) / ( (currentPathLength + knownBestPathLength) / 2 ) * 100;
		System.out.println("Percent Difference: " + percentDifference);
	}

	private java.util.List<Line2D> findTopRightArc(){
		System.out.println("Top Right");
		// System.out.println("where is left? " + pointSet.indexOf(leftPoint));
		// System.out.println("where is top? " + pointSet.indexOf(topPoint));
		// System.out.println("where is bottom? " + pointSet.indexOf(bottomPoint));
		// System.out.println("where is right? " + pointSet.indexOf(rightPoint));
		java.util.List<Line2D> temp = new ArrayList<Line2D>();
		if(topPoint == rightPoint){
			return temp;
		}
		Point2D nextPoint = topPoint;
		Point2D bestPoint;
		do{
    		double bestRelativeAngle = 1000, relativeAngle = 0;
			bestPoint = bottomPoint;
			for(Point2D p : pointSet){
				if((p.getY() < nextPoint.getY() && p.getX() > nextPoint.getX())){
    			// looks for the most horizontal angle from start point to determine which point is "most outer"
	    			relativeAngle = Math.atan((nextPoint.getY() - p.getY()) / (p.getX() - nextPoint.getX()));
	    			if(relativeAngle < bestRelativeAngle){
	    				bestRelativeAngle = relativeAngle;
		    			bestPoint = p;
	    			}
    			//}
				// if(p.getY() > bestPoint.getY() && p.getX() > nextPoint.getX()){
				// 	bestPoint = p;
				}
			}
			temp.add(new Line2D.Double(nextPoint, bestPoint));
			nextPoint = bestPoint;
			//System.out.println(nextPoint);
			pointSet.remove(bestPoint);
			System.out.println("Point Count: " + pointSet.size());
		}while(nextPoint != rightPoint);
		return temp;
	}
	private java.util.List<Line2D> findBottomRightArc(){
		System.out.println("Bottom Right");
		// System.out.println("where is left? " + pointSet.indexOf(leftPoint));
		// System.out.println("where is top? " + pointSet.indexOf(topPoint));
		// System.out.println("where is bottom? " + pointSet.indexOf(bottomPoint));
		// System.out.println("where is right? " + pointSet.indexOf(rightPoint));
		java.util.List<Line2D> temp = new ArrayList<Line2D>();
		if(rightPoint == bottomPoint){
			return temp;
		}
		Point2D nextPoint = rightPoint;
		Point2D bestPoint;
		do{
			// bestPoint = leftPoint;
			// for(Point2D p : pointSet){
			// 	if(p.getX() > bestPoint.getX() && p.getY() < nextPoint.getY()){
			// 		bestPoint = p;
			// 	}
			// }

    		double bestRelativeAngle = -1000, relativeAngle = 0;
			bestPoint = bottomPoint;
			for(Point2D p : pointSet){
				if((p.getY() < nextPoint.getY() && p.getX() < nextPoint.getX())){
    			// looks for the most horizontal angle from start point to determine which point is "most outer"
	    			relativeAngle = Math.atan((nextPoint.getY() - p.getY()) / (nextPoint.getX() - p.getX()));
	    			if(relativeAngle > bestRelativeAngle){
	    				bestRelativeAngle = relativeAngle;
		    			bestPoint = p;
	    			}
    			}
			}
			temp.add(new Line2D.Double(nextPoint, bestPoint));
			nextPoint = bestPoint;
			//System.out.println(nextPoint);
			pointSet.remove(bestPoint);
			System.out.println("Point Count: " + pointSet.size());
		}while(nextPoint != bottomPoint);
		return temp;
	}
	private java.util.List<Line2D> findBottomLeftArc(){
		System.out.println("Bottom Left");
		// System.out.println("where is left? " + pointSet.indexOf(leftPoint));
		// System.out.println("where is top? " + pointSet.indexOf(topPoint));
		// System.out.println("where is bottom? " + pointSet.indexOf(bottomPoint));
		// System.out.println("where is right? " + pointSet.indexOf(rightPoint));
		java.util.List<Line2D> temp = new ArrayList<Line2D>();
		if(bottomPoint == leftPoint){
			return temp;
		}
		Point2D nextPoint = bottomPoint;
		Point2D bestPoint;
		do{
			// bestPoint = topPoint;
			// for(Point2D p : pointSet){
			// 	if(p.getY() < bestPoint.getY() && p.getX() < nextPoint.getX()){
			// 		bestPoint = p;
			// 	}
			// }

    		double bestRelativeAngle = -1000, relativeAngle = 0;
			bestPoint = bottomPoint;
			for(Point2D p : pointSet){
				if((p.getY() > nextPoint.getY() && p.getX() < nextPoint.getX())){
    			// looks for the most horizontal angle from start point to determine which point is "most outer"
	    			relativeAngle = Math.atan((p.getY() - nextPoint.getY()) / (p.getX() - nextPoint.getX()));
	    			if(relativeAngle > bestRelativeAngle){
	    				bestRelativeAngle = relativeAngle;
		    			bestPoint = p;
	    			}
    			}
			}
			temp.add(new Line2D.Double(nextPoint, bestPoint));
			nextPoint = bestPoint;
			//System.out.println(nextPoint);
			pointSet.remove(bestPoint);
			System.out.println("Point Count: " + pointSet.size());
		}while(nextPoint != leftPoint);
		return temp;
	}
	private java.util.List<Line2D> findTopLeftArc(){
		System.out.println("Top Left");
		java.util.List<Line2D> temp = new ArrayList<Line2D>();
		if(leftPoint == topPoint){
			return temp;
		}
		Point2D nextPoint = leftPoint;
		Point2D bestPoint;
		do{
			// bestPoint = rightPoint;
			// for(Point2D p : pointSet){
			// 	if(p.getX() < bestPoint.getX() && p.getY() > nextPoint.getY()){
			// 		bestPoint = p;
			// 	}
			// }

    		double bestRelativeAngle = -1000, relativeAngle = 0;
			bestPoint = bottomPoint;
			for(Point2D p : pointSet){
				if((p.getY() > nextPoint.getY() && p.getX() > nextPoint.getX())){
    			// looks for the most horizontal angle from start point to determine which point is "most outer"
	    			relativeAngle = Math.atan((p.getY() - nextPoint.getY()) / (p.getX() - nextPoint.getX()));
	    			if(relativeAngle > bestRelativeAngle){
	    				bestRelativeAngle = relativeAngle;
		    			bestPoint = p;
	    			}
    			}
			}
			temp.add(new Line2D.Double(nextPoint, bestPoint));
			nextPoint = bestPoint;
			//System.out.println(nextPoint);
			pointSet.remove(bestPoint);
			System.out.println("Point Count: " + pointSet.size());
		}while(nextPoint != topPoint);
		return temp;
	}


    public void paint (Graphics g) {
    	if(readyToPaint){
    		super.paint(g);
	    	g.setColor(Color.RED);
	    	for(Line2D l : lineSet){
	    		g.fillOval((int)(l.getX1() - 5), (int)(l.getY1() - 5), 10, 10);
	    	}
	    	for(Point2D p : pointSet){
	    		g.fillOval((int)(p.getX() - 5), (int)(p.getY() - 5), 10, 10);
	    	}
	    	// g.setColor(Color.BLACK);
	    	// for(Point2D p : pointSet){
	    	// 	g.drawString("(" + (int)p.getX() + ", " + (int)p.getY() + ")", (int)p.getX(), (int)p.getY());
	    	// }
	    	// for(Line2D l : lineSet){
	    	// 	g.drawString("(" + (int)l.getX1() + ", " + (int)l.getY1() + ")", (int)l.getX1(), (int)l.getY1());
	    	// }
	        g.setColor(Color.BLUE);
	        for(Line2D l : lineSet){
	        	g.drawLine((int)l.getX1(), (int)l.getY1(), (int)l.getX2(), (int)l.getY2());
	        }
    	}
   }


}