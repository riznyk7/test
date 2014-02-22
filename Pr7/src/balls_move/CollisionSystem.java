package balls_move;

import princeton.lib.StdDraw;

public class CollisionSystem{
	private MinPQ<Event> pq;
	private double t = 0.0;
	private Particle[] particles;
	private int N;
	
	public CollisionSystem(Particle[] particles) {
		this.particles = particles;
		N = particles.length;
	}
	
	private void predict(Particle a){
		if (a == null) return;
		for (int i = 0; i < N; i++){
			double dt = a.timeToHit(particles[i]);
			pq.insert(new Event(t + dt, a, particles[i]));
		}
		pq.insert(new Event(t + a.timeToHitVerticalWall() , a, null));
		pq.insert(new Event(t + a.timeToHitHorizontalWall(), null, a));
	}
	
	
	private void redraw() {
        StdDraw.clear();
		StdDraw.line(0, 0, 0, 1);
		StdDraw.line(0, 0, 1, 0);
		StdDraw.line(1, 0, 1, 1);
		StdDraw.line(0, 1, 1, 1);
        for (int i = 0; i < particles.length; i++) {
            particles[i].draw();
        }
        StdDraw.show(20);
        pq.insert(new Event(t + 1.0 / 0.5, null, null));
        
    }
	
	public void simulate() { 
		pq = new MinPQ<Event>();
		for(int i = 0; i < N; i++) predict(particles[i]);
		pq.insert(new Event(0, null, null));
		while(!pq.isEmpty()) {
			Event event = pq.delMin();
			if(!event.isValid()) continue;
			Particle a = event.a;
			Particle b = event.b;
			for(int i = 0; i < N; i++)
			particles[i].move(event.time - t);
			t = event.time;
			if (a != null && b != null) a.bounceOff(b);
			else if (a != null && b == null) a.bounceOffVerticalWall();
			else if (a == null && b != null) b.bounceOffHorizontalWall();
			else if (a == null && b == null) redraw();
			predict(a);
			predict(b);
		}
	}
	
	private class Event implements Comparable<Event>{
		private double time;
		private Particle a, b;
		private int countA, countB;
		
		public Event(double t, Particle a, Particle b) {
			 this.time = t;
	         this.a = a;
	         this.b = b;
	         if (a != null) countA = a.count();
	         else countA = -1;
	         if (b != null) countB = b.count();
	         else countB = -1;
	}
		
        public int compareTo(Event that) {
            if (this.time < that.time) return -1;
            else if (this.time > that.time) return +1;
            else return  0;
        }
		
	
        public boolean isValid() {
            if (a != null && a.count() != countA) return false;
            if (b != null && b.count() != countB) return false;
            return true;
        }
	}
}
