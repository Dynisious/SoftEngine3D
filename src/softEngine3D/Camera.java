package softEngine3D;

import java.awt.Color;
import java.awt.Graphics;
/**
 * <p>
 * A Camera Object is used to provide a point of view into the 3D
 * environment.</p>
 *
 * @author Dynisious 20/07/2015
 * @versions 1.0.3
 */
public class Camera {
    /**
     * <p>
     * The location of the camera in 3D space.</p>
     */
    protected Point3D location;
    /**
     * <p>
     * The rotation around the x/y/z axis in radians.</p>
     */
    protected FPoint3D rotation;
    public void setRotation(FPoint3D rotation) {
        this.rotation = rotation;
    }
    public FPoint3D getRotation() {
        return rotation;
    }
    /**
     * <p>
     * The distortion of depth relative to the camera.</p>
     */
    private double zoom;
    /**
     * <p>
     * The x offset to apply when rendering.</p>
     */
    private int centerX;
    /**
     * <p>
     * The y offset to apply when rendering.</p>
     */
    private int centerY;
    /**
     * <p>
     * Controls whether the outline of all triangles get drawn.</p>
     */
    private static final boolean outline = true;
    /**
     * <p>
     * Controls whether the triangles get coloured in.</p>
     */
    private static final boolean colouring = true;

    /**
     * <p>
     * Creates a new Camera object.</p>
     *
     * @param location The location of this Camera in 3D space.
     * @param rotation The Rotation around the x/y/z axis.
     * @param zoom     The distortion of depth along the z Axis.
     * @param centerX  The x coordinate of the center of the draw space.
     * @param centerY  The y coordinate of the center of the draw space.
     */
    public Camera(final Point3D location, final FPoint3D rotation,
                  final double zoom, final int centerX, final int centerY) {
        this.location = location;
        this.rotation = rotation;
        this.zoom = zoom;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    /**
     * <p>
     * Translates the Camera by the using these values.</p>
     *
     * @param xTrans The translation along the x axis.
     * @param yTrans The translation along the y axis.
     * @param zTrans The translation along the z axis.
     */
    public void translate(final int xTrans, final int yTrans, final int zTrans) {
        location.translate(xTrans, yTrans, zTrans);
    }

    /**
     * <p>
     * Translates the Camera by the values of the passed Point3D.</p>
     *
     * @param translation The values to translate the Camera by.
     */
    public void translate(final Point3D translation) {
        location.translate(translation);
    }

    /**
     * <p>
     * Renders the passed Object3Ds on the passed Graphics object.</p>
     *
     * @param g       The Graphics object to draw on.
     * @param objects The Objects to render.
     */
    public void render(final Graphics g, Object3D[] objects) {
        objects = ZOrderer.orderObject3D(objects, true);
        for (Object3D object : objects) {
            drawTriangles(g, cameraSpace(object));
        }
    }

    /**
     * <p>
     * Returns all of the Objects in camera space scaled and positioned relative
     * to the camera in descending z order.</p>
     *
     * @param objects The Object3Ds being put into camera space.
     *
     * @return An array of deep copies of objects in camera space.
     */
    private Triangle[] cameraSpace(Object3D object) {
        object = object.getCopy(); //Get a deep copy of the Object3D.
        object.translate(location); //Translate the Object3D to camera space.
        //<editor-fold defaultstate="collapsed" desc="X axis rotation">
        {
            final double hypotinuse = Math.hypot(object.location.getY(),
                    object.location.getZ()); //The length of the line on the y/z axis.
            final double angle = rotation.getX() + Math.atan2(
                    object.location.getY(), object.location.getZ()); //The angle of the line on the y/z axis from the z axis plus the rotation passed.
            object.location.setZ((int) (Math.cos(angle) * hypotinuse)); //The new z coordinate of the line.
            object.location.setY((int) (Math.sin(angle) * hypotinuse)); //The new y coordinate of the line.
            object.rotateXAxis(rotation.getX());
        }//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Y axis rotation">
        {
            final double hypotinuse = Math.hypot(object.location.getX(),
                    object.location.getZ()); //The length of the line on the x/z axis.
            final double angle = rotation.getY() + Math.atan2(
                    object.location.getZ(), object.location.getX()); //The angle of the line on the z/x axis from the x axis plus the rotation passed.
            object.location.setX((int) (Math.cos(angle) * hypotinuse)); //The new x coordinate of the line.
            object.location.setZ((int) (Math.sin(angle) * hypotinuse)); //The new z coordinate of the line.
            object.rotateYAxis(rotation.getY());
        }//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Z axis rotation">
        {
            final double hypotinuse = Math.hypot(object.location.getY(),
                    object.location.getX()); //The length of the line on the y/x axis.
            final double angle = rotation.getZ() + Math.atan2(
                    object.location.getY(), object.location.getX()); //The angle of the line on the y/x axis from the x axis plus the rotation passed.
            object.location.setX((int) (Math.cos(angle) * hypotinuse)); //The new x coordinate of the line.
            object.location.setY((int) (Math.sin(angle) * hypotinuse)); //The new y coordinate of the line.
            object.rotateZAxis(rotation.getZ());
        }//</editor-fold>

        return ZOrderer.orderTriangle(object.getTriangles(), true);
    }

    /**
     * <p>
     * Draws the triangles to the Graphics.</p>
     *
     * @param g         The Graphics to draw to.
     * @param triangles The Triangles to draw.
     */
    private void drawTriangles(final Graphics g, final Triangle[] triangles) {
        for (final Triangle triangle : triangles) {
            final int[] xPoints = new int[Triangle.NumPoints];
            final int[] yPoints = new int[Triangle.NumPoints];
            int i = 0; //The current index in the two 'points' arrays.
            for (final Point3D point : triangle.getPoints()) {
                final double depth = zoom / Math.hypot(point.getX(),
                        Math.hypot(point.getY(), point.getZ())); //The scalar of depth for this Point3D.
                point.scaleX(depth);
                xPoints[i] = point.getX() + centerX;
                point.scaleY(depth);
                yPoints[i] = point.getY() + centerY;
                i++;
            }
            //Color[] col = new Color[]{/*Color.BLACK, Color.CYAN, Color.GRAY,
            //    Color.MAGENTA, Color.ORANGE, Color.PINK,*/ Color.GREEN};
            //g.setColor(col[(int) (Math.random() * col.length)]);
            if (colouring) {
                g.setColor(triangle.colour);
                g.fillPolygon(xPoints, yPoints, Triangle.NumPoints);
            }
            if (outline) {
                g.setColor(Color.BLUE);
                g.drawPolygon(xPoints, yPoints, Triangle.NumPoints);
            }
        }
    }

}
