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

/**
 *
 */
package com.kajabity.prolog.io.predicate;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import com.kajabity.prolog.core.arithmetic.Function;
import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.expression.Atom;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.NumericConstant;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.core.expression.Variable;
import com.kajabity.utils.token.TokenException;

/**
 * A predicate to "assert" a Java predicate (known as a {@link GroundLiteral}).
 *
 * @author Simon J. Williams
 */
public class AssertFunctionPredicate extends GroundLiteral
{

    public AssertFunctionPredicate( Database database, String name, int arity )
    {
        super( database, name, arity );
        assert arity == 2;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.kajabity.prolog.core.environment.GroundLiteral#execute(com.kajabity.prolog.core.engine.Goal)
     */
    @Override
    protected List<Variable> execute( Goal goal ) throws TokenException, IOException, PrologException
    {
        // the goal should contain a Tuple (assertg/2).
        if( goal.getTerm() instanceof Tuple )
        {
            Tuple params = (Tuple) goal.getTerm();
            if( params.getArity() == 2 )
            {
                List<?> args = params.getArgs().getTerms();

                Expression p1 = (Expression) args.get( 0 );
                Expression p2 = (Expression) args.get( 1 );

                if( p1 instanceof Tuple )
                {
                    List<?> specArgs = ((Tuple) p1).getArgs().getTerms();

                    Expression p3 = (Expression) specArgs.get( 0 );
                    Expression p4 = (Expression) specArgs.get( 1 );

                    if( p3 instanceof Atom && p4 instanceof NumericConstant && p2 instanceof Atom )
                    {
                        try
                        {
                            Class<?> predicateClass = (Class<?>) Class.forName( ((Atom) p2).getName() );

                            Constructor<?> predicateConstructor = (Constructor<?>) predicateClass.getConstructor( /*Database.class,*/ String.class,
                                    int.class );

                            Function groundLiteral = (Function) predicateConstructor.newInstance( /*database,*/ ((Atom) p3)
                                    .getName(), (int) ((NumericConstant) p4).doubleValue() );

                            database.add( groundLiteral );

                            return Collections.emptyList();
                        }
                        catch( ClassNotFoundException e )
                        {
                            throw new PrologException( getKey() + ": predicate class does not exist " + p2 );
                        }
                        catch( SecurityException e )
                        {
                            throw new PrologException( getKey() + ": predicate class is not accessible " + p2 );
                        }
                        catch( NoSuchMethodException e )
                        {
                            throw new PrologException( getKey() + ": predicate class does not have constructor " + p2 );
                        }
                        catch( IllegalArgumentException e )
                        {
                            throw new PrologException( getKey() + ": predicate class IllegalArgumentException " + p2 );
                        }
                        catch( InstantiationException e )
                        {
                            throw new PrologException( getKey() + ": predicate class InstantiationException " + p2 );
                        }
                        catch( IllegalAccessException e )
                        {
                            throw new PrologException( getKey() + ": predicate class IllegalAccessException " + p2 );
                        }
                        catch( InvocationTargetException e )
                        {
                            throw new PrologException( getKey() + ": predicate class InvocationTargetException " + p2 );
                        }
                    }

                    throw new PrologException( getKey() + ": First param should be a Tuple - " + goal.getTerm() );
                }

                throw new PrologException( getKey() + ": invalid parameters " + goal.getTerm() );
            }

            throw new PrologException( getKey() + ": Goal should have 2 parameters - " + goal.getTerm() );
        }

        throw new PrologException( getKey() + ": Goal should be a Tuple - " + goal.getTerm() );
    }
}
