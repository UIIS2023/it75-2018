package mvc;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JToggleButton;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
public class DrawingFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private DrawingView view = new DrawingView();
	private DrawingController controller;
	public boolean flag=false;
	private Color color;
	 private JTextArea logTextArea;
	private JScrollPane scrollPane;

	private JToggleButton tglBtnPoint, tglBtnLine, tglBtnRectangle, tglBtnDonut, tglBtnCircle, tglBtnHexagon,
			tglBtnSelect;

	private JButton btnModify, btnDelete, btnEdgeColor, btnInnerColor,btnRedo,btnUndo, btnLoadLineByLine, btnLoadNext,btnToFront,btnToBack,btnBringToFront,btnBringToBack;

	private ButtonGroup groupShapes = new ButtonGroup();

	public DrawingFrame() {
		getContentPane().setBackground(new Color(128, 128, 192));
		setBounds(100, 100, 1100, 750);

		JPanel pnlShapes = new JPanel();
		pnlShapes.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		pnlShapes.setToolTipText("");
		pnlShapes.setBackground(new Color(192, 192, 192));

		JPanel pnlMain = new JPanel();
	    logTextArea = new JTextArea();
	    logTextArea.setEditable(false);
	    logTextArea.setRows(10);
	    
	    scrollPane = new JScrollPane(logTextArea);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    pnlMain.setLayout(new BorderLayout());
	    pnlMain.add(view, BorderLayout.CENTER);
	    pnlMain.add(scrollPane, BorderLayout.SOUTH);
		tglBtnPoint = new JToggleButton("Point");
		tglBtnPoint.setForeground(new Color(0, 0, 0));
		tglBtnPoint.setBackground(new Color(255, 255, 255));

		tglBtnLine = new JToggleButton("Line");
		tglBtnLine.setForeground(new Color(0, 0, 0));
		tglBtnLine.setBackground(new Color(255, 255, 255));

		tglBtnRectangle = new JToggleButton("Rectangle");
		tglBtnRectangle.setBackground(new Color(255, 255, 255));
		tglBtnRectangle.setForeground(new Color(0, 0, 0));

		tglBtnCircle = new JToggleButton("Circle");
		tglBtnCircle.setForeground(new Color(0, 0, 0));
		tglBtnCircle.setBackground(new Color(255, 255, 255));

		tglBtnDonut = new JToggleButton("Donut");
		tglBtnDonut.setForeground(new Color(0, 0, 0));
		tglBtnDonut.setBackground(new Color(255, 255, 255));

		tglBtnHexagon = new JToggleButton("Hexagon");
		tglBtnHexagon.setForeground(new Color(0, 0, 0));
		tglBtnHexagon.setBackground(new Color(255, 255, 255));

	    btnLoadNext = new JButton("Load Next");
	    btnLoadNext.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
                try {
					controller.loadNextLine();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

	        }
	    });
	    btnLoadNext.setForeground(Color.BLACK);
	    btnLoadNext.setBackground(Color.WHITE);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem saveAsMenuItem = new JMenuItem("Save As");
		saveAsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveDrawingAs();
			}
		});
		fileMenu.add(saveAsMenuItem);

		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveDrawingAndCommands();
			}
		});
		fileMenu.add(saveMenuItem);

		JMenuItem openMenuItem = new JMenuItem("Open");
		openMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.loadDrawing();
			}
		});
		fileMenu.add(openMenuItem);
		
		fileMenu.addSeparator();

		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		fileMenu.add(exitMenuItem);

		groupShapes.add(tglBtnPoint);
		groupShapes.add(tglBtnLine);
		groupShapes.add(tglBtnCircle);
		groupShapes.add(tglBtnRectangle);
		groupShapes.add(tglBtnDonut);
		groupShapes.add(tglBtnHexagon);

		GroupLayout gl_pnlShapes = new GroupLayout(pnlShapes);
		gl_pnlShapes.setHorizontalGroup(gl_pnlShapes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlShapes.createSequentialGroup().addGap(17)
						.addGroup(gl_pnlShapes.createParallelGroup(Alignment.TRAILING)
								.addComponent(tglBtnCircle, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
								.addComponent(tglBtnPoint, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_pnlShapes.createParallelGroup(Alignment.LEADING)
								.addComponent(tglBtnLine, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
								.addComponent(tglBtnDonut, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_pnlShapes.createParallelGroup(Alignment.LEADING)
								.addComponent(tglBtnHexagon, GroupLayout.PREFERRED_SIZE, 115,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(tglBtnRectangle, GroupLayout.PREFERRED_SIZE, 115,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(29, Short.MAX_VALUE)));
		gl_pnlShapes.setVerticalGroup(gl_pnlShapes.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlShapes.createSequentialGroup().addContainerGap()
						.addGroup(gl_pnlShapes.createParallelGroup(Alignment.BASELINE).addComponent(tglBtnPoint)
								.addComponent(tglBtnLine).addComponent(tglBtnRectangle))
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_pnlShapes.createParallelGroup(Alignment.BASELINE).addComponent(tglBtnHexagon)
								.addComponent(tglBtnCircle).addComponent(tglBtnDonut))
						.addContainerGap()));
		pnlShapes.setLayout(gl_pnlShapes);
		
		JPanel pnlActions = new JPanel();
		pnlActions.setForeground(new Color(255, 255, 255));
		pnlActions.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		pnlActions.setBackground(new Color(192, 192, 192));

		tglBtnSelect = new JToggleButton("Select");
		tglBtnSelect.setForeground(new Color(0, 0, 0));
		tglBtnSelect.setBackground(new Color(255, 255, 255));

		btnModify = new JButton("Modify");
		btnModify.setEnabled(false);
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.updateShapes();
			}
		});
		btnModify.setForeground(new Color(0, 0, 0));
		btnModify.setBackground(new Color(255, 255, 255));

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.deleteShapes();
			}
		});
		btnDelete.setForeground(new Color(0, 0, 0));
		btnDelete.setBackground(new Color(255, 255, 255));
		
		btnUndo = new JButton("Undo");
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undo();
			}
		});
		btnUndo.setForeground(Color.BLACK);
		btnUndo.setBackground(Color.WHITE);
		
		btnRedo = new JButton("Redo");
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		Font largerFont = new Font(logTextArea.getFont().getName(), Font.PLAIN, 18);
		logTextArea.setFont(largerFont);
		btnRedo.setForeground(Color.BLACK);
		btnRedo.setBackground(Color.WHITE);
		btnUndo.setEnabled(false);
		btnRedo.setEnabled(false);
		btnDelete.setEnabled(false);
		GroupLayout gl_pnlMain = new GroupLayout(pnlMain);
		gl_pnlMain.setHorizontalGroup(
		    gl_pnlMain.createParallelGroup(Alignment.LEADING)
		        .addGroup(gl_pnlMain.createSequentialGroup()
		            .addContainerGap()
		            .addGroup(gl_pnlMain.createParallelGroup(Alignment.LEADING)
		                .addComponent(view, GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
		                .addGroup(gl_pnlMain.createSequentialGroup()
		                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 1000, GroupLayout.PREFERRED_SIZE)
		                    .addContainerGap()
		                )
		            )
		        )
		);
		gl_pnlMain.setVerticalGroup(
		    gl_pnlMain.createParallelGroup(Alignment.LEADING)
		        .addGroup(gl_pnlMain.createSequentialGroup()
		            .addContainerGap()
		            .addComponent(view, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		            .addPreferredGap(ComponentPlacement.RELATED)
		            .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
		            .addContainerGap()
		        )
		);
		pnlMain.setLayout(gl_pnlMain);
		
		 btnToFront =new BasicArrowButton(BasicArrowButton.EAST);

		 btnToBack = new BasicArrowButton(BasicArrowButton.WEST);
		
		 btnBringToFront = new BasicArrowButton(BasicArrowButton.EAST);
		
		 btnBringToBack = new BasicArrowButton(BasicArrowButton.WEST);
		 btnBringToBack.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		controller.BringToBack();
		 	}
		 });
		GroupLayout gl_pnlActions = new GroupLayout(pnlActions);
		gl_pnlActions.setHorizontalGroup(
			gl_pnlActions.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlActions.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_pnlActions.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnUndo, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tglBtnSelect, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlActions.createParallelGroup(Alignment.LEADING)
						.addComponent(btnRedo, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
						.addComponent(btnModify, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlActions.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlActions.createSequentialGroup()
							.addComponent(btnToBack, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnToFront, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBringToBack, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBringToFront, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)))
					.addGap(25))
		);
		gl_pnlActions.setVerticalGroup(
			gl_pnlActions.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlActions.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlActions.createParallelGroup(Alignment.BASELINE)
						.addComponent(tglBtnSelect)
						.addComponent(btnModify)
						.addComponent(btnDelete))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlActions.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlActions.createSequentialGroup()
							.addComponent(btnBringToBack, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_pnlActions.createSequentialGroup()
							.addComponent(btnBringToFront, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_pnlActions.createSequentialGroup()
							.addComponent(btnToBack, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_pnlActions.createSequentialGroup()
							.addGroup(gl_pnlActions.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_pnlActions.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnUndo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnRedo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(btnToFront, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
							.addGap(11))))
		);
		pnlActions.setLayout(gl_pnlActions);
		groupShapes.add(tglBtnSelect);

		JPanel pnlColors = new JPanel();
		pnlColors.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		pnlColors.setBackground(new Color(192, 192, 192));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlMain, GroupLayout.DEFAULT_SIZE, 1085, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(pnlShapes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(pnlActions, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pnlColors, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addContainerGap())))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(pnlColors, 0, 0, Short.MAX_VALUE)
						.addComponent(pnlActions, 0, 0, Short.MAX_VALUE)
						.addComponent(pnlShapes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlMain, GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
					.addGap(29))
		);

		btnInnerColor = new JButton("Inner Color");
		btnInnerColor.setForeground(new Color(0, 0, 0));
		btnInnerColor.setBackground(new Color(255, 255, 255));

		btnEdgeColor = new JButton("Edge Color");
		btnEdgeColor.setForeground(new Color(0, 0, 0));
		btnEdgeColor.setBackground(new Color(255, 255, 255));
		GroupLayout gl_pnlColors = new GroupLayout(pnlColors);
		gl_pnlColors.setHorizontalGroup(
			    gl_pnlColors.createParallelGroup(Alignment.LEADING)
			        .addGroup(gl_pnlColors.createSequentialGroup()
			            .addContainerGap()
			            .addComponent(btnInnerColor, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
			            .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			            .addGroup(gl_pnlColors.createParallelGroup(Alignment.LEADING)
			                .addComponent(btnEdgeColor, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
			                .addComponent(btnLoadNext, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
			            .addContainerGap())
			);

		gl_pnlColors.setVerticalGroup(
		    gl_pnlColors.createParallelGroup(Alignment.LEADING)
		        .addGroup(gl_pnlColors.createSequentialGroup()
		            .addGap(10)
		            .addGroup(gl_pnlColors.createParallelGroup(Alignment.BASELINE)
		                .addComponent(btnInnerColor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnEdgeColor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		            .addPreferredGap(ComponentPlacement.RELATED)
		            .addGroup(gl_pnlColors.createParallelGroup(Alignment.BASELINE)
		                .addComponent(btnLoadNext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		            .addContainerGap(23, Short.MAX_VALUE))
		);

		pnlColors.setLayout(gl_pnlColors);
		btnBringToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.BringToFront();
			}
		});
		btnToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.ToFront();
			}
		});
		btnToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.ToBack();
			}
		});

		btnEdgeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = controller.edgeColor();
				if (color != null) {
					btnEdgeColor.setBackground(color);
				}
			}
		});
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = controller.innerColor();
				if (color != null) {
					btnInnerColor.setBackground(color);
				}
			}
		});
		getContentPane().setLayout(groupLayout);

		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tglBtnPoint.isSelected())
					controller.drawPoint(e);
				else if (tglBtnLine.isSelected())
					controller.drawLine(e);
				else if (tglBtnCircle.isSelected())
					controller.drawCircle(e);
				else if (tglBtnRectangle.isSelected())
					controller.drawRectangle(e);
				else if (tglBtnDonut.isSelected())
					controller.drawDonut(e);
				else if (tglBtnHexagon.isSelected())
					controller.drawHexagon(e);
				else if (tglBtnSelect.isSelected()) {
					controller.selectShape(e);
				}
			}
		});

		pnlMain.add(view, GroupLayout.DEFAULT_SIZE);
		view.setBackground(Color.WHITE);
		view.setPreferredSize(new Dimension(1060, 900));
	}

	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}

	public JToggleButton getTglBtnRectangle() {
		return tglBtnRectangle;
	}

	public void setTglBtnRectangle(JToggleButton tglBtnRectangle) {
		this.tglBtnRectangle = tglBtnRectangle;
	}

	public JToggleButton getTglBtnSelect() {
		return tglBtnSelect;
	}

	public void setTglBtnSelect(JToggleButton tglBtnSelect) {
		this.tglBtnSelect = tglBtnSelect;
	}

	public JToggleButton getTglBtnPoint() {
		return tglBtnPoint;
	}

	public JToggleButton getTglBtnLine() {
		return tglBtnLine;
	}

	public JToggleButton getTglBtnDonut() {
		return tglBtnDonut;
	}

	public JToggleButton getTglBtnCircle() {
		return tglBtnCircle;
	}

	public JToggleButton getTglBtnHexagon() {
		return tglBtnHexagon;
	}

	public JButton getBtnModify() {
		return btnModify;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public JButton getBtnEdgeColor() {
		return btnEdgeColor;
	}

	public void setBtnEdgeColor(JButton btnEdgeColor) {
		this.btnEdgeColor = btnEdgeColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public void setBtnInnerColor(JButton btnInnerColor) {
		this.btnInnerColor = btnInnerColor;
	}
	public JButton getBtnRedo() {
		return btnRedo;
	}
	public JButton getBtnUndo() {
		return btnUndo;
	}

    public JTextArea getLogTextArea() {
        return logTextArea;
    }
}
