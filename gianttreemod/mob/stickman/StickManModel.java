package xmilcode.gianttreemod.mob.stickman;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class StickManModel extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer body;
    private ModelRenderer rightArm;
    private ModelRenderer leftArm;
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;
  
    public StickManModel()
    {
        textureWidth = 64;
        textureHeight = 64;

        // The box offset of each renderer is relative to the location of
        // its rotation center.
        // Rotation center (0, 0, 0) is located in the middle of the entities
        // bounding box at height 24.
        
        head = new ModelRenderer(this, 0, 0);
        head.setRotationPoint(0F, -8F, 0F);
        head.addBox(-2.5F, -8F, -2F, 5, 8, 4);
        head.setTextureSize(textureWidth, textureHeight);
        setRotation(head, 0F, 0F, 0F);
        
        body = new ModelRenderer(this, 16, 16);
        body.setRotationPoint(0F, -1F, 0F);
        body.addBox(-3F, -7F, -2F, 6, 14, 4);
        body.setTextureSize(textureWidth, textureHeight);
        setRotation(body, 0F, 0F, 0F);
        
        rightArm = new ModelRenderer(this, 40, 16);
        rightArm.setRotationPoint(-4F, -8F, 0F);
        rightArm.addBox(-1F, 0F, -1F, 2, 18, 2);
        rightArm.setTextureSize(textureWidth, textureHeight);
        setRotation(rightArm, 0F, 0F, 0F);
        
        leftArm = new ModelRenderer(this, 40, 16);
        leftArm.setRotationPoint(4F, -8F, 0F);
        leftArm.addBox(-1F, 0F, -1F, 2, 18, 2);
        leftArm.setTextureSize(textureWidth, textureHeight);
        setRotation(leftArm, 0F, 0F, 0F);
        
        rightLeg = new ModelRenderer(this, 0, 16);
        rightLeg.setRotationPoint(-2F, 6F, 0F);
        rightLeg.addBox(-1F, 0F, -1F, 2, 18, 2);
        rightLeg.setTextureSize(textureWidth, textureHeight);
        setRotation(rightLeg, 0F, 0F, 0F);
        
        leftLeg = new ModelRenderer(this, 0, 16);
        leftLeg.setRotationPoint(2F, 6F, 0F);
        leftLeg.addBox(-1F, 0F, -1F, 2, 18, 2);
        leftLeg.setTextureSize(textureWidth, textureHeight);
        setRotation(leftLeg, 0F, 0F, 0F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        head.render(f5);
        body.render(f5);
        rightArm.render(f5);
        leftArm.render(f5);
        rightLeg.render(f5);
        leftLeg.render(f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        head.rotateAngleY = f3 / (180F / (float)Math.PI);
        head.rotateAngleX = f4 / (180F / (float)Math.PI);

        rightArm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 2.0F * f1 * 0.5F;
        leftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        rightArm.rotateAngleZ = 0.0F;
        leftArm.rotateAngleZ = 0.0F;
        rightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        leftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
        rightLeg.rotateAngleY = 0.0F;
        leftLeg.rotateAngleY = 0.0F;
        rightArm.rotateAngleY = 0.0F;
        leftArm.rotateAngleY = 0.0F;
        
        float f6;
        float f7;

        if (onGround > -9990.0F)
        {
            f6 = onGround;
            body.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI * 2.0F) * 0.2F;
            // Removed these line because they change the rotation point of
            // the arms to a wrong position.
//            rightArm.rotationPointZ = MathHelper.sin(body.rotateAngleY) * 5.0F;
//            rightArm.rotationPointX = -MathHelper.cos(body.rotateAngleY) * 5.0F;
//            leftArm.rotationPointZ = -MathHelper.sin(body.rotateAngleY) * 5.0F;
//            leftArm.rotationPointX = MathHelper.cos(body.rotateAngleY) * 5.0F;
            rightArm.rotateAngleY += body.rotateAngleY;
            leftArm.rotateAngleY += body.rotateAngleY;
            leftArm.rotateAngleX += body.rotateAngleY;
            f6 = 1.0F - onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            f7 = MathHelper.sin(f6 * (float)Math.PI);
            float f8 = MathHelper.sin(onGround * (float)Math.PI) * -(head.rotateAngleX - 0.7F) * 0.75F;
            rightArm.rotateAngleX = (float)((double)rightArm.rotateAngleX - ((double)f7 * 1.2D + (double)f8));
            rightArm.rotateAngleY += body.rotateAngleY * 2.0F;
            rightArm.rotateAngleZ = MathHelper.sin(onGround * (float)Math.PI) * -0.4F;
        }

        rightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        leftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        rightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
        leftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
    }
}
