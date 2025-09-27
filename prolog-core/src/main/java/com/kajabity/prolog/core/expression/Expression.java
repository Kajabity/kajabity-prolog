/*
 *******************************************************************************
 *  Copyright   :   (c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_Term
 *
 *  Created     :   Mar 21, 2004 by Simon
 *
 *  $Header: /home/cvs/cvs/Prolog/Term/src/uk/co/williams_technologies/prolog/term/Expression.java,v 1.1.1.1 2004/05/05 05:19:27 simon Exp $
 *******************************************************************************
 *  $Log: Expression.java,v $
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
 * @author Simon To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public interface Expression
{
    /**
     * Used for variable identification - for anything except a substituted
     * variable this returns 'this'.
     *
     * @return DOCUMENT ME!
     */
    public Expression getFinalInstantiation(  );

    /**
     * test if an expression contains a variable.
     *
     * @param expr DOCUMENT ME!
     * @param variable DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean containsVariable( Variable variable );

    /**
     * A method for combining comma separated Terms into a List. It assumes
     * that the first expression might be either a Term or an existing List,
     * but adds the second to the list whichever it is. This is to support
     * operators such as the implication (":-") which have a higher precedence
     * than the comma (",").
     *
     * @param expr An .
     *
     * @return a List of Terms.
     */
    public abstract TermList append( Expression expr );

    /*
     * private boolean bracketed = false;
     */

    /**
     * Returns the bracketed.
     *
     * @return boolean
     */

    /*
     * public boolean isBracketed( ) { return bracketed; }
     */

    /**
     * Sets the bracketed.
     *
     * @param bracketed The bracketed to set
     *
     * @return DOCUMENT ME!
     */

    /*
     * public void setBracketed( boolean bracketed ) { this.bracketed =
     * bracketed;
     */

    /**
     * Construct and return a copy of this term with a fresh set of variables
     * so they are unique during unification/solving. Constant terms return
     * themselves.
     *
     * @param variables a Map of all non-anonymous variables replaced during
     *        the copy keyed by their String names.
     *
     * @return a new Term with any contained variables replaced.
     */
    public Expression makeCopy( Map<String, Variable> variables );

    /**
     * Differentiate between Terms and Lists of Terms.
     *
     * @return DOCUMENT ME!
     */
    public boolean isTerm(  );
}
