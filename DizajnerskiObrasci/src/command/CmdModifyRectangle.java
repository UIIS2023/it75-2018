package command;

import geometry.Rectangle;

public class CmdModifyRectangle implements Command {

	private Rectangle oldR;
	private Rectangle newR;
	private Rectangle initial;

	public CmdModifyRectangle(Rectangle oldR, Rectangle newR) {
		this.oldR = oldR;
		this.newR = newR;
		initial = new Rectangle(oldR.getUpperLeftPoint(),oldR.getWidth(),oldR.getHeight(),oldR.getColor(),oldR.getInnerColor());

	}

	@Override
	public void execute() {
		oldR.setUpperLeftPoint(newR.getUpperLeftPoint());
		oldR.setHeight(newR.getHeight());
		oldR.setWidth(newR.getWidth());
		oldR.setColor(newR.getColor());
		oldR.setInnerColor(newR.getInnerColor());
	}

	@Override
	public void unexecute() {
		oldR.setUpperLeftPoint(initial.getUpperLeftPoint());
		oldR.setHeight(initial.getHeight());
		oldR.setWidth(initial.getWidth());
		oldR.setColor(initial.getColor());
		oldR.setInnerColor(initial.getInnerColor());
	}
	@Override
	public String toString() {
	    return "Modified Rectangle: " + initial.toString() + " ---> " + oldR.toString();
	}
	

}
