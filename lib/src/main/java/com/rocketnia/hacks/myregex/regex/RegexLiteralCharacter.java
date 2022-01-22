package com.rocketnia.hacks.myregex.regex;

import com.rocketnia.hacks.myregex.Regex;
import com.rocketnia.hacks.myregex.RegexContext;
import com.rocketnia.hacks.myregex.RegexIterable;
import com.rocketnia.hacks.myregex.RegexMatch;
import com.rocketnia.util.Integrity;

public class RegexLiteralCharacter implements Regex
{
    private Character character;

    public RegexLiteralCharacter( Character character )
    {
        this.character = character;
    }

    public boolean equals( Object o )
    {
        if ( !(
               (o != null)
            && (o instanceof RegexLiteralCharacter)
        ) )
            return false;

        RegexLiteralCharacter other = (RegexLiteralCharacter)( o );

        return other.character.equals( character );
    }

    public RegexLiteralCharacter reversed()
    {
        return new RegexLiteralCharacter( character );
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
                    && Character.valueOf( subject.charAt( position ) ).equals( character )
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
