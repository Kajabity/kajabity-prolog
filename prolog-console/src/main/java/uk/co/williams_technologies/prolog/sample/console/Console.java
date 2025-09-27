/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *  Created on 16-Mar-2003
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/sample/uk/co/williams_technologies/prolog/sample/console/Console.java,v 1.8 2004/05/05 05:28:38 simon Exp $
 *******************************************************************************
 *  $Log: Console.java,v $
 *  Revision 1.8  2004/05/05 05:28:38  simon
 *  Lots of changes...
 *
 *  Revision 1.7  2004/02/24 04:52:46  simon
 *  Refactoring and fixes.
 *
 *  Revision 1.6  2004/02/17 08:35:44  simon
 *  Refactoring and adding Processor classes.
 *
 *  Revision 1.5  2004/01/15 07:43:33  simon
 *  Added call to initialise Arithmetic Builtins.
 *
 *  Revision 1.4  2004/01/15 07:08:14  simon
 *  Added during refactoring and arithmetic expression evaluation.
 *
 *  Revision 1.3  2003/11/24 04:46:49  simon
 *  Working again after implementing non-recursive parser.
 *
 *  Revision 1.2  2003/11/20 05:08:22  simon
 *  Switched to PrologOutputStream
 *
 *  Revision 1.1  2003/11/11 04:49:16  simon
 *  Starting Visual front end, adding logging.
 *
 *  Revision 1.9  2003/08/09 19:42:32  simon
 *  Starting to productize - ant build script and output to a build directory and a zipped
 *  distrubution with docs, javadoc and jars, bat/sh files.
 *
 *  Revision 1.8  2003/07/03 03:47:00  simon
 *  Fixes (not complete) to List handling.
 *
 *  Revision 1.7  2003/06/28 05:38:35  simon
 *  UPdate during adding of 'op' builtin.
 *
 *******************************************************************************
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
