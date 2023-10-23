
import java.sql.PreparedStatement;
import javax.sql.DataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Uporabnik
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/datasource.xml");
        DataSource dataSource = (DataSource) ctx.getBean("dataSource");
        String sql = "delete from opomba";
        PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql);
        stmt.execute();
        stmt.getConnection().close();
    }
}
