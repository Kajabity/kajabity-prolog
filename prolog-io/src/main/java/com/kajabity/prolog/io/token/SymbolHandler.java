/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/prolog/token/SymbolHandler.java,v 1.1.1.1 2004/05/05 05:19:26 simon Exp $
 *******************************************************************************
 *  $Log: SymbolHandler.java,v $
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

public class SymbolHandler
    extends Handler
{
    private int mask;
    private int symbolType;
    private int commentType;
    private int subtype;

    //private int dotType;

    public SymbolHandler( int mask, int symbolType, int commentType, int subtype )
    {
        this.mask = mask;
        this.symbolType = symbolType;
        this.subtype = subtype;
        this.commentType = commentType;
        //this.dotType = dotType;
    }


    public void handle( TokenSource in, Token token )
        throws TokenException, IOException
    {
        int peek0 = in.peek();
        int peek1 = in.peek( 1 );

        //  Check if this is a comment.
        if( peek0 == '/' && peek1 == '*' )
        {
            token.setType( commentType );

            token.append( ( char ) in.read() );
            token.append( ( char ) in.read() );

            while( in.peek() != EOF &&
                   !(in.peek() == '*' && in.peek( 1 ) == '/') )
            {
                token.append( ( char ) in.read() );
            }

            if( in.peek() == EOF )
            {
                // End of stream without delimiter.
                throw new UnterminatedTokenException(
                    "Reached End of input before end of comment." );
            }

            token.append( ( char ) in.read() );
            token.append( ( char ) in.read() );
        }
        else
        {
            token.setType( symbolType );
            token.setSubType( subtype );

            //  Append first char and all chars of the masked type.
            do
            {
                token.append( ( char ) in.read() );

                // If next bit is a comment - break off.
                if( in.peek() == '/' && in.peek( 1 ) == '*' )
                {
                    break;
                }
            }
            while( PrologHandlerTable.getInstance().isChar( in.peek(), mask ) );

            /*if( token.getBuf().toString().equals( "." ) )
            {
                token.setType( dotType );
            }*/
        }
    }

}
