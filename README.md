# Checkers

This repository contains a simple checkers AI. The origin behind this project was a group project during a Software Engineering course at LeTourneau University.
I was in a group of 5 that wanted to develop two checkers AIs and have them fight each other at the end of the semester. Sadly, due to COVID, the project lost
a lot of its traction and we never developed the UI.
But this is still a functioning checkers AI that you can play against. The grid is laid out in an 8x8. Every turn, it will print the table and prompt the user
for two inputs: the piece you want to move and where to move it. Each square has a designiated number:

0  1  2  3  4  5  6  7

8  9  10 11 12 13 14 15

16 17 18 19 20 21 22 23

24 25 26 27 28 29 30 31

32 33 34 35 36 37 38 39

40 41 42 43 44 45 46 47

48 49 50 51 52 53 54 55

56 57 58 59 60 61 62 63

To play, you type in the number corresponding the square you want a piece to move from, and the number corresponding to the square you want said piece to mvoe to.

One player's pieces are represented via 1s, and the other is represented via 2s.
This game of checkers does not account for double jumps or kinging.

The game ends when one player can no longer make any moves. That may be when they have no more pieces left to move, or all their pieces are locked.
