package softEngine3D.objects;

import java.util.Comparator;
import softEngine3D.matrixes.FPoint3D;
/**
 * <p>
 * A collection of 3 Points in 3D space.</p>
 *
 * @author Dynisious 18/10/2015
 * @version 0.0.1
 */
public final class Triangle implements Comparator<Triangle> {
    private FPoint3D p1;
    public void setP1(FPoint3D p1) {
        this.p1 = p1;
        generateCenter();
    }
    public FPoint3D getP1() {
        return p1;
    }
    private FPoint3D p2;
    public void setP2(FPoint3D p2) {
        this.p2 = p2;
        generateCenter();
    }
    public FPoint3D getP2() {
        return p2;
    }
    private FPoint3D p3;
    public void setP3(FPoint3D p3) {
        this.p3 = p3;
        generateCenter();
    }
    public FPoint3D getP3() {
        return p3;
    }
    private FPoint3D center; //The center of the triangle.d
    public FPoint3D getCenter() {
        return new FPoint3D(center.x, center.y, center.z);
    }
    public void generateCenter() {
        this.center = p1.addition(p2).addition(p3).multiplication(1 / 3f);
    }
    private final boolean inverted;
    public boolean isInverted() {
        return inverted;
    }

    /**
     * <p>
     * Creates a new Triangle with the passed values.</p>
     *
     * @param p1       The first point of the triangle.
     * @param p2       The second point of the triangle.
     * @param p3       The third point of the triangle.
     * @param inverted If the Triangle is inverted then it's face is said to be
     *                 inwards towards the location value of it's Object3D else
     *                 it's face is considered to be outwards.
     */
    public Triangle(final FPoint3D p1, final FPoint3D p2, final FPoint3D p3,
                    final boolean inverted) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        generateCenter();
        this.inverted = inverted;
    }

    public Triangle copy() {
        return new Triangle(p1.copy(), p2.copy(), p3.copy(), inverted);
    }

    @Override
    public int compare(final Triangle o1, final Triangle o2) {
        return o1.getCenter().z > o2.getCenter().z ? -1 : 0;
    }

}
