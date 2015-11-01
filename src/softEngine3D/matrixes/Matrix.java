package softEngine3D.matrixes;

import java.util.Arrays;
/**
 * <p>
 * A point in 3 dimensional space with x, y and z coordinates represented as
 * points. Also facilitates addition, subtraction and multiplication
 * operations.</p>
 *
 * @author Dynisious 27/09/2015
 * @version 0.0.2
 */
public class Matrix {
    public final static int sideLength = 4;
    public final double[] values = new double[sideLength * sideLength]; //The
    //values kept in this Matrix.
    /**
     * @param row    The row of the value to get.
     * @param column The column of the value to get.
     *
     * @return The value at the specified position.
     */
    public double getValue(final int row, final int column)
            throws ArrayIndexOutOfBoundsException {
        return values[(sideLength * row) + column];
    }
    /**
     * <p>
     * Sets the value at the specified location.</p>
     *
     * @param row    The row of the value to get.
     * @param column The column of the value to get.
     * @param value  The value to set in the Matrix.
     *
     * @return This Matrix.
     */
    public Matrix setValue(final int row, final int column,
                                         final double value)
            throws ArrayIndexOutOfBoundsException {
        values[(sideLength * row) + column] = value;
        return this;
    }

    /**
     * <p>
     * Creates a new FMatrix3D with the passed values.</p>
     * <p>
     * <b>WARNING!</b> only the values between positions (0,0) and (3,3)
     * -inclusive- will be taken from <code>values</code></p>
     *
     * @param values The values to initialise the FMatrix3D with.
     */
    public Matrix(double[] values) throws
            ArrayIndexOutOfBoundsException {
        System.arraycopy(values, 0, this.values, 0, this.values.length);
    }

    /**
     * <p>
 Returns the result of an addition operation between this
 Matrix and
 the passed values.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
 Matrix.</p>
     *
     * @param values The values to add.
     *
     * @return The result of an addition operation between this
         Matrix and
         the passed values.
     */
    public Matrix addition(final double[] values) throws
            ArrayIndexOutOfBoundsException {
        final double[] temp = new double[this.values.length]; //The combined values.
        Arrays.setAll(temp, (final int i) -> {
            return this.values[i] + values[i];
        });
        return new Matrix(temp);
    }

    /**
     * <p>
 Returns the result of performing an addition operation between this
 Matrix and the passed Matrix.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
 Matrix.</p>
     *
     * @param m The Matrix to add to this Matrix.
     *
     * @return The result of <code>this + p</code>.
     */
    public Matrix addition(final Matrix m) {
        return new Matrix(this.values).addition(m.values);
    }

    /**
     * <p>
 Returns the result of an subtraction operation between this
 Matrix and the passed values.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
 Matrix.</p>
     *
     * @param values The values to add.
     *
     * @return The result of an subtraction operation between this
         Matrix and
         the passed values.
     */
    public Matrix subtraction(final double[] values) throws
            ArrayIndexOutOfBoundsException {
        final double[] temp = new double[this.values.length]; //The combined values.
        Arrays.setAll(temp, (final int i) -> {
            return this.values[i] - values[i];
        });
        return new Matrix(temp);
    }

    /**
     * <p>
 Returns the result of an subtraction operation between this
 Matrix and the passed Matrix3D.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
 Matrix.</p>
     *
     * @param m The Matrix3D to add.
     *
     * @return The result <code>this - m</code>.
     */
    public Matrix subtraction(final Matrix m) {
        return new Matrix(this.values).subtraction(m.values);
    }

    /**
     * <p>
 Returns the result of an multiplication operation between this
 Matrix and the passed values.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
 Matrix.</p>
     *
     * @param values The values to multiply with.
     *
     * @return The result <code>this * values</code>.
     */
    public Matrix multiplication(final double[] values) throws
            ArrayIndexOutOfBoundsException {
        final double[] temp = new double[this.values.length]; //The combined values.
        for (int row = 0; row < this.values.length; row += sideLength) {
            for (int col = 0; col < sideLength; col++) {
                for (int i = 0; i < sideLength; i++) {
                    temp[row + col] += this.values[row + i] * values[(sideLength * i) + col];
                }
            }
        }
        return new Matrix(temp);
    }

