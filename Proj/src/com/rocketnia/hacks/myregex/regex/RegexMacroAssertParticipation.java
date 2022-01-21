package com.rocketnia.hacks.myregex.regex;

import com.rocketnia.hacks.myregex.Regex;
import com.rocketnia.hacks.myregex.RegexContext;
import com.rocketnia.hacks.myregex.RegexIterable;
import com.rocketnia.hacks.myregex.RegexMatch;
import com.rocketnia.util.Integrity;

public class RegexMacroAssertParticipation implements Regex
{
	private String name;

    public RegexMacroAssertParticipation( String name )
    {
        this.name = name;
    }

    public boolean equals( Object o )
    {
        if ( !(
               (o != null)
            && (o instanceof RegexMacroAssertParticipation)
        ) )
            return false;

        RegexMacroAssertParticipation other = (RegexMacroAssertParticipation)( o );

        return other.name.equals( name );
    }

    public Regex reversed()
    {
        return new RegexMacroAssertParticipation( name );
    }

    public RegexIterable matches( final String subject, final int position, final RegexContext context )
    {
        Integrity.assertNotNull( subject );

        final Regex self = this;

        return new RegexIterable()
        {
            protected RegexMatch getFirstMatch()
            {
                if ( context.macroHasParticipated( name ) )
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