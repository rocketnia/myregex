package com.rocketnia.hacks.myregex.regex;

import com.rocketnia.hacks.myregex.Regex;
import com.rocketnia.hacks.myregex.RegexContext;
import com.rocketnia.hacks.myregex.RegexIterable;
import com.rocketnia.hacks.myregex.RegexMatch;
import com.rocketnia.util.Integrity;

public class RegexNegation implements Regex
{
    private Regex innerRegex;

    public RegexNegation( Regex innerRegex )
    {
        this.innerRegex = innerRegex;
    }

    public boolean equals( Object o )
    {
        if ( !(
               (o != null)
            && (o instanceof RegexNegation)
        ) )
            return false;

        RegexNegation other = (RegexNegation)( o );

        return other.innerRegex.equals( innerRegex );
    }

    public Regex reversed()
    {
        Regex reversedInnerRegex = innerRegex.reversed();

        if ( reversedInnerRegex instanceof RegexNegation )
            return ((RegexNegation)( reversedInnerRegex )).innerRegex;

    	return new RegexNegation( new RegexLookbehind( reversedInnerRegex ) );
    }

    public RegexIterable matches( final String subject, final int position, final RegexContext context )
    {
        Integrity.assertNotNull( subject );

        final Regex self = this;

        return new RegexIterable()
        {
            protected RegexMatch getFirstMatch()
            {
                if ( innerRegex.matches( subject, position, context ).iterator().hasNext() )
                    return null;

                return new RegexMatch( self, subject, position, 0, context );
            }

            protected RegexMatch getNextMatch( RegexMatch currentMatch )
            {
                return null;
            }
        };
    }
}