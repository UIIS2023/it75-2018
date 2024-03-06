package buttons;

import javax.swing.JButton;

import mvc.DrawingModel;

public class DeleteButton implements ButtonObserver {
    private JButton btnDelete;
    private DrawingModel model;

    public DeleteButton(DrawingModel model, JButton btnDelete) {
        this.model = model;
        this.btnDelete = btnDelete;
        model.addButtonObserver(this);
        updateButtons();
    }

    @Override
    public void updateButtons() {
        btnDelete.setEnabled(model.getSelectedShapes().size() > 0);
    }
}

