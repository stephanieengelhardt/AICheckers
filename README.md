# AICheckers
This is a checkers game with an AI who plays checkers against you using mini-max alpha beta search.
Within this project, I created three different heuristics. The first heuristic is what the actual AI uses, the other two are for comparison. 
Let’s call it the good heuristic. The good heuristic checks the number of red versus black pieces, the number of red kings, 
the number of black kings, and if the next actions could eliminate a piece or not. The second heuristic improves that runtime 
because it doesn’t look at the actions that could be done to eliminate one of your pieces. Let’s call this one the medium heuristic. 
The medium heuristic checks the number of red versus black pieces, the number of red kings, and the number of black kings. 
The last one is completely random and can be used as a base comparator for the heuristics. Let’s call this the random heuristic.
Within this project, I created three different heuristics.

The good heuristic does well against the human opponent because it never jumps out in front of an opponent unless it is the only 
option or if it will get more opponents removed in the turn after that. However, if the human does well too, it’ll often end up in a tie 
where both have one king. Although the computer didn’t win in this situation, it still made good choices to get to the tie, so I think 
the heuristic is pretty good. The medium heuristic, on the other hand, doesn’t do very well. Without checking if it will get jumped over, 
the decision often ends up in the computer getting a checker removed, and thus eventually losing the game. The random heuristic never 
wins because it is randomly selecting options. It’ll eventually pick the wrong decision.

KNOWN ISSUE:
The checkers game only jumps over two pieces, after that it will stop the turn. I need to implement multiple step jumping. 
