package com.minecolonies.apiimp.initializer;

import com.minecolonies.api.colony.interactionhandling.InteractionType;
import com.minecolonies.api.colony.interactionhandling.ModInteractionTypes;
import com.minecolonies.coremod.colony.interactionhandling.impl.MissingBuildingInteraction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class ModInteractionTypeInitializer
{

    private ModInteractionTypeInitializer()
    {
        throw new IllegalStateException("Tried to initialize: ModInteractionTypeInitializer but this is a Utility class.");
    }

    public static void init(@NotNull final RegistryEvent.Register<InteractionType> event)
    {
        ModInteractionTypes.COMPLAINS_BUILDING = new InteractionType.Builder()
                                                   .setProducer(citizenData -> {
                                                       if (MissingBuildingInteraction.IsValidForSeverity(citizenData, MissingBuildingInteraction.Severity.COMPLAINING))
                                                       {
                                                           return Optional.of(new MissingBuildingInteraction(MissingBuildingInteraction.Severity.COMPLAINING));
                                                       }

                                                       return Optional.empty();
                                                   })
                                                   .setName(new ResourceLocation("minecolonies:building/complaining"))
                                                   .build();

        ModInteractionTypes.DEMANDS_BUILDING = new InteractionType.Builder()
                                                 .setProducer(citizenData -> {
                                                     if (MissingBuildingInteraction.IsValidForSeverity(citizenData, MissingBuildingInteraction.Severity.DEMANDING))
                                                     {
                                                         return Optional.of(new MissingBuildingInteraction(MissingBuildingInteraction.Severity.DEMANDING));
                                                     }

                                                     return Optional.empty();
                                                 })
                                                 .setName(new ResourceLocation("minecolonies:building/demanding"))
                                                 .build();

        event.getRegistry().register(ModInteractionTypes.COMPLAINS_BUILDING);
        event.getRegistry().register(ModInteractionTypes.DEMANDS_BUILDING);
    }
}
