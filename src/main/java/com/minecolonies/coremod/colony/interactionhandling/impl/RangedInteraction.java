package com.minecolonies.coremod.colony.interactionhandling.impl;

import com.minecolonies.api.colony.interactionhandling.IInteraction;
import com.minecolonies.api.colony.interactionhandling.type.InteractionType;

public class RangedInteraction implements IInteraction
{
    private final InteractionType type;

    public RangedInteraction(final InteractionType type) {this.type = type;}

    @Override
    public InteractionType getType()
    {
        return type;
    }
}
