package edu.nyu.cs.sc7443;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A fractal tree that can be drawn to a JFrame object. Uses recursion and lines to draw the tree. Inherits from the JPanel class in the Swing library in order to be drawn to a JFrame object in the
 * main method.
 * @author Foo Barstein, with comments by Sivan Cooperman (sc7443)
 *
 */
public class FractalTree extends JPanel {
	/**
	 * A Graphics 2D object from the Swing library. Passed into the drawFractal() function as a casted instance of a Graphics object by the paint() function.
	 */
    public Graphics2D g1;
    /**
     * The maximum angle that can be achieved, in degrees. Used as the divisor in a modulus operation on a given angle in order to convert said angle to one between 0 and 360 degrees.
     */
    public static final int maxAngle = 360;
    /**
     * The starting x-coordinate, in pixels. Used to position the initial branch of the fractal tree (the "trunk") on the x-axis.
     */
    public static final int startX = 600;
    /**
     * The starting y-coordinate, in pixels. Used to position the initial branch of the fractal tree (the "trunk") on the y-axis.
     */
    public static final int startY = 800;
    /**
     * The number of times the fractal recurses. Affects the ending position of each branch, and also how many times the drawFractal() function is called recursively.
     */
    public static final int numOfRecursions = 9;
    /**
     * The starting angle of the fractal, in degrees, with 0 degrees pointing in the positive y direction. Used when initially calling the drawFractal() function through the paint() function.
     */
    public static final int startAngle = 0;
    /**
     * Determines the amount of space the tree occupies on the screen. Affects the ending position of each branch.
     */
    public static final double treeSize = 2;
    /**
     * Determines how detailed the tree is. Lower values indicate more recusion at a smaller level. Should not be set higher than numOfRecursions, or else the tree will not be drawn.
     */
    public static final int Detail = 3;
    /**
     * In conjunction with constFact, sets the bounds for the angle, in degrees, of the next recursive branch of the fractal, between which a number is randomly chosen.
     */
    public static final int randFact = 30;
    /**
     * In conjunction with randFact, used to select a random angle. When drawFractal() is called, the array is cycled through so that each recursive branch has a different range of angles to be chosen from.
     */
    public static final int[] constFact = {-60, 05, -50, 45};
    /**
     * These three arrays are used to provide a random rgb value for each line drawn by the program. The resultant colors are varying shades of green.
     */
    public static int[] red =   {0, 0, 0, 0, 7, 15, 23, 31, 39, 47, 55, 43};
    public static int[] green = {171, 159, 147, 135, 123, 111, 99, 87, 75, 63, 51, 43};
    public static int[] blue =  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};   
    
    /**
     * Converts an angle's units from degrees to radians via the equation a = (d*pi)/180, where a is the resultant angle in radians, and d is the original angle in degrees.
     * @param deg An integer angle in degrees.
     * @return Returns an angle in radians as a double.
     */
    public static double degToRad(int deg) {
        return deg * Math.PI / 180;
    }
    
    /**
     * Draws a fractal tree recursively using the Swing library for Java. 
     * 
     * First the function calculates several variables representing midpoints and midpoints of midpoints between the starting x and y coordinates and the ending x and y coordinates for the given
     * function call. The function also calculates the length of the four lines it will draw (except during the first call, where one line is drawn), based on the number of recursions left and the
     * given size of the tree as determined by the treeSize variable. Next, the function is called recursively four times. The g1 argument remains the same, whereas the x and y arguments become the
     * one of the various points laying on the line between the initial and final points of the current line, and the angle becomes an angle rotated a pseudo-random amount from the previous angle,
     * and converted as to lay between 0 and 360 degrees. The number of recursions is reduced by one as it is passed into the function, in order that the function eventually stops calling itself.
     * Finally, the current line is drawn with a pseudo-random color between the starting coordinates and the ending coordinates.
     * @param g1 A Graphics 2D object.
     * @param x The starting x-coordinate of the line being drawn.
     * @param y The starting y-coordinate of the line being drawn.
     * @param n The number of recursions remaining. Decreases with every recursive call of the function.
     * @param angle The angle at which the line should be drawn. 0 degrees points in the positive y direction.
     */
    public static void drawFractal(Graphics2D g1, int x, int y, int n, int angle) {
    	// Checks to make sure that the number of recursions has not fallen below the detail threshold, in order to limit the amount of lines drawn.
        if (n == Detail) return;
        /**
         * Determines the length coefficient of the line being drawn, as determined by rounding the result of the equation l = t^(n-1), where l is the length coefficient, t is the tree size, and
         * n is the number of recursions.
         */
        int len = (int) Math.round(Math.pow(treeSize, n - 1));
         
        /**
         * The x-coordinate of the endpoint of the current line, as determined by rounding the result of the equation x1 = x - (2*l*sin(a)), where:
         * x1 is the x-coordinate of the endpoint, x is the x-coordinate of the starting point, l is the length coefficient, and a is the angle in radians, as converted by the degToRad() function.
         * This coordinate, in conujunction with yn1, is used to calculuate several midpoints.
         */
        int xn1 = (int) Math.round(x - (2 * len * Math.sin(degToRad(angle))));
        /**
         * The y-coordinate of the endpoint of the current line, as determined by rounding the result of the equation y1 = y - (2*l*cos(a)), where:
         * y1 is the y-coordinate of the endpoint, y is the y-coordinate of the starting point, l is the length coefficient, and a is the angle in radians, as converted by the degToRad() function.
         */
        int yn1 = (int) Math.round(y - (2 * len * Math.cos(degToRad(angle))));
        /**
         * The midpoint between the starting x-coordinate and the ending x-coordinate. Used to calculate other midpoints and to position the next recursively-drawn branch.
         */
        int mid1x = (x + xn1) / 2;
        /**
         * The midpoint between the starting y-coordinate and the ending y-coordinate. Used to calculate other midpoints and to position the next recursively-drawn branch.
         */
        int mid1y = (y + yn1) / 2;
        /**
         * The midpoint between the middle x-coordinate and the ending x-coordinate. Used to position the next recursively-drawn branch.
         */
        int mid2x = (mid1x + xn1) / 2;
        /**
         * The midpoint between the middle y-coordinate and the ending y-coordinate. Used to position the next recursively-drawn branch.
         */
        int mid2y = (mid1y + yn1) / 2;
        /**
         * The midpoint between the starting x-coordinate and the middle x-coordinate. Used to calculate other midpoints and to position the next recursively-drawn branch.
         */
        int mid3x = (x + mid1x) / 2;
        /**
         * The midpoint between the starting y-coordinate and the ending y-coordinate. Used to calculate other midpoints and to position the next recursively-drawn branch.
         */
        int mid3y = (y + mid1y) / 2;
        /**
         * The x-coordinate of the midpoint between mid3x and mid1x. Used to position the next recursively-drawn branch.
         */
        int mid4x = (mid3x + mid1x) / 2;
        /**
         * The y-coordinate of the midpoint between mid3x and mid1x. Used to position the next recursively-drawn branch.
         */
        int mid4y = (mid3y + mid1y) / 2;
         
        /**
         * A random number generator supplied by the Java random library to supply a random angle between randFact and various values of constFact.
         */
        java.util.Random r = new java.util.Random();
        // The drawFractal() method is called recursively four times here, each indicating a new line at the given coordinates, a decremented number of recursions (so that the program eventually
        // ends), and a pseudo-random angle off of the current line.
        drawFractal(g1, mid1x, mid1y, n - 1, (angle + r.nextInt(randFact) + constFact[0]) % maxAngle);
        drawFractal(g1, mid2x, mid2y, n - 1, (angle + r.nextInt(randFact) + constFact[1]) % maxAngle);
        drawFractal(g1, mid3x, mid3y, n - 1, (angle + r.nextInt(randFact) + constFact[2]) % maxAngle);
        drawFractal(g1, mid4x, mid4y, n - 1, (angle + r.nextInt(randFact) + constFact[3]) % maxAngle);
         
        /**
         * A Color object containing rgb values determined pseudo-randomly from the arrays red, green, and blue.
         */
        Color c = new Color(red[(r.nextInt() % 3) + n], green[(r.nextInt() % 3) + n], blue[(r.nextInt() % 3) + n]);
        // Here, an instance variable representing a color in the Graphics2D object passed into the function is set to the Color object defined above.
        g1.setColor(c);
        /**
         * A line object that takes the starting and ending x and y coordinates.
         */
        Line2D L1 = new Line2D.Double(x, y, xn1, yn1);
        // The line object is drawn by the Graphics2D object.
        g1.draw(L1);
        return;
    }
    
    /**
     * This method casts the passed-in Graphics object to a Graphics2D object that does the drawing and passes it into the drawFractal() function.
     * @param g A Graphics object
     */
    public void paint(final Graphics g) {
        g1 = (Graphics2D) g;
        drawFractal(g1, startX, startY, numOfRecursions, startAngle);
    }
    
    /**
     * Main method. Creates a new JFrame object with various properties such as a black background, title, 1200x1000 resolution, and so on. A FractalTree() object is added to the JFrame in order to
     * be drawn onto it.
     */
    public static void main(String args[]) {
    	/**
    	 * New JFrame object, the window upon which the fractal tree is drawn.
    	 */
        JFrame FF = new JFrame("Drawing a recursive tree");
        // Setting the window to close when the close button is pressed
        FF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /**
         * A new FractalTree object, added to the JFrame object so that it shows up on the screen.
         */
        FractalTree F = new FractalTree();
        // setting the background of the window to black
        FF.setBackground(Color.BLACK);
        // adding the FractalTree object to the JFrame object
        FF.add(F);
        // sizes all the contents of the JFrame correctly
        FF.pack();
        // makes it so that the user can see the window
        FF.setVisible(true);
        // sets the size of the window
        FF.setSize(1200, 1000);
    }
}