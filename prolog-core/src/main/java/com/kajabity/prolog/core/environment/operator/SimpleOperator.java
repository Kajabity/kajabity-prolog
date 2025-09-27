/*
 * Copyright (c) 2004-2025 Simon J. Williams
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
package com.kajabity.prolog.core.environment.operator;

import java.util.Stack;

import com.kajabity.prolog.core.environment.Associativity;
import com.kajabity.prolog.core.expression.Atom;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.TermList;
import com.kajabity.prolog.core.expression.Tuple;




/**
 * DOCUMENT ME!
 *
 * @author Simon To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SimpleOperator extends Operator
{
    /**
     * DOCUMENT ME!
     *
     * @param name
     * @param associativity
     * @param precedence
     */
    public SimpleOperator( String name, Associativity associativity,
            int precedence )
    {
        super( name, associativity, precedence );
    }


    /**
     *
     * @param values
     * @return
     * @see Operator#apply(Stack)
     */
    public Expression apply( Stack<Expression> values )
    {
        TermList args;

        if( getAssociativity().isInfix() )
        {
            Expression right = (Expression) values.pop();
            Expression left = (Expression) values.pop();

            //args = left.append( right );
            args = new TermList( left, right );
        }
        else
        {
            args = new TermList( (Expression) values.pop() );
        }

        return new Tuple( Atom.find( getName() ), args );
    }
}
