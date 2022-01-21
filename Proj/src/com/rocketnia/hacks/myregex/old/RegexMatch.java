package com.rocketnia.hacks.myregex.old;
/*

import com.rocketnia.util.Tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RegexMatch
{
    private Regex regex;
    private String subject;
    private int position;
    private int length;

    private RegexContext context;


    public RegexMatch( Regex regex, RegexMatch... others )
    {
    	this.regex = regex;

    	if ( others.length == 0 )
    	    throw new IllegalArgumentException();

        this.subject   = others[ 0 ].subject;
        this.position  = others[ 0 ].position;

        int endPosition = this.position + others[ 0 ].length;

        for ( RegexMatch other: others )
        {
            if ( this.subject != other.subject )
                throw new IllegalArgumentException();

            if ( this.position > other.position )
            {
                this.position = other.position;
            }

            int thisEndPosition = other.position + other.length;
            if ( endPosition < thisEndPosition )
            {
                endPosition = thisEndPosition;
            }
        }

        this.length = endPosition - this.position;

        this.context = new RegexContext( RegexMatch.getContexts( others ) );
    }


    public RegexMatch( Regex regex, String subject, int position, int length, RegexMatch... others )
    {
        this.regex = regex;
        this.subject = subject;
        this.position = position;
        this.length = length;

        this.context = new RegexContext( RegexMatch.getContexts( others ) );
    }


    public RegexMatch( Regex regex, String subject, int position, int length, Collection< RegexMatch > others )
    {
        this.regex = regex;
        this.subject = subject;
        this.position = position;
        this.length = length;

        this.context = new RegexContext( RegexMatch.getContexts( others ) );
    }


    public RegexMatch( RegexMatch other )
    {
    	this.regex     = other.regex;
        this.subject   = other.subject;
        this.position  = other.position;
        this.length    = other.length;
        this.context   = other.context;
    }


    public Regex getRegex()
    {
        return regex;
    }


    public String getSubject()
    {
        return subject;
    }


    public int getPosition()
    {
        return position;
    }


    public int getLength()
    {
        return length;
    }


    public RegexContext getContext()
    {
        return context;
    }


    public int getEndPosition()
    {
        return ( position + length );
    }


    public String getContents()
    {
        return ( subject.substring( position, getEndPosition() ) );
    }


    public RegexMatch reversed()
    {
        RegexMatch result = new RegexMatch( regex.reversed(), Tools.reverse( subject ), subject.length() - getEndPosition(), length );

        result.context = context.reversed();

        return result;
    }


    public RegexMatch withMacroDefinition( String name, Regex definition )
    {
        RegexMatch result = new RegexMatch( this );

        result.context = result.context.withMacroDefinition( name, definition );

        return result;
    }


    public RegexMatch withMacroMatch( String name, RegexMatch match )
    {
        RegexMatch result = new RegexMatch( this );

        result.context = result.context.withMacroMatch( name, match );

        return result;
    }


    private static List< RegexContext > getContexts( Collection< RegexMatch > matches )
    {
        List< RegexContext > contextList = new ArrayList< RegexContext >();

        for ( RegexMatch match: matches )
        {
            contextList.add( match.context );
        }

        return contextList;
    }


    private static List< RegexContext > getContexts( RegexMatch... matches )
    {
        List< RegexContext > contextList = new ArrayList< RegexContext >();

        for ( RegexMatch match: matches )
        {
            contextList.add( match.context );
        }

        return contextList;
    }
}

*/