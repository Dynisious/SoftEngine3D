package softEngine3D.objects;

import java.awt.Color;
import softEngine3D.matrixes.FPoint3D;
import softEngine3D.matrixes.Point3D;
/**
 * <p>
 * A collection of vertexes and lines between vertexes to make a net of
 * triangles and the color value for each triangle. Also contains the rotation
 * and translation of this Object3D in it's container space.</p>
 *
 * @author Dynisious 27/09/2015
 * @versions 0.0.1
 */
public class Object3D {
    public FPoint3D location; //The location of this Object3D in it's container space.
    public FPoint3D rotaion; //The rotation on the three different axis.
    private FPoint3D[] vertexes; //The vertexes of this Object3D.
    /**
     * <p>
     * <b>WARNING!</b> Editing the returned collection will not change values in
     * this Object3D.</p>
     *
     * @return A collection of all the vertexes in this Object3D.
     */
    public FPoint3D[] getVertexes() {
        final FPoint3D[] temp = new FPoint3D[vertexes.length];
        System.arraycopy(vertexes, 0, temp, 0, vertexes.length);
        return temp;
    }
    private Point3D[] triangles; //The the three coordinates in each Point3D
    //represent the index of the vertex for each point on this triangle.
    /**
     * <p>
     * <b>WARNING!</b> Editing the returned collection will not change the
     * values in this Object3D.</p>
     *
     * @return A copy of all the triangles in this Object3D.
     */
    public Point3D[] getTriangles() {
        final Point3D[] temp = new Point3D[triangles.length];
        System.arraycopy(triangles, 0, temp, 0, triangles.length);
        return triangles;
    }
    private Color[] colouring; //The color value for each triangle.
    /**
     * <p>
     * <b>WARNING!</b> Editing the returned collection will not change the
     * values in this Object3D.</p>
     *
     * @return A copy of all the colours in this Object3D.
     */
    public Color[] getColouring() {
        final Color[] temp = new Color[colouring.length];
        System.arraycopy(colouring, 0, temp, 0, colouring.length);
        return colouring;
    }

    /**
     * <p>
     * Overwrites this Object3D's vertexes, triangles and colouring.</p>
     *
     * @param vertexes  The new vertexes of the Object3D.
     * @param triangles The new triangles of the Object3D.
     * @param colouring The colouring of the Object3D.
     */
    public final void setModel(final FPoint3D[] vertexes,
                               final Point3D[] triangles,
                               final Color[] colouring)
            throws IllegalArgumentException {
        if (colouring.length != triangles.length) { //These two values need to
            //be equal so there is a colour value for each triangle.
            throw new IllegalArgumentException(
                    "ERROR : The colour array and triangles array are not the same size, there must be a colour per triangle. triangles.length="
                    + triangles.length + ", colouring.length=" + colouring.length);
        }
        for (int i = 0; i < triangles.length; i++) {
            final Point3D p = triangles[i]; //The Point3D being examined.
            if (p.x >= vertexes.length) {
                throw new IllegalArgumentException(
                        "ERROR: The vertex at this triangle's 'x' index does not exist, at Traingle"
                        + i + ".x=" + p.x + " ");
            } else if (p.y >= vertexes.length) {
                throw new IllegalArgumentException(
                        "ERROR: The vertex at this triangle's 'y' index does not exist, at Traingle"
                        + i + ".y=" + p.y + " ");
            } else if (p.z >= vertexes.length) {
                throw new IllegalArgumentException(
                        "ERROR: The vertex at this triangle's 'z' index does not exist, at Traingle"
                        + i + ".z=" + p.z + " ");
            }
        }
    }

    /**
     * <p>
     * Creates a new Object3D with the passed values.</p>
     *
     * @param location  The location of this Object3D in it's container space.
     * @param rotation  The rotation of this Object3D in it's container space.
     * @param vertexes  The vertexes of this Object3D.
     * @param triangles The triangles making up this Object3D.
     * @param colouring The colouring of each triangle making up this Object3D.
     */
    public Object3D(final FPoint3D location, final FPoint3D rotation,
                    final FPoint3D[] vertexes, final Point3D[] triangles,
                    final Color[] colouring) {
        this.location = location;
        this.rotaion = rotation;
        setModel(vertexes, triangles, colouring);
    }

}
