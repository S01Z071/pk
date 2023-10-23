/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.helper;

import si.sodisce.skupni.lists.SCListsGetCourtListRequest;
import si.sodisce.skupni.lists.SCListsGetCourtListResponse;
import si.sodisce.skupni.personnel.SCPersonnelAssignmentForUserRequest;
import si.sodisce.skupni.personnel.SCPersonnelAssignmentForUserResponse;
import si.sodisce.skupni.personnel.SCPersonnelAssistantForCourtRequest;
import si.sodisce.skupni.personnel.SCPersonnelAssistantForCourtResponse;
import si.sodisce.skupni.personnel.SCPersonnelForRoleRequest;
import si.sodisce.skupni.personnel.SCPersonnelForRoleResponse;
import si.sodisce.skupni.personnel.SCPersonnelGetByPersonnelIdRequest;
import si.sodisce.skupni.personnel.SCPersonnelGetByPersonnelIdResponse;
import si.sodisce.skupni.personnel.SCPersonnelGetByTaxIdRequest;
import si.sodisce.skupni.personnel.SCPersonnelGetByTaxIdResponse;
import si.sodisce.skupni.personnel.SCPersonnelJudgeForCourtRequest;
import si.sodisce.skupni.personnel.SCPersonnelJudgeForCourtResponse;
import si.sodisce.skupni.personnel.SCPersonnelRegistrarForCourtRequest;
import si.sodisce.skupni.personnel.SCPersonnelRegistrarForCourtResponse;
import si.sodisce.skupni.personnel.SCPersonnelRolesForPersonnelRequest;
import si.sodisce.skupni.personnel.SCPersonnelRolesForPersonnelResponse;
import si.sodisce.skupni.personnel.WsPersonnel;
import si.sodisce.skupni.personnel.co.Court;
import si.sodisce.skupni.personnel.co.Role;
import si.sodisce.skupni.personnel.co.User;
import si.sodisce.skupni.scrcontrol.SCRControl;

/**
 *
 * @author andrej
 */
public class WsPersonnelMock implements WsPersonnel {

    WsListsMock listsMock = new WsListsMock();

    @Override
    public SCPersonnelJudgeForCourtResponse judgeForCourt(SCPersonnelJudgeForCourtRequest s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SCPersonnelAssistantForCourtResponse assistantForCourt(SCPersonnelAssistantForCourtRequest scpfcr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SCPersonnelAssignmentForUserResponse assignmentForUser(SCPersonnelAssignmentForUserRequest scpfr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SCPersonnelRegistrarForCourtResponse registrarForCourt(SCPersonnelRegistrarForCourtRequest s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SCPersonnelForRoleResponse personnelForRole(SCPersonnelForRoleRequest scpfrr) {
        SCPersonnelForRoleResponse response = new SCPersonnelForRoleResponse();
        response.setSCRControl(new SCRControl());
        response.setRData(new SCPersonnelForRoleResponse.RData());
        response.getRData().setUsers(new SCPersonnelForRoleResponse.RData.Users());
        User user = new User();
        user.setFirstName("Marko");
        user.setLastName("Oman");
        user.setPersonnelId("S011274");
        user.setRoles(new User.Roles());
        Role role = new Role();
        role.setRoleCode("Test");
        role.setRoleDesc("Testni opis role");
        user.getRoles().getRole().add(role);
        //...
        response.getRData().getUsers().getUser().add(user);
        return response;
    }

    @Override
    public SCPersonnelRolesForPersonnelResponse rolesForPersonnel(SCPersonnelRolesForPersonnelRequest s) {

        String kadrovska = s.getData().getPersonnelId();
        SCPersonnelRolesForPersonnelResponse response = new SCPersonnelRolesForPersonnelResponse();
        response.setSCRControl(new SCRControl());
        response.getSCRControl().setReturnValue(1);
        response.setRData(new SCPersonnelRolesForPersonnelResponse.RData());
        User user = new User();
        if (kadrovska.equals("T200001")) {
            response.setRData(null);
            return response;
        }

        if (kadrovska.equals("T200002")) {
            user = getUser("Ime t200002", "Priimek t200002", "T200002");
            user.getRoles().getRole().add(getUserRole("001", new String[]{"S23"}));
        }
        if (kadrovska.equals("T200003")) {
            user = getUser("Ime t200003", "Priimek t200003", "T200003");
            user.getRoles().getRole().add(getUserRole("001", new String[]{"S23"}));
            user.getRoles().getRole().add(getUserRole("002", new String[]{"S23"}));
            user.getRoles().getRole().add(getUserRole("003", new String[]{"S01"}));
            user.getRoles().getRole().add(getUserRole("004", new String[]{"S01"}));
            user.getRoles().getRole().add(getUserRole("005", new String[]{"S01"}));
            user.getRoles().getRole().add(getUserRole("006", new String[]{"S01"}));

        }
        if (kadrovska.equals("T200004")) {
            user = getUser("Ime t200004", "Priimek t200004", "T200004");
            user.getRoles().getRole().add(getUserRole("001", new String[]{"S23", "S24", "S25"}));

        }
        if (kadrovska.equals("T200005")) {
            user = getUser("Ime t200005", "Priimek t200005", "T200005");
            user.getRoles().getRole().add(getUserRole("001", new String[]{"S23", "S03", "S43"}));
        }
        if (kadrovska.equals("S01Z027")) {
            user = getUser("Ime S01Z027", "Priimek S01Z027", "S01Z027");
            user.getRoles().getRole().add(getUserRole("001", new String[]{"S23"}));
            user.getRoles().getRole().add(getUserRole("002", new String[]{"S23"}));
        }
        if (kadrovska.equals("S011274")) {
            user = getUser("Ime S011274", "Priimek S011274", "S011274");
            user.getRoles().getRole().add(getUserRole("001", new String[]{"S23"}));
            user.getRoles().getRole().add(getUserRole("002", new String[]{"S23"}));
        }
        if (kadrovska.equals("T300001")) {
            user = getUser("Ime T300001", "Priimek T300001", "T300001");
            user.getRoles().getRole().add(getUserRole("001", new String[]{"S23"}));
            user.getRoles().getRole().add(getUserRole("002", new String[]{"S23"}));
        }

        response.getRData().setUser(user);

        return response;

    }

    @Override
    public SCPersonnelGetByTaxIdResponse getByTaxId(SCPersonnelGetByTaxIdRequest s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SCPersonnelGetByPersonnelIdResponse getByPersonnelId(SCPersonnelGetByPersonnelIdRequest s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private User getUser(String firstName, String lastName, String personnelId) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPersonnelId(personnelId);
        user.setRoles(new User.Roles());
        return user;
    }

    private Role getUserRole(String roleCode, String[] courtCodes) {
        Role role = new Role();
        role.setRoleCode(roleCode);
        role.setCourts(new Role.Courts());

        for (String s : courtCodes) {
            Court court = new Court();
            court.setCourtCode(s);
            court.setShortName(getCourtShortName(s));
            role.getCourts().getCourt().add(court);
        }
        return role;
    }

    private String getCourtShortName(String courtCode) {
        SCListsGetCourtListRequest request = new SCListsGetCourtListRequest();
        request.setData(new SCListsGetCourtListRequest.Data());
        SCListsGetCourtListResponse response = listsMock.getCourtList(new SCListsGetCourtListRequest());
        for (si.sodisce.skupni.lists.co.Court c : response.getRData().getCourts()) {
            if (c.getCode().equalsIgnoreCase(courtCode)) {

                return c.getShortName();
            }
        }
        return "";
    }
}
