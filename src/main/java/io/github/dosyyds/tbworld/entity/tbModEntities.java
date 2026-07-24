package io.github.dosyyds.tbworld.entity;

import io.github.dosyyds.tbworld.tbworld;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class tbModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE,
            tbworld.MODID);

    public static final Supplier<EntityType<ThreeBodyCitizen>> THREE_BODY_CITIZEN = ENTITIES.register(
            "three_body_citizen",
            () -> EntityType.Builder.of(ThreeBodyCitizen::new, MobCategory.MONSTER)
                    .sized(0.6F, 2.9F)
                    .eyeHeight(2.55F)
                    .passengerAttachments(2.80625F)
                    .clientTrackingRange(8)
                    .build("three_body_citizen"));

}
