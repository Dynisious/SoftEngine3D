package softEngine3D.objects;

import dynutils.collections.sets.ArraySet;
import dynutils.collections.sets.LinkedSet;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Set;
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
public class Camera extends GraphicsZone {
    /**
     * <p>
     * Translates the camera in it's container GraphicsZone relative to the
     * Camera's orientation.</p>
     *
     * @param x The shift along the x axis.
     * @param y The shift along the y axis.
     * @param z The shift along the z axis.
     */
    public void translate(final double x, final double y, final double z) {
        location.translate(Matrix
                .produceTransMatrixRotate(rotation).multiplication(
                        new FPoint3D(x, y, z)));
    }
    /**
     * <p>
     * The maximum distance away at which Triangles will not be rendered.</p>
     */
    public int renderDistance;
    /**
     * <p>
     * True if the outline Triangles are drawn.</p>
     */
    public boolean wireFrame;
    /**
     * <p>
     * True if Triangles get filled.</p>
     */
    public boolean fillTriangles; //Whether triangles are filled in.
    /**
     * <p>
     * The offset along the x axis to draw on Graphics objects.</p>
     */
    public int drawOffsetX;
    /**
     * <p>
     * The offset along the y axis to draw on Graphics objects.</p>
     */
    public int drawOffsetY;
    /**
     * <p>
     * The z value at which vertexes get scaled by 1.</p>
     */
    public double pof;

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
     * @param triangles      The GraphicTriangles which are directly inside this
     *                       Camera as a GraphicsZone.
     * @param zones          The GraphicsZones which are directly inside this
     *                       Camera.
     * @param renderLevel    The renderLevel of this Camera.
     * @param renderDistance The distance away when objects will fail to
     *                       render.
     * @param wireFrame      True if this Camera should render the outline and
     *                       vertexes of Triangles.
     * @param fillTriangles  True if this Camera should render the textures for
     *                       Triangles.
     * @param drawOffsetX    The offset from the origin to draw all objects on
     *                       the x axis.
     * @param drawOffsetY    The offset from the origin to draw all objects on
     *                       the y axis.
     * @param pof            The <i>point of focus</i> that this Camera
     *                       is initialised with.
     */
    public Camera(final FPoint3D location, final FPoint3D rotation,
                  final LinkedSet<GraphicsTriangle> triangles,
                  final LinkedSet<GraphicsZone> zones, final int renderLevel,
                  final int renderDistance, final boolean wireFrame,
                  final boolean fillTriangles, final int drawOffsetX,
                  final int drawOffsetY, final double pof) {
        super(location, rotation, triangles, zones, renderLevel);
        this.renderDistance = renderDistance;
        this.wireFrame = wireFrame;
        this.fillTriangles = fillTriangles;
        this.drawOffsetX = drawOffsetX;
        this.drawOffsetY = drawOffsetY;
        this.pof = pof;
    }

    /**
     * <p>
     * Renders the passed objects onto the the passed graphics after performing
     * all relevant rotation, dilation and transformation operations.</p>
     * <p>
     * <b>WARNING!</b> The passed GraphicsZone's will be modified.</p>
     *
     * @param g           The Graphics object to render the passed
     *                    GraphicsZone's on
     * @param xDifference The difference along the x axis that is allowed.
     * @param yDifference The difference along the y axis that is allowed.
     * @param worldZone   The outerMost GraphicsZone which all GraphicsZones to
     *                    be rendered exists inside.
     *
     * @throws ArithmeticException Thrown when there are more triangles to be
     *                             rendered than can be stored in a single
     *                             array.
     *
     * @see softEngine3D.objects.GraphicsZone.NoRelationException
     */
    public void render(final Graphics2D g, final int xDifference,
                       final int yDifference, GraphicsZone worldZone)
            throws NullPointerException {
        if (worldZone == null)
            throw new NullPointerException("The world zone is a null value.");
        if (g == null)
            throw new NullPointerException(
                    "The graphics object is a null value.");
        final Set[] sets = putInRelation(worldZone, this, renderLevel);
        final LinkedSet<GraphicsTriangle> triangles = (LinkedSet<GraphicsTriangle>) sets[0];
        final ArraySet<FPoint3D> vertexes = (ArraySet<FPoint3D>) sets[1];
        for (final FPoint3D vertex : vertexes) {
            final double scale = pof / vertex.z;
            vertex.scale(scale);
        }
        validateTriangles(renderDistance, xDifference, yDifference, triangles); //Validate the collected Triangles.
        g.translate(drawOffsetX, drawOffsetY);
        g.scale(1, -1);
        if (wireFrame) {
            g.setColor(Color.BLUE);
            for (final Triangle triangle : triangles) {
                final int[] x = new int[]{
                    (int) triangle.p1.x,
                    (int) triangle.p2.x,
                    (int) triangle.p3.x
                };
                final int[] y = new int[]{
                    (int) triangle.p1.y,
                    (int) triangle.p2.y,
                    (int) triangle.p3.y
                };
                g.drawPolygon(x, y, 3);
            }
            g.setColor(Color.RED);
            for (final Triangle triangle : triangles) {
                g.fillArc((int) triangle.p1.x - 3,
                        (int) triangle.p1.y - 3, 6, 6, 0, 360);
                g.fillArc((int) triangle.p2.x - 3,
                        (int) triangle.p2.y - 3, 6, 6, 0, 360);
                g.fillArc((int) triangle.p3.x - 3,
                        (int) triangle.p3.y - 3, 6, 6, 0, 360);
            }
        }
    }

