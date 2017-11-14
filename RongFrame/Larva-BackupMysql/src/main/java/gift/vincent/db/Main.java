package gift.vincent.db;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by sxjun on 15-9-16.
 */
public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        DBTableCopy bean = context.getBean("dbCopy", DBTableCopy.class);

        bean.copy();

    }

}
