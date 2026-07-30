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

    public static final Supplier<EntityType<ThreeBodyCitizen>> TBCITIZEN = ENTITIES.register(
            "tbcitizen",
            () -> EntityType.Builder.of(ThreeBodyCitizen::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.55F)
                    .eyeHeight(1.5F)
                    .passengerAttachments(2.80625F)
                    .clientTrackingRange(32)
                    .build("three_body_citizen"));

}
