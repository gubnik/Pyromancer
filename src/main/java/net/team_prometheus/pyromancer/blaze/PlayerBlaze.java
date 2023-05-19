package net.team_prometheus.pyromancer.blaze;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class PlayerBlaze {
    private int blaze;
    private int MIN_BLAZE = 0;
    private int MAX_BLAZE = 512;
    public int getBlaze(){
        return blaze;
    }
    public void addBlaze(int modifier){
        this.blaze = Math.min(blaze + modifier, MAX_BLAZE);
    }
    public void removeBlaze(int modifier){
        this.blaze = Math.max(MIN_BLAZE, blaze - modifier);
    }
    public void writeNbt(CompoundTag nbt){
        nbt.putInt("blaze", blaze);
    }
    public void loadNbt(CompoundTag nbt){
        blaze = nbt.getInt("blaze");
    }
    public void copyFrom(PlayerBlaze source){
        this.blaze = source.blaze;
    }
    public void setBlaze(int amount){
        this.blaze = amount;
    }
    public void playerChangeBlaze(Player player, int amount){
        player.getCapability(PlayerBlazeProvider.PLAYER_BLAZE).ifPresent(playerBlaze -> {
            if(amount > 0) playerBlaze.addBlaze(amount);
            else playerBlaze.removeBlaze(amount);
        });
    }
    public void playerChangeBlaze(Player player, float multiplier){
        player.getCapability(PlayerBlazeProvider.PLAYER_BLAZE).ifPresent(playerBlaze ->
            playerBlaze.setBlaze(Math.round(playerBlaze.getBlaze()*multiplier)));
    }
}
