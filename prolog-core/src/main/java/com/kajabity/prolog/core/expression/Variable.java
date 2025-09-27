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
