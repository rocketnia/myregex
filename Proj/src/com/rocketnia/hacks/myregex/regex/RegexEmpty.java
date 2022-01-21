package com.rocketnia.hacks.myregex.regex;

import com.rocketnia.hacks.myregex.Regex;
import com.rocketnia.hacks.myregex.RegexContext;
import com.rocketnia.hacks.myregex.RegexIterable;
import com.rocketnia.hacks.myregex.RegexMatch;
import com.rocketnia.util.Integrity;

// TODO: Figure out whether it would be better to implement uses of this class as something like RegexNegation( RegexConcatenation( RegexNegation( RegexAnyCharacter() ), RegexAnyCharacter() ) ).  

public class RegexEmpty implements Regex
{
    public RegexEmpty() {}

    public boolean equals( Object o )
    {
        if ( !(
               (o != null)
            && (o instanceof RegexEmpty)
        ) )
            return false;

        return true;
    }

    public Regex reversed()
    {
        return new RegexEmpty();
    }

    public RegexIterable matches( final String subject, final int position, final RegexContext context )
    {
        Integrity.assertNotNull( subject );

        final Regex self = this;

        return new RegexIterable()
        {
            protected RegexMatch getFirstMatch()
            {
                if (
                        (0 <= position)
                     &&      (position <= subject.length())
                )
                     return new RegexMatch( self, subject, position, 0, context );

                 return null;
            }

            protected RegexMatch getNextMatch( RegexMatch currentMatch )
            {
                return null;
            }
        };
    }
}