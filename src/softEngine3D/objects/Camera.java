package softEngine3D.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import softEngine3D.matrixes.*;
/**
 * <p>
 * </p>
 *
 * @author Dynisious 28/09/2015
 * @versions 0.0.1
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
    public double renderDistance; //The z distance away at which objects will no
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
                  final double renderDistance, final int drawOffsetX,
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
    public void render(final Graphics2D g, final Object3D[] objects) {
        class Polygon { //Contains all the data for a polygon to be rendered.
            public final FPoint3D[] vertexes; //The vertexes that make up this polygon.
            private int[] x; //The x coordinates of this Polygon.
            private int[] y; //The y coordinates of this Polygon.
            public final double distance; //The distance from the camera of this polygon in 3D space.
            public final Color colour; //The colour to fill the polygon with when it is renderd.

            /**
             * <p>
             * Creates a new Polygon with the passed values.</p>
             *
             * @param vertexes The vertexes that make up this polygon.
             * @param distance The distance from the camera of this polygon.
             * @param colour   The colour to fill this Polygon.
             */
            public Polygon(final FPoint3D[] vertexes, final Color colour,
                           final double distance) {
                this.vertexes = vertexes;
                this.colour = colour;
                this.distance = distance;
            }

            /**
             * @return The array of x coordinates for this Polygon.
             */
            public int[] getXs() {
                if (x == null) {
                    x = new int[vertexes.length];
                    Arrays.setAll(x, (final int i) -> {
                        return (int) vertexes[i].x + drawOffsetX;
                    });
                }
                return x;
            }

            /**
             * @return The array of y coordinates for this Polygon.
             */
            public int[] getYs() {
                if (y == null) {
                    y = new int[vertexes.length];
                    Arrays.setAll(y, (final int i) -> {
                        return (int) vertexes[i].y + drawOffsetY;
                    });
                }
                return y;
            }

        }
        //<editor-fold defaultstate="collapsed" desc="Create the transformation matrix which can convert points into camera space.">
        final TransformationMatrix cameraSpace = new TransformationMatrix(
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
        );//</editor-fold>
        final ArrayList<Polygon> polygons = new ArrayList<>(); //The polygons which are to be renderd.
        //<editor-fold defaultstate="collapsed" desc="Transform all vertexes and get all polygons.">
        Arrays.asList(objects).forEach((Object3D o) -> {
            o = o.getCopy();
            //<editor-fold defaultstate="collapsed" desc="The transformation matrix for this Object.">
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
            ).setValue(0, 3, o.location.x
            ).setValue(1, 3, o.location.y
            ).setValue(2, 3, o.location.z);
            //</editor-fold>

            final FPoint3D[] vertexes = o.getVertexes();
            //<editor-fold defaultstate="collapsed" desc="Transform/Filter out all Vertexes.">
            Arrays.setAll(vertexes, (final int v) -> {
                FPoint3D vertex = cameraSpace.multiplication(
                        trans.multiplication(vertexes[v]));
                if (vertex.z > 0) { //It's infront of the camera.
                    if (Math.hypot(Math.hypot(vertex.x, vertex.y), vertex.z) > renderDistance) { //It's outside of render distance.
                        vertex = null;
                    }
                } else { //It's behind the camera.
                    vertex = null;
                }
                return vertex;
            });
            //</editor-fold>

            final Color[] colouring = o.getColouring();
            final int[][] polys = o.getPolygons();
            //<editor-fold defaultstate="collapsed" desc="Get all polygons from this Object3D.">
            for (int p = 0; p < polys.length; p++) {
                final int[] polygon = polys[p];
                boolean valid = true; //Switched to false if the polygon is invalid.
                final FPoint3D[] vtx = new FPoint3D[polygon.length];
                FPoint3D centeriod = new FPoint3D(0, 0, 0);
                final double distance;
                for (int i = 0; i < polygon.length; i++) { //Check all the vertexes that this polygon references.
                    final FPoint3D v = vertexes[polygon[i]];
                    if (v == null) { //Null means the vertex was considered invalid.
                        valid = false;
                        break;
                    } else {
                        vtx[i] = v; //Get store this vertex for the polygon.
                        centeriod = centeriod.addition(v.x, v.y, v.z);
                    }
                }
                if (valid) {
                    centeriod = centeriod.multiplication(1.0 / vtx.length);
                    distance = Math.hypot(Math.hypot(centeriod.x,
                            centeriod.y), centeriod.z);
                    polygons.add(new Polygon(vtx, colouring[p], distance));
                }
            }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Transform all vertexes to give the perspective of depth when rendered.">
            for (int i = 0; i < vertexes.length; i++) {
                final FPoint3D vertex = vertexes[i];
                if (vertex != null) {
                    final FPoint3D v = vertex.multiplication(pof / Math.hypot(
                            Math.hypot(vertex.x, vertex.y), vertex.z));
                    vertex.x = v.x;
                    vertex.y = v.y;
                    vertex.z = v.z;
                }
            }
            //</editor-fold>
        });//</editor-fold>

        polygons.sort((final Polygon o1, final Polygon o2) -> { //Sort all polygons to be in descending order of distance.
            if (o1.distance > o2.distance) {
                return -1;
            }
            return 0;
        });

        //<editor-fold defaultstate="collapsed" desc="Render Polygons.">
        if (fillPolygons) {
            polygons.forEach((final Polygon polygon) -> {
                g.setColor(polygon.colour);
                g.fillPolygon(polygon.getXs(), polygon.getYs(),
                        polygon.vertexes.length);
            });
        }
        if (wireFrame) {
            g.setColor(Color.BLUE);
            polygons.forEach((final Polygon polygon) -> {
                g.drawPolygon(polygon.getXs(), polygon.getYs(),
                        polygon.vertexes.length);
            });
            g.setColor(Color.RED);
            polygons.forEach((final Polygon polygon) -> {
                for (int i = 0; i < polygon.vertexes.length; i++) {
                    g.fillArc(polygon.getXs()[i] - 3, polygon.getYs()[i] - 3, 6,
                            6, 0, 360);
                }
            });
            polygons.forEach((final Polygon polygon) -> {
                g.setColor(polygon.colour);
            });
        }
        //</editor-fold>
    }

}
