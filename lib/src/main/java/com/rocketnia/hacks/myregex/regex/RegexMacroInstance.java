package com.rocketnia.hacks.myregex.regex;

import com.rocketnia.hacks.myregex.Regex;
import com.rocketnia.hacks.myregex.RegexContext;
import com.rocketnia.hacks.myregex.RegexIterable;
import com.rocketnia.hacks.myregex.RegexIterator;
import com.rocketnia.hacks.myregex.RegexMatch;
import com.rocketnia.util.Integrity;

public class RegexMacroInstance implements Regex
{
    private String name;

    public RegexMacroInstance( String name )
    {
        this.name = name;
    }

    public boolean equals( Object o )
    {
        if ( !(
               (o != null)
            && (o instanceof RegexMacroInstance)
        ) )
            return false;

        RegexMacroInstance other = (RegexMacroInstance)( o );

        return other.name.equals( name );
    }

    public RegexMacroInstance reversed()
    {
        return new RegexMacroInstance( name );
    }

    public RegexIterable matches( final String subject, final int position, final RegexContext context )
    {
        Integrity.assertNotNull( subject );

        final Regex self = this;

        return new RegexIterable()
        {
            private RegexIterator iterator = null;

            protected RegexMatch getFirstMatch()
            {
                // TODO: make this at least a little bit more immune from infinite loops

                Regex innerRegex = context.getMacroDefinition( name );

                if ( innerRegex != null )
                    iterator = innerRegex.matches( subject, position, context ).iterator();

                return getNextMatch( null );
            }

            protected RegexMatch getNextMatch( RegexMatch currentMatch )
            {
                if ( iterator == null )
                    return null;

                if ( !iterator.hasNext() )
                    return null;

                RegexMatch nextMatch = new RegexMatch( self, iterator.next() );

                nextMatch = nextMatch.withMacroMatch( name, nextMatch );

                return nextMatch;
            }
        };
    }
}
