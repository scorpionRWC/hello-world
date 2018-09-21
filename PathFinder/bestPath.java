import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;
import java.util.Scanner;
import java.io.*;

/*
*  c = amount of statements
*  n = number of points
*  t = time to complete
*  
*  c = (1/2)n^3
*  t = c/100,000,000 = (n^3)/200,000,000
*/

public class bestPath extends JFrame {
	private final int numpoints = 50; // 929 for Zimbabwe , 1979 for Oman, 734 for Uruguay
    private Point2D [] p = new Point2D.Double[numpoints];
    private boolean [] used = new boolean[numpoints];
    private boolean ready = false;
    private Path path;
    private int count;
    private boolean borderDone = false;
    private int pointsRemaining = numpoints;
    private Scanner scan = new Scanner(System.in);
    private long timeStart = System.currentTimeMillis();
    private double actualLength = 0;
    private final int FrameX = 1000, FrameY = 600, FrameScale = 1;

    private class Path{
    	private Point2D startPoint;
    	private Point2D endPoint;
    	private Line2D line;
    	private Path next, previous;
    	private boolean rightDown, leftDown, leftUp, rightUp;
    	public void setStartPoint(double x, double y){
    		startPoint = new Point2D.Double(x, y);
    	}
    	public void setEndPoint(double x, double y){
    		endPoint = new Point2D.Double(x, y);
    	}
    	public void swapPoints(){
    		Point2D temp = new Point2D.Double(startPoint.getX(), startPoint.getY());
    		startPoint = new Point2D.Double(endPoint.getX(), endPoint.getY());
    		endPoint = new Point2D.Double(temp.getX(), temp.getY());
    		updateLine();
    	}
    	public Point2D getStartPoint(){
    		return startPoint;
    	}
    	public Point2D getEndPoint(){
    		return endPoint;
    	}
    	public Path(double x1, double y1, double x2, double y2){
    		setStartPoint(x1, y1);
    		setEndPoint(x2, y2);
    		line = new Line2D.Double(startPoint, endPoint);
    		checkDirection();
    		next = null;
    	}
    	public int findClosestPoint(){
    		int best = -1;
    		double dist = FrameX;
    		for(int i = 0; i < numpoints; i++){
    			if(used[i] == false){
	    			if(rightDown){
	    				if((p[i].getX() >= startPoint.getX() && p[i].getX() <= endPoint.getX() && p[i].getY() >= startPoint.getY()) || (p[i].getY() >= startPoint.getY() && p[i].getY() <= endPoint.getY() && p[i].getX() <= endPoint.getX())){
	    					if(line.ptSegDist(p[i].getX(), p[i].getY()) < dist){
	    						dist = line.ptSegDist(p[i].getX(), p[i].getY());
	    						best = i;
	    					}
	    				}
	    			}
	    			if(leftDown){
	    				if((p[i].getX() <= startPoint.getX() && p[i].getX() >= endPoint.getX() && p[i].getY() <= endPoint.getY()) || (p[i].getY() >= startPoint.getY() && p[i].getY() <= endPoint.getY() && p[i].getX() <= startPoint.getX())){
	    					if(line.ptSegDist(p[i].getX(), p[i].getY()) < dist){
	    						dist = line.ptSegDist(p[i].getX(), p[i].getY());
	    						best = i;
	    					}
	    				}
	    			}
	    			if(leftUp){
	    				if((p[i].getX() <= startPoint.getX() && p[i].getX() >= endPoint.getX() && p[i].getY() <= startPoint.getY()) || (p[i].getY() <= startPoint.getY() && p[i].getY() >= endPoint.getY() && p[i].getX() >= endPoint.getX())){
	    					if(line.ptSegDist(p[i].getX(), p[i].getY()) < dist){
	    						dist = line.ptSegDist(p[i].getX(), p[i].getY());
	    						best = i;
	    					}
	    				}
	    			}
	    			if(rightUp){
	    				if((p[i].getX() >= startPoint.getX() && p[i].getX() <= endPoint.getX() && p[i].getY() >= endPoint.getY()) || (p[i].getY() <= startPoint.getY() && p[i].getY() >= endPoint.getY() && p[i].getX() >= startPoint.getX())){
	    					if(line.ptSegDist(p[i].getX(), p[i].getY()) < dist){
	    						dist = line.ptSegDist(p[i].getX(), p[i].getY());
	    						best = i;
	    					}
	    				}
	    			}
	    		}
    		}
    		return best;
    	}
    	private void checkDirection(){	
    		// line is going right and down
	    	if(startPoint.getX() <= endPoint.getX() && startPoint.getY() <= endPoint.getY()){
	    		rightDown = true;
	    		leftDown = false;
	    		leftUp = false;
	    		rightUp = false;
	    	}
	    	// line is going left and down
	    	if(startPoint.getX() >= endPoint.getX() && startPoint.getY() <= endPoint.getY()){
	    		rightDown = false;
	    		leftDown = true;
	    		leftUp = false;
	    		rightUp = false;
	    	}
	    	// line is going left and up
	    	if(startPoint.getX() >= endPoint.getX() && startPoint.getY() >= endPoint.getY()){
	    		rightDown = false;
	    		leftDown = false;
	    		leftUp = true;
	    		rightUp = false;
	    	}
	    	// line is going right and up
	    	if(startPoint.getX() <= endPoint.getX() && startPoint.getY() >= endPoint.getY()){
	    		rightDown = false;
	    		leftDown = false;
	    		leftUp = false;
	    		rightUp = true;
	    	}
	    }

