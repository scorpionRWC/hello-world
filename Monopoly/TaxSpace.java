class TaxSpace extends Space{
	int tax;
	public TaxSpace(String n, int t){
		name = n;
		tax = t;
	}	

	public int getTax(){
		return tax;
	}

	public void printSpace(){
		super.printSpace();
		System.out.println("Pay: " + tax);
	}
}