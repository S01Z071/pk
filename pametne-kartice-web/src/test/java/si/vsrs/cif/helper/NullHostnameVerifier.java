/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.helper;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 *
 * @author andrej
 */
public class NullHostnameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(final String hostname, final SSLSession session) {
        return true;
    }
}
