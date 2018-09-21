import java.util.ArrayList;

class MonopolyBoard{
	Space []board = new Space[40];
	int []r = new int[6]; //rent values
	private static MonopolyBoard instance = null;
	ArrayList<Player> playerList = new ArrayList<Player>();
	int playersLeft;
	boolean allPropertyTaken, tradesInitialized;

	public void printEarnings(){
		for(int i = 0; i < 40; i++){
			if(board[i] instanceof Ownable){
				System.out.println(board[i].getName() + ": $" + ((Ownable)board[i]).getEarnings());
			}
		}
	}

	public static MonopolyBoard getInstance(){
		if(instance == null){
			instance = new MonopolyBoard();
		}
		return instance;
	}

	public String getColor(String n){
		for(Space sp : board){
			if(sp instanceof Ownable){
				if(sp.getName().equals(n)){
					return ((Ownable)(sp)).getColor();
				}
			}
		}
		return "";
	}

	public Space getSpace(String n){
		for(Space sp : board){
			if(sp instanceof Ownable){
				if(sp.getName().equals(n)){
					return sp;
				}
			}
		}
		return null;	
	}

	public void addPlayer(Player p){
		playerList.add(p);
	}

	public Player getPlayer(String n){
		for(Player player : playerList)
		{
			if(player.getName().equals(n)){
				return player;
			}
		}
		return null;
	}

	public Player checkWinner(){
		playersLeft = 4;
		for(Player p : playerList){
			if(p.isBankrupt()){
				playersLeft--;
			}
		}
		if(playersLeft == 1){
			for(Player p : playerList){
				if(p.isBankrupt() == false){
					System.out.println(p.getName() + " is the winner!");
					return p;
				}
			}
		}
		return null;
	}

	public void checkAllPropertyTaken(){ // Utility and Railroad Properties will be ignored
		if(tradesInitialized == false){
			for(Space sp : board){
				if(sp instanceof Property){
					if(((Ownable)sp).getOwner().equals("")){
						return;
					}
				}
			}
			System.out.println("******** Attention!!! **********");
			System.out.println("All Colored Properties have been bought!");
			System.out.println("Trading has commenced and Houses have been set!");
			System.out.println("******* End of Message *********");
			tradesInitialized = true;
			initiateTrades();
		}
	}

	public void printPlayerProfits(){
		for(Player pl : playerList){
			int prop1Acc = 0, prop2Acc = 0, railAcc = 0, utilAcc = 0; // accumilators of earnings
			boolean ownsRail = false, ownsUtil = false; // if player owns rail or util
			System.out.println();
			System.out.println(pl.getName() + " Profits Outline:");
			switch(pl.getName()){
				case "Bob":
					for(Space sp : board){
						if(sp instanceof Ownable){
							if(((Ownable)sp).getOwner().equals("Bob")){
								if (sp instanceof RailRoad){
									ownsRail = true;
									railAcc += ((Ownable)sp).getEarnings();
								}
								else if (sp instanceof Utility){
									ownsUtil = true;
									utilAcc += ((Ownable)sp).getEarnings();
								}
								else{
									if((((Ownable)sp).getColor()).equals("Brown")){
										prop1Acc += ((Ownable)sp).getEarnings();
									}
									else if((((Ownable)sp).getColor()).equals("Yellow")){
										prop2Acc += ((Ownable)sp).getEarnings();
									}
								}
							}
						}
					}
					if(ownsRail == true){
						System.out.println("RailRoad: " + railAcc);
					}
					if(ownsUtil == true){
						System.out.println("Utility: " + utilAcc);
					}
					System.out.println("Brown: " + prop1Acc);
					System.out.println("Yellow: " + prop2Acc);
					break;
				case "Max":
					for(Space sp : board){
						if(sp instanceof Ownable){
							if(((Ownable)sp).getOwner().equals("Max")){
								if (sp instanceof RailRoad){
									ownsRail = true;
									railAcc += ((Ownable)sp).getEarnings();
								}
								else if (sp instanceof Utility){
									ownsUtil = true;
									utilAcc += ((Ownable)sp).getEarnings();
								}
								else{
									if((((Ownable)sp).getColor()).equals("Light Blue")){
										prop1Acc += ((Ownable)sp).getEarnings();
									}
									else if((((Ownable)sp).getColor()).equals("Red")){
										prop2Acc += ((Ownable)sp).getEarnings();
									}
								}
							}
						}
					}
					if(ownsRail == true){
						System.out.println("RailRoad: " + railAcc);
					}
					if(ownsUtil == true){
						System.out.println("Utility: " + utilAcc);
					}
					System.out.println("Light Blue: " + prop1Acc);
					System.out.println("Red: " + prop2Acc);
					break;
				case "Sue":
					for(Space sp : board){
						if(sp instanceof Ownable){
							if(((Ownable)sp).getOwner().equals("Sue")){
								if (sp instanceof RailRoad){
									ownsRail = true;
									railAcc += ((Ownable)sp).getEarnings();
								}
								else if (sp instanceof Utility){
									ownsUtil = true;
									utilAcc += ((Ownable)sp).getEarnings();
								}
								else{
									if((((Ownable)sp).getColor()).equals("Pink")){
										prop1Acc += ((Ownable)sp).getEarnings();
									}
									else if((((Ownable)sp).getColor()).equals("Dark Blue")){
										prop2Acc += ((Ownable)sp).getEarnings();
									}
								}
							}
						}
					}
					if(ownsRail == true){
						System.out.println("RailRoad: " + railAcc);
					}
					if(ownsUtil == true){
						System.out.println("Utility: " + utilAcc);
					}
					System.out.println("Pink: " + prop1Acc);
					System.out.println("Dark Blue: " + prop2Acc);
					break;
				case "Ray":
					for(Space sp : board){
						if(sp instanceof Ownable){
							if(((Ownable)sp).getOwner().equals("Ray")){
								if (sp instanceof RailRoad){
									ownsRail = true;
									railAcc += ((Ownable)sp).getEarnings();
								}
								else if (sp instanceof Utility){
									ownsUtil = true;
									utilAcc += ((Ownable)sp).getEarnings();
								}
								else{
									if((((Ownable)sp).getColor()).equals("Orange")){
										prop1Acc += ((Ownable)sp).getEarnings();
									}
									else if((((Ownable)sp).getColor()).equals("Green")){
										prop2Acc += ((Ownable)sp).getEarnings();
									}
								}
							}
						}
					}
					if(ownsRail == true){
						System.out.println("RailRoad: " + railAcc);
					}
					if(ownsUtil == true){
						System.out.println("Utility: " + utilAcc);
					}
					System.out.println("Orange: " + prop1Acc);
					System.out.println("Green: " + prop2Acc);
					break;
				default:
					break;
			} // end switch
		} // end for Player
	} // end printPlayerProfits

