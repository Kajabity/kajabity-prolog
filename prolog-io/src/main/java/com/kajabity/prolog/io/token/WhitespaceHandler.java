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
