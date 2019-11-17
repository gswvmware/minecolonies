package com.minecolonies.api.colony.interactionhandling.type;

import com.minecolonies.api.colony.ICitizenData;
import com.minecolonies.api.colony.interactionhandling.ChatPriority;
import com.minecolonies.api.colony.interactionhandling.IInteraction;
import com.minecolonies.api.colony.interactionhandling.IInteractionWindowHandler;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class RangedInteractionTypeBuilder<V extends Number> extends InteractionTypeBuilder
{
    @NotNull
    private Function<ICitizenData, V> extractor;
    @NotNull
    private Optional<V>               lowerBounds = Optional.empty();
    @NotNull
    private Optional<V>               upperBounds = Optional.empty();

    private boolean includesLowerBounds = false;
    private boolean includesUpperBounds = false;

    public RangedInteractionTypeBuilder<V> withValueFrom(@NotNull final Function<ICitizenData, V> extractor)
    {
        this.extractor = extractor;
        return this;
    }

    public RangedInteractionTypeBuilder<V> withLowerBounds(@NotNull final V value)
    {
        this.lowerBounds = Optional.of(value);
        return this;
    }

    public RangedInteractionTypeBuilder<V> withUpperBounds(@NotNull final V value)
    {
        this.upperBounds = Optional.of(value);
        return this;
    }

    public RangedInteractionTypeBuilder<V> includeLowerBounds()
    {
        this.includesLowerBounds = true;
        return this;
    }

    public RangedInteractionTypeBuilder<V> includeUpperBounds()
    {
        this.includesUpperBounds = true;
        return this;
    }

    @Override
    public InteractionTypeBuilder setValidPredicate(final Predicate<ICitizenData> validPredicate)
    {
        throw new IllegalStateException("Can not set validation predicate on the ranged supplier.");
    }

    @Override
    public RangedInteractionTypeBuilder<V> setProducer(final Supplier<IInteraction> producer)
    {
        throw new IllegalStateException("Can not set producer on ranged interaction.");
    }

    @Override
    public RangedInteractionTypeBuilder<V> setPriority(final ChatPriority priority)
    {
        super.setPriority(priority);
        return this;
    }

    @Override
    public RangedInteractionTypeBuilder<V> setWindowInteractionHandlerSupplier(final Supplier<Supplier<IInteractionWindowHandler>> windowInteractionSupplier)
    {
        super.setWindowInteractionHandlerSupplier(windowInteractionSupplier);
        return this;
    }

    @Override
    public RangedInteractionTypeBuilder<V> setName(@NotNull final ResourceLocation name)
    {
        super.setName(name);
        return this;
    }

    @Override
    public InteractionType createInteractionType()
    {
        Validate.notNull(this.extractor);

        super.setValidPredicate(citizenData -> {
            final V value = this.extractor.apply(citizenData);
            if (this.lowerBounds.isPresent() && ((!this.includesLowerBounds && value.doubleValue() <= this.lowerBounds.get().doubleValue())
                                                   || value.doubleValue() < this.lowerBounds.get().doubleValue()))
            {
                return false;
            }

            return !this.upperBounds.isPresent() || ((this.includesUpperBounds || !(value.doubleValue() >= this.upperBounds.get().doubleValue())) && !(value.doubleValue()
                                                                                                                                                         > this.upperBounds.get()
                                                                                                                                                             .doubleValue()));
        });
        return super.createInteractionType();
    }
}
