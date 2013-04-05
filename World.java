package textbased;

import java.util.*;

/**
 * Text based version of Karel the Robot
 * Current version has 1 predefined map
 * @author Heather,Noel,Sam,Amber,Josh,MacsR4Luzrs
 */

public class World 
{
    private ArrayList walls = new ArrayList();//walls in world
    private ArrayList gems = new ArrayList(); //gems in world
    private boolean isRunning = true; //game ending bool
    Wall home = new Wall(0,0); // home space
    Player player; //object for karel
    
    //Map
    public static String level =
              "####################\n"
            + "#        $         #\n"
            + "#       $#$        #\n"
            + "#      $###$       #\n"
            + "#     $#####$      #\n"
            + "#    $#######$     #\n"
            + "#   $#########$    #\n"
            + "#  $###########$   #\n"
            + "#^ #############$ .#\n"
            + "####################\n";
    
    //Constructor - Set up world
    public World()
    {
        initWorld();
    }
    
    //Reads the map and adds all objects and their coordinates to arraylists
    public final void initWorld()
    {
        //create wall and gem objects
        Wall wall;
        Gem gem;
        
        //variables used to keep track of coordinates during loop
        int x = 0;
        int y = 0;
        
        for (int i = 0; i < level.length(); i++)
        {
            //Grab the item in string at i
            char item = level.charAt(i); 

            //Adjust X,Y value based on what character is at i
            //and create an item in the array list if needed
            if (item == '\n')
            {
                y += 1;
                x = 0;
            }
            else if (item == '#')
            {
                wall = new Wall(x,y);
                walls.add(wall);
                x += 1;
            }
            else if (item == '^')
            {
                player = new Player(x,y);
                x += 1;
            }
            else if (item == '$')
            {
                gem = new Gem(x,y);
                gems.add(gem);
                x += 1;
            }
            else if (item == '.')
            {
                home.SetX(x);
                home.SetY(y);
                x += 1;
            }
            else if (item == ' ')
            {
                x += 1;
            }
        }
        
        //Print the original map and legend
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("   Karel the Robot");
        System.out.print("Karel's directions: ^ North | "
                + "v South | > East | < West | ");
        System.out.println("# Walls | $ Gems | . Home");
        System.out.print(level);
        System.out.println("(Pick up all gems and go home)");
        
        //start up the game controller
        mController game = new mController();
    }
    
    //updates the map with karels new position
    public static void updateMap(int new_x, int new_y, char symbol) 
    {
        int num_rows = 10; // The number of rows
        int num_cols = 20; // The number of columns. Does not include the \n
        int num_symbols = 4; // The number of symbols for Karel
        int old_x = -1;
        int old_y = -1;
        char[] karel_symbols = {'^', '<', '>', 'v'}; // Karels symbols
        
        /* Converting from level string to temporary string array */
        String[] convert_map = new String [num_rows];
        for (int i= 0; i < num_rows; i++)
        {
            int x = (i * (num_cols + 1));
            convert_map[i] = level.substring(x, (x + num_cols));
        }
    
        /* Finding the last place Karel was and removing it */
        for (int i = 0; i < num_rows; i++)
        {
            for (int h = 0; h < num_symbols; h++)
            {
                /* Iterating through all of the possible Karel symbols
* and checking each string for their position. */
                int checker = convert_map[i].indexOf(karel_symbols[h]);
                if (checker != -1)
                {
                    old_y = i;
                    old_x = checker;
                    break;
                }
            }
        }
        
        /* Converting from temp string array to 2d character array*/
        char[][] current_map = new char [num_rows] [num_cols];
        for (int i = 0; i < num_rows; i++)
        {
            current_map[i] = convert_map[i].toCharArray();
        }
        if ((old_x != -1) && (old_y != -1))
        { // Making sure old_x and old_y were found
          current_map[old_y][old_x] = ' '; // Replacing Karel's old position
        }
        current_map[new_y][new_x] = symbol; // Putting Karel in his new position

        /* Overwriting level with updated map */
        String temp_level = new String();
        for (int i = 0; i < num_rows; i++)
        {
            for (int h = 0; h < num_cols; h++)
            {
                temp_level += current_map[i][h];
            }
            temp_level += '\n';
        }

        level = temp_level;
    }
    
