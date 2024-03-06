package command;

import geometry.Point;
import hexagonAdapter.HexagonAdapter;

public class CmdModifyHexagon implements Command {

    private HexagonAdapter oldH;
    private HexagonAdapter newH;
    private HexagonAdapter initial;

    public CmdModifyHexagon(HexagonAdapter oldH, HexagonAdapter newH) {
        this.oldH = oldH;
        this.newH = newH;
        this.initial = new HexagonAdapter(oldH.getHexagonCenter(),oldH.getHexagonRadius());
        this.initial.setHexagonBorderColor(oldH.getHexagonBorderColor());
        this.initial.setHexagonInnerColor(oldH.getHexagonInnerColor());
    }

    @Override
    public void execute() {
        if (newH != null) {
            oldH.moveBy(newH.getX(), newH.getY());
            oldH.setHexagonBorderColor(newH.getHexagonBorderColor());
            oldH.setHexagonInnerColor(newH.getHexagonInnerColor());
            oldH.setHexagonRadius(newH.getHexagonRadius());
     
        } else {
            System.out.println("New Hexagon is null in execute!");
        }
    }

    @Override
    public void unexecute() {
        if (initial != null) {
            oldH.moveBy(initial.getX(), initial.getY());
            oldH.setHexagonBorderColor(initial.getHexagonBorderColor());
            oldH.setHexagonInnerColor(initial.getHexagonInnerColor());
            oldH.setHexagonRadius(initial.getHexagonRadius());
        } else {
            System.out.println("Initial Hexagon is null in unexecute!");
        }
    }

    @Override
    public String toString() {
        if (initial != null && oldH != null) {
            return "Modified Hexagon: " + initial.toString() + " ---> " + oldH.toString();
        } else {
            return "CmdModifyHexagon with null values!";
        }
    }
}