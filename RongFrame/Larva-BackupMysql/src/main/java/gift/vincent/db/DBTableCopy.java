package gift.vincent.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sxjun on 15-9-16.
 */
@Component("dbCopy")
public class DBTableCopy {
    @Resource
    private DruidDataSource dataSource;
    @Value("${tbName}")
    private String tbName;
    @Value("${copy_url}")
    private String copyUrl;
    @Value("${copy_period}")
    private long copyPeriod;

    public List<String> getSqlList() throws SQLException {
        List<String> sqlList = new ArrayList<>();
        DruidPooledConnection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = dataSource.tryGetConnection();
            ps = connection.prepareStatement("SELECT * FROM " + tbName);
            rs = ps.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String insersql = "INSERT INTO " + tbName + " VALUES(";
            for (int i = 0; i < columnCount; i++) {
                insersql += "?,";
            }
            insersql = insersql.substring(0, insersql.length() - 1);
            insersql += ");";
            while (rs.next()) {
                String dbSql = insersql;
                for (int i = 0; i < columnCount; i++) {
                    Object object = rs.getObject(i + 1);
                    dbSql = dbSql.replaceFirst("\\?", object == null ? "null" : "\'" + object.toString() + "\'");
                }
                sqlList.add(dbSql);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            dataSource.discardConnection(connection);
        }
        return sqlList;

    }

    public void copy() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    List<String> sqlList = getSqlList();
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                    String dataFormat = format.format(new Date());
                    File file = new File(copyUrl + tbName + "-" + dataFormat + ".sql");
                    Writer writer = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(writer);
                    for (String sql : sqlList) {
                        bw.write(sql);
                        bw.newLine();
                    }
                    bw.close();
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        new Timer().scheduleAtFixedRate(timerTask, 0, copyPeriod);
    }
}
