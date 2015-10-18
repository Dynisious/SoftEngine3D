package softEngine3D.objects;

import java.util.ArrayList;
import java.util.LinkedHashSet;
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

    /**
     * <p>
     * Creates a new Object3D with the passed values. Objectively equal vertexes
     * in Triangles will be ensured to be of the same reference to decrease the
     * processing needed during rendering.</p>
     *
     * @param location  The location of this Object3D in it's container space.
     * @param rotation  The rotation of this Object3D in it's container space.
     * @param triangles The triangles making up this Object3D.
     */
    public Object3D(final FPoint3D location, final FPoint3D rotation,
                    final Triangle[] triangles) {
        this.location = location;
        this.rotaion = rotation;
        final ArrayList<FPoint3D> usedVertexes = new ArrayList<>(
                triangles.length);
        for (final Triangle triangle : triangles) {
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
        this.triangles = triangles;
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
        return (T) new Object3D(nLocation, nRotaion, nTriangles);
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
    public FPoint3D[] getVertexes() {
        final LinkedHashSet<FPoint3D> vertexes = new LinkedHashSet<>(
                triangles.length, 1);
        for (final Triangle triangle : triangles) {
            vertexes.add(triangle.getP1());
            vertexes.add(triangle.getP2());
            vertexes.add(triangle.getP3());
        }
        return vertexes.toArray(new FPoint3D[vertexes.size()]);
    }

    /**
     * <p>
     * Returns references to all valid Triangles that make up this Object3D.
     * <br/>Triangles are considered valid if their centers have a positive z
     * value, are within the render distance and they show their face to the
     * origin.</p>
     *
     * @param renderDistance The maximum distance a Triangle can be from the
     *                       origin.
     *
     * @return References to all valid Triangles.
     */
    public Triangle[] getValidTriangles(final double renderDistance) {
        FPoint3D origin = new FPoint3D(-location.x, -location.y, -location.z);/*
         The vector from the location of the Object3D to the origin.*/

        final ArrayList<Triangle> validTriangles = new ArrayList<>(
                triangles.length);
        final double quarterTurn = Math.PI / 2;
        for (final Triangle triangle : triangles) {
            if (triangle.getCenter().z > 0)
                if (triangle.getCenter().getMagnituid() < renderDistance)
                    if (triangle.getCenter().angleBetween(origin) < quarterTurn
                            != triangle.isInverted())
                        validTriangles.add(triangle);
        }
        return validTriangles.toArray(new Triangle[validTriangles.size()]);
    }

}
