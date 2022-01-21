package com.rocketnia.hacks.myregex.old;
/*

import com.rocketnia.hacks.myregex.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import com.rocketnia.util.Integrity;

public class RegexMacroInstance implements Regex
{
    private String name;

    public RegexMacroInstance( String name )
    {
        this.name = name;
    }

    public boolean equals( Object o )
    {
        if ( !(
               (o != null)
            && (o instanceof RegexMacroInstance)
        ) )
            return false;

        RegexMacroInstance other = (RegexMacroInstance)( o );

        return other.name.equals( name );
    }

    public RegexMacroInstance reversed()
    {
        return new RegexMacroInstance( name );
    }

    public RegexIterable matches( final String subject, final int position, final RegexContext context )
    {
        Integrity.assertNotNull( subject );

        final Regex self = this;

        return new RegexIterable()
        {
            private List< Regex > definitions = null;
            private Deque< Regex > unsatisfiedDefinitions = null;

            private Deque< RegexMatch > innerMatches = null;
            private Deque< RegexIterator > iterators = null;

            private boolean futuresExplored = false;
            private boolean complete = false;

            protected RegexMatch getFirstMatch()
            {
                definitions = new ArrayList< Regex >( context.getMacroDefinitions( name ) );

                definitions.add( new RegexGreedyClosure( new RegexAnyCharacter() ) );

                unsatisfiedDefinitions = new ArrayDeque< Regex >( definitions );

                innerMatches = new ArrayDeque< RegexMatch >();
                iterators = new LinkedList< RegexIterator >();

                return getNextMatch( null );
            }

            protected RegexMatch getNextMatch( RegexMatch currentMatch )
            {
                if ( complete )
                    return null;

                if ( !futuresExplored )
                {
                    while ( true )
                    {
                        if ( unsatisfiedDefinitions.isEmpty() )
                            break;

                        RegexIterator anotherIterator = unsatisfiedDefinitions.getFirst().matches( subject, position, context ).iterator();

                        RegexMatch anotherMatch = getNextWithOkayEndPositionFrom( anotherIterator );

                        if ( anotherMatch == null )
                            break;

                        unsatisfiedDefinitions.removeFirst();

                        innerMatches.push( anotherMatch );
                        iterators.push( anotherIterator );
                    }
                }

                RegexMatch nextMatch = new RegexMatch( self, subject, position, getCurrentEndPosition() - position, innerMatches );

                nextMatch = nextMatch.withMacroMatch( name, nextMatch );

                innerMatches.pop();

                RegexIterator topIterator = iterators.peek();

                RegexMatch nextTopMatch = getNextWithOkayEndPositionFrom( topIterator );

                if ( nextTopMatch == null )
                {
                    iterators.pop();
                    unsatisfiedDefinitions.addFirst( definitions.get( definitions.size() - unsatisfiedDefinitions.size() - 1 ) );

                    futuresExplored = true;

                    if ( iterators.size() == 0 )
                        complete = true;
                }
                else
                {
                    innerMatches.push( nextTopMatch );

                    futuresExplored = false;
                }

                return nextMatch;
            }

            private RegexMatch getNextWithOkayEndPositionFrom( RegexIterator iterator )
            {
                while ( iterator.hasNext() )
                {
                	RegexMatch match = iterator.next();

                    if ( isOkayEndPosition( match.getEndPosition() ) )
                        return match;
                }

                return null;
            }

            private boolean isOkayEndPosition( int testEndPosition )
            {
                if ( innerMatches.isEmpty() )
                    return true;

                return ( testEndPosition == innerMatches.peek().getEndPosition() );
            }

            private int getCurrentEndPosition()
            {
                if ( innerMatches.isEmpty() )
                    return position;

                return innerMatches.peek().getEndPosition();
            }
        };
    }
}

*/