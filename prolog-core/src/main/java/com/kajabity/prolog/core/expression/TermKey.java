/*
 * Created on Mar 24, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.kajabity.prolog.core.expression;

/**
 * @author Simon
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TermKey
{

	/**
	 * Contruct the abstract base Term class - provide and explicit tag value.
	 *
	 * @param tag identifies the type of the Term.
	 * @param name DOCUMENT ME!
	 * @param arity DOCUMENT ME!
	 */
	public TermKey( String name, int arity )
	{
	    this.name      = name;
	    this.arity     = arity;
	    this.key       = name + "/" + arity;
	}

	private final int	arity;
	private final String	key;
	protected final String	name;

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the arity.
	 */
	public int getArity(  )
	{
	    return arity;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the key.
	 */
	public String getKey(  )
	{
	    return key;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the name.
	 */
	public String getName(  )
	{
	    return name;
	}
}
