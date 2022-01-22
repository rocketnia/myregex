package com.rocketnia.hacks.myregex;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class RegexIterator implements Iterator< RegexMatch >
{
    private boolean isEmpty = false;
    private boolean matchPrepared = false;
    protected RegexMatch preparedMatch = null;

    public boolean hasNext()
    {
        prepareMatch();

        return ( !isEmpty );
    }

    public RegexMatch next()
    {
        prepareMatch();

        if ( isEmpty )
            throw new NoSuchElementException();

        matchPrepared = false;

        return preparedMatch;
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }

    private void prepareMatch()
    {
        if ( matchPrepared )
            return;

        if ( preparedMatch == null )
        {
            preparedMatch = getFirstMatch();
        }
        else
        {
            preparedMatch = getNextMatch( preparedMatch );
        }

        if ( preparedMatch == null )
            isEmpty = true;

        matchPrepared = true;
    }

    protected abstract RegexMatch getFirstMatch();
    protected abstract RegexMatch getNextMatch( RegexMatch currentMatch );
}
