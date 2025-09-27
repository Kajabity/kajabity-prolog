/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 * Created on 16-Mar-2003
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/InitialisePredicateBuiltins.java,v 1.2 2004/05/05 05:28:38 simon Exp $
 *******************************************************************************
 *  $Log: InitialisePredicateBuiltins.java,v $
 *  Revision 1.2  2004/05/05 05:28:38  simon
 *  Lots of changes...
 *
 *  Revision 1.1  2004/02/17 08:35:44  simon
 *  Refactoring and adding Processor classes.
 *
 *  Revision 1.7  2004/01/25 05:49:24  simon
 *  Added more built-ins (consult, put, tag, ...).
 *
 *  Revision 1.6  2004/01/15 07:07:54  simon
 *  Adding support for arithmetic.
 *
 *  Revision 1.5  2003/11/20 04:45:58  simon
 *  Implementing PrologInput/OutputStreams
 *  New "write( Term )" builtin.
 *
 *  Revision 1.4  2003/07/07 03:39:20  simon
 *  List handling improved.  First built-ins working.  Cut works!
 *
 *  Revision 1.3  2003/07/03 03:47:00  simon
 *  Fixes (not complete) to List handling.
 *
 *  Revision 1.2  2003/06/28 05:38:35  simon
 *  UPdate during adding of 'op' builtin.
 *
 *******************************************************************************
 */
package uk.co.williams_technologies.prolog.builtin;

import com.kajabity.prolog.core.environment.Database;

import uk.co.williams_technologies.prolog.builtin.predicate.ArithEqualPredicate;
import uk.co.williams_technologies.prolog.builtin.predicate.ArithGreaterThanEqualPredicate;
import uk.co.williams_technologies.prolog.builtin.predicate.ArithGreaterThanPredicate;
import uk.co.williams_technologies.prolog.builtin.predicate.ArithLessThanEqualPredicate;
import uk.co.williams_technologies.prolog.builtin.predicate.ArithLessThanPredicate;
import uk.co.williams_technologies.prolog.builtin.predicate.ArithNotEqualPredicate;
import uk.co.williams_technologies.prolog.builtin.predicate.ConsultPredicate;
import uk.co.williams_technologies.prolog.builtin.predicate.CutAtom;
import uk.co.williams_technologies.prolog.builtin.predicate.DebugShowGoalTreeAtom;
import uk.co.williams_technologies.prolog.builtin.predicate.EqualSamePredicate;
import uk.co.williams_technologies.prolog.builtin.predicate.FailAtom;
import uk.co.williams_technologies.prolog.builtin.predicate.IsPredicate;
import uk.co.williams_technologies.prolog.builtin.predicate.Op3Predicate;
import uk.co.williams_technologies.prolog.builtin.predicate.PutPredicate;
import uk.co.williams_technologies.prolog.builtin.predicate.Read1Predicate;
import uk.co.williams_technologies.prolog.builtin.predicate.TagPredicate;
import uk.co.williams_technologies.prolog.builtin.predicate.TrueAtom;
import uk.co.williams_technologies.prolog.builtin.predicate.WritePredicate;

/**
 * @author Simon
 */
public class InitialisePredicateBuiltins
{
    public static void initialise( Database database )
    {
        database.add( new Op3Predicate( database ) );

        database.add( new CutAtom( database ) );
        database.add( new FailAtom( database ) );
        database.add( new TrueAtom( database ) );

        //  Term tests.
        database.add( new EqualSamePredicate( database ) );
        database.add( new TagPredicate( database ) );

        database.add( new Read1Predicate( database ) );
        database.add( new PutPredicate( database ) );
        database.add( new WritePredicate( database ) );

        database.add( new ConsultPredicate( database ) );

        //  Arithmetic Evaluation
        database.add( new IsPredicate( database ) );

        //  Arithmetic Comparisons
        database.add( new ArithEqualPredicate( database ) );
        database.add( new ArithGreaterThanEqualPredicate( database ) );
        database.add( new ArithGreaterThanPredicate( database ) );
        database.add( new ArithLessThanEqualPredicate( database ) );
        database.add( new ArithLessThanPredicate( database ) );
        database.add( new ArithNotEqualPredicate( database ) );

        //  Debug predicates
        database.add( new DebugShowGoalTreeAtom( database ) );
    }
}
