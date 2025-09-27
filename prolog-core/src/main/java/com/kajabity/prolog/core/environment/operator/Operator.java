/*
 *******************************************************************************
 *  Copyright   :   (c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_Environment
 *
 *  Created     :   Mar 24, 2004 by Simon J. Williams
 *
 *  $Header: /home/cvs/cvs/Prolog/Environment/src/uk/co/williams_technologies/prolog/environment/Operator.java,v 1.2 2004/07/01 05:31:52 simon Exp $
 *******************************************************************************
 *  $Log: Operator.java,v $
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
import com.kajabity.prolog.core.expression.Expression;




/**
 * DOCUMENT ME!
 *
 * @author Simon To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public abstract class Operator implements Comparable<Operator>
{
    private String        name;

    private Associativity associativity;

    private int           precedence;


    public Operator( String name, Associativity associativity, int precedence )
    {
        this.name = name;
        this.associativity = associativity;
        this.precedence = precedence;
    }


    /**
     * Helper method which converts an operator to a Tuple.
     *
     * @return DOCUMENT ME!
     */
    public abstract Expression apply( Stack<Expression> values )
            throws PrologOperatorException;


    /**
     * Two Operators are considered equal if they have the same name and
     * position. This is a useful property for storing in the operator map in
     * the database.
     *
     * @param obj the object being compared - should be an instance of an
     *            Operator.
     * @return true if the objects have the same key(name and position).
     * @see Object#equals(Object)
     */
    public boolean equals( Object obj )
    {
        return obj instanceof Operator
                && name.equals( ((Operator) obj).name )
                && associativity.getDirection().equals(
                        ((Operator) obj).associativity.getDirection() );
    }


    /**
     * return a hashCode based on the name and position to complement the equals
     * and ease the use in maps.
     *
     * @see Object#hashCode()
     */
    public int hashCode()
    {
        return name.hashCode() + associativity.getDirection().hashCode();
    }


    /**
     * Generate a string defining this Operator instance.
     *
     * @return a string representing this instance of an operator.
     */
    public String toString()
    {
        return "op( " + getPrecedence() + ", "
                + getAssociativity() + ", " + getName() + " )";
    }


    /**
     * Compare with another Operator using precedence and direction.
     *
     * @see Comparable#compareTo(Object)
     */
    public int compareTo( Operator other )
    {
        if( precedence > other.precedence )
            return 1;

        if( precedence < other.precedence )
            return -1;

        return -associativity.getDirection().compareTo(
                other.associativity.getDirection() );
    }


    /**
     * DOCUMENT ME!
     *
     * @return Returns the associativity.
     */
    public Associativity getAssociativity()
    {
        return associativity;
    }


    /**
     * DOCUMENT ME!
     *
     * @param associativity The associativity to set.
     * @throws PrologOperatorException DOCUMENT ME!
     */
    public void setAssociativity( Associativity associativity )
            throws PrologOperatorException
    {
        if( this.associativity.getPosition() != associativity.getPosition() )
        {
            throw new PrologOperatorException(
                    "Attempt to change operator position from "
                            + this.associativity.getPosition() + " to "
                            + associativity.getPosition() );
        }

        this.associativity = associativity;
    }


    /**
     * DOCUMENT ME!
     *
     * @return Returns the precedence.
     */
    public int getPrecedence()
    {
        return precedence;
    }


    /**
     * DOCUMENT ME!
     *
     * @param precedence The precedence to set.
     */
    public void setPrecedence( int precedence )
    {
        //TODO: Check the range 1..1200
        this.precedence = precedence;
    }


    /**
     * DOCUMENT ME!
     *
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }
}
