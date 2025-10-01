/*
 * Copyright (c) 2004-2025 Simon J. Williams
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

/*
 * Created on Mar 24, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.kajabity.prolog.core.expression;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Simon
 */
public class ExpressionsTest extends TestCase {
    Expression a1 = Atom.find("a");
    Expression a2 = Atom.find("a");
    Expression a3 = Atom.find("b");

    Expression n1 = new NumericConstant(23);
    Expression n2 = new NumericConstant(23);
    Expression n3 = new NumericConstant(66);

    Expression v1 = new Variable("A");
    Expression v2 = new Variable("B");
    Expression v3 = new Variable("C");

    Expression cv1 = new Variable("D");
    Expression cv2 = new Variable("E");
    Expression cv3 = new Variable("F");
    Expression cv1b = new Variable("D");

    Expression av1 = new Variable("_");
    Expression av2 = new Variable("_G");
    Expression av3 = new Variable("_H");
    Expression t1 = new Tuple(Atom.find("f"), new TermList(a1, cv1));
    Expression t4 = new Tuple(Atom.find(":-"), new TermList(new Expression[]{t1, new TermList(new Expression[]{a1, cv1, cv2})}));
    Expression t2 = new Tuple(Atom.find("g"), new TermList(a2, cv2));
    Expression t3 = new Tuple(Atom.find("h"), new TermList(new Expression[]{a1, cv1, cv2}));
    Expression t5 = new Tuple(Atom.find("f"), new TermList(av1, cv3));

    public void testUnifyConstants() {
        //	Same atom
        List<Variable> subs = Expressions.unify(a1, a2);
        assertNotNull(subs);
        assertEquals(0, subs.size());
        Expressions.undoSubstitutions(subs);

        //	Different atom
        subs = Expressions.unify(a1, a3);
        assertNull(subs);
        Expressions.undoSubstitutions(subs);

        //	Same number
        subs = Expressions.unify(n1, n2);
        assertNotNull(subs);
        assertEquals(0, subs.size());
        Expressions.undoSubstitutions(subs);

        //	Different number
        subs = Expressions.unify(n1, n3);
        assertNull(subs);
        Expressions.undoSubstitutions(subs);
    }

    public void testUnifyVars() {
        //	var/atom
        List<Variable> subs = Expressions.unify(v1, a2);
        assertNotNull(subs);
        assertEquals(1, subs.size());
        Expressions.undoSubstitutions(subs);

        //	atom/var
        subs = Expressions.unify(a1, v3);
        assertNotNull(subs);
        assertEquals(1, subs.size());
        Expressions.undoSubstitutions(subs);

        //	var/var
        subs = Expressions.unify(v1, v2);
        assertNotNull(subs);
        assertEquals(1, subs.size());
        Expressions.undoSubstitutions(subs);
    }

    public void testContains() {
        assertTrue(t1.containsVariable((Variable) cv1));
        assertTrue(t2.containsVariable((Variable) cv2));
        assertTrue(t3.containsVariable((Variable) cv1));

        assertFalse(t1.containsVariable((Variable) cv2));
        assertFalse(t2.containsVariable((Variable) cv3));
    }

    public void testUnifyContains() {
        //	different vars - ok
        List<Variable> subs = Expressions.unify(v1, t1);
        assertNotNull(subs);
        assertEquals(1, subs.size());
        Expressions.undoSubstitutions(subs);

        //	var/tuple - contained
        subs = Expressions.unify(cv1, t1);
        assertNull(subs);
        Expressions.undoSubstitutions(subs);

        //	tuple/var - contained
        subs = Expressions.unify(t2, cv2);
        assertNull(subs);
        Expressions.undoSubstitutions(subs);

        //	var/tuple - contained but different instance of cv.
        subs = Expressions.unify(cv1b, t1);
        assertNotNull(subs);
        assertEquals(1, subs.size());
        Expressions.undoSubstitutions(subs);

        //  A more complex one.
        subs = Expressions.unify(t4, t5);
    }

    public void testMakeCopy() {
        Map<String, Variable> variables = new HashMap<String, Variable>();
        Expression cpy = v1.makeCopy(variables);
        assertFalse(cpy.equals(v1));

        cpy = t1.makeCopy(variables);
        assertFalse(cpy.containsVariable((Variable) cv1));
    }
}
