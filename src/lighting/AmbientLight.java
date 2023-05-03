package lighting;
import primitives.*;
import  static primitives.Color.BLACK;

public class AmbientLight {

    private Color intensity;
    public static final AmbientLight NONE = new AmbientLight(BLACK, Double3.ZERO);

    public AmbientLight(Color iA, Double3 kA) {
        intensity = iA.scale(kA);
    }

    public AmbientLight(Color iA, double kA) {
        intensity = iA.scale(kA);
    }

    public Color getIntensity(){
        return this.intensity;
    }
}
