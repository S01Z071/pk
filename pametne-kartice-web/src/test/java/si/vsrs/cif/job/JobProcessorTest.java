/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.job;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.batch.core.StepExecution;
import si.vsrs.cif.mod.Certifikat;

/**
 *
 * @author Uporabnik
 */
public class JobProcessorTest {
    
    public JobProcessorTest() {
    }
    
 
  
    @Test
    public void testisNullOrEmpty() throws Exception {
        JobProcessor jobProcessor = new JobProcessor();
        assertTrue(jobProcessor.isNullOrEmpty(null));
        assertTrue(jobProcessor.isNullOrEmpty(""));
        assertFalse(jobProcessor.isNullOrEmpty("x"));
        assertFalse(jobProcessor.isNullOrEmpty("1"));
        assertFalse(jobProcessor.isNullOrEmpty("1534"));
        assertFalse(jobProcessor.isNullOrEmpty("sdfsdf"));
    }
    
    @Test
    public void testGetSerialNumberXWithoutPrefixesInUpperCase00()throws Exception{
        JobProcessor jobProcessor = new JobProcessor();
        String result = jobProcessor.getSerialNumberXWithoutPrefixesInUpperCase("00ac97a664");
        assertEquals("AC97A664", result);
    }
    
    @Test
    public void testGetSerialNumberXWithoutPrefixesInUpperCase0XUpper()throws Exception{
        JobProcessor jobProcessor = new JobProcessor();
        String result = jobProcessor.getSerialNumberXWithoutPrefixesInUpperCase("0xac97a664");
        assertEquals("AC97A664", result);
    }
    
    @Test
    public void testGetSerialNumberXWithoutPrefixesInUpperCase0XLower()throws Exception{
        JobProcessor jobProcessor = new JobProcessor();
        String result = jobProcessor.getSerialNumberXWithoutPrefixesInUpperCase("0xac97a664");
        assertEquals("AC97A664", result);
    }
    
    @Test
    public void testGetSerialNumberXWithoutPrefixesInUpperCaseOkButLower()throws Exception{
        JobProcessor jobProcessor = new JobProcessor();
        String result = jobProcessor.getSerialNumberXWithoutPrefixesInUpperCase("ac97a664");
        assertEquals("AC97A664", result);
    }
    
    @Test
    public void testGetSerialNumberXWithoutPrefixesInUpperCaseOkTheSame()throws Exception{
        JobProcessor jobProcessor = new JobProcessor();
        String result = jobProcessor.getSerialNumberXWithoutPrefixesInUpperCase("AC97A664");
        assertEquals("AC97A664", result);
    }

   
}