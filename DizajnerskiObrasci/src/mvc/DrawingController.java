package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import command.CmdAddShape;
import command.CmdBringToBack;
import command.CmdBringToFront;
import command.CmdModifyCircle;
import command.CmdModifyDonut;
import command.CmdModifyHexagon;
import command.CmdModifyLine;
import command.CmdModifyPoint;
import command.CmdModifyRectangle;
import command.CmdRemoveShape;
import command.CmdSelectShape;
import command.CmdToBack;
import command.CmdToFront;
import command.Command;
import dialogues.DlgDrawCircle;
import dialogues.DlgDrawDonut;
import dialogues.DlgDrawHexagon;
import dialogues.DlgDrawRectangle;
import dialogues.DlgModifyCircle;
import dialogues.DlgModifyDonut;
import dialogues.DlgModifyHexagon;
import dialogues.DlgModifyLine;
import dialogues.DlgModifyPoint;
import dialogues.DlgModifyRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import hexagonAdapter.HexagonAdapter;

public class DrawingController {
	private DrawingModel model;
	private DrawingFrame frame;
	private String logFilePath = "log.txt";
	private Color edgeColor = Color.BLACK;
	private Color innerColor = Color.WHITE;
	private Color chosenEdgeColor;
	private Color chosenInnerColor;
	private Command cmd;
	private Point startPoint, endPoint;

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		startPoint = null;
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(frame, "Do you want to save the drawing?", "Warning",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Save Drawing As");
					int userSelection = fileChooser.showSaveDialog(null);
					if (userSelection == JFileChooser.APPROVE_OPTION) {
						String chosenFilePath = fileChooser.getSelectedFile().getPath();
						serializeDrawingAndCommands(chosenFilePath);
					}
					frame.dispose();
				} else if (option == JOptionPane.NO_OPTION) {
					frame.dispose();
				}
			}
		});
	}

	public void loadDrawing() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open Drawing");

		int userSelection = fileChooser.showOpenDialog(null);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String filePath = selectedFile.getPath();
			deserializeDrawing(filePath);
			JOptionPane.showMessageDialog(null, "Drawing successfully loaded.", "Success",
					JOptionPane.INFORMATION_MESSAGE);
			frame.repaint();
		}
	}

	public void deserializeDrawing(String filePath) {
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
			List<Shape> shapes = (List<Shape>) inputStream.readObject();
			
			for (Shape shape : shapes) {
				model.add(shape);
			}
			System.out.println("Drawing successfully deserialized from " + filePath);
			System.out.println("Deserialized shapes:");
			for (Shape shape : model.getShapes()) {
				System.out.println(shape);
			}

			String txtFilePath = getTxtFilePath(filePath);
			displayTextContent(txtFilePath);

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private String getTxtFilePath(String drawingFilePath) {
		return drawingFilePath.replace(".ser", ".txt");
	}

	private void displayTextContent(String txtFilePath) {
		try (BufferedReader reader = new BufferedReader(new FileReader(txtFilePath))) {
			StringBuilder textContent = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				textContent.append(line).append("\n");
			}

			frame.getLogTextArea().setText(textContent.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void serializeCommands(Stack<Command> commands, String filePath, String logContent) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			writer.write(logContent);
			System.out.println("Commands serialized to " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void serializeDrawingAndCommands(String filePath) {
		String shapesFilePath = filePath.replace(".txt", ".ser");
		String commandsFilePath = filePath.replace(".ser", ".txt");

		try (ObjectOutputStream shapesOutput = new ObjectOutputStream(new FileOutputStream(shapesFilePath));
				BufferedWriter commandsOutput = new BufferedWriter(new FileWriter(commandsFilePath))) {

			List<Shape> shapes = model.getShapes();
			shapesOutput.writeObject(shapes);

			System.out.println("Shapes serialized to " + shapesFilePath);

			String logContent = frame.getLogTextArea().getText();
			serializeCommands(model.getUndoStack(), commandsFilePath, logContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void executeCommand(Command cmd) {
		cmd.execute();
		model.getUndoStack().push(cmd);
		model.getRedoStack().clear();
	}

	public String serializeCommand(Command cmd) {
		if (cmd instanceof CmdAddShape) {
			return "CmdAddShape: " + ((CmdAddShape) cmd).toString();
		} else if (cmd instanceof CmdModifyCircle) {
			return "CmdModifyCircle: " + ((CmdModifyCircle) cmd).toString();
		} else if (cmd instanceof CmdRemoveShape) {
			return "CmdRemoveShape: " + ((CmdRemoveShape) cmd).toString();
		} else if (cmd instanceof CmdSelectShape) {
			return "CmdSelectShape: " + ((CmdSelectShape) cmd).toString();
		} else if (cmd instanceof CmdModifyDonut) {
			return "CmdModifyDonut: " + ((CmdModifyDonut) cmd).toString();
		} else if (cmd instanceof CmdModifyHexagon) {
			return "CmdModifyHexagon: " + ((CmdModifyHexagon) cmd).toString();
		} else if (cmd instanceof CmdModifyLine) {
			return "CmdModifyLine: " + ((CmdModifyLine) cmd).toString();
		} else if (cmd instanceof CmdModifyPoint) {
			return "CmdModifyPoint: " + ((CmdModifyPoint) cmd).toString();
		} else if (cmd instanceof CmdModifyRectangle) {
			return "CmdModifyRectangle: " + ((CmdModifyRectangle) cmd).toString();
		}
		return "Unknown command type: " + cmd.getClass().getSimpleName();
	}

	public void undo() {
		if (!model.getUndoStack().isEmpty()) {
			Command cmd = model.getUndoStack().pop();
			cmd.unexecute();
			model.getRedoStack().push(cmd);
			frame.repaint();
			printLogMessageRedoUndo(cmd, true);
		}

		model.notifyButtonObservers();
	}

	public void redo() {
		if (!model.getRedoStack().isEmpty()) {
			Command cmd = model.getRedoStack().pop();
			cmd.execute();
			model.getUndoStack().push(cmd);
			frame.repaint();
			printLogMessageRedoUndo(cmd, false);
		}

		model.notifyButtonObservers();

	}

	public void saveDrawingAs() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save Drawing As");

		int userSelection = fileChooser.showSaveDialog(null);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			String filePath = fileChooser.getSelectedFile().getPath();
			serializeDrawingAndCommands(filePath);

			JOptionPane.showMessageDialog(null, "Drawing is saved as: " + filePath, "Success",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void saveDrawingAndCommands() {
		String defaultDrawingPath = "DrawingsLogs/default.ser";
		String defaultCommandPath = "DrawingsLogs/default.txt";
		String logContent = frame.getLogTextArea().getText();
		File drawingFile = new File(defaultDrawingPath);
		if (drawingFile.exists()) {
			int option = JOptionPane.showConfirmDialog(null, "File already exists. Do you want to overwrite?",
					"Warning", JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				serializeDrawingAndCommands(defaultDrawingPath);
				JOptionPane.showMessageDialog(null, "Drawing and commands are saved.", "Success",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (option == JOptionPane.NO_OPTION) {
				saveDrawingAs();
				serializeCommands(model.getUndoStack(), defaultCommandPath, logContent);
			}
		} else {
			serializeDrawingAndCommands(defaultDrawingPath);
			serializeCommands(model.getUndoStack(), defaultCommandPath, logContent);
			JOptionPane.showMessageDialog(null, "Drawing and commands are saved.", "Success",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public Color edgeColor() {
		chosenEdgeColor = JColorChooser.showDialog(null, "Choose color", edgeColor);
		if (chosenEdgeColor != null) {
			edgeColor = chosenEdgeColor;
			return edgeColor;
		}
		return chosenEdgeColor;
	}

	public Color innerColor() {
		chosenInnerColor = JColorChooser.showDialog(null, "Choose color", innerColor);
		if (chosenInnerColor != null) {
			innerColor = chosenInnerColor;
			return innerColor;
		}
		return chosenInnerColor;
	}

	public void enableBtnUndo() {
		model.notifyButtonObservers();
	}

	public void drawPoint(MouseEvent e) {
		Point point = new Point(e.getX(), e.getY(), edgeColor);
		cmd = new CmdAddShape(model, point);
		executeCommand(cmd);
		frame.repaint();
		printLogMessage(cmd);
		enableBtnUndo();

	}

	public void drawLine(MouseEvent e) {
		if (startPoint == null) {
			startPoint = new Point(e.getX(), e.getY(), edgeColor);
		} else {
			endPoint = new Point(e.getX(), e.getY(), edgeColor);
			Line line = new Line(startPoint, endPoint, edgeColor);
			cmd = new CmdAddShape(this.model, line);
			executeCommand(cmd);

			printLogMessage(cmd);
			startPoint = null;
			frame.repaint();

		}
		enableBtnUndo();
	}

	public void drawCircle(MouseEvent e) {
		DlgDrawCircle dlgDrawCircle = new DlgDrawCircle();
		dlgDrawCircle.setVisible(true);
		if (dlgDrawCircle.isConfirm()) {
			Circle circle = new Circle(new Point(e.getX(), e.getY()),
					Integer.parseInt(dlgDrawCircle.getTxtRadius().getText()), edgeColor, innerColor);
			cmd = new CmdAddShape(this.model, circle);
			executeCommand(cmd);
			printLogMessage(cmd);
			frame.repaint();
		}
		enableBtnUndo();

	}

	public void drawRectangle(MouseEvent e) {
		DlgDrawRectangle dlgDrawRectangle = new DlgDrawRectangle();
		dlgDrawRectangle.setVisible(true);
		if (dlgDrawRectangle.isConfirm()) {
			Rectangle rectangle = new Rectangle(new Point(e.getX(), e.getY()),
					Integer.parseInt(dlgDrawRectangle.getTxtWidth().getText()),
					Integer.parseInt(dlgDrawRectangle.getTxtHeight().getText()), edgeColor, innerColor);
			cmd = new CmdAddShape(this.model, rectangle);
			executeCommand(cmd);

			printLogMessage(cmd);
			frame.repaint();
		}
		enableBtnUndo();

	}

	public void drawDonut(MouseEvent e) {
		DlgDrawDonut dlgDrawDonut = new DlgDrawDonut();
		dlgDrawDonut.setVisible(true);
		if (dlgDrawDonut.isConfirm()) {
			Donut donut = new Donut(new Point(e.getX(), e.getY()),
					Integer.parseInt(dlgDrawDonut.getTxtRadius().getText()),
					Integer.parseInt(dlgDrawDonut.getTxtInnerRadius().getText()), edgeColor, innerColor);
			cmd = new CmdAddShape(this.model, donut);
			executeCommand(cmd);
			printLogMessage(cmd);
			frame.repaint();
		}
		enableBtnUndo();

	}

	public void drawHexagon(MouseEvent e) {
		DlgDrawHexagon dlgDrawHexagon = new DlgDrawHexagon();
		dlgDrawHexagon.setVisible(true);
		if (dlgDrawHexagon.isConfirm()) {
			HexagonAdapter hexagon = new HexagonAdapter(new Point(e.getX(), e.getY()),
					Integer.parseInt(dlgDrawHexagon.getTxtRadius().getText()));
			hexagon.setHexagonInnerColor(innerColor);
			hexagon.setHexagonBorderColor(edgeColor);
			cmd = new CmdAddShape(this.model, hexagon);
			executeCommand(cmd);
			printLogMessage(cmd);
			frame.repaint();
		}
		enableBtnUndo();

	}

	public void selectShape(MouseEvent e) {
		for (int i = model.getShapes().size() - 1; i >= 0; i--) {
			Shape selectedShape = model.getByIndex(i);

			if (selectedShape.contains(e.getX(), e.getY())) {
				boolean isSelected = !selectedShape.isSelected();
				cmd = new CmdSelectShape(selectedShape, this.model, isSelected);
				executeCommand(cmd);

				frame.getView().repaint();
				printLogMessage(cmd);
				break;
			}
		}

		model.notifyButtonObservers();
	}

	public void deleteShapes() {
		ArrayList<Shape> toDeleteList = model.getSelectedShapes();
		cmd = new CmdRemoveShape(model, toDeleteList);
		executeCommand(cmd);
		frame.getView().repaint();
		model.notifyButtonObservers();
		printLogMessage(cmd);
	}

	public void updateShapes() {

		try {
			Shape shape = model.getSelectedShapes().get(0);
			if (shape instanceof Point)
				updatePoint((Point) shape);
			else if (shape instanceof Donut)
				updateDonut((Donut) shape);
			else if (shape instanceof Line)
				updateLine((Line) shape);
			else if (shape instanceof Circle)
				updateCircle((Circle) shape);
			else if (shape instanceof HexagonAdapter)
				updateHexagon((HexagonAdapter) shape);
			else if (shape instanceof Rectangle)
				updateRectangle((Rectangle) shape);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "List is empty, select shape!", "Error", JOptionPane.ERROR_MESSAGE,
					null);
		}
	}

	private void updatePoint(Point point) {
		DlgModifyPoint dlgModifyPoint = new DlgModifyPoint();

		dlgModifyPoint.getTxtX().setText(Integer.toString(point.getX()));
		dlgModifyPoint.getTxtY().setText(Integer.toString(point.getY()));
		dlgModifyPoint.getBtnEdgeColor().setBackground(point.getColor());
		dlgModifyPoint.setVisible(true);
		if (dlgModifyPoint.isConfirm()) {
			Point updatedPoint = new Point(Integer.parseInt(dlgModifyPoint.getTxtX().getText()),
					Integer.parseInt(dlgModifyPoint.getTxtY().getText()), true,
					dlgModifyPoint.getBtnEdgeColor().getBackground());
			cmd = new CmdModifyPoint(point, updatedPoint);
			executeCommand(cmd);
			frame.repaint();

			printLogMessage(cmd);
		}
	}

	public void updateLine(Line line) {
		DlgModifyLine dlgModifyLine = new DlgModifyLine();
		dlgModifyLine.getTxtStartPointX().setText(String.valueOf(line.getStartPoint().getX()));
		dlgModifyLine.getTxtStartPointY().setText(String.valueOf(line.getStartPoint().getY()));
		dlgModifyLine.getTxtEndPointX().setText(String.valueOf(line.getEndPoint().getX()));
		dlgModifyLine.getTxtEndPointY().setText(String.valueOf(line.getEndPoint().getY()));
		dlgModifyLine.getBtnEdgeColor().setBackground(line.getColor());
		dlgModifyLine.setVisible(true);
		if (dlgModifyLine.isConfirm()) {
			Line updatedLine = new Line();
			updatedLine.setStartPoint(new Point(Integer.parseInt(dlgModifyLine.getTxtStartPointX().getText()),
					Integer.parseInt(dlgModifyLine.getTxtStartPointY().getText())));
			updatedLine.setEndPoint(new Point(Integer.parseInt(dlgModifyLine.getTxtEndPointX().getText()),
					Integer.parseInt(dlgModifyLine.getTxtEndPointY().getText())));
			updatedLine.setColor(dlgModifyLine.getBtnEdgeColor().getBackground());
			updatedLine.setSelected(true);
			cmd = new CmdModifyLine(line, updatedLine);
			executeCommand(cmd);
			frame.repaint();

			printLogMessage(cmd);
		}
	}

	public void updateCircle(Circle circle) throws NumberFormatException, Exception {
		DlgModifyCircle dlgModifyCircle = new DlgModifyCircle();
		dlgModifyCircle.getTxtRadius().setText(String.valueOf(circle.getRadius()));
		dlgModifyCircle.getTxtCenterX().setText(String.valueOf(circle.getCenter().getX()));
		dlgModifyCircle.getTxtCenterY().setText(String.valueOf(circle.getCenter().getY()));
		dlgModifyCircle.getBtnEdgeColor().setBackground(circle.getColor());
		dlgModifyCircle.getBtnInnerColor().setBackground(circle.getInnerColor());
		dlgModifyCircle.setVisible(true);
		if (dlgModifyCircle.isConfirm()) {
			Circle updatedCircle = new Circle();
			updatedCircle.setCenter(new Point(Integer.parseInt(dlgModifyCircle.getTxtCenterX().getText()),
					Integer.parseInt(dlgModifyCircle.getTxtCenterY().getText())));
			updatedCircle.setColor(dlgModifyCircle.getBtnEdgeColor().getBackground());
			updatedCircle.setInnerColor(dlgModifyCircle.getBtnInnerColor().getBackground());
			updatedCircle.setRadius(Integer.parseInt(dlgModifyCircle.getTxtRadius().getText()));
			updatedCircle.setSelected(true);
			Command cmd = new CmdModifyCircle(circle, updatedCircle);
			executeCommand(cmd);
			frame.repaint();

			printLogMessage(cmd);
		}

	}

	public void updateRectangle(Rectangle rectangle) {
		DlgModifyRectangle dlgModifyRectangle = new DlgModifyRectangle();
		dlgModifyRectangle.getTxtHeight().setText(String.valueOf(rectangle.getHeight()));
		dlgModifyRectangle.getTxtWidth().setText(String.valueOf(rectangle.getWidth()));
		dlgModifyRectangle.getTxtUpperLeftPointX().setText(String.valueOf(rectangle.getUpperLeftPoint().getX()));
		dlgModifyRectangle.getTxtUpperLeftPointY().setText(String.valueOf(rectangle.getUpperLeftPoint().getY()));
		dlgModifyRectangle.getBtnEdgeColor().setBackground(rectangle.getColor());
		dlgModifyRectangle.getBtnInnerColor().setBackground(rectangle.getInnerColor());
		dlgModifyRectangle.setVisible(true);
		if (dlgModifyRectangle.isConfirm()) {
			Rectangle updatedRec = new Rectangle();
			updatedRec.setColor(dlgModifyRectangle.getBtnEdgeColor().getBackground());
			updatedRec.setInnerColor(dlgModifyRectangle.getBtnInnerColor().getBackground());
			updatedRec.setHeight(Integer.parseInt(dlgModifyRectangle.getTxtHeight().getText()));
			updatedRec.setWidth(Integer.parseInt(dlgModifyRectangle.getTxtWidth().getText()));
			updatedRec.setSelected(true);
			updatedRec
					.setUpperLeftPoint(new Point(Integer.parseInt(dlgModifyRectangle.getTxtUpperLeftPointX().getText()),
							Integer.parseInt(dlgModifyRectangle.getTxtUpperLeftPointY().getText())));
			Command cmd = new CmdModifyRectangle(rectangle, updatedRec);
			executeCommand(cmd);
			frame.repaint();

			printLogMessage(cmd);
		}
	}

	public void updateDonut(Donut donut) throws NumberFormatException, Exception {
		DlgModifyDonut dlgModifyDonut = new DlgModifyDonut();
		dlgModifyDonut.getTxtCenterX().setText(String.valueOf(donut.getCenter().getX()));
		dlgModifyDonut.getTxtCenterY().setText(String.valueOf(donut.getCenter().getY()));
		dlgModifyDonut.getTxtInnerRadius().setText(String.valueOf(donut.getInnerRadius()));
		dlgModifyDonut.getTxtRadius().setText(String.valueOf(donut.getRadius()));
		dlgModifyDonut.getBtnEdgeColor().setBackground(donut.getColor());
		dlgModifyDonut.getBtnInnerColor().setBackground(donut.getInnerColor());
		dlgModifyDonut.setVisible(true);
		if (dlgModifyDonut.isConfirm()) {
			Donut updatedDonut = new Donut();
			updatedDonut.setCenter(new Point(Integer.parseInt(dlgModifyDonut.getTxtCenterX().getText()),
					Integer.parseInt(dlgModifyDonut.getTxtCenterY().getText())));
			updatedDonut.setColor(dlgModifyDonut.getBtnEdgeColor().getBackground());
			updatedDonut.setInnerColor(dlgModifyDonut.getBtnInnerColor().getBackground());
			updatedDonut.setInnerRadius(Integer.parseInt(dlgModifyDonut.getTxtInnerRadius().getText()));
			updatedDonut.setRadius(Integer.parseInt(dlgModifyDonut.getTxtRadius().getText()));
			updatedDonut.setSelected(true);
			Command cmd = new CmdModifyDonut(donut, updatedDonut);
			executeCommand(cmd);
			frame.repaint();

			printLogMessage(cmd);
		}

	}

	public void updateHexagon(HexagonAdapter oldHexagon) {
		DlgModifyHexagon dlgModifyHexagon = new DlgModifyHexagon();
		dlgModifyHexagon.getTxtCenterHX().setText(Integer.toString(oldHexagon.getHexagonCenter().getX()));
		dlgModifyHexagon.getTxtCenterHY().setText(Integer.toString(oldHexagon.getHexagonCenter().getY()));
		dlgModifyHexagon.getTxtRadiusH().setText(Integer.toString(oldHexagon.getHexagonRadius()));
		dlgModifyHexagon.getBtnEdgeColor().setBackground(oldHexagon.getHexagonBorderColor());
		dlgModifyHexagon.getBtnInnerColor().setBackground(oldHexagon.getHexagonInnerColor());
		dlgModifyHexagon.setVisible(true);
		if (dlgModifyHexagon.isConfirm()) {
			int newCenterX = Integer.parseInt(dlgModifyHexagon.getTxtCenterHX().getText());
			int newCenterY = Integer.parseInt(dlgModifyHexagon.getTxtCenterHY().getText());
			int newRadius = Integer.parseInt(dlgModifyHexagon.getTxtRadiusH().getText());
			Color newEdgeColor = dlgModifyHexagon.getBtnEdgeColor().getBackground();
			Color newInnerColor = dlgModifyHexagon.getBtnInnerColor().getBackground();
			HexagonAdapter newHexagon = new HexagonAdapter(new Point(newCenterX, newCenterY), newRadius);
			newHexagon.setHexagonInnerColor(newInnerColor);
			newHexagon.setHexagonBorderColor(newEdgeColor);
			newHexagon.setSelected(true);

			Command cmd = new CmdModifyHexagon(oldHexagon, newHexagon);
			executeCommand(cmd);
			frame.repaint();
			printLogMessage(cmd);
		}
	}

	public void printLogMessageRedoUndo(Command cmd, boolean isUndo) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
			LocalDateTime currentDateTime = LocalDateTime.now();
			String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			String logMessage = formattedDateTime + ">>> ";

			if (isUndo) {
				logMessage += "Undo: ";
			} else {
				logMessage += "Redo: ";
			}

			if (cmd instanceof CmdModifyDonut) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdAddShape) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdRemoveShape) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdSelectShape) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdModifyCircle) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdModifyHexagon) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdModifyPoint) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdModifyLine) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdToBack) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdToFront) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdBringToBack) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdBringToFront) {
				logMessage += cmd.toString();
			}

			writer.write(logMessage);
			frame.getLogTextArea().append(logMessage + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printLogMessage(Command cmd) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
			LocalDateTime currentDateTime = LocalDateTime.now();
			String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			String logMessage = formattedDateTime + ">>> ";

			if (cmd instanceof CmdModifyDonut) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdAddShape) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdRemoveShape) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdSelectShape) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdModifyCircle) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdModifyHexagon) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdModifyPoint) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdModifyLine) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdToBack) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdToFront) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdBringToBack) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdBringToFront) {
				logMessage += cmd.toString();
			} else if (cmd instanceof CmdModifyRectangle) {
				logMessage += cmd.toString();
			}

			writer.write(logMessage);
			frame.getLogTextArea().append(logMessage + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private BufferedReader nextLineReader = null;

	public void loadNextLine() throws Exception {
		if (nextLineReader == null) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Load Next Line");

			int userSelection = fileChooser.showOpenDialog(null);
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				String filePath = selectedFile.getPath();

				try {
					nextLineReader = new BufferedReader(new FileReader(filePath));
				} catch (IOException e) {
					e.printStackTrace();
					nextLineReader = null;
					return;
				}
			} else {
				return;
			}
		}

		try {
			nextLineReader.mark(1000);
			String line = nextLineReader.readLine();

			if (line != null) {
				if (line.startsWith("-")) {
					frame.getLogTextArea().append(line + "\n");

					String nextLine;
					while ((nextLine = nextLineReader.readLine()) != null && nextLine.startsWith("-")) {
						frame.getLogTextArea().append(nextLine + "\n");
						nextLineReader.mark(1000);
					}

					nextLineReader.reset();

				} else {
					processLine(line);

					SwingUtilities.invokeLater(() -> {
						frame.getLogTextArea().append(line + "\n");
						frame.repaint();
					});
				}
			} else {
				nextLineReader.close();
				nextLineReader = null;
				JOptionPane.showMessageDialog(null, "End of file reached.", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			System.out.println("Read line: " + line);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processLine(String line) throws Exception {
		String[] tokens = line.split(">>>");
		String actionToken = (tokens.length > 1) ? tokens[1].trim() : "";
		String[] actionTokens = actionToken.split(" ");
		String action = "";
		if (actionTokens.length > 0) {
			action = actionTokens[0];
		} else {
			System.out.println("ActionTokens array is empty.");
		}
		switch (action) {
		case "Deselected:":
			int centerXx = Integer.parseInt(actionTokens[2].replaceAll("[^0-9]", ""));
			int centerYx = Integer.parseInt(actionTokens[3].replaceAll("[^0-9]", ""));

			for (int i = model.getShapes().size() - 1; i >= 0; i--) {
				Shape selectedShape = model.getByIndex(i);
				if (selectedShape.contains(centerXx, centerYx)) {
					boolean isSelected = !selectedShape.isSelected();
					cmd = new CmdSelectShape(selectedShape, this.model, isSelected);
					executeCommand(cmd);
					break;
				}

			}

			model.notifyButtonObservers();
			break;

		case "Added":
			try {

				String shapeType = actionTokens[2];
				System.out.println("Shape: " + shapeType);

				Shape newShape = null;
				switch (shapeType) {
				case "Point:":
					String[] parts1 = actionTokens[3].replaceAll("[^\\d,-]", "").split(",");
					String[] parts2 = actionTokens[6].split("[()]");
					String numberString = parts2[1].trim();
					String[] parts3 = actionTokens[4].replaceAll("[^\\d,-]", "").split(",");
					int shapeX = Integer.parseInt(parts1[0]);
					int shapeY = Integer.parseInt(parts3[0]);
					Color shapeColor = new Color(Integer.parseInt(numberString));
					newShape = new Point(shapeX, shapeY, shapeColor);

					break;
				case "Line:":
					try {
						if (actionTokens.length >= 9) {
							int startX = Integer.parseInt(actionTokens[3].replaceAll("[^0-9]", ""));
							int startY = Integer.parseInt(actionTokens[4].replaceAll("[^0-9]", ""));
							int endX = Integer.parseInt(actionTokens[5].replaceAll("[^0-9]", ""));
							int endY = Integer.parseInt(actionTokens[6].replaceAll("[^0-9]", ""));

							Color lineColor = new Color(Integer.parseInt(actionTokens[8].replaceAll("[^\\d-]+", "")));
							newShape = new Line(new Point(startX, startY, lineColor), new Point(endX, endY, lineColor));

						} else {
							System.out.println("Not enough elements in actionTokens array.");
						}
					} catch (NumberFormatException e) {
						System.out.println("Invalid number format: " + e.getMessage());
					}
					break;
				case "Circle:":
					try {
						int centerX = Integer.parseInt(actionTokens[3].replaceAll("[^0-9]", ""));
						int centerY = Integer.parseInt(actionTokens[4].replaceAll("[^0-9]", ""));
						int radius = Integer.parseInt(actionTokens[5].replaceAll("[^0-9]", ""));
						Color edgeColor = new Color(Integer.parseInt(actionTokens[8].replaceAll("[^\\d-]+", "")));
						Color innerColor = new Color(Integer.parseInt(actionTokens[11].replaceAll("[^\\d-]+", "")));

						newShape = new Circle(new Point(centerX, centerY, edgeColor), radius, edgeColor, innerColor);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
					break;
				case "Rectangle:":
					try {
						int upperLeftX = Integer.parseInt(actionTokens[3].replaceAll("[^0-9]", ""));
						int upperLeftY = Integer.parseInt(actionTokens[4].replaceAll("[^0-9]", ""));
						String[] WH = actionTokens[5].replaceAll("[^\\d,-]", "").split(",");
						int width = Integer.parseInt(WH[0]);
						int height = Integer.parseInt(WH[1]);
						Color edgeColor = new Color(Integer.parseInt(actionTokens[8].replaceAll("[^\\d-]+", "")));
						Color innerColor = new Color(Integer.parseInt(actionTokens[11].replaceAll("[^\\d-]+", "")));
						newShape = new Rectangle(new Point(upperLeftX, upperLeftY, edgeColor), width, height, edgeColor,
								innerColor);
					} catch (NumberFormatException e) {
					}
					break;
				case "Donut:":
					try {

						int centerX = Integer.parseInt(actionTokens[3].replaceAll("[^0-9]", ""));
						int centerY = Integer.parseInt(actionTokens[4].replaceAll("[^0-9]", ""));
						int radius = Integer.parseInt(actionTokens[5].replaceAll("[^0-9]", ""));
						int innerRadius = Integer.parseInt(actionTokens[7].replaceAll("[^0-9]", ""));
						Color edgeColor = new Color(Integer.parseInt(actionTokens[10].replaceAll("[^\\d-]+", "")));
						Color innerColor = new Color(Integer.parseInt(actionTokens[13].replaceAll("[^\\d-]+", "")));
						newShape = new Donut(new Point(centerX, centerY, edgeColor), radius, innerRadius, edgeColor,
								innerColor);

					} catch (NumberFormatException e) {
					}
					break;
				case "Hexagon:":
					try {

						int centerX = Integer.parseInt(actionTokens[3].replaceAll("[^0-9]", ""));
						int centerY = Integer.parseInt(actionTokens[4].replaceAll("[^0-9]", ""));
						int radius = Integer.parseInt(actionTokens[5].replaceAll("[^0-9]", ""));
						String extractedValue1 = actionTokens[6].replaceAll(".*\\((.*)\\).*", "$1");
						String extractedValue2 = actionTokens[7].replaceAll(".*\\((.*)\\).*", "$1");
						Color edgeColor = new Color(Integer.parseInt(extractedValue1));
						Color innerColor = new Color(Integer.parseInt(extractedValue2));
						newShape = new HexagonAdapter(new Point(centerX, centerY), radius, edgeColor, innerColor);

					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
					break;

				default:
					break;
				}
				if (newShape != null) {
					cmd = new CmdAddShape(this.model, newShape);
					executeCommand(cmd);
					enableBtnUndo();
				}
			} catch (NumberFormatException e) {
			}
			break;
		case "Removed":
			ArrayList<Shape> toDeleteList = model.getSelectedShapes();
			cmd = new CmdRemoveShape(model, toDeleteList);
			executeCommand(cmd);
			frame.getView().repaint();
			model.notifyButtonObservers();
			break;
		case "Selected:":
			int centerX = Integer.parseInt(actionTokens[2].replaceAll("[^0-9]", ""));
			int centerY = Integer.parseInt(actionTokens[3].replaceAll("[^0-9]", ""));
			for (int i = model.getShapes().size() - 1; i >= 0; i--) {
				Shape selectedShape = model.getByIndex(i);
				if (selectedShape instanceof Donut) {
					if (((Donut) selectedShape).getCenter().getX() == centerX
							&& ((Circle) selectedShape).getCenter().getY() == centerY) {
						boolean isSelected = true;
						cmd = new CmdSelectShape(selectedShape, this.model, isSelected);
						executeCommand(cmd);
						frame.getView().repaint();
					}
				}
				if (selectedShape instanceof Circle) {
					if (((Circle) selectedShape).getCenter().getX() == centerX
							&& ((Circle) selectedShape).getCenter().getY() == centerY) {
						boolean isSelected = true;
						cmd = new CmdSelectShape(selectedShape, this.model, isSelected);
						executeCommand(cmd);
						frame.getView().repaint();
					}
				}
				if (selectedShape instanceof HexagonAdapter) {
					if (((HexagonAdapter) selectedShape).getX() == centerX
							&& ((HexagonAdapter) selectedShape).getY() == centerY) {
						boolean isSelected = true;
						cmd = new CmdSelectShape(selectedShape, this.model, isSelected);
						executeCommand(cmd);
						frame.getView().repaint();
					}
				}
				if (selectedShape instanceof Point) {
					if (((Point) selectedShape).getX() == centerX && ((Point) selectedShape).getY() == centerY) {
						boolean isSelected = true;
						cmd = new CmdSelectShape(selectedShape, this.model, isSelected);
						executeCommand(cmd);
						frame.getView().repaint();
					}
				}
				if (selectedShape instanceof Rectangle) {
					if (((Rectangle) selectedShape).getUpperLeftPoint().getX() == centerX
							&& ((Rectangle) selectedShape).getUpperLeftPoint().getY() == centerY) {
						boolean isSelected = true;
						cmd = new CmdSelectShape(selectedShape, this.model, isSelected);
						executeCommand(cmd);
						frame.getView().repaint();
					}
				}
				if (selectedShape instanceof Line) {
					if (((Line) selectedShape).getStartPoint().getX() == centerX
							&& ((Line) selectedShape).getStartPoint().getY() == centerY) {
						boolean isSelected = true;
						cmd = new CmdSelectShape(selectedShape, this.model, isSelected);
						executeCommand(cmd);
						frame.getView().repaint();
					}
				}

			}

			model.notifyButtonObservers();

			break;
		case "Undo:":
			undo();
			break;
		case "Redo:":
			redo();
			break;
		case "To Front:":
			ToFront();
			break;
		case "To Back":
			ToBack();
			break;
		case "Bring To Back":
			BringToBack();
			break;
		case "Bring To Front":
			BringToFront();
			break;
		case "Modified":
			try {
				Shape modifiedShape = null;
				String shapeType = actionTokens[1];
				switch (shapeType) {
				case "Point:":

					Shape point = model.getSelectedShapes().get(0);
					int x = Integer.parseInt(actionTokens[9].replaceAll("[^0-9]", ""));
					int y = Integer.parseInt(actionTokens[10].replaceAll("[^0-9]", ""));
					Color color = new Color(Integer.parseInt(actionTokens[12].replaceAll("[^\\d-]+", "")));
					modifiedShape = new Point(x, y, true, color);
					cmd = new CmdModifyPoint((Point) point, (Point) modifiedShape);
					executeCommand(cmd);
					frame.repaint();
					break;
				case "Line:":
					System.out.println("------------------" + actionTokens.length);
					for (String token : actionTokens) {
						System.out.println(token);
					}
					Shape oldLine = model.getSelectedShapes().get(0);
					int startX = Integer.parseInt(actionTokens[11].replaceAll("[^0-9]", ""));
					int startY = Integer.parseInt(actionTokens[12].replaceAll("[^0-9]", ""));
					int endX = Integer.parseInt(actionTokens[13].replaceAll("[^\\d-]+", ""));
					int endY = Integer.parseInt(actionTokens[14].replaceAll("[^\\d-]+", ""));
					color = new Color(Integer.parseInt(actionTokens[16].replaceAll("[^0-9,-]", "")));
					modifiedShape = new Line(new Point(startX, startY), new Point(endX, endY), true, color);
					cmd = new CmdModifyLine((Line) oldLine, (Line) modifiedShape);
					executeCommand(cmd);
					frame.repaint();
					break;
				case "Donut:":
					Shape oldDonut = model.getSelectedShapes().get(0);
					centerX = Integer.parseInt(actionTokens[16].replaceAll("[^0-9]", ""));
					centerY = Integer.parseInt(actionTokens[17].replaceAll("[^0-9]", ""));
					int radius = Integer.parseInt(actionTokens[18].replaceAll("[^0-9]", ""));
					int innerRadius = Integer.parseInt(actionTokens[20].replaceAll("[^0-9]", ""));
					Color innerColor = new Color(Integer.parseInt(actionTokens[26].replaceAll("[^\\d-]+", "")));
					Color edgeColor = new Color(Integer.parseInt(actionTokens[23].replaceAll("[^\\d-]+", "")));
					modifiedShape = new Donut(new Point(centerX, centerY), radius, innerRadius, true, edgeColor,
							innerColor);
					cmd = new CmdModifyDonut((Donut) oldDonut, (Donut) modifiedShape);
					executeCommand(cmd);
					frame.repaint();
					break;
				case "Circle:":

					Shape oldCircle = model.getSelectedShapes().get(0);
					centerX = Integer.parseInt(actionTokens[14].replaceAll("[^0-9]", ""));
					centerY = Integer.parseInt(actionTokens[15].replaceAll("[^0-9]", ""));
					radius = Integer.parseInt(actionTokens[16].replaceAll("[^0-9]", ""));
					innerColor = new Color(Integer.parseInt(actionTokens[22].replaceAll("[^\\d-]+", "")));
					edgeColor = new Color(Integer.parseInt(actionTokens[19].replaceAll("[^\\d-]+", "")));

					modifiedShape = new Circle(new Point(centerX, centerY), radius, true, edgeColor, innerColor);
					cmd = new CmdModifyCircle((Circle) oldCircle, (Circle) modifiedShape);
					executeCommand(cmd);
					frame.repaint();
					break;
				case "Rectangle:":
					Shape rectangle = model.getSelectedShapes().get(0);
					int upperLeftX = Integer.parseInt(actionTokens[14].replaceAll("[^0-9]", ""));
					int upperLeftY = Integer.parseInt(actionTokens[15].replaceAll("[^0-9]", ""));
					String[] wh = actionTokens[16].split(",");
					int width = Integer.parseInt(wh[0].replaceAll("[^0-9]", ""));
					int height = Integer.parseInt(wh[1].replaceAll("[^0-9]", ""));
					edgeColor = new Color(Integer.parseInt(actionTokens[19].replaceAll("[^\\d-]+", "")));
					innerColor = new Color(Integer.parseInt(actionTokens[22].replaceAll("[^\\d-]+", "")));
					modifiedShape = new Rectangle(new Point(upperLeftX, upperLeftY), width, height, true, edgeColor,
							innerColor);
					cmd = new CmdModifyRectangle((Rectangle) rectangle, (Rectangle) modifiedShape);
					executeCommand(cmd);
					frame.repaint();
					break;
				case "Hexagon:":

					Shape hex = model.getSelectedShapes().get(0);
					centerX = Integer.parseInt(actionTokens[10].replaceAll("[^0-9]", ""));
					centerY = Integer.parseInt(actionTokens[11].replaceAll("[^0-9]", ""));
					radius = Integer.parseInt(actionTokens[12].replaceAll("[^0-9]", ""));
					edgeColor = new Color(Integer.parseInt(actionTokens[13].replaceAll("[^\\d-]+", "")));
					innerColor = new Color(Integer.parseInt(actionTokens[14].replaceAll("[^\\d-]+", "")));
					modifiedShape = new HexagonAdapter(new Point(centerX, centerY), radius, edgeColor, innerColor);
					cmd = new CmdModifyHexagon((HexagonAdapter) hex, (HexagonAdapter) modifiedShape);
					executeCommand(cmd);
					frame.repaint();
					break;
				}

				frame.getView().repaint();
			} catch (NumberFormatException | IndexOutOfBoundsException e) {

			}
			break;

		default:

			break;
		}
	}
	public void BringToFront() {
		if (!model.getSelectedShapes().isEmpty()) {
			Shape selectedShape = model.getSelectedShapes().get(0);
			Command cmd = new CmdBringToFront(model, selectedShape);
			executeCommand(cmd);
			frame.repaint();
			printLogMessage(cmd);
		}
	}

	public void BringToBack() {
		if (!model.getSelectedShapes().isEmpty()) {
			Shape selectedShape = model.getSelectedShapes().get(0);
			Command cmd = new CmdBringToBack(model, selectedShape);
			executeCommand(cmd);
			frame.repaint();
			printLogMessage(cmd);
		}
	}

	public void ToFront() {
		if (!model.getSelectedShapes().isEmpty()) {
			Shape selectedShape = model.getSelectedShapes().get(0);
			Command cmd = new CmdToFront(model, selectedShape);
			executeCommand(cmd);
			frame.repaint();
			printLogMessage(cmd);
		}
	}

	public void ToBack() {
		if (!model.getSelectedShapes().isEmpty()) {
			Shape selectedShape = model.getSelectedShapes().get(0);
			Command cmd = new CmdToBack(model, selectedShape);
			executeCommand(cmd);
			frame.repaint();
			printLogMessage(cmd);
		}
	}
}
