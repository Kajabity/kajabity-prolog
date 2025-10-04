% royal_family.pl

% Facts:
parent(charles, william).
parent(charles, harry).
parent(diana, william).
parent(diana, harry).

parent(william, george).
parent(william, charlotte).
parent(william, louis).

% Rules:
grandparent(X, Y) :- parent(X, Z), parent(Z, Y).
