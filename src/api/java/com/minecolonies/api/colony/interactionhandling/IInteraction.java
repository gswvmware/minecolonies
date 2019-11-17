package com.minecolonies.api.colony.interactionhandling;

import com.minecolonies.api.colony.ICitizenData;
import com.minecolonies.api.colony.interactionhandling.type.InteractionType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a single open interaction.
 */
public interface IInteraction extends INBTSerializable<CompoundNBT>
{
    /**
     * The type of the interaction.
     *
     * @return The type.
     */
    InteractionType getType();

    /**
     * Called every tick to ensure the interaction gets updated.
     *
     * @param data The citizen with the interaction, which gets updated.
     */
    default void onUpdate(@NotNull final ICitizenData data)
    {
        //Noop
    }

    /**
     * Indicates if this interaction is visible to the user.
     *
     * @return {@code True} to make the interaction visible to the user, {@code False} when not.
     */
    default boolean isVisible()
    {
        return false;
    }

    @Override
    default CompoundNBT serializeNBT()
    {
        return new CompoundNBT();
    }

    @Override
    default void deserializeNBT(CompoundNBT nbt)
    {
        //Noop.
    }

    /**
     * Gives access to the window handler for the interaction UI. Allows for the construction of Interaction trees in the UI.
     * <p>
     * If {@link #isVisible()} is {@code False} then this should return {@code null}. In all other cases this should return an instance.
     *
     * @return The interaction window handler.
     */
    @OnlyIn(Dist.CLIENT)
    @Nullable
    default IInteractionWindowHandler getWindowHandler()
    {
        return null;
    }
}
