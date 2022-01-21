package com.rocketnia.hacks.myregex.regex;

import com.rocketnia.hacks.myregex.Regex;
import com.rocketnia.hacks.myregex.RegexContext;
import com.rocketnia.hacks.myregex.RegexIterable;
import com.rocketnia.hacks.myregex.RegexIterator;
import com.rocketnia.hacks.myregex.RegexMatch;
import com.rocketnia.util.Integrity;

// TODO: Figure out whether this class is strictly necessary or whether it can be tamely implemented as RegexNegation( RegexNegation( innerRegex ) ). It used to be implemented that way, but that caused infinite looping problems just creating the regex object when RegexNegation and RegexLookbehind were used together.  

public class RegexLookahead implements Regex
{
    private Regex innerRegex;

    public RegexLookahead( Regex innerRegex )
    {
        this.innerRegex = innerRegex;
    }

    public boolean equals( Object o )
    {
        if ( !(
               (o != null)
            && (o instanceof RegexLookahead)
        ) )
            return false;

        RegexLookahead other = (RegexLookahead)( o );

        return other.innerRegex.equals( innerRegex );
    }

    public Regex reversed()
    {
        return new RegexLookbehind( innerRegex.reversed() );
    }

    public RegexIterable matches( final String subject, final int position, final RegexContext context )
    {
        Integrity.assertNotNull( subject );

        final Regex self = this;

        return new RegexIterable()
        {
            private RegexIterator innerIterator = null;

            protected RegexMatch getFirstMatch()
            {
                innerIterator = innerRegex.matches( subject, position, context ).iterator();

                return getNextMatch( null );
            }

            protected RegexMatch getNextMatch( RegexMatch currentMatch )
            {
                if ( innerIterator.hasNext() )
                    return new RegexMatch( self, subject, position, 0, innerIterator.next().getContext() );

                return null;
           }
        };
    }
}
