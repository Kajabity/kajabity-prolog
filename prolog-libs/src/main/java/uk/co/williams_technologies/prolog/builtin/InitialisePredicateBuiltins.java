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
