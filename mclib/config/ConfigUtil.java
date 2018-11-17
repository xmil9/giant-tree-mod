package xmilcode.mclib.config;


public class ConfigUtil
{
    public static String configStringFromEnum(String enumAsString)
    {
        return enumAsString.toLowerCase().replace('_', ' ');
    }

    public static String enumStringFromConfig(String configString)
    {
        return configString.toUpperCase().replace(' ', '_');
    }
}
