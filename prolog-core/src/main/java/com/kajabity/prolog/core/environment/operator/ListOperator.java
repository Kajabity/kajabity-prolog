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

/*
 * Created on Jul 19, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.kajabity.prolog.core.environment.operator;

import java.util.Stack;

import com.kajabity.prolog.core.environment.Associativity;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Expressions;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.TermList;
import com.kajabity.prolog.core.expression.Tuple;





public class ListOperator extends Operator
{
    public ListOperator( String name, Associativity associativity,
            int precedence )
    {
        super( name, associativity, precedence );
    }


    public Expression apply( Stack<Expression> values ) throws PrologOperatorException
    {
        //  One or more terms on top of stack - may be a Term, a TermList or
        // a Bar.
        Expression expr = values.pop();
        Stack<Expression> stack = new Stack<Expression>();
        if( expr.isTerm() )
        {
            stack.push( expr );
        }
        else
        {
            stack.addAll( ((TermList) expr).getTerms() );
        }

        Term LIST = Expressions.NIL;
        while( !stack.isEmpty() )
        {
            Expression top = stack.pop();
            if( !top.isTerm() )
                throw new PrologOperatorException(
                        "List contents should be Terms" );
            Term term = (Term) top;
            if( LIST == Expressions.NIL && term.getName().equals( Expressions.NIL.getName() ) )
            {
                LIST = (Term) term;
            }
            else
            {
                LIST = new Tuple( Expressions.NIL, new TermList( term, LIST ) );
            }
        }
        return LIST;
    }
}
