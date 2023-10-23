/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.helper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import si.sodisce.skupni.lists.SCListsGeoRegistryAddressByIdRequest;
import si.sodisce.skupni.lists.SCListsGeoRegistryAddressByIdResponse;
import si.sodisce.skupni.lists.SCListsGetAttorneyListRequest;
import si.sodisce.skupni.lists.SCListsGetAttorneyListResponse;
import si.sodisce.skupni.lists.SCListsGetCountryListRequest;
import si.sodisce.skupni.lists.SCListsGetCountryListResponse;
import si.sodisce.skupni.lists.SCListsGetCourtCompetenceTypeListRequest;
import si.sodisce.skupni.lists.SCListsGetCourtCompetenceTypeListResponse;
import si.sodisce.skupni.lists.SCListsGetCourtDepartmentListRequest;
import si.sodisce.skupni.lists.SCListsGetCourtDepartmentListResponse;
import si.sodisce.skupni.lists.SCListsGetCourtListRequest;
import si.sodisce.skupni.lists.SCListsGetCourtListResponse;
import si.sodisce.skupni.lists.SCListsGetCurrencyListRequest;
import si.sodisce.skupni.lists.SCListsGetCurrencyListResponse;
import si.sodisce.skupni.lists.SCListsGetLegalFormListRequest;
import si.sodisce.skupni.lists.SCListsGetLegalFormListResponse;
import si.sodisce.skupni.lists.SCListsGetNotaryListRequest;
import si.sodisce.skupni.lists.SCListsGetNotaryListResponse;
import si.sodisce.skupni.lists.SCListsGetPostOfficeListRequest;
import si.sodisce.skupni.lists.SCListsGetPostOfficeListResponse;
import si.sodisce.skupni.lists.SCListsGetRegisterTypeListRequest;
import si.sodisce.skupni.lists.SCListsGetRegisterTypeListResponse;
import si.sodisce.skupni.lists.SCListsOrganisationsListBrwRequest;
import si.sodisce.skupni.lists.SCListsOrganisationsListBrwResponse;
import si.sodisce.skupni.lists.WsLists;
import si.vsrs.cif.ws.interfaces.JAXBUtility;

/**
 *
 * @author andrej
 */
public class WsListsMock implements WsLists {

    /* @Override
     public String courtAll(String request) throws WsSkupniException {
     try {
     StringBuilder sbuilder = null;
     BufferedReader input = null;
     InputStream in = WsListsMock.class.getResourceAsStream("wsList_courtAll.xml");
     input = new BufferedReader(new InputStreamReader(in, "UTF-8"));

     sbuilder = new StringBuilder();

     String str = input.readLine();

     while (str != null) {
     sbuilder.append(str);
     str = input.readLine();
     if (str != null) {

     sbuilder.append("\n");

     }
     }
     return sbuilder.toString();
     } catch (Exception ex) {
     throw new RuntimeException(ex);
     }
     }*/
    @Override
    public SCListsGetAttorneyListResponse getAttorneyList(SCListsGetAttorneyListRequest sclglr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SCListsGetCourtListResponse getCourtList(SCListsGetCourtListRequest s) {
        try {
            StringBuilder sbuilder = null;
            BufferedReader input = null;
            InputStream in = WsListsMock.class.getResourceAsStream("wsList_courtAll.xml");
            input = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            sbuilder = new StringBuilder();

            String str = input.readLine();

            while (str != null) {
                sbuilder.append(str);
                str = input.readLine();
                if (str != null) {

                    sbuilder.append("\n");
                }
            }
            return (SCListsGetCourtListResponse) JAXBUtility.StringToXml(sbuilder.toString(), SCListsGetCourtListResponse.class);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public SCListsGetCourtCompetenceTypeListResponse getCourtCompetenceTypeList(SCListsGetCourtCompetenceTypeListRequest s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SCListsGetCourtDepartmentListResponse getCourtDepartmentList(SCListsGetCourtDepartmentListRequest s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SCListsGetCurrencyListResponse getCurrencyList(SCListsGetCurrencyListRequest s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SCListsGetRegisterTypeListResponse getRegisterTypeList(SCListsGetRegisterTypeListRequest s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SCListsGeoRegistryAddressByIdResponse geoRegistryAddressById(SCListsGeoRegistryAddressByIdRequest s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SCListsGetLegalFormListResponse getLegalFormList(SCListsGetLegalFormListRequest s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SCListsGetNotaryListResponse getNotaryList(SCListsGetNotaryListRequest s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SCListsOrganisationsListBrwResponse organisationsListBrw(SCListsOrganisationsListBrwRequest sclbr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SCListsGetPostOfficeListResponse getPostOfficeList(SCListsGetPostOfficeListRequest s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SCListsGetCountryListResponse getCountryList(SCListsGetCountryListRequest s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
