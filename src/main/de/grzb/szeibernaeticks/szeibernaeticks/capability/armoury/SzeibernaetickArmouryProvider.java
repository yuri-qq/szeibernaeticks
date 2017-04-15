package main.de.grzb.szeibernaeticks.szeibernaeticks.capability.armoury;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * Does Stuff related to Capabilities. TODO: What exactly, again?
 *
 * @author DemRat
 *
 */
public class SzeibernaetickArmouryProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(ISzeibernaetickArmouryCapability.class)
    public static final Capability<ISzeibernaetickArmouryCapability> SZEIBER_CAP = null;

    private ISzeibernaetickArmouryCapability instance = SZEIBER_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == SZEIBER_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == SZEIBER_CAP ? SZEIBER_CAP.<T>cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return SZEIBER_CAP.getStorage().writeNBT(SZEIBER_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        SZEIBER_CAP.getStorage().readNBT(SZEIBER_CAP, instance, null, nbt);
    }

}
