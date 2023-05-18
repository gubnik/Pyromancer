package net.team_prometheus.pyromancer.items;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class QuillItem extends Item {
    public QuillItem(Properties properties) {
        super(properties);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag){
        super.appendHoverText(itemStack, level, list, flag);
        String name = itemStack.getHoverName().getString();
        if(Screen.hasShiftDown()) {
            list.add(Component.translatable("desc." + name + ".line1"));
        } else {
            list.add(Component.translatable("desc.press_shift"));
        }
    }
    public static String getQuillDescription(ItemStack itemStack){
        return "item.pyromancer." + switch ((int)itemStack.getOrCreateTag().getDouble("quill")){
            case(0) -> "blazing_quill";
            case(1) -> "smoldering_twig";
            default -> "invalid";
        };
    }
    public static String getQuillDescription(int id){
        return "item.pyromancer." + switch (id){
            case(0) -> "blazing_quill";
            case(1) -> "smoldering_twig";
            default -> "invalid";
        };
    }
}
