/*
 * Created on 18-Nov-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.kajabity.prolog.builtin.predicate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;



import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Expressions;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.core.expression.Variable;
import com.kajabity.prolog.io.parse.ParseException;
import com.kajabity.prolog.io.parse.PrologParser;
import com.kajabity.prolog.io.token.Tokeniser;
import com.kajabity.utils.token.StringTokenSource;
import com.kajabity.utils.token.TokenException;
import com.kajabity.utils.token.TokenSource;

/**
 * Class description here....
 *
 * @author Simon
 */
public class Read1Predicate extends GroundLiteral
{
    public Read1Predicate( Database database )
    {
        this( database, "read", 1 );
    }

    public Read1Predicate( Database database, String name, int arity )
    {
        super( database, name, arity );
        assert arity == 1;
    }


    /*
     * (non-Javadoc)
     *
     * @see com.kajabity.prolog.core.environment.GroundLiteral#execute(com.kajabity.prolog.core.environment.PrologDatabase,
     *      com.kajabity.prolog.io.solver.Goal)
     */
    protected List<Variable> execute( Goal goal ) throws TokenException, IOException,
            PrologException
    {
        List<Variable> result = null;

        if( goal.getTerm() instanceof Tuple )
        {
            Tuple tuple = (Tuple) goal.getTerm();

            List<Expression> args = tuple.getArgs().getTerms();
            Expression p1 = args.get( 0 );

            //  Read a term from the current input stream.
            Expression expr = getExpression( database.getCurrentInputStream() );

            result = Expressions.unify( p1, expr );
        }

        return result;
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


    private Expression getExpression( InputStream in ) throws TokenException,
            IOException, ParseException, PrologException
    {
        //  Need an expression - so start with a "Partial" expression.
        boolean partial = true;
        Expression expr = null;

        PrologParser parser = new PrologParser( database );

        do
        {
            String line = getLine( in );

            TokenSource source = new StringTokenSource( line );
            Tokeniser t = new Tokeniser( source );

            parser.parse( t );

            if( parser.isFinished() )
            {
                expr = parser.getExpression();
                partial = false;
            }
        } while( partial );

        return expr;
    }
}
