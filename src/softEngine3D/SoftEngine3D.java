package softEngine3D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;
/**
 * <p>
 * Another attempt (3rd) at making something that somewhat resembles a 3D
 * graphics engine.</p>
 *
 * @author Dynisious 14/07/2015
 * @versions 0.0.1
 */
public class SoftEngine3D {
    final static Camera cam = new Camera(new Point3D(-300, -300, 0),
            new FPoint3D(0, 0, 0), 1000, 300, 300);
    static double rot = 0;

    /**
     * <p>
     * Initialises the objects for the graphics engine.</p>
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        final class Cube extends Object3D {

            public Cube() {
                super(new Point3D(300, 300, 1000),
                        new Triangle[]{
                            new Triangle(new Point3D(0, 0, 100),
                                    new Point3D[]{
                                        new Point3D(100, 100, 0),
                                        new Point3D(100, -100, 0),
                                        new Point3D(-100, 100, 0)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(0, 0, 100),
                                    new Point3D[]{
                                        new Point3D(-100, -100, 0),
                                        new Point3D(-100, 100, 0),
                                        new Point3D(100, -100, 0)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(0, 0, -100),
                                    new Point3D[]{
                                        new Point3D(100, 100, 0),
                                        new Point3D(100, -100, 0),
                                        new Point3D(-100, 100, 0)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(0, 0, -100),
                                    new Point3D[]{
                                        new Point3D(-100, -100, 0),
                                        new Point3D(-100, 100, 0),
                                        new Point3D(100, -100, 0)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(100, 0, 0),
                                    new Point3D[]{
                                        new Point3D(0, 100, 100),
                                        new Point3D(0, 100, -100),
                                        new Point3D(0, -100, 100)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(100, 0, 0),
                                    new Point3D[]{
                                        new Point3D(0, -100, -100),
                                        new Point3D(0, -100, 100),
                                        new Point3D(0, 100, -100)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(-100, 0, 0),
                                    new Point3D[]{
                                        new Point3D(0, 100, 100),
                                        new Point3D(0, 100, -100),
                                        new Point3D(0, -100, 100)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(-100, 0, 0),
                                    new Point3D[]{
                                        new Point3D(0, -100, -100),
                                        new Point3D(0, -100, 100),
                                        new Point3D(0, 100, -100)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(0, 100, 0),
                                    new Point3D[]{
                                        new Point3D(100, 0, 100),
                                        new Point3D(-100, 0, 100),
                                        new Point3D(100, 0, -100)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(0, 100, 0),
                                    new Point3D[]{
                                        new Point3D(-100, 0, -100),
                                        new Point3D(-100, 0, 100),
                                        new Point3D(100, 0, -100)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(0, -100, 0),
                                    new Point3D[]{
                                        new Point3D(100, 0, 100),
                                        new Point3D(-100, 0, 100),
                                        new Point3D(100, 0, -100)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(0, -100, 0),
                                    new Point3D[]{
                                        new Point3D(-100, 0, -100),
                                        new Point3D(-100, 0, 100),
                                        new Point3D(100, 0, -100)
                                    }, Color.GREEN)
                        });
            }

            public Cube(final Point3D location) {
                super(location,
                        new Triangle[]{
                            new Triangle(new Point3D(0, 0, 100),
                                    new Point3D[]{
                                        new Point3D(100, 100, 0),
                                        new Point3D(100, -100, 0),
                                        new Point3D(-100, 100, 0)
                                    }, Color.BLUE),
                            new Triangle(new Point3D(0, 0, 100),
                                    new Point3D[]{
                                        new Point3D(-100, -100, 0),
                                        new Point3D(-100, 100, 0),
                                        new Point3D(100, -100, 0)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(0, 0, -100),
                                    new Point3D[]{
                                        new Point3D(100, 100, 0),
                                        new Point3D(100, -100, 0),
                                        new Point3D(-100, 100, 0)
                                    }, Color.BLACK),
                            new Triangle(new Point3D(0, 0, -100),
                                    new Point3D[]{
                                        new Point3D(-100, -100, 0),
                                        new Point3D(-100, 100, 0),
                                        new Point3D(100, -100, 0)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(100, 0, 0),
                                    new Point3D[]{
                                        new Point3D(0, 100, 100),
                                        new Point3D(0, 100, -100),
                                        new Point3D(0, -100, 100)
                                    }, Color.RED),
                            new Triangle(new Point3D(100, 0, 0),
                                    new Point3D[]{
                                        new Point3D(0, -100, -100),
                                        new Point3D(0, -100, 100),
                                        new Point3D(0, 100, -100)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(-100, 0, 0),
                                    new Point3D[]{
                                        new Point3D(0, 100, 100),
                                        new Point3D(0, 100, -100),
                                        new Point3D(0, -100, 100)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(-100, 0, 0),
                                    new Point3D[]{
                                        new Point3D(0, -100, -100),
                                        new Point3D(0, -100, 100),
                                        new Point3D(0, 100, -100)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(0, 100, 0),
                                    new Point3D[]{
                                        new Point3D(100, 0, 100),
                                        new Point3D(-100, 0, 100),
                                        new Point3D(100, 0, -100)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(0, 100, 0),
                                    new Point3D[]{
                                        new Point3D(-100, 0, -100),
                                        new Point3D(-100, 0, 100),
                                        new Point3D(100, 0, -100)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(0, -100, 0),
                                    new Point3D[]{
                                        new Point3D(100, 0, 100),
                                        new Point3D(-100, 0, 100),
                                        new Point3D(100, 0, -100)
                                    }, Color.GREEN),
                            new Triangle(new Point3D(0, -100, 0),
                                    new Point3D[]{
                                        new Point3D(-100, 0, -100),
                                        new Point3D(-100, 0, 100),
                                        new Point3D(100, 0, -100)
                                    }, Color.GREEN)
                        });
            }

        }
        final class Frame extends javax.swing.JFrame {

            public Frame() {
                setSize(600, 600);
                setVisible(true);
                setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                setLayout(null);
            }

            @Override
            public void paint(Graphics g) {
                super.paint(g); //To change body of generated methods, choose Tools | Templates.

                Image img = this.createImage(600, 600);
                Graphics graph = img.getGraphics();
                final Cube[] cubes = new Cube[]{
                    new Cube(new Point3D(-100, -100, 10000)),
                    new Cube(new Point3D(600, 600, 3000)),
                    new Cube(new Point3D(300, 300, 3000)),
                    new Cube(new Point3D(50, 50, 1000)),
                    new Cube(new Point3D(100, 800, 8000))
                };
                for (Cube cube : cubes) {
                    cube.rotateXAxis(rot);
                    cube.rotateYAxis(rot);
                    cube.rotateZAxis(rot);
                }
                rot += 0.06;
                cam.render(graph, (Object3D[]) cubes);
                g.drawImage(img, 0, 0, this);
            }

        }

        final Frame frame = new Frame();
        Timer timer = new Timer("Tick");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                frame.repaint();
            }
        }, 0, 30);
    }

}
