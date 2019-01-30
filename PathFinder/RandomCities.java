import java.util.Random;
import java.io.*;
//import java.util.Scanner;

public class RandomCities{
	public static void main(String [] args)throws IOException{
		int numCities = Integer.parseInt(args[0]);
		Random rand = new Random();
		PrintWriter outFile = new PrintWriter( new FileWriter("RandomCities.txt"));
		//outFile.printf("Hello");
		outFile.printf("%d\n", numCities);
		for(int i = 0; i < numCities; i++){
			outFile.printf("%f %f\n", (double)rand.nextInt(950) + 25.0, (double)rand.nextInt(550) + 25.0);
		}
		outFile.close();
	}
}