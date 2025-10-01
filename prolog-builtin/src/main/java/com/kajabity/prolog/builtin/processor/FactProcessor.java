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
 * Prolog command processor which expects a "fact" and asserts it as such into the database.
 *
 * @author Simon
 */
public class FactProcessor extends Processor
{
    /**
     * Construct a 'Fact Processor' - name and arity are irrelevant.
     * @param name the processor name.
     */
    public FactProcessor( String name )
    {
        super( name, 0 );
    }

    /**
     * Executes the provided term by adding it to the database as an assertion.
     * Assumption is that the Fact processor name is empty so the whole term is asserted.
     */
    public void execute( Database db, Term term ) throws PrologException
    {
        //	Assert a fact - it has no tail.
        db.assertz( term, null );
    }
}
