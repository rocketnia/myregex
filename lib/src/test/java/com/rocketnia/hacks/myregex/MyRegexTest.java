package com.rocketnia.hacks.myregex;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.rocketnia.hacks.myregex.regex.*;
import com.rocketnia.util.Integrity;

public class MyRegexTest
{
    @Test
    public void backreferencesWork()
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

        List< String > reports = new ArrayList< String >();
        for ( RegexMatch match: TEST_REGEX.matches( TEST_SUBJECT, TEST_POSITION, new RegexContext() ) )
        {
            reports.add( "match: " + match.getPosition() + "-" + match.getEndPosition() + " [" + match.getContents() + "]" );
            for ( RegexMatch submatch: match.getContext().getMacroMatches( "surroundingPhrase" ) )
            {
                reports.add( "submatch: " + submatch.getPosition() + "-" + submatch.getEndPosition() + " [" + submatch.getContents() + "]" );
            }
        }

        Assertions.assertEquals(
            Arrays.< String >asList(
                "match: 5-13 [ or not ]",
                "submatch: 0-5 [to be]",
                "submatch: 13-18 [to be]",
                "match: 5-14 [ or not t]",
                "submatch: 1-5 [o be]",
                "submatch: 14-18 [o be]",
                "match: 5-15 [ or not to]",
                "submatch: 2-5 [ be]",
                "submatch: 15-18 [ be]",
                "match: 5-16 [ or not to ]",
                "submatch: 3-5 [be]",
                "submatch: 16-18 [be]",
                "match: 5-17 [ or not to b]",
                "submatch: 4-5 [e]",
                "submatch: 17-18 [e]"
            ),
            reports
        );
        System.out.println( "end matches" );
    }

    private static Regex compile( String regexString )
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

    private static Regex compileWithSlashes( String regexString )
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
