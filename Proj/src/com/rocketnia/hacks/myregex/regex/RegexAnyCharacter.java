package com.rocketnia.hacks.myregex.regex;

import com.rocketnia.hacks.myregex.Regex;
import com.rocketnia.hacks.myregex.RegexContext;
import com.rocketnia.hacks.myregex.RegexIterable;
import com.rocketnia.hacks.myregex.RegexMatch;
import com.rocketnia.util.Integrity;

public class RegexAnyCharacter implements Regex
{
    public RegexAnyCharacter() {}

    public boolean equals( Object o )
    {
        if ( !(
               (o != null)
            && (o instanceof RegexAnyCharacter)
        ) )
            return false;

        return true;
    }

    public Regex reversed()
    {
        return new RegexAnyCharacter();
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
                     &&      (position < subject.length())
                )
                     return new RegexMatch( self, subject, position, 1, context );

                 return null;
            }

            protected RegexMatch getNextMatch( RegexMatch currentMatch )
            {
                return null;
            }
        };
    }
}