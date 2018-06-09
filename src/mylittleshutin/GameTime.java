package mylittleshutin;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class handles how time passes inside the game. It also contains: 
 * - The logic for all the Random Events that occur
 * - The Functionality for updating the game screen every game day (including sprites)
 * - The failstate information that triggers a Game Over
 * - and the Game Over Screen
 * 
 * @author Nigel
 */

public class GameTime {
	ShutIn character;
	Scanner scan;
	int daysPassed;
	int eventChoice;
	boolean choiceMade;
	Random rand;
	StatusEvent randomEvent;

	public GameTime(ShutIn targetCharacter) {
		character = targetCharacter;
		randomEvent = new StatusEvent();
		daysPassed = character.getDaysPassed();
		eventChoice = 0;
		choiceMade = false;
		rand = new Random();
		scan = new Scanner(System.in);
	}	
	public void shortUpdate(String message) { //Streamlines code for short update messages.
		System.out.println(character.getShutInName() + " " + message);
	}
	
	public void runEvent() { //Handles the random events that occur	
		character.checkFailStates(); //Checks if character is dead.
		if(character.checkAlive()) { //Runs game if character is alive.
				eventChoice = rand.nextInt(20) + 1; //Determines what event (or dialogue) will trigger.
				switch(eventChoice) {
					case 1: shortUpdate("is complaining about the character Giren in DragonSphere Ultra on a Forum.");
							break;
					case 2: System.out.println("After staying up until 4am last night, " + character.getShutInName() 
							+ " wakes up at 9pm, decides it's getting late and goes to bed.");
							break;
					case 3: shortUpdate("is whinging online about how hard it is living in a Developed Country.\n");
							break;
					case 4: shortUpdate("is speed-running a 20 year old Video Game while streaming to a whopping audience of 3 viewers.");
							break;
					case 5: shortUpdate("is blaming all his problems on someone called 'Chad'.");
							break;
					case 6: shortUpdate("is having a Romantic Evening with his Anime Wife.");
							break;
					case 7: shortUpdate("is pretending to be a Feminist to attract the attention of Girls online.");
							break;
					case 8: shortUpdate("is busy cyberbullying Phil Pheaton on Twutter.");
							break;
					case 9: shortUpdate("is browsing Trenchcoats online.");
							break;
					case 10: shortUpdate("is contemplating what he'll do after everyone starts realising his Genius.");
							break;
					case 11: shortUpdate("is stealing Fresh Memes and sharing them on Plebbit as his own creation.");
							break;
					case 12: System.out.println("While playing the Realm of Conflict, " + character.getShutInName() 
							+ " offers to upgrade other player's armour for free, then logs off once they have given him their gear.");
							break;
					case 13: shortUpdate("is giving unwanted, ill-informed and frankly, dangerous Squat form advice on the BodyCrafting.com Forums.");
							break;
					case 14: shortUpdate("just got banned from another Dating app for Hostile Behaviour.");
							break;
//					case 15: randomEvent.instaEvent(character, randomEvent.rareHatText, "B:F", "-20|20"); 
//							break;
//					case 16: randomEvent.instaEvent(character, randomEvent.cleanRoomText, "h:B", "-0.2|-10");						
//							break;
//					case 17:  randomEvent.healthCheckUp(character); 
//							break;
//					case 18: randomEvent.gameSale(character);
//							break;
//					case 19: randomEvent.fakeDisability(character);
//							break;
					default: shortUpdate("is staring at the ceiling.");
				}
			//}
			System.out.println("+---------------------------------------------------+ \n"); //Separates each Event.
		} else { //Stops passage of time, shows funeral Screen.
			character.funeral();
		}
	}
}
