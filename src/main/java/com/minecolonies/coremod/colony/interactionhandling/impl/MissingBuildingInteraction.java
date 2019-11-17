package com.minecolonies.coremod.colony.interactionhandling.impl;

import com.minecolonies.api.colony.ICitizenData;
import com.minecolonies.api.colony.interactionhandling.IInteraction;
import org.jetbrains.annotations.NotNull;

public class MissingBuildingInteraction implements IInteraction
{

    private static final int      COMPLAIN_DAYS_WITHOUT_HOUSE = 7;
    private static final int      DEMANDS_DAYS_WITHOUT_HOUSE  = 14;
    @NotNull
    private final        Severity severity;
    private              boolean  isResolved                  = false;

    public MissingBuildingInteraction(@NotNull final Severity severity) {this.severity = severity;}

    @Override
    public void onUpdate(@NotNull final ICitizenData citizenData)
    {
        this.isResolved = !IsValidForSeverity(citizenData, severity);
    }

    public static boolean IsValidForSeverity(@NotNull final ICitizenData citizenData, @NotNull Severity severity)
    {
        final int homelessForDays = citizenData.getCitizenHappinessHandler().getNumberOfDaysWithoutHouse();
        if (severity == Severity.COMPLAINING)
        {
            return !(homelessForDays >= COMPLAIN_DAYS_WITHOUT_HOUSE && homelessForDays < DEMANDS_DAYS_WITHOUT_HOUSE);
        }
        else
        {
            return homelessForDays < DEMANDS_DAYS_WITHOUT_HOUSE;
        }
    }

    @Override
    public boolean isUnsolved()
    {
        return !isResolved;
    }

    public enum Severity
    {
        COMPLAINING,
        DEMANDING
    }
}
