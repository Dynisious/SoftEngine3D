package softEngine3D.objects;

import softEngine3D.objects.polygons.Polygon;
import java.util.Arrays;
import softEngine3D.matrixes.FPoint3D;
/**
 * <p>
 * Contains the rotation and translation of this Object3D in it's container
 * space along with a collection of Polygons.</p>
 *
 * @author Dynisious 27/09/2015
 * @version 2.0.2
 */
public class Object3D implements Comparable<Object3D> {
    public FPoint3D location; //The location of this Object3D in it's container space.
    public FPoint3D rotaion; //The rotation on the three different axis.
    public Polygon[] polygons; //The array of Polygons.

    /**
     * <p>
     * Creates a new Object3D with the passed values.</p>
     *
     * @param location The location of this Object3D in it's container space.
     * @param rotation The rotation of this Object3D in it's container space.
     * @param polygons The polygons making up this Object3D.
     */
    public Object3D(final FPoint3D location, final FPoint3D rotation,
                    final Polygon[] polygons) {
        this.location = location;
        this.rotaion = rotation;
        this.polygons = polygons;
    }

    /**
     * <p>
     * Returns a deep copy of this Object3D.</p>
     *
     * @return The new deep copy.
     */
    public Object3D getCopy() {
        final Polygon[] polys = new Polygon[this.polygons.length];
        Arrays.setAll(polys, (final int i) -> {
            return this.polygons[i];
        });
        return new Object3D(new FPoint3D(location.x, location.y, location.z),
                new FPoint3D(rotaion.x, rotaion.y, rotaion.z), polys);
    }

    @Override
    public int compareTo(final Object3D o) {
        if (location.z > o.location.z) {
            return -1;
        }
        return 0;
    }

}
