package io.github.dosyyds.tbworld.entity;

import io.github.dosyyds.tbworld.tbworld;
import io.github.dosyyds.tbworld.entity.ThreeBodyCitizen;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ThreeBodyCitizenRenderer extends MobRenderer<ThreeBodyCitizen, ThreeBodyCitizenModel<ThreeBodyCitizen>> {

    public ThreeBodyCitizenRenderer(EntityRendererProvider.Context context) {
        super(context, new ThreeBodyCitizenModel<>(context.bakeLayer(ThreeBodyCitizenModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(ThreeBodyCitizen entity) {
        int variant = entity.getVariantType(); // 通过 getter 获取
        System.out.println(variant);
        String textureName = "textures/entity/tbcitizentexture_a.png";
        switch (variant) {
            case 0 -> textureName = "textures/entity/tbcitizentexture_a.png";
            case 1 -> textureName = "textures/entity/tbcitizentexture_b.png";
            default -> System.err.println("Nuull");
        }
        return ResourceLocation.fromNamespaceAndPath(tbworld.MODID, textureName);
    }
}