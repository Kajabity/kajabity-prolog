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
package com.kajabity.prolog.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;

import org.apache.log4j.Logger;

import com.kajabity.prolog.core.environment.Associativity;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.Processor;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.environment.operator.PrologOperatorException;
import com.kajabity.prolog.core.environment.operator.SimpleOperator;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.io.parse.ParseException;
import com.kajabity.prolog.io.parse.PrologParser;
import com.kajabity.prolog.io.predicate.AssertFunctionPredicate;
import com.kajabity.prolog.io.predicate.AssertGroundLiteralPredicate;
import com.kajabity.prolog.io.predicate.AssertProcessor;
import com.kajabity.prolog.io.predicate.FactProcessor;
import com.kajabity.prolog.io.predicate.ImmediateProcessor;
import com.kajabity.prolog.io.predicate.ListDatabaseProcessor;
import com.kajabity.prolog.io.predicate.QuitProcessor;
import com.kajabity.prolog.io.predicate.SolveProcessor;
import com.kajabity.prolog.io.token.Tokeniser;
import com.kajabity.utils.token.InputStreamTokenSource;
import com.kajabity.utils.token.TokenException;
import com.kajabity.utils.token.TokenSource;

/**
 * Class description here....
 *
 * @author Simon
 */
public class Prolog
{
    private final static Logger logger = Logger.getLogger( Prolog.class );

    /**
     * Create a new database with some Core build-ins.
     *
     * @param in
     *            The default input stream - e.g. System.in.
     * @param out
     *            The default output stream - e.g. System.out.
     * @param err
     *            The default error output stream - e.g. System.err.
     * @return Returns an initialised database.
     * @throws PrologException
     */
    public static Database createDatabase( InputStream in, PrintStream out, PrintStream err ) throws PrologException
    {
        Database database = new Database( System.in, System.out, System.err );

        database.add( new QuitProcessor( "quit" ) );

        database.add( new ImmediateProcessor( ":-" ) );
        database.add( new SimpleOperator( ":-", Associativity.fx, 1200 ) );

        database.add( new AssertProcessor( ":-" ) );
        database.add( new SimpleOperator( ":-", Associativity.xfx, 1200 ) );

        database.add( new SolveProcessor( "?-" ) );
        database.add( new SimpleOperator( "?-", Associativity.fx, 1200 ) );

        database.setDefaultProcessor( new FactProcessor( "" ) );

        database.add( new ListDatabaseProcessor( "list_db" ) );

        database.add( new AssertGroundLiteralPredicate( database, "assertg", 2 ) );
        database.add( new AssertFunctionPredicate( database, "assert_function", 2 ) );
        database.add( new SimpleOperator( "/", Associativity.yfx, 400 ) );

        // Now initialised in "initialise.pro".
        //InitialiseArithmeticBuiltins.initialise( database );

        return database;
    }

    /**
     * Consult (load) a prolog file from the current input stream.
     *
     * @param db
     *            the prolog database used to parse the file and add definitions
     *            to.
     * @throws TokenException
     * @throws IOException
     * @throws ParseException
     * @throws PrologException
     */
    public static void consult( Database db ) throws TokenException, IOException, PrologException
    {
        logger.debug( "### Consulting..." );

        PrologParser parser = new PrologParser( db );
        TokenSource source = new InputStreamTokenSource( db.getCurrentInputStream() );
        Tokeniser tokeniser = new Tokeniser( source );

        while( parser.parse( tokeniser ) )
        {
            if( parser.isFinished() )
            {
                Expression expr = parser.getExpression();

                logger.debug( "Parsed: " + expr );

                if( expr.isTerm() )
                {
                    processTerm( db, (Term) expr );
                }
                else
                {
                    throw new PrologException( "Cannot process - expression is not a Term." );
                }
            }
            else
            {
                throw new PrologException( "Incomplete term in file." );
            }
        }
    }

    /**
     * Consult the contents of a URL (e.g. a file, etc. )
     *
     * @param url
     * @param db
     * @throws IOException
     * @throws PrologException
     * @throws ParseException
     * @throws TokenException
     * @throws PrologOperatorException
     */
    public static void consult( URL url, Database db ) throws PrologOperatorException, TokenException, IOException, PrologException
    {
        InputStream is = url.openStream();

        logger.debug( "### Consulting... " + url );

        PrologParser parser = new PrologParser( db );
        TokenSource source = new InputStreamTokenSource( is );
        Tokeniser tokeniser = new Tokeniser( source );

        while( parser.parse( tokeniser ) )
        {
            if( parser.isFinished() )
            {
                Expression expr = parser.getExpression();

                if( expr.isTerm() )
                {
                    processTerm( db, (Term) expr );
                }
                else
                {
                    throw new PrologException( "Cannot process - expression is not a Term." );
                }
            }
            else
            {
                throw new PrologException( "Incomplete term in file." );
            }
        }
    }

    /**
     * Extract the specified processor from the Term and execute it.
     *
     * @param db the prolog database on which to execute the processor.
     * @param term the Processor term.
     * @throws PrologException
     * @throws IOException
     * @throws TokenException
     * @throws ParseException
     */
    public static void processTerm( Database db, Term term ) throws PrologException, IOException, TokenException
    {
        logger.debug( "\tProcess Term: " + term + "." );

        // Find a processor to handle this expression.
        Processor processor = db.findProcessor( term.getName(), term.getArity() );

        // Execute the process.
        processor.execute( db, term );
    }
}
