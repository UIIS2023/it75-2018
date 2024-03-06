package command;

import geometry.Line;

public class CmdModifyLine implements Command{
	
	private Line oldL;
	private Line newL;
	private Line initial;

	
	public CmdModifyLine(Line oldL,Line newL)
	{
		this.newL=newL;
		this.oldL=oldL;
		initial = new Line(oldL.getStartPoint(),oldL.getEndPoint(),oldL.getColor());
		
	}
	@Override
	public void execute() {
		oldL.setStartPoint(newL.getStartPoint());
		oldL.setEndPoint(newL.getEndPoint());
		oldL.setColor(newL.getColor());
	}

	@Override
	public void unexecute() {
		oldL.setStartPoint(initial.getStartPoint());
		oldL.setEndPoint(initial.getEndPoint());
		oldL.setColor(initial.getColor());
	}
	@Override
	public String toString() {
	    return "Modified Line: " + initial.toString() + " ---> " + oldL.toString();
	}
	

	
}
