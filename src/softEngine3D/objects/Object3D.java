package softEngine3D.objects;

import java.awt.Color;
import java.util.Arrays;
import softEngine3D.matrixes.FPoint3D;
/**
 * <p>
 * A collection of vertexes and lines between vertexes to make a net of
 * polygons and the color value for each polygon. Also contains the rotation
 * and translation of this Object3D in it's container space.</p>
 *
 * @author Dynisious 27/09/2015
 * @versions 0.0.1
 */
public class Object3D implements Comparable<Object3D> {
    public FPoint3D location; //The location of this Object3D in it's container space.
    public FPoint3D rotaion; //The rotation on the three different axis.
    private FPoint3D[] vertexes; //The vertexes of this Object3D.
    /**
     * @return A collection of all the vertexes in this Object3D.
     */
    public FPoint3D[] getVertexes() {
        return vertexes;
    }
    private int[][] polygons; //The the three coordinates in each Point3D
    //represent the index of the vertex for each point on this polygon.
    /**
     * @return A copy of all the polygons in this Object3D.
     */
    public int[][] getPolygons() {
        return polygons;
    }
    private Color[] colouring; //The color value for each polygon.
    /**
     * @return A copy of all the colours with the same indexing as the polygons
     *         in this Object3D.
     */
    public Color[] getColouring() {
        return colouring;
    }

    /**
     * <p>
     * Overwrites this Object3D's vertexes, polygons and colouring.</p>
     *
     * @param vertexes  The new vertexes of the Object3D.
     * @param polygons  The new polygons of the Object3D.
     * @param colouring The colouring of the Object3D.
     */
    public final void setModel(final FPoint3D[] vertexes, final int[][] polygons,
                               final Color[] colouring)
            throws IllegalArgumentException {
        if (colouring.length != polygons.length) { //These two values need to
            //be equal so there is a colour value for each polygon.
            throw new IllegalArgumentException(
                    "ERROR : The colour array and polygons array are not the same size, there must be a colour per polygon. polygons.length="
                    + polygons.length + ", colouring.length=" + colouring.length);
        }
        for (int i = 0; i < polygons.length; i++) {
            for (int v = 0; v < polygons[i].length; v++) {
                if (polygons[i][v] >= vertexes.length) {
                    throw new IllegalArgumentException(
                            "ERROR: This vertex does not exist, at Polygon" + i + ":" + v);
                }
            }
        }
        this.vertexes = vertexes;
        this.polygons = polygons;
        this.colouring = colouring;
    }

    /**
     * <p>
     * Creates a new Object3D with the passed values.</p>
     *
     * @param location  The location of this Object3D in it's container space.
     * @param rotation  The rotation of this Object3D in it's container space.
     * @param vertexes  The vertexes of this Object3D.
     * @param polygons  The polygons making up this Object3D.
     * @param colouring The colouring of each polygon making up this Object3D.
     */
    public Object3D(final FPoint3D location, final FPoint3D rotation,
                    final FPoint3D[] vertexes, final int[][] polygons,
                    final Color[] colouring) throws IllegalArgumentException {
        this.location = location;
        this.rotaion = rotation;
        setModel(vertexes, polygons, colouring);
    }

    /**
     * <p>
     * Returns a deep copy of this Object3D.</p>
     *
     * @return The new deep copy.
     */
    public Object3D getCopy() {
        final FPoint3D[] vert = new FPoint3D[vertexes.length];
        Arrays.setAll(vert, (final int i) -> {
            final FPoint3D p = vertexes[i];
            return new FPoint3D(p.x, p.y, p.z);
        });
        final int[][] poly = new int[polygons.length][];
        Arrays.setAll(poly, (final int i) -> {
            final int[] p = new int[polygons[i].length];
            Arrays.setAll(p, (final int e) -> {
                return polygons[i][e];
            });
            return p;
        });
        final Color[] col = new Color[colouring.length];
        Arrays.setAll(col, (final int i) -> {
            final Color c = colouring[i];
            return new Color(c.getRGB());
        });
        return new Object3D(new FPoint3D(location.x, location.y, location.z),
                new FPoint3D(rotaion.x, rotaion.y, rotaion.z), vert, poly, col);
    }

    @Override
    public int compareTo(final Object3D o) {
        if (location.z > o.location.z) {
            return -1;
        }
        return 0;
    }

}
