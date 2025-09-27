/*
 *******************************************************************************
 *  Copyright   :   (c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_IO
 *
 *  Created     :   Apr 28, 2004 by Simon J. Williams
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/token/StringTokenSource.java,v 1.1.1.1 2004/05/05 05:19:26 simon Exp $
 *******************************************************************************
 *  $Log: StringTokenSource.java,v $
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *******************************************************************************
 */
package com.kajabity.utils.token;

import java.io.IOException;


/**
 * DOCUMENT ME!
 *
 * @author Simon To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class StringTokenSource
    implements TokenSource
{
    private byte [] bytes;
    private int     position;
    private int     lineNumber;

    public StringTokenSource( String source )
    {
        this.bytes     = source.getBytes(  );
        position       = 0;
        lineNumber     = 1;
    }

    /* (non-Javadoc)
     * @see uk.co.williams_technologies.token.TokenSource#read()
     */
    public int read(  )
        throws IOException
    {
        if( position < bytes.length )
        {
            int ch = bytes[ position++ ];

            if( ch == '\n' )
            {
                lineNumber++;
            }

            return ch;
        }
        else
        {
            return -1;
        }
    }

    /* (non-Javadoc)
     * @see uk.co.williams_technologies.token.TokenSource#peek()
     */
    public int peek(  )
        throws IOException
    {
        if( position < bytes.length )
        {
            return bytes[ position ];
        }
        else
        {
            return -1;
        }
    }

    /* (non-Javadoc)
     * @see uk.co.williams_technologies.token.TokenSource#peek(int)
     */
    public int peek( int offset )
        throws IOException
    {
        if( position + offset < bytes.length )
        {
            return bytes[ position + offset ];
        }
        else
        {
            return -1;
        }
    }

    /* (non-Javadoc)
     * @see uk.co.williams_technologies.token.TokenSource#getLineNumber()
     */
    public int getLineNumber(  )
    {
        return lineNumber;
    }
}