    /**
     * <p>
 Returns the result of an multiplication operation between this
 Matrix and the passed Matrix.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
 Matrix.</p>
     *
     * @param m The Matrix to multiply with.
     *
     * @return The result <code>this * m</code>.
     */
    public Matrix multiplication(final Matrix m) {
        return new Matrix(this.values).multiplication(m.values);
    }

    /**
     * <p>
 Returns the result of an multiplication operation between this
 Matrix and the passed scalar.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
 Matrix.</p>
     *
     * @param d The scalar to multiply by.
     *
     * @return The result <code>this * d</code>.
     */
    public Matrix multiplication(final double d) {
        final double[] temp = new double[values.length];
        Arrays.setAll(temp, (final int i) -> {
            return d * values[i];
        });
        return new Matrix(temp);
    }

    /**
     * <p>
 Returns the result of an multiplication operation between this
 Matrix and the passed FPoint3D.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
 Matrix.</p>
     *
     * @param p The FPoint3D to multiply with.
     *
     * @return The result <code>this * p</code>.
     */
    public FPoint3D multiplication(final FPoint3D p) {
        return new FPoint3D(
                ((values[0] * p.x) + (values[1] * p.y) + (values[2] * p.z) + values[3]),
                ((values[sideLength] * p.x) + (values[sideLength + 1] * p.y)
                + (values[sideLength + 2] * p.z) + values[sideLength + 3]),
                ((values[2 * sideLength] * p.x) + (values[(2 * sideLength) + 1] * p.y)
                + (values[(2 * sideLength) + 2] * p.z) + values[(2 * sideLength) + 3]));
    }

    /**
     * <p>
 Returns the result of an multiplication operation between this
 Matrix and the passed Point3D.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
 Matrix.</p>
     *
     * @param p The Point3D to multiply with.
     *
     * @return The result <code>this * p</code>.
     */
    public Point3D multiplication(final Point3D p) {
        return new Point3D(
                (int) (((values[0] * p.x) + (values[1] * p.y) + (values[2] * p.z) + values[3])),
                (int) (((values[sideLength] * p.x) + (values[sideLength + 1] * p.y)
                + (values[sideLength + 2] * p.z) + values[sideLength + 3])),
                (int) (((values[2 * sideLength] * p.x) + (values[(2 * sideLength) + 1] * p.y)
                + (values[(2 * sideLength) + 2] * p.z) + values[(2 * sideLength) + 3])));
    }

    /**
     * <p>
     * Performs the same operation as multiplication(FPoint3D) but it modifies
     * the values of the passed FPoint3D.</p>
     *
     * @param p The Point to modify.
     *
     * @return The FPoint3D post transformation.
     */
    public FPoint3D transform(final FPoint3D p) {
        p.x = (int) ((values[0] * p.x) + (values[1] * p.y) + (values[2] * p.z) + values[3]);
        p.y = (int) ((values[sideLength] * p.x) + (values[sideLength + 1] * p.y)
                + (values[sideLength + 2] * p.z) + values[sideLength + 3]);
        p.z = (int) ((values[2 * sideLength] * p.x) + (values[(2 * sideLength) + 1] * p.y)
                + (values[(2 * sideLength) + 2] * p.z) + values[(2 * sideLength) + 3]);
        return p;
    }

