/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/prolog/parse/ParseException.java,v 1.2 2004/07/01 05:31:53 simon Exp $
 *******************************************************************************
 *  $Log: ParseException.java,v $
 *  Revision 1.2  2004/07/01 05:31:53  simon
 *  Major update after many changes.
 *
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *  Revision 1.3  2003/11/20 05:14:29  simon
 *  Changes to implement write( Term ) built-in - added PrologOutputStream,
 *  also refactored to src/* folders.
 *
 *  Revision 1.2  2003/06/28 05:38:35  simon
 *  UPdate during adding of 'op' builtin.
 *
 *******************************************************************************
 */
package com.kajabity.prolog.io.parse;

import com.kajabity.prolog.core.environment.PrologException;

/**
 * DOCUMENT ME!
 *
 * @author Simon
 */
public class ParseException extends PrologException {
	/**
     *
     */
    private static final long serialVersionUID = -3067610104849894102L;

    /**
	 * Constructor for ParseException.
	 */
	public ParseException() {
		super();
	}

	/**
	 * Constructor for ParseException.
	 *
	 * @param message
	 */
	public ParseException(String message) {
		super(message);
	}

	/**
	 * Constructor for ParseException.
	 *
	 * @param message
	 * @param cause
	 */
	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor for ParseException.
	 *
	 * @param cause
	 */
	public ParseException(Throwable cause) {
		super(cause);
	}
}
