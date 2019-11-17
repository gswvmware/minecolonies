package com.minecolonies.api.colony.interactionhandling;

import com.ldtteam.blockout.views.Window;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the interactions visible to the user.
 */
@OnlyIn(Dist.CLIENT)
public interface IInteractionWindowHandler
{
    /**
     * Called when the interaction window for this interaction is opened. The window is already populated with the initial data for the interaction.
     *
     * @param window The window that represents this interaction.
     */
    @OnlyIn(Dist.CLIENT)
    void OnWindowForInteractionOpened(@NotNull final Window window);
}
