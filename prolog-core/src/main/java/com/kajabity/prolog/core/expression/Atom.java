/*
 *******************************************************************************
 *  Copyright   :   (c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_Term
 *
 *  Created     :   Mar 16, 2004 by Simon
 *
 *  $Header: /home/cvs/cvs/Prolog/Term/src/uk/co/williams_technologies/prolog/term/Atom.java,v 1.1.1.1 2004/05/05 05:19:27 simon Exp $
 *******************************************************************************
 *  $Log: Atom.java,v $
 *  Revision 1.1.1.1  2004/05/05 05:19:27  simon
 *  Import into CVS for sharing across computers.
 *
 *******************************************************************************
 */
package com.kajabity.prolog.core.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * A class representing a prolog atom which may be an Alpha Numeric string, a
 * set of one or more symbol characters or a quoted string of any characters.
 * Note: hashCode() and equals() are explicitly not overridden - atoms are
 * immutable and there should only be one with a given value so the basic Object
 * class implementations are appropriate.
 *
 * @author Simon J. Williams
 */
public final class Atom extends Term
{
    /** A map of all atoms created - they are immutable and so can be shared. */
    private final static Map<String, Atom> _cache     = new WeakHashMap<String, Atom>();

    /** A mapping of property names to their values for each atom. */
    private Map<Object, Object>              properties = null;

    /**
     * Privately construct an atom only when not already available in the
     * _cache.
     *
     * @param name
     *            The display name of the Atom.
     */
    private Atom( String name )
    {
        super( TAG_ATOM, name, 0 );
    }

    /**
     * Atoms are immutable so we can ensure there is only one instance of each
     * atom name.
     *
     * @param name
     *            the string name of the atom (unquoted).
     *
     * @return a new or existing atom with the given name.
     */
    public static Atom find( String name )
    {
        Atom atom = _cache.get( name );

        if( atom == null )
        {
            atom = new Atom( name );
            _cache.put( name, atom );
        }

        return atom;
    }

    /**
     * Look for and return a property stored against this atom using a key value
     * provided. If the key is not found, the result is null.
     *
     * @param key
     *            The key to look for in the Atom property map.
     *
     * @return the object stored against the key - or null if none is found.
     */
    public Object getProperty( Object key )
    {
        if( properties != null )
        {
            return properties.get( key );
        }

        return null;
    }

    /**
     * Save a property to the atom using the given key value. The property map
     * will be created if this is the first property stored on the atom.
     *
     * @param key
     *            The key used to retrieve the property when it is required.
     * @param value
     *            The value stored for the given key.
     */
    public void setProperty( Object key, Object value )
    {
        if( properties == null )
        {
            properties = new HashMap<Object, Object>();
        }

        properties.put( key, value );
    }

    /**
     * Basic string format is the atom name.
     *
     * @return A string representation of the atom - it's name.
     */
    public String toString()
    {
        return name;
    }
}
