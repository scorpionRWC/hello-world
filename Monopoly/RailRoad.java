class RailRoad extends Ownable{
	int numInSet;
	int []rent = {25, 50, 100, 200};

	public RailRoad(String n, int p){
		name = n;
		price = p;
		color = "Railroad";
		numInSet = 0;
		earnings = 0;
	}	

	public int getPrice(){
		return price;
	}

	public void setNumInSet(int i){
		numInSet = i;
	}

	public int getRent(){
		switch(numInSet){
			case 1: return rent[0];
			case 2: return rent[1];
			case 3: return rent[2];
			case 4: return rent[3];
			default: return -1;
		}
	}

	public void printSpace(){
		super.printSpace();
		for(int i = 0; i < 4; i++){
			System.out.println("Rent with set[" + (i + 1) + "]: " + rent[i]);
		}
		System.out.println();
	}
}