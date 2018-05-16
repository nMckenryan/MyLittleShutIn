package mylittleshutin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * This helper class contains all the functions the execute whenever a random event occurs.
 * @author Nigel
 */
public class StatusEvent {
    private String eventChoice;
	Scanner scan = new Scanner(System.in);
	private boolean choiceMade;
	private Random rand = new Random();
	
	public void StatusEvent() {
		choiceMade = false;
		eventChoice = "0";
	}
	
	//INSTANT EVENTS - Handles events that don't require an player interaction.
	public void instaEvent(ShutIn sampleShutIn, String message, String targetStats, String statChange) {
		System.out.println(message);
		System.out.println(targetStats + "   " + statChange);
		String[] statsEffected = targetStats.split(":"); //statsEffected designates codes to stat types, each divided by : character.
				//e.g. H = hunger, B = Boredom, M = Health(Medical), F = Funds.
				//		h = hunger gains, b = boredom gains, m = health gains, f = fund gains.
				//statChange works the same. statEffected = "H:b" statChange = "10|-5" means Hunger goes up 10, BoredomGains down 5.
		String[] statList = statChange.split("\\|");
	
		for(int i = 0; i < statsEffected.length; i++) {
			switch(statsEffected[i]) { //cycles through statE
				case "H":
					sampleShutIn.changeHunger(Integer.parseInt(statList[i]));
					break;
				case "B":
					sampleShutIn.changeBoredom(Integer.parseInt(statList[i]));
					break;
				case "M":
					sampleShutIn.changeHealth(Integer.parseInt(statList[i]));
					break;
				case "F":
					sampleShutIn.changeFunds(Integer.parseInt(statList[i]));
					break;
				//Changing Stat Gains. (Lowercase Codes)
				case "h":
					sampleShutIn.changeHungerGains(Float.parseFloat(statList[i]));
					break;
				case "b":
					sampleShutIn.changeBoredomGains(Float.parseFloat(statList[i]));
					break;
				case "m":
					sampleShutIn.changeHealthGains(Float.parseFloat(statList[i]));
					break;
				case "f":
					sampleShutIn.changeFundGains(Float.parseFloat(statList[i]));
					break;
			}
		}
	}
	
	//Holds the strings used to explain the insta event when it occurs.
	public String cleanRoomText = "Your mother storms into your Shut-In's room and demands that he clean it!\n"
				+ "He does as she asks quickly, for fear that she might cut off his chicken nuggest supplies. \n"
				+ "Your Shut In feels like he burned a few calories, but having a cleaning calmed him down. (-0.2 Weight Gain, -10 Boredom.)\n";
	
	public String rareHatText = "Your Shut-In recieved an Ultra Rare Helmet from a Random Drop while playing Squad Citadel 2!\n"
				+ "He sells it on the JetStream Marketplace immediately. (-20 Boredom, +$20 Funds)\n";

	
	//This event implements Random Chance. The player has a ~14% to gain a bonus to extra funds, 
	//otherwise, their funds are cut by quarter.	
	public void fakeDisability(ShutIn sampleShutIn) { 
		choiceMade = false;
		do {
			System.out.println("Your friend TeTsUo203 on your favorite Anime forum says he managed to get a lot more money from the Government by faking a mental illness\n"
					+ "Ring up the Unemployment Office and give it a try?\n"
					+ "Press 1 to make an attempt.\n"
					+ "Press 2 if it seems a bit risky.");

			try {
				eventChoice = scan.nextLine();
			} catch(InputMismatchException error) {
				System.err.println("Numbers Only! Exceptional Event: " + error);
				scan.next();
			}
			switch(eventChoice) {
				case "1": System.out.println("You call up the Unemployment Office... \n");
						int coinFlip = rand.nextInt(7) + 1;
						if(coinFlip == 3) {
							System.out.println("The Gods smile upon your Neckbeard this day! The lady on the phone files for additional disability allowance. (+$80 Funds)");
							sampleShutIn.changeFundGains(80f);
						}
						else {
							System.out.println(sampleShutIn + " tries to explain what illness he was diagnosed with, but the name of his illness changes time he mentions it.\n"
									+ "The lady on the phone is NOT impressed and cuts his Benefit by a quarter. (-25% Funds)");
							sampleShutIn.changeFundGains((-sampleShutIn.getFundGains() / 2));
						}
						choiceMade = true;
						break;
				case "2": System.out.println(sampleShutIn + "decides against calling, besides, that would involve talking to Normies.");
						sampleShutIn.changeBoredom(-10);
						choiceMade = true;
						break;
				default: System.out.println("Try Again"); 
			}
		} while(choiceMade = false);
    }
	
