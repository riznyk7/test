package balls_move;

import princeton.lib.StdDraw;

public class Tester {
	
	
	private static void setWindow() {
		StdDraw.setXscale(0, 1);
		StdDraw.setYscale(0, 1);
		StdDraw.line(0, 0, 0, 1);
		StdDraw.line(0, 0, 1, 0);
		StdDraw.line(1, 0, 1, 1);
		StdDraw.line(0, 1, 1, 1);
	}
	
	
	 
	 public static void main(String[] args) {

        StdDraw.show(0);
        Particle[] particles;
        int N = Integer.parseInt(args[0]);
        particles = new Particle[N];
        for (int i = 0; i < N; i++) {
        	particles[i] = new Particle();
        }
        CollisionSystem system = new CollisionSystem(particles);
        setWindow();
        system.simulate();
    }
}