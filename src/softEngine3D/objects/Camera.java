package softEngine3D.objects;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;
import softEngine3D.matrixes.FPoint3D;
/**
 * <p>
 * </p>
 *
 * @author Dynisious 28/09/2015
 * @versions 0.0.1
 */
public class Camera {
    public FPoint3D location; //The location of this Object3D in it's container space.
    public FPoint3D rotaion; //The rotation on the three different axis.
    public double zoom; //The zoom of this camera.
    public double pof; //The pof is the distance when each Object3D is the same
    //size as it is defined in the model.
    public float renderDistance; //The z distance away at which objects will no
    //longer be displayed.

    public Camera() {
    }

    /**
     * <p>
     * Creates a new Camera object initialised with the passed values.</p>
     *
     * @param location The location of this Camera in it's container space.
     * @param rotaion  The rotation of this Camera in it's container space.
     * @param zoom     The zoom factor that this Camera is initialised with.
     * @param pof      The <i>point of focus</i> that this Camera is initialised
     *                 with.
     */
    public Camera(final FPoint3D location, final FPoint3D rotaion,
                  final double zoom, final double pof) {
        this.location = location;
        this.rotaion = rotaion;
        this.zoom = zoom;
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
     */
    public void render(final Graphics g, Object3D[] objects) {
        Arrays.parallelSetAll(objects, (final int i) -> { //Translate all
            //objects relative to the camera.
            final Object3D o = objects[i]; //The Object3D being operated on.
            o.location = o.location.subtraction(location);
            return o;
        });
        {
            final List<Object3D> objs = Arrays.asList(objects); //Convert the objects to a list.
            objs.forEach((final Object3D o) -> { //Filter out all objects behind
                //the camera and all objects outside of the render distance.
                if (o.location.z < 0 || Math.hypot(Math.hypot(o.location.x,
                        o.location.y), o.location.z) > renderDistance) { //This
                    //object is behind the camera.
                    objs.remove(o);
                }
            });
        }
    }

}
