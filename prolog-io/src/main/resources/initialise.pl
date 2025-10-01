% Copyright (c) 2003-2025 Simon J. Williams
%
% Licensed under the Apache License, Version 2.0 (the "License");
% you may not use this file except in compliance with the License.
% You may obtain a copy of the License at
%
%     http://www.apache.org/licenses/LICENSE-2.0
%
% Unless required by applicable law or agreed to in writing, software
% distributed under the License is distributed on an "AS IS" BASIS,
% WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
% See the License for the specific language governing permissions and
% limitations under the License.

% ==============================================================================
% Predefined Operators, Predicates, etc.

% :- op( 1200, fx,  ?- ).
% :- op( 1200, xfx, :- ).
% :- op( 1200, fx,  :- ).
% :- op( 1100, xfy, ; ).
% :- op( 1000, xfy, , ).
% :- op( 400, yfx, / ).

% ==============================================================================
% Basic Predicates



% ==============================================================================
% Basic I/O operations

% ==============================================================================
% Arithmetic and Logic operations


:- op( 700, xfx, = ).
X = X.

not( P ) :- P, !, fail.
not( P ).
:- op( 249, fy, not ).



% ==============================================================================
%	IS PREDICATE

:- op( 700, xfx, is ).

% ==============================================================================
%	ARITHMETIC COMPARISON PREDICATES

:- op( 700, xfx, == ).

:- op( 700, xfx, =:= ).
:- op( 700, xfx, =\= ).
:- op( 700, xfx, < ).
:- op( 700, xfx, =< ).
:- op( 700, xfx, > ).
:- op( 700, xfx, >= ).

% ==============================================================================
%	ARITHMETIC FUNCTIONS


% ==============================================================================
% Standard Prolog operators.

:- op( 1200, xfx, --> ).

:- op( 1050, xfy, -> ).

:- op( 900, fy, \+ ).

:- op( 700, xfx, \= ).
:- op( 700, xfx, \== ).
:- op( 700, xfx, @< ).
:- op( 700, xfx, @=< ).
:- op( 700, xfx, @> ).
:- op( 700, xfx, @>= ).
:- op( 700, xfx, =.. ).

:- op( 500, yfx, + ).
:- op( 500, yfx, - ).
:- op( 500, yfx, /\ ).
:- op( 500, yfx, \/ ).

:- op( 400, yfx, * ).
:- op( 400, yfx, // ).
:- op( 400, yfx, rem ).
:- op( 400, yfx, mod ).
:- op( 400, yfx, << ).
:- op( 400, yfx, >> ).

%:- op( 249, fy, \+ ).

:- op( 200, xfx, ** ).

:- op( 200, xfy, ^ ).

:- op( 200, fy, - ).
:- op( 200, fy, \ ).



% ==============================================================================
% Looping

repeat.
repeat :- repeat.

% ==============================================================================
% List operations

% Arg 2 must be a bound value, not a free variable.
index( [ Head | _ ], 1, Head ).
index( [ _ | Tail ], Position, Element ) :-
	Position > 1,
	NewPosition is Position - 1,
	index( Tail, NewPosition, Element ).

append( [], List, List ).	% [] + List = List
append( [ Head | Tail ], SecondList, 	[ Head | Rest ] ) :-
	append( Tail, SecondList, Rest ).

% Call reverse with a bound value for the first arg.
reverse( [], [] ).
reverse( [ Head | Tail ], Result ) :-
	reverse( Tail, Temp ),
	append( Temp, [ Head ], Result ).


member( X, [ X | L ] ) :- !.
member( X, [ Y | L ] ) :- member( X, L ).

% ==============================================================================
% I/O operations

% Writes a newline to the output stream.
nl :- put( 13 ).

% ==============================================================================
%	Type Primitives.
% ==============================================================================

%  Succeeds if X is bound to an uninstatiated variable.
var( X ) :- tag( X, 0 ).

%  Succeeds if X is instantiated.
nonvar( X ) :- tag( X, 0 ), !, fail.
nonvar( X ).

%  Succeeds if X is an atom.
atom( X ) :- tag( X, 3 ).

%  Succeeds if X is an integer.
integer( X ) :- tag( X, 1 ).

%  Succeeds if X is a real point number.
real( X ) :- tag( X, 2 ).
float( X ) :- tag( X, 2 ).

%  Succeeds if X is a number (float or integer).
number( X ) :- tag( X, 1 ), !.
number( X ) :- tag( X, 2 ).

%  Succeeds if X is an atom or a number.
atomic( X ) :- tag( X, 3 ), !.
atomic( X ) :- tag( X, 1 ), !.
atomic( X ) :- tag( X, 2 ).

%  Succeeds if X is a list (empty or non-empty).
list( X ) :- tag( X, 4 ), !.
list( X ) :- tag( X, 5 ).

%  Succeeds if X is a tuple or non-empty list.
compound( X ) :- tag( X, 6 ), !.
compound( X ) :- tag( X, 5 ).

?- write( 'Kajabity Prolog.  Enter "quit." to exit.' ), nl, nl.
/* End of File. */
