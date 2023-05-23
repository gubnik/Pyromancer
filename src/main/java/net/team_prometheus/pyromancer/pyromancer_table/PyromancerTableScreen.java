package net.team_prometheus.pyromancer.pyromancer_table;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.Minecraft;
import java.util.concurrent.atomic.AtomicReference;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.team_prometheus.pyromancer.items.ModItems;
import org.jetbrains.annotations.NotNull;
public class PyromancerTableScreen extends AbstractContainerScreen<PyromancerTableMenu> {
    private final Level world;
    private final int x, y, z;
    private final Player entity;

    public PyromancerTableScreen(PyromancerTableMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }
    @Override
    public void render(@NotNull PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
    }
    @Override
    protected void renderBg(@NotNull PoseStack ms, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.setShaderTexture(0, new ResourceLocation("pyromancer:textures/screen/pyromancer_table_gui.png"));
        blit(ms, this.leftPos, this.topPos, 0, 0, 176, 166, 176, 166);

        RenderSystem.disableBlend();
    }
    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            assert this.minecraft != null;
            assert this.minecraft.player != null;
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }
    @Override
    public void containerTick() {
        super.containerTick();
    }
    @Override
    protected void renderLabels(@NotNull PoseStack poseStack, int mouseX, int mouseY) {
        if (textCondition(world, x, y, z))
            this.font.draw(poseStack,
                    textValue(new BlockPos(x, y, z), entity), 142, 41, -31232);
    }
    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
    }
    @Override
    public void init() {
        super.init();
        assert this.minecraft != null;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
    }
    public String textValue(BlockPos pos, Player entity) {
        if (entity == null)
            return "pizdec";
        int blaze_count = 0;
        AtomicReference<ItemStack> reference = new AtomicReference<>(ItemStack.EMPTY);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null)
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> reference.set(capability.getStackInSlot(0).copy()));
        return new java.text.DecimalFormat("##").format(reference.get().getOrCreateTag().getInt("blaze"));
    }
    public static boolean textCondition(LevelAccessor world, double x, double y, double z) {
        return (new Object() {
            public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slot_id) {
                AtomicReference<ItemStack> reference = new AtomicReference<>(ItemStack.EMPTY);
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity != null)
                    blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> reference.set(capability.getStackInSlot(slot_id).copy()));
                return reference.get();
            }
        }.getItemStack(world, new BlockPos(x, y, z), 0)).getItem() == ModItems.BLAZING_JOURNAL.get();
    }
}
