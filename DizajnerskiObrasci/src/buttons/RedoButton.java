package buttons;

import javax.swing.JButton;

import mvc.DrawingModel;

public class RedoButton implements ButtonObserver {
    private JButton btnRedo;
    private DrawingModel model;

    public RedoButton(DrawingModel model, JButton btnRedo) {
        this.model = model;
        this.btnRedo = btnRedo;
        model.addButtonObserver(this);
        updateButtons(); 
    }

    @Override
    public void updateButtons() {
    	btnRedo.setEnabled(!model.getRedoStack().isEmpty());
    }
}