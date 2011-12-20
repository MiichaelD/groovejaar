package groovejaar;
/*******************************************************************************
 * Copyright (c) 2011 Ale46.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 ******************************************************************************/
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Properties;
import java.util.concurrent.Callable;


public class Updater implements Callable<Boolean>{
	private URL updateURL;
	private Properties prop = new Properties();
	@Override
	public Boolean call() {
		try {
			updateURL = new URL("");
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		InputStream is = null;
		try {
			is = updateURL.openStream();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			prop.load(is);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
		String version = prop.getProperty("version");
		byte[] currentVersion = (normalizeVersion(version));
		byte[] runningVersion = (normalizeVersion(GrooveJaar.version));
		for (byte i =0;i<runningVersion.length;i++){
			if (currentVersion[i] > runningVersion[i])
				return true;
		}
		return false;
			
		
		
		
	}
	private byte[] normalizeVersion(String version){
		version = version.substring(0,version.indexOf(" "));
		String[] v =version.split("\\.");
		byte[] vb = new byte[v.length];
		
		for (int i = 0;i<vb.length;i++){
			vb[i] = Byte.parseByte(v[i]);
		}
		return vb;
	}

	
}
