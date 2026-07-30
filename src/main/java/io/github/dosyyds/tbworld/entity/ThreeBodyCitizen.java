package io.github.dosyyds.tbworld.entity;

import java.util.function.Predicate;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThreeBodyCitizen extends Monster {
    private int variantType = 0;    

    // 攻击目标筛选器：只攻击没有持剑的玩家
    private static final Predicate<LivingEntity> ATTACK_PREDICATE = target -> {
        if (target instanceof Player player) {
            ItemStack mainHand = player.getMainHandItem();
            // 若主手物品是剑标签，则不可攻击（返回false），否则可攻击（返回true）
            return !mainHand.is(ItemTags.SWORDS);
        }
        // 非玩家实体（如其他怪物）不攻击，可根据需要修改
        return false;
    };

    public int getVariantType() {
        return variantType;
    }

    public void setVariantType(int variantType) {
        this.variantType = variantType;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.variantType); // 将变种存入 NBT
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.variantType = compound.getInt("Variant"); // 从 NBT 读取
    }

    public ThreeBodyCitizen(EntityType<? extends ThreeBodyCitizen> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 10;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .add(Attributes.ARMOR, 2.0);
    }

    @Override
    protected void registerGoals() {
        // 0️⃣ 基础生存：浮水（防溺水）
        this.goalSelector.addGoal(0, new FloatGoal(this));

        // 1️⃣ 逃避持剑玩家（优先级最高，一看到就跑）
        // 参数：检测距离 8 格，步行速度 1.0，疾跑速度 1.2
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.0, 1.2,
                player -> player.getMainHandItem().is(ItemTags.SWORDS)));

        // 2️⃣ 近战攻击（只对 targetSelector 选中的目标生效）
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));

        // 7️⃣ 空闲时随机漫步（无威胁时自然移动）
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0));

        // 8️⃣ 社交行为：注视玩家和随机环顾（增加生动感）
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        // 🎯 目标选择器：主动攻击没有持剑的玩家
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true, ATTACK_PREDICATE));
    }
}