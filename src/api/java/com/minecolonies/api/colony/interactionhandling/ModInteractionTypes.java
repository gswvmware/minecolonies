package com.minecolonies.api.colony.interactionhandling;

import com.minecolonies.api.colony.interactionhandling.type.InteractionType;

public final class ModInteractionTypes
{

    public static InteractionType COMPLAINS_BUILDING;
    public static InteractionType DEMANDS_BUILDING;

    private ModInteractionTypes()
    {
        throw new IllegalStateException("Tried to initialize: ModInteractionTypes but this is a Utility class.");
    }
}
