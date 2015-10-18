package softEngine3D.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
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
    public boolean wireFrame = false; //Whether the outline of triangles are rendered.
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
     * @param g       The Graphics object to render the passed Object3D's on
     * @param objects The Object3D's to render.
     *
     * @throws ArithmeticException Thrown when there are more triangles to be
     *                             rendered than can be stored in a single
     *                             array.
     */
    public void render(final Graphics2D g, Object3D[] objects) {
        final ArrayList<Object3D> transformedObject3Ds = new ArrayList<>(
                objects.length);
        for (final Object3D obj : objects) {
            obj.location = obj.location.subtraction(location); //Gets this
            //Object3D's location relative to the Camera.
            if (obj.location.getMagnituid() < renderDistance) { //This Object3D
                //is within the render distance of the Camera.
                //<editor-fold defaultstate="collapsed" desc="Transform the Object3D's vertexes to be relative to the Camera.">
                obj.rotaion = obj.rotaion.subtraction(rotation); //Rotates this Object3D to be relative to the Camera.
                final TransformationMatrix objTrans = TransformationMatrix
                        .produceTransMatrix(obj.rotaion, obj.location); //The
                //TransformationMatrix which can move all of the Object3D's
                //vertexes to be relative to the Camera.
                for (final FPoint3D vertex : obj.getVertexes()) { //Transform all vertexes.
                    int pos = 0; //The current position in the TransformationMatrix.
                    double x, y, z; //The new x, y and z values.
                    x = (vertex.x * objTrans.values[pos++])
                            + (vertex.y * objTrans.values[pos++])
                            + (vertex.z * objTrans.values[pos++])
                            + objTrans.values[pos++];
                    y = (vertex.x * objTrans.values[pos++])
                            + (vertex.y * objTrans.values[pos++])
                            + (vertex.z * objTrans.values[pos++])
                            + objTrans.values[pos++];
                    z = (vertex.x * objTrans.values[pos++])
                            + (vertex.y * objTrans.values[pos++])
                            + (vertex.z * objTrans.values[pos++])
                            + objTrans.values[pos];
                    final double perspective = pof / Math
                            .hypot(Math.hypot(x, y), z); //The scaling of this vertex based on it's distance from the camera.
                    vertex.x = x * perspective;
                    vertex.y = y * perspective;
                    vertex.z = z;
                }
                //</editor-fold>
                for (final Triangle triangle : obj.triangles) { /*Regenerate
                     the center of each triangle.*/

                    triangle.generateCenter();
                }
                transformedObject3Ds.add(obj);
            }
        }
        objects = transformedObject3Ds.toArray(
                new Object3D[transformedObject3Ds.size()]);
        Arrays.sort(objects);
        final ArrayList<Triangle> triangles = new ArrayList<>(objects.length);
        for (final Object3D object3D : objects) {
            triangles.addAll(Arrays.asList(object3D.getValidTriangles(
                    renderDistance))); //Adds the triangles to the ArrayList.
        }
        triangles.sort(triangles.get(0)); //Sort the triangles into descending order.
        g.translate(drawOffsetX, drawOffsetY);
        if (wireFrame) {
            final LinkedHashSet<FPoint3D> vertexes = new LinkedHashSet<>(
                    triangles.size());
            g.setColor(Color.BLUE);
            for (final Triangle triangle : triangles) {
                int[] x = new int[]{(int) triangle.getP1().x, (int) triangle
                    .getP2().x, (int) triangle.getP3().x};
                int[] y = new int[]{(int) triangle.getP1().y, (int) triangle
                    .getP2().y, (int) triangle.getP3().y};
                vertexes.add(triangle.getP1());
                vertexes.add(triangle.getP2());
                vertexes.add(triangle.getP3());
                g.drawPolyline(x, y, 3);
            }
            g.setColor(Color.red);
            for (final FPoint3D vertex : vertexes) {
                g.fillArc((int) vertex.x - 3, (int) vertex.y - 3, 6, 6, 0, 360);
            }
        }
    }

}
