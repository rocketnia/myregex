package com.rocketnia.hacks.myregex;

public interface Regex
{
    public Regex reversed();
    public RegexIterable matches( String subject, int position, RegexContext context );
}
