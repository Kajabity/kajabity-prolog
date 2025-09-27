/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/prolog/token/NameHandler.java,v 1.1.1.1 2004/05/05 05:19:26 simon Exp $
 *******************************************************************************
 *  $Log: NameHandler.java,v $
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

public class NameHandler
    extends Handler
{
    private int mask;
    private int type;
    private int subtype;

    public NameHandler( int mask, int type, int subtype )
    {
        this.mask = mask;
        this.type = type;
        this.subtype = subtype;
    }


    public void handle( TokenSource in, Token token )
        throws TokenException, IOException
    {
        token.setType( type );
        token.setSubType( subtype );

        //  Append first char and all chars of the masked type.
        do
        {
            token.append( (char) in.read() );
        } while( PrologHandlerTable.getInstance().isChar( in.peek(), mask ) );
    }

}
