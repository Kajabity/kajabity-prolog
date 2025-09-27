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
package com.kajabity.prolog.core.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A TermList is a list of terms (obviously) usually created by a comma (and)
 * separated list.
 *
 * @author Simon
 */
public final class TermList implements Expression
{
    /**
     * The actual terms are stored in a List. The first must be a Term, the rest
     * can be Terms or TermLists.
     */
    private final List<Expression> terms;

    /**
     * Convenience to construct a TermList from two Expressions. The second
     * parameter is deliberately vague to allow a TermList to contain a
     * TermList. This is useful for the implication operator ( term :-
     * list-of-terms ).
     *
     * @param term
     * @param expr
     */
    public TermList( final Expression expr1, final Expression expr2 )
    {
        terms = new ArrayList<Expression>( 2 );
        terms.add( expr1 );
        terms.add( expr2 );
    }

    /**
     * Convenience to construct a TermList from one Expressions.
     *
     * @param term
     */
    public TermList( final Expression expr )
    {
        terms = new ArrayList<Expression>( 1 );
        terms.add( expr );
    }

    /**
     * Construct from an array of Expressions - this makes manual construction
     * of termlists simpler.
     *
     * @param terms
     */
    public TermList( Expression[] terms )
    {
        this.terms = Arrays.asList( terms );
    }

    /**
     * Construct from a List of Expressions.
     *
     * @param terms
     */
    public TermList( final List<Expression> exprs )
    {
        this.terms = new ArrayList<Expression>( exprs );
    }

    /**
     * @see Expression#containsVariable(Variable)
     */
    public boolean containsVariable( final Variable variable )
    {
        Iterator<Expression> iTerms = terms.iterator();

        while( iTerms.hasNext() )
        {
            if( iTerms.next().containsVariable( variable ) )
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Return the Term which is the Head of this term list.
     *
     * @return
     */
    public Expression getHead()
    {
        if( terms.isEmpty() )
        {
            return null;
        }

        return terms.get( 0 );
    }

    public TermList getTail()
    {
        if( terms.size() < 2 )
        {
            return null;
        }

        return new TermList( terms.subList( 1, terms.size() - 1 ) );
    }

    /**
     * @see Expression#append(Expression)
     */
    public TermList append( final Expression expr )
    {
        terms.add( expr );

        return this;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public int size()
    {
        return terms.size();
    }

    /**
     * @see Expression#makeCopy(Map)
     */
    public Expression makeCopy( Map<String, Variable> variables )
    {
        List<Expression> copyTerms = new ArrayList<Expression>( terms.size() );
        Iterator<Expression> iTerms = terms.iterator();

        while( iTerms.hasNext() )
        {
            copyTerms.add( iTerms.next().makeCopy( variables ) );
        }

        return new TermList( copyTerms );
    }

    /**
     * Two TermLists are equal if their contained terms are equal.
     *
     * @see Object#equals(Object)
     */
    public boolean equals( Object obj )
    {
        if( obj instanceof TermList )
        {
            return terms.equals( ((TermList) obj).terms );
        }

        return false;
    }

    /**
     * @see Object#toString()
     */
    public String toString()
    {
        StringBuffer buf = new StringBuffer();

        Iterator<Expression> iTerms = terms.iterator();

        while( iTerms.hasNext() )
        {
            Expression term = iTerms.next();
            if( term.isTerm() )
            {
                buf.append( term );
            }
            else
            {
                buf.append( "(" );
                buf.append( term );
                buf.append( ")" );
            }

            if( iTerms.hasNext() )
            {
                buf.append( ", " );
            }
        }

        return buf.toString();
    }

    /**
     * @see Expression#getFinalInstantiation()
     */
    public Expression getFinalInstantiation()
    {
        return this;
    }

    /**
     * @see Expression#isTerm()
     */
    public boolean isTerm()
    {
        return false;
    }

    /**
     * @return Returns the terms.
     */
    public List<Expression> getTerms()
    {
        return Collections.unmodifiableList( terms );
    }

    /**
     * @author Simon To change the template for this generated type comment go
     *         to Window - Preferences - Java - Code Generation - Code and
     *         Comments
     */
    public static TermList concatenate( Expression left, Expression right )
    {
        List<Expression> terms = new ArrayList<Expression>();
        if( left.isTerm() )
        {
            terms.add( left );
        }
        else
        {
            terms.addAll( ((TermList) left).terms );
        }

        if( right.isTerm() )
        {
            terms.add( right );
        }
        else
        {
            terms.addAll( ((TermList) right).terms );
        }

        return new TermList( terms );
    }
}
