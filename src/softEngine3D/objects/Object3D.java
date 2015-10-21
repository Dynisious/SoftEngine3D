package softEngine3D.objects;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
    public Triangle[] triangles; //The array of Polygons.
    public int appearanceDistance; //The distance at which this Object3D is able to be rendered.

    /**
     * <p>
     * Creates a new Object3D with the passed values. Objectively equal vertexes
     * in Triangles will be ensured to be of the same reference to decrease the
     * processing needed during rendering.</p>
     *
     * @param location           The location of this Object3D in it's container
     *                           space.
     * @param rotation           The rotation of this Object3D in it's container
     *                           space.
     * @param triangles          The triangles making up this Object3D.
     * @param appearanceDistance The maximum distance this Object3D can be from
     *                           the camera to render.
     */
    public Object3D(final FPoint3D location, final FPoint3D rotation,
                    final Triangle[] triangles, final int appearanceDistance) {
        this.location = location;
        this.rotaion = rotation;
        this.appearanceDistance = appearanceDistance;
        final LinkedList<Triangle> usedTriangles = new LinkedList<>();
        for (final Triangle triangle : triangles)
            if (!usedTriangles.contains(triangle))
                usedTriangles.add(triangle);
        this.triangles = usedTriangles.toArray(
                new Triangle[usedTriangles.size()]);

        final LinkedList<FPoint3D> usedVertexes = new LinkedList<>();
        for (final Triangle triangle : this.triangles) {
            int index;
            if ((index = usedVertexes.indexOf(triangle.getP1())) == -1) {
                usedVertexes.add(triangle.getP1());
            } else {
                triangle.setP1(usedVertexes.get(index));
            }
            if ((index = usedVertexes.indexOf(triangle.getP2())) == -1) {
                usedVertexes.add(triangle.getP2());
            } else {
                triangle.setP2(usedVertexes.get(index));
            }
            if ((index = usedVertexes.indexOf(triangle.getP3())) == -1) {
                usedVertexes.add(triangle.getP3());
            } else {
                triangle.setP3(usedVertexes.get(index));
            }
        }
    }

    /**
     * <p>
     * Creates a deep copy of this Object3D. However all the association between
     * objects in the this Object3D remain constant, that is to say that
     * if:<br/>
     * <code>p1 == p2</code> returns true in a triangle in this instance
     * then <code>p1 == p2</code> will return true in the corresponding triangle
     * in the returned copy.
     *
     * @param <T> The return type of this Object3D.
     *
     * @return A deep copy of this Object3D.
     */
    public <T extends Object3D> T copy() {
        final FPoint3D nLocation = new FPoint3D(location.x, location.y,
                location.z);
        final FPoint3D nRotaion = new FPoint3D(rotaion.x, rotaion.y, rotaion.z);
        final Triangle[] nTriangles = new Triangle[triangles.length];
        final ArrayList<FPoint3D> usedVertexes = new ArrayList<>(
                triangles.length);
        final ArrayList<FPoint3D> producedVertexes = new ArrayList<>(
                triangles.length);
        for (int i = 0; i < triangles.length; i++) { //Create a copy of each triangle but keep consistant references between vertexes.
            int index;
            FPoint3D p1;
            if ((index = usedVertexes.indexOf(triangles[i].getP1())) == -1) { //This vertex has not been used before.
                usedVertexes.add(p1 = triangles[i].getP1());
                producedVertexes.add(p1 = p1.copy());
            } else { //This vertex has been used before.
                p1 = producedVertexes.get(index);
            }
            FPoint3D p2;
            if ((index = usedVertexes.indexOf(triangles[i].getP2())) == -1) { //This vertex has not been used before.
                usedVertexes.add(p2 = triangles[i].getP2());
                producedVertexes.add(p2 = p2.copy());
            } else { //This vertex has been used before.
                p2 = producedVertexes.get(index);
            }
            FPoint3D p3;
            if ((index = usedVertexes.indexOf(triangles[i].getP3())) == -1) { //This vertex has not been used before.
                usedVertexes.add(p3 = triangles[i].getP3());
                producedVertexes.add(p3 = p3.copy());
            } else { //This vertex has been used before.
                p3 = producedVertexes.get(index);
            }
            nTriangles[i] = new Triangle(p1, p2, p3, triangles[i].isInverted());
        }
        return (T) new Object3D(nLocation, nRotaion, nTriangles,
                appearanceDistance);
    }

    /**
     * <p>
     * Orders a collection of Object3D's in descending z order.</p>
     *
     * @param o The Object3D to compare against.
     *
     * @return -1 if this Object3D has a higher z position.
     */
    @Override
    public int compareTo(final Object3D o) {
        if (location.z > o.location.z) {
            return -1;
        }
        return 0;
    }

    /**
     * @return References to all the vertexes which make up this Object3D with
     *         no duplicates.
     */
    public Set<FPoint3D> getVertexes() {
        final LinkedHashSet<FPoint3D> vertexes = new LinkedHashSet<>(
                triangles.length, 1);
        for (final Triangle triangle : triangles) {
            vertexes.add(triangle.getP1());
            vertexes.add(triangle.getP2());
            vertexes.add(triangle.getP3());
        }
        return vertexes;
    }

    private static final double QUARTER_TURN = Math.PI / 2;
    /**
     * <p>
     * Returns references to all valid Triangles that make up this Object3D.
     * <br/>Triangles are considered valid if their centers have a positive z
     * value, are within the render distance and they show their face towards
     * the origin (or away if inverted).</p>
     *
     * @param renderDistance The maximum distance a Triangle can be from the
     *                       origin.
     * @param xDifference
     * @param yDifference
     *
     * @return References to all valid Triangles.
     */
    public List<Triangle> getValidTriangles(final double renderDistance,
                                            final int xDifference,
                                            final int yDifference) {
        final LinkedList<Triangle> validTriangles = new LinkedList<>();
        for (final Triangle triangle : triangles) {
            triangle.generateCenter();
            if (triangle.getCenter().z > 0) //It's in front of the camera.
                if (triangle.getCenter().getMagnituid() < renderDistance) //It's
                    //within render distance.
                    if (Math.abs(triangle.getP1().x) < xDifference
                            && Math.abs(triangle.getP1().y) < yDifference
                            && Math.abs(triangle.getP2().x) < xDifference
                            && Math.abs(triangle.getP2().y) < yDifference
                            && Math.abs(triangle.getP3().x) < xDifference
                            && Math.abs(triangle.getP3().y) < yDifference) { //The image is within the bounds.
                        FPoint3D crossProduct = triangle.getP1().subtraction(
                                triangle.getCenter()).crossProduct(
                                        triangle.getP2()
                                        .subtraction(triangle.getCenter()));
                        final FPoint3D invCrossProduct = crossProduct.invert();
                        crossProduct = crossProduct.addition(triangle
                                .getCenter())
                                .subtraction(location).getMagnituid()
                                > invCrossProduct.addition(triangle.getCenter())
                                .subtraction(location).getMagnituid()
                                        ? crossProduct
                                        .addition(triangle.getCenter())
                                        : invCrossProduct
                                        .addition(triangle.getCenter()); //The cross
                        //product needs to point away from the center of the Object3D.
                        if (crossProduct.angleBetween(triangle.getCenter())
                                < QUARTER_TURN != triangle.isInverted()) //It's either showing it's
                            //face to the camera or it's inverted.
                            validTriangles.add(triangle);
                    }
        }
        return validTriangles;
    }

}
