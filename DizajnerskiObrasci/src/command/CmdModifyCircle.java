package command;

import geometry.Circle;

public class CmdModifyCircle implements Command{
	private Circle oldC;
	private Circle newC;
	private Circle initial;
	
	public CmdModifyCircle(Circle oldC, Circle newC) {
		this.oldC=oldC;
		this.newC=newC;
		initial=new Circle(oldC.getCenter(), oldC.getRadius(), oldC.getColor(), oldC.getInnerColor());
	}
	@Override
	public void execute()
	{
		
		oldC.setCenter(newC.getCenter());
		oldC.setColor(newC.getColor());
		oldC.setInnerColor(newC.getInnerColor());
		try {
			oldC.setRadius(newC.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void unexecute()   {
		oldC.setCenter(initial.getCenter());
		oldC.setColor(initial.getColor());
		oldC.setInnerColor(initial.getInnerColor());
		try {
			oldC.setRadius(initial.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public String toString() {
	    return "Modified Circle: " + initial.toString() + " ---> " + newC.toString();
	}
}
