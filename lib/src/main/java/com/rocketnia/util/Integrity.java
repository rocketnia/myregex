package com.rocketnia.util;

public class Integrity
{
    private Integrity() {}

    public static void assertNotNull( Object... args )
    {
        for ( Object arg: args )
        {
            if ( arg == null )
                throw new NullPointerException();
        }
    }

    public static void assertState( Boolean... args )
    {
        for ( Boolean arg: args )
        {
            if ( !arg )
                throw new IllegalStateException();
        }
    }
}
