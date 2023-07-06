# Minesweeper--Game
This is a project for the Multimedia course.
I develop a Minesweeper game using Java.
General rules:
There are 2 levels: Level 1: grid size 9x9, #mines [9-11], time [120-180], no superBomb
                    Level 2: grid size 16x16, #mines [35-45], time [240-360], there is a superBomb

In the medialab folder there are different game scenarios. Each scenario has in the first lin the level of the game, in the second the number of mines, in the third the time in seconds and in the fourth 1 or 0, indicationg the existence of a superbomb. If the format of the file isn't like that and we try to play this game, the program throws an InvalidDescriptionException or if the values are not in the allowed limits, it throws an  InvalidValueException.

User can:
- Create a new game scenario
- Load a scenario from the medialab folder
- Start the game from the loaded file
- Exit the app

Left click-> open square.
Right click-> flag (unflag) square
If user flags the superbomb in the first 4 actions, all the squares in the same line and column open.
User loses if he opens a bomb or if the time is up and he wins if he opens all the squares without a bomb.
Every time the position of the mines is randomly selected. The position of the mines are writen in the mines.txt file. 
