package net.thevaliantsquidward.unusualhybrids.entity.custom;

import com.peeko32213.unusualprehistory.common.entity.msc.util.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.LandCreaturePathNavigation;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.thevaliantsquidward.unusualhybrids.entity.ModEntities;
import net.thevaliantsquidward.unusualhybrids.sound.ModSounds;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class TheChildEntity extends EntityBaseDinosaurAnimal {
    
    public static final int MAX_TADPOLE_AGE = Math.abs(-30000);
    public static final Ingredient FOOD_ITEMS = Ingredient.of(Items.BEEF, Items.PORKCHOP, Items.CHICKEN, UPItems.RAW_COTY.get());
    private int age;
    private static final RawAnimation BABY_WALK = RawAnimation.begin().thenLoop("animation.indominousbaby.walk");
    private static final RawAnimation BABY_IDLE = RawAnimation.begin().thenLoop("animation.indominousbaby.idle");
    private static final RawAnimation BABY_SWIM = RawAnimation.begin().thenLoop("animation.indominousbaby.swim");
    public TheChildEntity(EntityType<? extends EntityBaseDinosaurAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new LandCreaturePathNavigation(this, level);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 45.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.13D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 15.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
    }


    protected SoundEvent getAmbientSound() {
        return UPSounds.REX_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.REX_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.REX_DEATH.get();
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(UPSounds.REX_STEP.get(), 0.1F, 1.0F);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) this.setAge(this.age + 1);
        super.aiStep();
    }

    public InteractionResult mobInteract(Player p_218703_, InteractionHand p_218704_) {
        ItemStack itemstack = p_218703_.getItemInHand(p_218704_);
        if (this.isFood(itemstack)) {
            this.eatFood(p_218703_, itemstack);
        }
        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    public boolean isFood(ItemStack stack) {
        return TheChildEntity.FOOD_ITEMS.test(stack);
    }

    private void eatFood(Player player, ItemStack stack) {
        this.decrementItem(player, stack);
        this.playSound(SoundEvents.GENERIC_EAT, 0.1F, 1.0F);
        this.increaseAge((int) ((float) (this.getTicksUntilGrowth() / 20) * 0.1F));
        this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
    }

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(IndominousRexEntity.class, EntityDataSerializers.INT);

    public void addAdditionalSaveData(CompoundTag p_218709_) {
        super.addAdditionalSaveData(p_218709_);
        p_218709_.putInt("Age", this.age);
        p_218709_.putInt("Variant", this.getVariant());
        p_218709_.putInt("VariantEyeColor", this.getVariantEyeColor());
    }
    public void setVariantEyecolor(int variant) {
        this.entityData.set(VARIANTEYECOLOR, Integer.valueOf(variant));
    }
    public int getVariantEyeColor() {
        return this.entityData.get(VARIANTEYECOLOR);
    }
    private static final EntityDataAccessor<Integer> VARIANTEYECOLOR = SynchedEntityData.defineId(IndominousRexEntity.class, EntityDataSerializers.INT);

    public void readAdditionalSaveData(CompoundTag p_218698_) {
        super.readAdditionalSaveData(p_218698_);
        this.setAge(p_218698_.getInt("Age"));
        this.setVariant(p_218698_.getInt("Variant"));
        this.setVariantEyecolor(p_218698_.getInt("VariantEyeColor"));
    }
    @javax.annotation.Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @javax.annotation.Nullable SpawnGroupData spawnDataIn, @javax.annotation.Nullable CompoundTag dataTag) {
        float variantChange = this.getRandom().nextFloat();
        if(variantChange <= 0.07F){
            this.setVariant(1);
            this.setVariantEyecolor(1);
        }else{
            this.setVariant(0);
            this.setVariantEyecolor(0);
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(VARIANTEYECOLOR, 0);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    private void decrementItem(Player player, ItemStack stack) {
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
    }

    private void increaseAge(int seconds) {
        this.setAge(this.age + seconds * 20);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    public void setAge(int age) {
        this.age = age;
        if (this.age >= MAX_TADPOLE_AGE) this.growUp();
    }
    public static String getVariantName(int variant) {
        return switch (variant) {
            case 1 -> "black";
            default -> "white";
        };
    }


    private void growUp() {
        if (this.level() instanceof ServerLevel server) {
            IndominousRexEntity frog = ModEntities.INDOM.get().create(this.level());
            if (frog == null) return;

            frog.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            frog.setVariant(this.getVariant());
            frog.finalizeSpawn(server, this.level().getCurrentDifficultyAt(frog.blockPosition()), MobSpawnType.CONVERSION, null, null);
            frog.setNoAi(this.isNoAi());
            if (this.hasCustomName()) {
                frog.setCustomName(this.getCustomName());
                frog.setCustomNameVisible(this.isCustomNameVisible());
            }

            frog.setPersistenceRequired();
            this.playSound(SoundEvents.PLAYER_LEVELUP, 0.15F, 1.0F);
            server.addFreshEntityWithPassengers(frog);
            this.discard();
        }
    }



    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    protected int getKillHealAmount() {
        return 0;
    }

    @Override
    protected boolean canGetHungry() {
        return false;
    }

    @Override
    protected boolean hasTargets() {
        return false;
    }

    @Override
    protected boolean hasAvoidEntity() {
        return false;
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
        return null;
    }

    private int getTicksUntilGrowth() {
        return Math.max(0, MAX_TADPOLE_AGE - this.age);
    }

    @Override
    public boolean shouldDropExperience() {
        return false;
    }

    protected <E extends TheChildEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isInWater()) {
            event.setAndContinue(BABY_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            event.setAndContinue(BABY_WALK);
            event.getController().setAnimationSpeed(1.5D);
        } else {
            event.setAndContinue(BABY_IDLE);
            event.getController().setAnimationSpeed(1.0D);
        }
        return PlayState.CONTINUE;
    }



    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

}
