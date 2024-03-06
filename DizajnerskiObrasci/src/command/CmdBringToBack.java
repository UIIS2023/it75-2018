package command;

import mvc.DrawingModel;
import geometry.Shape;

public class CmdBringToBack implements Command {
    private DrawingModel model;
    private Shape shape;

    public CmdBringToBack(DrawingModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    @Override
    public void execute() {
        model.sendToBack(shape);
    }

    @Override
    public void unexecute() {
        model.bringToFront(shape);
    }

    @Override
    public String toString() {
        return "Bring to Back: " + shape.toString();
    }
}