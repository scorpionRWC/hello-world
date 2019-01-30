import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClusterMergeTSP extends JFrame{
	java.util.List<Point2D> pointSet;
	java.util.List<Point2D> remainingPoints;
	ArrayList<Line2D> lineSet;
	java.util.List<ArrayList<Line2D>> clusterSet;
	double knownBestPathLength, currentPathLength;
	boolean readyToPaint;
	private final int FrameX = 1000, FrameY = 600;
    private Scanner scan = new Scanner(System.in);

    public ClusterMergeTSP(String fileName){
    	remainingPoints = new ArrayList<Point2D>();
		pointSet = new ArrayList<Point2D>();
		lineSet = new ArrayList<Line2D>();		
		clusterSet = new ArrayList<ArrayList<Line2D>>();
        setSize(FrameX,FrameY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
		readyToPaint = false;
		try{
			 Scanner inFile = new Scanner(new FileInputStream(fileName));

			 knownBestPathLength = inFile.nextInt();

			 while(inFile.hasNext())
			 {
			 	Point2D temp = new Point2D.Double(inFile.nextDouble(),inFile.nextDouble()); 
			 	pointSet.add(temp);
			 }

			 inFile.close();

			 System.out.println("Successful File Reading");
			 System.out.println("Point Count: " + pointSet.size());
		 }
		 catch(FileNotFoundException fnfe){
		 	System.out.println("File not found!!");
		 } 

		 // System.out.println("Press Enter");
		 // scan.nextLine();


		 findMinimumClusters();


	   		//Use this to debug one step at time
	   		/////////////////////
		    System.out.println("Press Enter");
	   		readyToPaint = true;
			repaint();
	   		scan.nextLine();
	   		//////////////////////

		joinClusters();

		currentPathLength = 0;
		for(Line2D l : lineSet){
			currentPathLength += l.getP1().distance(l.getP2());
		}
		 results();
		if(fileName.equals("RandomCities.txt")){
			readyToPaint = true;
		}
		 repaint();
	}

	public void joinClusters(){
		while(clusterSet.size() > 1){
		//	System.out.println("Before: " + clusterSet);
			double shortestDistance = Integer.MAX_VALUE;
			double clusterPathLength = 0, cluster2PathLength = 0;
			Point2D point1 = null, point2 = null;
			//point1 = point2 = new Point2D.Double(1.0, 2.0);
			ArrayList<Line2D> clusterA = null, clusterB = null;
			// clusterA = clusterB = new ArrayList<Line2D>();
			for(ArrayList<Line2D> cluster : clusterSet){
			//				System.out.println("New cluster1 ------------");
				double localShortestDistance = Integer.MAX_VALUE;
				Point2D localP1 = null, localP2 = null;
				// localP1 = localP2 = new Point2D.Double(1.0, 2.0);
				for(Line2D line : cluster){
				//			System.out.println("New line1 ------------");
					clusterPathLength += line.getP1().distance(line.getP2());
					for(ArrayList<Line2D> cluster2 : clusterSet){
						if(!cluster2.equals(cluster)){
					//		System.out.println("------------");
							for(Line2D line2 : cluster2){
					//System.out.println("In cluster2: " + line2);
								cluster2PathLength += line2.getP1().distance(line2.getP2());
								double dist = line.getP1().distance(line2.getP1());
								if(dist < localShortestDistance){
									localShortestDistance = dist;
									localP1 = line.getP1();
									localP2 = line2.getP1();
								}
								dist = line.getP1().distance(line2.getP2());
								if(dist < localShortestDistance){
									localShortestDistance = dist;
									localP1 = line.getP1();
									localP2 = line2.getP2();
								}
								dist = line.getP2().distance(line2.getP1());
								if(dist < localShortestDistance){
									localShortestDistance = dist;
									localP1 = line.getP2();
									localP2 = line2.getP1();
								}
								dist = line.getP2().distance(line2.getP2());
								if(dist < localShortestDistance){
									localShortestDistance = dist;
									localP1 = line.getP2();
									localP2 = line2.getP2();
								}
							} // end for each line in cluster2
							if(localShortestDistance < shortestDistance){
								clusterB = cluster2;
								//System.out.println("2B: " + clusterB + " ; " + localShortestDistance);
							}
							else{
								cluster2PathLength = 0;
							}
							if(localShortestDistance < shortestDistance){
								clusterA = cluster;
									//	System.out.println("1A: " + clusterB + " ; " + localShortestDistance);
								shortestDistance = localShortestDistance;
								point1 = localP1;
								point2 = localP2;
							}
							else{
								clusterPathLength = 0;
							}
						} // end if cluster2 != cluster
					} // end for cluster2
				} // end for each line in cluster
			} // end for each cluster in clusterSet

			// now that I have the closest points, I need to match the points to their respective lines
			// and figure out which of the four lines to connect and the best way to do so

			double clusterTotalPathLength = Integer.MAX_VALUE;
			Line2D line1toRemove = null, line2toRemove = null, line1toAdd = null, line2toAdd = null;
			boolean found = false;
		    // line2toAdd = line1toAdd = line1toRemove = line2toRemove = new Line2D.Double(100.0, 200.0, 300.0, 400.0);
			
			for(Line2D line : clusterA){
	//		System.out.println("line: " + line);
	//		System.out.println("P1: " + line.getP1() + ", P2: " + line.getP2() + ", point1: " + point1);
				if(line.getP1().equals(point1) || line.getP2().equals(point1)){
		//			System.out.println("In if1");
					double lineLength = line.getP1().distance(line.getP2());
					for(Line2D line2 : clusterB){

		//			System.out.println("line2: " + line2);
		//	System.out.println("P1: " + line2.getP1() + ", P2: " + line2.getP2() + ", point2: " + point2);
						if(line2.getP1().equals(point2) || line2.getP2().equals(point2)){
		//			System.out.println("In if2");
							double line2Length = line2.getP1().distance(line2.getP2());
							Line2D tempLine1 = new Line2D.Double(line.getP1(), line2.getP1());
							Line2D tempLine2 = new Line2D.Double(line.getP2(), line2.getP2());
							boolean pathCrossed = false;
							for(Line2D newTemp1 : clusterA){
								if(!newTemp1.equals(line)){
									for(Line2D newTemp2 : clusterB){
										if(!newTemp2.equals(line2)){
											if(tempLine1.intersectsLine(newTemp2)){
												if(!(tempLine1.getP1().equals(newTemp2.getP1())) && !(tempLine1.getP1().equals(newTemp2.getP2())) && !(tempLine1.getP2().equals(newTemp2.getP1())) && !(tempLine1.getP2().equals(newTemp2.getP2()))){
													pathCrossed = true;
												}
											}
											if(tempLine1.intersectsLine(newTemp1)){
												if(!(tempLine1.getP1().equals(newTemp1.getP1())) && !(tempLine1.getP1().equals(newTemp1.getP2())) && !(tempLine1.getP2().equals(newTemp1.getP1())) && !(tempLine1.getP2().equals(newTemp1.getP2()))){
													pathCrossed = true;
												}
											}
											if(tempLine2.intersectsLine(newTemp2)){
												if(!(tempLine2.getP1().equals(newTemp2.getP1())) && !(tempLine2.getP1().equals(newTemp2.getP2())) && !(tempLine2.getP2().equals(newTemp2.getP1())) && !(tempLine2.getP2().equals(newTemp2.getP2()))){
													pathCrossed = true;
												}
											}
											if(tempLine2.intersectsLine(newTemp1)){
												if(!(tempLine2.getP1().equals(newTemp1.getP1())) && !(tempLine2.getP1().equals(newTemp1.getP2())) && !(tempLine2.getP2().equals(newTemp1.getP1())) && !(tempLine2.getP2().equals(newTemp1.getP2()))){
													pathCrossed = true;
												}
											}
										}
									}
								}
							}
							if(!pathCrossed){
								double resultClusterPathLength = clusterPathLength - lineLength + cluster2PathLength - line2Length + line.getP1().distance(line2.getP1()) + line.getP2().distance(line2.getP2());
								if(resultClusterPathLength < clusterTotalPathLength){
									clusterTotalPathLength = resultClusterPathLength;
									line1toRemove = line;
									line2toRemove = line2;
									line1toAdd = new Line2D.Double(line.getP1(), line2.getP1());
									line2toAdd = new Line2D.Double(line.getP2(), line2.getP2());
									found = true;
								}
								resultClusterPathLength = clusterPathLength - lineLength + cluster2PathLength - line2Length + line.getP1().distance(line2.getP2()) + line.getP2().distance(line2.getP1());
								if(resultClusterPathLength < clusterTotalPathLength){
									clusterTotalPathLength = resultClusterPathLength;
									line1toRemove = line;
									line2toRemove = line2;
									line1toAdd = new Line2D.Double(line.getP1(), line2.getP2());
									line2toAdd = new Line2D.Double(line.getP2(), line2.getP1());
									found = true;
								}
							}
						}
					} // end for each line2 in cluster2
				}
			} // end for each line in cluster

			if(found){
				clusterSet.remove(clusterA);
				clusterSet.remove(clusterB);
				clusterA.remove(line1toRemove);
				clusterB.remove(line2toRemove);
				clusterA.addAll(clusterB);
				clusterA.add(line1toAdd);
				clusterA.add(line2toAdd);
				clusterSet.add(clusterA);
	
			}

		   		//Use this to debug one step at time
		   		///////////////////////
			    System.out.println("Press Enter");
		   		readyToPaint = true;
				repaint();
		   		scan.nextLine();
		   		//////////////////////

		//	System.out.println("After: " + clusterSet);
		} // end while clusterSet.size() > 1
	} // end method

	public void findMinimumClusters(){
		while(pointSet.size() >= 3){
			lineSet = new ArrayList<Line2D>();		
			Point2D p1 = null;
			double shortestDistance = Integer.MAX_VALUE;
			double secondShortestDistance = Integer.MAX_VALUE;
			Point2D closestPoint = null, secondClosestPoint = null;
			for(Point2D point1 : pointSet){
				double localShortestDistance = Integer.MAX_VALUE;
				double localSecondShortestDistance = Integer.MAX_VALUE;
				Point2D localClosestPoint = null, localSecondClosestPoint = null;
				for(Point2D point2 : pointSet){
					double dist = point1.distance(point2);
					if(dist < localShortestDistance && dist > 0){
						localSecondShortestDistance = localShortestDistance;
						localSecondClosestPoint = localClosestPoint;
						localShortestDistance = dist;
						localClosestPoint = point2;
					 }
					 else if(dist < localSecondShortestDistance && dist > 0){
					 	localSecondShortestDistance = dist;
					 	localSecondClosestPoint = point2;
					 }
				}
				if(closestPoint == null || secondClosestPoint == null){
					shortestDistance = localShortestDistance;
					secondShortestDistance = localSecondShortestDistance;
					closestPoint = localClosestPoint;
					secondClosestPoint = localSecondClosestPoint;
					p1 = point1;
				}
				else if((localShortestDistance + localSecondShortestDistance + localClosestPoint.distance(localSecondClosestPoint)) < (shortestDistance + secondShortestDistance + closestPoint.distance(secondClosestPoint))){
					shortestDistance = localShortestDistance;
					secondShortestDistance = localSecondShortestDistance;
					closestPoint = localClosestPoint;
					secondClosestPoint = localSecondClosestPoint;
					p1 = point1;
				}
			} // end outer for

			lineSet.add(new Line2D.Double(p1, closestPoint));
			lineSet.add(new Line2D.Double(closestPoint, secondClosestPoint));
			lineSet.add(new Line2D.Double(secondClosestPoint, p1));

			boolean lineCrosses = false;
			for(Line2D line : lineSet){
				for(ArrayList<Line2D> cluster : clusterSet){
					for(Line2D line2 : cluster){
						if(line.intersectsLine(line2)){
							lineCrosses = true;
						}
					}
				}
			}

			if(lineCrosses){
				remainingPoints.add(p1);
				remainingPoints.add(closestPoint);
				remainingPoints.add(secondClosestPoint);
			}
			else{
				clusterSet.add(lineSet);
			}

			pointSet.remove(p1);
			pointSet.remove(closestPoint);
			pointSet.remove(secondClosestPoint);

	   		// Use this to debug one step at time
	   		/////////////////////////
		 //    System.out.println("Press Enter");
	  //  		readyToPaint = true;
			// repaint();
	  //  		scan.nextLine();
	   		////////////////////////

		} // end while

		for(Point2D p : pointSet){
			remainingPoints.add(p);
		}

		pointSet.removeAll(pointSet);
				
	} // end method

	public void results(){
		int numLines = 0;
    	for(ArrayList<Line2D> lines : clusterSet)
    	for(Line2D l : lines)
    		numLines++;
		System.out.println("Num lines: " + numLines);
		System.out.println("Known Best Length: " + knownBestPathLength);
		System.out.println("My Found Length: " + currentPathLength);
		double percentDifference = (currentPathLength - knownBestPathLength) / ( (currentPathLength + knownBestPathLength) / 2 ) * 100;
		System.out.println("Percent Difference: " + percentDifference);
	}

    public void paint (Graphics g) {
    	if(readyToPaint){
    		super.paint(g);
	    	g.setColor(Color.RED);
	    	for(ArrayList<Line2D> lines : clusterSet){
		    	for(Line2D l : lines){
		    		g.fillOval((int)(l.getX1() - 5), (int)(l.getY1() - 5), 10, 10);
		    		g.fillOval((int)(l.getX2() - 5), (int)(l.getY2() - 5), 10, 10);
		    	}
		    }
	    	for(Point2D p : remainingPoints){
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
	    	for(ArrayList<Line2D> lines : clusterSet){
		        for(Line2D l : lines){
		        	g.drawLine((int)l.getX1(), (int)l.getY1(), (int)l.getX2(), (int)l.getY2());
		        }
		    }
    	}
   }

}