	    public boolean getRightDown(){
	    	return rightDown;
	    }
	    public boolean getLeftDown(){
	    	return leftDown;
	    }
	    public boolean getLeftUp(){
	    	return leftUp;
	    }
	    public boolean getRightUp(){
	    	return rightUp;
	    }
	    public Point2D getMidPoint(){
	    	return new Point2D.Double(((startPoint.getX() + endPoint.getX())/2), ((startPoint.getY() + endPoint.getY())/2));
	    }
    	public void addLine(){
    		next = new Path(endPoint.getX(), endPoint.getY(), endPoint.getX(), endPoint.getY());
    		next.setPrevious(this);
    	}
    	public void setPrevious(Path path1){
    		previous = path1;
    	}
    	public Path getPrevious(){
    		return previous;
    	}
    	public void setLine(double x1, double y1, double x2, double y2){
    		setStartPoint(x1, y1);
    		setEndPoint(x2, y2);
    		//line = new Line2D.Double(startPoint, endPoint);
    		line.setLine(startPoint, endPoint);
    	}
    	public void updateLine(){
    		setLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    		checkDirection();
    	}
    	public Line2D getLine(){
    		return line;
    	}
    	public Line2D getSegment(){
    		if(rightDown == true){
    			return new Line2D.Double(startPoint.getX() + 1, startPoint.getY() + 1, endPoint.getX() - 1, endPoint.getY() - 1);
    		}
    		if(leftDown == true){
    			return new Line2D.Double(startPoint.getX() - 1, startPoint.getY() + 1, endPoint.getX() + 1, endPoint.getY() - 1);
    		}
    		if(leftUp == true){
    			return new Line2D.Double(startPoint.getX() - 1, startPoint.getY() - 1, endPoint.getX() + 1, endPoint.getY() + 1);
    		}
    		if(rightUp == true){
    			return new Line2D.Double(startPoint.getX() + 1, startPoint.getY() - 1, endPoint.getX() - 1, endPoint.getY() + 1);
    		}
    		return null;
    	}
    	public Path getNext(){
    		if(next == null){
    			path.setPrevious(this);
    			return path;
    		}
    		return next;
    	}
    	public void setNext(Path nextPath){
    		next = nextPath;
    	}
    	/*public void paintLine(Graphics g){
	        g.setColor(Color.BLUE);
	    	g.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    	}*/
    }

