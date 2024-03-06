package buttons;

import javax.swing.JButton;

import mvc.DrawingModel;

public class UndoButton implements ButtonObserver {
    private JButton btnUndo;
    private DrawingModel model;

    public UndoButton(DrawingModel model, JButton btnUndo) {
        this.model = model;
        this.btnUndo = btnUndo;
        model.addButtonObserver(this);
        updateButtons(); 
    }

    @Override
    public void updateButtons() {
        btnUndo.setEnabled(!model.getUndoStack().isEmpty());
    }
}