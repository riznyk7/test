package balls_move;

import princeton.lib.StdDraw;
import princeton.lib.StdRandom;

public class Particle{
	private static final double INFINITY = Double.POSITIVE_INFINITY;
	private double rx, ry;
	private double vx, vy;
	private final double radius;
	private final double mass;
	private int count;
	
	public Particle() {
		radius = StdRandom.uniform(0.03, 0.05);
		rx = StdRandom.uniform(0+radius, 1-radius);
		ry = StdRandom.uniform(0+radius, 1-radius);
		vx = StdRandom.uniform(-0.01, 0.01);
		vy = StdRandom.uniform(-0.01, 0.01);
        mass   = 10 * radius;
    }
	
    public void move(double dt) {
        rx += vx * dt;
        ry += vy * dt;
    }

    public void draw() {
        StdDraw.filledCircle(rx, ry, radius);
    }

    public int count() { return count; }
    
    
	public double timeToHit(Particle that){
		if (this == that) return INFINITY;
		double dx = that.rx - this.rx;
		double dy = that.ry - this.ry;
		double dvx = that.vx - this.vx;
		double dvy = that.vy - this.vy;
		double dvdr = dx*dvx + dy*dvy;
		
		if( dvdr > 0) return INFINITY;
		double dvdv = dvx*dvx + dvy*dvy;
		double drdr = dx*dx + dy*dy;
		double sigma = this.radius + that.radius;
		double d = (dvdr*dvdr) - dvdv * (drdr - sigma*sigma);
		if (d < 0) return INFINITY;
		return -(dvdr + Math.sqrt(d)) / dvdv;
	}
	
    public double timeToHitVerticalWall() {
        if (vx > 0) return (1.0 - rx - radius) / vx;
        else if (vx < 0) return (radius - rx) / vx;  
        else return INFINITY;
    }

    public double timeToHitHorizontalWall() {
        if (vy > 0) return (1.0 - ry - radius) / vy;
        else if (vy < 0) return (radius - ry) / vy;
        else return INFINITY;
    }
	
	public void bounceOff(Particle that){
		double dx = that.rx - this.rx, dy = that.ry - this.ry;
		double dvx = that.vx - this.vx, dvy = that.vy - this.vy;
		double dvdr = dx*dvx + dy*dvy;
		double dist = this.radius + that.radius;
		double J = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);
		double Jx = J * dx / dist;
		double Jy = J * dy / dist;
		this.vx += Jx / this.mass;
		this.vy += Jy / this.mass;
		that.vx -= Jx / that.mass;
		that.vy -= Jy / that.mass;
		this.count++;
		that.count++;
	}
		
    public void bounceOffVerticalWall() {
        vx = -vx;
        count++;
    }

    public void bounceOffHorizontalWall() {
        vy = -vy;
        count++;
    }
	

}
