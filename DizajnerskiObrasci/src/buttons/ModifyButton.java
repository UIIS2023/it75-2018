package buttons;

import javax.swing.JButton;

import mvc.DrawingModel;

public class ModifyButton implements ButtonObserver {
    private JButton btnModify;
    private DrawingModel model;

    public ModifyButton(DrawingModel model, JButton btnModify) {
        this.model = model;
        this.btnModify = btnModify;
        model.addButtonObserver(this);
        updateButtons(); 
    }

    @Override
    public void updateButtons() {
    	btnModify.setEnabled(model.getSelectedShapes().size() == 1);
    }	
}