/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 * Created on 16-Mar-2003
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/predicate/CutAtom.java,v 1.1 2004/01/25 05:48:12 simon Exp $
 *******************************************************************************
 *  $Log: CutAtom.java,v $
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

import java.util.Collections;
import java.util.List;

import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.expression.Variable;

/**
 * @author Simon
 */
public class CutAtom extends GroundLiteral
{
    /**
     * @param database
     */
    public CutAtom( Database database )
    {
        super( database, "!", 0 );
    }

    /**
     * @param goal
     * @return
     * @see com.kajabity.prolog.core.environment.GroundLiteral#execute(Goal)
     */
    protected List<Variable> execute( Goal goal )
    {
        goal.cut();

        return Collections.emptyList();
    }
}
