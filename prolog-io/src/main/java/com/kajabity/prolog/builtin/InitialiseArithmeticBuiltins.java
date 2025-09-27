/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 * Created on 16-Mar-2003
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/InitialiseArithmeticBuiltins.java,v 1.2 2004/05/05 05:28:38 simon Exp $
 *******************************************************************************
 *  $Log: InitialiseArithmeticBuiltins.java,v $
 *  Revision 1.2  2004/05/05 05:28:38  simon
 *  Lots of changes...
 *
 *  Revision 1.1  2004/02/17 08:35:44  simon
 *  Refactoring and adding Processor classes.
 *
 *  Revision 1.6  2004/01/29 06:33:15  simon
 *  Refactored arithmetic functions (and renamed) to non-anonymous classes
 *  to allow for XML configuration
 *
 *  Revision 1.5  2004/01/25 05:48:56  simon
 *  Some refactoring - mainly, implemented majority of arithmetic functions.
 *
 *  Revision 1.4  2004/01/15 07:07:36  simon
 *  Adding support for arithmetic.
 *
 *  Revision 1.3  2003/11/20 05:14:28  simon
 *  Changes to implement write( Term ) built-in - added PrologOutputStream,
 *  also refactored to src/* folders.
 *
 *  Revision 1.2  2003/06/28 05:38:35  simon
 *  UPdate during adding of 'op' builtin.
 *
 *******************************************************************************
 */
package com.kajabity.prolog.builtin;

import com.kajabity.prolog.builtin.arithmetic.AbsFunction;
import com.kajabity.prolog.builtin.arithmetic.ArcosineFunction;
import com.kajabity.prolog.builtin.arithmetic.ArcsineFunction;
import com.kajabity.prolog.builtin.arithmetic.Arctangent2Function;
import com.kajabity.prolog.builtin.arithmetic.ArctangentFunction;
import com.kajabity.prolog.builtin.arithmetic.CeilFunction;
import com.kajabity.prolog.builtin.arithmetic.CosFunction;
import com.kajabity.prolog.builtin.arithmetic.DegreesToRadiansFunction;
import com.kajabity.prolog.builtin.arithmetic.EulersConstant;
import com.kajabity.prolog.builtin.arithmetic.FloatFunction;
import com.kajabity.prolog.builtin.arithmetic.FloorFunction;
import com.kajabity.prolog.builtin.arithmetic.IntegerDifferenceFunction;
import com.kajabity.prolog.builtin.arithmetic.IntegerFunction;
import com.kajabity.prolog.builtin.arithmetic.IntegerProductFunction;
import com.kajabity.prolog.builtin.arithmetic.IntegerQuotientFunction;
import com.kajabity.prolog.builtin.arithmetic.IntegerSumFunction;
import com.kajabity.prolog.builtin.arithmetic.LogicalAndFunction;
import com.kajabity.prolog.builtin.arithmetic.LogicalNegateFunction;
import com.kajabity.prolog.builtin.arithmetic.LogicalOrFunction;
import com.kajabity.prolog.builtin.arithmetic.ModulusFunction;
import com.kajabity.prolog.builtin.arithmetic.NaturalExponentFunction;
import com.kajabity.prolog.builtin.arithmetic.NaturalLogFunction;
import com.kajabity.prolog.builtin.arithmetic.NegateFunction;
import com.kajabity.prolog.builtin.arithmetic.PiConstant;
import com.kajabity.prolog.builtin.arithmetic.PowerFunction;
import com.kajabity.prolog.builtin.arithmetic.ProductFunction;
import com.kajabity.prolog.builtin.arithmetic.QuotientFunction;
import com.kajabity.prolog.builtin.arithmetic.RadiansToDegreesFunction;
import com.kajabity.prolog.builtin.arithmetic.RandomFunction;
import com.kajabity.prolog.builtin.arithmetic.ShiftLeftFunction;
import com.kajabity.prolog.builtin.arithmetic.ShiftRightFunction;
import com.kajabity.prolog.builtin.arithmetic.SinFunction;
import com.kajabity.prolog.builtin.arithmetic.SquareRootFunction;
import com.kajabity.prolog.builtin.arithmetic.SubtractFunction;
import com.kajabity.prolog.builtin.arithmetic.SumFunction;
import com.kajabity.prolog.builtin.arithmetic.TangentFunction;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.PrologException;


