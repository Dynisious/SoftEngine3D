package softEngine3D;

/**
 * <p>
 * A Light Object provides shading for triangles, raising the alpha of colors in
 * it's light.</p>
 *
 * @author Dynisious 24/07/2015
 * @versions 0.0.1
 */
public class Light extends Orderable {

    public Light(final Point3D location) {
        this.location = location;
    }

}
