/*
 *******************************************************************************
 *  Copyright   :   (c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_IO
 *
 *  Created     :   Apr 28, 2004 by Simon J. Williams
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/prolog/token/WhitespaceHandler.java,v 1.1.1.1 2004/05/05 05:19:26 simon Exp $
 *******************************************************************************
 *  $Log: WhitespaceHandler.java,v $
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
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
 * @author Simon
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class WhitespaceHandler extends Handler
{
	/**
	 *
	 */
    private int type;
    private int mask;

    public WhitespaceHandler( int mask, int type )
    {
        this.mask = mask;
        this.type = type;
    }


	/**
	 * @see com.kajabity.utils.token.Handler#handle(com.kajabity.utils.token.TokenSource, com.kajabity.utils.token.Token)
	 */
	public void handle( TokenSource in, Token token ) throws TokenException,
			IOException
	{
        token.setType( type );

        //  Append first char and all chars of the masked type.
        do
        {
            token.append( ( char ) in.read() );
        }
        while( PrologHandlerTable.getInstance().isChar( in.peek(), mask ) );
	}
}
