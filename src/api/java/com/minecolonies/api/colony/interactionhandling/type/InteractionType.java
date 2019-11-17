package com.minecolonies.api.colony.interactionhandling.type;

import com.minecolonies.api.colony.ICitizenData;
import com.minecolonies.api.colony.interactionhandling.ChatPriority;
import com.minecolonies.api.colony.interactionhandling.IInteraction;
import com.minecolonies.api.colony.interactionhandling.IInteractionWindowHandler;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class InteractionType extends ForgeRegistryEntry<InteractionType>
{
    @NotNull
    private final Predicate<ICitizenData> validPredicate;

    @NotNull
    private final Supplier<IInteraction> producer;

    @NotNull
    private final ChatPriority priority;

    @NotNull
    private final Supplier<Supplier<IInteractionWindowHandler>> windowInteractionSupplier;

    InteractionType(
      @NotNull final Predicate<ICitizenData> validPredicate,
      @NotNull final Supplier<IInteraction> producer,
      @NotNull final ChatPriority priority,
      @NotNull final Supplier<Supplier<IInteractionWindowHandler>> windowInteractionSupplier)
    {
        this.validPredicate = Validate.notNull(validPredicate);
        this.producer = Validate.notNull(producer);
        this.priority = Validate.notNull(priority);
        this.windowInteractionSupplier = Validate.notNull(windowInteractionSupplier);
    }

    public boolean isValid(@NotNull final ICitizenData citizenData)
    {
        Validate.notNull(citizenData);
        return validPredicate.test(citizenData);
    }

    @NotNull
    public IInteraction create()
    {
        return producer.get();
    }

    @NotNull
    public ChatPriority getPriority()
    {
        return priority;
    }

    @NotNull
    public Supplier<Supplier<IInteractionWindowHandler>> getWindowInteractionSupplier()
    {
        return windowInteractionSupplier;
    }
}
