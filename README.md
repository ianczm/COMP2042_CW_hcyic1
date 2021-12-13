# Software Maintenance Coursework

This individual coursework aims to apply Software Maintenance principles to the
original [Brick Destroy game by FilippoRanza](https://github.com/FilippoRanza/Brick_Destroy).

### Brick Destroy Description

This is a simple arcade video game. The player's goal is to destroy a level with a small ball.

Here are the key binds to play the game.
- `SPACE` to start or pause the game
- `A` to move left
- `D` to move right
- `ESC` to access the pause menu
- `ALT`+`SHIFT`+`F1` to open the console

The game automatically pauses if the window loses focus.

# Added Features

#### High Score Username System

- User will be prompted for username before starting game
- This is used for identifying them in the scoreboard

#### High Score System

- High score menu appears after each level, at the end of the game or whenever the user presses `H`
- Stores scores in a permanent `highscores.txt` file
- Sorts best ranking to the top
- Menu is a JFrame component
- Use `H` to access the high score menu anytime

#### Reward/Penalty System

- Player gains scores when bricks are hit
- More bricks hit with the same ball gains more score based on a multiplier
- Multiplier is a function of a logarithmic curve to prevent exceedingly high scores
- Multiplier is reset each time the ball is lost
