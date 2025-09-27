/*
 * Copyright (c) 2003-2025 Simon J. Williams
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
package com.kajabity.prolog.io.predicate;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.kajabity.prolog.core.arithmetic.ArithmeticExpression;
import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Associativity;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.environment.operator.SimpleOperator;
import com.kajabity.prolog.core.expression.Atom;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.NumericConstant;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.core.expression.Variable;


/**
 * Define an operator.
 *
 * @author Simon J. Williams
 */
public class Op3PredicateTemp extends GroundLiteral
{
    private final static Logger logger       = Logger
            .getLogger( Op3PredicateTemp.class );

    private final static String KEY          = "Associativity";

    private final static int    MAX_PRIORITY = 1200;

    private final static int    MIN_PRIORITY = 1;


    /**
     * Constructor for Op3Predicate.
     *
     * @param database
     */
    public Op3PredicateTemp( Database database, String name, int arity )
    {
        super( database, name, arity );
        assert arity == 3;

        Atom atom;

        atom = Atom.find( "fx" );
        atom.setProperty( KEY, Associativity.fx );

        atom = Atom.find( "fy" );
        atom.setProperty( KEY, Associativity.fy );

        atom = Atom.find( "xfx" );
        atom.setProperty( KEY, Associativity.xfx );

        atom = Atom.find( "xfy" );
        atom.setProperty( KEY, Associativity.xfy );

        atom = Atom.find( "yfx" );
        atom.setProperty( KEY, Associativity.yfx );

        atom = Atom.find( "xf" );
        atom
                .setProperty(
                        KEY,
                        com.kajabity.prolog.core.environment.Associativity.xf );

        atom = Atom.find( "yf" );
        atom.setProperty( KEY, Associativity.yf );
    }


    /**
     * Op/3 parameters: -
     * <li>Priority (IntegerNumber)
     * <li>Associativity (Atom)
     * <li>Name (atom)
     *
     * @param goal
     * @return List of substitutions - or null for a fail. In this op it will be
     *         an empty list if the operator is added ok or null if anything is
     *         wrong.
     * @throws PrologException
     * @see GroundLiteral#execute(Goal)
     */
    protected List<Variable> execute( Goal goal ) throws PrologException
    {
        //  the goal should contain a Tuple (op/3).
        if( goal.getTerm() instanceof Tuple )
        {
            Tuple tuple = (Tuple) goal.getTerm();
            if( tuple.getArity() == 3 )
            {
                List<Expression> args = tuple.getArgs().getTerms();

                Expression p1 = args.get( 0 );
                Expression p2 = args.get( 1 );
                Expression p3 = args.get( 2 );

                ArithmeticExpression ae = new ArithmeticExpression( p1 );
                NumericConstant result = ae.eval( database );

                int priority = (int) result.longValue();
                if( priority < MIN_PRIORITY || priority > MAX_PRIORITY )
                {
                    priority = 0;
                }

                Associativity ass = null;
                if( p2 instanceof Atom )
                {
                    ass = (Associativity) ((Atom) p2).getProperty( KEY );
                }

                if( ass == null )
                {
                    throw new PrologException(
                            getKey()
                                    + ": Second parameter must specify associativity - "
                                    + goal.getTerm() );
                }

                if( p3 instanceof Atom )
                {
                    Atom opName = (Atom) p3;
                    SimpleOperator op = new SimpleOperator( opName.getName(),
                            ass, priority );
                    database.add( op );

                    logger.debug( getKey() + ": Added operator " + op );

                    return Collections.emptyList();
                }

                throw new PrologException( getKey()
                        + ": Third parameter must be the operator name - "
                        + goal.getTerm() );
            }

            throw new PrologException( getKey()
                    + ": Goal should have 3 parameters - " + goal.getTerm() );
        }

        throw new PrologException( getKey() + ": Goal should be a Tuple - "
                + goal.getTerm() );
    }
}
