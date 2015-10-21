package softEngine3D.matrixes;

/**
 * <p>
 * A point in 3 dimensional space with x, y and z coordinates represented as
 * doubles. Also facilitates addition and subtraction operations.</p>
 *
 * @author Dynisious 27/09/2015
 * @version 0.0.2
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
     * Creates a new FPoint3D with zeroed values.</p>
     */
    public FPoint3D() {
    }

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
     * Returns the result of an addition transformation on this FPoint3D.</p>
     *
     * @param x The x value to add.
     * @param y The y value to add.
     * @param z The z value to add.
     *
     * @return This FPoint3D.
     */
    public FPoint3D translate(final double x, final double y, final double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * <p>
     * Returns the result of a translation transformation on this FPoint3D.</p>
     *
     * @param p The z values to add.
     *
     * @return This FPoint3D.
     */
    public FPoint3D translate(final FPoint3D p) {
        x += p.x;
        y += p.y;
        z += p.z;
        return this;
    }

    /**
     * <p>
     * Returns the result of a subtraction transformation on this FPoint3D.</p>
     *
     * @param x The x value to subtract.
     * @param y The y value to subtract.
     * @param z The z value to subtract.
     *
     * @return This FPoint3D.
     */
    public FPoint3D invTranslate(final double x, final double y, final double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     * <p>
     * Returns the result of a subtraction transformation on this FPoint3D.</p>
     *
     * @param p The z values to subtract.
     *
     * @return This FPoint3D.
     */
    public FPoint3D invTranslate(final FPoint3D p) {
        x -= p.x;
        y -= p.y;
        z -= p.z;
        return this;
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

    /**
     * <p>
     * Multiplies all of the coordinates by the passed scalar.</p>
     *
     * @param d The scalar to multiply by.
     *
     * @return This FPoint3D.
     */
    public FPoint3D scale(final double d) {
        x *= d;
        y *= d;
        z *= d;
        return this;
    }

    /**
     * @return The magnitude of this FPoint3D.
     */
    public double getMagnituid() {
        return Math.hypot(Math.hypot(x, y), z);
    }

    /**
     * <p>
     * Returns the result of a cross product operation between the two
     * FPoint3Ds.</p>
     * <p>
     * NOTE!!! The values of this FPoint3D will not be edited.</p>
     *
     * @param u The FPoint3D to perform the cross product operation with.
     *
     * @return The result of (<code>this X u</code>).
     */
    public FPoint3D crossProduct(final FPoint3D u) {
        return new FPoint3D((y * u.z) - (z * u.y),
                (z * u.x) - (x * u.z),
                (x * u.y) - (y * u.x));
    }

    /**
     * @param u The vector.
     *
     * @return The dot product of the two vectors.
     */
    public double dotProduct(final FPoint3D u) {
        return (x * u.x) + (y * u.y) + (z * u.z);
    }

    /**
     * @return Returns the unit vector in the direction of this FPoint3D.
     */
    public FPoint3D unitVector() {
        return multiplication(1 / getMagnituid());
    }

    /**
     * <p>
     * Returns the component of this FPoint3D in the direction of the passed
     * FPoint3D.</p>
     *
     * @param u The FPoint3D which represents the direction to point in.
     *
     * @return The vector resolute.
     */
    public FPoint3D vectorResolute(FPoint3D u) {
        u = u.unitVector();
        return u.multiplication(dotProduct(u));
    }

    /**
     * @param u The vector.
     *
     * @return The angle between the two vectors.
     */
    public double angleBetween(final FPoint3D u) {
        return Math.acos(dotProduct(u) / (getMagnituid() * u.getMagnituid()));
    }

    /**
     * @return Returns a new FPoint3D with inverted values for this FPoint3D.
     */
    public FPoint3D invert() {
        return new FPoint3D(-x, -y, -z);
    }

    public FPoint3D copy() {
        return new FPoint3D(x, y, z);
    }

    @Override
    public int compareTo(final FPoint3D o) {
        if (z > o.z) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.format("(x:%-1.3f, y:%-1.3f, z:%-1.3f)", x, y, z);
    }

}
