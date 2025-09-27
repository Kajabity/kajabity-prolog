% ******************************************************************************
%   Copyright   :(c) 2008 Williams Technologies Limited
%
%   Project     :   Kajabity Prolog
%
% ******************************************************************************

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

:- assertg( op / 3, 'com.kajabity.prolog.builtin.predicate.Op3Predicate' ).

:- assertg( ! / 0, 'com.kajabity.prolog.builtin.predicate.CutAtom' ).
:- assertg( true / 0, 'com.kajabity.prolog.builtin.predicate.TrueAtom' ).
:- assertg( fail / 0, 'com.kajabity.prolog.builtin.predicate.FailAtom' ).

:- assertg( debug_show_goal_tree / 0, 'com.kajabity.prolog.builtin.predicate.DebugShowGoalTreeAtom' ).

:- assertg( tag / 2, 'com.kajabity.prolog.builtin.predicate.TagPredicate' ).


% ==============================================================================
% Basic I/O operations

:- assertg( read / 1, 'com.kajabity.prolog.builtin.predicate.Read1Predicate' ).
:- assertg( put / 1, 'com.kajabity.prolog.builtin.predicate.PutPredicate' ).
:- assertg( write / 1, 'com.kajabity.prolog.builtin.predicate.WritePredicate' ).

:- assertg( consult / 1, 'com.kajabity.prolog.builtin.predicate.ConsultPredicate' ).

% ==============================================================================
% Arithmetic and Logic operations


:- op( 700, xfx, = ).
X = X.

not( P ) :- P, !, fail.
not( P ).
:- op( 249, fy, not ).



% ==============================================================================
%	IS PREDICATE

:- assertg( is / 2, 'com.kajabity.prolog.builtin.predicate.IsPredicate' ).
:- op( 700, xfx, is ).

% ==============================================================================
%	ARITHMETIC COMPARISON PREDICATES

:- assertg( ==  / 2, 'com.kajabity.prolog.builtin.predicate.EqualSamePredicate' ).
:- op( 700, xfx, == ).

:- assertg( =:= / 2, 'com.kajabity.prolog.builtin.predicate.ArithEqualPredicate' ).
:- assertg( =\= / 2, 'com.kajabity.prolog.builtin.predicate.ArithNotEqualPredicate' ).
:- op( 700, xfx, =:= ).
:- op( 700, xfx, =\= ).
:- assertg( <   / 2, 'com.kajabity.prolog.builtin.predicate.ArithLessThanPredicate' ).
:- op( 700, xfx, < ).
:- assertg( =<  / 2, 'com.kajabity.prolog.builtin.predicate.ArithLessThanEqualPredicate' ).
:- op( 700, xfx, =< ).
:- assertg( >   / 2, 'com.kajabity.prolog.builtin.predicate.ArithGreaterThanPredicate' ).
:- op( 700, xfx, > ).
:- assertg( >=  / 2, 'com.kajabity.prolog.builtin.predicate.ArithGreaterThanEqualPredicate' ).
:- op( 700, xfx, >= ).

% ==============================================================================
%	ARITHMETIC FUNCTIONS

