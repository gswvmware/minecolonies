package com.minecolonies.apiimp.initializer;

import com.minecolonies.api.colony.interactionhandling.ChatPriority;
import com.minecolonies.api.colony.interactionhandling.ModInteractionTypes;
import com.minecolonies.api.colony.interactionhandling.type.InteractionType;
import com.minecolonies.api.colony.interactionhandling.type.RangedInteractionTypeBuilder;
import com.minecolonies.coremod.colony.interactionhandling.impl.RangedInteraction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import org.jetbrains.annotations.NotNull;

public final class ModInteractionTypeInitializer
{

    private static final int COMPLAIN_DAYS_WITHOUT_HOUSE = 7;
    private static final int DEMANDS_DAYS_WITHOUT_HOUSE  = 14;

    private ModInteractionTypeInitializer()
    {
        throw new IllegalStateException("Tried to initialize: ModInteractionTypeInitializer but this is a Utility class.");
    }

    public static void init(@NotNull final RegistryEvent.Register<InteractionType> event)
    {
        ModInteractionTypes.COMPLAINS_BUILDING = new RangedInteractionTypeBuilder<Integer>()
                                                   .setPriority(ChatPriority.BLOCKING)
                                                   .setProducer(() -> new RangedInteraction(ModInteractionTypes.COMPLAINS_BUILDING))
                                                   .setWindowInteractionHandlerSupplier(() -> () -> null)
                                                   .setName(new ResourceLocation("minecolonies:building/complaining"))
                                                   .withValueFrom(citizenData -> citizenData.getCitizenHappinessHandler().getNumberOfDaysWithoutHouse())
                                                   .withLowerBounds(COMPLAIN_DAYS_WITHOUT_HOUSE)
                                                   .includeLowerBounds()
                                                   .withUpperBounds(DEMANDS_DAYS_WITHOUT_HOUSE)
                                                   .createInteractionType();

        ModInteractionTypes.DEMANDS_BUILDING = new RangedInteractionTypeBuilder<Integer>()
                                                 .setPriority(ChatPriority.BLOCKING)
                                                 .setProducer(() -> new RangedInteraction(ModInteractionTypes.DEMANDS_BUILDING))
                                                 .setWindowInteractionHandlerSupplier(() -> () -> null)
                                                 .setName(new ResourceLocation("minecolonies:building/demanding"))
                                                 .withValueFrom(citizenData -> citizenData.getCitizenHappinessHandler().getNumberOfDaysWithoutHouse())
                                                 .withLowerBounds(DEMANDS_DAYS_WITHOUT_HOUSE)
                                                 .includeLowerBounds()
                                                 .createInteractionType();

        event.getRegistry().register(ModInteractionTypes.COMPLAINS_BUILDING);
        event.getRegistry().register(ModInteractionTypes.DEMANDS_BUILDING);
    }
}
