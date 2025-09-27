/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/prolog/token/NumberHandler.java,v 1.1.1.1 2004/05/05 05:19:26 simon Exp $
 *******************************************************************************
 *  $Log: NumberHandler.java,v $
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

import java.io.IOException;

import com.kajabity.utils.token.Handler;
import com.kajabity.utils.token.Token;
import com.kajabity.utils.token.TokenException;
import com.kajabity.utils.token.TokenSource;

/**
 * <p>Title: SJW Test project</p>
 * <p>Description: Test and Utility project for SJW.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Simon J. Williams
 * @version 1.0
 */

public class NumberHandler
    extends Handler
{
    private int mask;
    private int integerType;
    //private int realType;

    public NumberHandler( int mask, int integerType/*, int realType*/ )
    {
        this.mask = mask;
        this.integerType = integerType;
        //this.realType = realType;
    }


    /**
     * Handle numbers - a set of digits with optional . followed immediately
     * (with no whitespace) by more digits.
     *
     * @param in
     * @param token
     * @throws IOException
     * @throws sjw.io.TokenException
     */
    public void handle( TokenSource in, Token token )
        throws IOException, TokenException
    {
        token.setType( integerType );

        //  Append first char and all chars of the masked type.
        do
        {
            token.append( (char) in.read() );
        } while( PrologHandlerTable.getInstance().isChar( in.peek(), mask ) );
    }
}
