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

	public Updater(String lastVersionURL) throws MalformedURLException {

		updateURL = new URL(lastVersionURL);

	}

	@Override
	public Boolean call() throws IOException {

		InputStream is = null;

		is = updateURL.openStream();

		prop.load(is);

		String version = prop.getProperty("version");
		int currentVersion = (normalizeVersion(version));
		int runningVersion = (normalizeVersion(GrooveJaar.version));

		/*for (byte i =0;i<runningVersion.length;i++){*/
		if (currentVersion > runningVersion)
			return true;
		/*}*/
		return false;




	}
	/*	private byte[] normalizeVersion(String version){
		//version = version.substring(0,version.indexOf(" "));
		String[] v =version.substring(0, version.indexOf("-")).split("\\.");
		byte[] vb = new byte[v.length+1];

		for (int i = 0;i<2;i++){
			vb[i] = Byte.parseByte(v[i]);
			System.out.println(vb[i]);
		}
		System.out.println(version);
		vb[vb.length] = Byte.parseByte(version.substring(7,8));

		return vb;
	}*/

	private int normalizeVersion(String version){
		return Integer.parseInt(version.substring(0,8).replace(".", "").replace("-", ""));
	}

}
