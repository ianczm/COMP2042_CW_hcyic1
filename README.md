# Software Maintenance Coursework

This individual coursework aims to apply Software Maintenance principles to the
original [Brick Destroy game by FilippoRanza](https://github.com/FilippoRanza/Brick_Destroy).

# Brick Destroy 2.0

This is a simple arcade video game. The player's goal is to destroy a level with a small ball.

Here are the key binds to play the game.
- `SPACE` to start or pause the game.
- `A` to move left.
- `D` to move right.
- `ESC` to access the pause menu.
- `ALT`+`SHIFT`+`F1` to open the console.

The game automatically pauses if the window loses focus.

# Added Features

## Documentation

### Game Info Screen

- This is to help the user get familiar with the game, especially if they are new.
- Appears in a formatted pop-up screen when user presses on the `Info` button in the main menu.

### JavaDocs

- This is to help fellow developers contribute with better understanding.
- JavaDocs are available for most new features such as scoring and new game behaviour.

### GitHub Readme

- Aims to provide a summary of changes made throughout this coursework.

## Scoring

### Rewards and Penalties

- This is to make the game more challenging and fast-paced to play.
- Players gain score for hitting bricks.
- A combo is recorded for the number of bricks hit without losing the ball.
- The score gained per brick increases according to the combo.
- The combo is lost each time the ball is lost.
- The combo is a function of a logarithmic curve to prevent exceedingly high scores.

### Leaderboards

- This is to encourage friendly competition among users.
- Leaderboards appear after each level or at the end of the game automatically.
- The user can press `H` to access the leaderboards anytime.
- Stores are scored in a `highscores.txt` file for persistence.
- Ranks from best to worst.

### User Identification

- Before the game, users will be prompted to input their username to represent themselves on the scoreboard.

## Game Behavior and Appearance

### Splash Screen

- The Splash Screen now features a brick wall which is more aligned with the game.

### Balls

- The vertical speed of balls increase each time it bounces off the player's platform.
- This is to encourage the user to rely more on their reflexes.
- Maintaining the ball being in the air, and hence the combo, would be more challenging but rewarding.
- Increases the pace of the game and makes level-clearing faster.

### Bricks

Added the following bricks to introduce variety, colour and challenges to the game.

- Clay
- Stone
- Granite
- Topaz
- Ruby
- Emerald

They feature different strengths, requiring different number of hits to break, as well as
being able to reflect the ball completely which is based on a unique pre-set probability.

### Levels

The game now features 7 levels in total. This increases variety and keeps the game
challenging and interesting as well. Levels are listed in order of difficulty.

- Full clay
- Clay and stone
- Clay and granite
- Granite and topaz
- Granite and ruby
- Granite and emerald
- Full emerald

# Project Refactoring and Maintenance

## Maven and Unit Testing

Introduced Maven to structure the project files according to an established archetype and
build `.jar` executable files to be run on other computers. Also has support for JUnit 5 unit tests.

There are currently detailed unit tests written for `leaderboards`, `ball`, `brick` and `platform`.

## MVC Design Pattern

Implemented the **Model-View-Controller** design pattern to most of the project.

- `Model` – Scores, Bricks, Balls, Platform
- `View` – Splash Screen, Game Board, Username Input Dialog, Debug Dialog
- `Controller` – Username Input, Debugging Input, Game Logic and Behaviour

## Class Organisation, Single Responsibility and Encapsulation

Organised closely related classes into their own packages, with the four main ones
being `control`, `leaderboards`, `model` and `view`.

This is to loosely couple unrelated classes and increase cohesion between related classes.
High cohesion is also the reason why leaderboards is separated from the rest of the packages.

Large classes and methods that expose underlying logic and implementations have also been
encapsulated through extracting smaller methods and lend to more abstracted and readable
code for the programmer.