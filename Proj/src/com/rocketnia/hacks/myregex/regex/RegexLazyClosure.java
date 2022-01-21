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

public class RegexLazyClosure implements Regex
{
    private Regex innerRegex;

    public RegexLazyClosure( Regex innerRegex )
    {
        this.innerRegex = innerRegex;
    }

    public boolean equals( Object o )
    {
        if ( !(
               (o != null)
            && (o instanceof RegexLazyClosure)
        ) )
            return false;

        RegexLazyClosure other = (RegexLazyClosure)( o );

        return other.innerRegex.equals( innerRegex );
    }

    public RegexLazyClosure reversed()
    {
        return new RegexLazyClosure( innerRegex.reversed() );
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
                innerIterator = new RegexAlternation( new RegexEmpty(), new RegexConcatenation( innerRegex, new RegexLazyClosure( innerRegex ) ) ).matches( subject, position, context ).iterator();

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
            private Deque< RegexMatch > innerMatches = null;
            private Deque< RegexIterator > iterators = null;

            protected RegexMatch getFirstMatch()
            {
                innerMatches = new ArrayDeque< RegexMatch >();
                iterators = new LinkedList< RegexIterator >();

                innerMatches.push( new RegexMatch( self, subject, position, 0, context ) );

                return innerMatches.peek();
            }

            protected RegexMatch getNextMatch( RegexMatch currentMatch )
            {
                if ( innerMatches.isEmpty() )
                    return null;

                iterators.push( innerRegex.matches( subject, innerMatches.peek().getEndPosition(), innerMatches.peek().getContext() ).iterator() );

                RegexIterator topIterator = null;
                RegexMatch anotherMatch = null;

                while ( anotherMatch == null )
                {
                    topIterator = iterators.pop();
                    innerMatches.pop();

                    anotherMatch = getNextTotalMatchThatWorks( topIterator );
                }

                innerMatches.push( anotherMatch );
                iterators.push( topIterator );

                return anotherMatch;
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
