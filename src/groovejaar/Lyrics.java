package groovejaar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lyrics {

	private ArrayList<String> getHTML(String url){
		ArrayList<String> res = new ArrayList<String>();

		URL lyr;
		try {
			lyr = new URL(url);
			URLConnection lyrc = lyr.openConnection();
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							lyrc.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) 
				res.add(inputLine);
			in.close();
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return res;
	}

	public  ArrayList<String> search(String title){
		boolean find = false;
		byte count = 0;
		ArrayList<String> res = new ArrayList<String>();
		ArrayList<String> html=null;
		try {
			
			html = getHTML("http://www.lyricsmania.com/searchnew.php?k="+URLEncoder.encode(title,"UTF-8")+"&x=0&y=0");//"http://webservices.lyrdb.com/lookup.php?q="+URLEncoder.encode(title,"UTF-8")+"&for=fullt&agent=GrooveJaar");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		for (int i  = 0;i<html.size();i++){
			if (find && count<10){
				Pattern p = Pattern.compile("<a href=.(.*?).>");
				Matcher m = p.matcher(html.get(i));

				while (m.find()) {
					if (m.group(1).contains("\"")){
						res.add(m.group(1).substring(1,m.group(1).indexOf("\"")));
						count++;
					}
					
				}
				
			}
			
			if (!find){
				if (html.get(i).contains("songs found:<br><br>")){
					find = true;
				}
			}
		}
		return res;
	}

	public String getLyrics(String id) throws UnsupportedEncodingException{
/*		ArrayList<String> id=search(title);
		for (int i = 0;i<id.size();i++)
			System.out.println(id.get(i));*/
		String html  = getHTML("http://www.lyricsmania.com/"+id).toString();
		int start = html.indexOf("<div id='songlyrics_h' class='dn'>")+"<div id='songlyrics_h' class='dn'>".length()+2;
		return html.substring(start ,html.indexOf("</div>", start));
		
	}

}