/**
 * Arithmetic Initialisation - adds the following features. Predicate : -X is +Y
 * Description : X is the result of evaluating arithmetic expression Y. An
 * arithmetic expression is either an number, or a variable which is bound to a
 * number, or a singleton list whose head is an integer (in this case the value
 * of the expression is the integer itself), or a single character in double
 * quotes (in this case the value of the expression is the ASCII code of the
 * character), or a compound expression. A compound expression is a tuple whose
 * functor is an arithmetic operator and whose arguments are arithmetic
 * expressions. A description of the allowed compound expressions, together
 * with the result of their evaluation follows: -
 * <ul>
 * <li>-E negative of E
 * <li>E1 + E2 sum of E1 and E2
 * <li>E1 ++ E2 integer sum of integers E1 and E2
 * <li>E1 - E2 difference of E1 and E2
 * <li>E1 -- E2 integer difference of integers E1 and E2
 * <li>E1 * E2 product of E1 and E2
 * <li>E1 ** E2 integer product of integers E1 and E2
 * <li>E1 / E2 quotient of E1 and E2
 * <li>E1 // E2 integer part of quotient of integers E1 and E2
 * <li>E1 div E2 integer part of quotient of integers E1 and E2
 * <li>E1 mod E2 remainder of the integer division of E1 and E2
 * <li>E1 ^ E2 E1 raised to the power E2
 * <li>pow(E1, E2) E1 raised to the power E2
 * <li>E1 < < E2 integer E1 shifted left by E2 bits
 * <li>E1 >> E2 integer E1 shifted right by E2 bits
 * <li>\E bitwise logical negation of integer E
 * <li>E1 /\ E2 bitwise logical And of integers E1 and E2
 * <li>E1 \/ E2 bitwise logical Or of integers E1 and E2
 * <li>sin(E) sine of an angle E in radians
 * <li>cos(E) cosine of an angle E in radians
 * <li>tan(E) tangent of an angle E in radians
 * <li>sin(E) arcsine of E (in radians) in the range -pi/2 to pi/2
 * <li>acos(E) arcosine of E (in radians) in the range 0 to pi
 * <li>atan(E) arctangent of E (in radians) in the range -pi/2 to pi/2
 * <li>atan2(E1, E2) arctangent of E1/E2 (in radians) in the range -pi to pi
 * <li>exp(E) natural anti-logarithm of E
 * <li>log(E) natural logarithm of E
 * <li>sqrt(E) square root of E
 * <li>deg2rad(E) number of radians in E degrees
 * <li>rad2deg(E) number of degrees in E radians
 * <li>float(E) E's value.
 * <li>int(E) integer truncation of E towards 0
 * <li>integer(E) the integer component (integer closest to 0) of E's value.
 * <li>abs(E) absolute value of E
 * <li>ceil(E) round up to next higher integer
 * <li>floor(E) round down to next lower integer
 * <li>pi value of pi trigonometric constant.
 * <li>e value of 'e' (Euler's number) logarithmic constant.
 * <li>rand random integer in the range 0 to 32767
 * </ul>
 */
