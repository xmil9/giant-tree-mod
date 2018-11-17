package xmilcode.mclib.util;


public class NamingUtil
{
    public static final String NAME_COMPONENT_SEP = ".";
    
    
    public static String generateUnlocalizedName(String modId, String nameId)
    {
	return modId + NAME_COMPONENT_SEP + nameId;
    }
    
    
    public static String generateTextureName(String modId, String nameId)
    {
	return modId + ":" + nameId;
    }
    
    
    public static String generateStagedTextureName(
	    String modId,
	    String nameId,
	    int stage)
    {
	return generateStagedTextureName(generateTextureName(modId, nameId), stage);
    }
    
    
    public static String generateStagedTextureName(String textureName,int stage)
    {
	return textureName + "_stage_" + stage;
    }

    
    public static String generateEntityName(String modId, String nameId)
    {
        return modId + NAME_COMPONENT_SEP + nameId;
    }

    
    public static String generateConfigSettingName(String modId, String nameId)
    {
        return "config" + NAME_COMPONENT_SEP + modId + NAME_COMPONENT_SEP + nameId;
    }
}
