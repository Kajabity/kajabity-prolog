/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 * Created on 16-Mar-2003
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/predicate/TrueAtom.java,v 1.1 2004/01/25 05:48:12 simon Exp $
 *******************************************************************************
 *  $Log: TrueAtom.java,v $
 *  Revision 1.1  2004/01/25 05:48:12  simon
 *  Moved into predicate package and other updates.
 *
 *  Revision 1.5  2004/01/15 07:07:54  simon
 *  Adding support for arithmetic.
 *
 *  Revision 1.4  2003/11/20 04:45:58  simon
 *  Implementing PrologInput/OutputStreams
 *  New "write( Term )" builtin.
 *
 *  Revision 1.3  2003/07/07 03:39:20  simon
 *  List handling improved.  First built-ins working.  Cut works!
 *
 *  Revision 1.2  2003/06/28 05:38:35  simon
 *  UPdate during adding of 'op' builtin.
 *
 *******************************************************************************
 */
package uk.co.williams_technologies.prolog.builtin.predicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.expression.Variable;
import com.kajabity.prolog.io.format.DisplayPrologFormat;
import com.kajabity.prolog.io.format.PrologFormat;

/**
 * @author Simon
 */
public class DebugShowGoalTreeAtom extends GroundLiteral
{
    public DebugShowGoalTreeAtom( Database database )
    {
        super( database, "debug_show_goal_tree", 0 );
    }

    /**
     * @see com.kajabity.prolog.core.environment.GroundLiteral#execute(com.kajabity.prolog.core.environment.PrologDatabase,
     *      com.kajabity.prolog.io.solver.Goal)
     */
    protected List<Variable> execute( Goal goal )
    {
        List<String> lines = new ArrayList<String>();
        StringBuffer line = new StringBuffer();

        PrologFormat format = new DisplayPrologFormat();
        Goal g = goal;
        while( g != null )
        {
            line.insert( 0, format.format( g.getTerm() ) );

            if( g.getPrevSibling() != null )
            {
                g = (Goal) g.getPrevSibling();
                line.insert( 0, ", " );
            }
            else
            {
                lines.add( 0, line.toString() );
                line.setLength( 0 );

                g = (Goal) g.getParent();
            }
        }

        Iterator<String> iLine = lines.iterator();
        while( iLine.hasNext() )
        {
            database.getCurrentOutputStream().println( line.toString() + iLine.next() );
            line.append( ". " );
        }

        return Collections.emptyList();
    }
}
