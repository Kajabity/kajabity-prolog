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
