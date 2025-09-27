/*
 *******************************************************************************
 *  Copyright   :   (c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_IO
 *
 *  Created     :   Apr 12, 2004 by Simon J. Williams
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/token/TokenSource.java,v 1.1.1.1 2004/05/05 05:19:26 simon Exp $
 *******************************************************************************
 *  $Log: TokenSource.java,v $
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *******************************************************************************
 */
package com.kajabity.utils.token;

import java.io.IOException;

/**
 * @author Simon
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public interface TokenSource
{
	/**
	 * @return
	 */
	public abstract int read() throws IOException;


	/**
	 * @return
	 */
	public abstract int peek() throws IOException;


	/**
	 * @param i
	 * @return
	 */
	public abstract int peek( int offset ) throws IOException;


	/**
	 * @return
	 */
	public abstract int getLineNumber();
}
