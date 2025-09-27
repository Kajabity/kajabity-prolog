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
