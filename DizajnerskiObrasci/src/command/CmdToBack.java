package command;

import mvc.DrawingModel;
import geometry.Shape;

public class CmdToBack implements Command {
    private DrawingModel model;
    private Shape shape;

    public CmdToBack(DrawingModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    @Override
    public void execute() {
        model.toBack(shape);
    }

    @Override
    public void unexecute() {
        model.toFront(shape);
    }

    @Override
    public String toString() {
        return "To Back: " + shape.toString();
    }
}