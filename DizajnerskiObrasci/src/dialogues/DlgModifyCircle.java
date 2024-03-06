package dialogues;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Font;

@SuppressWarnings("serial")
public class DlgModifyCircle extends JDialog {

	private final JPanel pnlCenter = new JPanel();
	private JButton btnModify;
	private JButton btnCancel;
	private JTextField txtCenterX;
	private JTextField txtCenterY;
	private JTextField txtRadius;
	private JButton btnEdgeColor;
	private JButton btnInnerColor;
	
	private boolean isConfirm;
	
	private Color edgeColor = new Color(0, 0, 0);
	private Color innerColor = new Color(255,255,255);
	

	public static void main(String[] args) {
		try {
			DlgModifyCircle dialog = new DlgModifyCircle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgModifyCircle() {
		setBounds(100, 100, 437, 271);
		setTitle("Modify Circle");
		setResizable(false);
		setModal(true);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		pnlCenter.setBackground(new Color(255, 255, 255));
		pnlCenter.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(pnlCenter, BorderLayout.CENTER);
		
		JLabel lblXCoordinate = new JLabel("Center (X,Y):");
		lblXCoordinate.setForeground(new Color(0, 0, 0));
		lblXCoordinate.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblRadius = new JLabel("Radius:");
		lblRadius.setForeground(new Color(0, 0, 0));
		lblRadius.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		btnEdgeColor = new JButton("Edge color");
		btnEdgeColor.setBackground(new Color(255, 255, 255));
		btnEdgeColor.setForeground(new Color(0, 0, 0));
		btnEdgeColor.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEdgeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edgeColor = JColorChooser.showDialog(null, "Choose Circle Egde color", edgeColor);
				if (edgeColor != null) {
					btnEdgeColor.setBackground(edgeColor);
				}
			}
		});
		
		btnInnerColor = new JButton("Inner color");
		btnInnerColor.setForeground(new Color(0, 0, 0));
		btnInnerColor.setBackground(new Color(255, 255, 255));
		btnInnerColor.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				innerColor = JColorChooser.showDialog(null, "Choose Circle Inner color", innerColor);
				if (innerColor != null) {
					btnInnerColor.setBackground(innerColor);
				}
			}
		});
		
		txtCenterX = new JTextField();
		txtCenterX.setColumns(10);
		
		txtCenterY = new JTextField();
		txtCenterY.setColumns(10);
		
		txtRadius = new JTextField();
		txtRadius.setColumns(10);
		GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
		gl_pnlCenter.setHorizontalGroup(
			gl_pnlCenter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCenter.createSequentialGroup()
					.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlCenter.createSequentialGroup()
							.addGap(91)
							.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
								.addComponent(lblXCoordinate)
								.addComponent(lblRadius))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
								.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_pnlCenter.createSequentialGroup()
									.addComponent(txtCenterX, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtCenterY, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_pnlCenter.createSequentialGroup()
							.addGap(144)
							.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
								.addComponent(btnEdgeColor, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
								.addComponent(btnInnerColor, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))))
					.addGap(137))
		);
		gl_pnlCenter.setVerticalGroup(
			gl_pnlCenter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCenter.createSequentialGroup()
					.addGap(31)
					.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblXCoordinate)
						.addComponent(txtCenterX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtCenterY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRadius))
					.addGap(18)
					.addComponent(btnEdgeColor)
					.addGap(11)
					.addComponent(btnInnerColor)
					.addContainerGap(153, Short.MAX_VALUE))
		);
		pnlCenter.setLayout(gl_pnlCenter);
		{
			JPanel pnlSouth = 
					new JPanel();
			pnlSouth.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
			pnlSouth.setBackground(new Color(255, 255, 255));
			getContentPane().add(pnlSouth, BorderLayout.SOUTH);
			{
				btnModify = new JButton("Modify");
				btnModify.setForeground(new Color(0, 0, 0));
				btnModify.setBackground(new Color(255, 255, 255));
				btnModify.setFont(new Font("Tahoma", Font.PLAIN, 12));
				btnModify.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							
							 if(txtCenterX.getText().trim().equals("") || txtCenterY.getText().trim().equals("") || txtRadius.getText().trim().equals("")){
									getToolkit().beep();
									JOptionPane.showMessageDialog(null, "Fields are empty!", "ERROR", JOptionPane.ERROR_MESSAGE, null);
									
									return;
								}else if(Integer.parseInt(txtRadius.getText()) < 1){
									getToolkit().beep();
									JOptionPane.showMessageDialog(null, "Radius can't be less than 1!", "ERROR", JOptionPane.ERROR_MESSAGE, null);
									
									return;
								}else{
									isConfirm = true;
									setVisible(false);
								}
							 validation(txtCenterX.getText(), txtCenterY.getText(), txtRadius.getText());
						
						} catch (NumberFormatException exc) {
							getToolkit().beep();
							JOptionPane.showMessageDialog(null, "Please insert numbers!", "ERROR", JOptionPane.ERROR_MESSAGE, null);
							
							return;
						}
					}
					});
				btnModify.setActionCommand("OK");
				getRootPane().setDefaultButton(btnModify);
			}
			{
				btnCancel = new JButton("Cancel");
				btnCancel.setForeground(new Color(0, 0, 0));
				btnCancel.setBackground(new Color(255, 255, 255));
				btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 12));
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				btnCancel.setActionCommand("Cancel");
			}
			GroupLayout gl_pnlSouth = new GroupLayout(pnlSouth);
			gl_pnlSouth.setHorizontalGroup(
				gl_pnlSouth.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlSouth.createSequentialGroup()
						.addGap(142)
						.addComponent(btnModify)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnCancel)
						.addGap(179))
			);
			gl_pnlSouth.setVerticalGroup(
				gl_pnlSouth.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlSouth.createSequentialGroup()
						.addGroup(gl_pnlSouth.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnModify)
							.addComponent(btnCancel))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			pnlSouth.setLayout(gl_pnlSouth);
		}
	}
	
	
	public void validation(String x, String y, String radius) {
		String exp2 = "^(([+-])?([1-9]{1})([0-9]+)?)$";
		if(x.matches("") || y.matches("") || radius.matches("")) {
			
		}
		else if(!x.matches(exp2) || !y.matches(exp2) || !radius.matches(exp2)){
        	throw new NumberFormatException();
        }
	}

	public JTextField getTxtCenterX() {
		return txtCenterX;
	}

	public JTextField getTxtCenterY() {
		return txtCenterY;
	}

	public JTextField getTxtRadius() {
		return txtRadius;
	}

	public JButton getBtnEdgeColor() {
		return btnEdgeColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public boolean isConfirm() {
		return isConfirm;
	}
}
