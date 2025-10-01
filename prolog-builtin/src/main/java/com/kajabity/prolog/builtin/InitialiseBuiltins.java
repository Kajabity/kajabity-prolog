/*
 * Copyright (c) 2025 Simon J. Williams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */

package com.kajabity.prolog.builtin;

import com.kajabity.prolog.builtin.arithmetic.*;
import com.kajabity.prolog.builtin.operator.SimpleOperator;
import com.kajabity.prolog.builtin.predicate.*;
import com.kajabity.prolog.builtin.processor.AssertProcessor;
import com.kajabity.prolog.builtin.processor.FactProcessor;
import com.kajabity.prolog.builtin.processor.QuitProcessor;
import com.kajabity.prolog.core.environment.Associativity;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.PrologException;

public class InitialiseBuiltins {
    public static void initialise(Database database) throws PrologException {
        initialiseProcessors(database);
        initialiseBasicPredicates(database);
        initialiseArithmeticComparisonPredicates(database);
        initialiseArithmeticBuiltins(database);
    }

    private static void initialiseProcessors(Database database) throws PrologException {
        database.add(new QuitProcessor("quit"));

        database.setDefaultProcessor(new FactProcessor(""));

        database.add(new AssertProcessor(":-"));
        database.add(new SimpleOperator(":-", Associativity.xfx, 1200));
    }

    private static void initialiseBasicPredicates(Database database) {
        database.add(new IsPredicate(database, "is", 2));
        database.add(new Op3Predicate(database, "op", 3));
        database.add(new CutAtom(database, "!", 0));
        database.add(new TrueAtom(database, "true", 0));
        database.add(new FailAtom(database, "fail", 0));
        database.add(new TagPredicate(database, "tag", 2));

    }

    private static void initialiseArithmeticComparisonPredicates(Database database) {
        database.add(new EqualSamePredicate(database, "==", 2));
        database.add(new ArithEqualPredicate(database, "=:=", 2));
        database.add(new ArithNotEqualPredicate(database, "=\\=", 2));
        database.add(new ArithLessThanPredicate(database, "<", 2));
        database.add(new ArithLessThanEqualPredicate(database, "=<", 2));
        database.add(new ArithGreaterThanPredicate(database, ">", 2));
        database.add(new ArithGreaterThanEqualPredicate(database, ">=", 2));
    }

    private static void initialiseArithmeticBuiltins(Database database) throws PrologException {
        //	sinh(E) hyperbolic sine of an angle E in radians
        //	cosh(E) hyperbolic cosine of an angle E in radians
        //	tanh(E) hyperbolic tangent of an angle E in radians
        //	log10(E) logarithm (base 10) of E
        //	sign(E) sign of E: -1 if negative, 1 if positive and 0 if 0

        //	-E negative of E
        database.add(new NegateFunction("-", 1));

        //	E1 + E2 sum of E1 and E2
        database.add(new SumFunction("+", 2));

        //	E1 ++ E2 integer sum of integers E1 and E2
        database.add(new IntegerSumFunction("++", 2));

        //	E1 - E2 difference of E1 and E2
        database.add(new SubtractFunction("-", 2));

        //	E1 -- E2 integer difference of integers E1 and E2
        database.add(new IntegerDifferenceFunction("--", 2));

        //	E1 * E2 product of E1 and E2
        database.add(new ProductFunction("*", 2));

        //	E1 ** E2 integer product of integers E1 and E2
        database.add(new IntegerProductFunction("**", 2));

        //	E1 / E2 quotient of E1 and E2
        database.add(new QuotientFunction("/", 2));

        //	E1 // E2 integer part of quotient of integers E1 and E2
        database.add(new IntegerQuotientFunction("//", 2));

        //	E1 div E2 integer part of quotient of integers E1 and E2
        database.add(new IntegerQuotientFunction("div", 2));

        //	E1 mod E2 remainder of the integer division of E1 and E2
        database.add(new ModulusFunction("mod", 2));

        //	E1 ^ E2 E1 raised to the power E2
        database.add(new PowerFunction("^", 2));

        //	pow(E1, E2) E1 raised to the power E2
        database.add(new PowerFunction("pow", 2));

        //	E1 << E2 integer E1 shifted left by E2 bits
        database.add(new ShiftLeftFunction("<<", 2));

        //	E1 >> E2 integer E1 shifted right by E2 bits
        database.add(new ShiftRightFunction(">>", 2));

        //	\E bitwise logical negation of integer E
        database.add(new LogicalNegateFunction("\\", 1));

        //	E1 /\ E2 bitwise logical And of integers E1 and E2
        database.add(new LogicalAndFunction("/\\", 2));

        //	E1 \/ E2 bitwise logical Or of integers E1 and E2
        database.add(new LogicalOrFunction("\\/", 2));

        //	sin(E) sine of an angle E in radians
        database.add(new SinFunction("sin", 1));

        //	cos(E) cosine of an angle E in radians
        database.add(new CosFunction("cos", 1));

        //	tan(E) tangent of an angle E in radians
        database.add(new TangentFunction("tan", 1));

        //	asin(E) arcsine of E (in radians) in the range -pi/2 to pi/2
        database.add(new ArcsineFunction("asin", 1));

        //	acos(E) arcosine of E (in radians) in the range 0 to pi
        database.add(new ArcosineFunction("acos", 1));

        //	atan(E) arctangent of E (in radians) in the range -pi/2 to pi/2
        database.add(new ArctangentFunction("atan", 1));

        //	atan2(E1, E2) arctangent of E1/E2 (in radians) in the range -pi to
        // pi
        database.add(new Arctangent2Function("atan2", 2));

        //	exp(E) natural anti-logarithm of E
        database.add(new NaturalExponentFunction("exp", 1));

        //	log(E) natural logarithm of E
        database.add(new NaturalLogFunction("log", 1));

        //	sqrt(E) square root of E
        database.add(new SquareRootFunction("sqrt", 1));

        //	deg2rad(E) number of radians in E degrees
        database.add(new DegreesToRadiansFunction("deg2rad", 1));

        //	rad2deg(E) number of degrees in E radians
        database.add(new RadiansToDegreesFunction("rad2deg", 1));

        //	float(E) E's value.
        database.add(new FloatFunction("float", 1));

        //	int(E) integer truncation of E towards 0
        database.add(new IntegerFunction("int", 1));

        //	integer(E) the integer component (integer closest to 0) of E's
        // value.
        database.add(new IntegerFunction("integer", 1));

        //	abs(E) absolute value of E
        database.add(new AbsFunction("abs", 1));

        //	ceil(E) round up to next higher integer
        database.add(new CeilFunction("ceil", 1));

        //	floor(E) round down to next lower integer
        database.add(new FloorFunction("floor", 1));

        //	pi value of pi trigonometric constant.
        database.add(new PiConstant("pi", 0));

        //	e value of 'e' (Euler's number) logarithmic constant.
        database.add(new EulersConstant("e", 0));

        //	rand random integer in the range 0 to 32767
        database.add(new RandomFunction("rand", 0));
    }

}
