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

package com.kajabity.prolog.builtin.predicate;

import java.util.Collections;
import java.util.List;

import com.kajabity.prolog.core.arithmetic.ArithmeticExpression;
import com.kajabity.prolog.core.arithmetic.PrologArithmeticException;
import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.NumericConstant;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.core.expression.Variable;


/**
 * @author Simon J. Williams
 */
public class ArithLessThanPredicate extends GroundLiteral
{
    public ArithLessThanPredicate( Database database )
    {
        this( database, "<", 2 );
    }

    public ArithLessThanPredicate( Database database, String name, int arity )
    {
        super( database, name, arity );
        assert arity == 2;
    }


    /*
     * (non-Javadoc)
     *
     * @see com.kajabity.prolog.core.environment.GroundLiteral#execute(com.kajabity.prolog.core.environment.PrologDatabase,
     *      com.kajabity.prolog.io.solver.Goal)
     */
    protected List<Variable> execute( Goal goal ) throws PrologArithmeticException
    {
        if( goal.getTerm() instanceof Tuple )
        {
            Tuple tuple = (Tuple) goal.getTerm();
            List<Expression> args = tuple.getArgs().getTerms();

            Expression p1 = args.get( 0 );
            Expression p2 = args.get( 1 );

            ArithmeticExpression ae = new ArithmeticExpression( p1 );
            NumericConstant left = ae.eval( database );

            ae = new ArithmeticExpression( p2 );
            NumericConstant right = ae.eval( database );

            if( left.isReal() || right.isReal() )
            {
                if( left.doubleValue() < right.doubleValue() )
                {
                    return Collections.emptyList();
                }
            }

            if( left.longValue() < right.longValue() )
            {
                return Collections.emptyList();
            }
        }

        return null;
    }
}
