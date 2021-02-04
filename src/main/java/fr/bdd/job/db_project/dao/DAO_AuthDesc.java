package fr.bdd.job.db_project.dao;

import fr.bdd.custom.sql.PreparedStatementAware;
import fr.bdd.job.dao.Dao;
import fr.bdd.job.db_project.jobclass.AuthDesc;
import fr.bdd.log.generate.CustomLogger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO pattern class.
 * Used for the job class {@link AuthDesc}
 *
 * @author Gaetan Brenckle
 */
public class DAO_AuthDesc implements Dao<AuthDesc> {

    private static final CustomLogger LOGGER = CustomLogger.create(DAO_AuthDesc.class.getName());
    private Connection connectionHandle_ = null;

    /**
     * Default constructor.
     * Need to call {@link DAO_AuthDesc#setConnection(Connection)} before any other function.
     *
     * @author Gaetan Brenckle
     */
    public DAO_AuthDesc() {
    }

    /**
     * Constructor with the Connection argument.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link Connection} - connection used.
     */
    public DAO_AuthDesc(Connection conn)  {
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
     * @return - {@link AuthDesc} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
*/
    public final AuthDesc select(Integer id) throws SQLException {
        AuthDesc retAuthDesc = null;
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Person dao_person_ID = new DAO_Person(this.connectionHandle_);
        DAO_Language dao_language_ID = new DAO_Language(this.connectionHandle_);

        if (id == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("AuthDesc select when the given id is null");
            }
            return retAuthDesc;
        }

        final String select_sql = String.format("%s %s %s",
                String.format("SELECT %s, %s, %s, %s", "document_ID", "person_ID", "language_ID", "dateNotice"),
                String.format("FROM %s ", "AuthDesc"),
                String.format("WHERE id = ?"));

        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.prepareStatement(select_sql));
        prepSelect.setInt(id);

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            if (resultSelect.next()) {
                retAuthDesc =
                        new AuthDesc()
                                .setdocument_ID(dao_document.select(resultSelect.getString("document_ID")))
                                .setperson_ID(dao_person_ID.select(resultSelect.getInt("person_ID")))
                                .setlanguage_ID(dao_language_ID.select(resultSelect.getString("language_ID")))
                                .setdateNotice(resultSelect.getDate("dateNotice"));

            }
        }
        return retAuthDesc;
    }

    /**
     * SELECT with the index of the associate job class.
     *
     * @author Gaetan Brenckle
     *
     * @param map - {@link HashMap<String, String>} - index of the associate job class. Can handle null.
     * @return - {@link AuthDesc} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    public final List<AuthDesc> selectByMultiCondition(HashMap<String, String> map) throws SQLException {
        final List<AuthDesc> retCategories = new ArrayList<>();
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Person dao_person_ID = new DAO_Person(this.connectionHandle_);
        DAO_Language dao_language_ID = new DAO_Language(this.connectionHandle_);

        if (map == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("AuthDesc select when the given id is null");
            }
            return retCategories;
        }


        StringBuilder select_sql = new StringBuilder(String.format("%s %s %s",
                String.format("SELECT %s, %s, %s, %s", "document_ID", "person_ID", "language_ID", "dateNotice"),
                String.format("FROM %s ", "AuthDesc"),
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
                AuthDesc AuthDesc =
                        new AuthDesc()
                                .setdocument_ID(dao_document.select(resultSelect.getString("document_ID")))
                                .setperson_ID(dao_person_ID.select(resultSelect.getInt("person_ID")))
                                .setlanguage_ID(dao_language_ID.select(resultSelect.getString("language_ID")))
                                .setdateNotice(resultSelect.getDate("dateNotice"));
                retCategories.add(AuthDesc);
            }
        }
        return retCategories;
    }

    /**
     * SELECT of all occurance of the AuthDesc class.
     *
     * @author Gaetan Brenckle
     *
     * @return - {@link List} - a list that contain all occurance of {@link AuthDesc}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
    */
    @Override
    public List<AuthDesc> selectAll() throws SQLException {
        final List<AuthDesc> retCategories = new ArrayList<>();
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Person dao_person_ID = new DAO_Person(this.connectionHandle_);
        DAO_Language dao_language_ID = new DAO_Language(this.connectionHandle_);

        String format = String.format("%s %s",
                String.format("SELECT %s, %s, %s, %s", "document_ID", "person_ID", "language_ID", "dateNotice"),
                String.format("FROM %s ", "AuthDesc"));
        final String selectAll_sql = format;

        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(connectionHandle_.prepareStatement(selectAll_sql));

        try(final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
            while (resultSelectAll.next()) {
                AuthDesc AuthDesc =
                        new AuthDesc()
                                .setdocument_ID(dao_document.select(resultSelectAll.getString("document_ID")))
                                .setperson_ID(dao_person_ID.select(resultSelectAll.getInt("person_ID")))
                                .setlanguage_ID(dao_language_ID.select(resultSelectAll.getString("language_ID")))
                                .setdateNotice(resultSelectAll.getDate("dateNotice"));
                retCategories.add(AuthDesc);
            }
        }

        return retCategories;
    }

    @Override
    public List<AuthDesc> selectAll(List<AuthDesc> excludeList) throws SQLException {
        return null;
    }

    /**
     * INSERT the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link AuthDesc} - insert the job class.
     * @return - boolean - the state of the sql insert.
     * @throws SQLException - throw the exception to force a try catch when used.
*/
    @Override
    public boolean insert(AuthDesc obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "AuthDesc"));
            }
            return false;
        }

        if (obj.getdocument_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "document_ID"));
            }
            retBool = false;
        }

        if (obj.getperson_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "person_ID"));
            }
            retBool = false;
        }

        if (obj.getlanguage_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "language_ID"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String insert_sql = String.format("%s %s ",
                String.format("INSERT INTO %s (%s, %s, %s, %s)", "AuthDesc", "document_ID", "person_ID", "language_ID", "dateNotice"),
                "VALUES (?, ?, ?, ?)");

        final PreparedStatementAware prepInsert = new PreparedStatementAware(connectionHandle_.prepareStatement(insert_sql));
        prepInsert.setString(obj.getdocument_ID().getdocument_ID());
        prepInsert.setInt(obj.getperson_ID().getperson_ID());
        prepInsert.setString(obj.getlanguage_ID().getlanguage_ID());
        prepInsert.setDate((Date) obj.getdateNotice());

        retBool = prepInsert.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Insert a new %s : [%s]", "AuthDesc", prepInsert.printSqlStatement(insert_sql));

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
     * @param obj - {@link AuthDesc} - insert the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
*/
    @Override
    public boolean update(AuthDesc obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "AuthDesc"));
            }
            return false;
        }

        if (obj.getdocument_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "document_ID"));
            }
            retBool = false;
        }

        if (obj.getperson_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "person_ID"));
            }
            retBool = false;
        }

        if (obj.getlanguage_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "language_ID"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String update_sql = String.format("%s %s %s %s %s %s ",
                String.format("UPDATE %s", "AuthDesc"),
                String.format("SET %s = ?", "document_ID"),
                String.format("SET %s = ?", "person_ID"),
                String.format("SET %s = ?", "language_ID"),
                String.format("SET %s = ?", "dateNotice"),
                String.format("WHERE %s = ?", "document_ID"));

        final PreparedStatementAware prepUpdate = new PreparedStatementAware(connectionHandle_.prepareStatement(update_sql));
        prepUpdate.setString(obj.getdocument_ID().getdocument_ID());
        prepUpdate.setInt(obj.getperson_ID().getperson_ID());
        prepUpdate.setString(obj.getlanguage_ID().getlanguage_ID());
        prepUpdate.setDate((Date) obj.getdateNotice());

        retBool = prepUpdate.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Update a new %s : [%s]", "AuthDesc", prepUpdate.printSqlStatement(update_sql));

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
     * @param obj - {@link AuthDesc} - insert the job class.
     * @return - boolean - the state of the sql delete.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean delete(AuthDesc obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "AuthDesc"));
            }
            return false;
        }

        if (obj.getdocument_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "document_ID"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String delete_sql = String.format("%s %s ",
                String.format("DELETE FROM %s", "AuthDesc"),
                String.format("WHERE %s = ?", "document_ID"));

        final PreparedStatementAware prepDelete = new PreparedStatementAware(connectionHandle_.prepareStatement(delete_sql));
        prepDelete.setString(obj.getdocument_ID().getdocument_ID());

        retBool = prepDelete.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Delete a new %s : [%s]", "AuthDesc", prepDelete.printSqlStatement(delete_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }

        return retBool;
    }
}
