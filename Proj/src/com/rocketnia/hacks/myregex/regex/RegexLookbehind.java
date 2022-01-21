package com.rocketnia.hacks.myregex.regex;

import com.rocketnia.hacks.myregex.Regex;
import com.rocketnia.hacks.myregex.RegexContext;
import com.rocketnia.hacks.myregex.RegexIterable;
import com.rocketnia.hacks.myregex.RegexIterator;
import com.rocketnia.hacks.myregex.RegexMatch;
import com.rocketnia.util.Integrity;
import com.rocketnia.util.Tools;

public class RegexLookbehind implements Regex
{
    private Regex reversedRegex;

    public RegexLookbehind( Regex innerRegex )
    {
        reversedRegex = innerRegex.reversed();
    }

    public boolean equals( Object o )
    {
        if ( !(
               (o != null)
            && (o instanceof RegexLookbehind)
        ) )
            return false;

        RegexLookbehind other = (RegexLookbehind)( o );

        return other.reversedRegex.equals( reversedRegex );
    }

    public Regex reversed()
    {
        return new RegexLookahead( reversedRegex.reversed() );
    }

    public RegexIterable matches( final String subject, final int position, final RegexContext context )
    {
        Integrity.assertNotNull( subject );

        final Regex self = this;

        return new RegexIterable()
        {
            private RegexIterator reversedIterator = null;

            protected RegexMatch getFirstMatch()
            {
                reversedIterator = reversedRegex.matches( Tools.reverse( subject ), subject.length() - position, context.reversed() ).iterator();

                return getNextMatch( null );
            }

            protected RegexMatch getNextMatch( RegexMatch currentMatch )
            {
                if ( reversedIterator.hasNext() )
                    return new RegexMatch( self, subject, position, 0, reversedIterator.next().getContext().reversed() );

                return null;
            }
        };
    }
}