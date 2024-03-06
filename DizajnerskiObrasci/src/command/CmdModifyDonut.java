package command;
import geometry.Donut;


public class CmdModifyDonut implements Command {
	private Donut oldD;
	private Donut newD;
	private Donut initial;

	public CmdModifyDonut(Donut oldD, Donut newD) {

		this.oldD = oldD;
		this.newD = newD;
		initial = new Donut(oldD.getCenter(),oldD.getRadius(),oldD.getInnerRadius(),oldD.getColor(),oldD.getInnerColor());
	}

	@Override
	public void execute(){
		oldD.setCenter(newD.getCenter());
		oldD.setColor(newD.getColor());
		oldD.setInnerColor(newD.getInnerColor());
		try {
			oldD.setRadius(newD.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oldD.setInnerRadius(newD.getInnerRadius());

	}

	@Override
	public void unexecute() {
		oldD.setCenter(initial.getCenter());
		oldD.setColor(initial.getColor());
		oldD.setInnerColor(initial.getInnerColor());
		try {
			oldD.setRadius(initial.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oldD.setInnerRadius(initial.getInnerRadius());

	}
	@Override
	public String toString() {
	    return "Modified Donut: " + initial.toString() + " ---> " + oldD.toString();
	}
	
	
}
