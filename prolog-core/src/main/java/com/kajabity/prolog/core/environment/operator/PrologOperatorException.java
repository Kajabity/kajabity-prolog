/*
 *******************************************************************************
 *  Copyright   :   (c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_Environment
 *
 *  Created     :   Apr 27, 2004 by Simon J. Williams
 *
 *  $Header: /home/cvs/cvs/Prolog/Environment/src/uk/co/williams_technologies/prolog/environment/PrologOperatorException.java,v 1.1.1.1 2004/05/05 05:19:26 simon Exp $
 *******************************************************************************
 *  $Log: PrologOperatorException.java,v $
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *******************************************************************************
 */

package com.kajabity.prolog.core.environment.operator;

import com.kajabity.prolog.core.environment.PrologException;

/**
 * @author Simon
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrologOperatorException extends PrologException
{
	/**
     *
     */
    private static final long serialVersionUID = 122762296305015401L;


    /**
	 *
	 */
	public PrologOperatorException()
	{
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param message
	 */
	public PrologOperatorException( String message )
	{
		super( message );
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param cause
	 */
	public PrologOperatorException( Throwable cause )
	{
		super( cause );
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param message
	 * @param cause
	 */
	public PrologOperatorException( String message, Throwable cause )
	{
		super( message, cause );
		// TODO Auto-generated constructor stub
	}
}
