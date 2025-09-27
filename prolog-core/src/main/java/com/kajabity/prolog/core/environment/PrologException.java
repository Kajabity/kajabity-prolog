/*
 *******************************************************************************
 *  Copyright   :   (c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_Environment
 *
 *  Created     :   Mar 24, 2004 by Simon J. Williams
 *
 *  $Header: /home/cvs/cvs/Prolog/Environment/src/uk/co/williams_technologies/prolog/environment/PrologException.java,v 1.1.1.1 2004/05/05 05:19:26 simon Exp $
 *******************************************************************************
 *  $Log: PrologException.java,v $
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *******************************************************************************
 */

package com.kajabity.prolog.core.environment;

/**
 * @author Simon
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrologException extends Exception
{
	/**
     *
     */
    private static final long serialVersionUID = -3131909369895745446L;


    /**
	 *
	 */
	public PrologException()
	{
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param message
	 */
	public PrologException( String message )
	{
		super( message );
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param cause
	 */
	public PrologException( Throwable cause )
	{
		super( cause );
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param message
	 * @param cause
	 */
	public PrologException( String message, Throwable cause )
	{
		super( message, cause );
		// TODO Auto-generated constructor stub
	}
}
