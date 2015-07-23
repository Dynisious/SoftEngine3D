package softEngine3D;

/**
 * <p>
 * A Point in 3 dimensional space.</p>
 *
 * @author Dynisious 14/07/2015
 * @versions 0.2.1
 */
public class Point3D {
    /**
     * <p>
     * The x coordinate of this point in 3D space.</p>
     */
    private int x;
    public void setX(int x) {
        this.x = x;
    }
    public int getX() {
        return x;
    }
    /**
     * <p>
     * The y coordinate of this point in 3D space.</p>
     */
    private int y;
    public void setY(int y) {
        this.y = y;
    }
    public int getY() {
        return y;
    }
    /**
     * <p>
     * The z coordinate of this point in 3D space.</p>
     */
    private int z;
    public void setZ(int z) {
        this.z = z;
    }
    public int getZ() {
        return z;
    }

    /**
     * <p>
     * Creates a new Point3D with the specified coordinates.</p>
     *
     * @param x The x coordinate of the Point3D.
     * @param y The y coordinate of the Point3D.
     * @param z The z coordinate of the Point3D.
     */
    public Point3D(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * <p>
     * Translates the Point3D by the using these values.</p>
     *
     * @param xTrans The translation along the x axis.
     * @param yTrans The translation along the y axis.
     * @param zTrans The translation along the z axis.
     */
    public void translate(final int xTrans, final int yTrans, final int zTrans) {
        x += xTrans;
        y += yTrans;
        z += zTrans;
    }

    /**
     * <p>
     * Translates the Point3D by the values of the passed Point3D.</p>
     *
     * @param translation The values to translate the Point3D by.
     */
    public void translate(final Point3D translation) {
        x += translation.getX();
        y += translation.getY();
        z += translation.getZ();
    }

    /**
     * <p>
     * Scales the Point3Ds X coordinate by the specified scalar.</p>
     *
     * @param scale The scalar to scale by.
     */
    public void scaleX(final double scale) {
        x *= scale;
    }

    /**
     * <p>
     * Scales the Point3Ds Y coordinate by the specified scalar.</p>
     *
     * @param scale The scalar to scale by.
     */
    public void scaleY(final double scale) {
        y *= scale;
    }

    /**
     * <p>
     * Scales the Point3Ds Z coordinate by the specified scalar.</p>
     *
     * @param scale The scalar to scale by.
     */
    public void scaleZ(final double scale) {
        z *= scale;
    }

    /**
     * <p>
     * Returns a deep copy of this Point3D.</p>
     *
     * @return a deep copy of this Point3D.
     */
    public Point3D getCopy() {
        return new Point3D(x, y, z);
    }

}
