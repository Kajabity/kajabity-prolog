/*
 *******************************************************************************
 *  Copyright   :(c) 2003/4 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *  Created on 16-Feb-2004
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/action/AssertProcessor.java,v 1.3 2004/05/05 05:28:38 simon Exp $
 *******************************************************************************
 *  $Log: AssertProcessor.java,v $
 *  Revision 1.3  2004/05/05 05:28:38  simon
 *  Lots of changes...
 *
 *  Revision 1.2  2004/02/24 04:49:39  simon
 *  Don't list variables in 'perform' - needs to be a generic 'assert' and 'solve' action
 *  Move Processor to environment package.
 *
 *  Revision 1.1  2004/02/17 08:35:44  simon
 *  Refactoring and adding Processor classes.
 *
 *******************************************************************************
 */
package uk.co.williams_technologies.prolog.builtin.processor;

import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.Processor;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.TermList;
import com.kajabity.prolog.core.expression.Tuple;


/**
 * Assert a "Horn" clause of the form: -<term>:- <term>, <term>.
 *
 * @author Simon J. Williams
 */
public class AssertProcessor extends Processor
{

    /**
     * @param functor
     * @param arity
     */
    public AssertProcessor( String name )
    {
        super( name, 2 );
    }


    /**
     * Add the expression as an assertion
     *
     * @throws PrologException
     * @see com.kajabity.prolog.io.builtin.processor.Processor#perform()
     */
    public void execute( Database db, Term term ) throws PrologException
    {
        //  It should be a tuple ":-( head, tail ).
        if( term.getTag() == Term.TAG_TUPLE )
        {
            TermList args = ((Tuple) term).getArgs();

            if( args.size() != 2 )
            {
                throw new PrologException( "Assert must have a tuple with 2 args: " + term );
            }

            Expression head = (Expression) args.getTerms().get( 0 );
            Expression tail = (Expression) args.getTerms().get( 1 );

            if( !head.isTerm() )
            {
                throw new PrologException( "Assert must have a Term as the head: " + term );
            }

            db.assertz( (Term) head, tail );
        }
        else
        {
            throw new PrologException( "Assert must have a tuple: " + term );
        }
    }

}
