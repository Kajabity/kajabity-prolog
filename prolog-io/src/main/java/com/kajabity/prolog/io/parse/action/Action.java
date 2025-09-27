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
package com.kajabity.prolog.io.parse.action;

import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.environment.operator.PrologOperatorException;
import com.kajabity.prolog.io.parse.ParseException;
import com.kajabity.prolog.io.parse.PrologParser;



/**
 * This is an abstract base class for 'Actions' to be performed when a parser
 * network matches a token in the input stream.
 *
 * @author Simon
 */
public abstract class Action
{
    public abstract void act( PrologParser parser ) throws PrologException,
            PrologOperatorException, ParseException;
}
