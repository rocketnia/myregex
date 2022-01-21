package com.rocketnia.hacks.myregex.regex;

//import java.util.ArrayDeque;
//import java.util.Deque;
//import java.util.LinkedList;

import com.rocketnia.hacks.myregex.Regex;
import com.rocketnia.hacks.myregex.RegexContext;
import com.rocketnia.hacks.myregex.RegexIterable;
import com.rocketnia.hacks.myregex.RegexIterator;
import com.rocketnia.hacks.myregex.RegexMatch;
//import com.rocketnia.hacks.myregex.RegexMultipleMacroDefinitionException;
import com.rocketnia.util.Integrity;

public class RegexGreedyClosure implements Regex
{
    private Regex innerRegex;

    public RegexGreedyClosure( Regex innerRegex )
    {
        this.innerRegex = innerRegex;
    }

    public boolean equals( Object o )
    {
        if ( !(
               (o != null)
            && (o instanceof RegexGreedyClosure)
        ) )
            return false;

        RegexGreedyClosure other = (RegexGreedyClosure)( o );

        return other.innerRegex.equals( innerRegex );
    }

    public RegexGreedyClosure reversed()
    {
        return new RegexGreedyClosure( innerRegex.reversed() );
    }

    public RegexIterable matches( final String subject, final int position, final RegexContext context )
    {
        Integrity.assertNotNull( subject );

        final Regex self = this;

        return new RegexIterable()
        {
            RegexIterator innerIterator;

            protected RegexMatch getFirstMatch()
            {
                innerIterator = new RegexAlternation( new RegexConcatenation( innerRegex, new RegexGreedyClosure( innerRegex ) ), new RegexEmpty() ).matches( subject, position, context ).iterator();

                return getNextMatch( null );
            }

            protected RegexMatch getNextMatch( RegexMatch currentMatch )
            {
                if ( innerIterator.hasNext() )
                    return new RegexMatch( self, innerIterator.next() );

                return null;
            }

// old, buggy version
// TODO: Fix this version up so that it works. It could still be useful, especially when combating Java stack overflow.
/*
            private Deque< RegexMatch     > innerMatches  = null;
            private Deque< RegexIterator  > iterators     = null;

            private boolean futuresExplored = false;

            protected RegexMatch getFirstMatch()
            {
                innerMatches  = new ArrayDeque< RegexMatch     >();
                iterators     = new LinkedList< RegexIterator  >();

                innerMatches.push( new RegexMatch( self, subject, position, 0, context ) );
                iterators.push( new RegexConcatenation( new RegexNegation( new RegexAnyCharacter() ), new RegexAnyCharacter() ).matches( subject, position, context ).iterator() );

                return getNextMatch( null );
            }

            protected RegexMatch getNextMatch( RegexMatch currentMatch )
            {
                if ( innerMatches.isEmpty() )
                    return null;

                if ( !futuresExplored )
                {
                    while ( true )
                    {
                        RegexIterator anotherIterator = innerRegex.matches( subject, innerMatches.peek().getEndPosition(), innerMatches.peek().getContext() ).iterator();

                        RegexMatch anotherMatch = getNextTotalMatchThatWorks( anotherIterator );

                        if ( anotherMatch == null )
                        	break;

                        innerMatches.push( anotherMatch );
                        iterators.push( anotherIterator );
                    }
                }

                RegexMatch nextMatch = innerMatches.pop();

                nextMatch = new RegexMatch( self, subject, position, nextMatch.getLength(), nextMatch.getContext() );

                RegexMatch nextTopMatch = getNextTotalMatchThatWorks( iterators.peek() );

                if ( nextTopMatch == null )
                {
                    iterators.pop();

                    futuresExplored = true;
                }
                else
                {
                    innerMatches.push( nextTopMatch );

                    futuresExplored = false;
                }

                return nextMatch;
            }

            private RegexMatch getNextTotalMatchThatWorks( RegexIterator iterator )
            {
                Integrity.assertState( !innerMatches.isEmpty() );

                while ( iterator.hasNext() )
                {
                    RegexMatch result;
                    try
                    {
                        result = new RegexMatch( self, innerMatches.peek(), iterator.next() );
                    }
                    catch ( RegexMultipleMacroDefinitionException e )
                    {
                        continue;
                    }

                    if (
                           (result.getLength() == 0)
                        && innerMatches.peek().getContext().hasSameDefinitionsAs( result.getContext() )
                    )
                        continue;

                    return result;
                }

                return null;
            }
*/
        };
    }
}