class Property extends Ownable{
	int housePrice;
	int mortgage;
	int []rent = new int[6];
	int numHouses;
	boolean isCompleteSet;

	public int getHousePrice(){
		return housePrice;
	}

	public int getMortgage(){
		return mortgage;
	}

	public int getRent(){
		if(isCompleteSet && numHouses == 0) 
			return rent[0] * 2;
		return rent[numHouses];
	}

	public int getNumHouses(){
		return numHouses;
	}

	public boolean getIsCompleteSet(){
		return isCompleteSet;
	}

	public void setNumHouses(int num){
		numHouses = num;
	}

	public void setIsCompleteSet(boolean ics){
		isCompleteSet = ics;
	}
/*
	public Property(String c, String n, int p, int hp, int []r){
		name = n;
		color = c;
		price = p;
		housePrice = hp;
		mortgage = p / 2;
		for(int i = 0; i < 6; i++){
			rent[i] = r[i];
		}
		isCompleteSet = false;
	}
*/
	public void printSpace(){
		super.printSpace();
		System.out.println("Base Rent: " + rent[0]);
		for(int i = 1; i < 6; i++){
			System.out.println("Rent with house(s)[" + i + "]: " + rent[i]);
		}
	}
}