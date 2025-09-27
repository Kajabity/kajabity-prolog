/*
 *******************************************************************************
 *  Copyright   :   (c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_Environment
 *
 *  Created     :   May 2, 2004 by Simon J. Williams
 *
 *  $Header: /home/cvs/cvs/Prolog/Environment/src/uk/co/williams_technologies/prolog/environment/SimpleOperator.java,v 1.2 2004/07/01 05:31:52 simon Exp $
 *******************************************************************************
 *  $Log: SimpleOperator.java,v $
 *  Revision 1.2  2004/07/01 05:31:52  simon
 *  Major update after many changes.
 *
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *******************************************************************************
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
