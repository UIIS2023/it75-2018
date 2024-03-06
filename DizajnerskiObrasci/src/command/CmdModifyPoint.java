package command;

import geometry.Point;

public class CmdModifyPoint implements Command {
	
	private Point oldP;
	private Point newP;
	private Point initial;

	public CmdModifyPoint(Point oldP, Point newP) {
		this.oldP = oldP;
		this.newP = newP;
		initial= new Point(oldP.getX(),oldP.getY(),oldP.getColor());
	}

	@Override
	public void execute() {
		oldP.setX(newP.getX());
		oldP.setY(newP.getY());
		oldP.setColor(newP.getColor());
	}

	@Override
	public void unexecute() {
		oldP.setX(initial.getX());
		oldP.setY(initial.getY());
		oldP.setColor(initial.getColor());
	}
	@Override
	public String toString() {
	    return "Modified Point: " + initial.toString() + " ---> " + oldP.toString();
	}
	

}