    public bestPath() {
        //setSize(1000,600);
        setSize(FrameX,FrameY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        count = 0;
        makePoints();
        //makePointsFromFile();
        initializeBorder();
        borderDone = true;
        findSmallestStreches();
      //  System.out.println("Length Before Cross Checking: " + getPathLength()); 
   		
   		//ready = true;
   		//repaint();
   		//scan.nextLine();
        
        //checkForCrosses();
        ready = true;
        System.out.println("Count: " + count);
        System.out.println("Time: " + ((System.currentTimeMillis() - timeStart))/1000.0 + " seconds");
        printSolution();
        repaint();
    }

    public void checkForCrosses(){
    	System.out.println("Checking for more Crosses...");
    	Path temp1 = path;
    	do{
    		Path temp2 = temp1.getNext();
    		do{
    			if(temp1.getLine().intersectsLine(temp2.getLine()) && temp2 != temp1.getNext() && temp1 != temp2.getNext()){
    				System.out.println("Cross found! Press Enter...");
   		ready = true;
   		repaint();
   		scan.nextLine();
    				temp1.setEndPoint(temp2.getLine().getX1(), temp2.getLine().getY1());
    				temp1.updateLine();
    				temp2.setEndPoint(temp1.getLine().getX2(), temp1.getLine().getY2());	
    				temp2.updateLine();
    				System.out.println("Cross Checked!!!");
    				break;
    			}
    			temp2 = temp2.getNext();
    		}while(temp2 != temp1);
    		temp1 = temp1.getNext();
    	}while(temp1 != path);
    }

    // returns the index of the next unused upper right point
    // will not accept points over the bounds
    private int findNextUpperRight(double boundX1, double boundY1){
    	double startX = -1.0, startY = -1.0;
    	double bestRelativeAngle = 1000, relativeAngle = 0;
    	startX = boundX1;
    	startY = boundY1;
    	int best = -1;
    	for(int i = 0; i < numpoints; i++){
    		count++;
    		// if the point is within bounds and not used yet
    		if((p[i].getY() >= boundY1 && p[i].getX() >= boundX1) && used[i] == false){
    			// looks for the most horizontal angle from start point to determine which point is "most outer"
    			relativeAngle = Math.atan((p[i].getY() - startY) / (p[i].getX() - startX));
    			if(relativeAngle < bestRelativeAngle){
    				bestRelativeAngle = relativeAngle;
	    			best = i;
    			}
    		}
    	}
    	if(best == -1){
    		return -1;
    	}
    	if(borderDone == false){
    		used[best] = true;
    		pointsRemaining--;
    	}
    	return best;
    }

    // returns the index of the next unused lower right point
    // will not accept points over the bounds
    private int findNextLowerRight(double boundX1, double boundY1){
    	double startX = -1.0, startY = -1.0;
    	double bestRelativeAngle = 1000, relativeAngle = 0;
    	startX = boundX1;
    	startY = boundY1;
    	int best = -1;
    	for(int i = 0; i < numpoints; i++){
    		count++;
    		// if the point is within bounds and not used yet
    		if((p[i].getY() >= boundY1 && p[i].getX() <= boundX1) && used[i] == false){
    			// looks for the most vertical angle from start point to determine which point is closer is "most outer"
    			// I notice I can still use atan, since the negative value of a triangle's side will "auto-flip" the triangle
    			relativeAngle = Math.atan((p[i].getY() - startY) / (p[i].getX() - startX));
    			if(relativeAngle < bestRelativeAngle){
    				bestRelativeAngle = relativeAngle;
	    			best = i;
    			}
    		}
    	}
    	if(best == -1){
    		return -1;
    	}
    	if(borderDone == false){
    		used[best] = true;
    		pointsRemaining--;
    	}
    	return best;
    }

    // returns the index of the next unused lower left point
    // will not accept points over the bounds
    private int findNextLowerLeft(double boundX1, double boundY1){
    	double startX = -1.0, startY = -1.0;
    	double bestRelativeAngle = 1000, relativeAngle = 0;
    	startX = boundX1;
    	startY = boundY1;
    	int best = -1;
    	for(int i = 0; i < numpoints; i++){
    		count++;
    		// if the point is within bounds and not used yet, will also accept upper point
    		if((p[i].getY() <= boundY1 && p[i].getX() <= boundX1) && ((used[i] == false) || (borderDone == false && p[i].getX() == path.getLine().getX1() && p[i].getY() == path.getLine().getY1()))){
    			// looks for the most horizontal angle from start point to determine which point is closer is "most outer"
    			// I notice I can still use atan, since the negative value of a triangle's side will "auto-flip" the triangle
    			relativeAngle = Math.atan((p[i].getY() - startY) / (p[i].getX() - startX));
    			if(relativeAngle < bestRelativeAngle){
    				bestRelativeAngle = relativeAngle;
	    			best = i;
    			}
    		}
    	}
    	if(best == -1){
    		return -1;
    	}
    	if(borderDone == false){
    		used[best] = true;
    		pointsRemaining--;
    	}
    	return best;
    }

    // returns the index of the next unused upper left point
    // will not accept points over the bounds
    private int findNextUpperLeft(double boundX1, double boundY1){
    	double startX = -1.0, startY = -1.0;
    	double bestRelativeAngle = 1000, relativeAngle = 0;
    	startX = boundX1;
    	startY = boundY1;
    	int best = -1;
    	for(int i = 0; i < numpoints; i++){
    		count++;
    		// if the point is within bounds and not used yet, will also accept upper point
    		if((p[i].getY() <= boundY1 && p[i].getX() >= boundX1)  && ((used[i] == false) || (borderDone == false && p[i].getX() == path.getLine().getX1() && p[i].getY() == path.getLine().getY1()))){
    			// looks for the most vertical angle from start point to determine which point is closer is "most outer"
    			// I notice I can still use atan, since the negative value of a triangle's side will "auto-flip" the triangle
    			relativeAngle = Math.atan((p[i].getY() - startY) / (p[i].getX() - startX));
    			if(relativeAngle < bestRelativeAngle){
    				bestRelativeAngle = relativeAngle;
	    			best = i;
    			}
    		}
    	}
    	if(best == -1){
    		return -1;
    	}
    	if(borderDone == false){
    		used[best] = true;
    		pointsRemaining--;
    	}
    	return best;
    }

    private void printSolution(){
    	Path temp = path;
    	double length = 0.0;
    	do{
    		//System.out.println(temp.getLine().getX1() + ", " + temp.getLine().getY1() + "; " + temp.getLine().getX2() + ", " + temp.getLine().getY2());
    		length += temp.getStartPoint().distance(temp.getEndPoint());
    		temp = temp.getNext();
    	}while(temp != path);
    	if(actualLength != 0)
    	System.out.println("Best Known Length: " + actualLength);
    	System.out.println("Total Length: " + length);
    	if(actualLength != 0)
    	System.out.println("Percent Difference: " + length / actualLength);
    }

    private double getPathLength(){
    	double length = 0.0;
    	Path temp = path;
    	do{
    		length += temp.getStartPoint().distance(temp.getEndPoint());
    		temp = temp.getNext();
    	}while(temp != path);
    	return length; 
    }

    // returns the index of the upper most point, this will be the beginning of the path
    private int findUpper(){
    	int upperIndex = -1;
    	double maxY = FrameY; 
    	for(int i = 0; i < numpoints; i++){
    		//count++;
    		if(p[i].getY() < maxY){
    			upperIndex = i;
    			maxY = p[i].getY();
    		}
    	}
    	if(upperIndex == -1){
    		return -1;
    	}
    	used[upperIndex] = true;
    	pointsRemaining--;
    	return upperIndex;
    }

    // the path starts by finding the outermost points, 
    // like putting a rubber band around a collection of pegs
    private void initializeBorder(){
    	path = new Path(0,0,FrameX,0);
    	Path temp = path;
    	int i = -1, upperIndex = -1;
    	upperIndex = findUpper();
    	temp.setLine(p[upperIndex].getX(), p[upperIndex].getY(), FrameX, p[upperIndex].getY());
    	do{
	    	i = findNextUpperRight(temp.getLine().getX1(), temp.getLine().getY1());
	    	if(i >= 0){
	    		temp.setEndPoint(p[i].getX(), p[i].getY());
	    		temp.updateLine();
	    		temp.addLine();
	    		temp = temp.getNext();
	    		temp.setLine(p[i].getX(), p[i].getY(), FrameX, p[i].getY());
	    	}
	    }while(i >= 0);
    	do{
	    	i = findNextLowerRight(temp.getLine().getX1(), temp.getLine().getY1());
	    	if(i >= 0){
	    		temp.setEndPoint(p[i].getX(), p[i].getY());
	    		temp.updateLine();
	    		temp.addLine();
	    		temp = temp.getNext();
	    		temp.setLine(p[i].getX(), p[i].getY(), p[i].getX(), FrameY);
	    	}
	    }while(i >= 0);
    	do{
	    	i = findNextLowerLeft(temp.getLine().getX1(), temp.getLine().getY1());
	    	if(i >= 0 && i != upperIndex){
	    		temp.setEndPoint(p[i].getX(), p[i].getY());
	    		temp.updateLine();
	    	//	if(i != upperIndex){
		    		temp.addLine();
		    		temp = temp.getNext();
		    		temp.setLine(p[i].getX(), p[i].getY(), p[i].getX(), FrameY);
		   // 	}
	    	}
	    }while(i >= 0 && i != upperIndex);
    	do{
	    	i = findNextUpperLeft(temp.getLine().getX1(), temp.getLine().getY1());
	    	if(i >= 0 && i != upperIndex){
	    		temp.setEndPoint(p[i].getX(), p[i].getY());
	    		temp.updateLine();
	    	//	if(i != upperIndex){
		    		temp.addLine();
		    		temp = temp.getNext();
		    		temp.setLine(p[i].getX(), p[i].getY(), p[i].getX(), FrameY);
		  //  	}
	    	}
	    }while(i >= 0 && i != upperIndex);

	    temp.setEndPoint(path.getLine().getX1(), path.getLine().getY1());
	    temp.updateLine();
    }

    // now that the rubber band is around the pegs,
    // the band must strech as little as possible to add one peg at a time
    private void findSmallestStreches(){
    	Path temp, temp2;
    	Path closestLine;
	    int closestIndex;// , i = -1;
	    double endX, endY;
	   	double shortestPointDistance, shortestPathLength;
	   	Point2D p1, p2, p3, newP;
	   	double oldPathLength, newPathLength;
	   	while(pointsRemaining >= 0){ 
	   		//scan.nextLine();
	   		//ready = true;
	   		//repaint();
	   		//ready = false; // not needed
	    	temp = path;
	    	closestLine = path;
		    closestIndex = -1;
		    endX = -1; 
		    endY = -1;
		   	shortestPointDistance = FrameX;
		    oldPathLength = getPathLength();
		    shortestPathLength = oldPathLength * 2;
		    for(int i = 0; i < numpoints; i++){
		   		temp = path;
	    		do{
		    		count++;
		    		if(used[i] == false){
			    		/*if(temp.getLine().ptSegDist(p[i].getX(), p[i].getY()) < shortestPointDistance){
			    			// if the point is equadistant from this line and the next, happens at sharp angles
			    			if(temp.getNext() != null && temp.getLine().ptSegDist(p[i].getX(), p[i].getY()) == temp.getNext().getLine().ptSegDist(p[i].getX(), p[i].getY())){
			    				/*if((temp.getRightDown() && temp.getNext().getRightUp() && p[i].getX() <= temp.getLine().getX2()) || (temp.getLeftDown() && temp.getNext().getRightDown() && p[i].getY() <= temp.getLine().getY2()) || (temp.getLeftUp() && temp.getNext().getLeftDown() && p[i].getX() >= temp.getLine().getX2()) || (temp.getRightUp() && temp.getNext().getLeftUp() && p[i].getY() >= temp.getLine().getY2())){
			    					shortestPointDistance = temp.getLine().ptSegDist(p[i].getX(), p[i].getY());
			    					closestLine = temp;
			    					closestIndex = i;
			    				}*/
			    			    /*else if(temp.getLine().intersectsLine(p[i].getX(), p[i].getY(), temp.getNext().getLine().getX2(), temp.getNext().getLine().getY2())){	
			    					shortestPointDistance = temp.getLine().ptSegDist(p[i].getX(), p[i].getY());
			    					closestLine = temp;
			    					closestIndex = i;
			    				}*/
			    				//else{
			    					/*shortestPointDistance = temp.getNext().getLine().ptSegDist(p[i].getX(), p[i].getY());
			    					closestLine = temp.getNext();
			    					closestIndex = i;*/
                    
			    	// works pretty dang good
                    /////////////////////////////////////////////////////////////////////////////////////////////////
			    	/*	if(temp.getLine().ptSegDist(p[i].getX(), p[i].getY()) < shortestPointDistance){
			    			shortestPointDistance = temp.getLine().ptSegDist(p[i].getX(), p[i].getY());
	    					closestLine = temp;
	    					closestIndex = i;
			    		}
    				*/////////////////////////////////////////////////////////////////////////////////////////////////


			    		/*temp2 = temp.getPrevious().getPrevious();
			    		//do{
			    			if(temp.getLine().intersectsLine(temp2.getLine())){// && temp2 != temp.getNext() && temp != temp2.getNext()){
								
								System.out.println("Intersection!!! Press Enter to Continue...");
						   		ready = true;
						   		repaint();
						   		scan.nextLine();

			    				fixIntersection(temp, temp2);
			    				//break;
			    			}
			    		//	temp2 = temp2.getNext();
			    		*///}while(temp2 != temp);

			    					////////////////////////////////////
			    					//
			    					// Next Idea: Compare original total path length with new total path length
			    					//            after adding a point. The new path with the shortest total length
			    					//            is used. 
			    					// New path length = original path length - current segment length + new segments lengths with added point
			    					//
			    					////////////////////////////////////

			    	// seems to work well
			    	///////////////////////////////////////////////////////////////////////////////////////////////
			    		p1 = new Point2D.Double(temp.getLine().getX1(), temp.getLine().getY1());
		    			p2 = new Point2D.Double(temp.getLine().getX2(), temp.getLine().getY2());
		    			newP = new Point2D.Double(p[i].getX(), p[i].getY());
			    		newPathLength = oldPathLength - p1.distance(p2) + p1.distance(newP) + newP.distance(p2);
			    		if(newPathLength < shortestPathLength){
			    			shortestPathLength = newPathLength;
			    			closestLine = temp;
			    			closestIndex = i;
			    		}
			    	///////////////////////////////////////////////////////////////////////////////////////////////

		    		/*	p1 = new Point2D.Double(temp.getLine().getX1(), temp.getLine().getY1());
		    			p2 = new Point2D.Double(temp.getLine().getX2(), temp.getLine().getY2());
		    			//if(temp.getNext() != null){
		    				p3 = new Point2D.Double(temp.getNext().getLine().getX2(), temp.getLine().getY2());
		    		//	}
		    		/*	else{
		    				p3 = new Point2D.Double(path.getLine().getX1(), path.getLine().getY1());
		    			}*/
		    		/*	newP = new Point2D.Double(p[i].getX(), p[i].getY());
		    			if((p1.distance(p2) + p2.distance(newP) + newP.distance(p3)) < (p1.distance(newP) + newP.distance(p2) + p2.distance(p3))){
		    			//	if(temp.getNext() != null){
		    				if(p1.distance(p2) + p2.distance(newP) + newP.distance(p3) < shortestPointDistance){
		    					shortestPointDistance = p1.distance(p2) + p2.distance(newP) + newP.distance(p3);//temp.getNext().getLine().ptSegDist(p[i].getX(), p[i].getY());
		    				
		    			//	}
		    			//	else {
		    			//		shortestPointDistance = path.getLine().ptSegDist(p[i].getX(), p[i].getY());
		    			//	}
	    					closestLine = temp.getNext();
	    					closestIndex = i;
	    				}
	    				//	System.out.println("Decision: " + p[i].getX() + ", " + p[i].getY());
		    			}
	    				else{
	    					if(p1.distance(newP) + newP.distance(p2) + p2.distance(p3) < shortestPointDistance){
	    					shortestPointDistance = p1.distance(newP) + newP.distance(p2) + p2.distance(p3);//temp.getLine().ptSegDist(p[i].getX(), p[i].getY());
	    					closestLine = temp;
	    					closestIndex = i;
	    				}
	    				}
			    				//}
			    			/*}
			    			else{
			    				shortestPointDistance = temp.getLine().ptSegDist(p[i].getX(), p[i].getY());
		    					closestLine = temp;
		    					closestIndex = i;
			    			}
			    		}*/
			    		/*p1 = new Point2D.Double(temp.getLine().getX1(), temp.getLine().getY1());
					    p2 = new Point2D.Double(temp.getLine().getX2(), temp.getLine().getY2());
					    newP = new Point2D.Double(p[i].getX(), p[i].getY());
					    if(p1.distance(newP) + newP.distance(p2) < shortestPointDistance){
			    			shortestPointDistance = p1.distance(newP) + newP.distance(p2);
		    				closestLine = temp;
		    				closestIndex = i;
					    }*/
		    		}
		    		temp = temp.getNext();
	    		}while(temp != path);
			}
			/*do{
				//count++;
				/*if(temp.getRightDown()){
	    			i = findNextUpperRight(temp.getLine().getX1(), temp.getLine().getY1());
	    			if(i < 0){
	    				i = findNextLowerRight(temp.getLine().getX1(), temp.getLine().getY1());
	    			}
				}
				if(temp.getLeftDown()){
	    			i = findNextLowerRight(temp.getLine().getX1(), temp.getLine().getY1());
	    			if(i < 0){
	    				i = findNextLowerLeft(temp.getLine().getX1(), temp.getLine().getY1());
	    			}
				}
				if(temp.getLeftUp()){
	    			i = findNextLowerLeft(temp.getLine().getX1(), temp.getLine().getY1());
	    			if(i < 0){
	    				i = findNextUpperLeft(temp.getLine().getX1(), temp.getLine().getY1());
	    			}
				}
				if(temp.getRightUp()){
	    			i = findNextUpperLeft(temp.getLine().getX1(), temp.getLine().getY1());
	    			if(i < 0){
	    				i = findNextUpperRight(temp.getLine().getX1(), temp.getLine().getY1());
	    			}
				}//////////
				i = temp.findClosestPoint();
				if(i >= 0){
					if(temp.getLine().ptSegDist(p[i].getX(), p[i].getY()) < shortestPointDistance){
						shortestPointDistance = temp.getLine().ptSegDist(p[i].getX(), p[i].getY());
    					closestLine = temp;
    					closestIndex = i;
					}
				}
				temp = temp.getNext();
			}while(temp != null);*/
	    	if(closestIndex >= 0){

					/*System.out.println("About to add point (" + (int)p[closestIndex].getX() + ", " + (int)p[closestIndex].getY() + ")... Press Enter to Continue...");
			   		ready = true;
			   		repaint();
			   		scan.nextLine();*/

	    		used[closestIndex] = true;
	    		pointsRemaining--;
	    		//System.out.println(pointsRemaining);
	    		endX = closestLine.getLine().getX2();
	    		endY = closestLine.getLine().getY2();
	    		temp = closestLine.getNext();
    		//System.out.println("Original: " + closestLine.getLine().getX1() + ", " + closestLine.getLine().getY1() + "; " + closestLine.getLine().getX2() + ", " + closestLine.getLine().getY2());
	    		closestLine.setEndPoint(p[closestIndex].getX(), p[closestIndex].getY());
	    		closestLine.updateLine();
    		//System.out.println("Updated: " + closestLine.getLine().getX1() + ", " + closestLine.getLine().getY1() + "; " + closestLine.getLine().getX2() + ", " + closestLine.getLine().getY2());
	    		closestLine.addLine();
	    		closestLine.getNext().setEndPoint(endX, endY);
	    		closestLine.getNext().updateLine();
    		//System.out.println("Next: " + closestLine.getNext().getLine().getX1() + ", " + closestLine.getNext().getLine().getY1() + "; " + closestLine.getNext().getLine().getX2() + ", " + closestLine.getNext().getLine().getY2());
	    		closestLine.getNext().setNext(temp);
	    		closestLine.getNext().setPrevious(closestLine);
	    		temp.setPrevious(closestLine.getNext());
	    		

				/*	System.out.println("Before fix... Press Enter to Continue...");
			   		ready = true;
			   		repaint();
			   		scan.nextLine();*/

			   	//	printLinePoints(closestLine.getPrevious().getLine());
			   	//	printLinePoints(closestLine.getLine());
			   	//	printLinePoints(closestLine.getNext().getLine());
	    		//temp2 = closestLine.getPrevious();
	    		//temp = closestLine.getNext();
	    		//do{
    			if(closestLine.getLine().intersectsLine(closestLine.getNext().getNext().getLine())){// && temp2 != temp.getNext() && temp != temp2.getNext()){
					
					//System.out.println("Intersection!!! Press Enter to Continue...");
			   		/*ready = true;
			   		repaint();
			   		scan.nextLine();*/

    				//fixIntersection(closestLine, closestLine.getNext().getNext());

				/*	System.out.println("Intersection fixed? Press Enter to Continue...");
			   		ready = true;
			   		repaint();
			   		scan.nextLine();*/
    				//break;
    			}
	    	}
    	}
    }

    private void printLinePoints(Line2D line){
    	System.out.println("(" + (int)line.getX1() + ", " + (int)line.getY1() + "), (" + (int)line.getX2() + ", " + (int)line.getY2() + ")");
    }

    private void fixIntersection(Path path1, Path path2){
    	Path temp = path1.getNext();//, temp2;
    	path2.setStartPoint(path1.getLine().getX2(), path1.getLine().getY2());
    	path1.setEndPoint(path2.getPrevious().getLine().getX2(), path2.getPrevious().getLine().getY2());
    	//path1.setNext(path2.getPrevious());
    	path1.updateLine();
    	//path2.setPrevious(temp);
    	path2.updateLine();

    	temp.swapPoints();
    /*	do{
    		temp.swapPoints();
    		temp2 = temp.getNext();
    		if(temp.getPrevious() == path1){
    			temp.setNext(path2);
    		}
    		else{
    			temp.setNext(temp.getPrevious());
    		}
    		if(temp.getNext() == path2){
    			temp.setPrevious(path1);
    		}
    		else{
    			temp.setPrevious(temp2);
    		}
    		temp = temp.getPrevious();
    	}while(temp.getPrevious() != path1);*/
    }

    private void makePoints(){
    	Random rand = new Random();
    	for(int i = 0; i < numpoints; i++){
    		//count++;
    		p[i] = new Point2D.Double((rand.nextInt(FrameX - 100) + 50), (rand.nextInt(FrameY - 100) + 50));
    		used[i] = false;
    	}
    }

    private void makePointsFromFile(){
    	try{
			 //Scanner inFile = new Scanner(new FileInputStream("ZimbabweCities"));
			 //Scanner inFile = new Scanner(new FileInputStream("OmanCities"));
			 Scanner inFile = new Scanner(new FileInputStream("UruguayCities"));

			 //double pos;
			 int ind;

			 actualLength = inFile.nextInt() / FrameScale;

			 while(inFile.hasNext())
			 {
			 	ind = inFile.nextInt() - 1;
			 	used[ind] = false;
			 	p[ind] = new Point2D.Double(inFile.nextDouble() / FrameScale, inFile.nextDouble() / FrameScale);
			 }

			 inFile.close();

			 System.out.println("Successful File Reading");
		 }
		 catch(FileNotFoundException fnfe){
		 	System.out.println("File not found!!");
		 } 
    }

    public void paint (Graphics g) {
    	if(ready){
    		super.paint(g);
	    	g.setColor(Color.RED);
	    	for(int i = 0; i < numpoints; i++){
	    		g.fillOval((int)(p[i].getX() - 5), (int)(p[i].getY() - 5),10,10);	
	    	}
	    	g.setColor(Color.BLACK);
	    	for(int i = 0; i < numpoints; i++){
	    		//g.drawString("(" + (int)p[i].getX() + ", " + (int)p[i].getY() + ")", (int)p[i].getX(), (int)p[i].getY());
	    	}
	        g.setColor(Color.BLUE);
	    	Path temp = path;
	    	do{
	    		g.drawLine((int)temp.getLine().getX1(), (int)temp.getLine().getY1(), (int)temp.getLine().getX2(), (int)temp.getLine().getY2());
	    		temp = temp.getNext();
	    	}while(temp != path);
    	}
   }


   public static void main (String[] args) {
		bestPath b = new bestPath();
   }
}
