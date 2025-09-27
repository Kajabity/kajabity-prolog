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
import com.kajabity.utils.token.TokenException;
import com.kajabity.utils.token.TokenSource;


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
