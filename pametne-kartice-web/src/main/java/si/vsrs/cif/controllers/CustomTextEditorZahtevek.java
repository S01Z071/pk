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
public class CustomTextEditorZahtevek extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        if (text == null || text.length()<1) {
            setValue("");
        } else {
            String noviText = text.replaceAll("\\s+", " ");
            noviText = noviText.substring(0, 1).toUpperCase() + noviText.substring(1, noviText.length());
            setValue(noviText.trim());

        }
    }
}
