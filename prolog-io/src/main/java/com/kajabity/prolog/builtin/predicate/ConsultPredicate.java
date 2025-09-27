/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 * Created on 16-Mar-2003
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/predicate/ConsultPredicate.java,v 1.1 2004/01/25 05:47:19 simon Exp $
 *******************************************************************************
 *  $Log: ConsultPredicate.java,v $
 *  Revision 1.1  2004/01/25 05:47:19  simon
 *  Added to enable basic 'consult' predicate processing.
 *
 *  Revision 1.3  2003/11/20 05:14:28  simon
 *  Changes to implement write( Term ) built-in - added PrologOutputStream,
 *  also refactored to src/* folders.
 *
 *  Revision 1.2  2003/06/28 05:38:35  simon
 *  UPdate during adding of 'op' builtin.
 *
 *******************************************************************************
 */
package com.kajabity.prolog.builtin.predicate;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.expression.Atom;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.core.expression.Variable;
import com.kajabity.prolog.io.Prolog;
import com.kajabity.utils.token.TokenException;

/**
 * @author Simon To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class ConsultPredicate extends GroundLiteral
{
    private final static Logger logger = Logger.getLogger( ConsultPredicate.class );

    public ConsultPredicate( Database database )
    {
        this( database, "consult", 1 );
    }

    public ConsultPredicate( Database database, String name, int arity )
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
    protected List<Variable> execute( Goal goal ) throws TokenException, IOException, PrologException
    {
        logger.debug( "CONSULT: " + goal.getTerm() );

        if( goal.getTerm() instanceof Tuple )
        {
            Tuple tuple = (Tuple) goal.getTerm();
            List<Expression> args = tuple.getArgs().getTerms();

            Expression p1 = args.get( 0 );

            logger.debug( "CONSULT: " + p1 + " - tag " + ((Term) p1).getTag() );

            if( p1.isTerm() && ((Term) p1).getTag() == Term.TAG_ATOM )
            {
                String filename = ((Atom) p1).getName();

                URL url = getClass().getResource( filename );
                if( url == null )
                {
                    logger.debug( "Failed to find " + filename );
                }
                else
                {
                    database.getCurrentOutputStream().println( "Consulting [" + filename + "]." );

                    Prolog.consult( url, database );

                    return Collections.emptyList();
                }
            }
        }

        return null;
    }
}
