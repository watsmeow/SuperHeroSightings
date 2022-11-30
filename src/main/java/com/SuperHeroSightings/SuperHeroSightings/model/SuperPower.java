package com.SuperHeroSightings.SuperHeroSightings.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class SuperPower {

    private int superPowerID;

    @NotBlank(message = "Super Power Name must not be blank")
    @Size(max=50, message = "SuperPower Name cannot be more than 50 characters.")
    private String superPowerName;

    public int getSuperPowerID() {
        return superPowerID;
    }

    public void setSuperPowerID(int superPowerID) {
        this.superPowerID = superPowerID;
    }

    public String getSuperPowerName() {
        return superPowerName;
    }

    public void setSuperPowerName(String superPowerName) {
        this.superPowerName = superPowerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuperPower that = (SuperPower) o;
        return superPowerID == that.superPowerID
                && Objects.equals(superPowerName, that.superPowerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(superPowerID, superPowerName);
    }
}
