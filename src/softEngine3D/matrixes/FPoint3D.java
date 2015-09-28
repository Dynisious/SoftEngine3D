package softEngine3D.matrixes;

/**
 * <p>
 * A point in 3 dimensional space with x, y and z coordinates represented as
 * points. Also facilitates addition and subtraction operations.</p>
 *
 * @author Dynisious 27/09/2015
 * @versions 0.0.1
 */
public class FPoint3D implements Comparable<FPoint3D> {
    /**
     * <p>
     * The x coordinate of this FPoint3D.</p>
     */
    public float x;
    /**
     * <p>
     * The y coordinate of this FPoint3D.</p>
     */
    public float y;
    /**
     * <p>
     * The z coordinate of this FPoint3D.</p>
     */
    public float z;

    /**
     * <p>
     * Creates a new FPoint3D with the passed coordinates.</p>
     *
     * @param x The x coordinate of this Point3D.
     * @param y The y coordinate of this Point3D.
     * @param z The z coordinate of this Point3D.
     */
    public FPoint3D(final float x, final float y, final float z) {
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
    public FPoint3D addition(final float x, final float y, final float z) {
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
    public FPoint3D subtraction(final float x, final float y, final float z) {
        return new FPoint3D(this.x - x, this.y - y, this.z - z);
    }
    
    @Override
    public int compareTo(FPoint3D o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
