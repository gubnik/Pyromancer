package net.team_prometheus.pyromancer.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class QuillItem extends Item {
    public QuillItem(Properties properties) {
        super(properties.stacksTo(1));
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag){
        super.appendHoverText(itemStack, level, list, flag);
        String name = itemStack.getItem().toString();
        if(Screen.hasShiftDown()) {
            list.add(Component.translatable("desc." + name + ".line1"));
        } else {
            list.add(Component.translatable("desc.press_shift").withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.BOLD));
        }
    }
    public static String getQuillDescription(ItemStack itemStack){
        return getQuill(itemStack.getOrCreateTag().getInt("quill")).toString();
    }
    public static Item getQuill(int id){
        return switch (id){
            case(0) -> ModItems.BLAZING_QUILL.get();
            case(1) -> ModItems.SMOLDERING_TWIG.get();
            default -> Items.AIR;
        };
    }
    public static int getQuillId(ItemStack itemStack){
        return switch (itemStack.getItem().toString()){
            case("blazing_quill") -> 0;
            case("smoldering_twig") -> 1;
            default -> -1;
        };
    }

}
