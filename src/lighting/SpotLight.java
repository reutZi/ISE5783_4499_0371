package lighting;

import primitives.*;

public class SpotLight extends PointLight{

    private Vector dir;

    public void setDir(Vector dir) {
        this.dir = dir;
    }

    public SpotLight(Color intensity, Point position, Vector dir) {
        super(intensity, position);
        this.dir = dir.normalize();
    }

    @Override
    public Color getIntensity(Point p){

        double projection = this.dir.dotProduct(getL(p));

        double numerator = Math.max(0, projection);

        return super.getIntensity(p).scale(numerator);
    }
}
