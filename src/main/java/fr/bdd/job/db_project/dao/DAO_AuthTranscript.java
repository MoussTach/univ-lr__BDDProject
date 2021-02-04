package fr.bdd.job.db_project.dao;

import fr.bdd.custom.sql.PreparedStatementAware;
import fr.bdd.job.dao.Dao;
import fr.bdd.job.db_project.jobclass.AuthTranscript;
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
 * Used for the job class {@link AuthTranscript}
 *
 * @author Gaetan Brenckle
 */
public class DAO_AuthTranscript implements Dao<AuthTranscript> {

    private static final CustomLogger LOGGER = CustomLogger.create(DAO_AuthTranscript.class.getName());
    private Connection connectionHandle_ = null;

    /**
     * Default constructor.
     * Need to call {@link DAO_AuthTranscript#setConnection(Connection)} before any other function.
     *
     * @author Gaetan Brenckle
     */
    public DAO_AuthTranscript() {
    }

    /**
     * Constructor with the Connection argument.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link Connection} - connection used.
     */
    public DAO_AuthTranscript(Connection conn) {
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
     * @return - {@link AuthTranscript} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    public final AuthTranscript select(String id) throws SQLException {
        AuthTranscript retAuthTranscript = null;
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Person dao_Person = new DAO_Person(this.connectionHandle_);

        if (id == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("AuthTranscript select when the given id is null");
            }
            return retAuthTranscript;
        }

        final String select_sql = String.format("%s %s %s",
                String.format("SELECT %s, %s", "document_ID", "person_ID"),
                String.format("FROM %s ", "AuthTranscript"),
                String.format("WHERE id = ?"));

        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.prepareStatement(select_sql));
        prepSelect.setString(id);

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            if (resultSelect.next()) {
                retAuthTranscript =
                        new AuthTranscript()
                                .setdocument_ID(dao_document.select(resultSelect.getString("document_ID")))
                                .setperson_ID(dao_Person.select(resultSelect.getInt("person_ID")));

            }
        }
        return retAuthTranscript;
    }

    /**
     * SELECT with the index of the associate job class.
     *
     * @author Gaetan Brenckle
     *
     * @param map - {@link HashMap<String, String>} - index of the associate job class. Can handle null.
     * @return - {@link AuthTranscript} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    public final List<AuthTranscript> selectByMultiCondition(HashMap<String, String> map) throws SQLException {
        final List<AuthTranscript> retCategories = new ArrayList<>();
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Person dao_Person = new DAO_Person(this.connectionHandle_);

        if (map == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("AuthTranscript select when the given id is null");
            }
            return retCategories;
        }


        StringBuilder select_sql = new StringBuilder(String.format("%s %s %s",
                String.format("SELECT %s, %s", "document_ID", "person_ID"),
                String.format("FROM %s ", "AuthTranscript"),
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
                AuthTranscript AuthTranscript =
                        new AuthTranscript()
                                        .setdocument_ID(dao_document.select(resultSelect.getString("document_ID")))
                                        .setperson_ID(dao_Person.select(resultSelect.getInt("person_ID")));
                retCategories.add(AuthTranscript);
            }
        }
        return retCategories;
    }

    /**
     * SELECT of all occurance of the AuthTranscript class.
     *
     * @author Gaetan Brenckle
     *
     * @return - {@link List} - a list that contain all occurance of {@link AuthTranscript}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public List<AuthTranscript> selectAll() throws SQLException {
        final List<AuthTranscript> retCategories = new ArrayList<>();
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Person dao_Person = new DAO_Person(this.connectionHandle_);

        String format = String.format("%s %s",
                String.format("SELECT %s, %s", "document_ID", "person_ID"),
                String.format("FROM %s ", "AuthTranscript"));
        final String selectAll_sql = format;

        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(connectionHandle_.prepareStatement(selectAll_sql));


        try(final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
            while (resultSelectAll.next()) {
                AuthTranscript AuthTranscript =
                        new AuthTranscript()
                                .setdocument_ID(dao_document.select(resultSelectAll.getString("document_ID")))
                                .setperson_ID(dao_Person.select(resultSelectAll.getInt("person_ID")));
                retCategories.add(AuthTranscript);
            }
        }

        return retCategories;
    }

    @Override
    public List<AuthTranscript> selectAll(List<AuthTranscript> excludeList) throws SQLException {
        return null;
    }

    /**
     * INSERT the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link AuthTranscript} - insert the job class.
     * @return - boolean - the state of the sql insert.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean insert(AuthTranscript obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "AuthTranscript"));
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

        if (!retBool) {
            return false;
        }

        String insert_sql = String.format("%s %s ",
                String.format("INSERT INTO %s (%s, %s)", "AuthTranscript", "document_ID", "person_ID"),
                "VALUES (?, ?)");

        final PreparedStatementAware prepInsert = new PreparedStatementAware(connectionHandle_.prepareStatement(insert_sql));
        prepInsert.setString(obj.getdocument_ID().getdocument_ID());
        prepInsert.setInt(obj.getperson_ID().getperson_ID());

        retBool = prepInsert.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Insert a new %s : [%s]", "AuthTranscript", prepInsert.printSqlStatement(insert_sql));

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
     * @param obj - {@link AuthTranscript} - insert the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean update(AuthTranscript obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "AuthTranscript"));
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

        if (!retBool) {
            return false;
        }

        String update_sql = String.format("%s %s %s %s ",
                String.format("UPDATE %s", "AuthTranscript"),
                String.format("SET %s = ?", "document_ID"),
                String.format("SET %s = ?", "person_ID"),
                String.format("WHERE %s = ?", "document_ID"));

        final PreparedStatementAware prepUpdate = new PreparedStatementAware(connectionHandle_.prepareStatement(update_sql));
        prepUpdate.setString(obj.getdocument_ID().getdocument_ID());
        prepUpdate.setInt(obj.getperson_ID().getperson_ID());

        retBool = prepUpdate.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Update a new %s : [%s]", "AuthTranscript", prepUpdate.printSqlStatement(update_sql));

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
     * @param obj - {@link AuthTranscript} - insert the job class.
     * @return - boolean - the state of the sql delete.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean delete(AuthTranscript obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "AuthTranscript"));
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
                String.format("DELETE FROM %s", "AuthTranscript"),
                String.format("WHERE %s = ?", "document_ID"));

        final PreparedStatementAware prepDelete = new PreparedStatementAware(connectionHandle_.prepareStatement(delete_sql));
        prepDelete.setString(obj.getdocument_ID().getdocument_ID());

        retBool = prepDelete.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Delete a new %s : [%s]", "AuthTranscript", prepDelete.printSqlStatement(delete_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }

        return retBool;
    }
}
