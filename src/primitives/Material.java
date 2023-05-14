package primitives;

public class Material {

    public Double3 Kd = Double3.ZERO;
    public Double3 Ks = Double3.ZERO;
    public int nShininess = 0;

    public Material setKd(Double3 kd) {
        Kd = kd;
        return this;
    }

    public Material setKs(Double3 ks) {
        Ks = ks;
        return this;
    }

    public Material setKd(double kd) {
        Kd = new Double3(kd);
        return this;
    }

    public Material setKs(double ks) {
        Ks = new Double3(ks);
        return this;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