	//This event gives the player a choice. Some good, others bad.
	public void gameSale(ShutIn sampleShutIn) {
		choiceMade = false;
		do {
			System.out.println("Good News! JetGames is having a Flash Trial! \n"
				+ "Get Unlimited Access to a new game (but only for a limited time) \n"
				+ "Press 1 to play Hokuto Shin Kitchen\n"
				+ "Press 2 to play Mel Gibson Safari 3\n"
				+ "Press 3 to Bean Counter 2000\n"
				+ "or Press 4 to Pass on the Flash Trial.\n");
			
			try {
				eventChoice = scan.nextLine();
            }
            catch(InputMismatchException error) {
				System.err.println("Numbers Only! Exceptional Event: " + error);
				scan.next();
            }
			switch(eventChoice) {
				case "1": System.out.println("Hokuto Shin Kitchen was a lot of Fun! but it made " + sampleShutIn + " very hungry (+8 Hunger, -20 Boredom)");
						sampleShutIn.changeHunger(10);
						sampleShutIn.changeBoredom(-20);
						choiceMade = true;
						break;
				case "2": System.out.println("Mel Gibson Safari 3 was ... ok. Killed a bit of time. (-10 Boredom)");
						sampleShutIn.changeBoredom(-10);
						choiceMade = true;
						break;
				case "3": System.out.println("Bean Counter 2000 is terrible... and not in the good way. It was buggy and VERY boring. (+10 Boredom)");
						sampleShutIn.changeBoredom(10);
						choiceMade = true;
						break;
				case "4": System.out.println("None of those games caught" + sampleShutIn + "'s attention.");
						choiceMade = true;
						break;
				default: System.out.println("Try Again"); 
			}
		}while(choiceMade = false);
	}
	
