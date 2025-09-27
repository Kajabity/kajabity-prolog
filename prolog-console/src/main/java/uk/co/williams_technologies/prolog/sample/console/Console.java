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

package uk.co.williams_technologies.prolog.sample.console;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.environment.operator.PrologOperatorException;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.io.Prolog;
import com.kajabity.prolog.io.parse.ParseException;
import com.kajabity.prolog.io.parse.PrologParser;
import com.kajabity.prolog.io.token.Tokeniser;
import com.kajabity.utils.token.StringTokenSource;
import com.kajabity.utils.token.TokenException;
import com.kajabity.utils.token.TokenSource;

/**
 * A main class to run Java Prolog as a console application.
 *
 * @author Simon J. Williams
 */
public class Console
{
    private final static Logger logger   = Logger.getLogger( Console.class );

    /**
     * The Prolog database.
     */
    private Database            database = null;

    /**
     * The Prolog parser.
     */
    PrologParser                parser   = null;


    /**
     * Loads initial definitions from an initialisation file.
     *
     * @throws PrologOperatorException
     * @throws TokenException
     * @throws IOException
     * @throws ParseException
     * @throws PrologException
     */
    private void init() throws PrologOperatorException, TokenException,
            IOException, ParseException, PrologException
    {
        database = Prolog.createDatabase( System.in, System.out, System.err );

        parser = new PrologParser( database );


        URL initialiseUrl = getClass().getResource( "/initialise.pro" );
        if( initialiseUrl == null )
        {
            logger.warn( "Failed to find initialisation file." );
        }
        else
        {
        	logger.info( "Prolog initialising from " + initialiseUrl );
            Prolog.consult( initialiseUrl, database );
        }
    }


    private String getLine( InputStream in ) throws IOException
    {
        StringBuffer linebuf = new StringBuffer();
        int ch;

        do
        {
            ch = in.read();
            if( ch == '\n' || ch == -1 )
            {
                break;
            }

            linebuf.append( (char) ch );
        } while( ch != '\n' && ch != -1 );

        String line = linebuf.toString().trim();

        return line;
    }

/**
    private boolean wantMore( PrintStream out, InputStream in )
            throws IOException
    {
        database.getCurrentOutputStream().println( "more? " );
        String line = getLine( in );
        logger.debug( "Read: [" + line + "]" );

        return line.trim().equals( ";" );
    }
*/
    /**
     * Wait for user input = then act on it.
     *
     * @param out
     * @param in
     * @throws IOException
     */
    public void run() throws IOException
    {
        boolean partial = false;
        do
        {
            if( partial )
            {
                database.getCurrentOutputStream().print( "&> " );
                partial = false;
            }
            else
            {
                database.getCurrentOutputStream().print( "Prolog> " );
            }

            String line = getLine( database.getCurrentInputStream() );
            logger.debug( "Read: [" + line + "]" );

            TokenSource source = new StringTokenSource( line );
            Tokeniser t = new Tokeniser( source );

            try
            {
                parser.parse( t );

                if( parser.isEmpty() )
                {
                    logger.debug( "==> Empty" );
                }
                else if( parser.isFinished() )
                {
                    Expression expr = parser.getExpression();
                    parser.reset();

                    logger.debug( "==> Parsed " + expr + "." );

                    //  Handle the parsed statement.
                    Prolog.processTerm( database, (Term) expr );
                }
                else
                {
                    logger.debug( "==> Partial" );
                    partial = true;
                }
            }
            catch( Exception ex )
            {
                ex.printStackTrace();
                parser.reset();
            }
        } while( !database.isTerminated() );

        database.getCurrentOutputStream().println( "Bye." );
    }


    public static void main( String[] args )
    {
        URL log4jConfig = Console.class.getClassLoader().getResource(
                "log4j.properties" );
        System.out.println( "Reading log4j config from "
                + log4jConfig.getPath() );
        PropertyConfigurator.configure( log4jConfig );

        Console console = new Console();

        try
        {
            console.init();
            console.run();
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
        }
    }
}
