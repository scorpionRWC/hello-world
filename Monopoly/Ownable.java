class Ownable extends Space{
	int price;
	String color;
	String owner = "";
	int earnings;

	public int getPrice(){
		return price;
	}

	public int getEarnings(){
		return earnings;
	}

	public String getColor(){
		return color;
	}

	public void printSpace(){
		super.printSpace();
		System.out.println(color);
		System.out.println("Price: " + price);
	}

	public String getOwner(){
		return owner;
	}

	public void setOwner(String o){
		owner = o;
	}

	public void earned(){
		if(this instanceof Property){
			earnings += ((Property)(this)).getRent();
		}
		else if(this instanceof RailRoad){
			earnings += ((RailRoad)(this)).getRent();
		}
		else if(this instanceof Utility) {
			earnings += ((Utility)(this)).getRent();
		}
	}

	public int getRent(){
		if(this instanceof Property){
			return ((Property)(this)).getRent();
		}
		else if(this instanceof RailRoad){
			return ((RailRoad)(this)).getRent();
		}
		else if(this instanceof Utility) {
			return ((Utility)(this)).getRent();
		}
		else {
			return -1;
		}
	}
}