	//SHOPPING FUNCTION - This event occurs once every seven days, it allows the player to purchase upgrades.
	public ShutIn shoppingTime(ShutIn sampleShutIn) {

			do {
				System.out.println(sampleShutIn + "'s Weekly Funds have arrived! What do you spend your \"\"hard earned\"\" dough on? \n"
						+ "Type 1 to buy three packs of Cards for the game 'Gwentstone: The Gathering ($10)\n"
						+ "Type 2 to order Waifu Warriors: Season " + sampleShutIn.getDaysPassed() / 7 + " ($20)\n"
						+ "Type 3 to order a small stash of Food from Mymridon.com. ($20)\n"
						+ "Type 4 to order a large stash of Food from Larry's Discount Warehouse. ($40)\n"
						+ "Type 5 to buy some BitDucats Cryptocurrency. ($50)\n"
						+ "Type 6 to order Weight Gainer 4000 (with extra Hydrochoridites). ($80) \n"
						+ "Type 7 to purchase Chinese Health Supplements from DjinniExpress ($30) \n"
						+ "Type 8 to save your money until next week.");
				eventChoice = scan.nextLine();
				
				switch(eventChoice) {
					//CARD PACK CHOICE
					case "1": if((choiceMade = creditCheck(sampleShutIn, 10)) == true) {
								System.out.println(sampleShutIn + " is happy with the cards he pulled from his booster packs.\n"
								+ "They will be a welcome addition to his Gwentstone Decks. (-10 Boredom.)");
								sampleShutIn.changeBoredom(-10);
							}
							break;
					//NEW SEASON CHOICE
					case "2": if((choiceMade = creditCheck(sampleShutIn, 20)) == true) {
								System.out.println("This new season of Waifu Warriors will keep " + sampleShutIn + " occupied over the week. "
								+ "Even if this season is terrible, He can always whinge about it online. (-30 Boredom)");
								sampleShutIn.changeBoredom(-30);
							} break;
					//SMALL FOOD ORDER CHOICE
					case "3": if((choiceMade = creditCheck(sampleShutIn, 20)) == true) {
								System.out.println("Mymridon's diligent drones delivered " + sampleShutIn + "'s food in little under an hour. (-20 Food)");
								sampleShutIn.changeHunger(-20);
							} break;
					//LARGE FOOD ORDER CHOICE
					case "4": if((choiceMade = creditCheck(sampleShutIn, 40)) == true) {
								System.out.println("A rickety truck rumbles past " + sampleShutIn + "'s house. The deliveryman throws a parcel onto the lawn and promptly leaves. \n"
								+ " A lot of the Food is passed it's used by date, or has Ukrainian Labels. It might not be the healthiest, but it's damn cheap. \n"
								+ "(-50 Hunger, -10 Health)");
								sampleShutIn.changeHunger(-50);
								sampleShutIn.changeHealth(-10);
							}
							break;
					//BITDUCAT CHOICE
					case "5": eventChoice = "0";
							choiceMade = false;
							while (!choiceMade) {
								System.out.println("BitDucats is a very volatile currency. The price could skyrocket or it could plummet. \n"
								+ "You will not see a return on your investment for a while. Are you sure you want to invest? \n"
								+ "Press '1' for Yes, Press '2' for No.");
								eventChoice = scan.nextLine();
								switch(eventChoice) { //No real reason for using an If conditional, just got sick of creating Switches.
									case "1": 
										if((choiceMade = creditCheck(sampleShutIn, 50)) == true) {
											if(sampleShutIn.getInventory().isEmpty()) {
												System.out.println("An Excited " + sampleShutIn + " converts $50 into BitDucats, thinking he'll be loaded in no time. (-$50 Funds) \n");
											} else {
												System.out.println(sampleShutIn + " adds to his stash of BitDucats. (-$50 Funds)\n");
											}
											sampleShutIn.addInventory("BitDucats");
											choiceMade = true;
										} break;
									case "2":
										System.out.println(sampleShutIn + " is unsure, and will hold off investing.\n");
										choiceMade = true;
										break;
									default: System.out.println("Try Again \n");
								}
							} break;
					//WEIGHT GAINER OPTION
					case "6": if((choiceMade = creditCheck(sampleShutIn, 80)) == true) {
								System.out.println( sampleShutIn + "'s sick gainz will sky rocket with this specially formulated, semi-legal weight"
								+ "gainer! He's gonna make it brah. (+ 5 kg Weight Gain per week, -10 Hunger.)");
								sampleShutIn.changeHunger(-10);
								sampleShutIn.changeWeightGains(0.7f); //5kg / 7 Days = 0.7 kg Per Day.
							}
							break;		
					//HEALTH SUPPLIMENTS - Health buffs are between 1 and 25.
					case "7": if((choiceMade = creditCheck(sampleShutIn, 30)) == true) {
								int healthBoost = rand.nextInt(25) + 1;
								System.out.println("Your special Chinese meds arrive in a slightly damp box. Normally, such bootleg meds would compromise\n"
								+ "the immune system. But " + sampleShutIn + " lives such an unhealthy lifestyle that it actually IMPROVES it. \n"
								+ "(+" + healthBoost + " Health)");
								sampleShutIn.changeHealth(healthBoost);
							} break;	
					//SAVE MONEY
					case "8": System.out.println("In an uncharacteristic show of restraint, " + sampleShutIn + " decides to hold onto his money for now.");
							choiceMade = true;
							break;
					default: System.out.println("Try Again.");
				}
			} while(choiceMade == false);
		return sampleShutIn;
	}
	
//Helper function for shoppingTime. Checks if the character has enough money to make a purchase.
	public boolean creditCheck(ShutIn deadbeat, int moneyOwed) { 
		if(moneyOwed <= deadbeat.getFunds()) { 
			deadbeat.changeFunds(-(moneyOwed)); //If character has enough money, cost is deducted from Funds.
			return true;
		} else {
			System.out.println(deadbeat + " cannot afford this! Money Needed: $" + moneyOwed
					+ "Money Owned: " + deadbeat.getFunds() + "Enter Anything to continue.");
			scan.next();
			return false;
		}
	}
	
	
	public ShutIn healthCheckUp(ShutIn sampleShutIn) { //Occurs randomly, severity based on character's health level.
		if(sampleShutIn.getHealth() <= 65) { //Characters will not get sick if they are over 65 health.
			int diceRoll = rand.nextInt(7) + 1;
			System.out.println("Something feels wrong, " + sampleShutIn + " video-calls FreeClinic.com and tells them his symptoms.\n");

			if(sampleShutIn.getHealth() <= 65) {
				System.out.println("The Doctor says you're in pretty bad shape.");
				switch(diceRoll) {
					case 1:
					case 2: System.out.println(sampleShutIn + " has been diagnosed with Ennuic Genericism, a typical disease with generic Symptoms. (-5 Health) \n");
						  sampleShutIn.changeHealth(-5);
						  break;
					case 3: System.out.println(sampleShutIn + "'s symptoms match those of Toxopyramidois, which makes the sufferer more susceptable \n"
						  + "to joining Multi-Level Marketing Schemes. (-5 Health, -$50 Funds)");
						  sampleShutIn.changeFunds(-50);
						  sampleShutIn.changeHealth(-10);
						  break;
					case 4: System.out.println("The Doctor diagnoses " + sampleShutIn + " with acute Hungrio-Hungriohippitus, which radically increases the victim's"
						  + " appetite for small, round mints. (-5 Health, +10 Hunger)");
						  sampleShutIn.changeHunger(10);
						  sampleShutIn.changeHealth(-5);
						  break;
					default: System.out.println("The doctor clears you with a clean bill of Health."
						  + sampleShutIn + " thinks he heard the Doctor say a vulgarity followed by the word 'Hypocondriac' before hanging up.");
				}
			} else if(sampleShutIn.getHealth() < 30){
				System.out.println("The Doctor is surprised you're still breathing.");
				switch(diceRoll) {
					case 1: System.out.println("The Doctor is amazed. " + sampleShutIn + " is riddled with disease, but \n"
						+ "instead of attacking vital systems, each disease is attacking other diseases in a fight for dominance. \n"
						+ "Many of " + sampleShutIn + "'s Illnesses are countering each other's effects. The last surviving disease \n"
						+ "will be too weak to survive attack form antibodies. (+20 Health)");
						sampleShutIn.changeHealth(20);
						break;
					case 2:
					case 3: System.out.println("The Doctor found a large, rapidly-growing absess on " + sampleShutIn + "'s body. \n"
							+ "He is rushed to the Hospital for Emergency Surgery. Eight hours later, the absess is removed. \n"
							+ "(-15 kg Weight. +5 Health.)");
						sampleShutIn.setBodyweight(-15);
						sampleShutIn.changeHealth(5);
						break;
					case 4:
					case 5: System.out.println(sampleShutIn + " is diagnosed with Extremesomnia, a condition that causes the sufferer to fall into a "
							+ " deep sleep for 15 hours at a time. (-15 Health, +3 Weight Gain)");
						sampleShutIn.changeHealth(-15);
						sampleShutIn.changeWeightGains(3);
					case 6: System.out.println(sampleShutIn + " had a Heart Attack during the call! (-25 Health) \n");
						sampleShutIn.changeHealth(-25);
						break;
					default: System.out.println("Surprisingly, The Doctor finds nothing wrong, but still delivers a lengthy lecture. (-5 Health)");
							 sampleShutIn.changeHealth(-5);
				}
			}
		} else {
			System.out.println(sampleShutIn + " wakes up with a slight cough and a sense of dread, but it goes away after 20 minutes.");
		}
	  return sampleShutIn;
	}
	
