package softEngine3D.matrixes;

/**
 * <p>
 * A point in 3 dimensional space with x, y and z coordinates represented as
 * points. Also facilitates addition and subtraction operations.</p>
 *
 * @author Dynisious 27/09/2015
 * @version 0.0.2
 */
public class Point3D implements Comparable<Point3D> {
    /**
     * <p>
     * The x coordinate of this Point3D.</p>
     */
    public int x;
    /**
     * <p>
     * The y coordinate of this Point3D.</p>
     */
    public int y;
    /**
     * <p>
     * The z coordinate of this Point3D.</p>
     */
    public int z;

    /**
     * <p>
     * Creates a new Point3D with zeroed values.</p>
     */
    public Point3D() {
    }

    /**
     * <p>
     * Creates a new Point3D with the passed coordinates.</p>
     *
     * @param x The x coordinate of this Point3D.
     * @param y The y coordinate of this Point3D.
     * @param z The z coordinate of this Point3D.
     */
    public Point3D(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * <p>
     * Returns the result of combining this Point3D with the passed
     * Point3D.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
     * Point3D.</p>
     *
     * @param p The Point3D to add to this Point3D.
     *
     * @return The result of <code>this + p</code>.
     */
    public Point3D addition(final Point3D p) {
        return new Point3D(x + p.x, y + p.y, z + p.z);
    }

    /**
     * <p>
     * Returns the result of an addition operation between this Point3D and the
     * passed values.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
     * Point3D.</p>
     *
     * @param x The x value to add.
     * @param y The y value to add.
     * @param z The z value to add.
     *
     * @return the result of an addition operation between this Point3D and the
     *         passed values.
     */
    public Point3D addition(final int x, final int y, final int z) {
        return new Point3D(this.x + x, this.y + y, this.z + z);
    }

    /**
     * <p>
     * Returns the result of an subtraction operation between this Point3D with
     * the passed Point3D.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
     * Point3D.</p>
     *
     * @param p The Point3D to add to this Point3D.
     *
     * @return The result of <code>this + p</code>.
     */
    public Point3D subtraction(final Point3D p) {
        return new Point3D(x - p.x, y - p.y, z - p.z);
    }

    /**
     * <p>
     * Returns the result of an subtraction operation between this Point3D and
     * the passed values.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
     * Point3D.</p>
     *
     * @param x The x value to add.
     * @param y The y value to add.
     * @param z The z value to add.
     *
     * @return the result of an subtraction operation between this Point3D and
     *         the passed values.
     */
    public Point3D subtraction(final int x, final int y, final int z) {
        return new Point3D(this.x - x, this.y - y, this.z - z);
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
    public Point3D multiplication(final double d) {
        return new Point3D((int) (x * d), (int) (y * d), (int) (z * d));
    }

    /**
     * @return The magnitude of this Point3D.
     */
    public double getMagnituid() {
        return Math.hypot(Math.hypot(x, y), z);
    }

    /**
     * @param u The vector.
     *
     * @return The dot product of the two vectors.
     */
    public double dotProduct(final Point3D u) {
        return (x * (double) u.x) + (y * (double) u.y) + (z * (double) u.z);
    }

    /**
     * @param u The vector.
     *
     * @return The angle between the two vectors.
     */
    public double angleBetween(final Point3D u) {
        return Math.acos(dotProduct(u) / (getMagnituid() * u.getMagnituid()));
    }

    public Point3D copy() {
        return new Point3D(x, y, z);
    }

    @Override
    public int compareTo(final Point3D o) {
        if (z > o.z) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "(x:" + x + ", y:" + y + ", z:" + z + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof Point3D) {
            final Point3D temp = (Point3D) obj;
            if (temp.x == x
                    && temp.y == y
                    && temp.z == z)
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 41 * hash + this.x;
        hash = 41 * hash + this.y;
        return hash;
    }

}
