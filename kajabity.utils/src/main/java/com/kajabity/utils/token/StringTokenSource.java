/*
 * Copyright (c) 2004-2025 Simon J. Williams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
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
