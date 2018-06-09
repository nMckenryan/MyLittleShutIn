package mylittleshutin;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class builds the Object which will store the ShutIn's data
 * @author Nigel
 */

public class ShutIn {

	public final int MAX_VALUE = 100;
	public final int MIN_VALUE = 0;
	
	private int saveID;
    private String shutInName;
    private double bodyweight;
    private int hunger;
    private int boredom;
    private int health;
    private int funds;
	private boolean isAlive;
	private int daysPassed;
	private ArrayList<String> inventory;
	
	//Handles all the status gains as time passes.
	//This used to be within another class but it became too unwieldy.
	private float hungerGains;
    private float boredomGains;
    private float healthGains;
    private float weightGains;
	private float fundGains;

	
    public ShutIn(){
		saveID = -1;
		saveID = 2;
        shutInName = "Rodger Default";
        bodyweight = Math.random() * (75 - 65) + 65; //Sets the character's weight between 65kg - 75kg
        boredom = 20;
        health = 90;
        hunger = 10;
        funds = 50;
		isAlive = true;
		daysPassed = 1;
		inventory = new ArrayList<>();
		inventory.add("");
		
		hungerGains = 1.0f;
		boredomGains = 1.0f;
		healthGains = 0.3f;
		weightGains = 0.5f;
		fundGains = 20.0f; //per week
    }
	
	public ShutIn(String attributeString) {		
		String[] attPart = attributeString.split(", ");
		
		saveID = Integer.parseInt(attPart[0]);
        shutInName = attPart[1];
        bodyweight = Double.parseDouble(attPart[2]);
        boredom = Integer.parseInt(attPart[3]);
        health = Integer.parseInt(attPart[4]);
        hunger = Integer.parseInt(attPart[5]);
        funds = Integer.parseInt(attPart[6]);
		isAlive = Boolean.parseBoolean(attPart[7]);
		daysPassed = Integer.parseInt(attPart[8]);

		hungerGains = Float.parseFloat(attPart[9]);
		boredomGains = Float.parseFloat(attPart[10]);
		healthGains = Float.parseFloat(attPart[11]);
		weightGains = Float.parseFloat(attPart[12]);
		fundGains = Float.parseFloat(attPart[13]);
		inventory = new ArrayList();
		inventory.add("nada");
	}
	
	public static ShutIn listToCharacter(ArrayList<String> list) {
		ShutIn newShutIn = null;
		try {
			StringBuilder build = new StringBuilder();
			for(Object val : list) {
				build.append(val.toString().replace("'", "")); //trims ' around strings.
			}
			newShutIn = new ShutIn(build.toString());
		} catch (Exception err) {
			System.out.println("Could not convert list to character: " + err);
		}
		return newShutIn;
	}
		