    /**
     * <p>
 Produces a Matrix which will rotate and translate a point
 in space around and along all three axis relative to the origin.</p>
     *
     * @param rotation    The rotation around each axis.
     * @param translation The translation along each axis.
     * @param scale       The scale factor along each axis.
     *
     * @return The completed Matrix.
     */
    public static Matrix produceTransMatrix(
            final FPoint3D rotation, final FPoint3D translation,
            final FPoint3D scale) {
        final double cosY = Math.cos(rotation.y);
        final double cosZ = Math.cos(rotation.z);
        final double sinZ = Math.sin(rotation.z);
        final double sinY = Math.sin(rotation.y);
        final double sinX = Math.sin(rotation.x);
        final double cosX = Math.cos(rotation.x);
        return new Matrix(
                new double[]{
                    /*<editor-fold defaultstate="collapsed" desc="Item (1,1)">*/
                    scale.x * cosY * cosZ,
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (1,2)">*/
                    scale.x * cosY * -sinZ,
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (1,3)">*/
                    scale.x * sinY,
                    /*</editor-fold>*/ translation.x,
                    /*<editor-fold defaultstate="collapsed" desc="Item (2,1)">*/
                    scale.y * ((-sinX * -sinY * cosZ) + (cosX * sinZ)),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (2,2)">*/
                    scale.y * ((-sinX * -sinY * -sinZ) + (cosX * cosZ)),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (2,3)">*/
                    scale.y * (-sinX * cosY),
                    /*</editor-fold>*/ translation.y,
                    /*<editor-fold defaultstate="collapsed" desc="Item (3,1)">*/
                    scale.z * ((cosX * -sinY * cosZ) + (sinX * sinZ)),/*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (3,2)">*/
                    scale.z * ((cosX * -sinY * -sinZ) + (sinX * cosZ)),/*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (3,3)">*/
                    scale.z * cosX * cosY,
                    /*</editor-fold>*/ translation.z,
                    0, 0, 0, 1
                }
        );
    }

    /**
     * <p>
 Produces a Matrix which will rotate and translate a point
 in space around and along all three axis relative to the origin.</p>
     *
     * @param translation The translation along each axis.
     * @param scale       The scale factor along each axis.
     *
     * @return The completed Matrix.
     */
    public static Matrix produceTransMatrixNoRotation(
            final FPoint3D translation, final FPoint3D scale) {
        return new Matrix(
                new double[]{
                    scale.x, 0, 0, translation.x,
                    0, scale.y, 0, translation.y,
                    0, 0, scale.z, translation.z,
                    0, 0, 0, 1
                }
        );
    }

    /**
     * <p>
 Produces a Matrix which will rotate and translate a point
 in space around and along all three axis relative to the origin.</p>
     *
     * @param scale The scale factor along each axis.
     *
     * @return The completed Matrix.
     */
    public static Matrix produceTransMatrixScale(
            final FPoint3D scale) {
        return new Matrix(
                new double[]{
                    scale.x, 0, 0, 0,
                    0, scale.y, 0, 0,
                    0, 0, scale.z, 0,
                    0, 0, 0, 1
                }
        );
    }

    /**
     * <p>
 Produces a Matrix which will rotate and translate a point
 in space around and along all three axis relative to the origin.</p>
     *
     * @param rotation The rotation around each axis.
     * @param scale    The scale factor along each axis.
     *
     * @return The completed Matrix.
     */
    public static Matrix produceTransMatrixNoTranslation(
            final FPoint3D rotation, final FPoint3D scale) {
        final double cosY = Math.cos(rotation.y);
        final double cosZ = Math.cos(rotation.z);
        final double sinZ = Math.sin(rotation.z);
        final double sinY = Math.sin(rotation.y);
        final double sinX = Math.sin(rotation.x);
        final double cosX = Math.cos(rotation.x);
        return new Matrix(
                new double[]{
                    /*<editor-fold defaultstate="collapsed" desc="Item (1,1)">*/
                    scale.x * cosY * cosZ,
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (1,2)">*/
                    scale.x * cosY * -sinZ,
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (1,3)">*/
                    scale.x * sinY,
                    /*</editor-fold>*/ 0,
                    /*<editor-fold defaultstate="collapsed" desc="Item (2,1)">*/
                    scale.y * ((-sinX * -sinZ * cosZ) + (cosX * sinZ)),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (2,2)">*/
                    scale.y * ((-sinX * -sinZ * -sinZ) + (cosX * cosZ)),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (2,3)">*/
                    scale.y * (-sinX * cosY),
                    /*</editor-fold>*/ 0,
                    /*<editor-fold defaultstate="collapsed" desc="Item (3,1)">*/
                    scale.z * ((cosX * -sinZ * cosZ) + (sinX * sinZ)),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (3,2)">*/
                    scale.z * ((cosX * -sinZ * -sinZ) + (sinX * cosZ)),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (3,3)">*/
                    scale.z * cosX * cosY,
                    /*</editor-fold>*/ 0,
                    0, 0, 0, 1
                }
        );
    }

