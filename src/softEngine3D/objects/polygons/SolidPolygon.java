package softEngine3D.objects.polygons;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import softEngine3D.matrixes.FPoint3D;
import softEngine3D.matrixes.TransformationMatrix;
/**
 * <p>
 * A SolidPolygon renders only a single colour inside it's shape.</p>
 *
 * @author Dynisious 05/10/2015
 * @version 0.0.1
 */
public class SolidPolygon extends Polygon {
    public Color colour;

    public SolidPolygon(final Color colour, final FPoint3D[] vertexes) {
        super(vertexes);
        this.colour = colour;
    }

    @Override
    public void render(final Graphics2D g) {
        g.setColor(colour);
        g.fillPolygon(getXs(), getYs(), vertexes.length);
    }

    @Override
    public Polygon transform(TransformationMatrix matrix) {
        Arrays.setAll(vertexes, (final int i) -> {
            return matrix.multiplication(vertexes[i]);
        });
        return this;
    }

    @Override
    public SolidPolygon getCopy() {
        final FPoint3D[] temp = new FPoint3D[vertexes.length];
        Arrays.setAll(temp, (final int i) -> {
            return vertexes[i].getCopy();
        });
        return new SolidPolygon(new Color(colour.getRGB(), true), temp);
    }

}
