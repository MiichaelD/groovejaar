package groovejaar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TokenKey {

	private File tokenFile = new File(System.getProperty ("user.dir")+File.separator+"token.properties");
	private Properties prop;
	private String jsToken, jsVersion,htmlToken, htmlVersion;

	public TokenKey() throws FileNotFoundException, IOException{

		prop = new Properties();

		prop.load(new FileInputStream(tokenFile));
		setJsToken(prop.getProperty("jsToken"));
		setJsVersion(prop.getProperty("jsVersion"));
		setHtmlToken(prop.getProperty("htmlToken"));
		setHtmlVersion(prop.getProperty("htmlVersion"));


	}

	public String getJsVersion() {
		return jsVersion;
	}

	public void setJsVersion(String jsVersion) {
		this.jsVersion = jsVersion;
	}

	public String getHtmlVersion() {
		return htmlVersion;
	}

	public void setHtmlVersion(String htmlVersion) {
		this.htmlVersion = htmlVersion;
	}

	public String getHtmlToken() {
		return htmlToken;
	}

	public void setHtmlToken(String htmlToken) {
		this.htmlToken = htmlToken;
	}

	public String getJsToken() {
		return jsToken;
	}

	public void setJsToken(String jsToken) {
		this.jsToken = jsToken;
	}


}