    /**
     * <p>
 Produces a Matrix which will rotate and translate a point
 in space around and along all three axis relative to the origin.</p>
     *
     * @param rotation    The rotation around each axis.
     * @param translation The translation along each axis.
     *
     * @return The completed Matrix.
     */
    public static Matrix produceTransMatrixNoScale(
            final FPoint3D rotation, final FPoint3D translation) {
        final double cosY = Math.cos(rotation.y);
        final double cosZ = Math.cos(rotation.z);
        final double sinZ = Math.sin(rotation.z);
        final double sinY = Math.sin(rotation.y);
        final double sinX = Math.sin(rotation.x);
        final double cosX = Math.cos(rotation.x);
        return new Matrix(
                new double[]{
                    /*<editor-fold defaultstate="collapsed" desc="Item (1,1)">*/
                    /*<editor-fold defaultstate="collapsed" desc="Item (1,1)">*/
                    /*<editor-fold defaultstate="collapsed" desc="Item (1,1)">*/
                    /*<editor-fold defaultstate="collapsed" desc="Item (1,1)">*/
                    cosY * cosZ,
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (1,2)">*/
                    cosY * -sinZ,/*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (1,3)">*/
                    sinY,
                    /*</editor-fold>*/ translation.x,
                    /*<editor-fold defaultstate="collapsed" desc="Item (2,1)">*/
                    ((-sinX * -sinY * cosZ) + (cosX * sinZ)),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (2,2)">*/
                    ((-sinX * -sinY * -sinZ) + (cosX * cosZ)),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (2,3)">*/
                    (-sinX * cosY),
                    /*</editor-fold>*/ translation.y,
                    /*<editor-fold defaultstate="collapsed" desc="Item (3,1)">*/
                    ((cosX * -sinY * cosZ) + (sinX * sinZ)),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (3,2)">*/
                    ((cosX * -sinY * -sinZ) + (sinX * cosZ)),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (3,3)">*/
                    cosX * cosY,
                    /*</editor-fold>*/ translation.z,
                    0, 0, 0, 1
                }
        );
    }

    /**
     * <p>
 Produces a Matrix which will rotate and translate a point
 in space around and along all three axis relative to the origin.</p>
     *
     * @param rotation The rotation around each axis.
     *
     * @return The completed Matrix.
     */
    public static Matrix produceTransMatrixRotate(
            final FPoint3D rotation) {
        final double cosY = Math.cos(rotation.y);
        final double cosZ = Math.cos(rotation.z);
        final double sinZ = Math.sin(rotation.z);
        final double sinY = Math.sin(rotation.y);
        final double sinX = Math.sin(rotation.x);
        final double cosX = Math.cos(rotation.x);
        return new Matrix(
                new double[]{
                    /*<editor-fold defaultstate="collapsed" desc="Item (1,1)">*/
                    cosY * cosZ,
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (1,2)">*/
                    cosY * -sinZ,
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (1,3)">*/
                    sinY,
                    /*</editor-fold>*/ 0,
                    /*<editor-fold defaultstate="collapsed" desc="Item (2,1)">*/
                    ((-sinX * -sinY * cosZ) + (cosX * sinZ)),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (2,2)">*/
                    ((-sinX * -sinY * -sinZ) + (cosX * cosZ)),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (2,3)">*/
                    (-sinX * cosY),
                    /*</editor-fold>*/ 0,
                    /*<editor-fold defaultstate="collapsed" desc="Item (3,1)">*/
                    ((cosX * -sinY * cosZ) + (sinX * sinZ)),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (3,2)">*/
                    ((cosX * -sinY * -sinZ) + (sinX * cosZ)),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (3,3)">*/
                    cosX * cosY,
                    /*</editor-fold>*/ 0,
                    0, 0, 0, 1
                }
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof Matrix) {
            final Matrix temp = (Matrix) obj;
            if (temp.values.equals(values))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 23 * hash + Arrays.hashCode(this.values);
        return hash;
    }

}
