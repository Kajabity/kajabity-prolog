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

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;

import com.kajabity.prolog.core.arithmetic.Function;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.Processor;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.environment.Relation;
import com.kajabity.prolog.core.environment.operator.Operator;
import com.kajabity.prolog.core.expression.Term;


/**
 * @author Simon To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ListDatabaseProcessor extends Processor
{
    /**
     * Construct this processor with a name.
     *
     * @param name the name this processor will handle.
     */
    public ListDatabaseProcessor( String name )
    {
        super( name, 0 );
    }


    /**
     * List the contents of the Database.
     *
     * @see com.kajabity.prolog.core.environment.Processor#execute(com.kajabity.prolog.core.environment.Database,
     *      com.kajabity.prolog.core.expression.Term)
     */
    public void execute( Database db, Term term ) throws PrologException
    {
        PrintStream out = db.getCurrentOutputStream();

        out.println( "Database" );
        out.println( "========" );

        out.println( "Relations" );

        Map<String, Relation> relations = db.getRelations();
        Iterator<String> ir = relations.keySet().iterator();
        while( ir.hasNext() )
        {
            Relation r = relations.get( ir.next() );
            if( r.isGroundLiteral() )
            {
                out.println( "\t" + r.getKey() + " (Ground Literal)" );
            }
            else
            {
                out.println( "\t" + r.getKey() );
            }
        }


        out.println( "Operators" );

        Map<String, Operator> operators = db.getOperators();
        Iterator<String> iOp = operators.keySet().iterator();
        while( iOp.hasNext() )
        {
            Operator op = operators.get( iOp.next() );
            out.println( "\t" + op );
        }


        out.println( "Arithmetic Functions" );

        Map<String, Function> functions = db.getFunctions();
        Iterator<String> ifunc = functions.keySet().iterator();
        while( ifunc.hasNext() )
        {
            Function func = functions.get( ifunc.next() );
            out.println( "\t" + func.getKey() );
        }


        out.println( "Command Processors" );

        Map<String, Processor> processors = db.getProcessors();
        Iterator<String> iproc = processors.keySet().iterator();
        while( iproc.hasNext() )
        {
            Processor proc = processors.get( iproc.next() );
            out.println( "\t" + proc.getKey() );
        }
    }

}