    private static final double QUARTER_TURN = Math.PI / 2;
    /**
     * <p>
     * Only retains valid Triangles.<br/>
     * Triangles are considered valid if and only if:<br/>
     * > The Triangle's center has a positive z.
     * > The Triangle's center is within the render distance.
     * > The Triangle's face points towards the origin.</p>
     *
     * @param renderDistance The maximum distance a Triangle is allowed to be
     *                       from the origin.
     * @param xDifference    The maximum difference along the x axis that is
     *                       allowed.
     * @param yDifference    The maximum difference along the y axis that is
     *                       allowed.
     * @param triangles      The Triangles to validate.
     */
    public static void validateTriangles(final double renderDistance,
                                         final int xDifference,
                                         final int yDifference,
                                         final Set<GraphicsTriangle> triangles) {
        final LinkedList<Triangle> validTriangles = new LinkedList<>();
        for (final GraphicsTriangle triangle : triangles) {
            triangle.generateCenter();
            if (triangle.center.z > 0) { //It's in front of the camera.
                if (triangle.center.getMagnituid() < renderDistance)  //It's
                    //within render distance.
                    if (xDifference > Math.abs(triangle.p1.x)
                            && xDifference > Math.abs(triangle.p2.x)
                            && xDifference > Math.abs(triangle.p3.x)
                            && yDifference > Math.abs(triangle.p1.y)
                            && yDifference > Math.abs(triangle.p2.y)
                            && yDifference > Math.abs(triangle.p3.y)) {
                        FPoint3D crossProduct = triangle.p1.subtraction(
                                triangle.center).crossProduct(triangle.p2
                                        .subtraction(triangle.center));
                        final FPoint3D invCrossProduct = crossProduct.invert();
                        crossProduct = crossProduct.addition(triangle.center)
                                .getMagnituid() > invCrossProduct.addition(
                                        triangle.center).getMagnituid() ? crossProduct
                                        .addition(triangle.center) : invCrossProduct
                                        .addition(triangle.center); //The cross
                        //product needs to point away from the center of the GraphicsZone.
                        if (crossProduct.angleBetween(triangle.center)
                                < QUARTER_TURN != triangle.inverted) //It's either
                            //showing it's face away from the camera or it's inverted.
                            validTriangles.add(triangle);
                    }
            }
        }
        triangles.retainAll(validTriangles);

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof Camera) {
            final Camera temp = (Camera) obj;
            if (temp.fillTriangles = fillTriangles
                    && temp.wireFrame == wireFrame
                    && temp.containerZone.equals(containerZone)
                    && temp.drawOffsetX == drawOffsetX
                    && temp.drawOffsetY == drawOffsetY
                    && temp.location.equals(location)
                    && temp.pof == pof
                    && temp.renderDistance == renderDistance
                    && temp.renderLevel == renderLevel
                    && temp.rotation.equals(rotation))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 41 * hash + this.renderDistance;
        hash = 41 * hash + (this.wireFrame ? 1 : 0);
        hash = 41 * hash + (this.fillTriangles ? 1 : 0);
        hash = 41 * hash + this.drawOffsetX;
        hash = 41 * hash + this.drawOffsetY;
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.pof) ^ (Double
                .doubleToLongBits(this.pof) >>> 32));
        return hash;
    }

}
