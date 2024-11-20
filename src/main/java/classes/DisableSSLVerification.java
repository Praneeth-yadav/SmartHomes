package classes;

import javax.net.ssl.*;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class DisableSSLVerification {

    public static void disableSSLVerification() throws NoSuchAlgorithmException, KeyManagementException {
        // Create a no-op TrustManager that accepts all certificates
        TrustManager[] trustAllCertificates = new TrustManager[]{
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCertificates, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Disable HostnameVerifier
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    }
}
