package dialogues;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class DlgModifyLine extends JDialog {

	private final JPanel pnlCenter = new JPanel();
	private JTextField txtStartPointX;
	private JTextField txtStartPointY;
	private JTextField txtEndPointX;
	private JTextField txtEndPointY;
	private JButton btnModify;
	private JButton btnCancel;
	private JButton btnEdgeColor;
	
	private boolean isConfirm;

	private Color edgeColor = new Color(0, 0, 0);
	
	
	public static void main(String[] args) {
		try {
			DlgModifyLine dialog = new DlgModifyLine();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public DlgModifyLine() {
		setTitle("Modify Line");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 509, 274);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		pnlCenter.setBackground(new Color(255, 255, 255));
		pnlCenter.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(pnlCenter, BorderLayout.CENTER);
		JLabel lblStartPointX = new JLabel("Start Point (X,Y):");
		lblStartPointX.setForeground(new Color(0, 0, 0));
		lblStartPointX.setFont(new Font("Tahoma", Font.BOLD, 12));
		JLabel lblEndPointX = new JLabel("End Point (X,Y):");
		lblEndPointX.setForeground(new Color(0, 0, 0));
		lblEndPointX.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		
		btnEdgeColor = new JButton("Choose color");
		btnEdgeColor.setBackground(new Color(255, 255, 255));
		btnEdgeColor.setForeground(new Color(0, 0, 0));
		btnEdgeColor.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEdgeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edgeColor = JColorChooser.showDialog(null, "Choose Line color", edgeColor);
				if (edgeColor != null) {
					btnEdgeColor.setBackground(edgeColor);
				}
				
			}
		});
		txtStartPointX = new JTextField();
		txtStartPointX.setColumns(10);
		txtStartPointY = new JTextField();
		txtStartPointY.setColumns(10);
		txtEndPointX = new JTextField();
		txtEndPointX.setColumns(10);
		txtEndPointY = new JTextField();
		txtEndPointY.setColumns(10);
		GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
		gl_pnlCenter.setHorizontalGroup(
			gl_pnlCenter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCenter.createSequentialGroup()
					.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlCenter.createSequentialGroup()
							.addGap(71)
							.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlCenter.createSequentialGroup()
									.addGap(8)
									.addComponent(lblEndPointX)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtEndPointX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(txtEndPointY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pnlCenter.createSequentialGroup()
									.addComponent(lblStartPointX)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtStartPointX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(txtStartPointY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_pnlCenter.createSequentialGroup()
							.addGap(129)
							.addComponent(btnEdgeColor, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(55, Short.MAX_VALUE))
		);
		gl_pnlCenter.setVerticalGroup(
			gl_pnlCenter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCenter.createSequentialGroup()
					.addGap(72)
					.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStartPointX)
						.addComponent(txtStartPointX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtStartPointY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEndPointX)
						.addComponent(txtEndPointX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtEndPointY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnEdgeColor)
					.addContainerGap(130, Short.MAX_VALUE))
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
				btnModify.setBackground(new Color(255, 255, 255));
				btnModify.setFont(new Font("Tahoma", Font.PLAIN, 12));
				btnModify.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							if(txtStartPointX.getText().trim().equals("") || txtStartPointY.getText().trim().equals("")||txtEndPointX.getText().trim().equals("") || txtEndPointY.getText().trim().equals("")){
								getToolkit().beep();
								JOptionPane.showMessageDialog(null, "Fields are empty!", "ERROR", JOptionPane.ERROR_MESSAGE, null);
								
								return;
							}else{
								isConfirm = true;
								setVisible(false);
							}
							validation(txtStartPointX.getText(), txtStartPointY.getText(),txtEndPointX.getText(), txtEndPointY.getText());
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
				btnCancel.setBackground(new Color(255, 255, 255));
				btnCancel.setForeground(new Color(0, 0, 0));
				btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 12));
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
						.addGap(140)
						.addComponent(btnModify)
						.addGap(18)
						.addComponent(btnCancel)
						.addGap(136))
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

	
	public void validation(String x1, String y1, String x2, String y2) {
		String exp2 = "^(([+-])?([1-9]{1})([0-9]+)?)$";
		if(x1.matches("") || y1.matches("")|| x2.matches("")|| y2.matches("")) {
			
		}
		else if(!x1.matches(exp2) || !y1.matches(exp2)|| !x2.matches(exp2)|| !y2.matches(exp2)){
        	throw new NumberFormatException();
        }
	}

	public JTextField getTxtStartPointX() {
		return txtStartPointX;
	}

	public JTextField getTxtStartPointY() {
		return txtStartPointY;
	}

	public JTextField getTxtEndPointX() {
		return txtEndPointX;
	}

	public JTextField getTxtEndPointY() {
		return txtEndPointY;
	}

	public boolean isConfirm() {
		return isConfirm;
	}

	public JButton getBtnEdgeColor() {
		return btnEdgeColor;
	}
}
