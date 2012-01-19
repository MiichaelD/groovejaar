package groovejaar;
/*******************************************************************************
 * Copyright (c) 2011 Ale46.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 ******************************************************************************/

import javax.swing.JDialog;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;


public class Disclaimer extends JDialog {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	/**
	 * Create the dialog.
	 */
	public Disclaimer() {
		setTitle("Disclaimer");
		this.setIconImage(new ImageUtil().getLogo());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JLabel lblGroovejaarIs = new JLabel("<html>\r\n1. GrooveJaar is a software that have the intent to expand the GrooveShark functions.<br/>\r\n2. Some mp3 filescannot be legally downloaded for free. GrooveJaar does not allow establishing the legal consequences of downloading this or that file.<br/>\r\n3.  All musical tracks presented are only for trying and fact-finding listening. It is your obligation to remove all downloaded mp3 files from your computer after listening and purchase a legal copy from the copyright holder.<br/>\r\n4. Not doing so may be in conflict with the copyright protection laws in effect in your country of residence. GrooveJaar bears no responsibility for any legal consequences.<br/>\r\n5. All musical tracks are the legal property of their respective owners. All copyrights, \r\ndistribution rights and other rights belong to their respective owners.<br/>\r\n6. Using this software indicates your agreement to be bound by our Disclaimer.\r\n</html>");
		lblGroovejaarIs.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblGroovejaarIs, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblGroovejaarIs, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);

	}

}
