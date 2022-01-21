package com.rocketnia.hacks.myregex;

import com.rocketnia.util.Tools;

public class RegexMatch
{
    private Regex regex;
    private String subject;
    private int position;
    private int length;

    private RegexContext context;


    public RegexMatch( Regex regex, String subject, int position, int length, RegexContext context )
    {
        this.regex     = regex;
        this.subject   = subject;
        this.position  = position;
        this.length    = length;
        this.context   = context;
    }


    public RegexMatch( Regex regex, RegexMatch other )
    {
    	this.regex = regex;

    	this.subject   = other.subject;
        this.position  = other.position;
        this.length    = other.length;
        this.context   = other.context;
    }


    public RegexMatch( Regex regex, RegexMatch firstOther, RegexMatch... others )
        throws RegexMultipleMacroDefinitionException
    {
    	this.regex = regex;

        this.subject   = firstOther.subject;
        this.position  = firstOther.position;


        this.context = firstOther.context;

        for ( RegexMatch other: others )
        {
            this.context = new RegexContext( this.context, other.context );
        }


        int endPosition = this.position + firstOther.length;

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
        return new RegexMatch( regex.reversed(), Tools.reverse( subject ), subject.length() - getEndPosition(), length, context.reversed() );
    }


    public RegexMatch withMacroDefinition( String name, Regex definition )
        throws RegexMultipleMacroDefinitionException
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
}
