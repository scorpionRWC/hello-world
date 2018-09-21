import java.util.ArrayList;

class Player{
	int money;
	int boardPosition;
	ArrayList<String> ownedSpaces = new ArrayList<String>();
	boolean bankrupt;
	boolean inJail;
	int numTurnsInJail;
	String name;
	Dice dice;
	MonopolyBoard mb;
	int []landHistory = new int[40];
	int doublesRolledInARow;

	public String getName(){
		return name;
	}

	public Player(String n){
		name = n;
		money = 10000;
		boardPosition = 0;
		bankrupt = false;
		inJail = false;
		numTurnsInJail = 0;
		doublesRolledInARow = 0;
		for(int i = 0; i < 40; i++){
			landHistory[i] = 0;
		}
	} 

	public void earnMoney(int m){
		money += m;
	}

	public void spendMoney(int m){
		money -= m;
	}

	public void addProperty(String os){
		ownedSpaces.add(os);
	}

	public void removeProperty(String os){
		ownedSpaces.remove(os);
	}

	public void moveNumSpaces(int n){
		if((boardPosition + n) >= 40){
			n -= 40 - boardPosition;
			boardPosition = 0;
			money += 200;
		}
		boardPosition += n;
	}

	public boolean canAfford(int p){
		return (money > p);
	}

	public boolean isBankrupt(){
		return bankrupt;
	}

	public void updateSets(String color){
		int count = 0;
		for(String ownedSpace : ownedSpaces){
			if((mb.getInstance().getColor(ownedSpace)).equals(color)){
				count++;
			}
		}
		for(String ownedSpace : ownedSpaces){
			if((mb.getInstance().getColor(ownedSpace)).equals(color)){
				if(mb.getInstance().getSpace(ownedSpace) instanceof PropertyOf2){
					if(count == 2){
						((Property)(mb.getInstance().getSpace(ownedSpace))).setIsCompleteSet(true);
						System.out.println("Full set of 2!");
					}
					else{
						((Property)(mb.getInstance().getSpace(ownedSpace))).setIsCompleteSet(false);
					}
				}
				else if(mb.getInstance().getSpace(ownedSpace) instanceof PropertyOf3){
					if(count == 3){
						((Property)(mb.getInstance().getSpace(ownedSpace))).setIsCompleteSet(true);
						System.out.println("Full set of 3!");
					}
					else{
						((Property)(mb.getInstance().getSpace(ownedSpace))).setIsCompleteSet(false);
					}
				}
				else if(mb.getInstance().getSpace(ownedSpace) instanceof RailRoad){
					((RailRoad)(mb.getInstance().getSpace(ownedSpace))).setNumInSet(count);
					System.out.println("Railroad set of " + count + "!");
				}
				else {// Utility
					if(count == 2){
						((Utility)(mb.getInstance().getSpace(ownedSpace))).setIsCompleteSet(true);
						System.out.println("Full Utility set!");
					}
					else{
						((Utility)(mb.getInstance().getSpace(ownedSpace))).setIsCompleteSet(false);
					}
				}
			}
		}
	}

	public void playerTurn(){
		System.out.println("Starting money: $" + money);
		dice.getInstance().rollDice();
		if(dice.getInstance().getIsDoubles() && doublesRolledInARow == 2){
			boardPosition = 10;
			inJail = true;
			doublesRolledInARow = 0;
			System.out.println(name + " went to Jail for Rolling Doubles 3 times in a row!");
			System.out.println("Remaining money: $" + money);
			System.out.println();
		}
		else{// normal turn
			if(inJail){
				if(!(dice.getInstance().getIsDoubles())){
					numTurnsInJail++;
					System.out.println(name + " spent " + numTurnsInJail + " turn(s) in Jail");
					if(numTurnsInJail == 3){
						inJail = false;
						numTurnsInJail = 0;
						money -= 50;
						System.out.println("... and is now free from Jail!");
					}
				}
				else{
					System.out.println("Escaped Jail by Rolling Doubles!");
					inJail = false;
					numTurnsInJail = 0;
				}
			}
			if(!inJail && !bankrupt){
				moveNumSpaces(dice.getInstance().getRoll());
				updateLandHistory(boardPosition);
				System.out.println(name + " landed on " + mb.getInstance().getSpace(boardPosition).getName());
				if (mb.getInstance().getSpace(boardPosition) instanceof Ownable){
					if (((Ownable)(mb.getInstance().getSpace(boardPosition))).getOwner().equals("")){
						if (canAfford(((Ownable)(mb.getInstance().getSpace(boardPosition))).getPrice())){
							((Ownable)(mb.getInstance().getSpace(boardPosition))).setOwner(name);
							spendMoney(((Ownable)(mb.getInstance().getSpace(boardPosition))).getPrice());
							addProperty(mb.getInstance().getSpace(boardPosition).getName());
							System.out.println("... and bought it!");
							updateSets(((Ownable)(mb.getInstance().getSpace(boardPosition))).getColor());
							mb.getInstance().checkAllPropertyTaken();
						}
					}
					else if((((Ownable)(mb.getInstance().getSpace(boardPosition))).getOwner()).equals(name)){
						// maybe use at some point? like trading?
					}
					else{
						spendMoney(((Ownable)(mb.getInstance().getSpace(boardPosition))).getRent());
						mb.getInstance().getPlayer(((Ownable)(mb.getInstance().getSpace(boardPosition))).getOwner()).earnMoney(((Ownable)(mb.getInstance().getSpace(boardPosition))).getRent());
						System.out.println("... and had to pay rent.");
						((Ownable)(mb.getInstance().getSpace(boardPosition))).earned();
					}
				}
				else if(mb.getInstance().getSpace(boardPosition) instanceof GoToJail){
					System.out.println(name + " was forced to go to Jail!");
					boardPosition = 10;
					inJail = true;
					doublesRolledInARow = 0;
				}
			} // end not in jail, etc. else
			if(money <= 0){
				bankrupt = true;
				System.out.println(name + " went Bankrupt!");
			}
			System.out.println("Remaining money: $" + money);
			System.out.println();
			if(!bankrupt && dice.getInstance().getIsDoubles()){
				System.out.println(name + " Rolled Doubles and gets to go again!");
				doublesRolledInARow++;
				playerTurn();
			}
			else{
				doublesRolledInARow = 0;
			}
		} // end normal turn
	}

	private void updateLandHistory(int p){
		landHistory[p]++;
	}

	public int getLandHistory(int i){// returns the land history of the space index
		return landHistory[i];
	}
}