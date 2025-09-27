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
