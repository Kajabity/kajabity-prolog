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
package com.kajabity.prolog.builtin.processor;

import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.Processor;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.expression.Term;


/**
 * @author Simon To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QuitProcessor extends Processor
{

    /**
     * @param functor
     * @param arity
     */
    public QuitProcessor( String name )
    {
        super( name, 0 );
    }


    /**
     * Inidicate to the Prolog engine that the session is finished.
     *
     * @see com.kajabity.prolog.core.environment.Processor#execute(com.kajabity.prolog.core.environment.Database,
     *      com.kajabity.prolog.core.expression.Term)
     */
    public void execute( Database db, Term term ) throws PrologException
    {
        db.setTerminated( true );
    }

}
