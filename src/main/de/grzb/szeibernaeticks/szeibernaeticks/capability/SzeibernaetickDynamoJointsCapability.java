package main.de.grzb.szeibernaeticks.szeibernaeticks.capability;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPriority;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyProductionEvent;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyConsumer;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyProducer;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.feedback.EnergyFeedbackDamage;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

public class SzeibernaetickDynamoJointsCapability
        implements ISzeibernaetickCapability, IEnergyConsumer, IEnergyProducer {

    private int maxStorage;
    private int storage;
    private float fractionalStorage;

    @Override
    public String getIdentifier() {
        return "dynamoJoints";
    }

    @Override
    public NBTTagCompound toNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("maxStorage", maxStorage);
        tag.setInteger("storage", storage);
        tag.setFloat("fractionalStorage", fractionalStorage);
        return tag;
    }

    @Override
    public void fromNBT(NBTTagCompound nbt) {
        maxStorage = nbt.getInteger("maxStorage");
        storage = nbt.getInteger("storage");
        fractionalStorage = nbt.getFloat("fractionalStorage");

        if(maxStorage == 0) {
            maxStorage = 100;
        }

        return;
    }

    @Override
    public BodyPart getBodyPart() {
        return BodyPart.JOINTS;
    }

    // IEnergyConsumer Implementation

    @Override
    public EnergyPriority currentConsumptionPrio() {
        return EnergyPriority.EMPTY_FAST;
    }

    @Override
    public boolean canStillConsume() {
        return storage < maxStorage;
    }

    @Override
    public int consume() {
        Log.log("[DynJointsCap] DynJoints attempting to consume energy!", LogType.DEBUG, LogType.SZEIBER_ENERGY,
                LogType.SZEIBER_CAP, LogType.SPAMMY);
        if(canStillConsume()) {
            storage++;
            Log.log("[DynJointsCap] DynJoints consuming energy! Now storing: " + storage, LogType.DEBUG,
                    LogType.SZEIBER_ENERGY, LogType.SZEIBER_CAP, LogType.SPAMMY);
            return 1;
        }
        return 0;
    }

    // IEnergyProducer Implementation

    @Override
    public EnergyPriority currentProductionPrio() {
        return EnergyPriority.EMPTY_FAST;
    }

    @Override
    public boolean canStillProduce() {
        return storage > 0;
    }

    @Override
    public int produceAdHoc() {
        Log.log("[DynJointsCap] DynJoints attempting to produce energy!", LogType.DEBUG, LogType.SZEIBER_ENERGY,
                LogType.SZEIBER_CAP, LogType.SPAMMY);
        if(canStillProduce()) {
            storage--;
            Log.log("[DynJointsCap] DynJoints produced energy! Remaining storage: " + storage, LogType.DEBUG,
                    LogType.SZEIBER_ENERGY, LogType.SZEIBER_CAP, LogType.SPAMMY);
            return 1;
        }
        return 0;
    }

    /**
     * Produces energy based on the given fall height.
     *
     * @param height
     *            How far this fell.
     * @param entity
     *            The entity this happend on
     * @return The amount of energy produced.
     */
    public int produce(float height, Entity entity) {
        fractionalStorage += height / 4;
        int energyProduced = 0;
        if(fractionalStorage > 0) {
            energyProduced = (int) fractionalStorage;
            fractionalStorage = fractionalStorage - energyProduced;
        }
        Log.log("[DynJointsCap] Producing energy: " + energyProduced, LogType.DEBUG, LogType.SZEIBER_ENERGY,
                LogType.SZEIBER_CAP);
        EnergyProductionEvent production = new EnergyProductionEvent(entity, energyProduced);
        MinecraftForge.EVENT_BUS.post(production);

        if(production.getRemainingAmount() > 0) {
            entity.attackEntityFrom(new EnergyFeedbackDamage(this), production.getRemainingAmount());
        }
        return energyProduced;
    }

}
