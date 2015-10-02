package softEngine3D.matrixes;

import java.util.Arrays;
/**
 * <p>
 * A point in 3 dimensional space with x, y and z coordinates represented as
 * points. Also facilitates addition, subtraction and multiplication
 * operations.</p>
 *
 * @author Dynisious 27/09/2015
 * @versions 0.0.1
 */
public class TransformationMatrix {
    private final static int squareSize = 4;
    public final double[] values = new double[squareSize * squareSize]; //The
    //values kept in this TransformationMatrix.
    /**
     * @param row    The row of the value to get.
     * @param column The column of the value to get.
     *
     * @return The value at the specified position.
     */
    public double getValue(final int row, final int column)
            throws ArrayIndexOutOfBoundsException {
        return values[(squareSize * row) + column];
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
        values[(squareSize * row) + column] = value;
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
        for (int row = 0; row < this.values.length; row += squareSize) {
            for (int col = 0; col < squareSize; col++) {
                for (int i = 0; i < squareSize; i++) {
                    temp[row + col] += this.values[row + i] * values[(squareSize * i) + col];
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
                ((values[squareSize] * p.x) + (values[squareSize + 1] * p.y)
                + (values[squareSize + 2] * p.z) + values[squareSize + 3]),
                ((values[2 * squareSize] * p.x) + (values[(2 * squareSize) + 1] * p.y)
                + (values[(2 * squareSize) + 2] * p.z) + values[(2 * squareSize) + 3]));
    }

}
