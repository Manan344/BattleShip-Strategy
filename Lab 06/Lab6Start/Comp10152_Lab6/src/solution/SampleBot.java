package solution;

import battleship.BattleShip;
import battleship.CellState;
import java.awt.Point;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A Sample random shooter - Takes no precaution on double shooting and has no
 * strategy once a ship is hit.
 * 
 * I, Manan Patel, 000735153 certify that this material is my
 * original work. No other person's work has been used without due
 * acknowledgement
 * 
 */
public class SampleBot {

    //variable to store size of the BattleShip game
    private final int gameSize;

    //variable to store instance object of BattleShip game
    private final BattleShip battleShip;

    //to check status of game board
    private final CellState[][] map;

    //to sink the ship upon a hit
    private final Stack<Point> hitStack;
    
    //to record previous hit
    private final ArrayList<Point> lastShot = new ArrayList<Point>();

    //Length of the ship
    private final ArrayList<Integer> shipSize = new ArrayList<Integer>();

    //to keep track of sunked ship
    private int trackSunk;

    //calculate number of shots fired to sink ship
    private int countShot;

    //variable to store last shot on x coordinate
    int xCheck = -1;

    //variable to store last shot on y coordinate
    int yCheck = -1;
    

    /**
     * Constructor keeps a copy of the BattleShip instance
     *
     * @param b previously created battleship instance - should be a new game
     */
    public SampleBot(BattleShip b) {

        //to store battleship instance
        battleShip = b;

        //to store boardsize of battleship
        gameSize = b.BOARDSIZE;

        //initialize stack class
        hitStack = new Stack<>();

        //collecting all ship size
        shipSize.add(2);
        shipSize.add(3);
        shipSize.add(3);
        shipSize.add(4);
        shipSize.add(5);

        //map is assigned with gamesize 
        map = new CellState[gameSize][gameSize];
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                //if map has no value
                map[i][j] = CellState.Empty;
            }
        }

    }
    

    /**
     * Fires shot at the ship and calls the battleship shoot method
     *
     * @return true if a Ship is hit, false otherwise
     */
    public boolean fireShot() {

        //Point class is set to null
        Point shot = null;

        //if  hitStack is not empty
        if (!hitStack.isEmpty()) {

            //instance of object is removed
            shot = hitStack.pop();
        } 
        
        //if hitStack is empty
        else {
            int highProb = 0;
            countShot = 0;
            lastShot.clear();            

            //locate the target with highest probability
            for (int y = 0; y < gameSize; y++) {
                for (int x = 0; x < gameSize; x++) {
                    //if cell is Empty
                    if (map[x][y] == CellState.Empty) {
                        
                        //Determine probability for cell
                        int points = getProb(x, y);

                        if (points >= highProb) {
                            highProb = points;
                            //contains x and y coordinates of a possible location
                            shot = new Point(x, y);
                        }
                    }
                }
            }
        }

        //Shoots the target and result will be saved in hit variable
        boolean hit = battleShip.shoot(shot);

        //if a Ship is hit
        if (hit) {

            //cell marked as Hit
            map[shot.x][shot.y] = CellState.Hit;

            //coordinates of shot are stored  
            lastShot.add(shot);
            countShot++;

            //check if ship is sunk
            if (trackSunk == battleShip.numberOfShipsSunk()) {
                if (xCheck >= 0) {
                    
                    //Fire at left direction
                    if (xCheck - shot.x == -1 && yCheck - shot.y == 0) {
                        if (shot.x - 1 >= 0 && map[shot.x - 1][shot.y] == CellState.Empty) {
                            Point target = new Point(shot.x - 1, shot.y);
                            hitStack.push(target);
                        }
                        if (shot.y - 1 >= 0 && map[shot.x][shot.y - 1] == CellState.Empty) {
                            Point target = new Point(shot.x, shot.y - 1);
                            hitStack.push(target);
                        }
                        if (shot.y + 1 < gameSize && map[shot.x][shot.y + 1] == CellState.Empty) {
                            Point target = new Point(shot.x, shot.y + 1);
                            hitStack.push(target);
                        }
                        if (shot.x + 1 < gameSize && map[shot.x + 1][shot.y] == CellState.Empty) {
                            Point target = new Point(shot.x + 1, shot.y);
                            hitStack.push(target);
                        }
                    } 
                    
                    //Fire upwards
                    else if (xCheck - shot.x == 0 && yCheck - shot.y == - 1 ) {
                        if (shot.x - 1 >= 0 && map[shot.x - 1][shot.y] == CellState.Empty) {
                            Point target = new Point(shot.x - 1, shot.y);
                            hitStack.push(target);
                        }
                        if (shot.x + 1 < gameSize && map[shot.x + 1][shot.y] == CellState.Empty) {
                            Point target = new Point(shot.x + 1, shot.y);
                            hitStack.push(target);
                        }
                        if (shot.y - 1 >= 0 && map[shot.x][shot.y - 1] == CellState.Empty) {
                            Point target = new Point(shot.x, shot.y - 1);
                            hitStack.push(target);
                        }
                        if (shot.y + 1 < gameSize && map[shot.x][shot.y + 1] == CellState.Empty) {
                            Point target = new Point(shot.x, shot.y + 1);
                            hitStack.push(target);
                        }
                    }
                    
                    //Fire at right direction
                    else if (xCheck - shot.x == 1 && yCheck - shot.y == 0) {
                        if (shot.x + 1 < gameSize && map[shot.x + 1][shot.y] == CellState.Empty) {
                            Point target = new Point(shot.x + 1, shot.y);
                            hitStack.push(target);
                        }
                        if (shot.y - 1 >= 0 && map[shot.x][shot.y - 1] == CellState.Empty) {
                            Point target = new Point(shot.x, shot.y - 1);
                            hitStack.push(target);
                        }
                        if (shot.y + 1 < gameSize && map[shot.x][shot.y + 1] == CellState.Empty) {
                            Point target = new Point(shot.x, shot.y + 1);
                            hitStack.push(target);
                        }
                        if (shot.x - 1 >= 0 && map[shot.x - 1][shot.y] == CellState.Empty) {
                            Point target = new Point(shot.x - 1, shot.y);
                            hitStack.push(target);
                        }
                    }    
                    
                    //Fire downwards
                    else if (xCheck - shot.x == 0 && yCheck - shot.y == 1) {
                        if (shot.x - 1 >= 0 && map[shot.x - 1][shot.y] == CellState.Empty) {
                            Point target = new Point(shot.x - 1, shot.y);
                            hitStack.push(target);
                        }
                        if (shot.x + 1 < gameSize && map[shot.x + 1][shot.y] == CellState.Empty) {
                           Point target = new Point(shot.x + 1, shot.y);
                           hitStack.push(target);
                        }
                        if (shot.y + 1 < gameSize && map[shot.x][shot.y + 1] == CellState.Empty) {
                            Point target = new Point(shot.x, shot.y + 1);
                            hitStack.push(target);
                        }
                        if (shot.y - 1 >= 0 && map[shot.x][shot.y - 1] == CellState.Empty) {
                            Point target = new Point(shot.x, shot.y - 1);
                            hitStack.push(target);
                        }
                    }
                    
                    else {
                        markShot(shot);
                    }

                } 
                else {
                    markShot(shot);
                }
                xCheck = shot.x;
                yCheck = shot.y;
            }
            
            else {
                trackSunk++;
                int minTarget = Collections.min(shipSize);

                //fires in the direction with highest probability
                switch (countShot) {
                    case 2:
                        shipSize.remove(new Integer(2));
                        hitStack.clear();
                        break;
                        
                    case 3:
                        if (countShot == minTarget) {
                            hitStack.clear();
                            shipSize.remove(new Integer(3));
                        } 
                        else {
                            //Fire around first hit until ship is sunk
                            hitStack.clear();
                            markShot(lastShot.get(0));
                        }
                        break;
                        
                    case 4:
                        if (minTarget == 3) {
                            hitStack.clear();
                            markShot(lastShot.get(0));
                        } 
                        else if (minTarget == 2) {
                            hitStack.clear();
                            markShot(lastShot.get(1));
                            markShot(lastShot.get(0));
                        }
                        break;

                    case 5:
                        switch (minTarget) {
                            case 2:
                                hitStack.clear();
                                markShot(lastShot.get(0));
                                break;
                            case 3:
                                hitStack.clear();
                                markShot(lastShot.get(1));
                                markShot(lastShot.get(0));
                                break;
                            case 4:
                                hitStack.clear();
                                markShot(lastShot.get(2));
                                markShot(lastShot.get(1));
                                markShot(lastShot.get(0));
                                break;
                                
                            default:
                                break;
                        }
                    default:
                        break;
                }
                trackSunk = battleShip.numberOfShipsSunk();
            }
       } 
        //coordinates are stored and marked as Miss so they won't get repeated
        else {
            map[shot.x][shot.y] = CellState.Miss;
        }
        return hit;
    }

    
    /**
     * Checking coordinates that surrounds target
     *
     * @param shot location of the shot
     */
    public void markShot(Point shot) {
        
        if (shot.x - 1 >= 0 && map[shot.x - 1][shot.y] == CellState.Empty) {
            Point target = new Point(shot.x - 1, shot.y);
            hitStack.push(target);
        }
        if (shot.x + 1 < gameSize && map[shot.x + 1][shot.y] == CellState.Empty) {
            Point target = new Point(shot.x + 1, shot.y);
            hitStack.push(target);
        }
        if (shot.y - 1 >= 0 && map[shot.x][shot.y - 1] == CellState.Empty) {
            Point target = new Point(shot.x, shot.y - 1);
            hitStack.push(target);
        }
        if (shot.y + 1 < gameSize && map[shot.x][shot.y + 1] == CellState.Empty) {
            Point target = new Point(shot.x, shot.y + 1);
            hitStack.push(target);
        }
    }

    
    /**
     * Calculate Probability on given coordinates
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return probability of a ship
     */
    public int getProb(int x, int y) {
        int xTop = getProbX(x, y, 1, 0);
        int xBottom = getProbX(x, y, -1, 0);
        int yTop = getProbY(x, y, 1, 0);
        int yBottom = getProbY(x, y, -1, 0);

        return xTop + xBottom + yTop + yBottom;
    }

    
    /**
     * Calculate Probability on x coordinate
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param xSide x position
     * @param count count probability
     * @return probability for X coordinate
     */
    public int getProbX(int x, int y, int xSide, int count) {
        
        if ((x >= 0 && x < gameSize) && map[x][y] == CellState.Empty && count < 4) {
            x += xSide;
            count++;
            count = getProbX(x, y, xSide, count);
        }
        return count;
    }
    

    /**
     * Calculate Probability on y coordinate
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param ySide y position
     * @param count count probability
     * @return probability for Y coordinate
     */
    public int getProbY(int x, int y, int ySide, int count) {
        
        if ((y >= 0 && y < gameSize) && map[x][y] == CellState.Empty && count < 4) {
            y += ySide;
            count++;
            count = getProbY(x, y, ySide, count);
        }
        return count;
    }
}
