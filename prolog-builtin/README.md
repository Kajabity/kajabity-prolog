Prolog Builtin
==============

This module contains the builtin Prolog functions and operators.

The builtin functions and operators are listed below.

| Name                     | Operator | Type          | Class                          | Description                                                                                                       |
|--------------------------|----------|---------------|--------------------------------|-------------------------------------------------------------------------------------------------------------------|
| :-/2                     |          | Processor     | AssertProcessor                | Assert a "Horn" clause of the form: -<term>:- <term>, <term>.                                                     |
| **quit**/0               |          | Processor     | QuitProcessor                  | Quit.                                                                                                             |
| **fact**/0               |          | Processor     | FactProcess                    | Default processor.                                                                                                |
| **list_db**/0            |          | Processor     | ListDatabaseProcessor          | Dump all database entries.                                                                                        |
| **=:=**/2                |          | GroundLiteral | ArithEqualPredicate            |                                                                                                                   |
| **>=**/2                 |          | GroundLiteral | ArithGreaterThanEqualPredicate |
| **>**/2                  |          | GroundLiteral | ArithGreaterThanPredicate      |                                                                                                                   |
| **>=**/2                 |          | GroundLiteral | ArithLessThanEqualPredicate    |                                                                                                                   |
| **>**/2                  |          | GroundLiteral | ArithLessThanPredicate         |                                                                                                                   |
| **=\\=**/2               |          | GroundLiteral | ArithNotEqualPredicate         |                                                                                                                   |
| **==**/2                 |          | GroundLiteral | EqualSamePredicate             |                                                                                                                   |
| **is**/2                 |          | GroundLiteral | IsPredicate                    | Arithmetic Evaluation                                                                                             |
| **op**/3                 |          | GroundLiteral | Op3Predicate                   |                                                                                                                   |
| **put**/1                |          | GroundLiteral | PutPredicate                   |                                                                                                                   |
| **tag**/2                |          | GroundLiteral | TagPredicate                   |                                                                                                                   |
| **!**/0                  |          | GroundLiteral | CutAtom                        |                                                                                                                   |
| **fail**/0               |          | GroundLiteral | FailAtom                       |                                                                                                                   |
| **true**/0               |          | GroundLiteral | TrueAtom                       |                                                                                                                   |
| bar                      |          |               |                                |                                                                                                                   |
| bracket                  |          |               |                                |                                                                                                                   |
| conjunction              |          |               |                                |                                                                                                                   |
| list                     |          |               |                                |                                                                                                                   |
| simple                   |          |               |                                |                                                                                                                   |
| tuple                    |          |               |                                |                                                                                                                   |
| **abs**/1                |          | Function      | AbsFunction                    | abs(E) absolute value of E                                                                                        |
| **acos**/1               |          | Function      | ArcosineFunction               | acos(E) arcosine of E (in radians) in the range 0 to pi                                                           |
| **asin**/1               |          | Function      | ArcsineFunction                | asin(E) arcsine of E (in radians) in the range -pi/2 to pi/2                                                      |
| **atan**/1               |          | Function      | ArctangentFunction             | atan(E) arctangent of E (in radians) in the range -pi/2 to pi/2                                                   |
| **atan2**/1              |          | Function      | Arctangent2Function            | atan2(E1, E2) arctangent of E1/E2 (in radians) in the range -pi to pi                                             |
| **ceil**/1               |          | Function      | CeilFunction                   | ceil(E) round up to next higher integer                                                                           |
| **cos**1/                |          | Function      | CosFunction                    | cos(E) cosine of an angle E in radians                                                                            |
| **deg2rad**/1            |          | Function      | DegreesToRadiansFunction       | deg2rad(E) number of radians in E degrees                                                                         |
| **float**/1              |          | Function      | FloatFunction                  | float(E) E's value.                                                                                               |
| **floor**/1              |          | Function      | FloorFunction                  | floor(E) round down to next lower integer                                                                         |
| **--**/1                 |          | Function      | IntegerDifferenceFunction      | E1 -- E2 integer difference of integers E1 and E2                                                                 |
| **int**/1, **integer**/1 |          | Function      | IntegerFunction                | int(E) integer truncation of E towards 0<br>integer(E) the integer component (integer closest to 0) of E's value. |
| **/**/2                  |          | Function      | IntegerQuotientFunction        | E1 // E2 integer part of quotient of integers E1 and E2                                                           |
| **/2                     |          | Function      | IntegerProductFunction         | E1 ** E2 integer product of integers E1 and E2                                                                    |
| **++**/2                 |          | Function      | IntegerSumFunction             | E1 ++ E2 integer sum of integers E1 and E2                                                                        |
| **/\\**/2                |          | Function      | LogicalAndFunction             | E1 /\ E2 bitwise logical And of integers E1 and E2                                                                |
| **\\**/1                 |          | Function      | LogicalNegateFunction          | \E bitwise logical negation of integer E                                                                          |
| **\/**/2                 |          | Function      | LogicalOrFunction              | E1 \/ E2 bitwise logical Or of integers E1 and E2                                                                 |
| **mod**/1                |          | Function      | ModulusFunction                | E1 mod E2 remainder of the integer division of E1 and E2                                                          |
| **exp**/1                |          | Function      | NaturalExponentFunction        | exp(E) natural anti-logarithm of E                                                                                |
| **log**/1                |          | Function      | NaturalLogFunction             | log(E) natural logarithm of E                                                                                     |
| **-**/1                  |          | Function      | NegateFunction                 | -E negative of E                                                                                                  |
| **pow**/2, **^**/2       |          | Function      | PowerFunction                  | E1 ^ E2 E1 raised to the power E2                                                                                 |
| */2                      |          | Function      | ProductFunction                | E1 * E2 product of E1 and E2                                                                                      |
| **/**/2                  |          | Function      | QuotientFunction               | E1 / E2 quotient of E1 and E2                                                                                     |
| **rad2deg**/1            |          | Function      | RadiansToDegreesFunction       | rad2deg(E) number of degrees in E radians                                                                         |
| **rand**/0               |          | Function      | RandomFunction                 | rand random integer in the range 0 to 32767                                                                       |
| **<<**/2                 |          | Function      | ShiftLeftFunction              | E1 << E2 integer E1 shifted left by E2 bits                                                                       |
| **>>**/2                 |          | Function      | ShiftRightFunction             | E1 >> E2 integer E1 shifted right by E2 bits                                                                      |
| **sin**/1                |          | Function      | SinFunction                    | sin(E) sine of an angle E in radians                                                                              |
| **sqrt**/1               |          | Function      | SquareRootFunction             | sqrt(E) square root of E                                                                                          |
| **-**/2                  |          | Function      | SubtractFunction               | E1 - E2 difference of E1 and E2                                                                                   |
| **+**/2                  |          | Function      | SumFunction                    | E1 + E2 sum of E1 and E2                                                                                          |
| **tan**/1                |          | Function      | TangentFunction                | tan(E) tangent of an angle E in radians                                                                           |
| **e**/0                  |          | Constant      | EulersConstant                 | e value of 'e' (Euler's number) logarithmic constant.                                                             |
| **pi**/0                 |          | Constant      | PiConstant                     | pi value of pi trigonometric constant.                                                                            |
