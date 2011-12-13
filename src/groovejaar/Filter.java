package groovejaar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowFilter;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Filter extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFilterAlbum;
	private JTextField txtFilterYear;
	private JTextField txtFilterTitle;
	private JTextField txtFilterArtist;
	private ImageIcon i = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));

	/**
	 * Create the frame.
	 */
	public Filter() {
		setTitle("Filter");
		setIconImage(i.getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		
		txtFilterAlbum = new JTextField();
		txtFilterAlbum.setColumns(10);
		
		JLabel label = new JLabel("Year");
		
		txtFilterYear = new JTextField();
		txtFilterYear.setColumns(10);
		
		JLabel label_1 = new JLabel("Title");
		
		JButton button = new JButton("Apply");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RowFilter<TableModel, Object> filterTitle = null;
				RowFilter<TableModel, Object> filterArtist = null;
				RowFilter<TableModel, Object> filterAlbum = null;
				RowFilter<TableModel, Object> filterYear = null;
				List<RowFilter<TableModel,Object>> filters = new ArrayList<RowFilter<TableModel,Object>>();
				RowFilter<TableModel, Object> compoundRowFilter = null;

				if (GrooveJaar.filterList.containsKey(GrooveJaar.getCurrentTab())){
					GrooveJaar.filterList.get(GrooveJaar.getCurrentTab()).put("filterTitle", txtFilterTitle.getText());
					GrooveJaar.filterList.get(GrooveJaar.getCurrentTab()).put("filterArtist", txtFilterArtist.getText());
					GrooveJaar.filterList.get(GrooveJaar.getCurrentTab()).put("filterAlbum", txtFilterAlbum.getText());
					GrooveJaar.filterList.get(GrooveJaar.getCurrentTab()).put("filterYear", txtFilterYear.getText());

				}else{
					HashMap<String,String> filter = new HashMap<String,String>();
					filter.put("filterTitle", txtFilterTitle.getText());
					filter.put("filterArtist", txtFilterArtist.getText());
					filter.put("filterAlbum", txtFilterAlbum.getText());
					filter.put("filterYear", txtFilterYear.getText());
					GrooveJaar.filterList.put(GrooveJaar.getCurrentTab(), filter);
				}	

				try {
					filterTitle = RowFilter.regexFilter("(?i)"+txtFilterTitle.getText(), 0);
					filterArtist = RowFilter.regexFilter("(?i)"+txtFilterArtist.getText(), 1);
					filterAlbum = RowFilter.regexFilter("(?i)"+txtFilterAlbum.getText(), 2);
					filterYear = RowFilter.regexFilter("(?i)"+txtFilterYear.getText(), 3);

					filters.add(filterTitle);
					filters.add(filterArtist);
					filters.add(filterAlbum);
					filters.add(filterYear);
					compoundRowFilter = RowFilter.andFilter(filters); 
				} catch (java.util.regex.PatternSyntaxException e1) {
					return;
				}
				GrooveJaar.sorters.get(GrooveJaar.getCurrentTab()).setRowFilter(compoundRowFilter);
			}
		});
		
		JButton button_1 = new JButton("Reset");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtFilterTitle.setText("");
				txtFilterAlbum.setText("");
				txtFilterYear.setText("");
				txtFilterArtist.setText("");
				GrooveJaar.clearFilter();
			}
		});
		
		txtFilterTitle = new JTextField();
		txtFilterTitle.setColumns(10);
		
		JLabel label_2 = new JLabel("Artist");
		
		txtFilterArtist = new JTextField();
		txtFilterArtist.setColumns(10);
		
		JLabel label_3 = new JLabel("Album");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(2)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addGap(98))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(2)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
							.addGap(82))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(txtFilterAlbum, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
								.addComponent(txtFilterArtist, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
								.addComponent(txtFilterTitle, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(txtFilterYear, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(58, Short.MAX_VALUE)
					.addComponent(button)
					.addGap(27)
					.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addGap(31))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(label_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtFilterTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtFilterArtist, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtFilterAlbum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtFilterYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(button_1)
						.addComponent(button))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(95)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
					.addGap(84))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
					.addGap(0))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
