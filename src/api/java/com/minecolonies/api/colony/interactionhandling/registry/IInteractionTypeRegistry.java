package com.minecolonies.api.colony.interactionhandling.registry;

import com.minecolonies.api.IMinecoloniesAPI;
import com.minecolonies.api.colony.interactionhandling.type.InteractionType;
import net.minecraftforge.registries.IForgeRegistry;

public interface IInteractionTypeRegistry
{
    static IForgeRegistry<InteractionType> getInstance()
    {
        return IMinecoloniesAPI.getInstance().getInteractionTypeRegistry();
    }
}
