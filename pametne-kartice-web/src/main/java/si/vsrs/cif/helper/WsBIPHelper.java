/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.helper;

import com.oracle.xmlns.oxp.service.publicreportservice.PublicReportService;
import com.oracle.xmlns.oxp.service.publicreportservice.ReportRequest;
import com.oracle.xmlns.oxp.service.publicreportservice.ReportResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author uporabnik
 */
public class WsBIPHelper {

   /* private PublicReportService wsBIPService;
    private String username;
    private String password;
    private String template;
    private String report;
    private String port;

    public PublicReportService getWsBIPService() {
        return wsBIPService;
    }

    public void setWsBIPService(PublicReportService wsBIPService) {
        this.wsBIPService = wsBIPService;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public byte[] callReport(byte by[]) throws Exception {

        com.oracle.xmlns.oxp.service.publicreportservice.PublicReportService port1 = getPort(port);
        ReportRequest re = new ReportRequest();
        re.setAttributeFormat("pdf");
        re.setAttributeTemplate(template);
        re.setReportAbsolutePath(report);
        re.setReportData(by);
        re.setSizeOfDataChunkDownload(-1);

        ReportResponse ress = port1.runReport(re, username, password);
        return ress.getReportBytes();

    }

    public com.oracle.xmlns.oxp.service.publicreportservice.PublicReportService getPort(String url) {
        com.oracle.xmlns.oxp.service.publicreportservice.PublicReportServiceService service
                = new com.oracle.xmlns.oxp.service.publicreportservice.PublicReportServiceService(this.getClass().getClassLoader().getResource("PublicReportService.wsdl"), new QName("http://xmlns.oracle.com/oxp/service/PublicReportService", "PublicReportServiceService"));
        com.oracle.xmlns.oxp.service.publicreportservice.PublicReportService port = service.getPublicReportService();
        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        return port;
    }
    */
}
