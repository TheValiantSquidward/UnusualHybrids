package net.thevaliantsquidward.unusualhybrids.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.UUID;

public class FusionScytheItem extends SwordItem {

    private final Multimap<Attribute, AttributeModifier> toolAttributes;
    public FusionScytheItem(Tier tier, int attackDamage, float attackSpeed) {
        super(tier, attackDamage, attackSpeed, new Properties()
                .stacksTo(1)
                .defaultDurability(tier.getUses() * 3)
        );
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -3.1F, AttributeModifier.Operation.ADDITION));
        this.toolAttributes = builder.build();
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player livingEntityIn, InteractionHand hand) {
        ItemStack itemstack = livingEntityIn.getItemInHand(hand);
        Vec3 view = livingEntityIn.getViewVector(1.0F);
        livingEntityIn.setDeltaMovement(view.multiply(2.0D, 1.0D, 2.0D));
        livingEntityIn.getCooldowns().addCooldown(this, 50);
        itemstack.hurtAndBreak(1, livingEntityIn, (player) -> {
            player.broadcastBreakEvent(livingEntityIn.getUsedItemHand());
        });
        return super.use(level, livingEntityIn, hand);
    }




    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getCount() == 1;
    }

}
