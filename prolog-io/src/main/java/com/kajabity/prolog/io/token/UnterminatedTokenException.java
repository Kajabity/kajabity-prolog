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

import com.kajabity.utils.token.TokenException;

public class UnterminatedTokenException
    extends TokenException
{

    /**
     *
     */
    private static final long serialVersionUID = -7662022780414952204L;


    public UnterminatedTokenException()
    {
    }


    public UnterminatedTokenException( String message )
    {
        super( message );
    }


    public UnterminatedTokenException( String message, Throwable cause )
    {
        super( message, cause );
    }


    public UnterminatedTokenException( Throwable cause )
    {
        super( cause );
    }
}
