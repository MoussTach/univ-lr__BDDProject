package fr.bdd.job.db_project.dao;

import fr.bdd.custom.sql.PreparedStatementAware;
import fr.bdd.job.dao.Dao;
import fr.bdd.job.db_project.jobclass.AuthAnalyst;
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
 * Used for the job class {@link AuthAnalyst}
 *
 * @author Gaetan Brenckle
 */
public class DAO_AuthAnalyst implements Dao<AuthAnalyst> {

    private static final CustomLogger LOGGER = CustomLogger.create(DAO_AuthAnalyst.class.getName());
    private Connection connectionHandle_ = null;

    /**
     * Default constructor.
     * Need to call {@link DAO_AuthAnalyst#setConnection(Connection)} before any other function.
     *
     * @author Gaetan Brenckle
     */
    public DAO_AuthAnalyst() {
    }

    /**
     * Constructor with the Connection argument.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link Connection} - connection used.
     */
    public DAO_AuthAnalyst(Connection conn) {
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
    public void setConnection(Connection conn) {
        this.connectionHandle_ = conn;
    }



    /**
     * SELECT with the index of the associate job class.
     *
     * @author Gaetan Brenckle
     *
     * @param id - {@link String} - index of the associate job class. Can handle null.
     * @return - {@link AuthAnalyst} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    public final AuthAnalyst select(String id) throws SQLException {
        AuthAnalyst retAuthAnalyst = null;
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Person dao_Person = new DAO_Person(this.connectionHandle_);

        if (id == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("AuthAnalyst select when the given id is null");
            }
            return retAuthAnalyst;
        }

        final String select_sql = String.format("%s %s %s",
                String.format("SELECT %s, %s, %s", "document_ID", "person_ID", "date"),
                String.format("FROM %s ", "AuthAnalyst"),
                String.format("WHERE id = ?"));

        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.prepareStatement(select_sql));
        prepSelect.setString(id);

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            if (resultSelect.next()) {
                retAuthAnalyst =
                        new AuthAnalyst()
                                .setdocument_ID(dao_document.select(resultSelect.getString("document_ID")))
                                .setperson_ID(dao_Person.select(resultSelect.getInt("person_ID")))
                                .setdate(resultSelect.getDate("date"));

            }
        }
        return retAuthAnalyst;
    }

    /**
     * SELECT with the index of the associate job class.
     *
     * @author Gaetan Brenckle
     *
     * @param map - {@link HashMap<String, String>} - index of the associate job class. Can handle null.
     * @return - {@link AuthAnalyst} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    public final List<AuthAnalyst> selectByMultiCondition(HashMap<String, String> map) throws SQLException {
        final List<AuthAnalyst> retCategories = new ArrayList<>();
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Person dao_Person = new DAO_Person(this.connectionHandle_);

        if (map == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("AuthAnalyst select when the given id is null");
            }
            return retCategories;
        }


        StringBuilder select_sql = new StringBuilder(String.format("%s %s %s",
                String.format("SELECT %s, %s, %s", "document_ID", "person_ID", "date"),
                String.format("FROM %s ", "AuthAnalyst"),
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
                AuthAnalyst AuthAnalyst =
                        new AuthAnalyst()
                                        .setdocument_ID(dao_document.select(resultSelect.getString("document_ID")))
                                        .setperson_ID(dao_Person.select(resultSelect.getInt("person_ID")))
                                        .setdate(resultSelect.getDate("date"));
                retCategories.add(AuthAnalyst);
            }
        }
        return retCategories;
    }

    /**
     * SELECT of all occurance of the AuthAnalyst class.
     *
     * @author Gaetan Brenckle
     *
     * @return - {@link List} - a list that contain all occurance of {@link AuthAnalyst}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public List<AuthAnalyst> selectAll() throws SQLException {
        final List<AuthAnalyst> retCategories = new ArrayList<>();
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Person dao_Person = new DAO_Person(this.connectionHandle_);

        String format = String.format("%s %s",
                String.format("SELECT %s, %s, %s", "document_ID", "person_ID", "date"),
                String.format("FROM %s ", "AuthAnalyst"));
        final String selectAll_sql = format;

        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(connectionHandle_.prepareStatement(selectAll_sql));


        try(final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
            while (resultSelectAll.next()) {
                AuthAnalyst AuthAnalyst =
                        new AuthAnalyst()
                                .setdocument_ID(dao_document.select(resultSelectAll.getString("document_ID")))
                                .setperson_ID(dao_Person.select(resultSelectAll.getInt("person_ID")))
                                .setdate(resultSelectAll.getDate("date"));
                retCategories.add(AuthAnalyst);
            }
        }

        return retCategories;
    }

    @Override
    public List<AuthAnalyst> selectAll(List<AuthAnalyst> excludeList) throws SQLException {
        return null;
    }

    /**
     * INSERT the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link AuthAnalyst} - insert the job class.
     * @return - boolean - the state of the sql insert.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean insert(AuthAnalyst obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "AuthAnalyst"));
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

        if (obj.getdate() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "date"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String insert_sql = String.format("%s %s ",
                String.format("INSERT INTO %s (%s, %s, %s)", "AuthAnalyst", "document_ID", "person_ID", "date"),
                "VALUES (?, ?, ?)");

        final PreparedStatementAware prepInsert = new PreparedStatementAware(connectionHandle_.prepareStatement(insert_sql));
        prepInsert.setString(obj.getdocument_ID().getdocument_ID());
        prepInsert.setInt(obj.getperson_ID().getperson_ID());
        prepInsert.setDate((Date) obj.getdate());

        retBool = prepInsert.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Insert a new %s : [%s]", "AuthAnalyst", prepInsert.printSqlStatement(insert_sql));

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
     * @param obj - {@link AuthAnalyst} - insert the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean update(AuthAnalyst obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "AuthAnalyst"));
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

        if (obj.getdate() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "date"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String update_sql = String.format("%s %s %s %s %s ",
                String.format("UPDATE %s", "AuthAnalyst"),
                String.format("SET %s = ?", "document_ID"),
                String.format("SET %s = ?", "person_ID"),
                String.format("SET %s = ?", "date"),
                String.format("WHERE %s = ?", "document_ID"));

        final PreparedStatementAware prepUpdate = new PreparedStatementAware(connectionHandle_.prepareStatement(update_sql));
        prepUpdate.setString(obj.getdocument_ID().getdocument_ID());
        prepUpdate.setInt(obj.getperson_ID().getperson_ID());
        prepUpdate.setDate((Date) obj.getdate());

        retBool = prepUpdate.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Update a new %s : [%s]", "AuthAnalyst", prepUpdate.printSqlStatement(update_sql));

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
     * @param obj - {@link AuthAnalyst} - insert the job class.
     * @return - boolean - the state of the sql delete.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean delete(AuthAnalyst obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "AuthAnalyst"));
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
                String.format("DELETE FROM %s", "AuthAnalyst"),
                String.format("WHERE %s = ?", "document_ID"));

        final PreparedStatementAware prepDelete = new PreparedStatementAware(connectionHandle_.prepareStatement(delete_sql));
        prepDelete.setString(obj.getdocument_ID().getdocument_ID());

        retBool = prepDelete.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Delete a new %s : [%s]", "AuthAnalyst", prepDelete.printSqlStatement(delete_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }

        return retBool;
    }
}
