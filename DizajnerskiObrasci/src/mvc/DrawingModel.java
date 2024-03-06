package mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Stack;

import buttons.ButtonObserver;
import command.Command;
import geometry.Shape;

public class DrawingModel extends Observable{

	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private ArrayList<Shape> selectedShapes = new ArrayList<Shape>();
	private List<ButtonObserver> buttonObservers = new ArrayList<>();
	private Stack<Command> undoStack = new Stack<>();
	private Stack<Command> redoStack = new Stack<>();
    private boolean isSelected = false;
    
    public Stack<Command> getUndoStack() {
        return undoStack;
    }

    public Stack<Command> getRedoStack() {
        return redoStack;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
	public void add(Shape shape) {
		shapes.add(shape);
	}

	public void remove(Shape shape) {
		selectedShapes.remove(shape);
		shapes.remove(shape);
	}

	public Shape getByIndex(int index) {
		return shapes.get(index);
	}

	public ArrayList<Shape> getShapes() {
		return shapes;
	}

	public ArrayList<Shape> getSelectedShapes() {
		return selectedShapes;
	}

	public void bringToFront(Shape shape) {
		if (shapes.contains(shape)) {
			shapes.remove(shape);
			shapes.add(shape);

		}
	}

	public void sendToBack(Shape shape) {
		if (shapes.contains(shape)) {
			shapes.remove(shape);
			shapes.add(0, shape);

		}
	}

	public void toFront(Shape shape) {
		if (shapes.contains(shape)) {
			int currentIndex = shapes.indexOf(shape);
			if (currentIndex < shapes.size() - 1) {
				shapes.remove(shape);
				shapes.add(currentIndex + 1, shape);

			}
		}
	}

	public void toBack(Shape shape) {
		if (shapes.contains(shape)) {
			int currentIndex = shapes.indexOf(shape);
			if (currentIndex > 0) {
				shapes.remove(shape);
				shapes.add(currentIndex - 1, shape);

			}
		}
	}

	public void removeByIndex(int index) {
		 if (index >= 0 && index < shapes.size()) {
		        shapes.remove(index);
		    }
	}
     public void addButtonObserver(ButtonObserver observer) {
        buttonObservers.add(observer);
    }

     public void removeButtonObserver(ButtonObserver observer) {
        buttonObservers.remove(observer);
    }

    void notifyButtonObservers() {
        for (ButtonObserver observer : buttonObservers) {
            observer.updateButtons();
        }
    }

}
