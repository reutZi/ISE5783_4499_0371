package primitives;

/**
 * Represents the material properties of a geometric object.
 */
public class Material {
    /** The diffuse reflection coefficient of the material. */
    public Double3 Kd = Double3.ZERO;

    /** The specular reflection coefficient of the material. */
    public Double3 Ks = Double3.ZERO;

    /** The transmission coefficient of the material. */
    public Double3 Kt = Double3.ZERO;

    /** The reflection coefficient of the material. */
    public Double3 Kr = Double3.ZERO;

    /** The shininess factor of the material. */
    public int nShininess = 0;

    /**
     * Sets the transmission coefficient of the material.
     *
     * @param kt The transmission coefficient as a Double3 object.
     * @return The Material object with the updated transmission coefficient.
     */
    public Material setKt(Double3 kt) {
        Kt = kt;
        return this;
    }

    /**
     * Sets the transmission coefficient of the material.
     *
     * @param kt The transmission coefficient as a double value.
     * @return The Material object with the updated transmission coefficient.
     */
    public Material setKt(double kt) {
        Kt = new Double3(kt);
        return this;
    }

    /**
     * Sets the reflection coefficient of the material.
     *
     * @param kr The reflection coefficient as a Double3 object.
     * @return The Material object with the updated reflection coefficient.
     */
    public Material setKr(Double3 kr) {
        Kr = kr;
        return this;
    }

    /**
     * Sets the reflection coefficient of the material.
     *
     * @param kr The reflection coefficient as a double value.
     * @return The Material object with the updated reflection coefficient.
     */
    public Material setKr(double kr) {
        Kr = new Double3(kr);
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient of the material.
     *
     * @param kd The diffuse reflection coefficient as a Double3 object.
     * @return The Material object with the updated diffuse reflection coefficient.
     */
    public Material setKd(Double3 kd) {
        Kd = kd;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient of the material.
     *
     * @param kd The diffuse reflection coefficient as a double value.
     * @return The Material object with the updated diffuse reflection coefficient.
     */
    public Material setKd(double kd) {
        Kd = new Double3(kd);
        return this;
    }

    /**
     * Sets the specular reflection coefficient of the material.
     *
     * @param ks The specular reflection coefficient as a Double3 object.
     * @return The Material object with the updated specular reflection coefficient.
     */
    public Material setKs(Double3 ks) {
        Ks = ks;
        return this;
    }

    /**
     * Sets the specular reflection coefficient of the material.
     *
     * @param ks The specular reflection coefficient as a double value.
     * @return The Material object with the updated specular reflection coefficient.
     */
    public Material setKs(double ks) {
        Ks = new Double3(ks);
        return this;
    }

    /**
     * Sets the shininess factor of the material.
     *
     * @param nShininess The shininess factor as an integer value.
     * @return The Material object with the updated shininess factor.
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
