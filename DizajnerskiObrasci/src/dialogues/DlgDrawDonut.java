package dialogues;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
public class DlgDrawDonut extends JDialog {

	private final JPanel pnlCenter = new JPanel();
	private JButton btnDraw;
	private JButton btnCancel;
	private JTextField txtRadius;
	private JTextField txtInnerRadius;
	private boolean isConfirm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgDrawDonut dialog = new DlgDrawDonut();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgDrawDonut() {
		setBounds(100, 100, 437, 218);
		setTitle("Draw Donut");
		setResizable(false);
		setModal(true);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		pnlCenter.setBackground(new Color(255, 255, 255));
		pnlCenter.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(pnlCenter, BorderLayout.CENTER);
		
		JLabel lblRadius = new JLabel("Radius:");
		lblRadius.setForeground(new Color(0, 0, 0));
		lblRadius.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblInnerRadius = new JLabel("Inner radius:");
		lblInnerRadius.setForeground(new Color(0, 0, 0));
		lblInnerRadius.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		txtRadius = new JTextField();
		txtRadius.setColumns(10);
		
		txtInnerRadius = new JTextField();
		txtInnerRadius.setColumns(10);
		GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
		gl_pnlCenter.setHorizontalGroup(
			gl_pnlCenter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCenter.createSequentialGroup()
					.addGap(83)
					.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_pnlCenter.createSequentialGroup()
							.addComponent(lblInnerRadius)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(txtInnerRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlCenter.createSequentialGroup()
							.addComponent(lblRadius)
							.addGap(83)
							.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(211, Short.MAX_VALUE))
		);
		gl_pnlCenter.setVerticalGroup(
			gl_pnlCenter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCenter.createSequentialGroup()
					.addGap(36)
					.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRadius))
					.addGap(18)
					.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInnerRadius)
						.addComponent(txtInnerRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(244, Short.MAX_VALUE))
		);
		pnlCenter.setLayout(gl_pnlCenter);
		{
			JPanel pnlSouth = new JPanel();
			pnlSouth.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
			pnlSouth.setBackground(new Color(255, 255, 255));
			getContentPane().add(pnlSouth, BorderLayout.SOUTH);
			{
				btnDraw = new JButton("Draw");
				btnDraw.setFont(new Font("Tahoma", Font.PLAIN, 12));
				btnDraw.setBackground(new Color(255, 255, 255));
				btnDraw.setForeground(new Color(0, 0, 0));
				btnDraw.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(txtRadius.getText().trim().equals("")|| txtInnerRadius.getText().trim().equals("")) {
							getToolkit().beep();
							JOptionPane.showMessageDialog(null, "Fields are empty! Please insert radius", "Error", JOptionPane.ERROR_MESSAGE, null);
							isConfirm = false;
							return;
						} 
						try {
							validate(txtRadius.getText(),txtInnerRadius.getText());
						} catch (NumberFormatException exc) {
							getToolkit().beep();
							JOptionPane.showMessageDialog(null, "Please insert numbers only!", "Error", JOptionPane.ERROR_MESSAGE, null);
							isConfirm = false;
							return;
						}
						 if(Integer.parseInt(txtRadius.getText()) < 1){
								getToolkit().beep();
								JOptionPane.showMessageDialog(null, "Radius can't be negative number!", "Error", JOptionPane.ERROR_MESSAGE, null);
								isConfirm = false;
								return;
							}
						 else if(Integer.parseInt(txtInnerRadius.getText()) < 1){
								getToolkit().beep();
								JOptionPane.showMessageDialog(null, "Inner radius can't be negative number!", "Error", JOptionPane.ERROR_MESSAGE, null);
								isConfirm = false;
								return;
							}
						 else if(Integer.parseInt(txtInnerRadius.getText())>Integer.parseInt(txtRadius.getText())){
							 getToolkit().beep();
								JOptionPane.showMessageDialog(null, "Inner radius can't be greater than radius!", "Error", JOptionPane.ERROR_MESSAGE, null);
								isConfirm = false;
								return;
						 }
						 else if(Integer.parseInt(txtInnerRadius.getText())== Integer.parseInt(txtRadius.getText())){
							 getToolkit().beep();
								JOptionPane.showMessageDialog(null, "Inner radius can't be the same value as radius!", "Error", JOptionPane.ERROR_MESSAGE, null);
								isConfirm = false;
								return;
						 }else {
								isConfirm = true;
								dispose();
							}	
						
					}
				});
				btnDraw.setActionCommand("OK");
				getRootPane().setDefaultButton(btnDraw);
			}
			{
				btnCancel = new JButton("Cancel");
				btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 12));
				btnCancel.setBackground(new Color(255, 255, 255));
				btnCancel.setForeground(new Color(0, 0, 0));
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
						.addGap(128)
						.addComponent(btnDraw, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
						.addGap(132))
			);
			gl_pnlSouth.setVerticalGroup(
				gl_pnlSouth.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlSouth.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_pnlSouth.createParallelGroup(Alignment.TRAILING)
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnDraw, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addContainerGap())
			);
			pnlSouth.setLayout(gl_pnlSouth);
		}
	}
	public void validate(String radius,String innerRadius) {
		String supp = "^(([+-])?([1-9]{1})([0-9]+)?)$";
	    if(!radius.matches(supp)){  
	    	throw new NumberFormatException();
	    }
	    else if(!innerRadius.matches(supp)){
	    	throw new NumberFormatException();
	    }
	}
	
	public boolean isConfirm() {
		return isConfirm;
	}

	public void setConfirm(boolean isConfirm) {
		this.isConfirm = isConfirm;
	}

	public JTextField getTxtRadius() {
		return txtRadius;
	}

	public JTextField getTxtInnerRadius() {
		return txtInnerRadius;
	}
}
