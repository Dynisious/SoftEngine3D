package softEngine3D;

import java.util.ArrayList;
/**
 * <p>
 * A Object3D is a collection of triangles or other Object3Ds which move as a
 * group in 3D space.</p>
 *
 * @author Dynisious 14/07/2015
 * @versions 1.0.2
 */
public class Object3D extends Orderable {
    /**
     * <p>
     * An Array of Triangle's in this Object3D.</p>
     */
    private Triangle[] triangles;
    /**
     * <p>
     * Returns a deep copy of all the Triangles that make up this Object3D
     * (or an empty array if any have a negative z value) in world space.</p>
     *
     * @return All the Triangles that make up this Object3D.
     */
    public Triangle[] getTriangles() {
        if (triangles != null) {
            final ArrayList<Triangle> model = new ArrayList<>(triangles.length);
            if (triangles != null) {
                for (Triangle triangle : triangles) {
                    triangle = triangle.getCopy(); //Get a deep copy of the Triangle.
                    triangle.translate(location); //Translate the Triangle into world space.
                    if (triangle.getLocation().getZ() > 0) { //This Triangle has a positive z value.
                        model.add(triangle); //Add the Triangle's Lines into model.
                    }
                }
            }
            return model.toArray(new Triangle[model.size()]);
        } else {
            return new Triangle[0];
        }
    }

    /**
     * <p>
     * An empty constructor so that types which extend this class can have a
     * constructor with no parameters.</p>
     */
    protected Object3D() {

    }

    /**
     * <p>
     * Creates a new Object3D.</p>
     *
     * @param location  The location of this Object3D in world space.
     * @param triangles The Triangles that make up this Object3D.
     */
    public Object3D(final Point3D location, final Triangle[] triangles) {
        this.location = location;
        this.triangles = triangles;
    }

    /**
     * <p>
     * Translates the Object3D by the using these values.</p>
     *
     * @param xTrans The translation along the x axis.
     * @param yTrans The translation along the y axis.
     * @param zTrans The translation along the z axis.
     */
    public void translate(final int xTrans, final int yTrans, final int zTrans) {
        location.translate(xTrans, yTrans, zTrans);
    }

    /**
     * <p>
     * Translates the Object3D by the values of the passed Point3D.</p>
     *
     * @param translation The values to translate the Object3D by.
     */
    public void translate(final Point3D translation) {
        location.translate(translation);
    }

    /**
     * <p>
     * Scales the Triangle by the specified scalar.</p>
     *
     * @param xScalar The scalar to scale the x axis by.
     * @param yScalar The scalar to scale the y axis by.
     * @param zScalar The scalar to scale the z axis by.
     */
    public void scale(final double xScalar, final double yScalar,
                      final double zScalar) {
        location.scaleX(xScalar);
        location.scaleY(yScalar);
        location.scaleZ(zScalar);
    }

    /**
     * <p>
     * Rotates the Object3D around the x axis based on the radians passed.</p>
     *
     * @param radian The number of radians the Object3D is being rotated around
     *               the x axis.
     */
    public void rotateXAxis(final double radian) {
        if (triangles != null) {
            for (Triangle triangle : triangles) {
                final double hypotinuse = Math.hypot(
                        triangle.getLocation().getY(),
                        triangle.getLocation().getZ()); //The length of the Object3D on the y/z axis.
                final double angle = radian
                        + Math.atan2(triangle.getLocation().getY(),
                                triangle.getLocation().getZ()); //The angle of the Object3D on the y/z axis from the z axis plus the rotation passed.
                triangle.getLocation().setZ((int) (Math.cos(angle) * hypotinuse)); //The new z coordinate of the Object3D.
                triangle.getLocation().setY((int) (Math.sin(angle) * hypotinuse)); //The new y coordinate of the Object3D.
                triangle.rotateXAxis(radian);
            }
        }
    }

    /**
     * <p>
     * Rotates the Object3D around the y axis based on the radians passed.</p>
     *
     * @param radian The number of radians the Object3D is being rotated around
     *               the y axis.
     */
    public void rotateYAxis(final double radian) {
        if (triangles != null) {
            for (Triangle triangle : triangles) {
                final double hypotinuse = Math.hypot(
                        triangle.getLocation().getX(),
                        triangle.getLocation().getZ()); //The length of the Triangle on the x/z axis.
                final double angle = radian
                        + Math.atan2(triangle.getLocation().getZ(),
                                triangle.getLocation().getX()); //The angle of the Triangle on the z/x axis from the x axis plus the rotation passed.
                triangle.getLocation().setX((int) (Math.cos(angle) * hypotinuse)); //The new x coordinate of the Triangle.
                triangle.getLocation().setZ((int) (Math.sin(angle) * hypotinuse)); //The new z coordinate of the Triangle.
                triangle.rotateYAxis(radian);
            }
        }
    }

    /**
     * <p>
     * Rotates the Object3D around the z axis based on the radians passed.</p>
     *
     * @param radian The number of radians the Object3D is being rotated around
     *               the z axis.
     */
    public void rotateZAxis(final double radian) {
        if (triangles != null) {
            for (Triangle triangle : triangles) {
                final double hypotinuse = Math.hypot(
                        triangle.getLocation().getY(),
                        triangle.getLocation().getX()); //The length of the Triangle on the y/x axis.
                final double angle = radian
                        + Math.atan2(triangle.getLocation().getY(),
                                triangle.getLocation().getX()); //The angle of the Triangle on the y/x axis from the x axis plus the rotation passed.
                triangle.getLocation().setY((int) (Math.sin(angle) * hypotinuse)); //The new y coordinate of the Triangle.
                triangle.getLocation().setX((int) (Math.cos(angle) * hypotinuse)); //The new x coordinate of the Triangle.
                triangle.rotateZAxis(radian);
            }
        }
    }

    /**
     * <p>
     * Returns a deep copy of this Object3D.</p>
     *
     * @return A deep copy of this Object3D.
     */
    public Object3D getCopy() {
        final Triangle[] cTriangles = triangles;
        if (cTriangles != null) {
            for (int i = 0; i < cTriangles.length; i++) {
                cTriangles[i] = cTriangles[i].getCopy();
            }
        }

        return new Object3D(location.getCopy(), cTriangles);
    }

}
