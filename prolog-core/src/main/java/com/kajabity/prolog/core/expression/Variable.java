/*
 *******************************************************************************
 *  Copyright   :   (c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_Term
 *
 *  Created     :   Mar 16, 2004 by Simon
 *
 *  $Header: /home/cvs/cvs/Prolog/Term/src/uk/co/williams_technologies/prolog/term/Variable.java,v 1.1.1.1 2004/05/05 05:19:27 simon Exp $
 *******************************************************************************
 *  $Log: Variable.java,v $
 *  Revision 1.1.1.1  2004/05/05 05:19:27  simon
 *  Import into CVS for sharing across computers.
 *
 *******************************************************************************
 */
package com.kajabity.prolog.core.expression;

import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author Simon J. Williams
 */
public final class Variable
    extends Term
{
    public final static char ANONYMOUS_START = '_';
    private final String     name;
    private final boolean    anonymous;
    private Expression       instance = null;

    /**
     * DOCUMENT ME!
     *
     * @param name
     */
    public Variable( String name )
    {
        super( TAG_VARIABLE, name, 0 );

        this.name          = name;
        this.anonymous     = ( name.charAt( 0 ) == ANONYMOUS_START );
    }

    /**
     * DOCUMENT ME!
     *
     * @return the deepest instance for this variable - or itself if not
     *         instantiated.
     */
    public Expression getFinalInstantiation(  )
    {
        if( instance == null )
        {
            return this;
        }
        else
        {
            return instance.getFinalInstantiation(  );
        }
    }

    /**
     * Test if instantiated. TODO: Should I check if instance is also
     * instantiated?
     *
     * @return DOCUMENT ME!
     */
    public boolean isInstatiated(  )
    {
        return instance != null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param instance
     */
    public void setInstance( Expression instance )
    {
        this.instance = instance;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public Expression getInstance(  )
    {
        return instance;
    }

    /**
     * Provide a simple String representation of this Variable.
     *
     * @return a String representation of this Variable.
     */
    public String toString(  )
    {
        return name;
    }

    /**
     * Return a copy of this variable - using a matching one from the list if
     * not anonymous and one exists.
     *
     * @param variables A map of all non-anonymous variables replaced during
     *        the copy.
     *
     * @return a copy of this Variable.
     */
    public Expression makeCopy( Map<String, Variable> variables )
    {
        Variable v;

        if( anonymous )
        {
            v = new Variable( name );
        }
        else
        {
            v = (Variable) variables.get( name );

            if( v == null )
            {
                v = new Variable( name );
                variables.put( name, v );
            }
        }

        return v;
    }

    /**
     * @see Expression#containsVariable(Variable)
     */
    public boolean containsVariable( Variable variable )
    {
        return equals( variable );
    }
    /**
     * @return Returns the anonymous.
     */
    public boolean isAnonymous()
    {
        return anonymous;
    }
}
