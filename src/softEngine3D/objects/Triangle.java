package softEngine3D.objects;

import java.util.Objects;
import softEngine3D.matrixes.FPoint3D;
/**
 * <p>
 * A collection of 3 Points in 3D space.</p>
 *
 * @author Dynisious 18/10/2015
 * @version 0.0.1
 */
public class Triangle implements Comparable<Triangle> {
    public FPoint3D p1;
    public FPoint3D p2;
    public FPoint3D p3;
    public FPoint3D center; //The center of the triangle.
    public void generateCenter() {
        this.center = p1.addition(p2).addition(p3).multiplication(1 / 3f);
    }

    /**
     * <p>
     * Creates a new Triangle with the passed values.</p>
     *
     * @param p1 The first point of the triangle.
     * @param p2 The second point of the triangle.
     * @param p3 The third point of the triangle.
     */
    public Triangle(final FPoint3D p1, final FPoint3D p2, final FPoint3D p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        generateCenter();
    }

    /**
     * @param <T> The return type of the Triangle.
     *
     * @return A deep copy of this Triangle.
     */
    public <T extends Triangle> T copy() {
        return (T) new Triangle(p1.copy(), p2.copy(), p3.copy());
    }

    /**
     * <p>
     * The natural ordering for Triangles is from highest center-z value to
     * lowest.</p>
     *
     * @param triangle The Triangle to compare against.
     *
     * @return -1 if this Triangle has a higher z value else 0.
     */
    @Override
    public int compareTo(final Triangle triangle) {
        return center.z > triangle.center.z ? -1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof Triangle) {
            final Triangle temp = (Triangle) obj;
            if (temp.center.equals(center)
                    && temp.p1.equals(p1)
                    && temp.p2.equals(p2)
                    && temp.p3.equals(p3))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 53 * hash + Objects.hashCode(this.p1);
        hash = 53 * hash + Objects.hashCode(this.p2);
        hash = 53 * hash + Objects.hashCode(this.p3);
        hash = 53 * hash + Objects.hashCode(this.center);
        return hash;
    }

}
