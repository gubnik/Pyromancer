package net.team_prometheus.pyromancer.items;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.init.ModAttributes;

@Mod.EventBusSubscriber
public class ItemAttributeAssignmentHell {
    @SubscribeEvent
    public static void itemEvent(ItemAttributeModifierEvent event){
        ItemStack itemStack = event.getItemStack();
        if(itemStack.getItem() instanceof BlazingJournal){
            int consumption = switch(itemStack.getOrCreateTag().getInt("quill")){
                case(1) -> 2;
                case(0) -> 1;
                default -> 0;
            };
            event.addModifier(ModAttributes.BLAZE_CONSUMPTION.get(),
                    new AttributeModifier(PyromancerMod.BLAZE_CONSUMPTION_UUID,"Journal modifier",
                            consumption, AttributeModifier.Operation.ADDITION));
        }
    }
}
