package softEngine3D.matrixes;

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
    public final float[][] values = new float[4][4]; //The values kept in this
    //FTransformationMatrix.
    /**
     * @param row    The row of the value to get.
     * @param column The column of the value to get.
     *
     * @return The value at the specified position.
     */
    public float getValue(final int row, final int column) {
        return values[row][column];
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
    public TransformationMatrix(float[][] values) throws
            ArrayIndexOutOfBoundsException {
        for (int row = 0; row < 4; row++) {
            System.arraycopy(values[row], 0, this.values[row], 0, 4);
        }
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
    public TransformationMatrix addition(final float[][] values) throws
            ArrayIndexOutOfBoundsException {
        final float[][] temp = new float[4][4]; //The combined values.
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                temp[row][col] = this.values[row][col] + values[row][col];
            }
        }
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
    public TransformationMatrix subtraction(final float[][] values) throws
            ArrayIndexOutOfBoundsException {
        final float[][] temp = new float[4][4]; //The combined values.
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                temp[row][col] = this.values[row][col] - values[row][col];
            }
        }
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
    public TransformationMatrix multiplication(final float[][] values) throws
            ArrayIndexOutOfBoundsException {
        final float[][] temp = new float[4][4]; //The combined values.
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                for (int i = 0; i < 4; i++) {
                    temp[row][col] = this.values[row][i] * values[i][col];
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
                (this.values[0][0] * p.x) + (this.values[0][1] * p.y) + (this.values[0][2] * p.z),
                (this.values[1][0] * p.x) + (this.values[1][1] * p.y) + (this.values[1][2] * p.z),
                (this.values[2][0] * p.x) + (this.values[2][1] * p.y) + (this.values[2][2] * p.z)
        );
    }

}
