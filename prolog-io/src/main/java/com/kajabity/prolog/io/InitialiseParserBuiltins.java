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

package com.kajabity.prolog.io;

import com.kajabity.prolog.builtin.operator.SimpleOperator;
import com.kajabity.prolog.builtin.predicate.*;
import com.kajabity.prolog.builtin.processor.ListDatabaseProcessor;
import com.kajabity.prolog.core.environment.Associativity;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.io.predicate.AssertFunctionPredicate;
import com.kajabity.prolog.io.predicate.AssertGroundLiteralPredicate;
import com.kajabity.prolog.io.predicate.ImmediateProcessor;
import com.kajabity.prolog.io.predicate.SolveProcessor;

public class InitialiseParserBuiltins {
    public static void initialise(Database database) throws PrologException {
        database.add( new ImmediateProcessor( ":-" ) );
        database.add( new SimpleOperator( ":-", Associativity.fx, 1200 ) );

        database.add( new SolveProcessor( "?-" ) );
        database.add( new SimpleOperator( "?-", Associativity.fx, 1200 ) );

        database.add( new AssertGroundLiteralPredicate( database, "assertg", 2 ) );
        database.add( new AssertFunctionPredicate( database, "assert_function", 2 ) );

        database.add( new Read1Predicate(database, "read", 1 ));
        database.add( new PutPredicate(database, "put", 1 ));
        database.add( new WritePredicate(database, "write", 1 ));
        database.add( new ConsultPredicate(database, "consult", 1 ));
        database.add( new ListDatabaseProcessor( "list_db" ) );
        database.add(new DebugShowGoalTreeAtom(database, "debug_show_goal_tree", 0));

        database.add( new SimpleOperator( "/", Associativity.yfx, 400 ) );
    }
}
