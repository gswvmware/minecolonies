package com.minecolonies.api.colony.interactionhandling;

import com.minecolonies.api.colony.ICitizenData;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Function;

public class InteractionType extends ForgeRegistryEntry<InteractionType>
{
    @NotNull
    private final Function<ICitizenData, Optional<IInteraction>> producer;

    private InteractionType(
      @NotNull final Function<ICitizenData, Optional<IInteraction>> producer,
      @NotNull final ResourceLocation name)
    {
        this.producer = producer;
        this.setRegistryName(name);
    }

    public Optional<IInteraction> create(@NotNull final ICitizenData citizenData)
    {
        Validate.notNull(citizenData);
        return this.producer.apply(citizenData);
    }

    public static class Builder
    {
        private Function<ICitizenData, Optional<IInteraction>> producer;
        private ResourceLocation                               name;

        public Builder setProducer(final Function<ICitizenData, Optional<IInteraction>> producer)
        {
            this.producer = producer;
            return this;
        }

        public Builder setName(final ResourceLocation name)
        {
            this.name = name;
            return this;
        }

        public InteractionType build()
        {
            return new InteractionType(Validate.notNull(producer), Validate.notNull(name));
        }
    }
}
