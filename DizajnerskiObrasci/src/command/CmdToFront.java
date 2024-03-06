package command;

import mvc.DrawingModel;
import geometry.Shape;

public class CmdToFront implements Command {
    private DrawingModel model;
    private Shape shape;

    public CmdToFront(DrawingModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    @Override
    public void execute() {
        model.toFront(shape);
    }

    @Override
    public void unexecute() {
        model.toBack(shape);
    }

    @Override
    public String toString() {
        return "To Front: " + shape.toString();
    }
}