	public void saveGame() { //Handles saving of the game. Completed automatically every game day.
		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(this + ".txt"));
			output.writeObject(this);
		} catch(IOException err) {
			System.err.println("Cannot write to file! " + err);
		}
	}
	
	public void checkFailStates(){ //FAILSTATES: Defines certain conditions that trigger a Game Over. Death is Permanent.
		if(this.getHunger() > 100) {
			System.out.println(this.shutInName + "has died of Hunger. \n");
			this.setAlive(false);
		} else if(this.getHealth() < 0) {
			System.out.println(this.shutInName + " has succumbed to one of the many diseases festering in his body.");
			this.setAlive(false);
		} else if(this.getBoredom() > 100) {
			System.out.println(this.shutInName + " became so bored, his heart literally stopped.");
			this.setAlive(false);
		}	
	}
	
	public void funeral() { 	//GAME OVER SCREEN: Once a ShutIn dies, the Player is presented with this screen.
		String coffinSize = this.shutInName;
		double charSize = this.getBodyweight();
		
		if(charSize >= 200.0) { 	//Determines how large the character's coffin is.
			coffinSize += " was buried in a Extra Large Coffin";
		} else if(charSize >= 300.0) {
			coffinSize += " loaded into a extra large Coffin imported from Missisippi.";
		} else if(charSize >= 400.0) {
			coffinSize += " was loaded into a Piano Crate";
		} else if(charSize >= 500.0) {
			coffinSize += "'s body was airlifted onto a beach via two helicopters, where it was rigged with dynamite.";
		} else {
			coffinSize += " was buried in a Regular Coffin";
		}
		
		System.out.println("+----------------R.I.P IN PEACE  " + this.shutInName + "  --------------------------+\n" 
				+ this.shutInName + " is Dead. He is no more. He has ceased to be. \n"
				+ "He has expired and gone to meet his maker (not you, I meant Jolly Old Jehovah). \n"
				+ coffinSize + "\n"
				+ "The Forums he frequented mourned him for about a day, then went back to \n"
				+ "complaining about Video Games that they hadn't even played.\n"
				+ "All his possesions were divided and sold, Within a few years he will be forgotten. \n"
				+ "Ashes to Ashes, Dust to Dust. Be more bloody careful next time. \n"
				+ "+--------------------------------------------------------------------------+\n");
	}
	
	public void updateStatus() { //Updates the character's status each day.
		this.bodyweight += this.weightGains - (getBodyweight() / 100f); //As the character grows, the harder it is for him to gain weight.
        this.boredom += this.boredomGains;
        this.health += this.healthGains - (bodyweight /70.0f); //For every 70kg the shut in carries, the faster Health deteriorates.
        this.hunger += this.hungerGains + (bodyweight / 60.0f); //Every 60kg the character carries, the hungrier he gets.
		
		if(this.daysPassed % 7 == 0) { //The shutin gets paid every 7 days.
			this.funds += this.getFundGains();
		}
		
		//Instills limits on health parameters. (most can't go higher than 0, or 100);
		if(this.health > MAX_VALUE) { //100 = healthier, 0 = Dead.
			this.health = MAX_VALUE;
		}
		if(this.boredom < MIN_VALUE){ //100 = dead. 0 = Fine.
			this.boredom = MIN_VALUE;
		}
		if(this.hunger < MIN_VALUE){ //Same as above.
			this.hunger = MIN_VALUE;
		}
		if(this.funds < MIN_VALUE) {
			this.funds = MIN_VALUE;
		}
	}
	
	//GETTERS AND SETTERS. The 'Setters' have been change to 'Changers' i.e. 'changeVariable' for clarity re: events.
	
	@Override
	public String toString() { //Gets default values for inserting new character into DB
		return saveID + ", '" + shutInName + "', " + bodyweight + ", " + boredom + ", " + health + ", " + hunger + ", " + funds + ", " + isAlive + ", " + daysPassed + ", " 
		+ hungerGains + ", " + boredomGains + ", " + healthGains + ", " + weightGains + ", " + fundGains + ", '" + "nada" + "'";
	}
	
	public int getSaveID() {
		return saveID;
	}

	public void setSaveID(int shutID) {
		this.saveID = shutID;
	}
	
    public String getShutInName() {
        return shutInName;
    }
	
    public void setShutInName(String shutInName) {
        this.shutInName = shutInName;
    }

    public double getBodyweight() {
        return bodyweight;
    }
	
	public String getTextBodyweight() { //Formats Bodyweight to 2 decimal points for text display
		DecimalFormat nf = new DecimalFormat("#0.00");     
        return nf.format(bodyweight);
    }
	

    public void setBodyweight(float bodyweight) {
        this.bodyweight = bodyweight;
    }

    public int getHunger() {
        return hunger;
    }

    public void changeHunger(int hunger) {
        this.hunger += hunger;
    }

    public int getBoredom() {
        return boredom;
    }

    public void changeBoredom(int boredom) {
        this.boredom += boredom;
    }

    public int getHealth() {
        return health;
    }

    public void changeHealth(int health) {
        this.health += health;
    }

    public int getFunds() {
        return funds;
    }

    public void changeFunds(int funds) {
        this.funds += funds;
    }
	
	public boolean checkAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}	
	
	public int getDaysPassed() {
		return daysPassed;
	}
	
	public void addDay() {
		this.daysPassed += 1;
	}
	
	public void setDaysPassed(int passedDays) {
		this.daysPassed = passedDays;
	}
	
	public float getHungerGains() {
        return hungerGains;
    }

    public void changeHungerGains(float hungerGains) {
        this.hungerGains += hungerGains;
    }

    public float getBoredomGains() {
        return boredomGains;
    }

    public void changeBoredomGains(float boredomGains) {
        this.boredomGains += boredomGains;
    }

    public float getHealthGains() {
        return healthGains;
    }

    public void changeHealthGains(float healthGains) {
        this.healthGains += healthGains;
    }

    public float getWeightGains() {
        return weightGains;
    }

    public void changeWeightGains(float weightGains) {
        this.weightGains += weightGains;
    }
	
	public float getFundGains() {
		return fundGains;
	}

	public void changeFundGains(float fundGains) {
		this.fundGains += fundGains;
	}
	
	public ArrayList<String> getInventory() {
		return inventory;
	}

	public void addInventory(String item) {
		this.inventory.add(item);
	}
	
	public void clearInventory() {
		this.inventory.clear();
	}
}
