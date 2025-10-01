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

package com.kajabity.prolog.io.predicate;

import java.io.IOException;

import com.kajabity.prolog.builtin.processor.AssertProcessor;
import com.kajabity.prolog.builtin.processor.FactProcessor;
import com.kajabity.prolog.builtin.processor.ListDatabaseProcessor;
import com.kajabity.prolog.builtin.processor.QuitProcessor;
import junit.framework.TestCase;

import com.kajabity.prolog.core.environment.Associativity;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.Processor;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.environment.Relation;
import com.kajabity.prolog.builtin.operator.SimpleOperator;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.io.format.PrologFormat;
import com.kajabity.prolog.io.format.SimplePrologFormat;
import com.kajabity.prolog.io.parse.ParseException;
import com.kajabity.prolog.io.parse.PrologParser;
import com.kajabity.prolog.io.token.Tokeniser;
import com.kajabity.utils.token.StringTokenSource;
import com.kajabity.utils.token.TokenException;
import com.kajabity.utils.token.TokenSource;

public class AssertGroundLiteralPredicateTest extends TestCase
{

    public void testParse() throws TokenException, IOException, ParseException, PrologException
    {
        // ========================= CREATE DATABASE =========================
        Database database = new Database( System.in, System.out, System.err );

        database.add( new SimpleOperator( ":-", Associativity.fx, 1200 ) );
        database.add( new ImmediateProcessor( ":-" ) );

        database.add( new SimpleOperator( ":-", Associativity.xfx, 1200 ) );
        database.add( new AssertProcessor( ":-" ) );

        database.add( new SolveProcessor( "?-" ) );

        database.setDefaultProcessor( new FactProcessor( "" ) );

        database.add( new QuitProcessor( "quit" ) );
        database.add( new ListDatabaseProcessor( "list_db" ) );

        database.add( new SimpleOperator( "/", Associativity.yfx, 400 ) );
        database.add( new AssertGroundLiteralPredicate( database, "assert_gl", 2 ) );

        // ========================= PARSE A STATEMENT =========================
        PrologParser parser = new PrologParser( database );
        PrologFormat formatter = new SimplePrologFormat();

        String text = ":- assert_gl( op3tmp / 3, 'com.kajabity.prolog.io.predicate.Op3PredicateTemp' ).";
        TokenSource source = new StringTokenSource( text );

        System.out.println( "Parse \"" + text + "\" ==> " );

        Tokeniser tokeniser = new Tokeniser( source );

        parser.parse( tokeniser );
        Expression expr = parser.getExpression();

        System.out.println( "\t(empty " + parser.isEmpty() + ", finished " + parser.isFinished() + ") ==> "
                + formatter.format( expr ) + "\n\n" );

        // ======================= EXECUTE THE STATEMENT =======================
        // Find a processor to handle this expression.
        if( expr instanceof Term )
        {
            Term term = (Term) expr;
            Processor processor = database.findProcessor( term.getName(), term.getArity() );

            // Execute the process.
            processor.execute( database, term );

            Relation relation = database.findRelation( "op3tmp", 3 );
            assert relation != null;
        }
    }
}
