/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylittleshutin;

/**
 * This class handles the sprites of the ShutIn, 
 * which will increase in size according to his bodyweight.
 * 
 * @author Nigel
 */
public class Sprites {
	private String spriteType;
	
	public Sprites() {
		spriteType = skinnySprite;
	}
	
	public String getSpriteType() {
		return spriteType;
	}
	
	public void setSpriteType(String spriteType) {
		this.spriteType = spriteType;
	}
	
	@Override
	public String toString() {
		return spriteType;
	}
	
	//The rest of the class contains the Sprites. Added an inconspicous number
	//on the character's belt to make sprite identification easier during testing.
	public static final String skinnySprite =
								"        __        \n" +
								"       /  \\      \n" +
								"     -------     \n" +
								"      |   |      \n" +
								"      |   |       \n" +
								"      || ||      \n" +
								"        |        \n" +
								"     ||   ||     \n" +
								"    | |   | |    \n" +
								"    | |-1-| |    \n" +
								"      |   |      \n" +
								"      |||||      \n" +
								"      |   |      \n" +
								"      |   |      \n" +
								"     ||   |||    \n";

	public static final String fatSprite =
									"       __        \n" +
									"      /  \\     \n" +
									"    ---------     \n" +
									"    /|     |\\   \n" +
									"     |     |     \n" +
									"      || ||      \n" +
									"       |||       \n" +
									"    |||   ||||   \n" +
									"  | |       | |  \n" +
									" |  |---2---|  | \n" +
									"    |       |     \n" +
									"    |||||||||     \n" +
									"      |   |      \n" +
									"      |   |      \n" +
									"     ||   |||    \n";

	public static final String hugeSprite =
							"       __      \n" +
							"      /  \\       \n" +
							"    ---------     \n" +
							"   ||      ||    \n" +
							"   ||      ||     \n" +
							"     ||| ||      \n" +
							"       |||       \n" +
							" ||||||   |||||| \n" +
							"| |           | |\n" +
							"| |--_     _--| |\n" +
							"  |  ---3---  |   \n" +
							"  |  |||||||  |   \n" +
							"  ||| |   | |||  \n" +
							"      |   |      \n" +
							"     ||   |||    \n";

	public static final String giganticSprite =
							"        __        \n" +
							"       /  \\       \n" +
							"     ---------    \n" +
							"   /|         |\\ \n" +
							"   ||         ||   \n" +
							"    ||||   ||||  \n" +
							"        |||       \n" +
							" |||||||   |||||| \n" +
							"| |              | |\n" +
							"| |--_       _---| |\n" +
							"  |   ---4---    |   \n" +
							"  |   ||||||||   |   \n" +
							"  |  |  |  |  |  |   \n" +
							"   ||   |  |   ||   \n" +
							"      |||  |||    \n";
}
