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
package com.kajabity.prolog.io.parse;

import java.io.IOException;


import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.environment.operator.PrologOperatorException;
import com.kajabity.prolog.io.parse.action.Action;
import com.kajabity.prolog.io.parse.match.Matcher;
import com.kajabity.prolog.io.token.Tokeniser;
import com.kajabity.utils.token.TokenException;

/**
 * DOCUMENT ME!
 *
 * @author Simon To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
class Tx
{
    private Matcher matcher      = null;
    private int     matcherParam = 0;
    private Action  action       = null;
    private int     nextState    = 0;


    /**
     * Construct a standard transition.
     *
     * @param matcher
     * @param matcherParam
     * @param action
     * @param nextState
     */
    Tx( Matcher matcher, int matcherParam, Action action, int nextState )
    {
        this.matcher = matcher;
        this.matcherParam = matcherParam;
        this.action = action;
        this.nextState = nextState;
    }


    /**
     * DOCUMENT ME!
     *
     * @return
     */
    int getNextState()
    {
        return nextState;
    }


    /**
     * DOCUMENT ME!
     *
     * @param tokeniser
     *            DOCUMENT ME!
     * @param parser
     *            DOCUMENT ME!
     * @return
     */
    boolean matches( Tokeniser tokeniser, PrologParser parser )
    {
        if( matcher == null )
        {
            return true;
        }
        else
        {
            return matcher.match( parser, matcherParam );
        }
    }


    /**
     * DOCUMENT ME!
     *
     * @param tokeniser
     *            DOCUMENT ME!
     * @param parser
     *            DOCUMENT ME!
     * @throws ParseException
     *             DOCUMENT ME!
     * @throws TokenException
     *             DOCUMENT ME!
     * @throws IOException
     *             DOCUMENT ME!
     */
    void doAction( Tokeniser tokeniser, PrologParser parser )
            throws PrologOperatorException, PrologException, ParseException
    {
        if( action != null )
        {
            action.act( parser );
        }
    }


    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public boolean isLamda()
    {
        return matcher == null;
    }
}
