/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.helper;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import si.sodisce.skupni.lists.SCListsGetCourtListRequest;
import si.sodisce.skupni.lists.SCListsGetCourtListResponse;
import si.sodisce.skupni.lists.WsLists;
import si.sodisce.skupni.lists.co.Court;
import si.sodisce.skupni.personnel.WsPersonnel;

/**
 *
 * @author andrej
 */
@Service
public class SkupniServisiHelper {

    @Autowired
    private WsPersonnel wsPersonnel;
    @Autowired
    private WsLists wsLists;

    public WsPersonnel getWsPersonnel() {
        return wsPersonnel;
    }

    public void setWsPersonnel(WsPersonnel wsPersonnel) {
        this.wsPersonnel = wsPersonnel;
    }

    public WsLists getWsLists() {
        return wsLists;
    }

    public void setWsLists(WsLists wsLists) {
        this.wsLists = wsLists;
    }

    public List<Court> getSodisca() {
        SCListsGetCourtListRequest request = new SCListsGetCourtListRequest();
        request.setData(new SCListsGetCourtListRequest.Data());
        SCListsGetCourtListResponse response = wsLists.getCourtList(request);
        List<Court> courts = response.getRData().getCourts();
        return courts;    
    }

    //vrne vse podatke o dolocenem sodiscu - po sifri sodisca
    @Cacheable("sodisce")
    public Court getSodisceData(String sifra) {
        List<Court> sodisca = getSodisca();
        for (int i = 0; i < sodisca.size(); i++) {
            if (sodisca.get(i).getCode().compareTo(sifra) == 0) {
                return sodisca.get(i);
            }
        }
        return new Court();
    }
}
