package me.shinybless.Galactic.DEN;

import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityVillager;
import net.minecraft.server.v1_16_R3.World;

public class NPC extends EntityVillager {
    public NPC(EntityTypes<? extends EntityVillager> entitytypes, World world) {
        super(entitytypes, world);
    }
}
