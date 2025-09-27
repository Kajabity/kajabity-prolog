/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/predicate/TagPredicate.java,v 1.1 2004/01/25 05:46:48 simon Exp $
 *******************************************************************************
 *  $Log: TagPredicate.java,v $
 *  Revision 1.1  2004/01/25 05:46:48  simon
 *  Added to enable term type checking.
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

/**
 * Created on 16-Mar-2003 To change this generated comment edit the template
 * variable "filecomment": Window>Preferences>Java>Templates. To enable and
 * disable the creation of file comments go to Window>Preferences>Java>Code
 * Generation.
 */
package uk.co.williams_technologies.prolog.builtin.predicate;

import java.util.Iterator;
import java.util.List;

import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Expressions;
import com.kajabity.prolog.core.expression.NumericConstant;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.core.expression.Variable;


/**
 * @author Simon To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class TagPredicate extends GroundLiteral
{
    public TagPredicate( Database database )
    {
        super( database, "tag", 2 );
    }


    /*
     * (non-Javadoc)
     *
     * @see com.kajabity.prolog.core.environment.GroundLiteral#execute(com.kajabity.prolog.core.environment.PrologDatabase,
     *      com.kajabity.prolog.io.solver.Goal)
     */
    protected List<Variable> execute( Goal goal )
    {
        if( goal.getTerm() instanceof Tuple )
        {
            Tuple tuple = (Tuple) goal.getTerm();
            List<Expression> args = tuple.getArgs().getTerms();

            Iterator<Expression> iargs = args.iterator();
            Expression left = null;
            Expression right = null;

            if( iargs.hasNext() )
            {
                left = (Expression) iargs.next();

                if( iargs.hasNext() )
                {
                    right = (Expression) iargs.next();

                    NumericConstant result = new NumericConstant( ((Term) left.getFinalInstantiation()).getTag() );

                    return Expressions.unify( right, result );
                }
            }
        }

        return null;
    }
}
