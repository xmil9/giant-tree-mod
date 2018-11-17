package xmilcode.gianttreemod.mob.stickman;

import cpw.mods.fml.client.registry.RenderingRegistry;
import xmilcode.gianttreemod.GiantTreeMod;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;


public class StickManRenderer extends RenderLiving
{
    private static String TEXTURE_PATH = "textures/models/stickmanmob.png";
    
    
    public StickManRenderer(ModelBase model, float shadowSize)
    {
        super(model, shadowSize);
    }

    
    public static void register()
    {
        RenderingRegistry.registerEntityRenderingHandler(
                StickManEntity.class,
                new StickManRenderer(new StickManModel(), StickManEntity.SHADOW_SIZE));
    }
    
    
    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return new ResourceLocation(GiantTreeMod.MODID, TEXTURE_PATH);
    }
}
