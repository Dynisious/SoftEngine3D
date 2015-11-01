package softEngine3D.objects;

import java.util.Objects;
import softEngine3D.matrixes.FPoint3D;
/**
 * <p>
 * A GraphicsTriangle is the same as a normal Triangle but includes some extra
 * features which are used for graphics operations.</p>
 *
 * @author Dynisious 23/10/2015
 * @version 0.0.1
 */
public class GraphicsTriangle extends Triangle {
    /**
     * <p>
     * Inverted is true if the Triangle's face points towards the origin of it's
     * GraphicsZone.</p>
     */
    public boolean inverted;

    /**
     * <p>
     * Creates a new Triangle with the passed values.</p>
     *
     * @param p1       The first point of the triangle.
     * @param p2       The second point of the triangle.
     * @param p3       The third point of the triangle.
     * @param inverted True if the Triangle's face points towards the origin of
     *                 it's GraphicsZone.
     */
    public GraphicsTriangle(final FPoint3D p1, final FPoint3D p2,
                            final FPoint3D p3, final boolean inverted) {
        super(p1, p2, p3);
        this.inverted = inverted;
    }

    @Override
    public <T extends Triangle> T copy() {
        return (T) new GraphicsTriangle(p1.copy(), p2.copy(), p3.copy(),
                inverted);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof GraphicsTriangle) {
            final GraphicsTriangle temp = (GraphicsTriangle) obj;
            if (temp.inverted == inverted
                    && temp.center.equals(center)
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
        hash = 67 * hash + (this.inverted ? 1 : 0);
        hash = 67 * hash + Objects.hashCode(this.p1);
        hash = 67 * hash + Objects.hashCode(this.p2);
        hash = 67 * hash + Objects.hashCode(this.p3);
        return hash;
    }

}
