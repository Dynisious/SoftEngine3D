package softEngine3D;

/**
 * <p>
 * An Orderable is anything which can be passed through a ZOrderer.</p>
 *
 * @author Dynisious 23/07/2015
 * @version 0.0.1
 */
public abstract class Orderable {
    /**
     * <p>
     * The Location of this Object3D in 3D space.</p>
     */
    protected Point3D location;
    public Point3D getLocation() {
        return location;
    }

}
