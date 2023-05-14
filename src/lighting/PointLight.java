package lighting;

import primitives.*;

public class PointLight extends Light implements LightSource{
    private Point position;
    private double Kc = 1;
    private double Kl = 0;
    private double Kq = 0;

    public PointLight setKc(double Kc) {
        this.Kc = Kc;
        return this;
    }

    public PointLight setKl(double Kl) {
        this.Kl = Kl;
        return this;
    }

    public PointLight setKq(double Kq) {
        this.Kq = Kq;
        return this;
    }

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p){
        double d = p.distance(position);
        double denominator = Kc + Kl * d + Kq * d * d;
        return this.getIntensity().reduce(denominator);
    }

    @Override
    public Vector getL(Point p){
        return p.subtract(position).normalize();
    }
}
