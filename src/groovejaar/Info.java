package groovejaar;
/*******************************************************************************
 * Copyright (c) 2011 Ale46.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 ******************************************************************************/
import java.awt.BorderLayout;






import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JEditorPane;
import javax.swing.JLabel;

import java.awt.Font;
import java.io.IOException;
import java.net.URISyntaxException;


import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;

import java.awt.SystemColor;


public class Info extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	

	/**
	 * Create the dialog.
	 * @throws IOException 
	 */
	public Info() {
		setTitle("Info");
		//this.setLocationRelativeTo(null);
		this.setIconImage(new ImageUtil().getLogo());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 575, 409);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblImage = new JLabel(new ImageUtil().getImgIcon("icon.png"));
		
		JLabel lblVersion = new JLabel(("Version: ")+GrooveJaar.version);
		lblVersion.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		
		JEditorPane dtrpnrnthirdpartySoftwareInvolved = new JEditorPane();
		dtrpnrnthirdpartySoftwareInvolved.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		dtrpnrnthirdpartySoftwareInvolved.setOpaque(false);
		dtrpnrnthirdpartySoftwareInvolved.setBackground(SystemColor.control);
		dtrpnrnthirdpartySoftwareInvolved.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent arg0) {
				if (HyperlinkEvent.EventType.ACTIVATED.equals(arg0.getEventType())) {  
					try {
						
						GrooveJaar.openURL(arg0.getURL().toString());
					} catch (IOException e) {
						
						e.printStackTrace();
					} catch (URISyntaxException e) {
						
						e.printStackTrace();
					}
				}
			}
		});
		dtrpnrnthirdpartySoftwareInvolved.setEditable(false);
		dtrpnrnthirdpartySoftwareInvolved.setContentType("text/html");
		dtrpnrnthirdpartySoftwareInvolved.setText("<html>\r\n<font size=\"4\" face=\"Tahoma\" >\r\n<center>\r\nThird-party software involved (and obviously thanks to all):<br />\r\n<a href='http://code.google.com/p/jgroove/'><b>jgroove</b></a><br />\r\n<a href='http://code.google.com/p/google-gson/'><b>gson</b></a><br />\r\n<a href='http://www.jthink.net/jaudiotagger/'><b>Jaudiotagger</b></a><br />\r\n<a href='http://www.miglayout.com/'><b>MigLayout</b></a><br />\r\n<a href='http://sourceforge.net/projects/jacomp3player/'><b>jaco-mp3-player</b></a><br />\r\nExtra thanks goes to all the people that share his kwnolodge (a lot of code is taken from them).\r\n</center>\r\n</font>\r\n</html>");
		

		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(206)
					.addComponent(lblImage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(478))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(dtrpnrnthirdpartySoftwareInvolved, GroupLayout.PREFERRED_SIZE, 509, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(165, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(171)
					.addComponent(lblVersion, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(155, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblImage, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(lblVersion)
					.addGap(11)
					.addComponent(dtrpnrnthirdpartySoftwareInvolved, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
		);
		contentPanel.setLayout(gl_contentPanel);
	}
}
