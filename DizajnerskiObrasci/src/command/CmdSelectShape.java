package command;

import geometry.Shape;
import mvc.DrawingModel;

public class CmdSelectShape implements Command {

    private Shape shape;
    private DrawingModel model;
    private boolean isSelected;

    public CmdSelectShape(Shape shape, DrawingModel model, boolean isSelected) {
        this.shape = shape;
        this.model = model;
        this.isSelected = isSelected;
    }

    @Override
    public void execute() {
        shape.setSelected(isSelected);
        if (isSelected) {
            model.getSelectedShapes().add(shape);
        } else {
            model.getSelectedShapes().remove(shape);
        }
    }

    @Override
    public void unexecute() {
        isSelected = !isSelected;
        shape.setSelected(isSelected);

        if (isSelected) {
            model.getSelectedShapes().add(shape);
        } else {
            model.getSelectedShapes().remove(shape);
        }
    }

    @Override
    public String toString() {
        return (isSelected ? "Selected: " : "Deselected: ") + shape.toString();
    }
}