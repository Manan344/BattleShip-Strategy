package solution;

import battleship.BattleShip;

/**
 * Starting code for Comp10152 - Lab#6
 *
 * I, Manan Patel, 000735153 certify that this material is my original work. No
 * other person's work has been used without due acknowledgement
 * 
 */
public class COMP10152_Lab6 {

    //Total games in order to calculate the average
    static final int NUMBEROFGAMES = 10000;
    
    /**
     * Method to play the game
     */
    public static void startingSolution() {

        //variable to keep track of shots
        int totalShots = 0;

        //Display battleship version
        System.out.println(BattleShip.version());

        //loop to check how many times the game should be played
        for (int game = 0; game < NUMBEROFGAMES; game++) {

            //BattleShip object created from jar file
            BattleShip battleShip = new BattleShip();
            
            //SampleBot object passed in battleship as an argument
            SampleBot sampleBot = new SampleBot(battleShip);

            //This method will run until all the ships are sunk 
            while (!battleShip.allSunk()) {
                //to fire shot on the ship
                sampleBot.fireShot();
            }

            //variable to store shots the taken per game
            int gameShots = battleShip.totalShotsTaken();

            //variable to store total shots in all the game
            totalShots += gameShots;
        }

        //Display the result of average shots
        System.out.printf("SampleBot - The Average # of Shots required in %d games to sink all Ships = %.2f\n", NUMBEROFGAMES, (double) totalShots / NUMBEROFGAMES);

    }

    
    /**
     * Main method to start battleship game
     *
     * @param args
     */
    public static void main(String[] args) {
        startingSolution();
    }
}
