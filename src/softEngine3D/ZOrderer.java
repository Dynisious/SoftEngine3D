package softEngine3D;

import java.util.ArrayList;
import java.util.Collections;
/**
 * <p>
 * An ZOrderer is anything which can be z ordered.</p>
 *
 * @author Dynisious 23/07/2015
 * @version 0.0.1
 */
public interface ZOrderer {

    /**
     * <p>
     * Reorders all the Objects in the array to be in the specified z order.</p>
     *
     * @param objects    The Objects to reorder.
     * @param descending True if the returned array should be in descending
     *                   order.
     *
     * @return The Array of Objects in the specified z order.
     */
    public static Object3D[] orderObject3D(Object3D[] objects,
                                           final boolean descending) {
        final ArrayList<Object3D> cObjects = new ArrayList<>(objects.length);
        for (Object3D object : objects) { //Go through each ZOrderer in the model.
            if (cObjects.size() > 0) {
                if (object.getLocation().getZ() < cObjects.get(0).getLocation().getZ()) { //This is the lowest z value.
                    cObjects.add(0, object);
                } else if (object.getLocation().getZ() > cObjects.get(
                        cObjects.size() - 1).getLocation().getZ()) { //This is the highest z value.
                    cObjects.add(object);
                } else {
                    //<editor-fold defaultstate="collapsed" desc="Binary search for best index for the z order.">
                    int max = cObjects.size(); //The maximum possible index that can be checked.
                    int min = 0; //The minimum possilbe index to be checked.
                    int index = (int) Math.ceil(cObjects.size() / 2); //The current index in the current list of objects being checked.
                    while (object.getLocation().getZ() != cObjects.get(
                            index).getLocation().getZ()) { //While the closest index is yet to be found.
                        if (object.getLocation().getZ() > cObjects.get(
                                index).getLocation().getZ()) { //The closest index is above this one.
                            min = index; //Set the minimum index to this one.
                            final int i = (int) (min + Math.ceil(
                                    (max - min) / 2)); //Find the center index between the maximum and the minimum.
                            if (i == max || i == index) { //This is the closest index that will be found.
                                index = i;
                                break; //Stop searching for the closest index.
                            }
                            index = i;
                        } else if (object.getLocation().getZ() < cObjects.get(
                                index).getLocation().getZ()) { //The closest index is bellow this one.
                            max = index; //Set the maximum index to this one.
                            final int i = (int) (min + Math.floor(
                                    (max - min) / 2)); //Find the center index between the maximum and the minimum.
                            if (i == min || i == index) { //This is the closest index that will be found.
                                index = i;
                                break; //Stop searching for the closest index.
                            }
                            index = i;
                        }
                    }//</editor-fold>
                    cObjects.add(index + 1, object); //Insert the object at the best index for it's z value.
                }
            } else {
                cObjects.add(object);
            }
        }

        if (descending) { //The order needs to be reversed.
            Collections.reverse(cObjects);
        }
        return cObjects.toArray(new Object3D[cObjects.size()]);
    }

