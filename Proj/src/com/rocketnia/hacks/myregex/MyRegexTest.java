package com.rocketnia.hacks.myregex;

import com.rocketnia.hacks.myregex.regex.*;
import com.rocketnia.util.Integrity;

public class MyRegexTest
{
    public static void main( String[] args )
    {

        // These are the values being tested.
        //
        // Note also that the loop below has a special line for outputting the
        // matches of "surroundingPhrase" specifically. If this is changed to
        // use macros other than that, it would be a good idea to update (or
        // generalize) the loop below.
        String  TEST_SUBJECT   = "to be or not to be";
        int     TEST_POSITION  = 5;
        Regex   TEST_REGEX     =
            new RegexConcatenation(
                new RegexMacroDefinition(
                    "surroundingPhrase",
                    new RegexConcatenation(
                        new RegexAnyCharacter(),
                        new RegexGreedyClosure(
                            new RegexAnyCharacter()
                        )
                    )
                ),
                new RegexLookbehind(
                    new RegexMacroInstance( "surroundingPhrase" )
                ),
                new RegexGreedyClosure(
                    new RegexAnyCharacter()
                ),
                new RegexLookahead(
                    new RegexMacroInstance( "surroundingPhrase" )
                ),
                new RegexMacroAssertSingleValue( "surroundingPhrase" )
            );

        System.out.println( "begin matches" );

        for ( RegexMatch match: TEST_REGEX.matches( TEST_SUBJECT, TEST_POSITION, new RegexContext() ) )
        {
            System.out.println( "match: " + match.getPosition() + "-" + match.getEndPosition() + " [" + match.getContents() + "]" );
            for ( RegexMatch submatch: match.getContext().getMacroMatches( "surroundingPhrase" ) )
            {
                System.out.println( "submatch: " + submatch.getPosition() + "-" + submatch.getEndPosition() + " [" + submatch.getContents() + "]" );
            }
        }

        System.out.println( "end matches" );
    }

    public static Regex compile( String regexString )
    {
        Integrity.assertNotNull( regexString );

        switch ( regexString.length() )
        {
        case 0:
            return new RegexAtom( new RegexLazyClosure( new RegexLiteralCharacter( 'a' ) ) );
        case 1:
        	return new RegexLiteralCharacter( regexString.charAt( 0 ) );
        default:
            return new RegexConcatenation( new RegexLiteralCharacter( regexString.charAt( 0 ) ), compile( regexString.substring( 1 ) ) );
        }
    }

    public static Regex compileWithSlashes( String regexString )
    {
        Integrity.assertNotNull( regexString );

        String stringLeft = regexString;
        Regex regexSoFar = null;
        Regex nextPiece = null;

        while ( true )
        {
            if ( stringLeft.length() == 0 )
            {
                if ( regexSoFar == null )
                {
                    return new RegexAtom( new RegexLazyClosure( new RegexLiteralCharacter( 'a' ) ) );
                }
                else
                {
                    return regexSoFar;
                }
            }

            switch ( stringLeft.charAt( 0 ) )
            {
            case '\\':
                if ( stringLeft.length() == 1 )
                    throw new IllegalArgumentException();

                regexSoFar = concatenate( regexSoFar, nextPiece );
                nextPiece = new RegexLiteralCharacter( stringLeft.charAt( 1 ) );
                stringLeft = stringLeft.substring( 2 );
                break;

            default:
                regexSoFar = concatenate( regexSoFar, nextPiece );
            	nextPiece = new RegexLiteralCharacter( stringLeft.charAt( 0 ) );
                stringLeft = stringLeft.substring( 1 );
                break;
            }
        }
    }

    private static Regex concatenate( Regex first, Regex second )
    {
        if ( !(
               (first   != null)
            && (second  != null)
        ) )
            return null;

        return new RegexConcatenation( first, second );
    }
}
