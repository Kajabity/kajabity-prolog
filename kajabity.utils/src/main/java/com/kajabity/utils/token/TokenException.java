/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/token/TokenException.java,v 1.1.1.1 2004/05/05 05:19:26 simon Exp $
 *******************************************************************************
 *  $Log: TokenException.java,v $
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *  Revision 1.3  2003/11/19 07:56:56  simon
 *  Changed source directories
 *  Added PrologInput/OutputStreams
 *  Added Write predicate (not quite right yet).
 *
 *  Revision 1.2  2003/06/28 05:38:35  simon
 *  UPdate during adding of 'op' builtin.
 *
 *******************************************************************************
 */


package com.kajabity.utils.token;

/**
 * <p>Title: SJW Test project</p>
 * <p>Description: Test and Utility project for SJW.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Simon J. Williams
 * @version 1.0
 */

public class TokenException
    extends Exception
{

    public TokenException()
    {
    }


    public TokenException( String message )
    {
        super( message );
    }


    public TokenException( String message, Throwable cause )
    {
        super( message, cause );
    }


    public TokenException( Throwable cause )
    {
        super( cause );
    }
}
