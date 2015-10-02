package softEngine3D.matrixes;

/**
 * <p>
 * A point in 3 dimensional space with x, y and z coordinates represented as
 * doubles. Also facilitates addition and subtraction operations.</p>
 *
 * @author Dynisious 27/09/2015
 * @versions 0.0.1
 */
public class FPoint3D implements Comparable<FPoint3D> {
    /**
     * <p>
     * The x coordinate of this FPoint3D.</p>
     */
    public double x;
    /**
     * <p>
     * The y coordinate of this FPoint3D.</p>
     */
    public double y;
    /**
     * <p>
     * The z coordinate of this FPoint3D.</p>
     */
    public double z;

    /**
     * <p>
     * Creates a new FPoint3D with the passed coordinates.</p>
     *
     * @param x The x coordinate of this Point3D.
     * @param y The y coordinate of this Point3D.
     * @param z The z coordinate of this Point3D.
     */
    public FPoint3D(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * <p>
     * Returns the result of combining this FPoint3D with the passed
     * FPoint3D.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
     * FPoint3D.</p>
     *
     * @param p The FPoint3D to add to this FPoint3D.
     *
     * @return The result of <code>this + p</code>.
     */
    public FPoint3D addition(final FPoint3D p) {
        return new FPoint3D(x + p.x, y + p.y, z + p.z);
    }

    /**
     * <p>
     * Returns the result of an addition operation between this FPoint3D and the
     * passed values.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
     * FPoint3D.</p>
     *
     * @param x The x value to add.
     * @param y The y value to add.
     * @param z The z value to add.
     *
     * @return the result of an addition operation between this FPoint3D and the
     *         passed values.
     */
    public FPoint3D addition(final double x, final double y, final double z) {
        return new FPoint3D(this.x + x, this.y + y, this.z + z);
    }

    /**
     * <p>
     * Returns the result of an subtraction operation between this FPoint3D with
     * the passed FPoint3D.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
     * FPoint3D.</p>
     *
     * @param p The FPoint3D to add to this FPoint3D.
     *
     * @return The result of <code>this + p</code>.
     */
    public FPoint3D subtraction(final FPoint3D p) {
        return new FPoint3D(x - p.x, y - p.y, z - p.z);
    }

    /**
     * <p>
     * Returns the result of an subtraction operation between this FPoint3D and
     * the passed values.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
     * FPoint3D.</p>
     *
     * @param x The x value to add.
     * @param y The y value to add.
     * @param z The z value to add.
     *
     * @return the result of an subtraction operation between this FPoint3D and
     *         the passed values.
     */
    public FPoint3D subtraction(final double x, final double y, final double z) {
        return new FPoint3D(this.x - x, this.y - y, this.z - z);
    }

    /**
     * <p>
     * Returns the result of a multiplication operation on all of the
     * coordinates by the passed scalar.</p>
     *
     * @param d The scalar to multiply by.
     *
     * @return The result of the multiplication operation equal to
     *         <code>this * d</code>.
     */
    public FPoint3D multiplication(final double d) {
        return new FPoint3D(x * d, y * d, z * d);
    }

    @Override
    public int compareTo(final FPoint3D o) {
        if (z > o.z) {
            return -1;
        }
        return 0;
    }

}
