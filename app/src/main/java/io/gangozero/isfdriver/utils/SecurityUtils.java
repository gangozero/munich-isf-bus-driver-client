package io.gangozero.isfdriver.utils;

import android.content.Context;
import android.content.res.Resources;
import io.gangozero.isfdriver.R;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * Created by eleven on 10/09/2016.
 */
public class SecurityUtils {

	public static SSLContext initSSLContext(Context context) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, IOException, CertificateException {
		char[] passphrase = getPass();
		KeyStore ksTrust = KeyStore.getInstance("BKS");
		ksTrust.load(context.getResources().openRawResource(R.raw.out_sert), passphrase);
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		tmf.init(ksTrust);

		// Create a SSLContext with the certificate
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());
		return sslContext;

		// Create a HTTPS connection
		//URL url = new URL("https", "10.0.2.2", 8443, "/ssltest");
		//HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	}

	private static char[] getPass() {
		return "temp-pass".toCharArray();
	}

	public static SSLContext initCertificate(Context c) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");

		Resources resources = c.getResources();
		InputStream caInput = resources.openRawResource(resources.getIdentifier("out_sert", "raw", c.getPackageName()));
		java.security.cert.Certificate ca;

		try {
			ca = cf.generateCertificate(caInput);
			if (ca == null) throw new CertificateException("Certificate wasn't generated");
			//logger.consoleLog("ca=" + ((X509Certificate) ca).getSubjectDN());
		} finally {
			caInput.close();
		}

		String keyStoreType = KeyStore.getDefaultType();
		KeyStore keyStore = KeyStore.getInstance(keyStoreType);
		keyStore.load(null, getPass());

		keyStore.setCertificateEntry("ca", ca);

		String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
		tmf.init(keyStore);

		SSLContext context = SSLContext.getInstance("SSL");
		context.init(null, tmf.getTrustManagers(), null);
		return context;
	}
}
