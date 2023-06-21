package geometries;

import primitives.*;


/**
 * Representing a cuboid as a body of multiple rectangular polygons.
 * We extend Geometries rather than Geometry in order to represent a list of objects
 * @author Hila & Hannah-Leah
 */
public class Cuboid extends Geometries {

    /**
     * constructor - without color, kd, kr, ks
     *
     * @param p1     top left point
     * @param width  of cuboid
     * @param height of cuboid
     * @param depth  of cuboid
     */
    public Cuboid(Point p1, double width, double height, double depth) {
        this(p1, width, height, depth, new Color(java.awt.Color.BLACK), 0, 0, 0);
    }

    /**
     * constructor - without kd, kr, ks
     *
     * @param p1       top left point
     * @param width    of cuboid
     * @param height   of cuboid
     * @param depth    of cuboid
     * @param emission color of cuboid
     */
    public Cuboid(Point p1, double width, double height, double depth, Color emission) {
        this(p1, width, height, depth, emission, 0, 0, 0);
    }

    /**
     * constructor - without kr, ks
     *
     * @param p1       top left point
     * @param width    of cuboid
     * @param height   of cuboid
     * @param depth    of cuboid
     * @param emission color of cuboid
     * @param kd       diffusion of cuboid
     */
    public Cuboid(Point p1, double width, double height, double depth, Color emission, double kd) {
        this(p1, width, height, depth, emission, kd, 0, 0);
    }

    /**
     * constructor - without ks
     *
     * @param p1       top left point
     * @param width    of cuboid
     * @param height   of cuboid
     * @param depth    of cuboid
     * @param emission color of cuboid
     * @param kd       diffusion of cuboid
     * @param kr       reflection of cuboid
     */
    public Cuboid(Point p1, double width, double height, double depth, Color emission, double kd, double kr) {
        this(p1, width, height, depth, emission, kd, kr, 0);
    }
    /**
     * constructor
     *
     * @param p1       top left point
     * @param width    of cuboid
     * @param height   of cuboid
     * @param depth    of cuboid
     * @param emission color of cuboid
     * @param kd       diffusion of cuboid
     * @param kr       reflection of cuboid
     * @param ks       shininess of cuboid
     */
    public Cuboid(Point p1, double width, double height, double depth, Color emission, double kd, double kr, double ks) {
        Point p2 = new Point(p1.getX(), p1.getY() - height, p1.getZ());
        Point p3 = new Point(p1.getX() + width, p2.getY(), p1.getZ());
        Point p4 = new Point(p1.getX() + width, p1.getY(), p1.getZ());
        //front
        this.add(new Polygon(p1, p2, p3, p4).setEmission(emission).setMaterial(new Material().setKd(kd).setKr(kr).setKs(ks)));
        Point p5 = new Point(p3.getX(), p3.getY(), p3.getZ() - depth);
        Point p6 = new Point(p4.getX(), p4.getY(), p4.getZ() - depth);
        //right
        this.add(new Polygon(p4, p3, p5, p6).setEmission(emission).setMaterial(new Material().setKd(kd).setKr(kr).setKs(ks)));
        Point p7 = new Point(p5.getX() - width, p5.getY(), p5.getZ());
        Point p8 = new Point(p6.getX() - width, p6.getY(), p6.getZ());
        //back
        this.add(new Polygon(p6, p5, p7, p8).setEmission(emission).setMaterial(new Material().setKd(kd).setKr(kr).setKs(ks)));
        //left
        this.add(new Polygon(p8, p7, p2, p1).setEmission(emission).setMaterial(new Material().setKd(kd).setKr(kr).setKs(ks)));
        //up
        this.add(new Polygon(p1, p4, p6, p8).setEmission(emission).setMaterial(new Material().setKd(kd).setKr(kr).setKs(ks)));
        //down
        this.add(new Polygon(p2, p3, p5, p7).setEmission(emission).setMaterial(new Material().setKd(kd).setKr(kr).setKs(ks)));
    }

}
