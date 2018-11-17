package xmilcode.gianttreemod.mob.barkbeetle;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;


public class BarkBeetleModel extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer leftAntennaBase;
    private ModelRenderer leftAntennaMiddle;
    private ModelRenderer leftAntennaTip;
    private ModelRenderer rightAntennaBase;
    private ModelRenderer rightAntennaMiddle;
    private ModelRenderer rightAntennaTip;
    private ModelRenderer body;
    private ModelRenderer rightFrontLegBase;
    private ModelRenderer rightFrontLegTip;
    private ModelRenderer rightMiddleLegBase;
    private ModelRenderer rightMiddleLegTip;
    private ModelRenderer rightRearLegBase;
    private ModelRenderer rightRearLegTip;
    private ModelRenderer leftFrontLegBase;
    private ModelRenderer leftFrontLegTip;
    private ModelRenderer leftMiddleLegBase;
    private ModelRenderer leftMiddleLegTip;
    private ModelRenderer leftRearLegBase;
    private ModelRenderer leftRearLegTip;


    public BarkBeetleModel()
    {
        textureWidth = 32;
        textureHeight = 32;

        // The box offset of each renderer is relative to the location of
        // its rotation center.
        // Rotation center (0, 0, 0) is located in the middle of the entities
        // bounding box at height 24.
        
        body = new ModelRenderer(this, 0, 0);
        body.setRotationPoint(0F, 23F, 0F);
        body.addBox(-2F, -2F, -3F, 4, 2, 7);
        body.setTextureSize(textureWidth, textureHeight);
        setRotation(body, 0F, 0F, 0F);
        
        head = new ModelRenderer(this, 0, 10);
        head.setRotationPoint(0F, 22F, -3F);
        head.addBox(-2F, -1F, -3F, 4, 1, 3);
        head.setTextureSize(textureWidth, textureHeight);
        setRotation(head, 0F, 0F, 0F);
        
        // Antennas
        // Children of the head model.
        // Their rotation points have to be relative to the rotation point of the
        // head model.
        
        final int antennaTextureX = 0;
        final int antennaTextureY = 15;
        
        leftAntennaBase = new ModelRenderer(this, antennaTextureX, antennaTextureY);
        leftAntennaBase.setRotationPoint(1F, -2F, -3F); // Relative to head.
        leftAntennaBase.addBox(0F, 0F, 0F, 1, 1, 1);
        leftAntennaBase.setTextureSize(textureWidth, textureHeight);
        setRotation(leftAntennaBase, 0F, 0F, 0F);
        head.addChild(leftAntennaBase);
        
        leftAntennaMiddle = new ModelRenderer(this, antennaTextureX, antennaTextureY);
        leftAntennaMiddle.setRotationPoint(2F, -3F, -4F); // Relative to head.
        leftAntennaMiddle.addBox(0F, 0F, 0F, 1, 1, 1);
        leftAntennaMiddle.setTextureSize(textureWidth, textureHeight);
        setRotation(leftAntennaMiddle, 0F, 0F, 0F);
        head.addChild(leftAntennaMiddle);
        
        leftAntennaTip = new ModelRenderer(this, antennaTextureX, antennaTextureY);
        leftAntennaTip.setRotationPoint(2F, -4F, -5F); // Relative to head.
        leftAntennaTip.addBox(0F, 0F, 0F, 1, 1, 1);
        leftAntennaTip.setTextureSize(textureWidth, textureHeight);
        setRotation(leftAntennaTip, 0F, 0F, 0F);
        head.addChild(leftAntennaTip);
        
        rightAntennaBase = new ModelRenderer(this, antennaTextureX, antennaTextureY);
        rightAntennaBase.setRotationPoint(-2F, -2F, -3F); // Relative to head.
        rightAntennaBase.addBox(0F, 0F, 0F, 1, 1, 1);
        rightAntennaBase.setTextureSize(textureWidth, textureHeight);
        setRotation(rightAntennaBase, 0F, 0F, 0F);
        head.addChild(rightAntennaBase);
        
        rightAntennaMiddle = new ModelRenderer(this, antennaTextureX, antennaTextureY);
        rightAntennaMiddle.setRotationPoint(-3F, -3F, -4F); // Relative to head.
        rightAntennaMiddle.addBox(0F, 0F, 0F, 1, 1, 1);
        rightAntennaMiddle.setTextureSize(textureWidth, textureHeight);
        setRotation(rightAntennaMiddle, 0F, 0F, 0F);
        head.addChild(rightAntennaMiddle);
        
        rightAntennaTip = new ModelRenderer(this, antennaTextureX, antennaTextureY);
        rightAntennaTip.setRotationPoint(-3F, -4F, -5F); // Relative to head.
        rightAntennaTip.addBox(0F, 0F, 0F, 1, 1, 1);
        rightAntennaTip.setTextureSize(textureWidth, textureHeight);
        setRotation(rightAntennaTip, 0F, 0F, 0F);
        head.addChild(rightAntennaTip);
        
        // Legs
        // The tip sections are children of the base sections.
        
        final int legBaseTextureX = 0;
        final int legBaseTextureY = 18;
        final int legTipTextureX = 0;
        final int legTipTextureY = 21;
        
        rightFrontLegBase = new ModelRenderer(this, legBaseTextureX, legBaseTextureY);
        rightFrontLegBase.setRotationPoint(-3F, 22F, -3F);
        rightFrontLegBase.addBox(-1F, 0F, 0F, 2, 1, 1);
        rightFrontLegBase.setTextureSize(textureWidth, textureHeight);
        setRotation(rightFrontLegBase, 0F, 0F, 0F);
        
        rightFrontLegTip = new ModelRenderer(this, legTipTextureX, legTipTextureY);
        rightFrontLegTip.setRotationPoint(-1F, 0F, 0F); // Relative to base section.
        rightFrontLegTip.addBox(-1F, 1F, 0F, 1, 1, 1);
        rightFrontLegTip.setTextureSize(textureWidth, textureHeight);
        setRotation(rightFrontLegTip, 0F, 0F, 0F);
        rightFrontLegBase.addChild(rightFrontLegTip);
        
        rightMiddleLegBase = new ModelRenderer(this, legBaseTextureX, legBaseTextureY);
        rightMiddleLegBase.setRotationPoint(-3F, 22F, 0F);
        rightMiddleLegBase.addBox(-1F, 0F, 0F, 2, 1, 1);
        rightMiddleLegBase.setTextureSize(textureWidth, textureHeight);
        setRotation(rightMiddleLegBase, 0F, 0F, 0F);
        
        rightMiddleLegTip = new ModelRenderer(this, legTipTextureX, legTipTextureY);
        rightMiddleLegTip.setRotationPoint(-1F, 0F, 0F); // Relative to base section.
        rightMiddleLegTip.addBox(-1F, 1F, 0F, 1, 1, 1);
        rightMiddleLegTip.setTextureSize(textureWidth, textureHeight);
        setRotation(rightMiddleLegTip, 0F, 0F, 0F);
        rightMiddleLegBase.addChild(rightMiddleLegTip);
        
        rightRearLegBase = new ModelRenderer(this, legBaseTextureX, legBaseTextureY);
        rightRearLegBase.setRotationPoint(-3F, 22F, 3F);
        rightRearLegBase.addBox(-1F, 0F, 0F, 2, 1, 1);
        rightRearLegBase.setTextureSize(textureWidth, textureHeight);
        setRotation(rightRearLegBase, 0F, 0F, 0F);
        
        rightRearLegTip = new ModelRenderer(this, legTipTextureX, legTipTextureY);
        rightRearLegTip.setRotationPoint(-1F, 0F, 0F); // Relative to base section.
        rightRearLegTip.addBox(-1F, 1F, 0F, 1, 1, 1);
        rightRearLegTip.setTextureSize(textureWidth, textureHeight);
        setRotation(rightRearLegTip, 0F, 0F, 0F);
        rightRearLegBase.addChild(rightRearLegTip);
        
        leftFrontLegBase = new ModelRenderer(this, legBaseTextureX, legBaseTextureY);
        leftFrontLegBase.setRotationPoint(2F, 22F, -3F);
        leftFrontLegBase.addBox(0F, 0F, 0F, 2, 1, 1);
        leftFrontLegBase.setTextureSize(textureWidth, textureHeight);
        setRotation(leftFrontLegBase, 0F, 0F, 0F);
        
        leftFrontLegTip = new ModelRenderer(this, legTipTextureX, legTipTextureY);
        leftFrontLegTip.setRotationPoint(1F, 0F, 0F); // Relative to base section.
        leftFrontLegTip.addBox(1F, 1F, 0F, 1, 1, 1);
        leftFrontLegTip.setTextureSize(textureWidth, textureHeight);
        setRotation(leftFrontLegTip, 0F, 0F, 0F);
        leftFrontLegBase.addChild(leftFrontLegTip);
        
        leftMiddleLegBase = new ModelRenderer(this, legBaseTextureX, legBaseTextureY);
        leftMiddleLegBase.setRotationPoint(2F, 22F, 0F);
        leftMiddleLegBase.addBox(0F, 0F, 0F, 2, 1, 1);
        leftMiddleLegBase.setTextureSize(textureWidth, textureHeight);
        setRotation(leftMiddleLegBase, 0F, 0F, 0F);
        
        leftMiddleLegTip = new ModelRenderer(this, legTipTextureX, legTipTextureY);
        leftMiddleLegTip.setRotationPoint(1F, 0F, 0F); // Relative to base section.
        leftMiddleLegTip.addBox(1F, 1F, 0F, 1, 1, 1);
        leftMiddleLegTip.setTextureSize(textureWidth, textureHeight);
        setRotation(leftMiddleLegTip, 0F, 0F, 0F);
        leftMiddleLegBase.addChild(leftMiddleLegTip);
        
        leftRearLegBase = new ModelRenderer(this, legBaseTextureX, legBaseTextureY);
        leftRearLegBase.setRotationPoint(2F, 22F, 3F);
        leftRearLegBase.addBox(0F, 0F, 0F, 2, 1, 1);
        leftRearLegBase.setTextureSize(textureWidth, textureHeight);
        setRotation(leftRearLegBase, 0F, 0F, 0F);
        
        leftRearLegTip = new ModelRenderer(this, legTipTextureX, legTipTextureY);
        leftRearLegTip.setRotationPoint(1F, 0F, 0F); // Relative to base section.
        leftRearLegTip.addBox(1F, 1F, 0F, 1, 1, 1);
        leftRearLegTip.setTextureSize(textureWidth, textureHeight);
        setRotation(leftRearLegTip, 0F, 0F, 0F);
        leftRearLegBase.addChild(leftRearLegTip);
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
        // The antennas are children of the head model and don't need to be
        // drawn separately.
        // The same for the tips of the leg. They are renderer with the leg
        // bases.
        
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        head.render(f5);
        body.render(f5);
        rightFrontLegBase.render(f5);
        rightMiddleLegBase.render(f5);
        rightRearLegBase.render(f5);
        leftFrontLegBase.render(f5);
        leftMiddleLegBase.render(f5);
        leftRearLegBase.render(f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        // Same head and leg movement as biped but alternating for the six legs. 

        head.rotateAngleY = f3 / (180F / (float)Math.PI);
        head.rotateAngleX = f4 / (180F / (float)Math.PI);

        float angleA = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        float angleB = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;

        rightFrontLegBase.rotateAngleX = angleA;
        rightFrontLegBase.rotateAngleY = 0.0F;
        rightMiddleLegBase.rotateAngleX = angleB;
        rightMiddleLegBase.rotateAngleY = 0.0F;
        rightRearLegBase.rotateAngleX = angleA;
        rightRearLegBase.rotateAngleY = 0.0F;
        
        leftFrontLegBase.rotateAngleX = angleB;
        leftFrontLegBase.rotateAngleY = 0.0F;
        leftMiddleLegBase.rotateAngleX = angleA;
        leftMiddleLegBase.rotateAngleY = 0.0F;
        leftRearLegBase.rotateAngleX = angleB;
        leftRearLegBase.rotateAngleY = 0.0F;
    }
}
