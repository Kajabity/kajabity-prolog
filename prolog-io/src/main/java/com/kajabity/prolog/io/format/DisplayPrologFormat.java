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
 * Created on May 11, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.kajabity.prolog.io.format;

import java.util.Iterator;
import java.util.List;

import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.TermList;
import com.kajabity.prolog.core.expression.Tuple;




/**
 * Simple Prolog expression formatter.
 *
 * @author Simon J. Williams
 */
public class DisplayPrologFormat extends PrologFormat
{

    /**
     * @see PrologFormat#format(com.kajabity.prolog.core.expression.Expression)
     */
    public String format( Expression expr )
    {
        if( expr == null )
            return "";

        StringBuffer buf = new StringBuffer();
        format( buf, expr.getFinalInstantiation() );

        //buf.append( "." );

        return buf.toString();
    }


    private void format( StringBuffer buf, Expression expr )
    {
        if( expr.isTerm() )
        {
            Term term = (Term) expr;
            switch( term.getTag() )
            {
            case Term.TAG_ATOM: //  Ignore quotable atoms.
            case Term.TAG_INTEGER:
            case Term.TAG_REAL:
            case Term.TAG_VARIABLE:
                buf.append( term.getName() );
                break;

            case Term.TAG_NIL:
                buf.append( "[]" );
                break;

            case Term.TAG_LIST:
                List<Expression> args = ((Tuple) term).getArgs().getTerms();
                Expression left = args.get( 0 );
                Expression right = args.get( 1 );

                if( left.isTerm() && right.isTerm() )
                {
                    buf.append( "[ " );
                    formatList( buf, (Term) left.getFinalInstantiation(),
                            (Term) right.getFinalInstantiation() );
                    buf.append( " ]" );
                    break;
                }
            //  Fall through

            case Term.TAG_TUPLE:
                format( buf, ((Tuple) term).getFunctor()
                        .getFinalInstantiation() );
                buf.append( "( " );
                format( buf, ((Tuple) term).getArgs().getFinalInstantiation() );
                buf.append( " )" );
                break;
            }
        }
        else
        {
            List<Expression> terms = ((TermList) expr).getTerms();
            Iterator<Expression> iTerms = terms.iterator();
            while( iTerms.hasNext() )
            {
                Expression element = iTerms.next();
                format( buf, element.getFinalInstantiation() );

                if( iTerms.hasNext() )
                {
                    buf.append( ", " );
                }
            }
        }
    }


    private void formatList( StringBuffer buf, Term left, Term right )
    {
        format( buf, left );

        if( right.getTag() == Term.TAG_NIL )
        {
            //  End of list - return.
            return;
        }

        if( right.getTag() == Term.TAG_LIST )
        {
            List<Expression> args = ((Tuple) right).getArgs().getTerms();
            if( args.size() == 2 )
            {
                Expression left2 = args.get( 0 );
                Expression right2 = args.get( 1 );

                if( left2.isTerm() && right2.isTerm() )
                {
                    buf.append( ", " );
                    formatList( buf, (Term) left2.getFinalInstantiation(), (Term) right2.getFinalInstantiation() );
                    return;
                }
            }
        }

        buf.append( " | " );
        format( buf, right.getFinalInstantiation() );
    }
}
