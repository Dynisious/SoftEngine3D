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
public class TransformationMatrix {
    public final static int sideLength = 4;
    public final double[] values = new double[sideLength * sideLength]; //The
    //values kept in this TransformationMatrix.
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
     * @return This TransformationMatrix.
     */
    public TransformationMatrix setValue(final int row, final int column,
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
    public TransformationMatrix(double[] values) throws
            ArrayIndexOutOfBoundsException {
        System.arraycopy(values, 0, this.values, 0, this.values.length);
    }

    /**
     * <p>
     * Returns the result of an addition operation between this
     * TransformationMatrix and
     * the passed values.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
     * TransformationMatrix.</p>
     *
     * @param values The values to add.
     *
     * @return The result of an addition operation between this
     *         TransformationMatrix and
     *         the passed values.
     */
    public TransformationMatrix addition(final double[] values) throws
            ArrayIndexOutOfBoundsException {
        final double[] temp = new double[this.values.length]; //The combined values.
        Arrays.setAll(temp, (final int i) -> {
            return this.values[i] + values[i];
        });
        return new TransformationMatrix(temp);
    }

    /**
     * <p>
     * Returns the result of performing an addition operation between this
     * TransformationMatrix and the passed TransformationMatrix.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
     * TransformationMatrix.</p>
     *
     * @param m The TransformationMatrix to add to this TransformationMatrix.
     *
     * @return The result of <code>this + p</code>.
     */
    public TransformationMatrix addition(final TransformationMatrix m) {
        return new TransformationMatrix(this.values).addition(m.values);
    }

    /**
     * <p>
     * Returns the result of an subtraction operation between this
     * TransformationMatrix and the passed values.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
     * TransformationMatrix.</p>
     *
     * @param values The values to add.
     *
     * @return The result of an subtraction operation between this
     *         TransformationMatrix and
     *         the passed values.
     */
    public TransformationMatrix subtraction(final double[] values) throws
            ArrayIndexOutOfBoundsException {
        final double[] temp = new double[this.values.length]; //The combined values.
        Arrays.setAll(temp, (final int i) -> {
            return this.values[i] - values[i];
        });
        return new TransformationMatrix(temp);
    }

    /**
     * <p>
     * Returns the result of an subtraction operation between this
     * TransformationMatrix and the passed Matrix3D.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
     * TransformationMatrix.</p>
     *
     * @param m The Matrix3D to add.
     *
     * @return The result <code>this - m</code>.
     */
    public TransformationMatrix subtraction(final TransformationMatrix m) {
        return new TransformationMatrix(this.values).subtraction(m.values);
    }

    /**
     * <p>
     * Returns the result of an multiplication operation between this
     * TransformationMatrix and the passed values.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
     * TransformationMatrix.</p>
     *
     * @param values The values to multiply with.
     *
     * @return The result <code>this * values</code>.
     */
    public TransformationMatrix multiplication(final double[] values) throws
            ArrayIndexOutOfBoundsException {
        final double[] temp = new double[this.values.length]; //The combined values.
        for (int row = 0; row < this.values.length; row += sideLength) {
            for (int col = 0; col < sideLength; col++) {
                for (int i = 0; i < sideLength; i++) {
                    temp[row + col] += this.values[row + i] * values[(sideLength * i) + col];
                }
            }
        }
        return new TransformationMatrix(temp);
    }

    /**
     * <p>
     * Returns the result of an multiplication operation between this
     * TransformationMatrix and the passed TransformationMatrix.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
     * TransformationMatrix.</p>
     *
     * @param m The TransformationMatrix to multiply with.
     *
     * @return The result <code>this * m</code>.
     */
    public TransformationMatrix multiplication(final TransformationMatrix m) {
        return new TransformationMatrix(this.values).multiplication(m.values);
    }

    /**
     * <p>
     * Returns the result of an multiplication operation between this
     * TransformationMatrix and the passed scalar.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
     * TransformationMatrix.</p>
     *
     * @param d The scalar to multiply by.
     *
     * @return The result <code>this * d</code>.
     */
    public TransformationMatrix multiplication(final double d) {
        final double[] temp = new double[values.length];
        Arrays.setAll(temp, (final int i) -> {
            return d * values[i];
        });
        return new TransformationMatrix(temp);
    }

    /**
     * <p>
     * Returns the result of an multiplication operation between this
     * TransformationMatrix and the passed FPoint3D.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
     * TransformationMatrix.</p>
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
     * Returns the result of an multiplication operation between this
     * TransformationMatrix and the passed Point3D.</p>
     * <p>
     * <b>WARNING!</b> This operation does not alter the values of this
     * TransformationMatrix.</p>
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
     * Produces a TransformationMatrix which will rotate and translate a point
     * in space around and along all three axis relative to the origin.</p>
     *
     * @param rotation    The rotation around each axis.
     * @param translation The translation along each axis.
     * @param scale       The scale factor along each axis.
     *
     * @return The completed TransformationMatrix.
     */
    public static TransformationMatrix produceTransMatrix(
            final FPoint3D rotation, final FPoint3D translation,
            final FPoint3D scale) {
        return new TransformationMatrix(
                new double[]{
                    /*<editor-fold defaultstate="collapsed" desc="Item (1,1)">*/
                    scale.x * Math.cos(rotation.y) * Math.cos(rotation.z),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (1,2)">*/
                    scale.x * Math.cos(rotation.y) * -Math.sin(rotation.z),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (1,3)">*/
                    scale.x * Math.sin(rotation.y),
                    /*</editor-fold>*/ translation.x,
                    /*<editor-fold defaultstate="collapsed" desc="Item (2,1)">*/
                    scale.y * ((-Math.sin(rotation.x) * -Math.sin(rotation.y)
                    * Math.cos(rotation.z))
                    + (Math.cos(rotation.x) * Math.sin(rotation.z))),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (2,2)">*/
                    scale.y * ((-Math.sin(rotation.x) * -Math.sin(rotation.y)
                    * -Math.sin(rotation.z))
                    + (Math.cos(rotation.x) * Math.cos(rotation.z))),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (2,3)">*/
                    scale.y * (-Math.sin(rotation.x) * Math.cos(rotation.y)),
                    /*</editor-fold>*/ translation.y,
                    /*<editor-fold defaultstate="collapsed" desc="Item (3,1)">*/
                    scale.z * ((Math.cos(rotation.x) * -Math.sin(rotation.y)
                    * Math.cos(rotation.z))
                    + (Math.sin(rotation.x) * Math.sin(rotation.z))),/*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (3,2)">*/
                    scale.z * ((Math.cos(rotation.x) * -Math.sin(rotation.y)
                    * -Math.sin(rotation.z))
                    + (Math.sin(rotation.x) * Math.cos(rotation.z))),/*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (3,3)">*/
                    scale.z * Math.cos(rotation.x) * Math.cos(rotation.y),
                    /*</editor-fold>*/ translation.z,
                    0, 0, 0, 1
                }
        );
    }

    /**
     * <p>
     * Produces a TransformationMatrix which will rotate and translate a point
     * in space around and along all three axis relative to the origin.</p>
     *
     * @param translation The translation along each axis.
     * @param scale       The scale factor along each axis.
     *
     * @return The completed TransformationMatrix.
     */
    public static TransformationMatrix produceTransMatrixNoRotation(
            final FPoint3D translation, final FPoint3D scale) {
        return new TransformationMatrix(
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
     * Produces a TransformationMatrix which will rotate and translate a point
     * in space around and along all three axis relative to the origin.</p>
     *
     * @param scale The scale factor along each axis.
     *
     * @return The completed TransformationMatrix.
     */
    public static TransformationMatrix produceTransMatrixScale(
            final FPoint3D scale) {
        return new TransformationMatrix(
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
     * Produces a TransformationMatrix which will rotate and translate a point
     * in space around and along all three axis relative to the origin.</p>
     *
     * @param rotation The rotation around each axis.
     * @param scale    The scale factor along each axis.
     *
     * @return The completed TransformationMatrix.
     */
    public static TransformationMatrix produceTransMatrixNoTranslation(
            final FPoint3D rotation, final FPoint3D scale) {
        return new TransformationMatrix(
                new double[]{
                    /*<editor-fold defaultstate="collapsed" desc="Item (1,1)">*/
                    scale.x * Math.cos(rotation.y) * Math.cos(rotation.z),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (1,2)">*/
                    scale.x * Math.cos(rotation.y) * -Math.sin(rotation.z),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (1,3)">*/
                    scale.x * Math.sin(rotation.y), 0,
                    /*<editor-fold defaultstate="collapsed" desc="Item (2,1)">*/
                    scale.y * ((-Math.sin(rotation.x) * -Math.sin(rotation.y)
                    * Math.cos(rotation.z))
                    + (Math.cos(rotation.x) * Math.sin(rotation.z))),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (2,2)">*/
                    scale.y * ((-Math.sin(rotation.x) * -Math.sin(rotation.y)
                    * -Math.sin(rotation.z))
                    + (Math.cos(rotation.x) * Math.cos(rotation.z))),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (2,3)">*/
                    scale.y * (-Math.sin(rotation.x) * Math.cos(rotation.y)),
                    /*</editor-fold>*/ 0,
                    /*<editor-fold defaultstate="collapsed" desc="Item (3,1)">*/
                    scale.z * ((Math.cos(rotation.x) * -Math.sin(rotation.y)
                    * Math.cos(rotation.z))
                    + (Math.sin(rotation.x) * Math.sin(rotation.z))),/*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (3,2)">*/
                    scale.z * ((Math.cos(rotation.x) * -Math.sin(rotation.y)
                    * -Math.sin(rotation.z))
                    + (Math.sin(rotation.x) * Math.cos(rotation.z))),/*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (3,3)">*/
                    scale.z * Math.cos(rotation.x) * Math.cos(rotation.y),
                    /*</editor-fold>*/ 0,
                    0, 0, 0, 1
                }
        );
    }

    /**
     * <p>
     * Produces a TransformationMatrix which will rotate and translate a point
     * in space around and along all three axis relative to the origin.</p>
     *
     * @param rotation    The rotation around each axis.
     * @param translation The translation along each axis.
     *
     * @return The completed TransformationMatrix.
     */
    public static TransformationMatrix produceTransMatrixNoScale(
            final FPoint3D rotation, final FPoint3D translation) {
        return new TransformationMatrix(
                new double[]{
                    /*<editor-fold defaultstate="collapsed" desc="Item (1,1)">*/
                    Math.cos(rotation.y) * Math.cos(rotation.z),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (1,2)">*/
                    Math.cos(rotation.y) * -Math.sin(rotation.z),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (1,3)">*/
                    Math.sin(rotation.y),
                    /*</editor-fold>*/ translation.x,
                    /*<editor-fold defaultstate="collapsed" desc="Item (2,1)">*/
                    ((-Math.sin(rotation.x) * -Math.sin(rotation.y)
                    * Math.cos(rotation.z))
                    + (Math.cos(rotation.x) * Math.sin(rotation.z))),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (2,2)">*/
                    ((-Math.sin(rotation.x) * -Math.sin(rotation.y)
                    * -Math.sin(rotation.z))
                    + (Math.cos(rotation.x) * Math.cos(rotation.z))),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (2,3)">*/
                    (-Math.sin(rotation.x) * Math.cos(rotation.y)),
                    /*</editor-fold>*/ translation.y,
                    /*<editor-fold defaultstate="collapsed" desc="Item (3,1)">*/
                    ((Math.cos(rotation.x) * -Math.sin(rotation.y)
                    * Math.cos(rotation.z))
                    + (Math.sin(rotation.x) * Math.sin(rotation.z))),/*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (3,2)">*/
                    ((Math.cos(rotation.x) * -Math.sin(rotation.y)
                    * -Math.sin(rotation.z))
                    + (Math.sin(rotation.x) * Math.cos(rotation.z))),/*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (3,3)">*/
                    Math.cos(rotation.x) * Math.cos(rotation.y),
                    /*</editor-fold>*/ translation.z,
                    0, 0, 0, 1
                }
        );
    }

    /**
     * <p>
     * Produces a TransformationMatrix which will rotate and translate a point
     * in space around and along all three axis relative to the origin.</p>
     *
     * @param rotation The rotation around each axis.
     *
     * @return The completed TransformationMatrix.
     */
    public static TransformationMatrix produceTransMatrixRotate(
            final FPoint3D rotation) {
        return new TransformationMatrix(
                new double[]{
                    /*<editor-fold defaultstate="collapsed" desc="Item (1,1)">*/
                    Math.cos(rotation.y) * Math.cos(rotation.z),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (1,2)">*/
                    Math.cos(rotation.y) * -Math.sin(rotation.z),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (1,3)">*/
                    Math.sin(rotation.y),
                    /*</editor-fold>*/ 0,
                    /*<editor-fold defaultstate="collapsed" desc="Item (2,1)">*/
                    ((-Math.sin(rotation.x) * -Math.sin(rotation.y)
                    * Math.cos(rotation.z))
                    + (Math.cos(rotation.x) * Math.sin(rotation.z))),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (2,2)">*/
                    ((-Math.sin(rotation.x) * -Math.sin(rotation.y)
                    * -Math.sin(rotation.z))
                    + (Math.cos(rotation.x) * Math.cos(rotation.z))),
                    /*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (2,3)">*/
                    (-Math.sin(rotation.x) * Math.cos(rotation.y)),
                    /*</editor-fold>*/ 0,
                    /*<editor-fold defaultstate="collapsed" desc="Item (3,1)">*/
                    ((Math.cos(rotation.x) * -Math.sin(rotation.y)
                    * Math.cos(rotation.z))
                    + (Math.sin(rotation.x) * Math.sin(rotation.z))),/*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (3,2)">*/
                    ((Math.cos(rotation.x) * -Math.sin(rotation.y)
                    * -Math.sin(rotation.z))
                    + (Math.sin(rotation.x) * Math.cos(rotation.z))),/*</editor-fold>*//*<editor-fold defaultstate="collapsed" desc="Item (3,3)">*/
                    Math.cos(rotation.x) * Math.cos(rotation.y),
                    /*</editor-fold>*/ 0,
                    0, 0, 0, 1
                }
        );
    }

}