    /**
     * <p>
     * Reorders all the Objects in the array to be in the specified z order.</p>
     *
     * @param triangles  The Objects to reorder.
     * @param descending True if the returned array should be in descending
     *                   order.
     *
     * @return The Array of Objects in the specified z order.
     */
    public static Triangle[] orderTriangle(Triangle[] triangles,
                                           final boolean descending) {
        final ArrayList<Triangle> cObjects = new ArrayList<>(triangles.length);
        for (Triangle object : triangles) { //Go through each ZOrderer in the model.
            if (cObjects.size() > 0) {
                if (object.getLocation().getZ() < cObjects.get(0).getLocation().getZ()) { //This is the lowest z value.
                    cObjects.add(0, object);
                } else if (object.getLocation().getZ() > cObjects.get(
                        cObjects.size() - 1).getLocation().getZ()) { //This is the highest z value.
                    cObjects.add(object);
                } else {
                    //<editor-fold defaultstate="collapsed" desc="Binary search for best index for the z order.">
                    int max = cObjects.size(); //The maximum possible index that can be checked.
                    int min = 0; //The minimum possilbe index to be checked.
                    int index = (int) Math.ceil(cObjects.size() / 2); //The current index in the current list of objects being checked.
                    while (object.getLocation().getZ() != cObjects.get(
                            index).getLocation().getZ()) { //While the closest index is yet to be found.
                        if (object.getLocation().getZ() > cObjects.get(
                                index).getLocation().getZ()) { //The closest index is above this one.
                            min = index; //Set the minimum index to this one.
                            final int i = (int) (min + Math.ceil(
                                    (max - min) / 2)); //Find the center index between the maximum and the minimum.
                            if (i == max || i == index) { //This is the closest index that will be found.
                                index = i;
                                break; //Stop searching for the closest index.
                            }
                            index = i;
                        } else if (object.getLocation().getZ() < cObjects.get(
                                index).getLocation().getZ()) { //The closest index is bellow this one.
                            max = index; //Set the maximum index to this one.
                            final int i = (int) (min + Math.floor(
                                    (max - min) / 2)); //Find the center index between the maximum and the minimum.
                            if (i == min || i == index) { //This is the closest index that will be found.
                                index = i;
                                break; //Stop searching for the closest index.
                            }
                            index = i;
                        }
                    }//</editor-fold>
                    cObjects.add(index + 1, object); //Insert the object at the best index for it's z value.
                }
            } else {
                cObjects.add(object);
            }
        }

        if (descending) { //The order needs to be reversed.
            Collections.reverse(cObjects);
        }
        return cObjects.toArray(new Triangle[cObjects.size()]);
    }

    /**
     * <p>
     * Reorders all the Objects in the array to be in the specified z order.</p>
     *
     * @param lights     The Objects to reorder.
     * @param descending True if the returned array should be in descending
     *                   order.
     *
     * @return The Array of Objects in the specified z order.
     */
    public static Light[] orderLight(Light[] lights, final boolean descending) {
        final ArrayList<Light> cObjects = new ArrayList<>(lights.length);
        for (Light object : lights) { //Go through each ZOrderer in the model.
            if (cObjects.size() > 0) {
                if (object.getLocation().getZ() < cObjects.get(0).getLocation().getZ()) { //This is the lowest z value.
                    cObjects.add(0, object);
                } else if (object.getLocation().getZ() > cObjects.get(
                        cObjects.size() - 1).getLocation().getZ()) { //This is the highest z value.
                    cObjects.add(object);
                } else {
                    //<editor-fold defaultstate="collapsed" desc="Binary search for best index for the z order.">
                    int max = cObjects.size(); //The maximum possible index that can be checked.
                    int min = 0; //The minimum possilbe index to be checked.
                    int index = (int) Math.ceil(cObjects.size() / 2); //The current index in the current list of objects being checked.
                    while (object.getLocation().getZ() != cObjects.get(
                            index).getLocation().getZ()) { //While the closest index is yet to be found.
                        if (object.getLocation().getZ() > cObjects.get(
                                index).getLocation().getZ()) { //The closest index is above this one.
                            min = index; //Set the minimum index to this one.
                            final int i = (int) (min + Math.ceil(
                                    (max - min) / 2)); //Find the center index between the maximum and the minimum.
                            if (i == max || i == index) { //This is the closest index that will be found.
                                index = i;
                                break; //Stop searching for the closest index.
                            }
                            index = i;
                        } else if (object.getLocation().getZ() < cObjects.get(
                                index).getLocation().getZ()) { //The closest index is bellow this one.
                            max = index; //Set the maximum index to this one.
                            final int i = (int) (min + Math.floor(
                                    (max - min) / 2)); //Find the center index between the maximum and the minimum.
                            if (i == min || i == index) { //This is the closest index that will be found.
                                index = i;
                                break; //Stop searching for the closest index.
                            }
                            index = i;
                        }
                    }//</editor-fold>
                    cObjects.add(index + 1, object); //Insert the object at the best index for it's z value.
                }
            } else {
                cObjects.add(object);
            }
        }

        if (descending) { //The order needs to be reversed.
            Collections.reverse(cObjects);
        }
        return cObjects.toArray(new Light[cObjects.size()]);
    }

}
