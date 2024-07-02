package net.thevaliantsquidward.unusualhybrids.misc;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IHitListener {
    void onHit(ItemStack stack, LivingEntity target, boolean isCharged);

}