/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.common;

import java.io.Serializable;
import java.util.Formatter;

/**
 *
 * @author andrej
 */
public abstract class BaseModel implements Serializable {

    public abstract String getPrintSimple();

    public abstract String getPrintInLine();

    public String getPrintHTML() {
        return getPrintSimple().replaceAll(" ", "&nbsp;").replaceAll("\n", "<br/>");
    }

    public String getPrintHTMLInLine() {
        String tableStart = "";
        String tableEnd = "";
        String niz = getPrintInLine();
        if (niz.matches(".*\\|.*")) {
            tableStart = "<tr><td>";
            tableEnd = "</td></tr>";
        }
        return tableStart + niz.replaceAll(" ", "&nbsp;").replaceAll("\n", "<br/>").replaceAll("\\|", "<td/><td>") + tableEnd;
    }

    public String getPrintInLineHeader() {
        return "";
    }

    public String getPrintHTMLInLineHeader() {
        String tableStart = "";
        String tableEnd = "";
        String niz = getPrintInLineHeader();
        if (niz.matches(".*\\|.*")) {
            tableStart = "<tr><td>";
            tableEnd = "</td></tr>";
        }
        return tableStart + niz.replaceAll(" ", "&nbsp;").replaceAll("\n", "<br/>").replaceAll("\\|", "<td/><td>") + tableEnd;
    }
}
