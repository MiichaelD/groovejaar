package groovejaar;
/*******************************************************************************
 * Copyright (c) 2011 Ale46.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 ******************************************************************************/
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.HashMap;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;



public class OptionGui extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblDownloadDirectory;
	private JTextField txtDownload;
	private Option opt = new Option();
	private ImageIcon i = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
	private JTextField txtTemplate;
	private JComboBox<String> comboBox;
	private JCheckBox chkBitrateSize;
	
	
	/**
	 * Create the dialog.
	 * @throws IOException 
	 */
	public OptionGui() throws IOException {
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setIconImage(i.getImage());
		//this.setLocationRelativeTo(null);
		setTitle(("Settings"));
		setBounds(100, 100, 506, 391);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			lblDownloadDirectory = new JLabel(("Download Directory"));
		}

		txtDownload = new JTextField();
		txtDownload.setColumns(10);
		
		txtDownload.setText(opt.getDownloadPath());
		JButton button = new JButton("...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showDialog(null, ("Select download path")) == JFileChooser.APPROVE_OPTION) { 
					txtDownload.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		final JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Byte((byte) opt.getMaxDownloads()), new Byte((byte) 1), new Byte((byte) 5), new Byte((byte) 1)));
		
		JLabel lblSimoultaneousDownloads = new JLabel(("Simultaneous Downloads (high value maybe can ban you from GrooveShark)"));
		
		final JCheckBox chckbxAutoCheckUpdat = new JCheckBox(("Auto check update at startup"), opt.getUpdate());
		
		txtTemplate = new JTextField(opt.getFileTemplate());
		txtTemplate.setColumns(10);
		
		JLabel lblFileNameTemplate = new JLabel("File name template (Reserved words: %TrackNum %Title% %Artist% %Album%)");
		
		JButton btnTestTemplate = new JButton("Test Template");
		btnTestTemplate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HashMap<String,String> mp3 = new HashMap<String,String>();
				mp3.put("TrackNum", "02");
				mp3.put("SongName", "Symphony No. 5");
				mp3.put("ArtistName", "Beethoven");
				mp3.put("AlbumName", "Classical Best Of");
				mp3.put("Year", "1800");
				GrooveJaar.showMessage("You're downloads will saved as: "+GrooveJaar.makeTitle(mp3,txtTemplate.getText()));
			}
		});
		
		JLabel lblIfFileExits = new JLabel("If file exits");
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Ask Action", "Overwrite", "Skip"}));
		
		chkBitrateSize = new JCheckBox("Auto get bitrate and size",opt.autoBitrateSize());
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblFileNameTemplate, GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(txtDownload, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(button, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblDownloadDirectory))
							.addGap(8))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(441, Short.MAX_VALUE))
						.addComponent(lblSimoultaneousDownloads)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(txtTemplate, 361, 361, 361)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnTestTemplate)
							.addContainerGap())
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblIfFileExits, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 84, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap(319, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(chkBitrateSize)
							.addContainerGap(303, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(chckbxAutoCheckUpdat, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDownloadDirectory)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtDownload, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblSimoultaneousDownloads)
					.addGap(7)
					.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblFileNameTemplate)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtTemplate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnTestTemplate))
					.addGap(18)
					.addComponent(lblIfFileExits)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
					.addComponent(chkBitrateSize)
					.addGap(18)
					.addComponent(chckbxAutoCheckUpdat))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(("Save"));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						try {
							opt.save("downloadPath",txtDownload.getText());
							opt.save("maxDownloads",String.valueOf(spinner.getValue()));
							opt.save("autoUpdate", chckbxAutoCheckUpdat.isSelected() ? "true" : "false");
							opt.save("template", txtTemplate.getText());
							opt.save("ifExist", (String)comboBox.getSelectedItem());
							opt.save("autoBitrateSize", chkBitrateSize.isSelected() ? "true" : "false");
							GrooveJaar.initOptions();
							OptionGui.this.dispose();
							
						} catch (FileNotFoundException e) {
							
							e.printStackTrace();
						} catch (IOException e) {
							
							e.printStackTrace();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton(("Cancel"));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						OptionGui.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
