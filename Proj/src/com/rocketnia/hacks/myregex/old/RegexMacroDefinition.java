package com.rocketnia.hacks.myregex.old;
/*

import com.rocketnia.util.Integrity;

public class RegexMacroDefinition implements Regex
{
    private String name;
    private Regex innerRegex;

    public RegexMacroDefinition( String name, Regex innerRegex )
    {
        this.name        = name;
        this.innerRegex  = innerRegex;
    }

    public boolean equals( Object o )
    {
        if ( !(
               (o != null)
            && (o instanceof RegexMacroDefinition)
        ) )
            return false;

        RegexMacroDefinition other = (RegexMacroDefinition)( o );

        return (
               other.name        .equals( name        )
            && other.innerRegex  .equals( innerRegex  )
        );
    }

    public RegexMacroDefinition reversed()
    {
        return new RegexMacroDefinition( name, innerRegex );
    }

    public RegexIterable matches( final String subject, final int position, final RegexContext context )
    {
        Integrity.assertNotNull( subject );

        final Regex self = this;

        return new RegexIterable()
        {
            protected RegexMatch getFirstMatch()
            {
                for ( RegexMatch originalMatch: context.getMacroMatches( name ) )
                {
                    boolean thisMatchWorks = false;

                    for ( RegexMatch newMatch: innerRegex.matches( subject, originalMatch.getPosition(), context ) )
                    {
                        if ( newMatch.getEndPosition() == originalMatch.getEndPosition() )
                        {
                            thisMatchWorks = true;

                            break;
                        }
                    }

                    if ( !thisMatchWorks )
                    	return null;
                }

                return new RegexMatch( self, subject, position, 0, context.withMacroDefinition( name, innerRegex ) );
            }

            protected RegexMatch getNextMatch( RegexMatch currentMatch )
            {
                return null;
            }
        };
    }
}

*/