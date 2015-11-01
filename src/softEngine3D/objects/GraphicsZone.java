package softEngine3D.objects;

import dynutils.collections.sets.ArraySet;
import dynutils.collections.sets.LinkedSet;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import softEngine3D.matrixes.FPoint3D;
import softEngine3D.matrixes.Matrix;
/**
 * <p>
 * A GraphicsZone is a level in a hierarchy of GraphicsZones; multiple other
 * GraphicsZones can be stored inside of a GraphicsZone as well as a collection
 * of Triangles. The locations of all things directly inside of a GraphicsZone
 * are all relative to that GraphisZone.<br/>
 * GraphicsZone's facilitate the getting and putting of Triangles from/to a
 * different GraphicsZone, as long as those GraphicsZones are within the same
 * hierarchy.</p>
 *
 * @author Dynisious 27/09/2015
 * @version 3.0.2
 */
public class GraphicsZone implements Comparable<GraphicsZone> {
    /**
     * <p>
     * The location of this GraphicsZone.</p>
     */
    public FPoint3D location;
    /**
     * <p>
     * The rotation on the three different axis.</p>
     */
    public FPoint3D rotation;
    /**
     * <p>
     * The Triangles inside this GraphicsZone.</p>
     */
    private final LinkedSet<GraphicsTriangle> triangles = new LinkedSet<>();
    /**
     * <p>
     * The Triangles inside this GraphicsZone.</p>
     *
     * @return All the GraphicsTriangles inside this GraphicsZone.
     */
    public LinkedSet<GraphicsTriangle> getTriangles() {
        return triangles;
    }
    /**
     * <p>
     * An ArraySet of all the vertexes in this GraphicsZone.</p>
     */
    private final ArraySet<FPoint3D> vertexes = new ArraySet<>();
    /**
     * <p>
     * An ArraySet of all the vertexes in this GraphicsZone.</p>
     *
     * @return An array of a copy of all vertexes in this GraphicsZone.
     */
    public FPoint3D[] getVertexes() {
        final FPoint3D[] temp = new FPoint3D[vertexes.size()];
        int i = 0;
        for (final FPoint3D vertex : vertexes)
            temp[i++] = vertex.copy();
        return temp;
    }
    /**
     * <p>
     * The GraphicsZones inside this one.</p>
     */
    private final LinkedSet<GraphicsZone> zones = new LinkedSet<>(false);
    /**
     * <p>
     * The GraphicsZones inside this one.</p>
     *
     * @return All the GraphicsZones inside this GraphicsZone.
     */
    public LinkedSet<GraphicsZone> getZones() {
        return zones;
    }
    public void addZone(final GraphicsZone zone) {
        if (zones.add(zone)) {
            zone.containerZone = this;
            zone.setHierarchyLevel(hierarchyLevel + 1);
        }
    }
    /**
     * <p>
     * The level of 'importance' this GraphicsZone has. A low number is low
     * importance and a high number is high importance.</p>
     */
    public int renderLevel;
    /**
     * <p>
     * The hierarchy level indicates how far down the hierarchy of GraphicsZones
     * this GraphicsZone is 0 means there is no higher GraphicsZone.</p>
     */
    private int hierarchyLevel = 0;
    /**
     * <p>
     * The hierarchy level indicates how far down the hierarchy of GraphicsZones
     * this GraphicsZone is 0 means there is no higher GraphicsZone.</p>
     *
     * @return The hierarchy level of this GraphicsZone.
     */
    public int getHierarchyLevel() {
        return hierarchyLevel;
    }
    protected void setHierarchyLevel(final int hierarchyLevel) {
        this.hierarchyLevel = hierarchyLevel;
    }
    /**
     * <p>
     * The GraphicsZone this GraphicsZone is relative to.</p>
     */
    protected GraphicsZone containerZone;
    public GraphicsZone getContainerZone() {
        return containerZone;
    }

