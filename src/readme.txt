Name: Qilin Yang
Email: yangql15@bu.edu
BU ID: U73204239

Compilation and execution instruction:
	1. javac .\Play.java
	2. java Play

Description of each class:
	Card:
		A class that abstracts a physical card. Can be set visible or invisible.

	PokerCard:
		The abstraction of a poker card. It extends the Card class. A poker card has a suit and a value. The corresponding setters and getters are implemented. In addition, a toString method is provided which can be later used.

	Deck:
		The abstraction of a deck of poker cards. It utilizes the PokerCard class. The Deck provides a range of methods resembling a real deck of cards. For example, the deck can shuffle, pop the first card, and reset to the original state.
	
	Game:
		The abstraction for a general game. It implements the common functionalities shared among games. For example registering 

	Utils:
		A class that provides utility functions.

	BlackJack:
		The class of Playing BlackJack game, which extends Class PointGame. Class BlackJack contains special game process and game rules just for BlackJack. For example, the method dealerCards() is unique and just for BlackJack rules.

	Trianta:
		The class of Playing Trianta game, which extends Class PointGame. Class Trianta also contains unique game process and game rules.  For example, the method rotate_dealer() is unique and just for Trianta rules.


    PointGame: Super class define common methods used by BlackJack and Trianta

Any additional works or missed functionalities?
	1. + Foolproof (User will get notified when inputted a wrong value, and it won't break the program.)
	2. + Utils class provides multiple functionalities.
	3. + beautifulWait.
	4. + Implemented all requested functionalities, including the "split" for blackjack.
	