package io.github.dosyyds.tbworld.entity;

import io.github.dosyyds.tbworld.tbworld;
import io.github.dosyyds.tbworld.entity.ThreeBodyCitizen;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ThreeBodyCitizenRenderer extends MobRenderer<ThreeBodyCitizen, ThreeBodyCitizenModel<ThreeBodyCitizen>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(tbworld.MODID,
            "textures/entity/tbcitizentexture.png");

    public ThreeBodyCitizenRenderer(EntityRendererProvider.Context context) {
        super(context, new ThreeBodyCitizenModel<>(context.bakeLayer(ThreeBodyCitizenModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(ThreeBodyCitizen entity) {
        return TEXTURE;
    }
}