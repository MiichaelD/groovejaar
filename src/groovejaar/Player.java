package groovejaar;
import jaco.mp3.player.MP3Player;

import java.awt.Color;
import java.awt.Toolkit;



import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class Player{

	/**
	 * 
	 */

	private MP3Player player = new MP3Player();
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private JList<String> list = new JList<String>(model);
	public JFrame frame;
	private ImageIcon i = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
	
	public Player(){


		frame = new JFrame("GrooveJaar Player");
		frame.setIconImage(i.getImage());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnRemove = new JButton("Delete");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int n = list.getSelectedIndex();
				model.remove(n);
				
				player.removeQueue(n);
			}
		});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(player, GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnRemove, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(player, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(173)
							.addComponent(btnRemove)))
					.addContainerGap())
		);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					int index = list.locationToIndex(arg0.getPoint());
					player.setIndex(index);
					if (player.isPlaying()||player.isPaused())
						player.stop();
					player.play();
				}
			}
		});
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting())
					//	System.out.println("index:"+list.getSelectedIndex()+"="+model.get(list.getSelectedIndex()));
					player.setIndex(list.getSelectedIndex());

			}
		});
		scrollPane.setViewportView(list);


		list.setForeground(Color.BLACK);
		frame.getContentPane().setLayout(groupLayout);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(false);
	}

	public void addToQueue(String songID, String title){
		model.addElement(title);
		player.add(songID);
	}

	public void addAndPlay(String songID,String title){
		addToQueue (songID,title);
		if (player.isPaused()||player.isPlaying())
			player.stop();
		player.setIndex(model.size()-1);
		player.play();
		
	}
}
