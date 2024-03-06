package mvc;

import javax.swing.JFrame;

import buttons.DeleteButton;
import buttons.ModifyButton;
import buttons.RedoButton;
import buttons.UndoButton;

public class Application {

	public static void main(String[] args) {
		DrawingModel model = new DrawingModel();
		DrawingFrame frame = new DrawingFrame();
		DeleteButton deleteButton = new DeleteButton(model, frame.getBtnDelete());
		UndoButton undoButton = new UndoButton(model, frame.getBtnUndo());
		RedoButton redoButton = new RedoButton(model, frame.getBtnRedo());
		ModifyButton modifyButton = new ModifyButton(model, frame.getBtnModify());
		DrawingController controller = new DrawingController(model,frame);
		frame.getView().setModel(model);
		model.addButtonObserver(deleteButton);
		model.addButtonObserver(modifyButton);
		model.addButtonObserver(undoButton);
		model.addButtonObserver(redoButton);
		frame.setController(controller);
		frame.setTitle("IT75/2018 Stanojkovic Andrija");
		frame.setSize(1100, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
	}

}
