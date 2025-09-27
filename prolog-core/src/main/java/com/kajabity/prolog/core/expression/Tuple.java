/*
 *******************************************************************************
 *  Copyright   :   (c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_Term
 *
 *  Created     :   Mar 16, 2004 by Simon
 *
 *  $Header: /home/cvs/cvs/Prolog/Term/src/uk/co/williams_technologies/prolog/term/Tuple.java,v 1.1.1.1 2004/05/05 05:19:27 simon Exp $
 *******************************************************************************
 *  $Log: Tuple.java,v $
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
public final class Tuple
    extends Term
{
    private final Term     functor;
    private final TermList args;

    /**
     * DOCUMENT ME!
     *
     * @param functor
     * @param args
     */
    public Tuple( Term functor, TermList args )
    {
        super( TAG_TUPLE, functor.toString(  ), args.size(  ) );

        this.functor     = functor;
        this.args        = args;
    }

    /**
     * DOCUMENT ME!
     *
     * @param functor
     * @param args
     */
    public Tuple( Term functor, Term arg )
    {
        super( TAG_TUPLE, functor.toString(  ), 1 );

        this.functor     = functor;
        this.args        = new TermList( arg );
    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the args.
     */
    public TermList getArgs(  )
    {
        return args;
    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the functor.
     */
    public Term getFunctor(  )
    {
        return functor;
    }

    /**
     * equals if the functor and args are equal.
     *
     * @see Object#equals(Object)
     */
    public boolean equals( Object obj )
    {
        if( obj instanceof Tuple )
        {
            return functor.equals( ( (Tuple) obj ).functor ) &&
                   args.equals( ( (Tuple) obj ).args );
        }

        return false;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    public int hashCode(  )
    {
        //TODO: hash from func and args.
        return super.hashCode(  );
    }

    /**
     * @see Object#toString()
     */
    public String toString(  )
    {
        StringBuffer buf = new StringBuffer(  );

        buf.append( functor.toString(  ) );
        buf.append( "( " );
        buf.append( args.toString(  ) );
        buf.append( " )" );

        return buf.toString(  );
    }

    public Expression makeCopy( Map<String, Variable> variables )
    {
        return new Tuple( (Term) functor.makeCopy( variables ),
                          (TermList) args.makeCopy( variables ) );
    }

    /**
     * @see Expression#containsVariable(Variable)
     */
    public boolean containsVariable( Variable variable )
    {
        return functor.containsVariable( variable ) ||
               args.containsVariable( variable );
    }
}
