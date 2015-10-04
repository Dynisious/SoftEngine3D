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
                    0, -Math.cos(rotaion.x), Math.sin(rotaion.x), 0,
                    0, Math.sin(rotaion.x), -Math.cos(rotaion.x), 0,
                    0, 0, 0, 1
                }
        ).multiplication(
                new double[]{
                    -Math.cos(rotaion.y), 0, -Math.sin(rotaion.y), 0,
                    0, 1, 0, 0,
                    Math.sin(rotaion.y), 0, -Math.cos(rotaion.y), 0,
                    0, 0, 0, 1
                }
        ).multiplication(
                new double[]{
                    -Math.cos(rotaion.z), Math.sin(rotaion.z), 0, 0,
                    -Math.sin(rotaion.z), -Math.cos(rotaion.z), 0, 0,
                    0, 0, 1, 0,
                    0, 0, 0, 1
                }
        ).multiplication(new FPoint3D(x, y, z)));
    }
    public FPoint3D rotaion; //The rotation on the three different axis.
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
     * @param rotaion        The rotation of this Camera in it's
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
    public Camera(final FPoint3D location, final FPoint3D rotaion,
                  final int renderDistance, final int drawOffsetX,
                  final int drawOffsetY, final double pof) {
        this.location = location;
        this.rotaion = rotaion;
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
            final TransformationMatrix trans = new TransformationMatrix(
                    new double[]{
                        1, 0, 0, 0,
                        0, Math.cos(rotaion.x), -Math.sin(rotaion.x), 0,
                        0, Math.sin(rotaion.x), Math.cos(rotaion.x), 0,
                        0, 0, 0, 1
                    }
            ).multiplication(
                    new double[]{
                        Math.cos(rotaion.y), 0, Math.sin(rotaion.y), 0,
                        0, 1, 0, 0,
                        -Math.sin(rotaion.y), 0, Math.cos(rotaion.y), 0,
                        0, 0, 0, 1
                    }
            ).multiplication(
                    new double[]{
                        Math.cos(rotaion.z), -Math.sin(rotaion.z), 0, 0,
                        Math.sin(rotaion.z), Math.cos(rotaion.z), 0, 0,
                        0, 0, 1, 0,
                        0, 0, 0, 1
                    }
            ).multiplication(
                    new double[]{
                        1, 0, 0, -location.x,
                        0, 1, 0, -location.y,
                        0, 0, 1, -location.z,
                        0, 0, 0, 1
                    }
            );
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
                //<editor-fold defaultstate="collapsed" desc="The transformation matrix for this Object3D.">
                final TransformationMatrix trans = new TransformationMatrix(
                        new double[]{
                            1, 0, 0, 0,
                            0, Math.cos(o.rotaion.x), -Math.sin(o.rotaion.x), 0,
                            0, Math.sin(o.rotaion.x), Math.cos(o.rotaion.x), 0,
                            0, 0, 0, 1
                        }
                ).multiplication(
                        new double[]{
                            Math.cos(o.rotaion.y), 0, Math.sin(o.rotaion.y), 0,
                            0, 1, 0, 0,
                            -Math.sin(o.rotaion.y), 0, Math.cos(o.rotaion.y), 0,
                            0, 0, 0, 1
                        }
                ).multiplication(
                        new double[]{
                            Math.cos(o.rotaion.z), -Math.sin(o.rotaion.z), 0, 0,
                            Math.sin(o.rotaion.z), Math.cos(o.rotaion.z), 0, 0,
                            0, 0, 1, 0,
                            0, 0, 0, 1
                        }
                ).multiplication(
                        new double[]{
                            1, 0, 0, o.location.x,
                            0, 1, 0, o.location.y,
                            0, 0, 1, o.location.z,
                            0, 0, 0, 1
                        }
                );//</editor-fold>
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
