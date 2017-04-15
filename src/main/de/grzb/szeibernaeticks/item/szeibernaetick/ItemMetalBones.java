package main.de.grzb.szeibernaeticks.item.szeibernaetick;

import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickMapper;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.ISzeibernaetickCapability;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.SzeibernaetickCapabilityProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.SzeibernaetickMetalBonesCapability;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemMetalBones extends SzeibernaetickBase {

    public ItemMetalBones(String name) {
        super(name);
        setCreativeTab(CreativeTabs.COMBAT);
        SzeibernaetickMapper.instance.register(SzeibernaetickMetalBonesCapability.class, this);
    }

    @Override
    public ISzeibernaetickCapability getCapabilityInstance() {
        return new SzeibernaetickMetalBonesCapability();
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new SzeibernaetickCapabilityProvider(getCapabilityInstance());
    }
}