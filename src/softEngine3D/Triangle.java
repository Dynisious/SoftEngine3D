package softEngine3D;

import java.awt.Color;
/**
 * <p>
 * Triangles are used to create 3D objects.</p>
 *
 * @author Dynisious 22/07/2015
 * @versions 0.3.2
 */
public class Triangle extends Orderable {
    /**
     * <p>
     * The number of Point3Ds a Triangle has.</p>
     */
    public static final int NumPoints = 3;
    /**
     * <p>
     * The three points of this Triangle.</p>
     */
    private Point3D[] points;
    /**
     * <p>
     * The Color to fill in this triangle with.</p>
     */
    public Color colour;

    /**
     * <p>
     * Creates a new Triangle object.</p>
     *
     * @param location The location of this Triangle in 3D space.
     * @param points   The 3 points which make up this Triangle.
     * @param colour   The Color to use to fill in this triangle.
     */
    public Triangle(final Point3D location, final Point3D[] points,
                    final Color colour) {
        final String errorMessage = "A Triangle must have exactly 3 Point3Ds.";
        if (points.length != NumPoints) { //There is an incorrect number of Point3Ds.
            throw new IndexOutOfBoundsException(errorMessage);
        }
        for (Point3D point : points) {
            if (point == null) {
                throw new IndexOutOfBoundsException(errorMessage);
            }
        }
        this.location = location;
        this.points = points;
        this.colour = colour;
    }

    /**
     * <p>
     * Translates the Triangle by the using these values.</p>
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
     * Translates the Triangle by the values of the passed Point3D.</p>
     *
     * @param translation The values to translate the Triangle by.
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
     * Rotates the Triangle around the x axis based on the radians passed.</p>
     *
     * @param radian The number of radians the Triangle is being rotated around
     *               the x axis.
     */
    public void rotateXAxis(final double radian) {
        for (Point3D point : points) {
            double hypotinuse = Math.hypot(point.getY(), point.getZ()); //The length of the line to the point on the y/z axis.
            double angle = radian + Math.atan2(point.getY(), point.getZ()); //The angle of the line to the point on the y/z axis from the z axis plus the rotation passed.
            point.setZ((int) (Math.cos(angle) * hypotinuse)); //The new z coordinate of the point3D.
            point.setY((int) (Math.sin(angle) * hypotinuse)); //The new y coordinate of the point3D.
        }
    }

    /**
     * <p>
     * Rotates the Triangle around the y axis based on the radians passed.</p>
     *
     * @param radian The number of radians the Triangle is being rotated around
     *               the y axis.
     */
    public void rotateYAxis(final double radian) {
        for (Point3D point : points) {
            double hypotinuse = Math.hypot(point.getX(), point.getZ()); //The length of the line to the point on the x/z axis.
            double angle = radian + Math.atan2(point.getZ(), point.getX()); //The angle of the line to the point on the x/z axis from the z axis plus the rotation passed.
            point.setX((int) (Math.cos(angle) * hypotinuse)); //The new x coordinate of the point3D.
            point.setZ((int) (Math.sin(angle) * hypotinuse)); //The new z coordinate of the point3D.
        }
    }

    /**
     * <p>
     * Rotates the Triangle around the z axis based on the radians passed.</p>
     *
     * @param radian The number of radians the Triangle is being rotated around
     *               the z axis.
     */
    public void rotateZAxis(final double radian) {
        for (Point3D point : points) {
            double hypotinuse = Math.hypot(point.getX(), point.getY()); //The length of the line to the point on the x/y axis.
            double angle = radian + Math.atan2(point.getY(), point.getX()); //The angle of the line to the point on the x/y axis from the y axis plus the rotation passed.
            point.setY((int) (Math.sin(angle) * hypotinuse)); //The new y coordinate of the point3D.
            point.setX((int) (Math.cos(angle) * hypotinuse)); //The new x coordinate of the point3D.
        }
    }

    /**
     * <p>
     * Returns a deep copy of all the Point3Ds that make up this Triangle
     * (or an empty array if any have a negative z value) in world space.</p>
     *
     * @return All the Point3Ds that make up this Triangle.
     */
    public Point3D[] getPoints() {
        boolean valid = true; //A boolean value indicating whether this Triangle's points should be returned.
        final Point3D[] cPoints = new Point3D[points.length]; //A deep copy of the array of Point3Ds for The triangle.
        for (int i = 0; i < points.length; i++) {
            cPoints[i] = points[i].getCopy(); //Get a deep copy of the Point3D.
            cPoints[i].translate(location); //Translate the Point3D into world space.
            if (cPoints[i].getZ() <= 0) { //The Point3D has a negative z value.
                valid = false; //No Point3Ds are to be returned.
                break; //No more Point3Ds are to be copied.
            }
        }

        if (valid) {
            return cPoints;
        } else {
            return new Point3D[0];
        }
    }

    /**
     * <p>
     * Returns a deep copy of this Triangle.</p>
     *
     * @return A deep copy of this Triangle.
     */
    public Triangle getCopy() {
        Point3D[] cPoint3Ds = new Point3D[points.length];
        for (int i = 0; i < points.length; i++) {
            cPoint3Ds[i] = points[i].getCopy();
        }
        return new Triangle(location.getCopy(), cPoint3Ds, colour);
    }

}
