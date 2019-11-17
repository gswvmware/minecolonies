package com.minecolonies.api.colony.interactionhandling.type;

import com.minecolonies.api.colony.ICitizenData;
import com.minecolonies.api.colony.interactionhandling.ChatPriority;
import com.minecolonies.api.colony.interactionhandling.IInteraction;
import com.minecolonies.api.colony.interactionhandling.IInteractionWindowHandler;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class InteractionTypeBuilder
{
    private ResourceLocation                              name;
    private Supplier<Supplier<IInteractionWindowHandler>> windowInteractionSupplier;
    private Predicate<ICitizenData>                       validPredicate;
    private Supplier<IInteraction>                        producer;
    private ChatPriority                                  priority;

    public InteractionTypeBuilder setValidPredicate(final Predicate<ICitizenData> validPredicate)
    {
        this.validPredicate = validPredicate;
        return this;
    }

    public InteractionTypeBuilder setProducer(final Supplier<IInteraction> producer)
    {
        this.producer = producer;
        return this;
    }

    public InteractionTypeBuilder setPriority(final ChatPriority priority)
    {
        this.priority = priority;
        return this;
    }

    public InteractionTypeBuilder setWindowInteractionHandlerSupplier(Supplier<Supplier<IInteractionWindowHandler>> windowInteractionSupplier)
    {
        this.windowInteractionSupplier = windowInteractionSupplier;
        return this;
    }

    public InteractionTypeBuilder setName(@NotNull final ResourceLocation name)
    {
        this.name = name;
        return this;
    }

    public InteractionType createInteractionType()
    {
        return new InteractionType(validPredicate, producer, priority, windowInteractionSupplier);
    }
}