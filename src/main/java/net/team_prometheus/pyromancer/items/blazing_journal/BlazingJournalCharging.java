package net.team_prometheus.pyromancer.items.blazing_journal;

import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.ItemStackedOnOtherEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_prometheus.pyromancer.init.PyromancerConfig;

@Mod.EventBusSubscriber
public class BlazingJournalCharging {

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void filling(ItemStackedOnOtherEvent event){
        int blazeValue = PyromancerConfig.COMMON.blazeValue.get();
        int journalMaxCapacity = PyromancerConfig.COMMON.journalMaxCapacity.get();

        ItemStack carriedItemstack = event.getCarriedItem();
        ItemStack journal = event.getStackedOnItem();
        String oldQuill = journal.getOrCreateTag().getString("quill");

        if(carriedItemstack.getItem() instanceof QuillItem quillItem
        && event.getClickAction().equals(ClickAction.SECONDARY)){
            if(!oldQuill.equals(quillItem.toString())){
                journal.getOrCreateTag().putString("quill", quillItem.toString());
                event.getCarriedSlotAccess().set(new ItemStack(QuillItem.QuillEnum.getQuillItem(oldQuill)));
                event.setCanceled(true);
            }
        }

        if(carriedItemstack.getItem().equals(Items.BLAZE_POWDER)
        && event.getClickAction().equals(ClickAction.SECONDARY)){
            while (journal.getOrCreateTag().getInt("blaze") + blazeValue <= journalMaxCapacity){
                carriedItemstack.setCount(carriedItemstack.getCount() - 1);
                event.getCarriedSlotAccess().set(carriedItemstack);
                journal.getOrCreateTag().putInt("blaze", journal.getOrCreateTag().getInt("blaze") + blazeValue);
                if(!event.isCanceled()) event.setCanceled(true);
            }
        }
    }
}
