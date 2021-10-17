# CardGame

## Compilation and execution instruction:
    1. javac .\Play.java
    2. java Play


## Description of each class:
    Card:
        A class that abstracts a physical card. Can be set visible or invisible.

    PokerCard:
        The abstraction of a poker card. It extends the Card class. A poker card has a suit and a value. The corresponding setters and getters are implemented. In addition, a toString method is provided which can be later used.

    Deck:
        The abstraction of a deck of poker cards. It utilizes the PokerCard class. The Deck provides a range of methods resembling a real deck of cards. For example, the deck can shuffle, pop the first card, and reset to the original state.
	
    Player:
        The abstraction of general game player. It defines some attributes that a normal player should have, like name and score.

    PokerPlayer:
        The class of Player in Poker card game, which extends Class Player. It utilizes Class PokerCard to provide basic attributes and functions that can be used in BlackJack and Trianta.

    Game:
        The abstraction for a general game. It implements the common functionalities shared among games. For example registering all the players.

    PointGame:
        Super class define common methods used by BlackJack and Trianta

    Utils:
        A class that provides utility functions.

    Menu:
	    The entrance class of two games. It utilized the methods StartGame() of BlackJack and Trianta.
        
    Play:
        The entrance class of the whole program this time. It just calls a new Menu Object.

    BlackJack:
        The class of Playing BlackJack game, which extends Class PointGame. Class BlackJack contains special game process and game rules just for BlackJack. For example, the method dealerCards() is unique and just for BlackJack rules.

    Trianta:
        The class of Playing Trianta game, which extends Class PointGame. Class Trianta also contains unique game process and game rules.  For example, the method rotate_dealer() is unique and just for Trianta rules.


## Any additional works or missed functionalities?
    1. + Beautified commendline interface
        (Includes proper waiting and animations)
    2. + Implemented all requested functionalities, including the "split" for blackjack
        (When testing, we can 'split' multiple times with the program working correctly and not breaking)
    3. + Foolproof design
        (User will get notified when inputted a wrong value, and it won't break the program)
    4. + A Utils class that provides multiple functionalities
    5. + Really detailed code annotation, especially for the more "fruitful" classes: PointGame.java, BlackJack.java, and Trianta.java
    6. + Good cooperation among teammates