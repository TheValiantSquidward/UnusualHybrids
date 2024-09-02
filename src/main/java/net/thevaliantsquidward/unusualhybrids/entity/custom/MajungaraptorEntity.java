package net.thevaliantsquidward.unusualhybrids.entity.custom;


import com.peeko32213.unusualprehistory.common.entity.msc.util.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.PounceGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thevaliantsquidward.unusualhybrids.tag.ModTags;
import net.thevaliantsquidward.unusualhybrids.entity.ModEntities;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;

public class MajungaraptorEntity extends EntityClimber {
    private static final EntityDataAccessor<Boolean> PRESS = SynchedEntityData.defineId(MajungaraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SCALE = SynchedEntityData.defineId(MajungaraptorEntity.class, EntityDataSerializers.INT);

    protected boolean pushingState = false;
    private static final RawAnimation VELOCI_WALK = RawAnimation.begin().thenLoop("animation.majungaraptor.walk");
    private static final RawAnimation VELOCI_IDLE = RawAnimation.begin().thenLoop("animation.majungaraptor.idle");
    private static final RawAnimation VELOCI_ATTACK = RawAnimation.begin().thenLoop("animation.velociraptor.attack");
    private static final RawAnimation VELOCI_SWIM = RawAnimation.begin().thenLoop("animation.velociraptor.swim");
    private static final RawAnimation MAJUNGAR_RUN = RawAnimation.begin().thenLoop("animation.majungaraptor.run");
    public MajungaraptorEntity(EntityType<? extends EntityBaseDinosaurAnimal> entityType, Level level) {
        super(entityType, level);
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
        this.setMaxUpStep(1.0F);
        this.refreshDimensions();
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new PounceGoal(this, 0));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 2D, false));
        this.goalSelector.addGoal(5, new PushButtonsGoal(this, 0.5F, 5, 2));
        this.goalSelector.addGoal(2, new MajungaraptorEntity.IMeleeAttackGoal());
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34) {
                    @Override
                    public boolean canUse() {
                        if (this.mob.isVehicle()) {
                            return false;
                        } else {
                            if (!this.forceTrigger) {
                                if (this.mob.getNoActionTime() >= 100) {
                                    return false;
                                }
                                if (((MajungaraptorEntity) this.mob).isHungry()) {
                                    if (this.mob.getRandom().nextInt(60) != 0) {
                                        return false;
                                    }
                                } else {
                                    if (this.mob.getRandom().nextInt(30) != 0) {
                                        return false;
                                    }
                                }
                            }

                            Vec3 vec3d = this.getPosition();
                            if (vec3d == null) {
                                return false;
                            } else {
                                this.wantedX = vec3d.x;
                                this.wantedY = vec3d.y;
                                this.wantedZ = vec3d.z;
                                this.forceTrigger = false;
                                return true;
                            }
                        }
                    }
                }

        );
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
        this.goalSelector.addGoal(3, new OpenDoorGoal(this, true));
    }




    @Override
    public boolean doHurtTarget(Entity target) {
        boolean shouldHurt;
        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float knockback = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (shouldHurt = target.hurt(this.damageSources().mobAttack(this), damage)) {
            if (knockback > 0.0f && target instanceof LivingEntity) {
                ((LivingEntity) target).knockback(knockback * 0.5f, Mth.sin(this.getYRot() * ((float) Math.PI / 180)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }
            this.doEnchantDamageEffects(this, target);
            this.setLastHurtMob(target);
        }
        if (shouldHurt && target instanceof LivingEntity livingEntity) {
            this.playSound(UPSounds.RAPTOR_HURT.get(), 0.1F, 1.0F);
        }
        return shouldHurt;
    }

    @Override
    public boolean isAlliedTo(Entity pEntity) {
        return pEntity.is(this);
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.RAPTOR_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.RAPTOR_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.RAPTOR_DEATH.get();
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Override
    protected SoundEvent getAttackSound() {
        return UPSounds.RAPTOR_ATTACK.get();
    }

    @Override
    protected int getKillHealAmount() {
        return 5;
    }

    @Override
    protected boolean canGetHungry() {
        return true;
    }

    @Override
    protected boolean hasTargets() {
        return true;
    }

    @Override
    protected boolean hasAvoidEntity() {
        return true;
    }

    @Override
    protected boolean hasCustomNavigation() {
        return false;
    }

    @Override
    protected boolean hasMakeStuckInBlock() {
        return false;
    }

    @Override
    protected boolean customMakeStuckInBlockCheck(BlockState blockState) {
        return false;
    }

    @Override
    protected TagKey<EntityType<?>> getTargetTag() {
        return ModTags.MAJUNGAR_TARGETS;
    }

    @Override
    public void setCustomName(@org.jetbrains.annotations.Nullable Component pName) {
        super.setCustomName(pName);
    }

    private void attack(LivingEntity entity) {
        entity.hurt(this.damageSources().mobAttack(this), 5.0F);
    }

    class IMeleeAttackGoal extends MeleeAttackGoal {
        public IMeleeAttackGoal() {
            super(MajungaraptorEntity.this, 1.6D, true);
        }

        protected double getAttackReachSqr(LivingEntity p_25556_) {
            return (double) (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 0.66F + p_25556_.getBbWidth());
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0 && this.getTicksUntilNextAttack() <= 0) {
                this.resetAttackCooldown();
                ((MajungaraptorEntity) this.mob).setHungry(false);
                ((MajungaraptorEntity) this.mob).attack(enemy);
                ((MajungaraptorEntity) this.mob).setTimeTillHungry(mob.getRandom().nextInt(300) + 300);
            }
        }

    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Press", this.hasPressed());
        compound.putInt("scale", this.getModelScale());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setPress(compound.getBoolean("Press"));
        this.setScale(Math.min(compound.getInt("scale"), 0));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PRESS, false);
        this.entityData.define(SCALE, 0);

    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (SCALE.equals(pKey)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(pKey);
    }

    public EntityDimensions getDimensions(Pose pPose) {
        return super.getDimensions(pPose).scale(getScale(this.getModelScale()));
    }

    private static float getScale(int scale) {
        switch (scale) {
            case 1:
                return 1.8F;
            default:
                return 0.9F;
        }
    }

    public int getModelScale() {
        return this.entityData.get(SCALE);
    }

    public void setScale(int scale) {
        this.entityData.set(SCALE, scale);
    }

    public void setPress(boolean eepy) {
        this.entityData.set(PRESS, Boolean.valueOf(eepy));
    }

    public boolean hasPressed() {
        return this.entityData.get(PRESS).booleanValue();
    }

    @Override
    public void tick() {
        super.tick();
        checkCollisions();
    }

    private void checkCollisions() {

        boolean flag = false;
        AABB axisalignedbb = this.getBoundingBox().inflate(0.2D);
        for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(axisalignedbb.minX), Mth.floor(axisalignedbb.minY), Mth.floor(axisalignedbb.minZ), Mth.floor(axisalignedbb.maxX), Mth.floor(axisalignedbb.maxY), Mth.floor(axisalignedbb.maxZ))) {
            BlockState blockstate = this.level().getBlockState(blockpos);
            if (blockstate.is(ModTags.MAJUNGAR_BREAKABLES)) {
                flag = this.level().destroyBlock(blockpos, true, this) || flag;
            }
        }
    }


    private static class PushButtonsGoal extends MoveToBlockGoal {
        private final MajungaraptorEntity raptor;

        private static final int WAIT_TICKS = 20;
        protected int ticksWaited;

        public PushButtonsGoal(MajungaraptorEntity pMob, double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
            super(pMob, pSpeedModifier, pSearchRange, pVerticalSearchRange);
            this.raptor = pMob;
        }

        public double getDesiredSquaredDistanceToTarget() {
            return 1.5D;
        }

        public boolean canUse() {
            return super.canUse();
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse();
        }

        public void tick() {
            if (this.isReachedTarget()) {
                if (this.ticksWaited >= 40) {
                    this.onReachedTarget();
                    if (raptor.random.nextFloat() <= 0.05F) {
                        if (raptor.random.nextFloat() < 0.1F) {
                            raptor.spawnAtLocation(UPItems.RAPTOR_FEATHERS.get());
                        }
                    }
                } else {
                    ++this.ticksWaited;
                }
            } else if (!this.isReachedTarget() && raptor.random.nextFloat() < 0.05F) {
                raptor.playSound(UPSounds.RAPTOR_SEARCH.get(), 0.1F, 1.0F);
            }

            super.tick();
        }

        protected void onReachedTarget() {
            boolean flag = false;
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(raptor.level(), raptor)) {
                BlockState blockstate = raptor.level().getBlockState(this.blockPos);
                if (blockstate.is(UPBlocks.AMBER_BUTTON.get())) {
                    this.pushButton(blockstate);

                }

            }
        }


        @Override
        protected BlockPos getMoveToTarget() {
            return this.blockPos;
        }

        @Override
        protected boolean isValidTarget(LevelReader world, BlockPos pos) {
            BlockState blockState = world.getBlockState(pos);
            return (blockState.is(UPBlocks.AMBER_BUTTON.get()));
        }

        private void pushButton(BlockState blockstate) {
            // Your existing code
            ((MajungaraptorEntity) this.mob).pushingState = true;
            this.nextStartTick = this.nextStartTick(this.mob);

            // Get the current state of the block
            BlockState state = this.mob.level().getBlockState(this.blockPos);

            // Check if the block is a button or a door (you can add more checks as needed)
            if (blockstate.is(ModTags.MAJUNGAR_BREAKABLES)) {
                // Break the block
                this.mob.level().destroyBlock(this.blockPos, true, this.mob);

                // Optionally drop items from the block (customize as needed)
                Block.getDrops(state, (ServerLevel) this.mob.level(), this.blockPos, null, this.mob, ItemStack.EMPTY)
                        .forEach(itemStack -> this.mob.spawnAtLocation(itemStack));
            }
        }


        public void start() {
            this.ticksWaited = 0;
            super.start();
        }

    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.MAJUNGARAPTOR.get().create(serverLevel);
    }


    protected <E extends MajungaraptorEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            return PlayState.CONTINUE;
        }
        if (this.isInWater()) {
            event.setAndContinue(VELOCI_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWater()) {
            {
                if (this.isSprinting()) {
                    event.setAndContinue(MAJUNGAR_RUN);
                    event.getController().setAnimationSpeed(1.0D);
                    return PlayState.CONTINUE;
                }
                event.setAndContinue(VELOCI_WALK);
                event.getController().setAnimationSpeed(1.5D);
                return PlayState.CONTINUE;

            }
        } else {
            event.setAndContinue(VELOCI_IDLE);
            event.getController().setAnimationSpeed(1.0D);
        }
        return PlayState.CONTINUE;
    }

    protected <E extends MajungaraptorEntity> PlayState attackController(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationController.State.PAUSED)) {
            return event.setAndContinue(VELOCI_ATTACK);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
        controllers.add(new AnimationController<>(this, "Attack", 0, this::attackController));
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

}