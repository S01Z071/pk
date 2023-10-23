
package com.oracle.xmlns.oxp.service.publicreportservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.oracle.xmlns.oxp.service.publicreportservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Fault_QNAME = new QName("http://xmlns.oracle.com/oxp/service/PublicReportService", "fault");
    private final static QName _Fault1_QNAME = new QName("http://xmlns.oracle.com/oxp/service/PublicReportService", "fault1");
    private final static QName _Fault2_QNAME = new QName("http://xmlns.oracle.com/oxp/service/PublicReportService", "fault2");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.oracle.xmlns.oxp.service.publicreportservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetScheduledReportStatusResponse }
     * 
     */
    public GetScheduledReportStatusResponse createGetScheduledReportStatusResponse() {
        return new GetScheduledReportStatusResponse();
    }

    /**
     * Create an instance of {@link UploadTemplateForReportResponse }
     * 
     */
    public UploadTemplateForReportResponse createUploadTemplateForReportResponse() {
        return new UploadTemplateForReportResponse();
    }

    /**
     * Create an instance of {@link GetScheduledReportStatusInSession }
     * 
     */
    public GetScheduledReportStatusInSession createGetScheduledReportStatusInSession() {
        return new GetScheduledReportStatusInSession();
    }

    /**
     * Create an instance of {@link GetReportParametersResponse }
     * 
     */
    public GetReportParametersResponse createGetReportParametersResponse() {
        return new GetReportParametersResponse();
    }

    /**
     * Create an instance of {@link LocalDeliveryOption }
     * 
     */
    public LocalDeliveryOption createLocalDeliveryOption() {
        return new LocalDeliveryOption();
    }

    /**
     * Create an instance of {@link GetScheduledReportInfoInSessionResponse }
     * 
     */
    public GetScheduledReportInfoInSessionResponse createGetScheduledReportInfoInSessionResponse() {
        return new GetScheduledReportInfoInSessionResponse();
    }

    /**
     * Create an instance of {@link GetScheduledReportInfoInSession }
     * 
     */
    public GetScheduledReportInfoInSession createGetScheduledReportInfoInSession() {
        return new GetScheduledReportInfoInSession();
    }

    /**
     * Create an instance of {@link GetReportDefinitionInSession }
     * 
     */
    public GetReportDefinitionInSession createGetReportDefinitionInSession() {
        return new GetReportDefinitionInSession();
    }

    /**
     * Create an instance of {@link GetBIPHTTPSessionIntervalResponse }
     * 
     */
    public GetBIPHTTPSessionIntervalResponse createGetBIPHTTPSessionIntervalResponse() {
        return new GetBIPHTTPSessionIntervalResponse();
    }

    /**
     * Create an instance of {@link GetSecurityModel }
     * 
     */
    public GetSecurityModel createGetSecurityModel() {
        return new GetSecurityModel();
    }

    /**
     * Create an instance of {@link DeleteScheduledReportInSession }
     * 
     */
    public DeleteScheduledReportInSession createDeleteScheduledReportInSession() {
        return new DeleteScheduledReportInSession();
    }

    /**
     * Create an instance of {@link InvalidParametersException }
     * 
     */
    public InvalidParametersException createInvalidParametersException() {
        return new InvalidParametersException();
    }

    /**
     * Create an instance of {@link SuspendScheduledReportInSession }
     * 
     */
    public SuspendScheduledReportInSession createSuspendScheduledReportInSession() {
        return new SuspendScheduledReportInSession();
    }

    /**
     * Create an instance of {@link TemplateFormatsLabelValues }
     * 
     */
    public TemplateFormatsLabelValues createTemplateFormatsLabelValues() {
        return new TemplateFormatsLabelValues();
    }

    /**
     * Create an instance of {@link SuspendScheduledReportInSessionResponse }
     * 
     */
    public SuspendScheduledReportInSessionResponse createSuspendScheduledReportInSessionResponse() {
        return new SuspendScheduledReportInSessionResponse();
    }

    /**
     * Create an instance of {@link JobStatus }
     * 
     */
    public JobStatus createJobStatus() {
        return new JobStatus();
    }

    /**
     * Create an instance of {@link UpdateReportDefinitionInSession }
     * 
     */
    public UpdateReportDefinitionInSession createUpdateReportDefinitionInSession() {
        return new UpdateReportDefinitionInSession();
    }

    /**
     * Create an instance of {@link UploadReportResponse }
     * 
     */
    public UploadReportResponse createUploadReportResponse() {
        return new UploadReportResponse();
    }

    /**
     * Create an instance of {@link GetSecurityModelResponse }
     * 
     */
    public GetSecurityModelResponse createGetSecurityModelResponse() {
        return new GetSecurityModelResponse();
    }

    /**
     * Create an instance of {@link DeleteScheduledReportHistoryInSession }
     * 
     */
    public DeleteScheduledReportHistoryInSession createDeleteScheduledReportHistoryInSession() {
        return new DeleteScheduledReportHistoryInSession();
    }

    /**
     * Create an instance of {@link JobHistoryInfo }
     * 
     */
    public JobHistoryInfo createJobHistoryInfo() {
        return new JobHistoryInfo();
    }

    /**
     * Create an instance of {@link RunReportResponse }
     * 
     */
    public RunReportResponse createRunReportResponse() {
        return new RunReportResponse();
    }

    /**
     * Create an instance of {@link EMailDeliveryOption }
     * 
     */
    public EMailDeliveryOption createEMailDeliveryOption() {
        return new EMailDeliveryOption();
    }

    /**
     * Create an instance of {@link OperationFailedException }
     * 
     */
    public OperationFailedException createOperationFailedException() {
        return new OperationFailedException();
    }

    /**
     * Create an instance of {@link IsReportExist }
     * 
     */
    public IsReportExist createIsReportExist() {
        return new IsReportExist();
    }

    /**
     * Create an instance of {@link ItemData }
     * 
     */
    public ItemData createItemData() {
        return new ItemData();
    }

    /**
     * Create an instance of {@link GetFolderContentsInSession }
     * 
     */
    public GetFolderContentsInSession createGetFolderContentsInSession() {
        return new GetFolderContentsInSession();
    }

    /**
     * Create an instance of {@link ArrayOfParamNameValue }
     * 
     */
    public ArrayOfParamNameValue createArrayOfParamNameValue() {
        return new ArrayOfParamNameValue();
    }

    /**
     * Create an instance of {@link RunReportInSession }
     * 
     */
    public RunReportInSession createRunReportInSession() {
        return new RunReportInSession();
    }

    /**
     * Create an instance of {@link GetScheduledReportStatus }
     * 
     */
    public GetScheduledReportStatus createGetScheduledReportStatus() {
        return new GetScheduledReportStatus();
    }

    /**
     * Create an instance of {@link ResumeScheduledReportResponse }
     * 
     */
    public ResumeScheduledReportResponse createResumeScheduledReportResponse() {
        return new ResumeScheduledReportResponse();
    }

    /**
     * Create an instance of {@link GetScheduledReportHistoryInfoInSession }
     * 
     */
    public GetScheduledReportHistoryInfoInSession createGetScheduledReportHistoryInfoInSession() {
        return new GetScheduledReportHistoryInfoInSession();
    }

    /**
     * Create an instance of {@link GetScheduledReportHistoryInfoResponse }
     * 
     */
    public GetScheduledReportHistoryInfoResponse createGetScheduledReportHistoryInfoResponse() {
        return new GetScheduledReportHistoryInfoResponse();
    }

    /**
     * Create an instance of {@link UploadReportDataChunkResponse }
     * 
     */
    public UploadReportDataChunkResponse createUploadReportDataChunkResponse() {
        return new UploadReportDataChunkResponse();
    }

    /**
     * Create an instance of {@link GetListOfScheduledReportsStatusInSessionResponse }
     * 
     */
    public GetListOfScheduledReportsStatusInSessionResponse createGetListOfScheduledReportsStatusInSessionResponse() {
        return new GetListOfScheduledReportsStatusInSessionResponse();
    }

    /**
     * Create an instance of {@link UploadTemplateForReportInSession }
     * 
     */
    public UploadTemplateForReportInSession createUploadTemplateForReportInSession() {
        return new UploadTemplateForReportInSession();
    }

    /**
     * Create an instance of {@link ArrayOfTemplateFormatLabelValue }
     * 
     */
    public ArrayOfTemplateFormatLabelValue createArrayOfTemplateFormatLabelValue() {
        return new ArrayOfTemplateFormatLabelValue();
    }

    /**
     * Create an instance of {@link GetScheduledReportHistoryInfo }
     * 
     */
    public GetScheduledReportHistoryInfo createGetScheduledReportHistoryInfo() {
        return new GetScheduledReportHistoryInfo();
    }

    /**
     * Create an instance of {@link DownloadReportDataChunkResponse }
     * 
     */
    public DownloadReportDataChunkResponse createDownloadReportDataChunkResponse() {
        return new DownloadReportDataChunkResponse();
    }

    /**
     * Create an instance of {@link DeleteFolderInSessionResponse }
     * 
     */
    public DeleteFolderInSessionResponse createDeleteFolderInSessionResponse() {
        return new DeleteFolderInSessionResponse();
    }

    /**
     * Create an instance of {@link GetScheduledReportInfo }
     * 
     */
    public GetScheduledReportInfo createGetScheduledReportInfo() {
        return new GetScheduledReportInfo();
    }

    /**
     * Create an instance of {@link DeleteReport }
     * 
     */
    public DeleteReport createDeleteReport() {
        return new DeleteReport();
    }

    /**
     * Create an instance of {@link ReportRequest }
     * 
     */
    public ReportRequest createReportRequest() {
        return new ReportRequest();
    }

    /**
     * Create an instance of {@link RunReport }
     * 
     */
    public RunReport createRunReport() {
        return new RunReport();
    }

    /**
     * Create an instance of {@link GetReportDefinition }
     * 
     */
    public GetReportDefinition createGetReportDefinition() {
        return new GetReportDefinition();
    }

    /**
     * Create an instance of {@link DeleteScheduledReport }
     * 
     */
    public DeleteScheduledReport createDeleteScheduledReport() {
        return new DeleteScheduledReport();
    }

    /**
     * Create an instance of {@link ScheduleRequest }
     * 
     */
    public ScheduleRequest createScheduleRequest() {
        return new ScheduleRequest();
    }

    /**
     * Create an instance of {@link DownloadReportInSession }
     * 
     */
    public DownloadReportInSession createDownloadReportInSession() {
        return new DownloadReportInSession();
    }

    /**
     * Create an instance of {@link ScheduleReportResponse }
     * 
     */
    public ScheduleReportResponse createScheduleReportResponse() {
        return new ScheduleReportResponse();
    }

    /**
     * Create an instance of {@link RemoveTemplateForReport }
     * 
     */
    public RemoveTemplateForReport createRemoveTemplateForReport() {
        return new RemoveTemplateForReport();
    }

    /**
     * Create an instance of {@link DeleteFolder }
     * 
     */
    public DeleteFolder createDeleteFolder() {
        return new DeleteFolder();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link RemoveTemplateForReportInSessionResponse }
     * 
     */
    public RemoveTemplateForReportInSessionResponse createRemoveTemplateForReportInSessionResponse() {
        return new RemoveTemplateForReportInSessionResponse();
    }

    /**
     * Create an instance of {@link WebDAVDeliveryOption }
     * 
     */
    public WebDAVDeliveryOption createWebDAVDeliveryOption() {
        return new WebDAVDeliveryOption();
    }

    /**
     * Create an instance of {@link ImpersonateResponse }
     * 
     */
    public ImpersonateResponse createImpersonateResponse() {
        return new ImpersonateResponse();
    }

    /**
     * Create an instance of {@link DeleteReportResponse }
     * 
     */
    public DeleteReportResponse createDeleteReportResponse() {
        return new DeleteReportResponse();
    }

    /**
     * Create an instance of {@link UploadReportInSessionResponse }
     * 
     */
    public UploadReportInSessionResponse createUploadReportInSessionResponse() {
        return new UploadReportInSessionResponse();
    }

    /**
     * Create an instance of {@link GetListOfScheduledReportsStatusResponse }
     * 
     */
    public GetListOfScheduledReportsStatusResponse createGetListOfScheduledReportsStatusResponse() {
        return new GetListOfScheduledReportsStatusResponse();
    }

    /**
     * Create an instance of {@link RemoveTemplateForReportInSession }
     * 
     */
    public RemoveTemplateForReportInSession createRemoveTemplateForReportInSession() {
        return new RemoveTemplateForReportInSession();
    }

    /**
     * Create an instance of {@link DeleteReportInSessionResponse }
     * 
     */
    public DeleteReportInSessionResponse createDeleteReportInSessionResponse() {
        return new DeleteReportInSessionResponse();
    }

    /**
     * Create an instance of {@link GetReportDefinitionResponse }
     * 
     */
    public GetReportDefinitionResponse createGetReportDefinitionResponse() {
        return new GetReportDefinitionResponse();
    }

    /**
     * Create an instance of {@link DeleteFolderInSession }
     * 
     */
    public DeleteFolderInSession createDeleteFolderInSession() {
        return new DeleteFolderInSession();
    }

    /**
     * Create an instance of {@link ScheduleReportInSessionResponse }
     * 
     */
    public ScheduleReportInSessionResponse createScheduleReportInSessionResponse() {
        return new ScheduleReportInSessionResponse();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link Impersonate }
     * 
     */
    public Impersonate createImpersonate() {
        return new Impersonate();
    }

    /**
     * Create an instance of {@link ReportDataChunk }
     * 
     */
    public ReportDataChunk createReportDataChunk() {
        return new ReportDataChunk();
    }

    /**
     * Create an instance of {@link DeleteScheduledReportInSessionResponse }
     * 
     */
    public DeleteScheduledReportInSessionResponse createDeleteScheduledReportInSessionResponse() {
        return new DeleteScheduledReportInSessionResponse();
    }

    /**
     * Create an instance of {@link DeleteScheduledReportResponse }
     * 
     */
    public DeleteScheduledReportResponse createDeleteScheduledReportResponse() {
        return new DeleteScheduledReportResponse();
    }

    /**
     * Create an instance of {@link GetBIPHTTPSessionInterval }
     * 
     */
    public GetBIPHTTPSessionInterval createGetBIPHTTPSessionInterval() {
        return new GetBIPHTTPSessionInterval();
    }

    /**
     * Create an instance of {@link CreateReportResponse }
     * 
     */
    public CreateReportResponse createCreateReportResponse() {
        return new CreateReportResponse();
    }

    /**
     * Create an instance of {@link UploadTemplateForReportInSessionResponse }
     * 
     */
    public UploadTemplateForReportInSessionResponse createUploadTemplateForReportInSessionResponse() {
        return new UploadTemplateForReportInSessionResponse();
    }

    /**
     * Create an instance of {@link SuspendScheduledReport }
     * 
     */
    public SuspendScheduledReport createSuspendScheduledReport() {
        return new SuspendScheduledReport();
    }

    /**
     * Create an instance of {@link GetFolderContentsResponse }
     * 
     */
    public GetFolderContentsResponse createGetFolderContentsResponse() {
        return new GetFolderContentsResponse();
    }

    /**
     * Create an instance of {@link FaxDeliveryOption }
     * 
     */
    public FaxDeliveryOption createFaxDeliveryOption() {
        return new FaxDeliveryOption();
    }

    /**
     * Create an instance of {@link CreateReportFolderInSession }
     * 
     */
    public CreateReportFolderInSession createCreateReportFolderInSession() {
        return new CreateReportFolderInSession();
    }

    /**
     * Create an instance of {@link CreateReportFolder }
     * 
     */
    public CreateReportFolder createCreateReportFolder() {
        return new CreateReportFolder();
    }

    /**
     * Create an instance of {@link TemplateFormatLabelValue }
     * 
     */
    public TemplateFormatLabelValue createTemplateFormatLabelValue() {
        return new TemplateFormatLabelValue();
    }

    /**
     * Create an instance of {@link IsReportExistInSession }
     * 
     */
    public IsReportExistInSession createIsReportExistInSession() {
        return new IsReportExistInSession();
    }

    /**
     * Create an instance of {@link IsFolderExistInSession }
     * 
     */
    public IsFolderExistInSession createIsFolderExistInSession() {
        return new IsFolderExistInSession();
    }

    /**
     * Create an instance of {@link DeleteScheduledReportHistoryResponse }
     * 
     */
    public DeleteScheduledReportHistoryResponse createDeleteScheduledReportHistoryResponse() {
        return new DeleteScheduledReportHistoryResponse();
    }

    /**
     * Create an instance of {@link GetListOfScheduledReportsStatus }
     * 
     */
    public GetListOfScheduledReportsStatus createGetListOfScheduledReportsStatus() {
        return new GetListOfScheduledReportsStatus();
    }

    /**
     * Create an instance of {@link GetScheduledReportHistoryInfoInSessionResponse }
     * 
     */
    public GetScheduledReportHistoryInfoInSessionResponse createGetScheduledReportHistoryInfoInSessionResponse() {
        return new GetScheduledReportHistoryInfoInSessionResponse();
    }

    /**
     * Create an instance of {@link DeleteReportInSession }
     * 
     */
    public DeleteReportInSession createDeleteReportInSession() {
        return new DeleteReportInSession();
    }

    /**
     * Create an instance of {@link GetReportParametersInSession }
     * 
     */
    public GetReportParametersInSession createGetReportParametersInSession() {
        return new GetReportParametersInSession();
    }

    /**
     * Create an instance of {@link GetFolderContents }
     * 
     */
    public GetFolderContents createGetFolderContents() {
        return new GetFolderContents();
    }

    /**
     * Create an instance of {@link HasReportAccessInSessionResponse }
     * 
     */
    public HasReportAccessInSessionResponse createHasReportAccessInSessionResponse() {
        return new HasReportAccessInSessionResponse();
    }

    /**
     * Create an instance of {@link CreateReport }
     * 
     */
    public CreateReport createCreateReport() {
        return new CreateReport();
    }

    /**
     * Create an instance of {@link GetReportParametersInSessionResponse }
     * 
     */
    public GetReportParametersInSessionResponse createGetReportParametersInSessionResponse() {
        return new GetReportParametersInSessionResponse();
    }

    /**
     * Create an instance of {@link CreateReportInSession }
     * 
     */
    public CreateReportInSession createCreateReportInSession() {
        return new CreateReportInSession();
    }

    /**
     * Create an instance of {@link AccessDeniedException }
     * 
     */
    public AccessDeniedException createAccessDeniedException() {
        return new AccessDeniedException();
    }

    /**
     * Create an instance of {@link SuspendScheduledReportResponse }
     * 
     */
    public SuspendScheduledReportResponse createSuspendScheduledReportResponse() {
        return new SuspendScheduledReportResponse();
    }

    /**
     * Create an instance of {@link IsFolderExist }
     * 
     */
    public IsFolderExist createIsFolderExist() {
        return new IsFolderExist();
    }

    /**
     * Create an instance of {@link ReportDefinition }
     * 
     */
    public ReportDefinition createReportDefinition() {
        return new ReportDefinition();
    }

    /**
     * Create an instance of {@link CreateReportFolderResponse }
     * 
     */
    public CreateReportFolderResponse createCreateReportFolderResponse() {
        return new CreateReportFolderResponse();
    }

    /**
     * Create an instance of {@link CreateReportInSessionResponse }
     * 
     */
    public CreateReportInSessionResponse createCreateReportInSessionResponse() {
        return new CreateReportInSessionResponse();
    }

    /**
     * Create an instance of {@link ScheduleReport }
     * 
     */
    public ScheduleReport createScheduleReport() {
        return new ScheduleReport();
    }

    /**
     * Create an instance of {@link HasReportAccessResponse }
     * 
     */
    public HasReportAccessResponse createHasReportAccessResponse() {
        return new HasReportAccessResponse();
    }

    /**
     * Create an instance of {@link ResumeScheduledReportInSessionResponse }
     * 
     */
    public ResumeScheduledReportInSessionResponse createResumeScheduledReportInSessionResponse() {
        return new ResumeScheduledReportInSessionResponse();
    }

    /**
     * Create an instance of {@link DownloadReportResponse }
     * 
     */
    public DownloadReportResponse createDownloadReportResponse() {
        return new DownloadReportResponse();
    }

    /**
     * Create an instance of {@link RunReportInSessionResponse }
     * 
     */
    public RunReportInSessionResponse createRunReportInSessionResponse() {
        return new RunReportInSessionResponse();
    }

    /**
     * Create an instance of {@link UpdateReportDefinitionResponse }
     * 
     */
    public UpdateReportDefinitionResponse createUpdateReportDefinitionResponse() {
        return new UpdateReportDefinitionResponse();
    }

    /**
     * Create an instance of {@link GetScheduledReportInfoResponse }
     * 
     */
    public GetScheduledReportInfoResponse createGetScheduledReportInfoResponse() {
        return new GetScheduledReportInfoResponse();
    }

    /**
     * Create an instance of {@link GetReportDefinitionInSessionResponse }
     * 
     */
    public GetReportDefinitionInSessionResponse createGetReportDefinitionInSessionResponse() {
        return new GetReportDefinitionInSessionResponse();
    }

    /**
     * Create an instance of {@link DeliveryRequest }
     * 
     */
    public DeliveryRequest createDeliveryRequest() {
        return new DeliveryRequest();
    }

    /**
     * Create an instance of {@link DeleteFolderResponse }
     * 
     */
    public DeleteFolderResponse createDeleteFolderResponse() {
        return new DeleteFolderResponse();
    }

    /**
     * Create an instance of {@link IsReportExistResponse }
     * 
     */
    public IsReportExistResponse createIsReportExistResponse() {
        return new IsReportExistResponse();
    }

    /**
     * Create an instance of {@link ReportResponse }
     * 
     */
    public ReportResponse createReportResponse() {
        return new ReportResponse();
    }

    /**
     * Create an instance of {@link ArrayOfTemplateFormatsLabelValues }
     * 
     */
    public ArrayOfTemplateFormatsLabelValues createArrayOfTemplateFormatsLabelValues() {
        return new ArrayOfTemplateFormatsLabelValues();
    }

    /**
     * Create an instance of {@link PrintDeliveryOption }
     * 
     */
    public PrintDeliveryOption createPrintDeliveryOption() {
        return new PrintDeliveryOption();
    }

    /**
     * Create an instance of {@link UpdateReportDefinition }
     * 
     */
    public UpdateReportDefinition createUpdateReportDefinition() {
        return new UpdateReportDefinition();
    }

    /**
     * Create an instance of {@link RemoveTemplateForReportResponse }
     * 
     */
    public RemoveTemplateForReportResponse createRemoveTemplateForReportResponse() {
        return new RemoveTemplateForReportResponse();
    }

    /**
     * Create an instance of {@link ResumeScheduledReportInSession }
     * 
     */
    public ResumeScheduledReportInSession createResumeScheduledReportInSession() {
        return new ResumeScheduledReportInSession();
    }

    /**
     * Create an instance of {@link FTPDeliveryOption }
     * 
     */
    public FTPDeliveryOption createFTPDeliveryOption() {
        return new FTPDeliveryOption();
    }

    /**
     * Create an instance of {@link HasReportAccess }
     * 
     */
    public HasReportAccess createHasReportAccess() {
        return new HasReportAccess();
    }

    /**
     * Create an instance of {@link IsFolderExistInSessionResponse }
     * 
     */
    public IsFolderExistInSessionResponse createIsFolderExistInSessionResponse() {
        return new IsFolderExistInSessionResponse();
    }

    /**
     * Create an instance of {@link DeleteScheduledReportHistoryInSessionResponse }
     * 
     */
    public DeleteScheduledReportHistoryInSessionResponse createDeleteScheduledReportHistoryInSessionResponse() {
        return new DeleteScheduledReportHistoryInSessionResponse();
    }

    /**
     * Create an instance of {@link ScheduleReportInSession }
     * 
     */
    public ScheduleReportInSession createScheduleReportInSession() {
        return new ScheduleReportInSession();
    }

    /**
     * Create an instance of {@link DeleteScheduledReportHistory }
     * 
     */
    public DeleteScheduledReportHistory createDeleteScheduledReportHistory() {
        return new DeleteScheduledReportHistory();
    }

    /**
     * Create an instance of {@link Logout }
     * 
     */
    public Logout createLogout() {
        return new Logout();
    }

    /**
     * Create an instance of {@link GetScheduledReportStatusInSessionResponse }
     * 
     */
    public GetScheduledReportStatusInSessionResponse createGetScheduledReportStatusInSessionResponse() {
        return new GetScheduledReportStatusInSessionResponse();
    }

    /**
     * Create an instance of {@link GetListOfScheduledReportsStatusInSession }
     * 
     */
    public GetListOfScheduledReportsStatusInSession createGetListOfScheduledReportsStatusInSession() {
        return new GetListOfScheduledReportsStatusInSession();
    }

    /**
     * Create an instance of {@link UploadReport }
     * 
     */
    public UploadReport createUploadReport() {
        return new UploadReport();
    }

    /**
     * Create an instance of {@link UploadReportInSession }
     * 
     */
    public UploadReportInSession createUploadReportInSession() {
        return new UploadReportInSession();
    }

    /**
     * Create an instance of {@link IsFolderExistResponse }
     * 
     */
    public IsFolderExistResponse createIsFolderExistResponse() {
        return new IsFolderExistResponse();
    }

    /**
     * Create an instance of {@link DownloadReport }
     * 
     */
    public DownloadReport createDownloadReport() {
        return new DownloadReport();
    }

    /**
     * Create an instance of {@link IsReportExistInSessionResponse }
     * 
     */
    public IsReportExistInSessionResponse createIsReportExistInSessionResponse() {
        return new IsReportExistInSessionResponse();
    }

    /**
     * Create an instance of {@link ParamNameValue }
     * 
     */
    public ParamNameValue createParamNameValue() {
        return new ParamNameValue();
    }

    /**
     * Create an instance of {@link GetFolderContentsInSessionResponse }
     * 
     */
    public GetFolderContentsInSessionResponse createGetFolderContentsInSessionResponse() {
        return new GetFolderContentsInSessionResponse();
    }

    /**
     * Create an instance of {@link ValidateLogin }
     * 
     */
    public ValidateLogin createValidateLogin() {
        return new ValidateLogin();
    }

    /**
     * Create an instance of {@link HasReportAccessInSession }
     * 
     */
    public HasReportAccessInSession createHasReportAccessInSession() {
        return new HasReportAccessInSession();
    }

    /**
     * Create an instance of {@link ArrayOfString }
     * 
     */
    public ArrayOfString createArrayOfString() {
        return new ArrayOfString();
    }

    /**
     * Create an instance of {@link ValidateLoginResponse }
     * 
     */
    public ValidateLoginResponse createValidateLoginResponse() {
        return new ValidateLoginResponse();
    }

    /**
     * Create an instance of {@link UploadReportDataChunk }
     * 
     */
    public UploadReportDataChunk createUploadReportDataChunk() {
        return new UploadReportDataChunk();
    }

    /**
     * Create an instance of {@link DownloadReportDataChunk }
     * 
     */
    public DownloadReportDataChunk createDownloadReportDataChunk() {
        return new DownloadReportDataChunk();
    }

    /**
     * Create an instance of {@link UpdateReportDefinitionInSessionResponse }
     * 
     */
    public UpdateReportDefinitionInSessionResponse createUpdateReportDefinitionInSessionResponse() {
        return new UpdateReportDefinitionInSessionResponse();
    }

    /**
     * Create an instance of {@link LogoutResponse }
     * 
     */
    public LogoutResponse createLogoutResponse() {
        return new LogoutResponse();
    }

    /**
     * Create an instance of {@link JobInfo }
     * 
     */
    public JobInfo createJobInfo() {
        return new JobInfo();
    }

    /**
     * Create an instance of {@link DownloadReportInSessionResponse }
     * 
     */
    public DownloadReportInSessionResponse createDownloadReportInSessionResponse() {
        return new DownloadReportInSessionResponse();
    }

    /**
     * Create an instance of {@link ResumeScheduledReport }
     * 
     */
    public ResumeScheduledReport createResumeScheduledReport() {
        return new ResumeScheduledReport();
    }

    /**
     * Create an instance of {@link CreateReportFolderInSessionResponse }
     * 
     */
    public CreateReportFolderInSessionResponse createCreateReportFolderInSessionResponse() {
        return new CreateReportFolderInSessionResponse();
    }

    /**
     * Create an instance of {@link UploadTemplateForReport }
     * 
     */
    public UploadTemplateForReport createUploadTemplateForReport() {
        return new UploadTemplateForReport();
    }

    /**
     * Create an instance of {@link GetReportParameters }
     * 
     */
    public GetReportParameters createGetReportParameters() {
        return new GetReportParameters();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccessDeniedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/oxp/service/PublicReportService", name = "fault")
    public JAXBElement<AccessDeniedException> createFault(AccessDeniedException value) {
        return new JAXBElement<AccessDeniedException>(_Fault_QNAME, AccessDeniedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidParametersException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/oxp/service/PublicReportService", name = "fault1")
    public JAXBElement<InvalidParametersException> createFault1(InvalidParametersException value) {
        return new JAXBElement<InvalidParametersException>(_Fault1_QNAME, InvalidParametersException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationFailedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/oxp/service/PublicReportService", name = "fault2")
    public JAXBElement<OperationFailedException> createFault2(OperationFailedException value) {
        return new JAXBElement<OperationFailedException>(_Fault2_QNAME, OperationFailedException.class, null, value);
    }

}