public class InitialiseArithmeticBuiltins
{
    public static void initialise( Database database ) throws PrologException
    {
        //	sinh(E) hyperbolic sine of an angle E in radians
        //	cosh(E) hyperbolic cosine of an angle E in radians
        //	tanh(E) hyperbolic tangent of an angle E in radians
        //	log10(E) logarithm (base 10) of E
        //	sign(E) sign of E: -1 if negative, 1 if positive and 0 if 0

        //	-E negative of E
        database.add( new NegateFunction( "-", 1 ) );

        //	E1 + E2 sum of E1 and E2
        database.add( new SumFunction( "+", 2 ) );

        //	E1 ++ E2 integer sum of integers E1 and E2
        database.add( new IntegerSumFunction( "++", 2 ) );

        //	E1 - E2 difference of E1 and E2
        database.add( new SubtractFunction( "-", 2 ) );

        //	E1 -- E2 integer difference of integers E1 and E2
        database.add( new IntegerDifferenceFunction( "--", 2 ) );

        //	E1 * E2 product of E1 and E2
        database.add( new ProductFunction( "*", 2 ) );

        //	E1 ** E2 integer product of integers E1 and E2
        database.add( new IntegerProductFunction( "**", 2 ) );

        //	E1 / E2 quotient of E1 and E2
        database.add( new QuotientFunction( "/", 2 ) );

        //	E1 // E2 integer part of quotient of integers E1 and E2
        database.add( new IntegerQuotientFunction( "//", 2 ) );

        //	E1 div E2 integer part of quotient of integers E1 and E2
        database.add( new IntegerQuotientFunction( "div", 2 ) );

        //	E1 mod E2 remainder of the integer division of E1 and E2
        database.add( new ModulusFunction( "mod", 2 ) );

        //	E1 ^ E2 E1 raised to the power E2
        database.add( new PowerFunction( "^", 2 ) );

        //	pow(E1, E2) E1 raised to the power E2
        database.add( new PowerFunction( "pow", 2 ) );

        //	E1 << E2 integer E1 shifted left by E2 bits
        database.add( new ShiftLeftFunction( "<<", 2 ) );

        //	E1 >> E2 integer E1 shifted right by E2 bits
        database.add( new ShiftRightFunction( ">>", 2 ) );

        //	\E bitwise logical negation of integer E
        database.add( new LogicalNegateFunction( "\\", 1 ) );

        //	E1 /\ E2 bitwise logical And of integers E1 and E2
        database.add( new LogicalAndFunction( "/\\", 2 ) );

        //	E1 \/ E2 bitwise logical Or of integers E1 and E2
        database.add( new LogicalOrFunction( "\\/", 2 ) );

        //	sin(E) sine of an angle E in radians
        database.add( new SinFunction( "sin", 1 ) );

        //	cos(E) cosine of an angle E in radians
        database.add( new CosFunction( "cos", 1 ) );

        //	tan(E) tangent of an angle E in radians
        database.add( new TangentFunction( "tan", 1 ) );

        //	asin(E) arcsine of E (in radians) in the range -pi/2 to pi/2
        database.add( new ArcsineFunction( "asin", 1 ) );

        //	acos(E) arcosine of E (in radians) in the range 0 to pi
        database.add( new ArcosineFunction( "acos", 1 ) );

        //	atan(E) arctangent of E (in radians) in the range -pi/2 to pi/2
        database.add( new ArctangentFunction( "atan", 1 ) );

        //	atan2(E1, E2) arctangent of E1/E2 (in radians) in the range -pi to
        // pi
        database.add( new Arctangent2Function( "atan2", 2 ) );

        //	exp(E) natural anti-logarithm of E
        database.add( new NaturalExponentFunction( "exp", 1 ) );

        //	log(E) natural logarithm of E
        database.add( new NaturalLogFunction( "log", 1 ) );

        //	sqrt(E) square root of E
        database.add( new SquareRootFunction( "sqrt", 1 ) );

        //	deg2rad(E) number of radians in E degrees
        database.add( new DegreesToRadiansFunction( "deg2rad", 1 ) );

        //	rad2deg(E) number of degrees in E radians
        database.add( new RadiansToDegreesFunction( "rad2deg", 1 ) );

        //	float(E) E's value.
        database.add( new FloatFunction( "float", 1 ) );

        //	int(E) integer truncation of E towards 0
        database.add( new IntegerFunction( "int", 1 ) );

        //	integer(E) the integer component (integer closest to 0) of E's
        // value.
        database.add( new IntegerFunction( "integer", 1 ) );

        //	abs(E) absolute value of E
        database.add( new AbsFunction( "abs", 1 ) );

        //	ceil(E) round up to next higher integer
        database.add( new CeilFunction( "ceil", 1 ) );

        //	floor(E) round down to next lower integer
        database.add( new FloorFunction( "floor", 1 ) );

        //	pi value of pi trigonometric constant.
        database.add( new PiConstant( "pi", 0 ) );

        //	e value of 'e' (Euler's number) logarithmic constant.
        database.add( new EulersConstant( "e", 0 ) );

        //	rand random integer in the range 0 to 32767
        database.add( new RandomFunction( "rand", 0 ) );
    }
}
