package net.team_prometheus.pyromancer.items.blazing_journal;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class BlazingJournal extends Item {
    public final boolean canHoldQuill;
    public BlazingJournal(Properties properties, boolean canHoldQuill) {
        super(properties.stacksTo(1));
        this.canHoldQuill = canHoldQuill;
    }

    public boolean isEnchantable(@NotNull ItemStack itemStack){return true;}
    public int getEnchantmentValue(ItemStack itemStack){return 10;}
    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected){
        if(itemStack.getOrCreateTag().getString("quill").equals("")) itemStack.getOrCreateTag().putString("quill", "blazing_quill");
        itemStack.getOrCreateTag().putDouble("CustomModelData", QuillItem.QuillEnum.getQuillId(itemStack.getOrCreateTag().getString("quill")));
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag){
        super.appendHoverText(itemStack, level, list, flag);
        if(itemStack.getOrCreateTag().getString("quill").equals("")){
            itemStack.getOrCreateTag().putString("quill", "blazing_quill");
        }
        String name = itemStack.getOrCreateTag().getString("quill");
        String quillName = "item.pyromancer." + name;
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
}
