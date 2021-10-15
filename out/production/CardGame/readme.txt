Name: Chenwei Cui
Email: ccui@bu.edu
BU ID: U24658267

Compilation and execution instruction:
	1. javac .\Play.java
	2. java Play

Description of each class:
	Board: An abstraction of the game board. Responsible for displaying the game, recording the players' moves. Also provides a method for checking the winner.
	BoardGame: An abstraction of the general board game. TicTacToe and OrderAndChaos extends this class.
	Menu: Manages the main loop and the choosing of games.
	OrderAndChaos: The game of order and chaos. It is an extension of the class BoardGame. This class is responsible for logics that are specific to this game, for example the rule for determining the winner.
	Play: This is the entrypoint of the games. It creates and runs a menu object.
	Player: A class that keeps track of the information about a player, for example the player name, the player token, and the score.
	Slot: Basic element that comprises the Board. Has a getter and a setter.
	TicTacToe: The game of tic tac toe. It is an extension of the class BoardGame. This class is responsible for logics that are specific to this game, for example the rule for determining the winner.
	Utils: A class that provides utility functions.



Any additional works or missed functionalities?
	1. + Foolproof (User will get notified when inputted a wrong value, and it won't break the program.)
	2. + Improved the procedure for determining the winner.
	3. + Multiple user support, and users can use custom tokens.
	4. + Created a utility class to manage utility functions.
	