	private void initiateHouses(int num){
		for(Space sp : board){
			if(sp instanceof Property){
				((Property)sp).setNumHouses(num);
				((Property)sp).setIsCompleteSet(true);
			}
		}
	}

	public void setHousesWithBudget(){ // initiates all houses according to $600 budget
		for(Space sp : board){
			if(sp instanceof Property){
				switch(((Ownable)sp).getColor()){
					case "Brown":
						((Property)sp).setNumHouses(5);
						break;
					case "Light Blue":
						((Property)sp).setNumHouses(4);
						break;
					case "Pink": 
					case "Orange":
						((Property)sp).setNumHouses(2);
						break;
					default:
						((Property)sp).setNumHouses(1);
						break;
				}
			}
		} // end set houses for
	}

	public void initiateTrades(){ 
		tradesInitialized = true;
		for(Space sp : board){ // clears players' colored properties
			if(sp instanceof Property){
				for(Player pl : playerList){
					if(((Ownable)sp).getOwner().equals(pl.getName())){
						pl.removeProperty(sp.getName());
					}
				}
			}
		}

		for(Space sp : board){ // ...then assigns each player 2 monopolies
			if(sp instanceof Ownable){
				switch(((Ownable)sp).getColor()){
					case "Brown":
					case "Yellow":
						for(Player pl : playerList){
							if(pl.getName().equals("Bob")){
								pl.addProperty(sp.getName());
							}
						}
						((Ownable)sp).setOwner("Bob");
						break;
					case "Light Blue":
					case "Red":
						for(Player pl : playerList){
							if(pl.getName().equals("Max")){
								pl.addProperty(sp.getName());
							}
						}
						((Ownable)sp).setOwner("Max");
						break;
					case "Pink": 
					case "Dark Blue":
						for(Player pl : playerList){
							if(pl.getName().equals("Sue")){
								pl.addProperty(sp.getName());
							}
						}
						((Ownable)sp).setOwner("Sue");
						break;
					case "Orange":
					case "Green":
						for(Player pl : playerList){
							if(pl.getName().equals("Ray")){
								pl.addProperty(sp.getName());
							}
						}
						((Ownable)sp).setOwner("Ray");
						break;
					default:
						break;
				}
			}
		} // end assign monopolies for
		setHousesWithBudget();
		//initiateHouses(1); // each property is going to be given 1 house
	} // end initiateTrades

