package com.rocketnia.hacks.myregex.old;
/*

import com.rocketnia.util.Integrity;
import com.rocketnia.util.Tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RegexContext
{
    private Map< String, List< Regex >       > macroDefinitions;
    private Map< String, List< RegexMatch >  > macroMatches;
    private Map< String, String              > singleValuedMacros;


    public RegexContext( RegexContext... others )
    {
        macroDefinitions    = new TreeMap< String, List< Regex >       >();
        macroMatches        = new TreeMap< String, List< RegexMatch >  >();
        singleValuedMacros  = new TreeMap< String, String              >();

        addAll( others );
    }


    public RegexContext( Collection< RegexContext > others )
    {
        macroDefinitions    = new TreeMap< String, List< Regex >       >();
        macroMatches        = new TreeMap< String, List< RegexMatch >  >();
        singleValuedMacros  = new TreeMap< String, String              >();

        addAll( others );
    }


    private void addAll( RegexContext[] others )
    {
        for ( RegexContext other: others )
        {
            add( other );
        }
    }


    private void addAll( Collection< RegexContext > others )
    {
        for ( RegexContext other: others )
        {
            add( other );
        }
    }


    private void add( RegexContext other )
    {
        for ( String macroName: other.macroDefinitions.keySet() )
        {
            forceMacroName( macroName );

            macroDefinitions    .get( macroName ).addAll( other.macroDefinitions  .get( macroName ) );

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
                    && (singleValuedMacros.get( macroName ) != match.getContents())
                )
                {
                    singleValuedMacros.remove( macroName );
                }
            }
        }
    }


    public RegexContext withMacroDefinition( String name, Regex definition )
    {
        Integrity.assertNotNull( name, definition );

        RegexContext result = new RegexContext( this );

        result.forceMacroName( name );

        result.macroDefinitions.get( name ).add( definition );

        return result;
    }




    public RegexContext withMacroMatch( String name, RegexMatch match )
    {
        Integrity.assertNotNull( name, match );

        RegexContext result = new RegexContext( this );

        if ( result.singleValuedMacros.containsKey( name ) )
        {
            if ( result.singleValuedMacros.get( name ) != match.getContents() )
                result.singleValuedMacros.remove( name );
        }
        else
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

            for ( Regex       definition:  macroDefinitions  .get( macroName ) )
            {
                result.macroDefinitions  .get( macroName ).add( definition  .reversed() );
            }

            for ( RegexMatch  match:       macroMatches      .get( macroName ) )
            {
            	result.macroMatches      .get( macroName ).add( match       .reversed() );
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
        if ( macroDefinitions.containsKey( name ) )
            return;

        macroDefinitions.  put( name, new ArrayList< Regex >()      );
        macroMatches.      put( name, new ArrayList< RegexMatch >() );
    }


    public List< Regex > getMacroDefinitions( String name )
    {
        Integrity.assertNotNull( name );

        if ( macroDefinitions.containsKey( name ) )
            return new ArrayList< Regex >( macroDefinitions.get( name ) );

        return new ArrayList< Regex >();
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

*/
