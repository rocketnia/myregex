package com.rocketnia.hacks.myregex;

import com.rocketnia.util.Integrity;
import com.rocketnia.util.Tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RegexContext
{
    private Map< String, Regex               > macroDefinitions;
    private Map< String, List< RegexMatch >  > macroMatches;
    private Map< String, String              > singleValuedMacros;


    public RegexContext()
    {
        macroDefinitions    = new TreeMap< String, Regex               >();
        macroMatches        = new TreeMap< String, List< RegexMatch >  >();
        singleValuedMacros  = new TreeMap< String, String              >();
    }


    public RegexContext( RegexContext other )
    {
        macroDefinitions    = new TreeMap< String, Regex               >();
        macroMatches        = new TreeMap< String, List< RegexMatch >  >();
        singleValuedMacros  = new TreeMap< String, String              >();

        try
        {
           add( other );
        }
        catch ( RegexMultipleMacroDefinitionException e ) {}
    }


    public RegexContext( RegexContext... others )
        throws RegexMultipleMacroDefinitionException
    {
        macroDefinitions    = new TreeMap< String, Regex               >();
        macroMatches        = new TreeMap< String, List< RegexMatch >  >();
        singleValuedMacros  = new TreeMap< String, String              >();

        addAll( others );
    }


    public RegexContext( Collection< RegexContext > others )
        throws RegexMultipleMacroDefinitionException
    {
        macroDefinitions    = new TreeMap< String, Regex               >();
        macroMatches        = new TreeMap< String, List< RegexMatch >  >();
        singleValuedMacros  = new TreeMap< String, String              >();

        addAll( others );
    }


    private void addAll( RegexContext[] others )
        throws RegexMultipleMacroDefinitionException
    {
        for ( RegexContext other: others )
        {
            add( other );
        }
    }


    private void addAll( Collection< RegexContext > others )
        throws RegexMultipleMacroDefinitionException
    {
        for ( RegexContext other: others )
        {
            add( other );
        }
    }


    private void add( RegexContext other )
        throws RegexMultipleMacroDefinitionException
    {
        for ( String macroName: other.macroDefinitions.keySet() )
        {
            forceMacroName( macroName );

            Regex otherDefinition = other.macroDefinitions.get( macroName );

            if (
                   macroDefinitions.containsKey( macroName )
                && !macroDefinitions.get( macroName ).equals( otherDefinition )
            )
                throw new RegexMultipleMacroDefinitionException();

            macroDefinitions.put( macroName, otherDefinition );

            if ( other.singleValuedMacros.containsKey( macroName ) )
            {
                String value = other.singleValuedMacros.get( macroName );

                boolean valueIsUnique = true;
                for ( RegexMatch match: macroMatches.get( macroName ) )
                {
                    if ( match.getContents() != value )
                    {
                    	valueIsUnique = false;

                        break;
                    }
                }

                if ( valueIsUnique )
                {
                    singleValuedMacros.put( macroName, value );
                }
            }

            for ( RegexMatch match: other.macroMatches.get( macroName ) )
            {
                macroMatches.get( macroName ).add( match );

                if (
                       singleValuedMacros.containsKey( macroName )
                    && !singleValuedMacros.get( macroName ).equals( match.getContents() )
                )
                {
                    System.out.println( "blah1: " + singleValuedMacros.get( macroName ) );
                    System.out.println( "blah2: " + match.getContents() );
                    singleValuedMacros.remove( macroName );
                }
            }
        }
    }


    public RegexContext withMacroDefinition( String name, Regex definition )
        throws RegexMultipleMacroDefinitionException
    {
        Integrity.assertNotNull( name, definition );

        Integrity.assertState( !macroDefinitions.containsKey( name ) );

        RegexContext result = new RegexContext( this );

        result.forceMacroName( name );

        result.macroDefinitions.put( name, definition );

        return result;
    }


    public RegexContext withMacroMatch( String name, RegexMatch match )
    {
        Integrity.assertNotNull( name, match );

        RegexContext result = new RegexContext( this );

        if ( result.singleValuedMacros.containsKey( name ) )
        {
            if ( !result.singleValuedMacros.get( name ).equals( match.getContents() ) )
                result.singleValuedMacros.remove( name );
        }

        if (
               !result.macroMatches.containsKey( name )
            || result.macroMatches.get( name ).isEmpty()
        )
        {
            result.singleValuedMacros.put( name, match.getContents() );
        }

        result.forceMacroName( name );

        result.macroMatches.get( name ).add( match );

        return result;
    }


    public RegexContext reversed()
    {
        RegexContext result = new RegexContext();

        for ( String macroName: macroDefinitions.keySet() )
        {
            result.forceMacroName( macroName );

            result.macroDefinitions.put( macroName, macroDefinitions.get( macroName ).reversed() );

            for ( RegexMatch match: macroMatches.get( macroName ) )
            {
            	result.macroMatches.get( macroName ).add( match.reversed() );
            }
        }

        for ( String macroName: singleValuedMacros.keySet() )
        {
            result.singleValuedMacros.put( macroName, Tools.reverse( singleValuedMacros.get( macroName ) ) );
        }

        return result;
    }


    private void forceMacroName( String name )
    {
        if ( macroMatches.containsKey( name ) )
            return;

        macroMatches.put( name, new ArrayList< RegexMatch >() );
    }


    public boolean hasSameDefinitionsAs( RegexContext other )
    {
        Integrity.assertNotNull( other );

        return other.macroDefinitions.equals( macroDefinitions );
    }


    public Regex getMacroDefinition( String name )
    {
        Integrity.assertNotNull( name );

        if ( macroDefinitions.containsKey( name ) )
            return macroDefinitions.get( name );

        return null;
    }


    public List< RegexMatch > getMacroMatches( String name )
    {
        Integrity.assertNotNull( name );

        if ( macroMatches.containsKey( name ) )
            return new ArrayList< RegexMatch >( macroMatches.get( name ) );

        return new ArrayList< RegexMatch >();
    }


    public boolean macroHasParticipated( String name )
    {
        Integrity.assertNotNull( name );

        return ( macroMatches.containsKey( name ) );
    }


    public boolean macroIsSingleValued( String name )
    {
        Integrity.assertNotNull( name );

        return ( singleValuedMacros.containsKey( name ) );
    }
}