    /**
     * <p>
     * Creates a new GraphicsZone with the passed values. Checks all vertexes
     * directly used in this GraphicsZone to ensure all references are the
     * same.</p>
     *
     * @param location    The location of this GraphicsZone.
     * @param rotation    The rotation of this GraphicsZone.
     * @param triangles   The triangles inside this GraphicsZone.
     * @param zones       The GraphicsZones inside this GraphicsZone.
     * @param renderLevel The importance of this GraphicsZone. A low number is
     *                    low importance where as a high number is high
     *                    importance.
     */
    public GraphicsZone(final FPoint3D location, final FPoint3D rotation,
                        final Collection<GraphicsTriangle> triangles,
                        final Collection<GraphicsZone> zones,
                        final int renderLevel) {
        this.location = location;
        this.rotation = rotation;
        if (triangles != null)
            this.triangles.addAll(triangles);
        if (zones != null)
            this.zones.addAll(zones);
        this.renderLevel = renderLevel;
        for (final Triangle triangle : this.triangles) {
            int index;
            if ((index = vertexes.indexOf(triangle.p1)) != -1) {
                triangle.p1 = vertexes.get(index);
            } else {
                vertexes.add(triangle.p1);
            }
            if ((index = vertexes.indexOf(triangle.p2)) != -1) {
                triangle.p2 = vertexes.get(index);
            } else {
                vertexes.add(triangle.p2);
            }
            if ((index = vertexes.indexOf(triangle.p3)) != -1) {
                triangle.p3 = vertexes.get(index);
            } else {
                vertexes.add(triangle.p3);
            }
        }
    }

    public <T extends GraphicsZone> T copy() {
        final LinkedSet<GraphicsTriangle> nTriangles = new LinkedSet<>();
        for (final GraphicsTriangle triangle : this.triangles)
            nTriangles.add(triangle.copy());
        final LinkedSet<GraphicsZone> nZones = new LinkedSet<>(false);
        for (final GraphicsZone zone : this.zones)
            nZones.add(zone.copy());
        return (T) new GraphicsZone(location.copy(), rotation.copy(), nTriangles,
                nZones, renderLevel);
    }

    /**
     * <p>
     * Returns copies of all the Triangles and all the vertexes directly inside
     * this GraphicsZone in a List[] in the form:<br/>
     * {LinkedSet->Triangles, ArraySet->Vertexes}</p>
     *
     * @return The {Triangles, Vertexes}.
     */
    public Set[] copyTrianglesAndVertexes() {
        final LinkedSet<GraphicsTriangle> nTriangles = new LinkedSet<>();
        final ArraySet<FPoint3D> nVertexes = new ArraySet<>(this.vertexes.size());
        for (final FPoint3D vertex : this.vertexes)
            nVertexes.add(vertex.copy());
        for (final GraphicsTriangle triangle : this.triangles)
            nTriangles.add(new GraphicsTriangle(
                    nVertexes.get(nVertexes.indexOf(triangle.p1)),
                    nVertexes.get(nVertexes.indexOf(triangle.p2)),
                    nVertexes.get(nVertexes.indexOf(triangle.p3)),
                    triangle.inverted));
        return new Set[]{nTriangles, nVertexes};
    }

    /**
     * <p>
     * Gets a copy of all of the Triangles and Vertexes both directly and
     * indirectly inside this GraphicsZone, as long as they are above the
     * minimum render level, in relation to this GraphicsZone.</p>
     *
     * @param minRenderLevel The minimum <i>render level</i> needed to return
     *                       the GraphicsTriangles directly inside a
     *                       GraphicsZone.
     *
     * @return The List[] containing the GraphicsTriangles and vertexes of
     *         form:<br/>> {LinkedSet->Triangles, ArraySet->Vertexes}
     */
    public Set[] copyAllTrianglesAndVertexes(final int minRenderLevel) {
        final Set[] returnSets = new Set[]{new LinkedSet<>(), new ArraySet<>()};
        final LinkedSet<GraphicsTriangle> nTriangles = (LinkedSet<GraphicsTriangle>) returnSets[0];
        final ArraySet<FPoint3D> nVertexes = (ArraySet<FPoint3D>) returnSets[1];
        if (renderLevel >= minRenderLevel) {
            final Set[] temp = copyTrianglesAndVertexes();
            nTriangles.addAll(temp[0]);
            nVertexes.addAll(temp[1]);
        }
        for (final GraphicsZone zone : zones) {
            final Matrix transformation = Matrix.produceTransMatrixNoScale(
                    zone.rotation, zone.location);
            final Set[] tempSets = zone.copyAllTrianglesAndVertexes(
                    minRenderLevel);
            final Set<FPoint3D> tempVertexes = tempSets[1];
            for (final FPoint3D vertex : tempVertexes)
                nVertexes.add(transformation.transform(vertex));
            for (final GraphicsTriangle triangle : (LinkedSet<GraphicsTriangle>) tempSets[0])
                nTriangles.add(new GraphicsTriangle(
                        nVertexes.get(nVertexes.indexOf(triangle.p1)),
                        nVertexes.get(nVertexes.indexOf(triangle.p2)),
                        nVertexes.get(nVertexes.indexOf(triangle.p3)),
                        triangle.inverted));
        }
        return returnSets;
    }

