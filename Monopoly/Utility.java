class Utility extends Ownable{
	boolean isCompleteSet;
	Dice dice;

	public Utility(String n, int p){
		name = n;
		price = p;
		isCompleteSet = false;
		color = "Utility";
		earnings = 0;
	}	

	public int getPrice(){
		return price;
	}

	public boolean getIsCompleteSet(){
		return isCompleteSet;
	}

	public void setIsCompleteSet(boolean ics){
		isCompleteSet = ics;
	}

	public int getRent(){
		if(isCompleteSet)
			return dice.getInstance().getRoll() * 10;
		else 
			return dice.getInstance().getRoll() * 4;
	}
}