package mylittleshutin;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The main File for My Little Shut-In. Contains the menu used to start a new game, the main menu.
 * @author Nigel
 */
public class MyLittleShutIn {
	public static Scanner scan = new Scanner(System.in);

    public static ShutIn newGame() {
        ShutIn newChar = new ShutIn();
        int nameEntered = 0;
        String sName;
        int choiceNum = 0;
		String nextInput;
		
		//SETTING THE NAME FOR THE CHARACTER
		System.out.println("Welcome to the world of My Little ShutIn!\n");
		System.out.println("What is your Shut-in's name?");
		sName = scan.nextLine();
		
		//NAME VALIDATION LOOP
        do {
			if(sName.isEmpty()) { //Sets Default Name for Character if nothing is entered.
				System.out.println("Since you can't think of a name, We'll give him a nice default one.");
				sName = "Rodger Default";
			}
            System.out.println("Ah! so your Shut-in's name is " + sName + "?\n" //Double Checks with the user Re: Character Name.
                    + "Type '1' for 'Yes' \n"
                    + "Type '2' for 'No' \n");
            try {
				nextInput = scan.nextLine();
				choiceNum = Integer.valueOf(nextInput);
            }
            catch(InputMismatchException | NumberFormatException error) {
				System.err.println("Oi! Numbers Only! Exceptional Event: " + error + "\nPress Enter to Continue.");
				scan.nextLine();
            }

            switch(choiceNum){
                case 1: System.out.println("Very Good!"); //Sets in name, Starts Game.
                        newChar.setShutInName(sName);
                        nameEntered = 1;
                        break;
                case 2: System.out.println("Alright, Try Again. Don't mess this up. \n" //Allows player to reenter name.
						+ "What is your Shut-in's Name?");
						sName = scan.nextLine();
                        break;
				default: System.out.println("Didn't catch that."); //Handles other numbers being entered, returns loop to DoubleCheck Menu
            }
        } while(nameEntered != 1);
		System.out.println("\n\nYou are in charge of " + newChar + ", an anti-social Shut in, who, at the ripe \n"
				+ "old age of 19, has decided that the only way he will get any satistifaction or \n"
				+ "recognition in this life is to become the World's Fattest Man.\n\n"
				+ " - Your goal is to keep your little cretin alive for as long as possible.\n"
				+ " - Keep him as Entertained and Healthy as possible. Don't make him starve either.\n"
				+ " - Every 5 Second in Game, a day will pass\n"
				+ " - Each Day, a random event may occur that will effect your character's well being. \n"
				+ " - Every week you will get a chance to buy supplies for your character. \n"
				+ " - WARNING: This game uses a Permadeath System. Once your character is dead, they're gone forever.\n\n "
				+ "Once you understand everything, Press any key then hit enter to continue.");
		scan.next();
        return newChar;
    }
	
	public static void main(String[] args) {
		mainMenu();
    }
	
	public void saveGame(ShutIn savedSI) {
		try {
			//PrintWriter writer = new PrintWriter(savedSI + ".txt");
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(savedSI + ".txt"));
			output.writeObject(savedSI);
		} catch(IOException err) {
			System.err.println("Cannot write to file! " + err);
		}
	}
	
	public static ShutIn loadGame() {
		ShutIn loadedShutIn = new ShutIn();
		Boolean gameFound = false;
		do {
			try {
				String loadName;
				System.out.println("Type the full name of the Shut-In you want to load: (Try Big McHuge for a Trial)");
				loadName = scan.nextLine();
				ObjectInputStream input = new ObjectInputStream(new FileInputStream(loadName + ".txt"));
				loadedShutIn = (ShutIn) input.readObject();
				System.out.println("Loaded: " + loadName + "!");
				gameFound = true;
			} catch(IOException | ClassNotFoundException err) {
				System.err.println("Can't load file! Please Try Again!");
			}
		} while(gameFound == false);
		return loadedShutIn;
	}
	
	public static void runGame(ShutIn runnableCharacter) {
		GameTime game = new GameTime(runnableCharacter);
		if(runnableCharacter.checkAlive() == true) {
			game.runTime();
		}
	}
    
    public static void mainMenu() {
        String select;
		boolean menuChoice = false;
		
		//Below is the ASCII art Introscreen.
		System.out.println(" ___ ___  __ __      _      ____  ______  ______  _        ___       _____ __ __  __ __  ______        ____  ____  \n" +
						   "|   |   ||  |  |    | |    |    ||      ||      || |      /  _]     / ___/|  |  ||  |  ||      |      |    ||    \\ \n" +
							"| _   _ ||  |  |    | |     |  | |      ||      || |     /  [_     (   \\_ |  |  ||  |  ||      | _____ |  | |  _  |\n" +
							"|  \\_/  ||  ~  |    | |___  |  | |_|  |_||_|  |_|| |___ |    _]     \\__  ||  _  ||  |  ||_|  |_||     ||  | |  |  |\n" +
							"|   |   ||___, |    |     | |  |   |  |    |  |  |     ||   [_      /  \\ ||  |  ||  :  |  |  |  |_____||  | |  |  |\n" +
							"|   |   ||     |    |     | |  |   |  |    |  |  |     ||     |     \\    ||  |  ||     |  |  |         |  | |  |  |\n" +
							"|___|___||____/     |_____||____|  |__|    |__|  |_____||_____|      \\___||__|__| \\__,_|  |__|        |____||__|__|");
		
		//MAIN MENU
		do {
			System.out.println("*-------------------------------*\n"
					+ "Welcome to My Little Shut-In!\n"
					+ "Type 'new' to start a new game\n"
					+ "Type 'load' to load an old game\n"
					+ "Type 'exit' to Quit\n"
					+ "*-------------------------------*\n");
			select = scan.nextLine();

			switch(select) {
				case "new": menuChoice = true; 
							runGame(newGame());
							break;
				case "load": menuChoice = true;
							runGame(loadGame());
							break;
				case "exit": System.out.println("Goodbye!");
							System.exit(0);
							break;
				default: System.out.println("Try Again \n");
			}
		}while(menuChoice == false);
    }
}