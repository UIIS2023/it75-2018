package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Circle extends SurfaceShape implements Serializable{

	private Point center = new Point();
	protected int radius;

	public Circle() {

	}

	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}

	public Circle(Point center, int radius, boolean selected) {
		this(center, radius);
		this.setSelected(selected);
	}

	public Circle(Point center, int radius, boolean selected, Color color) {
		this(center, radius, selected);
		this.setColor(color);
	}

	public Circle(Point center, int radius, boolean selected, Color color, Color innerColor) {
		this(center, radius, selected, color);
		this.setInnerColor(innerColor);
	}

	public Circle(Point center, int radius, Color color, Color innerColor) {
		this(center, radius);
		this.setColor(color);
		this.setInnerColor(innerColor);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Circle) {
			return this.radius - ((Circle) o).radius;
		}
		return 0;
	}

	@Override
	public void moveBy(int byX, int byY) {
		this.center.moveBy(byX, byY);

	}

	public boolean contains(int x, int y) {
		return center.distance(x, y) <= radius;
	}

	public boolean contains(Point p) {
		return center.distance(p.getX(), p.getY()) <= radius;
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillOval(this.center.getX() - this.radius + 1, this.center.getY() - this.radius + 1, this.radius * 2 - 2,
				this.radius * 2 - 2);

	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawOval(this.center.getX() - this.radius, this.center.getY() - this.radius, this.radius * 2,
				this.radius * 2);
		this.fill(g);

		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.center.getX() - 3, this.center.getY() - 3, 6, 6);
			g.drawRect(this.center.getX() - this.radius - 3, this.center.getY() - 3, 6, 6);
			g.drawRect(this.center.getX() + this.radius - 3, this.center.getY() - 3, 6, 6);
			g.drawRect(this.center.getX() - 3, this.center.getY() - this.radius - 3, 6, 6);
			g.drawRect(this.center.getX() - 3, this.center.getY() + this.radius - 3, 6, 6);
		}

	}

	public double area() {
		return radius * radius * Math.PI;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Circle) {
			Circle c = (Circle) obj;
			if (this.center.equals(c.center) && this.radius == c.radius) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) throws Exception {
		if (radius > 0) {
			this.radius = radius;
		} else {
			throw new NumberFormatException("Radius has to be a value greater then 0!");
		}
	}

	@Override
	public String toString() {
		return "Circle: (" + this.center.getX() + ", " + this.center.getY() + "), " + "Radius=" + this.radius
				+ ", Edge Color: (" + Integer.toString(getColor().getRGB()) + ")" + ", Inner Color: ("
				+ Integer.toString(getInnerColor().getRGB()) + ")";
	}

	public void selectCircle(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(this.center.getX() - 3, this.center.getY() - 3, 6, 6);
		g.drawRect(this.center.getX() - radius - 3, this.center.getY() - 3, 6, 6);
		g.drawRect(this.center.getX() + radius - 3, this.center.getY() - 3, 6, 6);
		g.drawRect(this.center.getX() - 3, this.center.getY() - radius - 3, 6, 6);
		g.drawRect(this.center.getX() - 3, this.center.getY() + radius - 3, 6, 6);
	}

}