package main.de.grzb.szeibernaeticks.container;

import main.de.grzb.szeibernaeticks.tileentity.TileEntityContainerBase;
import net.minecraft.inventory.Container;

public abstract class ContainerBase extends Container {
    protected TileEntityContainerBase tileEntityContainer;

    public ContainerBase(TileEntityContainerBase tileEntityContainer) {
        super();
        this.tileEntityContainer = tileEntityContainer;
    }

}
