package com.rocketnia.hacks.myregex;

public abstract class RegexIterable implements Iterable< RegexMatch >
{
    public RegexIterator iterator()
    {
        final RegexIterable self = this;

        return new RegexIterator()
        {
            protected RegexMatch getFirstMatch()
            {
                return self.getFirstMatch();
            }

            protected RegexMatch getNextMatch( RegexMatch currentMatch )
            {
                return self.getNextMatch( currentMatch );
            }
        };
    }

    protected RegexMatch getFirstMatch()
    {
        return getNextMatch( null );
    }

    protected abstract RegexMatch getNextMatch( RegexMatch currentMatch );
}
