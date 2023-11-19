package net.team_prometheus.pyromancer.items.blazing_journal;

import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.ItemStackedOnOtherEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_prometheus.pyromancer.init.PyromancerConfig;
import net.team_prometheus.pyromancer.items.ItemUtils;
import net.team_prometheus.pyromancer.items.blazing_journal.compendium.CompendiumOfFlame;
import net.team_prometheus.pyromancer.items.weaponary.pyromancy.PyromancyItem;

@Mod.EventBusSubscriber
public class BlazingJournalCharging {
    private static final int MAX_ITEMS = CompendiumOfFlame.MAX_ITEMS;
    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void filling(ItemStackedOnOtherEvent event){
        int blazeValue = PyromancerConfig.COMMON.blazeValue.get();
        int journalMaxCapacity = PyromancerConfig.COMMON.journalMaxCapacity.get();

        ItemStack carriedItemstack = event.getCarriedItem();
        ItemStack journal = event.getStackedOnItem();
        String oldQuill = journal.getOrCreateTag().getString("quill");

        if(carriedItemstack.getItem() instanceof QuillItem quillItem
        && event.getClickAction().equals(ClickAction.SECONDARY) && (journal.getItem() instanceof BlazingJournal blazingJournal && blazingJournal.canHoldQuill)){
            if(!oldQuill.equals(quillItem.toString())){
                journal.getOrCreateTag().putString("quill", quillItem.toString());
                event.getCarriedSlotAccess().set(new ItemStack(QuillItem.QuillEnum.getQuillItem(oldQuill)));
                event.setCanceled(true);
            }
        }

        if(carriedItemstack.getItem().equals(Items.BLAZE_POWDER)
        && event.getClickAction().equals(ClickAction.SECONDARY) && journal.getItem() instanceof BlazingJournal){
            while (journal.getOrCreateTag().getInt("blaze") + blazeValue <= journalMaxCapacity){
                carriedItemstack.setCount(carriedItemstack.getCount() - 1);
                event.getCarriedSlotAccess().set(carriedItemstack);
                journal.getOrCreateTag().putInt("blaze", journal.getOrCreateTag().getInt("blaze") + blazeValue);
                if(!event.isCanceled()) event.setCanceled(true);
            }
        }

        if(carriedItemstack.getItem() instanceof PyromancyItem pyromancyItem
        && journal.getItem() instanceof CompendiumOfFlame compendium && event.getClickAction().equals(ClickAction.SECONDARY)){
            int slotId = -1;
            for(int i = 0; i < MAX_ITEMS; i++){
                if(!(ItemUtils.getItemFromItem(journal, i).getItem() instanceof PyromancyItem)){
                    slotId = i;
                    break;
                }
            }
            if(slotId != -1){
                ItemUtils.setItemInItem(journal, carriedItemstack, slotId);
                event.getCarriedSlotAccess().set(ItemStack.EMPTY);
                event.setCanceled(true);
            }
        }
    }
}
