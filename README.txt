MY LITTLE SHUT-IN VERSION 2.5:

Changelog: 
- Trimmed down some of the unnecessary bloat and redundant methods.
- Overhaulled the save system. Game saves are stored in a JDBC/Derby Database, instead of a .txt file. (Operations handled in DatabaseOps)
- Changed saving methods to work with new database.
- Methods in 'MyLittleShutIn' migrated to GameInstance GUI class and original class deleted.
- Created a GUI for the main game program and menu
- Tested the program with Unit Tests, all can be found inside the Test Packages.

Problems:

- Currently no functionality to enter commands from the GUI into the console, as such, multichoice events have been removed.
- ShutIns do not have inventory due to problems implementing command entry.
- Store and Profile buttons do not work.


PROGRAM DESIGN AND CONSTRUCTION PROJECT PROPOSAL:
MY LITTLE SHUT-IN

The object of this Virtual Pet game is to get your Shut-in to become the World's Fattest Man.

**t t** = Feature will not be featured in first version as it doesn't add much to the game initially, or must be cut in the interest of time. these features may be implemented in future versions.

FEATURES:

- The player will be able to name their Shut-in **t and give them a custom Hometown t**. The Shut-in will be saved in a file which can be loaded into the game from the game's title menu.

- Multiple Status Bars
	- Hunger (from 0 to 100, starts game at 10). The aim is to keep it as close to 0 as possible. As bodyweight increases, so will the rate Hunger accumulates. If this stat reaches 100, your shutin will die. **t when the hunger meter reaches 60. the rate that Bodyweight drops will slow down, as your shutin's body goes into starvation mode. t**

	- Boredom (from 0 to 100, starts game at 20). - If this meter reaches 100, your Shut-in will give up on their quest.

	- Bodyweight Meter (in kg) this is randomised at the start of the game, it can be within 65kg - 75 Kg. 

	- Health (out of 100, starts game at 80) - The health and wellbeing of your Shut-in. as Bodyweight increases, this deteriorates. A low Health increases the chances of Bad Random Events occuring.

	- Funds - Used to buy Food. Your shutin recieves the Unemployment Benefit, but can recieve other funds through random events and other means
		Your shut-in can also buy upgrades that increase his quality of life and mitigate the amount of calories he burns each day. (e.g. Urine Bottles prevents your Shut-in from burning precious calories by walking to the bathroom +0.5% bodyweight gain -5 Boredom Gain)

- Your shut-in will be represented by a pre-generated sprite (ASCII for command line version, Maybe a .PNG file for GUI Version?), which increases in size depending on his bodyweight.		

- In addition to this sprite, Messages detailing what your Shut-in is currently doing will appear on the screen (when not in a menu/upgrade screen etc)

**t - A Profile Screen showing the rates that your Shut-in's Status bars accumulate. t**

- Random Events that effect your Shutin's status. e.g. Your shut-in loses a limb due to Gangrene. -50kg Bodyweight. Some random events may allow you to make a choice. i.e. attempting to fake a Disability to get more funds from the Benefit (which could backfire)

- **t A Leaderboard screen, Noting the current record holders for Fattest Man and your Shut-in's current Bodyweight. t**


- If your shut-in becomes the World's fattest man. Nothing happens. Your shut-in recieves no reward and the game continues as normal.

- Upon Death, your shut-in's progress will be measured by the size of their coffin. (Regular Coffin, XL Coffin, Custom Coffin imported from Alabama, Piano Case, Elephant Transport Crate, Cargoship Container)

FAILSTATES: 
 - Hunger state reaches 100. The shutin starves.
 - **t Boredom meter reaches 100. Your shut-in has an ephitany and abandons their quest. Becomes an award winning bodybuilder. t** (standard death applies)
 - **t Death due to random health condition event that occurs at low health stat (Lethal Conditions start occuring at 25 or less Health) t** 0 Health = Death


