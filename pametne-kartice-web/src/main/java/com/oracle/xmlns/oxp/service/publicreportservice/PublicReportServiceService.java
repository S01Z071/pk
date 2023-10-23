
package com.oracle.xmlns.oxp.service.publicreportservice;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "PublicReportServiceService", targetNamespace = "http://xmlns.oracle.com/oxp/service/PublicReportService", wsdlLocation = "file:/home/andrej/A/aProgrami/pametne-kartice/pametne-kartice-wsdl/src/main/wsdl/PublicReportService.wsdl")
public class PublicReportServiceService
    extends Service
{

    private final static URL PUBLICREPORTSERVICESERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.oracle.xmlns.oxp.service.publicreportservice.PublicReportServiceService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.oracle.xmlns.oxp.service.publicreportservice.PublicReportServiceService.class.getResource(".");
            url = new URL(baseUrl, "file:/home/andrej/A/aProgrami/pametne-kartice/pametne-kartice-wsdl/src/main/wsdl/PublicReportService.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'file:/home/andrej/A/aProgrami/pametne-kartice/pametne-kartice-wsdl/src/main/wsdl/PublicReportService.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        PUBLICREPORTSERVICESERVICE_WSDL_LOCATION = url;
    }

    public PublicReportServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PublicReportServiceService() {
        super(PUBLICREPORTSERVICESERVICE_WSDL_LOCATION, new QName("http://xmlns.oracle.com/oxp/service/PublicReportService", "PublicReportServiceService"));
    }

    /**
     * 
     * @return
     *     returns PublicReportService
     */
    @WebEndpoint(name = "PublicReportService")
    public PublicReportService getPublicReportService() {
        return super.getPort(new QName("http://xmlns.oracle.com/oxp/service/PublicReportService", "PublicReportService"), PublicReportService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PublicReportService
     */
    @WebEndpoint(name = "PublicReportService")
    public PublicReportService getPublicReportService(WebServiceFeature... features) {
        return super.getPort(new QName("http://xmlns.oracle.com/oxp/service/PublicReportService", "PublicReportService"), PublicReportService.class, features);
    }

}