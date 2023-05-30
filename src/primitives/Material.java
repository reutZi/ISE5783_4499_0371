package primitives;

public class Material {

    public Double3 Kd = Double3.ZERO;
    public Double3 Ks = Double3.ZERO;
    public Double3 Kt = Double3.ZERO;
    public Double3 Kr = Double3.ZERO;
    public int nShininess = 0;

    public Material setKt(Double3 kt) {
        Kt = kt;
        return this;
    }

    public Material setKt(double kt) {
        Kt = new Double3(kt);
        return this;
    }

    public Material setKr(Double3 kr) {
        Kr = kr;
        return this;
    }

    public Material setKr(double kr) {
        Kr = new Double3(kr);
        return this;
    }

    public Material setKd(Double3 kd) {
        Kd = kd;
        return this;
    }

    public Material setKd(double kd) {
        Kd = new Double3(kd);
        return this;
    }

    public Material setKs(Double3 ks) {
        Ks = ks;
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
