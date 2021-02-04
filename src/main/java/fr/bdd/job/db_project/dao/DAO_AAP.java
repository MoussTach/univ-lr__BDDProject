package fr.bdd.job.db_project.dao;

import fr.bdd.custom.sql.PreparedStatementAware;
import fr.bdd.job.dao.Dao;
import fr.bdd.job.db_project.jobclass.AAP;
import fr.bdd.log.generate.CustomLogger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO pattern class.
 * Used for the job class {@link AAP}
 *
 * @author Gaetan Brenckle
 */
public class DAO_AAP implements Dao<AAP> {

    private static final CustomLogger LOGGER = CustomLogger.create(DAO_AAP.class.getName());
    private Connection connectionHandle_ = null;

    /**
     * Default constructor.
     * Need to call {@link DAO_AAP#setConnection(Connection)} before any other function.
     *
     * @author Gaetan Brenckle
     */
    public DAO_AAP() {
    }

    /**
     * Constructor with the Connection argument.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link Connection} - connection used.
     */
    public DAO_AAP(Connection conn)  {
        this.connectionHandle_ = conn;
    }

    /**
     * Setter of the connection.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link Connection} - Connection used.
     */
    @Override
    public void setConnection(Connection conn)  {
        this.connectionHandle_ = conn;
    }

    /**
     * SELECT with the index of the associate job class.
     *
     * @author Gaetan Brenckle
     *
     * @param id - {@link Integer} - index of the associate job class. Can handle null.
     * @return - {@link AAP} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
*/
    public final AAP select(Integer id) throws SQLException {
        AAP retAAP = null;
        DAO_Person dao_responsable = new DAO_Person(this.connectionHandle_);
        DAO_Person dao_student = new DAO_Person(this.connectionHandle_);

        if (id == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("AAP select when the given id is null");
            }
            return retAAP;
        }

