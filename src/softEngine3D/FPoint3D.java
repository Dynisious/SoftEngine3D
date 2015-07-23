package softEngine3D;

/**
 * <p>
 * A Point in 3 dimensional space.</p>
 *
 * @author Dynisious 14/07/2015
 * @versions 0.0.1
 */
public class FPoint3D {
    /**
     * <p>
     * The x coordinate of this point in 3D space.</p>
     */
    private double x;
    public void setX(double x) {
        this.x = x;
    }
    public double getX() {
        return x;
    }
    /**
     * <p>
     * The y coordinate of this point in 3D space.</p>
     */
    private double y;
    public void setY(double y) {
        this.y = y;
    }
    public double getY() {
        return y;
    }
    /**
     * <p>
     * The z coordinate of this point in 3D space.</p>
     */
    private double z;
    public void setZ(double z) {
        this.z = z;
    }
    public double getZ() {
        return z;
    }

    /**
     * <p>
     * Creates a new FPoint3D with the specified coordinates.</p>
     *
     * @param x The x coordinate of the FPoint3D.
     * @param y The y coordinate of the FPoint3D.
     * @param z The z coordinate of the FPoint3D.
     */
    public FPoint3D(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * <p>
     * Translates the FPoint3D by the using these values.</p>
     *
     * @param xTrans The translation along the x axis.
     * @param yTrans The translation along the y axis.
     * @param zTrans The translation along the z axis.
     */
    public void translate(final double xTrans, final double yTrans,
                          final double zTrans) {
        x += xTrans;
        y += yTrans;
        z += zTrans;
    }

    /**
     * <p>
     * Translates the FPoint3D by the values of the passed FPoint3D.</p>
     *
     * @param translation The values to translate the FPoint3D by.
     */
    public void translate(final FPoint3D translation) {
        x += translation.getX();
        y += translation.getY();
        z += translation.getZ();
    }

    /**
     * <p>
     * Scales the FPoint3D by the specified scalar.</p>
     *
     * @param scale The scalar to scale by.
     */
    public void scale(final double scale) {
        x *= scale;
        y *= scale;
        z *= scale;
    }

    /**
     * <p>
     * Returns a deep copy of this FPoint3D.</p>
     *
     * @return a deep copy of this FPoint3D.
     */
    public FPoint3D getCopy() {
        return new FPoint3D(x, y, z);
    }

}
