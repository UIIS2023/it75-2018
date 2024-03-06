package command;

import mvc.DrawingModel;
import geometry.Shape;

public class CmdBringToFront implements Command {
    private DrawingModel model;
    private Shape shape;

    public CmdBringToFront(DrawingModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    @Override
    public void execute() {
        model.bringToFront(shape);
    }

    @Override
    public void unexecute() {
        model.sendToBack(shape);
    }

    @Override
    public String toString() {
        return "Bring to Front: " + shape.toString();
    }
}