        final String select_sql = String.format("%s %s %s",
                String.format("SELECT %s, %s, %s, %s", "AAP_ID", "responsable", "student", "edition"),
                String.format("FROM %s ", "AAP"),
                String.format("WHERE id = ?"));

        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.prepareStatement(select_sql));
        prepSelect.setInt(id);

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            if (resultSelect.next()) {
                retAAP =
                        new AAP()
                                .setAAP_ID(resultSelect.getInt("AAP_ID"))
                                .setresponsable(dao_responsable.select(resultSelect.getInt("responsable")))
                                .setstudent(dao_student.select(resultSelect.getInt("student")))
                                .setedition(resultSelect.getString("edition"));

            }
        }
        return retAAP;
    }

    /**
     * SELECT with the index of the associate job class.
     *
     * @author Gaetan Brenckle
     *
     * @param map - {@link HashMap<String, String>} - index of the associate job class. Can handle null.
     * @return - {@link AAP} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    public final List<AAP> selectByMultiCondition(HashMap<String, String> map) throws SQLException {
        final List<AAP> retCategories = new ArrayList<>();
        DAO_Person dao_responsable = new DAO_Person(this.connectionHandle_);
        DAO_Person dao_student = new DAO_Person(this.connectionHandle_);

        if (map == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("AAP select when the given id is null");
            }
            return retCategories;
        }


        StringBuilder select_sql = new StringBuilder(String.format("%s %s %s",
                String.format("SELECT %s, %s, %s, %s", "AAP_ID", "responsable", "student", "edition"),
                String.format("FROM %s ", "AAP"),
                String.format("WHERE 1=1")
        ));

        for (Map.Entry<String,String> entry : map.entrySet()) {
            select_sql.append(String.format(" %s ", entry.getKey()));
        }


        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.prepareStatement(select_sql.toString()));
        for (Map.Entry<String,String> entry : map.entrySet()) {
            prepSelect.setString(entry.getValue());
        }

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            while (resultSelect.next()) {
                AAP AAP =
                        new AAP()
                                .setAAP_ID(resultSelect.getInt("AAP_ID"))
                                .setresponsable(dao_responsable.select(resultSelect.getInt("responsable")))
                                .setstudent(dao_student.select(resultSelect.getInt("student")))
                                .setedition(resultSelect.getString("edition"));
                retCategories.add(AAP);
            }
        }
        return retCategories;
    }

    /**
     * SELECT of all occurance of the AAP class.
     *
     * @author Gaetan Brenckle
     *
     * @return - {@link List} - a list that contain all occurance of {@link AAP}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
*/
    @Override
    public List<AAP> selectAll() throws SQLException {
        final List<AAP> retCategories = new ArrayList<>();
        DAO_Person dao_responsable = new DAO_Person(this.connectionHandle_);
        DAO_Person dao_student = new DAO_Person(this.connectionHandle_);

        String format = String.format("%s %s",
                String.format("SELECT %s, %s, %s, %s", "AAP_ID", "responsable", "student", "edition"),
                String.format("FROM %s ", "AAP"));
        final String selectAll_sql = format;

        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(connectionHandle_.prepareStatement(selectAll_sql));


        try(final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
            while (resultSelectAll.next()) {
                AAP AAP =
                        new AAP()
                                .setAAP_ID(resultSelectAll.getInt("AAP_ID"))
                                .setresponsable(dao_responsable.select(resultSelectAll.getInt("responsable")))
                                .setstudent(dao_student.select(resultSelectAll.getInt("student")))
                                .setedition(resultSelectAll.getString("edition"));
                retCategories.add(AAP);
            }
        }

        return retCategories;
    }

    @Override
    public List<AAP> selectAll(List<AAP> excludeList) throws SQLException {
        return null;
    }

    /**
     * INSERT the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link AAP} - insert the job class.
     * @return - boolean - the state of the sql insert.
     * @throws SQLException - throw the exception to force a try catch when used.
*/
    @Override
    public boolean insert(AAP obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "AAP"));
            }
            return false;
        }

        if (obj.getAAP_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "AAP_ID"));
            }
            retBool = false;
        }

        if (obj.getresponsable() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "responsable"));
            }
            retBool = false;
        }

        if (obj.getstudent() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "student"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String insert_sql = String.format("%s %s ",
                String.format("INSERT INTO %s (%s, %s, %s, %s)", "AAP", "AAP_ID", "responsable", "student", "edition"),
                "VALUES (?, ?, ?, ?)");

        final PreparedStatementAware prepInsert = new PreparedStatementAware(connectionHandle_.prepareStatement(insert_sql));
        prepInsert.setInt(obj.getAAP_ID());
        prepInsert.setInt(obj.getresponsable().getperson_ID());
        prepInsert.setInt(obj.getstudent().getperson_ID());
        prepInsert.setString(obj.getedition());

        retBool = prepInsert.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Insert a new %s : [%s]", "AAP", prepInsert.printSqlStatement(insert_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }
        return retBool;
    }

    /**
     * UPDATE the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link AAP} - insert the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
*/
    @Override
    public boolean update(AAP obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "AAP"));
            }
            return false;
        }

        if (obj.getAAP_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "AAP_ID"));
            }
            retBool = false;
        }

        if (obj.getresponsable() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "responsable"));
            }
            retBool = false;
        }

        if (obj.getstudent() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "student"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String update_sql = String.format("%s %s %s %s %s %s ",
                String.format("UPDATE %s", "AAP"),
                String.format("SET %s = ?", "AAP_ID"),
                String.format("SET %s = ?", "responsable"),
                String.format("SET %s = ?", "student"),
                String.format("SET %s = ?", "edition"),
                String.format("WHERE %s = ?", "AAP_ID"));

        final PreparedStatementAware prepUpdate = new PreparedStatementAware(connectionHandle_.prepareStatement(update_sql));
        prepUpdate.setInt(obj.getAAP_ID());
        prepUpdate.setInt(obj.getresponsable().getperson_ID());
        prepUpdate.setInt(obj.getstudent().getperson_ID());
        prepUpdate.setString(obj.getedition());

        retBool = prepUpdate.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Update a new %s : [%s]", "AAP", prepUpdate.printSqlStatement(update_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }
        return retBool;
    }


    /**
     * DELETE the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link AAP} - insert the job class.
     * @return - boolean - the state of the sql delete.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean delete(AAP obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "AAP"));
            }
            return false;
        }

        if (obj.getAAP_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "AAP_ID"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String delete_sql = String.format("%s %s ",
                String.format("DELETE FROM %s", "AAP"),
                String.format("WHERE %s = ?", "AAP_ID"));

        final PreparedStatementAware prepDelete = new PreparedStatementAware(connectionHandle_.prepareStatement(delete_sql));
        prepDelete.setInt(obj.getAAP_ID());

        retBool = prepDelete.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Delete a new %s : [%s]", "AAP", prepDelete.printSqlStatement(delete_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }

        return retBool;
    }
}
