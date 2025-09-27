/*
 *******************************************************************************
 *  Copyright   :   (c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_IO
 *
 *  Created     :   Apr 12, 2004 by Simon J. Williams
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/token/InputStreamTokenSource.java,v 1.1.1.1 2004/05/05 05:19:26 simon Exp $
 *******************************************************************************
 *  $Log: InputStreamTokenSource.java,v $
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *******************************************************************************
 */

package com.kajabity.utils.token;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Simon
 */
public class InputStreamTokenSource implements TokenSource
{
    private InputStream stream;

    private boolean     eof        = false;

    private byte[]      buffer     = new byte[ 1024 ];

    private int         available  = 0;

    private int         pos        = 0;

    private int         lineNumber = 1;


    /**
     * @param inputStream
     */
    public InputStreamTokenSource( InputStream inputStream )
    {
        this.stream = inputStream;
    }


    /**
     * @return
     * @throws IOException
     */
    public int read() throws IOException
    {
        if( available == 0 )
        {
            fill();
        }

        if( available == 0 )
        {
            return -1;
        }

        int ch = buffer[ pos++ ];
        available--;
        if( pos >= buffer.length )
        {
            pos = 0;
        }

        if( ch == '\n' )
        {
            lineNumber++;
        }

        return ch;
    }


    /**
     * @return
     * @throws IOException
     */
    public int peek() throws IOException
    {

        if( available <= 0 )
        {
            fill();
        }

        if( available <= 0 )
        {
            return -1;
        }

        return buffer[ pos ];
    }


    /**
     * @param offset
     * @return
     * @throws IOException
     */
    public int peek( int offset ) throws IOException
    {
        if( offset < 0 || offset >= buffer.length )
        {
            throw new IOException( "Peek out of range: offset " + offset );
        }

        if( available <= offset )
        {
            fill();
        }

        if( available <= offset )
        {
            return -1;
        }

        return buffer[ (pos + offset) % buffer.length ];
    }


    /**
     * @return
     */
    public int getLineNumber()
    {
        return lineNumber;
    }


    /**
     * This is a private method used to fill up the input buffer with as much as
     * is currently available from the input stream.
     *
     * @throws IOException
     */
    private void fill() throws IOException
    {
        if( !eof )
        {
            int fillPos = (pos + available) % buffer.length;
            int fillSize;
            if( fillPos < pos )
            {
                fillSize = pos - fillPos;
            }
            else
            {
                fillSize = buffer.length - fillPos;
            }

            int count = stream.read( buffer, fillPos, fillSize );
            if( count == -1 )
            {
                eof = true;
            }
            else
            {
                available += count;
            }
        }
    }
}
