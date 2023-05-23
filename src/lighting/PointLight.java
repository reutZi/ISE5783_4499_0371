package lighting;

import primitives.*;

/**
 * Represents a point light source in a 3D scene.
 * This light source emits light uniformly in all directions from a specific position.
 */
public class PointLight extends Light implements LightSource {
    private Point position;
    private double Kc = 1;
    private double Kl = 0;
    private double Kq = 0;

    /**
     * Sets the constant attenuation factor for the light.
     *
     * @param Kc The constant attenuation factor.
     * @return The modified PointLight object.
     */
    public PointLight setKc(double Kc) {
        this.Kc = Kc;
        return this;
    }

    /**
     * Sets the linear attenuation factor for the light.
     *
     * @param Kl The linear attenuation factor.
     * @return The modified PointLight object.
     */
    public PointLight setKl(double Kl) {
        this.Kl = Kl;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor for the light.
     *
     * @param Kq The quadratic attenuation factor.
     * @return The modified PointLight object.
     */
    public PointLight setKq(double Kq) {
        this.Kq = Kq;
        return this;
    }

    /**
     * Constructs a new PointLight object with the given intensity and position.
     *
     * @param intensity The color and intensity of the light.
     * @param position The position of the light source.
     */
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

    @Override
    public double getDistance(Point point){
        return point.distance(position);
    }
}
