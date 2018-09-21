class PropertyOf2 extends Property{
	public PropertyOf2(String c, String n, int p, int hp, int []r){
		name = n;
		color = c;
		price = p;
		housePrice = hp;
		mortgage = p / 2;
		for(int i = 0; i < 6; i++){
			rent[i] = r[i];
		}
		isCompleteSet = false;
		earnings = 0;
	}	
}