:- assert_function( - / 1, 'com.kajabity.prolog.builtin.arithmetic.NegateFunction' ).
:- assert_function( + / 2, 'com.kajabity.prolog.builtin.arithmetic.SumFunction' ).
:- assert_function( ++ / 2, 'com.kajabity.prolog.builtin.arithmetic.IntegerSumFunction' ).
:- assert_function( - / 2, 'com.kajabity.prolog.builtin.arithmetic.SubtractFunction' ).
:- assert_function( -- / 2, 'com.kajabity.prolog.builtin.arithmetic.IntegerDifferenceFunction' ).
:- assert_function( * / 2, 'com.kajabity.prolog.builtin.arithmetic.ProductFunction' ).
:- assert_function( ** / 2, 'com.kajabity.prolog.builtin.arithmetic.IntegerProductFunction' ).
:- assert_function( / / 2, 'com.kajabity.prolog.builtin.arithmetic.QuotientFunction' ).
:- assert_function( // / 2, 'com.kajabity.prolog.builtin.arithmetic.IntegerQuotientFunction' ).
:- assert_function( div / 2, 'com.kajabity.prolog.builtin.arithmetic.IntegerQuotientFunction' ).
:- assert_function( mod / 2, 'com.kajabity.prolog.builtin.arithmetic.ModulusFunction' ).
:- assert_function( ^ / 2, 'com.kajabity.prolog.builtin.arithmetic.PowerFunction' ).
:- assert_function( pow / 2, 'com.kajabity.prolog.builtin.arithmetic.PowerFunction' ).
:- assert_function( << / 2, 'com.kajabity.prolog.builtin.arithmetic.ShiftLeftFunction' ).
:- assert_function( >> / 2, 'com.kajabity.prolog.builtin.arithmetic.ShiftRightFunction' ).
:- assert_function( \ / 1, 'com.kajabity.prolog.builtin.arithmetic.LogicalNegateFunction' ).
:- assert_function( /\ / 2, 'com.kajabity.prolog.builtin.arithmetic.LogicalAndFunction' ).
:- assert_function( \/ / 2, 'com.kajabity.prolog.builtin.arithmetic.LogicalOrFunction' ).
:- assert_function( sin / 1, 'com.kajabity.prolog.builtin.arithmetic.SinFunction' ).
:- assert_function( cos / 1, 'com.kajabity.prolog.builtin.arithmetic.CosFunction' ).
:- assert_function( tan / 1, 'com.kajabity.prolog.builtin.arithmetic.TangentFunction' ).
:- assert_function( asin / 1, 'com.kajabity.prolog.builtin.arithmetic.ArcsineFunction' ).
:- assert_function( acos / 1, 'com.kajabity.prolog.builtin.arithmetic.ArcosineFunction' ).
:- assert_function( atan / 1, 'com.kajabity.prolog.builtin.arithmetic.ArctangentFunction' ).
:- assert_function( atan2 / 2, 'com.kajabity.prolog.builtin.arithmetic.Arctangent2Function' ).
:- assert_function( exp / 1, 'com.kajabity.prolog.builtin.arithmetic.NaturalExponentFunction' ).
:- assert_function( log / 1, 'com.kajabity.prolog.builtin.arithmetic.NaturalLogFunction' ).
:- assert_function( sqrt / 1, 'com.kajabity.prolog.builtin.arithmetic.SquareRootFunction' ).
:- assert_function( deg2rad / 1, 'com.kajabity.prolog.builtin.arithmetic.DegreesToRadiansFunction' ).
:- assert_function( rad2deg / 1, 'com.kajabity.prolog.builtin.arithmetic.RadiansToDegreesFunction' ).
:- assert_function( float / 1, 'com.kajabity.prolog.builtin.arithmetic.FloatFunction' ).
:- assert_function( int / 1, 'com.kajabity.prolog.builtin.arithmetic.IntegerFunction' ).
:- assert_function( integer / 1, 'com.kajabity.prolog.builtin.arithmetic.IntegerFunction' ).
:- assert_function( abs / 1, 'com.kajabity.prolog.builtin.arithmetic.AbsFunction' ).
:- assert_function( ceil / 1, 'com.kajabity.prolog.builtin.arithmetic.CeilFunction' ).
:- assert_function( floor / 1, 'com.kajabity.prolog.builtin.arithmetic.FloorFunction' ).
:- assert_function( pi / 0, 'com.kajabity.prolog.builtin.arithmetic.PiConstant' ).
:- assert_function( e / 0, 'com.kajabity.prolog.builtin.arithmetic.EulersConstant' ).
:- assert_function( rand / 0, 'com.kajabity.prolog.builtin.arithmetic.RandomFunction' ).

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
