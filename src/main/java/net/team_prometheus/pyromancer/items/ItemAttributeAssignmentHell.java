package net.team_prometheus.pyromancer.items;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.init.ModAttributes;
@SuppressWarnings("unused")
@Mod.EventBusSubscriber
public class ItemAttributeAssignmentHell {
    @SubscribeEvent
    public static void itemEvent(ItemAttributeModifierEvent event){
        ItemStack itemStack = event.getItemStack();
        switch (itemStack.getItem().toString()) {
            case ("blazing_journal") -> {
                int quill = itemStack.getOrCreateTag().getInt("quill");
                int consumption = switch (quill) {
                    case (1) -> 2;
                    case (0) -> 1;
                    default -> 0;
                };
                event.addModifier(ModAttributes.BLAZE_CONSUMPTION.get(),
                        new AttributeModifier(PyromancerMod.JOURNAL_BLAZE_CONSUMPTION_UUID, "Journal modifier",
                                consumption, AttributeModifier.Operation.ADDITION));
                if (quill == 1) {
                    event.addModifier(ModAttributes.PYROMANCY_DAMAGE.get(),
                            new AttributeModifier(PyromancerMod.JOURNAL_PYROMANCY_DAMAGE_UUID, "Weapon modifier",
                                    1, AttributeModifier.Operation.ADDITION));
                }
            }
            case ("sizzling_hand") -> {
                event.addModifier(ModAttributes.BLAZE_CONSUMPTION.get(),
                        new AttributeModifier(PyromancerMod.BASE_BLAZE_CONSUMPTION_UUID, "Journal modifier",
                                1, AttributeModifier.Operation.ADDITION));
                event.addModifier(ModAttributes.PYROMANCY_DAMAGE.get(),
                        new AttributeModifier(PyromancerMod.BASE_PYROMANCY_DAMAGE_UUID, "Weapon modifier",
                                2, AttributeModifier.Operation.ADDITION));
            }
        }
    }
}
