package com.rocketnia.hacks.myregex.regex;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

import com.rocketnia.hacks.myregex.Regex;
import com.rocketnia.hacks.myregex.RegexContext;
import com.rocketnia.hacks.myregex.RegexIterable;
import com.rocketnia.hacks.myregex.RegexIterator;
import com.rocketnia.hacks.myregex.RegexMatch;
import com.rocketnia.util.Integrity;
import com.rocketnia.util.Tools;

public class RegexAlternation implements Regex
{
    private List< Regex > innerRegexes;

    public RegexAlternation( Regex... innerRegexes )
    {
        this.innerRegexes = new ArrayList< Regex >();

        for ( Regex innerRegex: innerRegexes )
        {
            add( innerRegex );
        }
    }

    private RegexAlternation( Collection< Regex > innerRegexes )
    {
        this.innerRegexes = new ArrayList< Regex >();

        for ( Regex innerRegex: innerRegexes )
        {
            add( innerRegex );
        }
    }

    private void add( Regex innerRegex )
    {
        if ( innerRegex instanceof RegexAlternation )
        {
            for ( Regex otherInnerRegex: ((RegexAlternation)( innerRegex )).innerRegexes )
            {
                add( otherInnerRegex );
            }
        }
        else
        {
        	innerRegexes.add( innerRegex );
        }
    }

    public boolean equals( Object o )
    {
        if ( !(
               (o != null)
            && (o instanceof RegexAlternation)
        ) )
            return false;

        RegexAlternation other = (RegexAlternation)( o );

        return other.innerRegexes.equals( innerRegexes );
    }

    public RegexAlternation reversed()
    {
        return new RegexAlternation( Tools.reverse( innerRegexes ) );
    }

    public RegexIterable matches( final String subject, final int position, final RegexContext context )
    {
        Integrity.assertNotNull( subject );

        final Regex self = this;

        return new RegexIterable()
        {
            private Deque< RegexIterator > innerIterators = null;

            protected RegexMatch getFirstMatch()
            {
                innerIterators = new ArrayDeque< RegexIterator >();

                for ( Regex innerRegex: innerRegexes )
                {
                    innerIterators.addLast( innerRegex.matches( subject, position, context ).iterator() );
                }

                return getNextMatch( null );
            }

            protected RegexMatch getNextMatch( RegexMatch currentMatch )
            {
                while ( true )
                {
                    if ( innerIterators.isEmpty() )
                        return null;

                    if ( innerIterators.peekFirst().hasNext() )
                    	return new RegexMatch( self, innerIterators.peekFirst().next() );

                    innerIterators.pollFirst();
                }
            }
        };
    }
}