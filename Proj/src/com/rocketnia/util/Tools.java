package com.rocketnia.util;

import java.util.ArrayList;
import java.util.List;


public class Tools
{
    private Tools() {}

    public static String reverse( String original )
    {
        StringBuffer reversedSubjectBuffer = new StringBuffer();

        for ( int cursor = original.length() - 1; 0 <= cursor; cursor-- )
        {
            reversedSubjectBuffer.append( original.charAt( cursor ) );
        }

        return reversedSubjectBuffer.toString();
    }

    public static < T > List< T > reverse( List< T > original )
    {
        List< T > reversed = new ArrayList< T >();

        for ( int cursor = original.size() - 1; 0 <= cursor; cursor-- )
        {
            reversed.add( original.get( cursor ) );
        }

        return reversed;
    }
}
