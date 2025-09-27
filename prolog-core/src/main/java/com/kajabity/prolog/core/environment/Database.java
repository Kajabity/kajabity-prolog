/*
 *******************************************************************************
 *  Copyright   :   (c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_Environment
 *
 *  Created     :   Mar 24, 2004 by Simon J. Williams
 *
 *  $Header: /home/cvs/cvs/Prolog/Environment/src/uk/co/williams_technologies/prolog/environment/Database.java,v 1.2 2004/07/01 05:31:52 simon Exp $
 *******************************************************************************
 *  $Log: Database.java,v $
 *  Revision 1.2  2004/07/01 05:31:52  simon
 *  Major update after many changes.
 *
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *******************************************************************************
 */
package com.kajabity.prolog.core.environment;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.kajabity.prolog.core.arithmetic.Function;
import com.kajabity.prolog.core.environment.operator.Operator;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;

/**
 * DOCUMENT ME!
 *
 * @author Simon To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Database implements PrologContext
{
    private final static Logger logger                     = Logger.getLogger( Database.class );

    // ========================================================================
    // operators
    Map<String, Operator>                         operators                  = new TreeMap<String, Operator>();

    // relations
    Map<String, Relation>                         relations                  = new TreeMap<String, Relation>();

    // assertions
    // built-ins
    // processors
    Map<String, Processor>                         processors                 = new TreeMap<String, Processor>();

    // functions
    Map<String, Function>                         functions                  = new TreeMap<String, Function>();

    /** Input streams mapped by their atom name. */
    Map<String, InputStream>                         inputStreams               = new HashMap<String, InputStream>();

    /** Output streams mapped by their atom name. */
    Map<String, PrintStream>                         outputStreams              = new HashMap<String, PrintStream>();

    /** Error Output streams mapped by their atom name. */
    Map<String, PrintStream>                         errorStreams               = new HashMap<String, PrintStream>();

    // TODO: Check the default stream names for prolog.
    public final static String  DEFAULT_INPUT_STREAM_NAME  = "_default";

    public final static String  DEFAULT_OUTPUT_STREAM_NAME = "_default";

    public final static String  DEFAULT_ERROR_STREAM_NAME  = "_default";

    InputStream                 currentInputStream;

    PrintStream                 currentOutputStream;

    PrintStream                 currentErrorStream;

    private boolean             terminated                 = false;

    private Processor           defaultProcessor;

    public Database( InputStream in, PrintStream out, PrintStream err )
    {
        inputStreams.put( DEFAULT_INPUT_STREAM_NAME, in );
        currentInputStream = in;

        outputStreams.put( DEFAULT_OUTPUT_STREAM_NAME, out );
        currentOutputStream = out;

        errorStreams.put( DEFAULT_ERROR_STREAM_NAME, err );
        currentErrorStream = err;
    }

    /* (non-Javadoc)
     * @see com.kajabity.prolog.core.environment.PrologContext#asserta(com.kajabity.prolog.core.expression.Term, com.kajabity.prolog.core.expression.Expression)
     */
    public void asserta( Term head, Expression tail ) throws PrologException
    {
        // Find or create the relation...
        String key = head.getKey();
        Relation relation = relations.get( key );
        if( relation == null )
        {
            // Easy: - Create the relation...
            relation = new DynamicRelation( head.getName(), head.getArity() );

            // ... add it to the map...
            add( relation );

            // ... and, add the Clause.
            Clause clause = new Clause( head, tail );
            relation.add( clause );
        }
        else
        {
            // We can only add a clause if it is dynamic?
            if( relation.isDynamic() )
            {
                // Add the clause.
                Clause clause = new Clause( head, tail );
                relation.insert( 0, clause );

                logger.debug( "Added relation " + key );
            }
            else
            {
                // Whoops! Can't add to this relation.
                throw new PrologException( "Attempt to add to Static Relation: " + key );
            }
        }

    }

    /* (non-Javadoc)
     * @see com.kajabity.prolog.core.environment.PrologContext#assertz(com.kajabity.prolog.core.expression.Term, com.kajabity.prolog.core.expression.Expression)
     */
    public void assertz( Term head, Expression tail ) throws PrologException
    {
        // Find or create the relation...
        String key = head.getKey();
        Relation relation = relations.get( key );
        if( relation == null )
        {
            // Easy: - Create the relation...
            relation = new DynamicRelation( head.getName(), head.getArity() );

            // ... add it to the map...
            relations.put( key, relation );

            // ... and, add the Clause.
            Clause clause = new Clause( head, tail );
            relation.add( clause );

            logger.debug( "Added relation " + key );
        }
        else
        {
            // We can only add a clause if it is dynamic?
            if( relation.isDynamic() )
            {
                // Add the clause.
                Clause clause = new Clause( head, tail );
                relation.add( clause );

                logger.debug( "Added relation " + key );
            }
            else
            {
                // Whoops! Can't add to this relation.
                throw new PrologException( "Attempt to add to Static Relation: " + key );
            }
        }

    }

    /**
     * Add a relation to the database using it's key ("name/arity").
     *
     * @param relation
     */
    public void add( Relation relation )
    {
        relations.put( relation.getKey(), relation );

        logger.debug( "Added relation " + relation.getKey() );
    }

    /* (non-Javadoc)
     * @see com.kajabity.prolog.core.environment.PrologContext#findRelation(com.kajabity.prolog.core.expression.Term)
     */
    public Relation findRelation( Term term )
    {
        return relations.get( term.getKey() );
    }

    public Relation findRelation( String name, int arity )
    {
        String key = name + "/" + arity;
        return relations.get( key );
    }

    public void add( Operator operator )
    {
        String key = operator.getName() + "/" + operator.getAssociativity().getPosition();
        operators.put( key, operator );
    }

    /* (non-Javadoc)
     * @see com.kajabity.prolog.core.environment.PrologContext#findOperator(java.lang.String, com.kajabity.prolog.core.environment.Associativity.Position)
     */
    public Operator findOperator( String name, Associativity.Position position )
    {
        String key = name + "/" + position;
        return operators.get( key );
    }

    public void add( Function function ) throws PrologException
    {
    	logger.debug( ":- assert_function( " + function.getName() + " / " + function.getArity() + ", '"
                + function.getClass().getCanonicalName() + "' )." );

        String key = function.getKey();
        if( functions.containsKey( key ) )
        {
            throw new PrologException( "Attempt to add duplicate function " + key );
        }

        functions.put( key, function );
    }

    /* (non-Javadoc)
     * @see com.kajabity.prolog.core.environment.PrologContext#findFunction(java.lang.String, int)
     */
    public Function findFunction( String name, int arity )
    {
        String key = name + "/" + arity;
        return functions.get( key );
    }

    public void add( Processor processor ) throws PrologException
    {
        String key = processor.getKey();
        if( processors.containsKey( key ) )
        {
            throw new PrologException( "Attempt to add duplicate processor " + key );
        }

        processors.put( key, processor );
    }

    /* (non-Javadoc)
     * @see com.kajabity.prolog.core.environment.PrologContext#findProcessor(java.lang.String, int)
     */
    public Processor findProcessor( String name, int arity )
    {
        String key = name + "/" + arity;
        Processor processor = processors.get( key );

        if( processor == null )
        {
            processor = defaultProcessor;
        }

        return processor;
    }

    /* (non-Javadoc)
     * @see com.kajabity.prolog.core.environment.PrologContext#isTerminated()
     */
    public boolean isTerminated()
    {
        return terminated;
    }

    /* (non-Javadoc)
     * @see com.kajabity.prolog.core.environment.PrologContext#setTerminated(boolean)
     */
    public void setTerminated( boolean terminated )
    {
        this.terminated = terminated;
    }

    /**
     * @return Returns the currentErrorStream.
     */
    public PrintStream getCurrentErrorStream()
    {
        return currentErrorStream;
    }

    /**
     * @param currentErrorStream
     *            The currentErrorStream to set.
     */
    // public void setCurrentErrorStream( PrintStream currentErrorStream )
    // {
    // this.currentErrorStream = currentErrorStream;
    // }
    /**
     * @return Returns the currentInputStream.
     */
    public InputStream getCurrentInputStream()
    {
        return currentInputStream;
    }

    /**
     * Add an input stream to the map using the name as the key - then set it to
     * be the current input stream.
     *
     * @param name
     *            the name of the stream - usually the Filename from the atom
     *            used to identify it.
     * @param stream
     *            an InputStream openned on the stream which becomes the current
     *            inpt stream.
     */
    public void addInputStream( String name, InputStream stream )
    {
        inputStreams.put( name, stream );
        currentInputStream = stream;
    }

    /**
     * Remove the named input stream from the map and set the current to be the
     * "_default".
     *
     * @param name
     *            the name of the input stream.
     */
    public void removeInputStream( String name )
    {
        inputStreams.remove( name );
        currentInputStream = inputStreams.get( DEFAULT_INPUT_STREAM_NAME );
    }

    /**
     * @return Returns the currentOutputStream.
     */
    public PrintStream getCurrentOutputStream()
    {
        return currentOutputStream;
    }

    /**
     * @param currentOutputStream
     *            The currentOutputStream to set.
     */
    // public void setCurrentOutputStream( PrintStream currentOutputStream )
    // {
    // this.currentOutputStream = currentOutputStream;
    // }
    /**
     * @return Returns the defaultProcessor.
     */
    public Processor getDefaultProcessor()
    {
        return defaultProcessor;
    }

    /**
     * @param defaultProcessor
     *            The defaultProcessor to set.
     */
    public void setDefaultProcessor( Processor defaultProcessor )
    {
        this.defaultProcessor = defaultProcessor;
    }

    /**
     * @return Returns the functions.
     */
    public Map<String, Function> getFunctions()
    {
        return Collections.unmodifiableMap( functions );
    }

    /**
     * @return Returns the operators.
     */
    public Map<String, Operator> getOperators()
    {
        return Collections.unmodifiableMap( operators );
    }

    /**
     * @return Returns the processors.
     */
    public Map<String, Processor> getProcessors()
    {
        return Collections.unmodifiableMap( processors );
    }

    /**
     * @return Returns the relations.
     */
    public Map<String, Relation> getRelations()
    {
        return Collections.unmodifiableMap( relations );
    }

    /**
     * toString used to help debugging.
     *
     * @see Object#toString()
     */
    public String toString()
    {
        StringBuffer buf = new StringBuffer( "Database( " );

        buf.append( relations.size() );
        buf.append( " relations, " );
        buf.append( operators.size() );
        buf.append( " operators, " );
        buf.append( functions.size() );
        buf.append( " functions, " );
        buf.append( processors.size() );
        buf.append( " processors )" );

        return buf.toString();
    }

}
