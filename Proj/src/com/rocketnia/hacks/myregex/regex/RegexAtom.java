package com.rocketnia.hacks.myregex.regex;

import com.rocketnia.hacks.myregex.Regex;
import com.rocketnia.hacks.myregex.RegexContext;
import com.rocketnia.hacks.myregex.RegexIterable;
import com.rocketnia.hacks.myregex.RegexMatch;
import com.rocketnia.util.Integrity;

public class RegexAtom implements Regex
{
    private Regex innerRegex;

    public RegexAtom( Regex innerRegex )
    {
        this.innerRegex = innerRegex;
    }

    public boolean equals( Object o )
    {
        if ( !(
               (o != null)
            && (o instanceof RegexAtom)
        ) )
            return false;

        RegexAtom other = (RegexAtom)( o );

        return other.innerRegex.equals( innerRegex );
    }

    public RegexAtom reversed()
    {
        return new RegexAtom( innerRegex.reversed() );
    }

    public RegexIterable matches( final String subject, final int position, final RegexContext context )
    {
        Integrity.assertNotNull( subject );

        final Regex self = this;

        return new RegexIterable()
        {
            protected RegexMatch getFirstMatch()
            {
                for ( RegexMatch match: innerRegex.matches( subject, position, context ) )
                {
                    return new RegexMatch( self, match );
                }

                return null;
            }

            protected RegexMatch getNextMatch( RegexMatch currentMatch )
            {
                return null;
            }
        };
    }
}