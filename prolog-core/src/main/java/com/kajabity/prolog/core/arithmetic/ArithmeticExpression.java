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

/*
 * Created on 28-Nov-2003
 */
package com.kajabity.prolog.core.arithmetic;

import com.kajabity.prolog.core.environment.PrologContext;
import com.kajabity.prolog.core.expression.Atom;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.NumericConstant;
import com.kajabity.prolog.core.expression.Tuple;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Stack;


/**
 * Class used to evaluate prolog expressions as arithmetic ones. It converts
 * the expression to Reverse Polar Notation on a Stack and then evaluates the
 * expressions. If any of the functors (or operators) does not have an
 * appropriate evaluator (IEvaluable) an exception is thrown.
 *
 * @author simon
 */
public class ArithmeticExpression {
    private final static Logger logger = Logger
            .getLogger(ArithmeticExpression.class);

    /**
     * The expression to be evaluated as an arithmetic expression.
     */
    private final Expression expr;


    /**
     * Construct an arithmetic expression solver for an expression tree.
     *
     * @param expression The expression to be evaluated.
     */
    public ArithmeticExpression(Expression expression) {
        this.expr = expression;
        logger.debug("Constructed ArithmeticExpression for: " + expression);
    }


    /**
     * Evaluate the arithmetic expression to produce a numeric answer.
     *
     * @param database evaluate in the context of this prolog Database.
     * @return the result of evaluating the arithmetic expression.
     * @throws PrologArithmeticException
     */
    public NumericConstant eval(PrologContext database)
            throws PrologArithmeticException {
        NumericConstant value = null;

        Stack<Expression> exec = toExecutionStack(database);

        if (logger.isDebugEnabled()) {
            logger.debug("Execute: ");

            Iterator<Expression> iExec = exec.iterator();

            while (iExec.hasNext()) {
                logger.debug(iExec.next() + ", ");
            }
        }

        Stack<NumericConstant> values = new Stack<NumericConstant>();
        Function evaluator;

        do {
            Expression ex = exec.pop();
            value = null;

            if (ex instanceof NumericConstant) {
                value = (NumericConstant) ex;
            } else if (ex instanceof Atom) {
                //  Look for name/0 as evaluator (e.g. defined constants like
                // pi).
                evaluator = database.findFunction(((Atom) ex).getName(), 0);

                if (evaluator != null) {
                    value = evaluator.evaluate(values);
                }
            } else if (ex instanceof Tuple) {
                //  Get the name/arity evaluator then add each of the parameters
                //  to the todo stack.
                Tuple s = (Tuple) ex;
                evaluator = database.findFunction(s.getName(), s.getArity());

                if (evaluator != null) {
                    value = evaluator.evaluate(values);
                }
            }

            if (value != null) {
                values.push(value);
            } else {
                throw new PrologArithmeticException(
                        "Invalid Arithmetic Expression: " + ex);
            }
        }
        while (!exec.isEmpty());

        //TODO Don't rely on just returning the last element. Review.
        return value;
    }


    /**
     * Convert the internal expression to be a Reverse Polar Notation execution
     * stack ready for evaluation. Each node in the expression checked for an
     * evaluator - either on the expression itself or from the database.
     *
     * @param database DOCUMENT ME!
     * @return
     */
    private Stack<Expression> toExecutionStack(PrologContext database) {
        Stack<Expression> exec = new Stack<Expression>();
        Stack<Expression> todo = new Stack<Expression>();

        // Seed the conversion with the top level expression.
        todo.push(expr.getFinalInstantiation());

        while (!todo.isEmpty()) {
            Expression ex = todo.pop();
            exec.push(ex);

            if (ex instanceof Tuple) {
                Tuple tuple = (Tuple) ex;
                Iterator<Expression> iArgs = tuple.getArgs().getTerms().iterator();

                while (iArgs.hasNext()) {
                    todo.push(iArgs.next()
                            .getFinalInstantiation());
                }
            }
        }

        return exec;
    }
}