    //Game controller
    final class mController
    {
        public mController()
        {
            //Run the game until finished
            while (isRunning == true)
            {
                //prompt user with choices and process input
                choiceMade(choiceOptions());
                
                //Print the updated map
                System.out.println("  Karel The Robot");
                System.out.print(level);
                System.out.println("Gems remaining: " + gems.size());
            }
        }
        
        //Prompt the user with choices
        public int choiceOptions()
        {
            System.out.println("Enter a choice:");
            System.out.println("1 - Go (Move Karel one space forward in his "
                    + "current direction)");
            System.out.println("2 - Turn Karel Left");
            System.out.println("3 - Turn Karel Right");
            System.out.println("4 - Multiple Instructions (Command Line)");
            
            Scanner in = new Scanner(System.in);
            int user_input = in.nextInt();
            System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            return user_input;
        }
        
        public  void actions() 
        {
            Scanner in = new Scanner(System.in);
            List<String> zoom = new ArrayList<>();
            String new_input = new String();
            boolean check;
            System.out.println("Input instructions followed by "
                    + "enter (Use 0 to run all commands):");
            System.out.println("left (turns karel left)");
            System.out.println("right (turns karel right)");
            System.out.println("go (moves karel one space in the direction"
                    + "he is facing)");
        
            while (true)
            {
                new_input = in.nextLine().toLowerCase();
                check = new_input.equals("0");
                if (check)
                {
                    break;
                }

                zoom.add(new_input);

            }
            for (String z : zoom) 
            { 
                switch (z)
                {
                    case "left": choiceMade(2);
                          break;
                    case "right": choiceMade(3);
                          break;
                    case "go": choiceMade(1); 
                          break;
                    default: System.out.println("Bad input: ");
                             System.out.println(z);
                             System.out.println("Continuing");

                }
            }

        }
        
        public void choiceMade(int choice)
        {
            //Get karels current direction
            char direction = player.GetDirection();
            
            if (choice == 1) //Attempt to move the player
            {                
                switch(direction)
                {
                    case '^':
                        handleMove(0,-1);
                        break;
                    case 'v':
                        handleMove(0, 1);
                        break;
                    case '>':
                        handleMove(1,0);
                        break;
                    case '<':
                        handleMove(-1,0);
                        break;
                }
                
            }
            else if (choice == 2) //Turn the player left
            {                
                switch(direction)
                {
                    case '^':
                        player.SetDirection('<');
                        break;
                    case 'v':
                        player.SetDirection('>');
                        break;
                    case '>':
                        player.SetDirection('^');
                        break;
                    case '<':
                        player.SetDirection('v');
                        break;
                }
            }
            else if (choice == 3)//turn the player right
            {                
                switch(direction)
                {
                    case '^':
                        player.SetDirection('>');
                        break;
                    case 'v':
                        player.SetDirection('<');
                        break;
                    case '>':
                        player.SetDirection('v');
                        break;
                    case '<':
                        player.SetDirection('^');
                        break;
                }
            }
            else if (choice == 4) //Get multiple commands
            {
                actions();
            }
            
            //update the map with new position or direction icon
            updateMap(player.GetX(),player.GetY(),player.GetDirection());
        }
        
        public void handleMove(int x, int y)
        {
            //Get where karel wants to move
            int newX = x + player.GetX();
            int newY = y + player.GetY();
            
            if (player.isWallCollision(newX, newY, walls))
            {
                //collided with wall - do not move karel
            }
            else if (player.isHomeCollision(newX,newY,home))
            {
                //if karel is home and all gems are taken, move and end game
                if(gems.isEmpty())
                {
                    player.move(x,y);
                    isRunning = false;
                    System.out.println("You have won!");
                }
            }
            else if (player.isGemCollision(newX, newY, gems) != -1)
            {
                //pick up the gem and move karel
                gems.remove(player.isGemCollision(newX, newY, gems));
                player.move(x, y);
            }
            else
            {
                //move karel
                player.move(x, y);
            }
        }
    }
}
