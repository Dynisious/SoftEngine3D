package softEngine3D.objects.polygons;

import java.awt.Graphics2D;
import java.util.Arrays;
import softEngine3D.matrixes.FPoint3D;
import softEngine3D.matrixes.TransformationMatrix;
/**
 * <p>
 * A Polygon is a closed shape with a specific method of for rendering and
 * specific was of handling transformations.</p>
 *
 * @author Dynisious 03/10/2015
 * @version 0.0.1
 */
public abstract class Polygon implements Comparable<Polygon> {
    protected final FPoint3D[] vertexes; //The vertexes that make up this Polygon.

    /**
     * @return An FPoint3D representing the geometric center of all vertexes in
     *         this Polygon.
     */
    protected FPoint3D getCentroid() {
        FPoint3D centeriod = new FPoint3D(0, 0, 0);
        for (final FPoint3D v : vertexes) {
            centeriod = centeriod.addition(v);
        }
        centeriod = centeriod.multiplication(1.0 / vertexes.length);
        return centeriod;
    }

    /**
     * <p>
     * A new Polygon with the passed vertexes.</p>
     *
     * @param vertexes The vertexes of this Polygon.
     */
    public Polygon(final FPoint3D[] vertexes) {
        this.vertexes = vertexes;
    }

    /**
     * <p>
     * Renders this Polygon onto the passed Graphics2D.</p>
     *
     * @param g The Graphics2D to render onto.
     */
    public abstract void render(final Graphics2D g);

    /**
     * @return The x coordinates of all vertexes in this Polygon.
     */
    public int[] getXs() {
        final int[] xs = new int[vertexes.length];
        Arrays.setAll(vertexes, (final int i) -> {
            return vertexes[i].x;
        });
        return xs;
    }

    /**
     * @return The y coordinates of all vertexes in this Polygon.
     */
    public int[] getYs() {
        final int[] ys = new int[vertexes.length];
        Arrays.setAll(vertexes, (final int i) -> {
            return vertexes[i].y;
        });
        return ys;
    }

    /**
     * <p>
     * Transforms this Polygon using this passed TransformationMatrix.</p>
     *
     * @param matrix The TransformationMatrix to use for the transformation.
     *
     * @return This Polygon.
     */
    public abstract Polygon transform(final TransformationMatrix matrix);

    /**
     * <p>
     * Returns true if this Polygon's centroid has a positive z and is less than
     * renderDistance units away from the origin.</p>
     *
     * @param renderDistance The maximum distance that the centroid can be from
     *                       the origin.
     *
     * @return True if this Polygon is valid to be rendered.
     */
    public boolean valid(final int renderDistance) {
        final FPoint3D centeriod = getCentroid();
        if (centeriod.z > 0) {
            if (renderDistance < Math.hypot(Math.hypot(centeriod.x,
                    centeriod.y), centeriod.z)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param <T> The type of Polygon this Method returns.
     *
     * @return A deep copy of this Polygon.
     */
    public abstract <T extends Polygon> T getCopy();

    /**
     * <p>
     * Returns -1 if this Polygon should move closer to the '0' index or 0 if it
     * should stay where it is.</p>
     *
     * @param p The Polygon to compare this Polygon against.
     *
     * @return The shift this Polygon should make.
     */
    @Override
    public int compareTo(final Polygon p) {
        if (p.getCentroid().z < getCentroid().z) {
            return -1;
        }
        return 0;
    }

}
