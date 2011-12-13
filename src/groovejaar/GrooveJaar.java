package groovejaar;
/*******************************************************************************
 * Copyright (c) 2010 Ale46.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Point;




import java.awt.EventQueue;
import java.awt.Toolkit;



import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;



import javax.swing.JPopupMenu;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import javax.swing.SwingUtilities;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


import javax.swing.table.TableColumnModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.HashMap;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.border.TitledBorder;
import java.awt.Font;

import jgroove.JGroovex;

import jgroove.jsonx.JsonGetSong.Result;

import jgroove.jsonx.JsonUser;


import net.miginfocom.swing.MigLayout;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;

import org.jaudiotagger.tag.TagException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JSeparator;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

public class GrooveJaar {
	//Declarations
	private ClosableTabbedPane jTabbedPane;
	private JFrame frmGroovejaar;
	private ExtendedTextField txtSearch;
	private String[] columnNames = {
			("Title"),
			("Artist"),
			("Album"),
			("Year"),
			("Track N."),
			("Duration"),
			("Action"),
			("Number")

	};
	public static HashMap<Integer,HashMap<String,String>> filterList = new HashMap<Integer,HashMap<String,String>>();

	public static JsonUser user = null;
	public static final String version = "1.0.21 Beta";
	private String[] column = {("Filename"), ("Status"),("Percentage") };
	private static Option opt;
	public static String downloadPath;
	private static byte maxDownloads;
	private static String skin;
	public static Timer timer;
	private final String projectPage = "";//"http://code.google.com/p/groovejaar/";
	private static int currentTab;
	private int editingRow;
	private int[] editingRows;
	private Player player;
	private HashMap<String, String>[] playlists;
	private static ExecutorService exec;
	public static HashMap<Integer,TableRowSorter<TableModel>> sorters = new HashMap<Integer,TableRowSorter<TableModel>>() ;
	private DefaultTableModel modelDl = new DefaultTableModel(null,column){

		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int col) {
			return false;
		}
	};
	private JTable table_1;
	private JLabel lblArtist,lblAlbum,lblTitle,lblBitrate,lblPreview,lblSize;
	public static HashMap<Integer,HashMap<String, String>[]> results = new HashMap<Integer,HashMap<String, String>[]>();
	private static String ifExist;
	private JMenu mnLogged;
	private JMenu mnPlaylist,mnGroove;
	private JMenuItem mntmLogin,mnUserLib,mnFav;
	//End Declarations

	//Functions
	private DefaultTableModel makeModel() {
		DefaultTableModel model = new DefaultTableModel(null ,columnNames){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
				if (col!=6)
					return false;
				return true;
			}
		};
		return model;
	}

	protected JComponent makeTextPanel(final String text, final String type, final int newTab) throws Exception {
		final JPanel panel = new JPanel(new BorderLayout());
		new Thread() {
			public void run() {
				final DefaultTableModel model = makeModel();
				String searchKey =text;
				final JTable table = new JTable(model);
				TableRowSorter<TableModel> sorter  = new TableRowSorter<TableModel>(table.getModel());
				table.setRowSorter(sorter);


				final JMenuItem mnuFilter = new JMenuItem("Apply Filter");
				final JMenuItem mnuDownloadAll = new JMenuItem("Download all items");
				final JMenuItem mnuDownloaSelected = new JMenuItem("Download selected items");
				final JMenu mnuShowOther = new JMenu("Show Extra Tab");
				final JMenuItem mnuShowLenght = new JMenuItem("Track Duration");
				final JMenuItem mnuShowTrack = new JMenuItem("Track Number");

				mnuShowOther.add(mnuShowLenght);
				mnuShowOther.add(mnuShowTrack);
				final JPopupMenu popupMenu = new JPopupMenu();
				mnuDownloadAll.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/alldl.png"))));
				mnuDownloaSelected.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/selectdl.png"))));
				mnuShowOther.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/showtab.png"))));
				mnuFilter.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/filter.png"))));
				mnuShowLenght.setSelected(false);
				mnuShowTrack.setSelected(false);

				mnuFilter.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Filter f = new Filter();

						f.setVisible(true);

					}

				}

						);

				mnuDownloadAll.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						for (int i = 0;i<table.getRowCount();i++){
							addToDownload(i);
						}
					}
				});

				mnuDownloaSelected.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int[] rows = table.getSelectedRows();
						for (int i = 0;i<rows.length;i++){

							addToDownload(Integer.parseInt((String) table.getValueAt(rows[i],7)));
						}
					}
				});

				mnuShowLenght.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						if (!mnuShowLenght.isSelected()){
							table.getColumnModel().getColumn(5).setMinWidth(0);
							table.getColumnModel().getColumn(5).setMaxWidth(100);
							table.getColumnModel().getColumn(5).setWidth(80);
							table.getColumnModel().getColumn(5).setPreferredWidth(60);
							mnuShowLenght.setSelected(true);
							mnuShowLenght.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/check.png"))));
						}else{
							table.getColumnModel().getColumn(5).setWidth(0);
							table.getColumnModel().getColumn(5).setPreferredWidth(60);
							table.getColumnModel().getColumn(5).setMaxWidth(0);
							mnuShowLenght.setIcon(null);
							mnuShowLenght.setSelected(false);
						}

					}
				});

				mnuShowTrack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						if (!mnuShowTrack.isSelected()){
							table.getColumnModel().getColumn(4).setMinWidth(0);
							table.getColumnModel().getColumn(4).setMaxWidth(60);
							table.getColumnModel().getColumn(4).setWidth(60);
							table.getColumnModel().getColumn(4).setPreferredWidth(60);
							mnuShowTrack.setSelected(true);
							mnuShowTrack.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/check.png"))));
						}else{							
							table.getColumnModel().getColumn(4).setWidth(0);
							table.getColumnModel().getColumn(4).setPreferredWidth(60);
							table.getColumnModel().getColumn(4).setMaxWidth(0);
							mnuShowTrack.setSelected(false);
							mnuShowTrack.setIcon(null);

						}


					}
				});
				popupMenu.add(mnuFilter);
				popupMenu.add(mnuDownloaSelected);
				popupMenu.add(mnuDownloadAll);
				popupMenu.add(new JSeparator());
				popupMenu.add(mnuShowOther);

				table.addMouseListener( new MouseAdapter(){


					public void mousePressed(MouseEvent e) {
						showPopup(e);
					}
					public void mouseReleased(MouseEvent e) {
						showPopup(e);
					}

					private void showPopup(MouseEvent e) {

						if (e.isPopupTrigger()) {
							popupMenu.show(e.getComponent(), e.getX(), e.getY());
						}
					}
				});

				table.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent e) {

						if (e.getKeyCode() == KeyEvent.VK_UP ){

							table.setEditingColumn(table.getSelectedColumn()-1);
							table.getSelectionModel().setValueIsAdjusting(true);

						}


						else if (e.getKeyCode() == KeyEvent.VK_DOWN ){

							table.setEditingRow(table.getSelectedColumn()+1);
							table.getSelectionModel().setValueIsAdjusting(true);

						}
					}
				});
				final TableColumnModel columnModel = table.getColumnModel();
				((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);


				final JButtonTableCellRenderer buttonRenderer = new JButtonTableCellRenderer();
				JButtonTableCellEditor buttonEditor = new JButtonTableCellEditor();

				// Registers an ActionListener on the editor.
				buttonEditor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						// Note: the following row and column are in the "view" and
						// not necessarily are the same for the model!
						editingRow = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(),7));
						int editingColumn = table.getEditingColumn();


						if (editingColumn == 6){

							addToDownload(editingRow);

						}

					}
				});


				columnModel.getColumn(6).setCellRenderer(buttonRenderer);
				columnModel.getColumn(6).setCellEditor(buttonEditor);
				table.setShowGrid(false);

				table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {

						if (e.getValueIsAdjusting()) {

							if (table.getSelectedColumn()!=6){
								if (table.getSelectedRowCount()>1){

									int[] sel = table.getSelectedRows();
									editingRows = new int[sel.length];
									for (int i =0;i<editingRows.length;i++)
										editingRows[i] =  Integer.parseInt((String) table.getValueAt(sel[i],7));
								}else{
									editingRows = null;
									editingRow = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(),7));
								}

								//System.out.println(getCurrentTab());
								//System.out.println(editingRow);
								try {
									if (opt.autoBitrateSize())
										getBitrateAndSize(results.get(getCurrentTab())[editingRow].get("SongID"));
									else{
										lblBitrate.setText("<html>Bitrate: <font color='red'>Auto get bitrate Disabled (Go to option)</font></html>");
										lblSize.setText("<html>Size: <font color='red'>Auto get size Disabled (Go to option)</font></html>");
									}

								} catch (IOException e1) {

									e1.printStackTrace();
								} catch (TagException e1) {

									e1.printStackTrace();
								}

								String title = results.get(currentTab)[editingRow].get("SongName") != null ? results.get(currentTab)[editingRow].get("SongName") : results.get(currentTab)[editingRow].get("Name");
								lblTitle.setText( ("<html>Title: <font color='blue'>")+title+"</font></html>");
								lblAlbum.setText(("<html>Album: <font color='blue'>")+results.get(currentTab)[editingRow].get("AlbumName")+"</font></html>");
								lblArtist.setText( ("<html>Artist: <font color='blue'>")+results.get(currentTab)[editingRow].get("ArtistName")+"</font></html>");
								//if(results.get(currentTab)[editingRow].get("Year")!=null) lblYear.setText( ("<html>Year: <font color='blue'>")+results.get(currentTab)[editingRow].get("Year")+"</font></html>");
								//else lblYear.setText( ("<html>Year: <font color='red'>N\\A")+"</font></html>");
								/*if(!results.get(currentTab)[editingRow].get("TrackNum").equals("0")) lblTrackN.setText( ("<html>Track N.: <font color='blue'>")+results.get(currentTab)[editingRow].get("TrackNum")+"</font></html>");
								else lblTrackN.setText( ("<html>Track N.: <font color='red'>N\\A")+"</font></html>");
								 */
								if(results.get(currentTab)[editingRow].get("CoverArtFilename")!=null){
									ImageIcon i = null;
									if (!results.get(currentTab)[editingRow].get("CoverArtFilename").isEmpty()){
										//lblCoverArt.setText( ("<html>CoverArt: <font color='green'>Present")+"</font></html>");

										try {
											i = new ImageIcon(new URL("http://beta.grooveshark.com/static/amazonart/s"+results.get(currentTab)[editingRow].get("CoverArtFilename")));
										} catch (MalformedURLException e1) {

											e1.printStackTrace();
										}
										lblPreview.setIcon(i);
										lblPreview.setEnabled(true);

									}else {
										//	lblCoverArt.setText( ("<html>CoverArt: <font color='red'>Not Present")+"</font></html>");
										lblPreview.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("nocover.jpg"))));
										lblPreview.setEnabled(false);
									}
								}else{
									//lblCoverArt.setText( ("<html>CoverArt: <font color='red'>Not Present")+"</font></html>");
									lblPreview.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("nocover.jpg"))));
									lblPreview.setEnabled(false);
								}
								//String lenght = results.get(currentTab)[editingRow].get("EstimateDuration");
								/*
								if(lenght!=null) {
									if (!lenght.equals("0")){
										//System.out.println(results.get(currentTab)[editingRow].get("EstimateDuration"));

										lblDuration.setText( ("<html>Duration: <font color='blue'>")+parseDuration(results.get(currentTab)[editingRow].get("EstimateDuration"))+"</font></html>");
									}else 
										lblDuration.setText( ("<html>Duration: <font color='red'>N\\A")+"</font></html>"); 
								}else{
									lblDuration.setText( ("<html>Duration: <font color='red'>N\\A")+"</font></html>"); 
								}*/
							}
						}
					}

				});

				table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
				table.getColumnModel().getColumn(3).setPreferredWidth(50);
				table.getColumnModel().getColumn(3).setMaxWidth(60);
				table.getColumnModel().getColumn(6).setPreferredWidth(100);
				table.getColumnModel().getColumn(6).setMinWidth(100);
				table.getColumnModel().getColumn(6).setMaxWidth(100);
				table.getColumnModel().getColumn(4).setPreferredWidth(0);
				table.getColumnModel().getColumn(4).setMinWidth(0);
				table.getColumnModel().getColumn(4).setMaxWidth(0);
				table.getColumnModel().getColumn(5).setPreferredWidth(0);
				table.getColumnModel().getColumn(5).setMinWidth(0);
				table.getColumnModel().getColumn(5).setMaxWidth(0);
				table.getColumnModel().getColumn(7).setPreferredWidth(0);
				table.getColumnModel().getColumn(7).setMinWidth(0);
				table.getColumnModel().getColumn(7).setMaxWidth(0);
				panel.add(new JScrollPane(table), BorderLayout.CENTER);
				HashMap<String, String>[] songs = null;
				ExecutorService executor = Executors.newSingleThreadExecutor();
				Future<HashMap<String, String>[]> task = null;
				//System.out.println("DEBUGGGG:"+type);
				//System.out.println("DEBUGGGG:"+searchKey);
				if (type.equals("top"))
					task = executor.submit(new GrooveThread("top", searchKey.contains("Monthly") ? "monthly":"daily"));
				else if (type.equals("playlist")){
					task = executor.submit(new GrooveThread("playlist",searchKey.split(":")[1].trim()));
					searchKey = searchKey.split(":")[0];
				}
				else if (type.equals("album")){
					task = executor.submit(new GrooveThread("album",searchKey.split(":")[1].trim()));
					searchKey = searchKey.split(":")[0];
				}
				else if (type.equals("userSong")){
					task = executor.submit(new GrooveThread("userSong",user.result.userID));
				}
				else if (type.equals("fav")){
					task = executor.submit(new GrooveThread("fav",user.result.userID));
				}
				else if (type.equals("songs"))
					task = executor.submit(new GrooveThread("search",searchKey));
				try {
					songs = task.get();
				} catch (InterruptedException e1) {

					e1.printStackTrace();
				} catch (ExecutionException e1) {
					jTabbedPane.setTitleAt(newTab, "Error - "+searchKey+"     ");
					e1.printStackTrace();
					return;
				}

				results.put(searchKey.hashCode(), songs);
				executor.shutdown();
				for (int i = 0;i<songs.length;i++){

					String[] newRow = { songs[i].get("SongName") != null ? songs[i].get("SongName") : songs[i].get("Name") ,songs[i].get("ArtistName"),songs[i].get("AlbumName"),songs[i].get("Year"),songs[i].get("TrackNum"),parseDuration(songs[i].get("EstimateDuration")),"",Integer.toString(i) };
					model.addRow(newRow);

				}
				jTabbedPane.setTitleAt(newTab,searchKey+" ("+songs.length+")     ");
				sorters.put((searchKey).hashCode(), sorter);

				if (jTabbedPane.getTabCount()==1){
					setCurrentTab((searchKey).hashCode());
				}


			}

		}.start();

		return panel;
	}

	private void addToDownload(int rowNumber){

		//System.out.println("TAB"+currentTab);
		String downloadName = makeTitle(results.get(currentTab)[rowNumber],opt.getFileTemplate());
		String id = results.get(currentTab)[rowNumber].get("SongID");
		//check if file exits
		String dl =  checkExists(downloadName);
		if (dl != null){
			downloadName = dl;


			String[] row = {downloadName,("Waiting"),"0%"} ;
			modelDl.addRow(row);
			try {
				download(id, downloadName,modelDl.getRowCount());
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {

				e1.printStackTrace();
			}
		}else{
			String[] row = {downloadName,("Skipped"),""} ;
			modelDl.addRow(row);
		}
	}

	private void setCurrentTab(int value){

		currentTab = value;
	}

	static int getCurrentTab(){

		return currentTab;
	}

	private String parseDuration(String time){
		if (time != null){
			double number = Double.parseDouble(time)/60;
			return String.format("%.2f", number)+ " mins";
		}
		return "NA";
	}

	public static String makeTitle(HashMap<String,String> mp3, String template){

		String x = template;

		x = x.replaceFirst("%Track%",  mp3.get("TrackNum")!=null ? mp3.get("TrackNum") : "NA");
		x = x.replaceFirst("%Artist%", mp3.get("ArtistName")!=null||!mp3.get("ArtistName").isEmpty() ? mp3.get("ArtistName"):"");
		x = x.replaceFirst("%Title%", mp3.get("SongName")!=null?mp3.get("SongName"):mp3.get("Name") );
		x = x.replaceFirst("%Album%",mp3.get("AlbumName")!=null?mp3.get("AlbumName"):"");
		x = x.replaceFirst("%Year%", mp3.get("Year")!=null?mp3.get("Year"):"");

		return x;
	}

	private void search(final String query, final String type) {
		Toolkit.getDefaultToolkit().beep(); 
		new Thread((new Runnable() {
			public void run() {
				try {
					JComponent panel2 = null;
					String searchKey = query;

					panel2 = makeTextPanel(searchKey,type,jTabbedPane.getTabCount());
					if (type.equals("playlist")) searchKey = searchKey.substring(0,query.indexOf(":"));
					if (type.equals("album")) searchKey = searchKey.substring(0,query.indexOf(":"));

					jTabbedPane.addTab(("Searching - ")+searchKey, panel2);
					txtSearch.setText("");

				} catch (IOException e) {
					e.printStackTrace();
				} catch (CannotReadException e) {
					e.printStackTrace();
				} catch (TagException e) {
					e.printStackTrace();
				} catch (ReadOnlyFileException e) {
					e.printStackTrace();
				} catch (InvalidAudioFrameException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		})).start();	

	}

	private void getBitrateAndSize(final String songID) throws IOException, TagException{

		new Thread((new Runnable() {

			@Override
			public void run() {
				GrooveJaar.this.lblBitrate.setText(("Bitrate: Wait.."));
				GrooveJaar.this.lblSize.setText(("Size: Wait.."));
				Result songURL = null;
				try {
					songURL = JGroovex.getSongURL(songID).result;
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				//System.out.println("Serverid:"+songURL.streamServerID);
				//System.out.println("Serverid:"+songURL.streamKey);
				//System.out.println("Serverid:"+songURL.ip);

				Object[] params = null;

				InputStream is = null;
				int downloaded=0;
				try {
					params =  JGroovex.getSongStream(songURL.ip, songURL.streamKey);
					JGroovex.markSongAsDownloaded(songURL.streamServerID, songURL.streamKey, songID);
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				is = (InputStream)params[1];
				//int lenght = (Integer) params[0];

				File tempMp3=new File(System.getProperty ("user.dir")+File.separator+"temp"+File.separator+songID+".mp3");
				OutputStream out = null;
				try {
					out = new FileOutputStream(tempMp3);
				} catch (FileNotFoundException e) {
					GrooveJaar.this.lblBitrate.setText("<html>Bitrate: <font color='red'>Error</font></html>");
					GrooveJaar.this.lblSize.setText("<html>Size: <font color='red'>Error</font></html>");
					e.printStackTrace();
				}
				byte buf[]=new byte[1024];
				double size = Double.parseDouble(params[0].toString())/1048576;
				GrooveJaar.this.lblSize.setText(("<html>Size: <font color='blue'>")+String.format("%.2f", size)+ " MB</font></html>");
				try {

					while(downloaded<70000){
						downloaded+=is.read(buf);
						out.write(buf,0,is.read(buf));


					}
				} catch (IOException e) {
					GrooveJaar.this.lblSize.setText("<html>Size: <font color='red'>Error</font></html>");
					e.printStackTrace();
				}
				try {
					out.close();
				} catch (IOException e) {
					GrooveJaar.this.lblBitrate.setText("<html>Bitrate: <font color='red'>Error</font></html>");
					e.printStackTrace();
				}
				try {
					is.close();

				} catch (IOException e) {
					GrooveJaar.this.lblBitrate.setText("<html>Bitrate: <font color='red'>Error</font></html>");
					e.printStackTrace();
				}
				//System.out.println("\nFile Downloaded...................................");

				try {
					JGroovex.markSongComplete(songURL.streamServerID, songURL.streamKey, songID);
				} catch (IOException e) {

					e.printStackTrace();
				}

				AudioFile audioFile = null;
				AudioHeader audioHeader = null;
				try {
					audioFile = AudioFileIO.read(tempMp3);
					audioHeader = audioFile.getAudioHeader();

				} catch (CannotReadException e) {

					GrooveJaar.this.lblBitrate.setText("<html>Bitrate: <font color='red'>Error</font></html>");
					tempMp3.delete();
				} catch (IOException e) {

					GrooveJaar.this.lblBitrate.setText("<html>Bitrate: <font color='red'>Error</font></html>");
					tempMp3.delete();
				} catch (TagException e) {

					GrooveJaar.this.lblBitrate.setText("<html>Bitrate: <font color='red'>Error</font></html>");
				} catch (ReadOnlyFileException e) {
					tempMp3.delete();
					GrooveJaar.this.lblBitrate.setText("<html>Bitrate: <font color='red'>Error</font></html>");
				} catch (InvalidAudioFrameException e) {
					tempMp3.delete();
					GrooveJaar.this.lblBitrate.setText("<html>Bitrate: <font color='red'>Error</font></html>");
				}

				GrooveJaar.this.lblBitrate.setText(("<html>Bitrate: <font color='blue'>")+ audioHeader.getBitRate()+" kbps</font></html>");

				tempMp3.delete(); // temp file no more required
			}


		})).start();
	}




	private   void  download(final String id, final String title, final int lastRow) throws IOException, InterruptedException{

		Runnable dl = new Runnable() {

			@Override
			public void run() {

				Result songURL = null;
				try {
					songURL = JGroovex.getSongURL(id).result;
				} catch (IOException e1) {
					modelDl.setValueAt(("Error"), lastRow-1, 1);
					e1.printStackTrace();
				}
				//System.out.println("Serverid:"+songURL.streamServerID);
				//System.out.println("Serverid:"+songURL.streamKey);
				//System.out.println("Serverid:"+songURL.ip);
				modelDl.setValueAt(("Downloading"), lastRow-1, 1);
				Object[] params = null;

				InputStream is = null;
				int downloaded=0;
				try {
					params =  JGroovex.getSongStream(songURL.ip, songURL.streamKey);
					JGroovex.markSongAsDownloaded(songURL.streamServerID, songURL.streamKey, id);
					initTimer(songURL.streamServerID, songURL.streamKey, id);
				} catch (IOException e1) {
					modelDl.setValueAt(("Error"), lastRow-1, 1);
					e1.printStackTrace();
				}
				is = (InputStream)params[1];
				int lenght = (Integer) params[0];

				File f=new File(downloadPath+File.separator+clearTitle(title)+".mp3");
				OutputStream out = null;
				try {
					out = new FileOutputStream(f);
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				}
				byte buf[]=new byte[3000];
				int len;
				try {

					while((len=is.read(buf))>0){
						downloaded+=len;
						out.write(buf,0,len);
						modelDl.setValueAt(downloaded*100/lenght+"%", lastRow-1, 2);
					}
				} catch (IOException e) {
					modelDl.setValueAt(("Error"), lastRow-1, 1);
					e.printStackTrace();
				}
				try {
					out.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
				try {
					is.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
				//System.out.println("\nFile Downloaded...................................");
				modelDl.setValueAt(("Finished"), lastRow-1, 1);
				try {
					JGroovex.markSongComplete(songURL.streamServerID, songURL.streamKey, id);
					timer.cancel();
				} catch (IOException e) {

					e.printStackTrace();
				}

			}
		};
		exec.execute(dl);


	}

	private void downloadCover (String coverName){
		File f=new File(downloadPath+File.separator+coverName);
		InputStream is = null;

		try {
			is = new URL("http://beta.grooveshark.com/static/amazonart/"+coverName).openStream();
		} catch (MalformedURLException e1) {
			showMessage(("Cover Download Failed"));
			e1.printStackTrace();
		} catch (IOException e1) {
			showMessage(("Cover Download Failed"));
			e1.printStackTrace();
		}
		OutputStream out = null;
		try {
			out = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			showMessage(("Cover Download Failed"));
			e.printStackTrace();
		}
		byte buf[]=new byte[1024];
		int len;
		try {

			while((len=is.read(buf))>0){

				out.write(buf,0,len);

			}
		} catch (IOException e) {
			showMessage(("Cover Download Failed"));
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			showMessage(("Cover Download Failed"));
			e.printStackTrace();
		}
		try {
			is.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		showMessage(("Cover Downloaded"));
	}

	public static void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	static void initOptions() throws FileNotFoundException, IOException{
		opt = new Option();

		//setSkin(opt.getSkin());
		skin = opt.getSkin();
		downloadPath = opt.getDownloadPath();
		if (!new File(downloadPath).exists())
			new File(downloadPath).mkdir();
		maxDownloads= opt.getMaxDownloads();
		exec = Executors.newFixedThreadPool(maxDownloads);
		ifExist = opt.getActionIfExist();
		
	}

	private void checkTemp(){
		File check = new File(downloadPath+File.separator+"temp");
		if (!check.exists())
			check.mkdir();
		else{ //something wrong with temp mp3 files
			check.deleteOnExit();
		}

	}

	public static void initTimer(final int streamServerID, final String streamKey, final String id ){
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					JGroovex.markStreamKeyOver30Seconds(0, streamServerID, streamKey, id);

				} catch (IOException e) {

					e.printStackTrace();
				}
				timer.cancel();
			}
		}, 30 * 1000);
	}

	private String clearTitle(String title){
		String[] badChars = { "\\","/","\"","<",">","|","*",":","?"};
		for (int k = 0;k<badChars.length;k++)
			if (title.contains(badChars[k])) {
				title =  title.replace(badChars[k], "");
			}
		return title;
	}

	private String checkExists(String downloadName) {
		File fx = new File(opt.getDownloadPath()+File.separator+downloadName+".mp3");
		
		if (fx.exists()){
			if (ifExist.equalsIgnoreCase("Overwrite")){
				fx.delete();
				return downloadName;
			}else if (ifExist.equalsIgnoreCase("Skip")){
				return null;
			}else{
			
					
				Object[] options = {"Don't download", "Overwrite", "Rename"};
				int n = JOptionPane.showOptionDialog(frmGroovejaar,
						"The file you want do download already exists, What should I do??",
						downloadName,
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,     //do not use a custom Icon
						options,  //the titles of buttons
						options[0]); //default button title
	
				if (n==0||n==-1)
					return null;
				else if (n==1){
					fx.delete();
					return downloadName;
				}
				else if (n==2){
					downloadName = JOptionPane.showInputDialog(null,
							"Rename File",
							"Enter new name for the file",
							JOptionPane.QUESTION_MESSAGE);
					return downloadName;
				}

			}
		}else
			return downloadName;
		return null;
	}

	public static void clearFilter(){
		if (results.containsKey(currentTab) && sorters.get(getCurrentTab()) != null )
			sorters.get(getCurrentTab()).setRowFilter(null);
	}

	private String getIdFromURL(String url){
		String id = url.substring(url.lastIndexOf("/")+1,url.length());
		return id;
	}

	static void openURL (String url) throws IOException, URISyntaxException{
		Desktop desktop = Desktop.getDesktop();
		if (desktop.isSupported(Desktop.Action.BROWSE)){
			desktop.browse(new URI(url) );
		}else
			showMessage(("Error"));
	}

	private void checkUpdate(boolean showNoUpdate) throws InterruptedException, ExecutionException{

		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Boolean> task = null;
		task = executor.submit(new Updater());
		if (task.get())
			showMessage(("New version available, check the project page!"));
		else
			if (showNoUpdate)
				showMessage(("No new version available"));
		executor.shutdown();


	}

	private void setSkin(String lookAndFeel) throws FileNotFoundException, IOException{

		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (InstantiationException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {

			e.printStackTrace();
		}

		SwingUtilities.updateComponentTreeUI(frmGroovejaar);
		frmGroovejaar.pack();

	}


	private boolean login (String username, String password) throws IOException{
		user = JGroovex.authenticateUser(username, password);
		if (!user.result.userID.equals("0")){
			makeLoginMenu();
			return true;
		}else
			return false;
	}

	private  void makeLoginMenu(){
		mnLogged.setText("Logged in as: "+user.result.username);
		mnGroove.remove(mntmLogin);

		try {
			playlists = JGroovex.userGetPlaylists(user.result.userID).result.Playlists;
		} catch (IOException e) {

			e.printStackTrace();
		}
		if (playlists.length == 0)
			mnPlaylist.add(new JMenuItem("No playlist"));
		else
			for (int i = 0;i<playlists.length;i++){
				//userPlaylists.put(lists[i].get("PlaylistID"), lists[i].get("Name"));
				final String id = playlists[i].get("PlaylistID");
				final String name =  playlists[i].get("Name");
				JMenuItem item = new JMenuItem(playlists[i].get("Name"));
				item.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						search(name+":"+id,"playlist");
					}
				});

				mnPlaylist.add(item);
			}
		mnLogged.add(mnPlaylist);
		mnLogged.add(mnUserLib);
		mnLogged.add(mnFav);
		mnGroove.add(mnLogged);
	}

	//End Functions

	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		initOptions();
		try {

			UIManager.setLookAndFeel(skin);
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					GrooveJaar window = new GrooveJaar();
					window.frmGroovejaar.setVisible(true);


				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public GrooveJaar() throws FileNotFoundException, IOException, InterruptedException, ExecutionException {

		try {
			//CountryUtil.initCountryCode();

			JGroovex.initiateQueue();

		} catch (IOException e) {
			showMessage("Fatal error.. Grooveshark.com seems to be down..");
			e.printStackTrace();
			System.exit(-1);
		}
		if (opt.showDisclaimer()){
			Disclaimer dis = new Disclaimer();
			dis.setLocationRelativeTo(null);
			dis.setVisible(true);
			dis.setAlwaysOnTop(true);
			opt.save("showDisclaimer", "false");
		}
		checkTemp();

		if (opt.getUpdate())
			checkUpdate(false);

		initialize();
		if (!opt.getUser().isEmpty() && !opt.getPassword().isEmpty() && user==null)
			login(opt.getUser(),opt.getPassword());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGroovejaar = new JFrame();

		frmGroovejaar.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
		frmGroovejaar.setTitle("GrooveJaar");
		frmGroovejaar.setBounds(100, 100, 850, 629);
		frmGroovejaar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		frmGroovejaar.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, ("Selected Song Info"), TitledBorder.LEADING, TitledBorder.TOP, null, null));

		jTabbedPane = new ClosableTabbedPane(JTabbedPane.TOP);
		jTabbedPane.addChangeListener(new ChangeListener() {
			// This method is called whenever the selected tab changes
			public void stateChanged(ChangeEvent evt) {

				// Get current tab
				if (jTabbedPane.getTabCount()>0){

					int sel = jTabbedPane.getSelectedIndex();
					String tab = jTabbedPane.getTabTitleAt(sel);
					if (!tab.contains("Searching - ")){ 

						tab = tab.substring(0,tab.lastIndexOf(" ("));

						setCurrentTab(tab.hashCode());
					}
				}


				/*
				if (filterList.containsKey(getCurrentTab())){
					txtFilterTitle.setText(filterList.get(getCurrentTab()).get("filterTitle"));
					txtFilterAlbum.setText(filterList.get(getCurrentTab()).get("filterAlbum"));
					txtFilterYear.setText(filterList.get(getCurrentTab()).get("filterYear"));
					txtFilterArtist.setText(filterList.get(getCurrentTab()).get("filterArtist"));
				}else
					clearFilter();*/

			}
		});
		jTabbedPane.setBorder(new TitledBorder(null, ("Search Results"), TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), ("Search"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), ("Downloads List"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JPanel panel_4 = new JPanel();
		panel_4.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel_4.setBorder(new TitledBorder(null, "Action", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(frmGroovejaar.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(menuBar, GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(panel, GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE))
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(jTabbedPane, GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
												.addContainerGap())
												.addGroup(groupLayout.createSequentialGroup()
														.addContainerGap()
														.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
														.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(menuBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
								.addGap(11)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(jTabbedPane, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
										.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
										.addContainerGap())
				);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));

		JButton btnAddLib = new JButton("");
		btnAddLib.setHorizontalAlignment(SwingConstants.LEFT);
		btnAddLib.setToolTipText("Add to User Lib");
		btnAddLib.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/user.png"))));
		btnAddLib.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (user == null)
					showMessage("Login first");
				else
					try {
						JGroovex.userAddSongsToLibrary(results.get(currentTab)[editingRow].get("SongID"),
								results.get(currentTab)[editingRow].get("SongName"),
								results.get(currentTab)[editingRow].get("AlbumID"),
								results.get(currentTab)[editingRow].get("AlbumName"),
								results.get(currentTab)[editingRow].get("ArtistID"),
								results.get(currentTab)[editingRow].get("ArtistName"),
								results.get(currentTab)[editingRow].get("CoverArtFilename"),
								results.get(currentTab)[editingRow].get("TrackNum"));
						showMessage("Added to "+user.result.username+" song library");
					} catch (IOException e1) {
						showMessage("Error");
					}
			}
		});

		JButton btnPlay = new JButton("");
		btnPlay.setToolTipText("Play");
		btnPlay.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/play.png"))));
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!player.frame.isVisible()){

					player.frame.setVisible(true);

				}

				final String songID = results.get(currentTab)[editingRow].get("SongID");
				player.addAndPlay( songID, (results.get(currentTab)[editingRow].get("SongName")!=null?results.get(currentTab)[editingRow].get("SongName"):results.get(currentTab)[editingRow].get("Name"))+"-"+results.get(currentTab)[editingRow].get("ArtistName"));
			}
		});
		panel_4.add(btnPlay);

		JButton btnAddToQueue = new JButton("");
		btnAddToQueue.setToolTipText("Add to Queue");
		btnAddToQueue.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/addqueue.png"))));
		btnAddToQueue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!player.frame.isVisible()){
					player.frame.setVisible(true);

				}

				if (editingRows != null)
					for (int i = 0;i<editingRows.length;i++){
						final String songID = results.get(currentTab)[editingRows[i]].get("SongID");
						player.addToQueue( songID, (results.get(currentTab)[editingRows[i]].get("SongName")!=null?results.get(currentTab)[editingRows[i]].get("SongName"):results.get(currentTab)[editingRows[i]].get("Name"))+"-"+results.get(currentTab)[editingRows[i]].get("ArtistName"));
					}else{
						final String songID = results.get(currentTab)[editingRow].get("SongID");
						player.addToQueue( songID, (results.get(currentTab)[editingRow].get("SongName")!=null?results.get(currentTab)[editingRow].get("SongName"):results.get(currentTab)[editingRow].get("Name"))+"-"+results.get(currentTab)[editingRow].get("ArtistName"));
					}
			}
		});
		panel_4.add(btnAddToQueue);

		JButton btnAddFav = new JButton("");
		btnAddFav.setAlignmentY(0.0f);
		btnAddFav.setToolTipText("Add to favorites");
		btnAddFav.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/fav.png"))));
		btnAddFav.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (user == null)
					showMessage("Login first");
				else
					try {

						JGroovex.favorite(results.get(currentTab)[editingRow].get("SongID"),
								results.get(currentTab)[editingRow].get("SongName"),
								results.get(currentTab)[editingRow].get("AlbumID"),
								results.get(currentTab)[editingRow].get("AlbumName"),
								results.get(currentTab)[editingRow].get("ArtistID"),
								results.get(currentTab)[editingRow].get("ArtistName"),
								results.get(currentTab)[editingRow].get("CoverArtFilename"),
								results.get(currentTab)[editingRow].get("TrackNum"));
						showMessage("Added to "+user.result.username+"'s favorite");
					} catch (IOException e1) {
						showMessage("Error");
					}
			}
		});

		JButton button_3 = new JButton("");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (user == null)
					showMessage("Login first");
				else
					if (playlists.length>0){
						String[] playIDs = new String[playlists.length];
						for (int i = 0;i<playIDs.length;i++)
							playIDs[i] = (i+1)+". "+playlists[i].get("Name");

						String s = (String)JOptionPane.showInputDialog(
								null,
								"Select the playlist:\n",
								"Add to playlist",
								JOptionPane.PLAIN_MESSAGE,
								null,
								playIDs,playIDs[0]);

						//If a string was returned, say so.
						if ((s != null) && (s.length() > 0)) {
							try {
								JGroovex.playlistAddSongToExisting(playlists[Integer.parseInt(s.substring(0, s.indexOf(".")))-1].get("PlaylistID"), results.get(currentTab)[editingRow].get("SongID"));
								showMessage("Song added to your playlist");
							} catch (IOException e) {
								showMessage("Error");
								e.printStackTrace();
							}
						}

					}else
						showMessage("No playlist found, you need to create one first");
			}
		});
		button_3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/addplaylist.png"))));
		panel_4.add(button_3);
		panel_4.add(btnAddFav);
		panel_4.add(btnAddLib);

		JButton button_2 = new JButton("");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable(){

					@Override
					public void run() {
						Lyrics ly = new Lyrics();
						try {
							ArrayList<String> lyr = ly.search((results.get(currentTab)[editingRow].get("SongName") != null ? results.get(currentTab)[editingRow].get("SongName") : results.get(currentTab)[editingRow].get("Name"))+ " " +results.get(currentTab)[editingRow].get("ArtistName"));
							if (lyr.size()>0){
								String[] possibilities = new String[lyr.size()];
								for (int i = 0;i<possibilities.length;i++)
									possibilities[i] = String.valueOf(i+1)+"."+lyr.get(i).substring(0,lyr.get(i).length()-5).replace("lyrics_", "").replace("_", " ");

								String s = (String)JOptionPane.showInputDialog(
										null,
										"Select the best match for your song:\n",
										"Lyric chooser",
										JOptionPane.PLAIN_MESSAGE,
										null,
										possibilities,possibilities[0]);

								//If a string was returned, say so.
								if ((s != null) && (s.length() > 0)) {
									LyricsGui lyg = new LyricsGui(ly.getLyrics(lyr.get(Integer.parseInt(s.substring(0,1))-1)));
									lyg.setLocationRelativeTo(null);
									lyg.setVisible(true);
								}
							}else
								showMessage("No lyrics found");

						} catch (UnsupportedEncodingException e) {

							e.printStackTrace();
						}

					}

				}).start();


			}
		});
		button_2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/lyrics.png"))));
		panel_4.add(button_2);

		JScrollPane scrollPane = new JScrollPane();

		table_1 = new JTable();


		scrollPane.setViewportView(table_1);
		scrollPane.setColumnHeaderView(null);
		table_1.setModel(modelDl);
		table_1.setTableHeader(null);
		final JMenuItem mnuPlay = new JMenuItem("Play on default system player");
		final JMenuItem mnuListDelete = new JMenuItem("Delete from this list");
		final JMenuItem mnuDiskDelete= new JMenuItem("Delete from the disk");
		final JPopupMenu popupMenu = new JPopupMenu();
		mnuPlay.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/playmenu.png"))));
		mnuDiskDelete.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/delete.png"))));
		mnuListDelete.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/listdelete.png"))));

		mnuPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					Desktop desktop = Desktop.getDesktop();
					if (System.getProperty("os.name").toLowerCase().contains("win"))
						Runtime.getRuntime().exec(("rundll32 SHELL32.DLL,ShellExec_RunDLL " + opt.getDownloadPath()+File.separator+table_1.getValueAt(table_1.getSelectedRow(),0 ).toString()+".mp3"));
					else if (desktop.isSupported(Desktop.Action.OPEN)){
						desktop.open(new File(opt.getDownloadPath()+File.separator+table_1.getValueAt(table_1.getSelectedRow(),0 ).toString()+".mp3"));
					}
					else
						showMessage(("Error"));
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		mnuListDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modelDl.removeRow(table_1.getSelectedRow());
			}
		});

		mnuDiskDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new File(opt.getDownloadPath()+File.separator+table_1.getValueAt(0, table_1.getSelectedRow()).toString()+".mp3").delete();
				table_1.setValueAt("Deleted", table_1.getSelectedRow(), 1);
			}
		});


		popupMenu.add(mnuPlay);
		popupMenu.add(mnuListDelete);
		popupMenu.add(mnuDiskDelete);
		table_1.addMouseListener( new MouseAdapter(){


			public void mousePressed(MouseEvent e) {
				showPopup(e);
			}
			public void mouseReleased(MouseEvent e) {
				showPopup(e);
			}

			private void showPopup(MouseEvent e) {

				if (e.isPopupTrigger()) {
					Point p = e.getPoint();

					// get the row index that contains that coordinate
					int rowNumber = table_1.rowAtPoint( p );

					// Get the ListSelectionModel of the JTable
					ListSelectionModel model = table_1.getSelectionModel();

					// set the selected interval of rows. Using the "rowNumber"
					// variable for the beginning and end selects only that one row.
					model.setSelectionInterval( rowNumber, rowNumber );
					if (table_1.getValueAt(rowNumber, 1).equals("Finished")|| table_1.getValueAt(rowNumber, 1).equals("Deleted") ){
						mnuPlay.setEnabled(true);
						mnuListDelete.setEnabled(true);
						mnuDiskDelete.setEnabled(true);
					}else{
						mnuPlay.setEnabled(false);
						mnuListDelete.setEnabled(false);
						mnuDiskDelete.setEnabled(false);
					}
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
				gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
						.addContainerGap()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
						.addGap(4))
				);
		gl_panel_2.setVerticalGroup(
				gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_2.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		panel_2.setLayout(gl_panel_2);

		txtSearch = new ExtendedTextField();
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {

				if (arg0.getKeyCode() == KeyEvent.VK_ENTER && !txtSearch.getText().isEmpty()){
					if (results.containsKey(txtSearch.getText().hashCode())){
						showMessage(("Already searched"));

					}else
						search(txtSearch.getText(),"songs");
				}
			}
		});
		txtSearch.setColumns(10);

		JButton button = new JButton("");
		button.setToolTipText(("Clear Search Box"));
		button.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/clear.png"))));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtSearch.setText("");
			}
		});

		JButton button_1 = new JButton("");
		button_1.setToolTipText(("Search!"));
		button_1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/find.png"))));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (results.containsKey(txtSearch.getText().hashCode())){
					showMessage(("Already searched"));
					return;
				}
				if (!txtSearch.getText().isEmpty())
					search(txtSearch.getText(),"songs");
				else 
					showMessage(("Nothing to search"));
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
				gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel_1.createSequentialGroup()
										.addContainerGap()
										.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(button, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel_1.createSequentialGroup()
												.addGap(7)
												.addComponent(txtSearch, GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)))
												.addContainerGap())
				);
		gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addContainerGap()
						.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(button)
								.addComponent(button_1))
								.addGap(18))
				);
		panel_1.setLayout(gl_panel_1);

		JMenu mnFile = new JMenu("File");

		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem(("Exit"));
		mntmExit.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/exit.png"))));
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		JMenuItem mntmOpenProjectPage = new JMenuItem(("Open Project Page"));
		mntmOpenProjectPage.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/open.png"))));
		mntmOpenProjectPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					openURL(projectPage);
				} catch (IOException e) {

					e.printStackTrace();
				} catch (URISyntaxException e) {

					e.printStackTrace();
				}
			}
		});
		mnFile.add(mntmOpenProjectPage);

		JMenuItem mntmOpenDownloadDir = new JMenuItem(("Open Download Path"));
		mntmOpenDownloadDir.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/folder.png"))));
		mntmOpenDownloadDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (Desktop.isDesktopSupported())
						Desktop.getDesktop().open(new File(opt.getDownloadPath()));
					else
						showMessage(("Function not supported on this OS"));
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		});
		mnFile.add(mntmOpenDownloadDir);

		JSeparator separator_2 = new JSeparator();
		mnFile.add(separator_2);
		mnFile.add(mntmExit);

		JMenu mnTools = new JMenu(("Tools"));
		menuBar.add(mnTools);

		JMenu mnGet = new JMenu(("Get Top Songs"));
		mnGet.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/topsongs.png"))));
		mnTools.add(mnGet);

		JMenuItem mntmGetTopSongs = new JMenuItem(("Of the Day"));
		mnGet.add(mntmGetTopSongs);


		JMenuItem mntmOfTheMonth = new JMenuItem(("Of the Month"));
		mntmOfTheMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				search("Top Monthly Songs", "top");
			}
		});
		mnGet.add(mntmOfTheMonth);
		mntmGetTopSongs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				search("Top Daily Songs", "top");
			}
		});

		JMenuItem mntmGetSongsFrom = new JMenuItem(("Get songs from Playlist"));
		mntmGetSongsFrom.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/playlist.png"))));
		mntmGetSongsFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String url = JOptionPane.showInputDialog(null,
						("Enter the remote url of your playlist (Contrl+V to paste)"),
						("Load song from playlist url"),
						JOptionPane.QUESTION_MESSAGE);

				if (url!=null){
					String playlistName = (url.substring(url.indexOf("playlist")+9,url.lastIndexOf("/")).replace("+"," "));
					search(playlistName+":"+getIdFromURL(url),"playlist");

				}
			}
		});
		mnTools.add(mntmGetSongsFrom);

		JMenuItem mntmGetSongsFrom_1 = new JMenuItem(("Get songs from Album"));
		mntmGetSongsFrom_1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/album.png"))));
		mntmGetSongsFrom_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String url = JOptionPane.showInputDialog(null,
						("Enter the remote url of your album (Contrl+V to paste)"),
						("Load song from album url"),
						JOptionPane.QUESTION_MESSAGE);

				if (url!=null){
					String albumName = (url.substring(url.indexOf("album")+6,url.lastIndexOf("/")).replace("+"," "));
					search(albumName+":"+getIdFromURL(url),"album");
				}
			}
		});
		mnTools.add(mntmGetSongsFrom_1);

		JSeparator separator_3 = new JSeparator();
		mnTools.add(separator_3);

		JMenuItem mntmShowInternalPlayer = new JMenuItem("Show Internal Player");
		mntmShowInternalPlayer.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/showplayer.png"))));
		mntmShowInternalPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player.frame.pack();
				player.frame.setVisible(true);
			}
		});

		mnTools.add(mntmShowInternalPlayer);

		JMenu mnSettings = new JMenu(("Settings"));

		menuBar.add(mnSettings);

		JMenuItem mntmManageOptions = new JMenuItem(("Manage Options"));
		mntmManageOptions.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/settings.png"))));
		mntmManageOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					OptionGui optWind = new OptionGui();

					optWind.setLocationRelativeTo(null);
					optWind.setVisible(true);
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		});
		mnSettings.add(mntmManageOptions);

		JSeparator separator = new JSeparator();
		mnSettings.add(separator);

		JMenu mnSetSkin = new JMenu(("Set Skin"));
		mnSetSkin.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/skins.png"))));
		mnSettings.add(mnSetSkin);

		JMenuItem mntmSystem = new JMenuItem("System");
		mntmSystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					setSkin(UIManager.getSystemLookAndFeelClassName());
					opt.save("skin", UIManager.getSystemLookAndFeelClassName());
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		});
		mnSetSkin.add(mntmSystem);

		JMenuItem mntmNimbus = new JMenuItem("Nimbus");
		mntmNimbus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					setSkin("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					opt.save("skin", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		});
		mnSetSkin.add(mntmNimbus);

		JMenuItem mntmMotif = new JMenuItem("Motif");
		mntmMotif.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					setSkin("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
					opt.save("skin", "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		});
		mnSetSkin.add(mntmMotif);

		JMenuItem mntmMetal = new JMenuItem("Metal");
		mntmMetal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					setSkin("javax.swing.plaf.metal.MetalLookAndFeel");
					opt.save("skin", "javax.swing.plaf.metal.MetalLookAndFeel");
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}

			}
		});
		mnSetSkin.add(mntmMetal);

		mnGroove = new JMenu("GrooveShark User");
		mnLogged = new JMenu("");
		mnPlaylist = new JMenu("Playlist");
		mnUserLib = new JMenuItem("Get User Library");
		mnUserLib.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				search(user.result.username+" Library","userSong");
			}

		}


				);

		mnFav = new JMenuItem("Get Favorites");
		mnFav.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				search(user.result.username+ " Fav","fav");

			}

		});

		menuBar.add(mnGroove);

		mntmLogin = new JMenuItem("Login");
		mntmLogin.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/login.png"))));
		mnGroove.add(mntmLogin);
		mntmLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final Login login = new Login();

				int a = JOptionPane.showConfirmDialog(frmGroovejaar ,login.getPanel(),"Insert username and password",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);  
				if(a==JOptionPane.OK_OPTION){

					mntmLogin.setEnabled(false);
					mntmLogin.setText("Logging in..");



					try {
						if (login(login.getUsername(),login.getPassword())){
							showMessage("Logged in");
							if (login.stayLogged()){
								opt.save("user", login.getUsername());
								opt.save("password", login.getPassword());
							}else{
								opt.save("user", "");
								opt.save("password", "");


							}
						}
					} catch (FileNotFoundException e) {

						e.printStackTrace();
					} catch (IOException e) {

						e.printStackTrace();
					}
				}
			}
		}
				);

		JMenu menu = new JMenu("?");

		menuBar.add(menu);

		JMenuItem mntmDisclaimer = new JMenuItem(("Disclaimer"));
		mntmDisclaimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Disclaimer dis = new Disclaimer();
				dis.setLocationRelativeTo(null);
				dis.setVisible(true);
			}
		});
		mntmDisclaimer.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/disclaimer.png"))));
		menu.add(mntmDisclaimer);

		JMenuItem mntmCheckUpdate = new JMenuItem(("Check Update"));
		mntmCheckUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					checkUpdate(true);
				} catch (InterruptedException e) {
					showMessage(("Unable to check udpate"));
					e.printStackTrace();
				} catch (ExecutionException e) {
					showMessage(("Unable to check udpate"));
					e.printStackTrace();
				}
			}
		});

		JMenuItem mntmMakeMeHappy = new JMenuItem(("Make me Happy"));
		mntmMakeMeHappy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					openURL("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=RFMAKH4HU66G2&lc=IT&item_name=Thanks%20for%20GrooveJaar&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donate_SM%2egif%3aNonHosted");
				} catch (IOException e) {

					e.printStackTrace();
				} catch (URISyntaxException e) {

					e.printStackTrace();
				}
			}
		});

		JMenuItem mntmLikeMeOn = new JMenuItem(("Like me on Facebook"));
		menu.add(mntmLikeMeOn);


		mntmLikeMeOn.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/facebook.png"))));
		mntmLikeMeOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					openURL("https://www.facebook.com/pages/GrooveJaar/207506022642084");
				} catch (IOException e) {

					e.printStackTrace();
				} catch (URISyntaxException e) {

					e.printStackTrace();
				}
			}
		});
		mntmMakeMeHappy.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/paypal.png"))));
		menu.add(mntmMakeMeHappy);
		mntmCheckUpdate.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/update.png"))));
		menu.add(mntmCheckUpdate);

		JSeparator separator_1 = new JSeparator();
		menu.add(separator_1);

		JMenuItem mntmInfo = new JMenuItem(("Info"));
		mntmInfo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/info.png"))));
		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Info infoWind = new Info();
				infoWind.setLocationRelativeTo(null);
				infoWind.setVisible(true);
			}
		});
		menu.add(mntmInfo);

		panel.setLayout(new MigLayout("", "[:117.00px:113.00px,fill][pref!,fill]", "[pref!,top][pref!,top][pref!,top][pref!,top][pref!,top]"));

		lblPreview = new JLabel("");
		lblPreview.setForeground(Color.WHITE);
		panel.add(lblPreview, "flowx,cell 0 0 1 5");
		lblPreview.setEnabled(false);
		lblPreview.setToolTipText(("Click to Save!"));
		lblPreview.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if(lblPreview.isEnabled())
					downloadCover(results.get(currentTab)[editingRow].get("CoverArtFilename"));

			}
		});


		lblArtist = new JLabel(("Artist:"));
		lblArtist.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblArtist, "cell 1 0,growx,aligny baseline");

		lblTitle = new JLabel(("Title:"));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblTitle, "cell 1 1,growx,aligny baseline");

		lblAlbum = new JLabel(("Album:"));
		lblAlbum.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblAlbum, "cell 1 2,growx,aligny baseline");

		lblBitrate = new JLabel(("BitRate:"));
		lblBitrate.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblBitrate, "cell 1 3,grow");

		lblSize = new JLabel("Size:");
		lblSize.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblSize, "cell 1 4");

		frmGroovejaar.getContentPane().setLayout(groupLayout);
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(580);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(100);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(50);
		//table_1.getColumnModel().getColumn(3).setPreferredWidth(50);
		table_1.setShowGrid(false);

		Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
		if (dimScreen.height<frmGroovejaar.getSize().height|| dimScreen.width<frmGroovejaar.getSize().width){
			//frmGroovejaar.setSize(frmGroovejaar.getSize().width, dimScreen.height);
			frmGroovejaar.setMaximumSize(dimScreen);
			frmGroovejaar.setExtendedState(frmGroovejaar.getExtendedState()|JFrame.MAXIMIZED_BOTH);

		}
		player = new Player();

	}
}


