package net.team_prometheus.pyromancer.items.blazing_journal;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.registries.ModItems;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class QuillItem extends Item {
    public final int blazeConsumptionModifier;
    public final double pyromancyDamageModifier;
    public QuillItem(Properties properties, int blazeConsumptionModifier, double pyromancyDamageModifier) {
        super(properties.stacksTo(1));
        this.blazeConsumptionModifier = blazeConsumptionModifier;
        this.pyromancyDamageModifier = pyromancyDamageModifier;
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
    public enum QuillEnum {
        BLAZING_QUILL("blazing_quill", ModItems.BLAZING_QUILL),
        SMOLDERING_TWIG("smoldering_twig", ModItems.SMOLDERING_TWIG);
        private final String name;
        private final RegistryObject<Item> registryObject;
        QuillEnum(String name, RegistryObject<Item> registryObject){
            this.name = name;
            this.registryObject = registryObject;
        }
        public static Item getQuillItem(String name){
            for(QuillEnum quillEnum : values()){
                if(quillEnum.name.equals(name)){
                    return quillEnum.registryObject.get();
                }
            } return Items.AIR;
        }
        public static int getQuillId(String name){
            int id = 0;
            for(QuillEnum quillEnum : values()){
                if(quillEnum.name.equals(name)){
                    return id;
                }
                id++;
            } return 0;
        }
    }
}
