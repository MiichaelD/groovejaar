package groovejaar;
/*******************************************************************************
 * Copyright (c) 2011 Ale46.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 ******************************************************************************/



import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;




public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JPasswordField passwordField;
	
	private JCheckBox chckbxLogin;
	

	/**
	 * Create the dialog.
	 */
	public Login() {
		
		//setTitle("Login");
		this.setIconImage(new ImageUtil().getLogo());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 278, 218);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			textField = new JTextField();
			textField.setColumns(10);
		}
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		passwordField = new JPasswordField();
		
		JLabel lblUsername = new JLabel("Username");
		
		JLabel lblPassword = new JLabel("Password");
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("252px"),},
			new RowSpec[] {
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("14px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("14px"),
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("33px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		contentPanel.add(textField, "1, 4, fill, top");
		contentPanel.add(lblUsername, "1, 2, center, top");
		contentPanel.add(lblPassword, "1, 6, center, top");
		contentPanel.add(passwordField, "1, 8, fill, top");
		getContentPane().add(contentPanel);
		
		chckbxLogin = new JCheckBox("Auto Login");
		contentPanel.add(chckbxLogin, "1, 10");
		
	}
	
	public String getUsername(){
		return textField.getText();
	}
	
	public String getPassword(){
		return new String(passwordField.getPassword());
	}
	
	public JPanel getPanel(){
		return  contentPanel;
	}
	
	public JFrame getFrame(){
		return this;
	}
	
	public boolean stayLogged(){
		return chckbxLogin.isSelected();
	}
}
