package com.rocketnia.hacks.myregex.regex;

import com.rocketnia.hacks.myregex.Regex;
import com.rocketnia.hacks.myregex.RegexContext;
import com.rocketnia.hacks.myregex.RegexIterable;
import com.rocketnia.hacks.myregex.RegexIterator;
import com.rocketnia.hacks.myregex.RegexMatch;
import com.rocketnia.util.Integrity;

public class RegexConcatenation implements Regex
{
    private Regex first;
    private Regex second;

    public RegexConcatenation( Regex first, Regex second )
    {
        this.first = first;
        this.second = second;
    }

    public RegexConcatenation( Regex... innerRegexes )
    {
        switch ( innerRegexes.length )
        {
        case 0:
            first   = new RegexEmpty();
            second  = new RegexEmpty();
            break;
        case 1:
            first   = innerRegexes[ 0 ];
            second  = new RegexEmpty();
            break;
        case 2:
            first   = innerRegexes[ 0 ];
            second  = innerRegexes[ 1 ];
            break;
        default:
            Regex[] innerRegexesTail = new Regex[ innerRegexes.length - 1 ];
            System.arraycopy( innerRegexes, 1, innerRegexesTail, 0, innerRegexesTail.length );
            first   = innerRegexes[ 0 ];
            second  = new RegexConcatenation( innerRegexesTail );
            break;
        }
    }

    public boolean equals( Object o )
    {
        if ( !(
               (o != null)
            && (o instanceof RegexConcatenation)
        ) )
            return false;

        RegexConcatenation other = (RegexConcatenation)( o );

        return (
               other.first   .equals( first   )
            && other.second  .equals( second  )
        );
    }

    public RegexConcatenation reversed()
    {
        return new RegexConcatenation( second.reversed(), first.reversed() );
    }

    public RegexIterable matches( final String subject, final int position, final RegexContext context )
    {
        Integrity.assertNotNull( subject );

        final Regex self = this;

        return new RegexIterable()
        {
            private RegexMatch firstMatch = null;
            private RegexIterator firstIterator = null;
            private RegexIterator secondIterator = null;

            protected RegexMatch getFirstMatch()
            {
                firstIterator = first.matches( subject, position, context ).iterator();

                if ( !firstIterator.hasNext() )
                    return null;

                firstMatch = firstIterator.next();

                secondIterator = second.matches( subject, firstMatch.getEndPosition(), firstMatch.getContext() ).iterator();

                return getNextMatch( null );
            }

            protected RegexMatch getNextMatch( RegexMatch currentMatch )
            {
                while ( true )
                {
                    while ( secondIterator.hasNext() )
                    {
                        RegexMatch secondMatch = secondIterator.next();

                        return new RegexMatch( self, subject, position, firstMatch.getLength() + secondMatch.getLength(), secondMatch.getContext() );
                    }

                    if ( !firstIterator.hasNext() )
                        return null;

                    firstMatch = firstIterator.next();

                    secondIterator = second.matches( subject, firstMatch.getEndPosition(), firstMatch.getContext() ).iterator();
                }
            }
        };
    }
}