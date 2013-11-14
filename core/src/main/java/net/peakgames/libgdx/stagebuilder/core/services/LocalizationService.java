package net.peakgames.libgdx.stagebuilder.core.services;

public interface LocalizationService {

    /**
     *
     * @param s string to be localized.
     * @return  Returns the localized value of s
     */
    public String getString(String s);
    /**
    *
    * @param s string to be localized.
    * @param args parameter values which will be parsed in the text
    * @return  Returns the localized value of s
    */
    public String getString(String s, Object ... args);
}