	public void bitDucats(ShutIn sampleShutIn) { //Handles the events involving BitDucats.
		int coinFlip = rand.nextInt(10) + 1; // 10% chance of Increase, 10% change of decrease. Most of the time it stagnates. Just like real life! :D
		switch(coinFlip) {
			case 1:	//PRICE INCREASE
				if(sampleShutIn.getInventory().contains("bitDucats")) {
					System.out.println("BitDucat prices are through the roof!\n"
							+ "Should " +sampleShutIn +" sell his Bitducats now? or wait for a better opportunity?");
					priceAction(sampleShutIn, 100);
				} else { //Will not effect character without BitDucats in their inventory.
					System.out.println("BitDucat Prices have hit an all time high, a pity " + sampleShutIn + " doesn't own any.");
				} break;
			case 2: //PRICE DECREASE
				if(sampleShutIn.getInventory().contains("bitDucats")) {
				   System.out.println(sampleShutIn + " woke up to find his favorite forum filled with panicked BitDucat investors!\n"
						   + "Apparently, the price has tanked! People are afraid that this is a market crash and that BitDucat will be worthless in the future!"
						   + "Should " +sampleShutIn +" tap out and sell his BitDucats now?");
				   priceAction(sampleShutIn, 15);
			   } else {
				   System.out.println(sampleShutIn + " is reading Forum posts from inconsolable BitDucat investors, who lost thousands of dollars "
						   + "in the recent crash.\n");
			   } break;
			default: //PRICE STAGNATION
			   if(sampleShutIn.getInventory().contains("bitDucats")) {
				   System.out.println("BitDucat Prices have stagnated, They're worth the same amount as when " + sampleShutIn + "last bought his.\n"
						   + "Should " +sampleShutIn +" sell his Bitducats now? or wait for a better opportunity?");
				   priceAction(sampleShutIn, 50);
			   } else {
				   System.out.println(sampleShutIn + " is reading Forum posts from BitDucat investors trying to get new people to invest.");
			   }
		}
	}
		
	public void priceAction(ShutIn sampleShutIn, int bitducatPrice) { //Handles processing sale of Bitducats.
		do {
			int earnedAmount = sampleShutIn.getInventory().size() * bitducatPrice; //bitducatPrice holds price of one BitDucat.
			System.out.println("Enter 1 to Sell, or Enter 2 to Wait");
			eventChoice = scan.nextLine();
			switch(eventChoice) {
				case "1": 
					System.out.println(sampleShutIn + " sells all his BitDucats, earning a total of: $" + earnedAmount);
					sampleShutIn.changeFunds(earnedAmount);
					sampleShutIn.clearInventory();
					break;
				case "2":
					System.out.println(sampleShutIn + " decides to hold onto his Digital Cash Stash.\n");
					break;
				default: System.out.println("Try Again");
			}
		} while (!eventChoice.equals("1") && !eventChoice.equals("2"));
	}	
} //END OF PROGRAM
