package command;

import java.util.ArrayList;
import geometry.Shape;
import mvc.DrawingModel;

public class CmdRemoveShape implements Command {

    private DrawingModel model;
    private ArrayList<Shape> deleteList;

    public CmdRemoveShape(DrawingModel model, ArrayList<Shape> deleteList) {
        this.model = model;
        this.deleteList = new ArrayList<>(deleteList); 
    }

    @Override
    public void execute() {
        for (Shape shape : deleteList) {
            shape.setSelected(false);
            model.remove(shape);
        }
    }

    @Override
    public void unexecute() {
        for (Shape shape : deleteList) {
            shape.setSelected(true);
            model.add(shape);
        }
        model.getSelectedShapes().addAll(deleteList); 
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Removed Shapes:");
        for (Shape shape : deleteList) {
            sb.append("\n- ").append(shape.toString());
        }
        return sb.toString();
    }
}