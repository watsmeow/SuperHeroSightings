package com.SuperHeroSightings.SuperHeroSightings.model;

import java.util.Objects;

public class SuperHero {

    private int superID;

    private String superName;

    private String superDescription;

    private String superPower;

    public int getSuperID() {
        return superID;
    }

    public void setSuperID(int superID) {
        this.superID = superID;
    }

    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }

    public String getSuperDescription() {
        return superDescription;
    }

    public void setSuperDescription(String superDescription) {
        this.superDescription = superDescription;
    }

    public String getSuperPower() {
        return superPower;
    }

    public void setSuperPower(String superPower) {
        this.superPower = superPower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuperHero superHero = (SuperHero) o;
        return superID == superHero.superID && Objects.equals(superName, superHero.superName) && Objects.equals(superDescription, superHero.superDescription) && Objects.equals(superPower, superHero.superPower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(superID, superName, superDescription, superPower);
    }


}
