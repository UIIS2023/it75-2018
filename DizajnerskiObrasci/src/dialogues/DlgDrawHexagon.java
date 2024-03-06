package dialogues;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DlgDrawHexagon extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtRadius;
	private boolean isConfirm;
	private JLabel lblRadius;
	private JButton okButton;
	private JButton cancelButton;
	private JPanel buttonPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgDrawHexagon dialog = new DlgDrawHexagon();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DlgDrawHexagon() {
		setBounds(100, 100, 430, 199);
		setTitle("Draw Hexagon");
		setResizable(false);
		setModal(true);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			lblRadius = new JLabel("Radius:");
			lblRadius.setForeground(new Color(0, 0, 0));
			lblRadius.setFont(new Font("Tahoma", Font.BOLD, 12));
		}
		{
			txtRadius = new JTextField();
			txtRadius.setColumns(10);
		}
		{
			buttonPane = new JPanel();
			buttonPane.setBackground(new Color(255, 255, 255));
			{
				okButton = new JButton("Draw");
				okButton.setForeground(new Color(0, 0, 0));
				okButton.setBackground(new Color(255, 255, 255));
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							
							if(txtRadius.getText().trim().equals("")) {
								getToolkit().beep();
								JOptionPane.showMessageDialog(null, "Field is empty! Please insert radius", "Error", JOptionPane.ERROR_MESSAGE, null);
								isConfirm = false;
								return;
							} else if(Integer.parseInt(txtRadius.getText()) < 1){
								getToolkit().beep();
								JOptionPane.showMessageDialog(null, "Radius can't be less than 1!", "Error", JOptionPane.ERROR_MESSAGE, null);
								isConfirm = false;
								return;
							} else {
								isConfirm = true;
								dispose();
							}	
							validate(txtRadius.getText());
						} catch (NumberFormatException exc) {
							getToolkit().beep();
							JOptionPane.showMessageDialog(null, "Please insert only numbers!", "Error", JOptionPane.ERROR_MESSAGE, null);
							isConfirm = false;
							return;
						}
						
						
					}
				});
				okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setBackground(new Color(255, 255, 255));
				cancelButton.setForeground(new Color(0, 0, 0));
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(146)
						.addComponent(okButton)
						.addGap(18)
						.addComponent(cancelButton)
						.addGap(126))
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(cancelButton)
							.addComponent(okButton)))
			);
			buttonPane.setLayout(gl_buttonPane);
		}
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(101)
					.addComponent(lblRadius)
					.addGap(33)
					.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(143, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, 414, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(50)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRadius)
						.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
					.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		contentPanel.setLayout(gl_contentPanel);
	}
	
	public void validate(String radius) {
		String supp = "^(([+-])?([1-9]{1})([0-9]+)?)$";
	    if(!radius.matches(supp)){  
	    	throw new NumberFormatException();
	    }
	}

	public boolean isConfirm() {
		return isConfirm;
	}

	public JTextField getTxtRadius() {
		return txtRadius;
	}
}
