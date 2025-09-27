/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/prolog/token/InvalidNumericTokenException.java,v 1.1.1.1 2004/05/05 05:19:26 simon Exp $
 *******************************************************************************
 *  $Log: InvalidNumericTokenException.java,v $
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


package com.kajabity.prolog.io.token;

import com.kajabity.utils.token.TokenException;

/**
 * <p>Title: SJW Test project</p>
 * <p>Description: Test and Utility project for SJW.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Simon J. Williams
 * @version 1.0
 */

public class InvalidNumericTokenException
    extends TokenException
{
    /**
     *
     */
    private static final long serialVersionUID = 688939648571588705L;


    public InvalidNumericTokenException()
    {
    }


    public InvalidNumericTokenException( String message )
    {
        super( message );
    }


    public InvalidNumericTokenException( String message, Throwable cause )
    {
        super( message, cause );
    }


    public InvalidNumericTokenException( Throwable cause )
    {
        super( cause );
    }
}
