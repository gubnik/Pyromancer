package net.team_prometheus.pyromancer.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.init.ModAttributes;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BlazingJournal extends Item {
    private ImmutableListMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableListMultimap.builder();
    private int consumption;
    public BlazingJournal(Properties properties) {
        super(properties.stacksTo(1));
        builder.putAll(super.getDefaultAttributeModifiers(EquipmentSlot.OFFHAND));
    }
    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected){
        itemStack.getOrCreateTag().putDouble("CustomModelData", itemStack.getOrCreateTag().getInt("quill"));
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag){
        super.appendHoverText(itemStack, level, list, flag);
        String name = QuillItem.getQuillDescription(itemStack);
        String quillName = "item.pyromancer." + QuillItem.getQuill(itemStack.getOrCreateTag().getInt("quill")).toString();
        if(Screen.hasShiftDown()) {
            list.add(Component.translatable(quillName).withStyle(ChatFormatting.BOLD));
            list.add(Component.translatable("desc." + name + ".line1").withStyle(ChatFormatting.GOLD));
            list.add(Component.translatable("desc.blazing_journal.charge").withStyle(ChatFormatting.BOLD));
            list.add(Component.literal(Integer.toString(itemStack.getOrCreateTag().getInt("blaze"))).withStyle(ChatFormatting.GOLD));
        } else {
            list.add(Component.translatable("desc.press_shift").withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.BOLD));
        }
    }
    public static boolean isJournal(Item item){
        return(item instanceof BlazingJournal);
    }

    // TODO: sort out this mess
    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        return slot == EquipmentSlot.OFFHAND ? this.builder.build() : super.getDefaultAttributeModifiers(slot);
    }
}
