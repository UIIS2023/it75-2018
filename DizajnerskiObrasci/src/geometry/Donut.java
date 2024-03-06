package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Donut extends Circle implements Serializable{

	private int innerRadius;

	public Donut() {

	}

	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius);
		this.innerRadius = innerRadius;
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		this.setSelected(selected);
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color) {
		this(center, radius, innerRadius, selected);
		this.setColor(color);
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color, Color innerColor) {
		this(center, radius, innerRadius, selected, color);
		this.setInnerColor(innerColor);
	}

	public Donut(Point center, int radius, int innerRadius, Color color, Color innerColor) {
		this(center, radius, innerRadius);
		this.setColor(color);
		this.setInnerColor(innerColor);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Donut) {
			return (int) (this.area() - ((Donut) o).area());
		}
		return 0;
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		super.fill(g);
		g.setColor(Color.WHITE);
		g.fillOval(this.getCenter().getX() - this.innerRadius, this.getCenter().getY() - this.innerRadius,
				this.innerRadius * 2, this.innerRadius * 2);
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		g.setColor(getColor());
		g.drawOval(this.getCenter().getX() - this.innerRadius, this.getCenter().getY() - this.innerRadius,
				this.innerRadius * 2, this.innerRadius * 2);
	}

	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}

	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return super.contains(x, y) && dFromCenter > innerRadius;
	}

	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().distance(p.getX(), p.getY());
		return super.contains(p.getX(), p.getY()) && dFromCenter > innerRadius;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut r = (Donut) obj;
			if (this.getCenter().equals(r.getCenter()) && this.getRadius() == r.getRadius()
					&& this.innerRadius == r.innerRadius) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public int getInnerRadius() {
		return innerRadius;
	}

	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}

	@Override
	public String toString() {
		return "Donut: (" + getCenter().getX() + ", " + getCenter().getY() + "), " + "Radius=" + radius
				+ ", Inner Radius=" + this.innerRadius + ", Edge Color: (" + Integer.toString(super.getColor().getRGB())
				+ ")" + ", Inner Color: (" + Integer.toString(super.getInnerColor().getRGB()) + ")";
	}

}