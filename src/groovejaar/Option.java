package groovejaar;
/*******************************************************************************
 * Copyright (c) 2011 Ale46.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Ale46 - initial API and implementation
 ******************************************************************************/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.swing.UIManager;


public class Option {

	private File config = new File(System.getProperty ("user.dir")+File.separator+"config.properties");
	private Properties prop;
	
	public Option() throws FileNotFoundException, IOException{

		prop = new Properties();
		prop.load(new FileInputStream(config));
	}

	public void save(HashMap<String,String> map) throws FileNotFoundException, IOException{
		Set<String> keys = map.keySet();
		for (Iterator<String> i = keys.iterator(); i.hasNext();) {
			String key = i.next();
			String value = map.get(key);
			prop.setProperty(key, value);
		}


		prop.store(new FileOutputStream(config), null);
	}

	public void save(String key, String value) throws FileNotFoundException, IOException{
		prop.setProperty(key, value);
		prop.store(new FileOutputStream(config), null);
	}
	
	public String getDownloadPath(){
		if (prop.getProperty("downloadPath").isEmpty()) return System.getProperty ("user.dir");
		return prop.getProperty("downloadPath", System.getProperty ("user.dir"));

	}
	
	public String getUser(){
		return prop.getProperty("user");

	}
	
	public String getPassword(){
		return prop.getProperty("password");

	}
	
	public byte getMaxDownloads(){
		return Byte.parseByte(prop.getProperty("maxDownloads"));
	}
	
	public String getSkin(){
		if (prop.getProperty("skin").contains("system"))
			return UIManager.getSystemLookAndFeelClassName();
		else
			return prop.getProperty("skin");
	}
	
	public boolean getUpdate(){
		if (prop.getProperty("autoUpdate").equals("true"))
			return true;
		else
			return false;
	}
	
	public String getFileTemplate(){
		return prop.getProperty("template");
	}
	
	public String getActionIfExist(){
		return prop.getProperty("ifExist");
	}
	
	public boolean autoBitrateSize(){
		if (prop.getProperty("autoBitrateSize").equals("true"))
			return true;
		else
			return false;
	}
	
	public boolean showDisclaimer(){
		return new Boolean(prop.getProperty("showDisclaimer"));
	}
}
