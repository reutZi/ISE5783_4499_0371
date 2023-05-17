package lighting;

import primitives.*;

public class DirectionalLight extends Light implements LightSource{

    private Vector dir;

    public DirectionalLight(Color intensity, Vector dir) {
        super(intensity);
        this.dir = dir.normalize();
    }

    @Override
    public Color getIntensity(Point p){
        return this.getIntensity();
    }

    @Override
    public Vector getL(Point p){
        return this.dir;
    }
}
