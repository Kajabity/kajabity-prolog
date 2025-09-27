/*
 * Copyright (c) 2003-2025 Simon J. Williams
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


package com.kajabity.prolog.io.token;

import java.io.IOException;

import com.kajabity.utils.token.Handler;
import com.kajabity.utils.token.Token;
import com.kajabity.utils.token.TokenSource;



public class DelimitedHandler
    extends Handler
{
    private int type;
    private int subtype;

    public DelimitedHandler( int type, int subtype )
    {
        this.type = type;
        this.subtype = subtype;
    }


    /**
     * Next char from the stream is the delimiter.
     * @param in
     * @param token
     * @return
     */
    public void handle( TokenSource in, Token token )
        throws UnterminatedTokenException, IOException
    {
        //  Token has been reset and the line number and offset added.
        token.setType( type );
        token.setSubType( subtype );

        // Get and skip the delimiter.
        int delim = in.read();

        int ch = in.read();
        while( ch != EOF && ch != delim )
        {
            token.append( ( char ) ch );
            /** @todo: Handle escapes using '\'. */
            ch = in.read();
        }

        if( ch == EOF )
        {
            // End of stream without delimiter.
            throw new UnterminatedTokenException(
                "Reached End of input when searching for closing delimiter: " +
                delim );
        }

        token.setQuote( (char) delim );
    }

}
