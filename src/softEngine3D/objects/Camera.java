package softEngine3D.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedHashSet;
import java.util.LinkedList;
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
    public boolean wireFrame = true; //Whether the outline of triangles are rendered.
    public boolean fillPolygons = true; //Whether triangles are filled in.
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
     * @param g           The Graphics object to render the passed Object3D's on
     * @param xDifference The difference along the x axis that is allowed.
     * @param yDifference The difference along the y axis that is allowed.
     * @param objects     The Object3D's to render.
     *
     * @throws ArithmeticException Thrown when there are more triangles to be
     *                             rendered than can be stored in a single
     *                             array.
     */
    public void render(final Graphics2D g, final int xDifference,
                       final int yDifference, Object3D... objects) {
        if (objects.length != 0) {
            final TransformationMatrix camTrans = TransformationMatrix
                    .produceTransMatrixRotate(rotation.invert());
            final LinkedList<Object3D> toRender = new LinkedList<>();
            final LinkedHashSet<FPoint3D> allVertexes = new LinkedHashSet<>();
            for (Object3D object : objects) {
                object = object.copy();
                camTrans.transform(object.location.invTranslate(location));
                object.rotaion.invTranslate(rotation);
                if (object.location.getMagnituid() < object.appearanceDistance
                        && object.location.z > 0) {
                    toRender.add(object);
                    final TransformationMatrix objTrans = TransformationMatrix
                            .produceTransMatrixNoScale(object.rotaion,
                                    object.location);
                    for (final FPoint3D point : object.getVertexes()) {
                        if (allVertexes.contains(point))
                            continue;
                        allVertexes.add(point);
                        objTrans.transform(point);
                        final double scale = pof / point.z;
                        TransformationMatrix.produceTransMatrixScale(
                                new FPoint3D(scale, scale, 1))
                                .transform(point);
                    }
                }
            }
            if (!toRender.isEmpty()) {
                g.translate(drawOffsetX, drawOffsetY);
                g.scale(-1, -1);
                final LinkedList<Triangle> allTriangles = new LinkedList<>();
                for (final Object3D object : toRender)
                    allTriangles.addAll(object.getValidTriangles(renderDistance,
                            xDifference, yDifference));
                if (wireFrame && !allTriangles.isEmpty()) {
                    g.setColor(Color.BLUE);
                    final LinkedHashSet<FPoint3D> vertexes = new LinkedHashSet(
                            allTriangles.size() * 3);
                    final int[] x = new int[3];
                    final int[] y = new int[3];
                    for (final Triangle triangle : allTriangles) {
                        vertexes.add(triangle.getP1());
                        vertexes.add(triangle.getP2());
                        vertexes.add(triangle.getP3());
                        x[0] = (int) triangle.getP1().x;
                        x[1] = (int) triangle.getP2().x;
                        x[2] = (int) triangle.getP3().x;
                        y[0] = (int) triangle.getP1().y;
                        y[1] = (int) triangle.getP2().y;
                        y[2] = (int) triangle.getP3().y;
                        g.drawPolygon(x, y, 3);
                    }
                    g.setColor(Color.red);
                    for (final FPoint3D vertex : vertexes)
                        g.fillArc((int) (vertex.x - 2),
                                (int) (vertex.y - 2), 5, 5, 0, 360);
                }
            }
        }
    }

}
