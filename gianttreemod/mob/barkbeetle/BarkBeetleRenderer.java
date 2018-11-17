package xmilcode.gianttreemod.mob.barkbeetle;

import xmilcode.gianttreemod.GiantTreeMod;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;


public class BarkBeetleRenderer extends RenderLiving
{
    private static String TEXTURE_PATH = "textures/models/barkbeetlemob.png";
    
    
    public BarkBeetleRenderer(ModelBase model, float shadowSize)
    {
        super(model, shadowSize);
    }

    
    public static void register()
    {
        RenderingRegistry.registerEntityRenderingHandler(
                BarkBeetleEntity.class,
                new BarkBeetleRenderer(new BarkBeetleModel(), BarkBeetleEntity.SHADOW_SIZE));
    }
    
    
    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return new ResourceLocation(GiantTreeMod.MODID, TEXTURE_PATH);
    }
}
