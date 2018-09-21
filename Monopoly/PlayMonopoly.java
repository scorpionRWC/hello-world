class PlayMonopoly{
	static MonopolyBoard mb;
	static Player bob;
	static Player max;
	static Player sue;
	static Player ray;

	public static void main(String[] args) {
		//mb.printBoard();
		bob = new Player("Bob");
		max = new Player("Max");
		sue = new Player("Sue");
		ray = new Player("Ray");

		mb.getInstance().addPlayer(bob);
		mb.getInstance().addPlayer(max);
		mb.getInstance().addPlayer(sue);
		mb.getInstance().addPlayer(ray);
		//mb.getInstance().initiateTrades();
		//mb.getInstance().setHousesWithBudget(); // budget is $600
		playRounds(40);
		mb.getInstance().printEarnings();
		System.out.println("Players were each given two monopolies.");
		System.out.println("Houses for each monopoly were initialized based on $600 budget.");
		mb.getInstance().printPlayerProfits();
	}

	private static void playRounds(int rounds){
		for(int i = 0; i < rounds; i++){
			System.out.println("Turn " + (i+1) + ":");
			bob.playerTurn();
			max.playerTurn();
			sue.playerTurn();
			ray.playerTurn();
		}
	}

	private static void fullGame(){
		do{
			if(!bob.isBankrupt()){
				bob.playerTurn();
			}
			if(!max.isBankrupt()){
				max.playerTurn();
			}
			if(!sue.isBankrupt()){
				sue.playerTurn();
			}
			if(!ray.isBankrupt()){
				ray.playerTurn();
			}
		}while(mb.getInstance().checkWinner() == null);	
	}

	private void printLandingHistory(){
		for(int i = 0; i < 40; i++){
			System.out.print(String.format("%30s : ", mb.getInstance().getSpaceName(i)));
			for(int j = 0; j < bob.getLandHistory(i) + max.getLandHistory(i) + sue.getLandHistory(i) + ray.getLandHistory(i); j++){
				System.out.print("*");
			}
			System.out.println();
		}
	}
}