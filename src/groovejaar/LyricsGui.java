package groovejaar;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;


import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;



public class LyricsGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	


	/**
	 * Create the frame.
	 */
	public LyricsGui(String text) {
		setTitle("Lyrics");
		this.setIconImage(new ImageUtil().getLogo());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 560, 343);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTextArea txtrTexthtml = new JTextArea();
		txtrTexthtml.setEditable(false);
		
		txtrTexthtml.setLineWrap(true);
		txtrTexthtml.setRows(8);
		JScrollPane scrollingResult = new JScrollPane(txtrTexthtml);
		
		JLabel lblLyric = new JLabel("Lyric (maybe this can be a wrong lyric, take care!):");
		lblLyric.setFont(new Font("Tahoma", Font.BOLD, 11));
		
			txtrTexthtml.setText(" "+text.replaceAll("<br />,", "\n"));
			GroupLayout gl_contentPane = new GroupLayout(contentPane);
			gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(10)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
							.addComponent(scrollingResult, Alignment.LEADING)
							.addComponent(lblLyric, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
						.addGap(10))
			);
			gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(11)
						.addComponent(lblLyric)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollingResult, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))
			);
			contentPane.setLayout(gl_contentPane);
		
	}
}