    /**
     * <p>
     * Returns a copy of all of the Triangles and vertexes both directly and
     * indirectly inside of <i>from</i> in relation to <i>too</i> in a Set[] of
     * form:<br/>
     * > {LinkedSet->Triangles, ArraySet->FPoint3D}.</p>
     *
     * @param from           The GraphicsZone to get all the Triangles from.
     * @param too            The GraphicsZone for them to all be relative to.
     * @param minRenderLevel The minimum render level required to return the
     *                       GraphicsTriangles directly inside of a
     *                       GraphicsZone.
     *
     * @return Copies of all Triangles and vertexes from <code>from</code> in
     *         relation to <code>too</code> as a Set[].
     *
     * @throws NullPointerException Thrown if <code>from</code> or
     *                              <code>too</code> are null pointers.
     */
    public static Set[] putInRelation(final GraphicsZone from,
                                      final GraphicsZone too,
                                      final int minRenderLevel)
            throws NullPointerException {
        if (from == null || too == null)
            throw new NullPointerException(
                    "One of the GraphicsZones is a null pointer.");
        final Set[] returnSets = from
                .copyAllTrianglesAndVertexes(minRenderLevel);
        final ArraySet<FPoint3D> nVertexes = (ArraySet<FPoint3D>) returnSets[1];
        for (GraphicsZone zone = from; zone != null; zone = zone.containerZone) {
            final Matrix transformation = Matrix.produceTransMatrixNoScale(
                    zone.rotation, zone.location);
            for (final FPoint3D vertex : nVertexes)
                transformation.transform(vertex);
        }
        final LinkedSet<GraphicsZone> tooTrace = new LinkedSet<>(false);
        for (GraphicsZone zone = too; zone != null; zone = tooTrace.peek().containerZone)
            tooTrace.push(zone);
        for (final GraphicsZone zone : tooTrace) {
            final Matrix transformation = Matrix.produceTransMatrixRotate(
                    zone.rotation.invert());
            final FPoint3D translation = too.location.invert();
            for (final FPoint3D vertex : nVertexes)
                transformation.transform(vertex.translate(translation));
        }
        return returnSets;
    }

    /**
     * <p>
     * Orders a collection of GraphicsZone's in descending z order.</p>
     *
     * @param o The GraphicsZone to compare against.
     *
     * @return -1 if this GraphicsZone has a higher z position.
     */
    @Override
    public int compareTo(final GraphicsZone o) {
        if (location.z > o.location.z) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof GraphicsZone) {
            final GraphicsZone temp = (GraphicsZone) obj;
            if (temp.containerZone.equals(containerZone)
                    && temp.location.equals(location)
                    && temp.renderLevel == renderLevel
                    && temp.rotation.equals(rotation)
                    && temp.getTriangles().equals(getTriangles())
                    && temp.getZones().equals(getZones()))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 31 * hash + Objects.hashCode(this.location);
        hash = 31 * hash + Objects.hashCode(this.rotation);
        hash = 31 * hash + Objects.hashCode(this.triangles);
        hash = 31 * hash + Objects.hashCode(this.zones);
        hash = 31 * hash + this.renderLevel;
        hash = 31 * hash + this.hierarchyLevel;
        return hash;
    }

}
