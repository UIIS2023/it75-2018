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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;

@SuppressWarnings("serial")
public class DlgModifyPoint extends JDialog {

	private final JPanel pnlCenter = new JPanel();
	private JTextField txtX;
	private JTextField txtY;
	private JButton btnModify;
	private JButton btnCancel;
	private JButton btnEdgeColor;

	private boolean isConfirm;

	private Color edgeColor = new Color(0, 0, 0);

	public static void main(String[] args) {
		try {
			DlgModifyPoint dialog = new DlgModifyPoint();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public DlgModifyPoint() {
		setTitle("Modify Point");
		setBounds(100, 100, 401, 214);
		setResizable(false);
		setModal(true);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		pnlCenter.setBackground(new Color(255, 255, 255));
		pnlCenter.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(pnlCenter, BorderLayout.CENTER);
		JLabel lblXCoordinate = new JLabel("(X,Y) coordinates:");
		lblXCoordinate.setForeground(new Color(0, 0, 0));
		lblXCoordinate.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtX = new JTextField();
		txtX.setColumns(10);
		txtY = new JTextField();
		txtY.setColumns(10);
		btnEdgeColor = new JButton("Choose color");
		btnEdgeColor.setBackground(new Color(255, 255, 255));
		btnEdgeColor.setForeground(new Color(0, 0, 0));
		btnEdgeColor.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEdgeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edgeColor = JColorChooser.showDialog(null, "Choose Point color", edgeColor);
				if (edgeColor != null) {
					btnEdgeColor.setBackground(edgeColor);
				}
			}
		});
		GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
		gl_pnlCenter.setHorizontalGroup(
			gl_pnlCenter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCenter.createSequentialGroup()
					.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlCenter.createSequentialGroup()
							.addGap(60)
							.addComponent(lblXCoordinate)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtX, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtY, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlCenter.createSequentialGroup()
							.addGap(88)
							.addComponent(btnEdgeColor, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(88, Short.MAX_VALUE))
		);
		gl_pnlCenter.setVerticalGroup(
			gl_pnlCenter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCenter.createSequentialGroup()
					.addGap(31)
					.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblXCoordinate)
						.addComponent(txtX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnEdgeColor)
					.addContainerGap(162, Short.MAX_VALUE))
		);
		pnlCenter.setLayout(gl_pnlCenter);
		{
			JPanel pnlSouth = new JPanel();
			pnlSouth.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
			pnlSouth.setBackground(new Color(255, 255, 255));
			getContentPane().add(pnlSouth, BorderLayout.SOUTH);
			{
				btnModify = new JButton("Modify");
				btnModify.setForeground(new Color(0, 0, 0));
				btnModify.setFont(new Font("Tahoma", Font.PLAIN, 12));
				btnModify.setBackground(new Color(255, 255, 255));
				btnModify.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							if (txtX.getText().trim().equals("") || txtY.getText().trim().equals("")) {
								getToolkit().beep();
								JOptionPane.showMessageDialog(null, "Fields are empty!", "ERROR",
										JOptionPane.ERROR_MESSAGE, null);
								isConfirm = false;
								return;
							} else {
								isConfirm = true;
								setVisible(false);
							}
							validation(txtX.getText(), txtY.getText());
						} catch (NumberFormatException exc) {
							getToolkit().beep();
							JOptionPane.showMessageDialog(null, "Please insert numbers!", "ERROR",
									JOptionPane.ERROR_MESSAGE, null);
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
				btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 12));
				btnCancel.setBackground(new Color(255, 255, 255));
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancel.setActionCommand("Cancel");
			}
			GroupLayout gl_pnlSouth = new GroupLayout(pnlSouth);
			gl_pnlSouth.setHorizontalGroup(
				gl_pnlSouth.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlSouth.createSequentialGroup()
						.addGap(103)
						.addComponent(btnModify)
						.addGap(18)
						.addComponent(btnCancel)
						.addGap(143))
			);
			gl_pnlSouth.setVerticalGroup(
				gl_pnlSouth.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_pnlSouth.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_pnlSouth.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnModify)
							.addComponent(btnCancel))
						.addContainerGap())
			);
			pnlSouth.setLayout(gl_pnlSouth);
		}
	}

	public void validation(String x, String y) {
		String exp2 = "^(([+-])?([1-9]{1})([0-9]+)?)$";
		if (x.matches("") || y.matches("")) {

		} else if (!x.matches(exp2) || !y.matches(exp2)) {
			throw new NumberFormatException();
		}
	}

	public boolean isConfirm() {
		return isConfirm;
	}


	public JTextField getTxtX() {
		return txtX;
	}


	public JTextField getTxtY() {
		return txtY;
	}


	public JButton getBtnEdgeColor() {
		return btnEdgeColor;
	}

}
