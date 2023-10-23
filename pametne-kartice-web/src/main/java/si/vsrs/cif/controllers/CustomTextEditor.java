/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.controllers;

import java.beans.PropertyEditorSupport;

/**
 *
 * @author uporabnik
 */
public class CustomTextEditor extends PropertyEditorSupport {

    boolean eNaslov;

    public CustomTextEditor(boolean eNaslov) {
        this.eNaslov = eNaslov;
    }

    @Override
    public void setAsText(String text) {
        if (text == null || text.length()<1) {
            setValue("");
        } else {            
            if (eNaslov) {
                setValue(text.trim().toLowerCase());
            } else {
                String[] tabela = text.split(" ");
                String noviText = "";
                for (int i = 0; i < tabela.length; i++) {
                    if (tabela[i].length() > 1) {
                        noviText += tabela[i].substring(0, 1).toUpperCase() + tabela[i].substring(1, tabela[i].length()).toLowerCase() + " ";
                    }
                    if (tabela[i].length() == 1) {
                        noviText += tabela[i].substring(0, 1).toUpperCase() + " ";
                    }
                }
                setValue(noviText.trim());
            }
        }
    }
}
