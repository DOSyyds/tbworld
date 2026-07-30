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

    public static final Supplier<EntityType<MyVariantEntity>> TBCITIZEN =
        ENTITY_TYPES.register("tbcitizen",
                () -> EntityType.Builder.of(MyVariantEntity::new, MobCategory.CREATURE)
                        .sized(0.6F, 1.8F) // 设置碰撞箱大小
                        .build("tbcitizen") // build 方法需要传入注册名
        );

}