	private MonopolyBoard(){
		allPropertyTaken = tradesInitialized = false;

		board[0] = new PassGo("GO!");
		r[0] = 2;
		r[1] = 10;
		r[2] = 30;
		r[3] = 90;
		r[4] = 160;
		r[5] = 250;
		board[1] = new PropertyOf2("Brown", "Mediterranean Avenue", 60, 50, r);
		board[2] = new CommunityChestSpace("Community Chest");
		r[0] = 4;
		r[1] = 20;
		r[2] = 60;
		r[3] = 180;
		r[4] = 320;
		r[5] = 450;
		board[3] = new PropertyOf2("Brown", "Baltic Avenue", 60, 50, r);
		board[4] = new TaxSpace("Income Tax", 200);
		board[5] = new RailRoad("Reading Railroad", 200);
		r[0] = 6;
		r[1] = 30;
		r[2] = 90;
		r[3] = 270;
		r[4] = 400;
		r[5] = 550;
		board[6] = new PropertyOf3("Light Blue", "Oriental Avenue", 100, 50, r);
		board[7] = new ChanceCardSpace("Chance");
		board[8] = new PropertyOf3("Light Blue", "Vermont Avenue", 100, 50, r);
		r[0] = 8;
		r[1] = 40;
		r[2] = 100;
		r[3] = 300;
		r[4] = 450;
		r[5] = 600;
		board[9] = new PropertyOf3("Light Blue", "Connecticut Avenue", 120, 50, r);
		board[10] = new JailSpace("Jail Space");
		r[0] = 10;
		r[1] = 50;
		r[2] = 150;
		r[3] = 450;
		r[4] = 625;
		r[5] = 750;
		board[11] = new PropertyOf3("Pink", "St. Charles Place", 140, 100, r);
		board[12] = new Utility("Electric Company", 150);
		board[13] = new PropertyOf3("Pink", "States Avenue", 140, 100, r);
		r[0] = 12;
		r[1] = 60;
		r[2] = 180;
		r[3] = 500;
		r[4] = 700;
		r[5] = 900;
		board[14] = new PropertyOf3("Pink", "Virginia Avenue", 160, 100, r);
		board[15] = new RailRoad("Pennsylvania Railroad", 200);
		r[0] = 14;
		r[1] = 70;
		r[2] = 200;
		r[3] = 550;
		r[4] = 750;
		r[5] = 950;
		board[16] = new PropertyOf3("Orange", "St. James Place", 180, 100, r);
		board[17] = new CommunityChestSpace("Community Chest");
		board[18] = new PropertyOf3("Orange", "Tennessee Avenue", 180, 100, r);
		r[0] = 16;
		r[1] = 80;
		r[2] = 220;
		r[3] = 600;
		r[4] = 800;
		r[5] = 1000;
		board[19] = new PropertyOf3("Orange", "New York Avenue", 200, 100, r);
		board[20] = new FreeParking("Free Parking");
		r[0] = 18;
		r[1] = 90;
		r[2] = 250;
		r[3] = 700;
		r[4] = 875;
		r[5] = 1050;
		board[21] = new PropertyOf3("Red", "Kentucky Avenue", 220, 150, r);
		board[22] = new ChanceCardSpace("Chance");
		board[23] = new PropertyOf3("Red", "Indiana Avenue", 220, 150, r);
		r[0] = 20;
		r[1] = 100;
		r[2] = 300;
		r[3] = 750;
		r[4] = 925;
		r[5] = 1100;
		board[24] = new PropertyOf3("Red", "Illinois Avenue", 240, 150, r);
		board[25] = new RailRoad("B. & O. Railroad", 200);
		r[0] = 22;
		r[1] = 110;
		r[2] = 330;
		r[3] = 800;
		r[4] = 975;
		r[5] = 1150;
		board[26] = new PropertyOf3("Yellow", "Atlantic Avenue", 260, 150, r);
		board[27] = new PropertyOf3("Yellow", "Ventnor Avenue", 260, 150, r);
		board[28] = new Utility("Water Works", 150);
		r[0] = 24;
		r[1] = 120;
		r[2] = 360;
		r[3] = 850;
		r[4] = 1025;
		r[5] = 1200;
		board[29] = new PropertyOf3("Yellow", "Marvin Gardens", 280, 150, r);
		board[30] = new GoToJail("Go To Jail");
		r[0] = 26;
		r[1] = 130;
		r[2] = 390;
		r[3] = 900;
		r[4] = 1100;
		r[5] = 1275;
		board[31] = new PropertyOf3("Green", "Pacific Avenue", 300, 200, r);
		board[32] = new PropertyOf3("Green", "North Carolina Avenue", 300, 200, r);
		board[33] = new CommunityChestSpace("Community Chest");
		r[0] = 28;
		r[1] = 150;
		r[2] = 450;
		r[3] = 1000;
		r[4] = 1200;
		r[5] = 1400;
		board[34] = new PropertyOf3("Green", "Pennsylvania Avenue", 320, 200, r);
		board[35] = new RailRoad("Short Line", 200);
		board[36] = new ChanceCardSpace("Chance");
		r[0] = 35;
		r[1] = 175;
		r[2] = 500;
		r[3] = 1100;
		r[4] = 1300;
		r[5] = 1500;
		board[37] = new PropertyOf2("Dark Blue", "Park Place", 350, 200, r);
		board[38] = new TaxSpace("Luxury Tax", 100);
		r[0] = 50;
		r[1] = 200;
		r[2] = 600;
		r[3] = 1400;
		r[4] = 1700;
		r[5] = 2000;
		board[39] = new PropertyOf2("Dark Blue", "Boardwalk", 400, 200, r);
	}

	public void printBoard(){
		for(int i = 0; i < 40; i++){
			board[i].printSpace();
			System.out.println();
		}
	}

	public String getSpaceName(int i){ // needs index of the board
		return board[i].getName();
	}

	public Space getSpace(int i){
		return board[i];
	}
}