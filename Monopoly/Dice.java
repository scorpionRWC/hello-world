import java.util.Random;

class Dice{
	int dice1;
	int dice2;
	private static Dice instance = null;
	Random rand = new Random();

	private Dice(){}

	public static Dice getInstance(){
		if(instance == null){
			instance = new Dice();
		}
		return instance;
	}

	public void rollDice(){
		dice1 = rand.nextInt(6) + 1;
		dice2 = rand.nextInt(6) + 1;
	}

	public int getRoll(){
		return dice1 + dice2;
	}

	public boolean getIsDoubles(){
		return (dice1 == dice2);
	}
}