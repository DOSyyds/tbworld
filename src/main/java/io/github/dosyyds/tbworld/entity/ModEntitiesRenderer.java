package io.github.dosyyds.tbworld.entity;

import io.github.dosyyds.tbworld.tbworld;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EnderEyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD, modid = tbworld.MODID)
public class ModEntitiesRenderer {
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(tbModEntities.THREE_BODY_CITIZEN.get(), ThreeBodyCitizenRenderer::new);
    }

    private static class ThreeBodyCitizenRenderer
            extends MobRenderer<ThreeBodyCitizen, EndermanModel<ThreeBodyCitizen>> {
        private static final ResourceLocation TEXTURE = ResourceLocation
                .withDefaultNamespace("textures/entity/enderman/enderman.png");

        public ThreeBodyCitizenRenderer(EntityRendererProvider.Context context) {
            super(context, new EndermanModel<>(context.bakeLayer(ModelLayers.ENDERMAN)), 0.5F);
            this.addLayer(new EnderEyesLayer<>(this));
        }

        @Override
        public ResourceLocation getTextureLocation(ThreeBodyCitizen entity) {
            return TEXTURE;
        }
    }
}
