
Prolog IO
=========

The Prolog IO module provides the Prolog parser as well as set of predicates for reading and writing Prolog terms.


Predicates
----------

The table below lists the predicates in the Prolog IO module:

| Name                     | Operator | Type          | Class                        | Description                                                                                                                                |
|--------------------------|----------|---------------|------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|
| **consult**              |          |               | ConsultPredicate             |                                                                                                                                            |
| **debug_show_goal_tree** |          | GroundLiteral | DebugShowGoalTreeAtom        |                                                                                                                                            |
| **read**/1               |          |               | Read1Predicate               |                                                                                                                                            |
| **write**/1              |          |               | WritePredicate               |                                                                                                                                            |
| **:-**/1                 |          | Processor     | ImmediateProcessor           | A command processor to solve a solution once only - with no output.  It is used for running immediate goals - e.g. when consulting a file. |
| **?-**/1                 |          | Processor     | SolveProcessor               | A Term processor which expects to be handling a Goal to be solved - "?- goal, goal, ... ."                                                 |
| **assert_function**/2    |          |               | AssertFunctionPredicate      |                                                                                                                                            |
| **assertg**/2            |          |               | AssertGroundLiteralPredicate | A predicate to "assert" a Java predicate (known as a GroundLiteral).                                                                       |
