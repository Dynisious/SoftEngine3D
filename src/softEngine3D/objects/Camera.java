package softEngine3D.objects;

import softEngine3D.objects.polygons.Polygon;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import softEngine3D.matrixes.*;
/**
 * <p>
 * A Camera is an object in 3D space which is able to render it's view of the 3D
 * space around it to a Graphics2D object; contains multiple values to
 * manipulate the view.</p>
 *
 * @author Dynisious 28/09/2015
 * @version 1.0.2
 */
public class Camera {
    public FPoint3D location; //The location of this Object3D in it's container space.
    /**
     * <p>
     * Translates the camera in camera space rather than it's container
     * space.</p>
     *
     * @param x The shift along the x axis.
     * @param y The shift along the y axis.
     * @param z The shift along the z axis.
     */
    public void translate(final double x, final double y, final double z) {
        location = location.addition(new TransformationMatrix(
                new double[]{
                    1, 0, 0, 0,
                    0, -Math.cos(rotation.x), Math.sin(rotation.x), 0,
                    0, Math.sin(rotation.x), -Math.cos(rotation.x), 0,
                    0, 0, 0, 1
                }
        ).multiplication(
                new double[]{
                    -Math.cos(rotation.y), 0, -Math.sin(rotation.y), 0,
                    0, 1, 0, 0,
                    Math.sin(rotation.y), 0, -Math.cos(rotation.y), 0,
                    0, 0, 0, 1
                }
        ).multiplication(
                new double[]{
                    -Math.cos(rotation.z), Math.sin(rotation.z), 0, 0,
                    -Math.sin(rotation.z), -Math.cos(rotation.z), 0, 0,
                    0, 0, 1, 0,
                    0, 0, 0, 1
                }
        ).multiplication(new FPoint3D(x, y, z)));
    }
    public FPoint3D rotation; //The rotation on the three different axis.
    public int renderDistance; //The z distance away at which objects will no
    //longer be displayed.
    public boolean wireFrame = false; //Whether the outline of polygons are rendered.
    public boolean fillPolygons = true; //Whether polygons are filled in.
    public int drawOffsetX; //The offset from the origin to draw all objects on the x axis.
    public int drawOffsetY; //The offset from the origin to draw all objects on the y axis.
    public double pof; //The distance at which vertexes appear as they are modeled.

    /**
     * <p>
     * Creates a new Camera object initialised with the passed
     * values.</p>
     *
     * @param location       The location of this Camera in it's
     *                       container
     *                       space.
     * @param rotation       The rotation of this Camera in it's
     *                       container
     *                       space.
     * @param renderDistance The distance away when objects will fail to
     *                       render.
     * @param drawOffsetX    The offset from the origin to draw all objects on
     *                       the x axis.
     * @param drawOffsetY    The offset from the origin to draw all objects on
     *                       the y axis.
     * @param pof            The <i>point of focus</i> that this Camera
     *                       is initialised with.
     */
    public Camera(final FPoint3D location, final FPoint3D rotation,
                  final int renderDistance, final int drawOffsetX,
                  final int drawOffsetY, final double pof) {
        this.location = location;
        this.rotation = rotation;
        this.renderDistance = renderDistance;
        this.drawOffsetX = drawOffsetX;
        this.drawOffsetY = drawOffsetY;
        this.pof = pof;
    }

    /**
     * <p>
     * Renders the passed objects onto the the passed graphics after performing
     * all relevant rotation, dilation and transformation operations.</p>
     * <p>
     * <b>WARNING!</b> The passed Object3D's will be modified.</p>
     *
     * @param g       The Graphics object to render the passed Object3D's on
     * @param objects The Object3D's to render.
     *
     * @throws ArithmeticException Thrown when there are more polygons to be
     *                             rendered than can be stored in a single
     *                             array.
     */
    public void render(final Graphics2D g, Object3D[] objects) {
        /*<editor-fold defaultstate="collapsed" desc="Transform Object3Ds into camera space.">*/ {
            final TransformationMatrix trans = TransformationMatrix.produceTransMatrix(
                    rotation, location.multiplication(-1));
            Arrays.setAll(objects, (final int i) -> {
                final Object3D o = objects[i].getCopy();
                o.location = trans.multiplication(o.location);
                return o;
            });
        }//</editor-fold>
        final Polygon[] polygons;
        /*<editor-fold defaultstate="collapsed" desc="Get all polygons relative to the Camera and in descending z order.">*/ {
            final ArrayList<Polygon> polys = new ArrayList<>();
            for (final Object3D o : objects) {
                final TransformationMatrix trans = TransformationMatrix.produceTransMatrix(
                        o.rotaion, o.location); //The transformation matrix for this Object3D.
                for (final Polygon p : o.polygons) {
                    p.transform(trans);
                    if (p.valid(renderDistance)) {
                        polys.add(p);
                    }
                }
            }
            polygons = polys.toArray(new Polygon[polys.size()]);
            Arrays.sort(polygons);
        }//</editor-fold>
        for (final Polygon p : polygons) {
            p.render(g);
        }
    }

}
