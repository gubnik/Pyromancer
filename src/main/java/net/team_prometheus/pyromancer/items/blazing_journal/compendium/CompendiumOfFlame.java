package net.team_prometheus.pyromancer.items.blazing_journal.compendium;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.team_prometheus.pyromancer.items.ItemUtils;
import net.team_prometheus.pyromancer.items.blazing_journal.BlazingJournal;
import net.team_prometheus.pyromancer.items.weaponary.pyromancy.PyromancyItem;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class CompendiumOfFlame extends BlazingJournal {
    public static final int MAX_ITEMS = 5;
    public CompendiumOfFlame(Properties properties, boolean canHoldQuill) {
        super(properties, canHoldQuill);
    }
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag compound) {
        return new CompendiumCapability();
    }
    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected){
        if(ItemUtils.getItemFromItem(itemStack, itemStack.getOrCreateTag().getInt("active_slot")) != ItemStack.EMPTY) {
            itemStack.getOrCreateTag().putDouble("CustomModelData", 1);
        } else itemStack.getOrCreateTag().putDouble("CustomModelData", 0);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag){
        if(Screen.hasShiftDown()) {
            String pyromancy;
            ItemStack slotItem;
            for(int i = 0; i < MAX_ITEMS; i++){
                if((slotItem = ItemUtils.getItemFromItem(itemStack, i)).getItem() instanceof PyromancyItem){
                    pyromancy = Component.translatable("item.pyromancer." + slotItem.getItem()).getString();
                    list.add(Component.literal("1 : " + pyromancy));
                }
            }
            String blaze = Component.translatable("desc.blazing_journal.charge").getString();
            list.add(Component.literal(blaze + " " + itemStack.getOrCreateTag().getInt("blaze")).withStyle(ChatFormatting.BOLD));
        } else {
            list.add(Component.translatable("desc.press_shift").withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.BOLD));
        }
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand) {
        ItemStack activeItem = getActiveItem(entity);
        if(activeItem.getItem() instanceof PyromancyItem pyromancyItem) {
            return pyromancyItem.compendiumUse(world, entity, hand);
        } return InteractionResultHolder.pass(entity.getMainHandItem());
    }
    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        ItemStack activeItem = ItemUtils.getItemFromItem(itemStack, itemStack.getOrCreateTag().getInt("active_slot"));
        return activeItem.getUseAnimation();
    }
    @Override
    public int getUseDuration(@NotNull ItemStack itemstack) {
        ItemStack activeItem = ItemUtils.getItemFromItem(itemstack, itemstack.getOrCreateTag().getInt("active_slot"));
        return activeItem.getUseDuration();
    }
    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity entity, @NotNull ItemStack itemStack, int time) {
        ItemStack activeItem = ItemUtils.getItemFromItem(itemStack, itemStack.getOrCreateTag().getInt("active_slot"));
        activeItem.onUseTick(level, entity, time);
    }
    public ItemStack getActiveItem(Player player){
        return ItemUtils.getItemFromItem(player.getMainHandItem(), player.getMainHandItem().getOrCreateTag().getInt("active_slot"));
    }
}
