package fr.bdd.job.db_project.dao;

import fr.bdd.custom.sql.PreparedStatementAware;
import fr.bdd.job.dao.Dao;
import fr.bdd.job.db_project.jobclass.NatureOfDoc;
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
 * Used for the job class {@link NatureOfDoc}
 *
 * @author Gaetan Brenckle
 */
public class DAO_NatureOfDoc implements Dao<NatureOfDoc> {

    private static final CustomLogger LOGGER = CustomLogger.create(DAO_NatureOfDoc.class.getName());
    private Connection connectionHandle_ = null;

    /**
     * Default constructor.
     * Need to call {@link DAO_NatureOfDoc#setConnection(Connection)} before any other function.
     *
     * @author Gaetan Brenckle
     */
    public DAO_NatureOfDoc() {
    }

    /**
     * Constructor with the Connection argument.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link Connection} - connection used.
     */
    public DAO_NatureOfDoc(Connection conn) {
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
     * @return - {@link NatureOfDoc} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    public final NatureOfDoc select(String id) throws SQLException {
        NatureOfDoc retNatureOfDoc = null;

        if (id == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("NatureOfDoc select when the given id is null");
            }
            return retNatureOfDoc;
        }

        final String select_sql = String.format("%s %s %s",
                String.format("SELECT %s, %s", "natureOfDoc_ID", "supportOfDoc"),
                String.format("FROM %s ", "NatureOfDoc"),
                String.format("WHERE natureOfDoc_ID = ?"));

        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.prepareStatement(select_sql));
        prepSelect.setString(id);

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            if (resultSelect.next()) {
                retNatureOfDoc =
                        new NatureOfDoc()
                                .setnatureOfDoc_ID(resultSelect.getString("natureOfDoc_ID"))
                                .setsupportOfDoc(resultSelect.getString("supportOfDoc"));

            }
        }
        return retNatureOfDoc;
    }

    /**
     * SELECT with the index of the associate job class.
     *
     * @author Gaetan Brenckle
     *
     * @param map - {@link HashMap} - index of the associate job class. Can handle null.
     * @return - {@link List} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    public final List<NatureOfDoc> selectByMultiCondition(HashMap<String, String> map) throws SQLException {
        final List<NatureOfDoc> retSupportOfDocs = new ArrayList<>();

        if (map == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("NatureOfDoc select when the given id is null");
            }
            return retSupportOfDocs;
        }


        StringBuilder select_sql = new StringBuilder(String.format("%s %s %s",
                String.format("SELECT %s, %s", "natureOfDoc_ID", "supportOfDoc"),
                String.format("FROM %s ", "NatureOfDoc"),
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
                NatureOfDoc NatureOfDoc =
                        new NatureOfDoc()
                                    .setnatureOfDoc_ID(resultSelect.getString("natureOfDoc_ID"))
                                    .setsupportOfDoc(resultSelect.getString("supportOfDoc"));
                retSupportOfDocs.add(NatureOfDoc);
            }
        }
        return retSupportOfDocs;
    }

    /**
     * SELECT of all occurance of the NatureOfDoc class.
     *
     * @author Gaetan Brenckle
     *
     * @return - {@link List} - a list that contain all occurance of {@link NatureOfDoc}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public List<NatureOfDoc> selectAll() throws SQLException {
        final List<NatureOfDoc> retSupportOfDocs = new ArrayList<>();

        String format = String.format("%s %s",
                String.format("SELECT %s, %s", "natureOfDoc_ID", "supportOfDoc"),
                String.format("FROM %s ", "NatureOfDoc"));
        final String selectAll_sql = format;

        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(connectionHandle_.prepareStatement(selectAll_sql));


        try(final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
            while (resultSelectAll.next()) {
                NatureOfDoc NatureOfDoc =
                        new NatureOfDoc()
                                .setnatureOfDoc_ID(resultSelectAll.getString("natureOfDoc_ID"))
                                .setsupportOfDoc(resultSelectAll.getString("supportOfDoc"));
                retSupportOfDocs.add(NatureOfDoc);
            }
        }

        return retSupportOfDocs;
    }

    @Override
    public List<NatureOfDoc> selectAll(List<NatureOfDoc> excludeList) throws SQLException {
        return null;
    }

    /**
     * INSERT the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link NatureOfDoc} - insert the job class.
     * @return - boolean - the state of the sql insert.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean insert(NatureOfDoc obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "NatureOfDoc"));
            }
            return false;
        }

        if (obj.getnatureOfDoc_ID() == null || obj.getnatureOfDoc_ID().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "natureOfDoc_ID"));
            }
            retBool = false;
        }

        if (obj.getsupportOfDoc() == null || obj.getsupportOfDoc().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "supportOfDoc"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String insert_sql = String.format("%s %s ",
                String.format("INSERT INTO %s (%s, %s)", "NatureOfDoc", "natureOfDoc_ID", "supportOfDoc"),
                "VALUES (?, ?)");

        final PreparedStatementAware prepInsert = new PreparedStatementAware(connectionHandle_.prepareStatement(insert_sql));
        prepInsert.setString(obj.getnatureOfDoc_ID());
        prepInsert.setString(obj.getsupportOfDoc());

        retBool = prepInsert.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Insert a new %s : [%s]", "NatureOfDoc", prepInsert.printSqlStatement(insert_sql));

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
     * @param obj - {@link NatureOfDoc} - insert the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean update(NatureOfDoc obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "NatureOfDoc"));
            }
            return false;
        }

        if (obj.getnatureOfDoc_ID() == null || obj.getnatureOfDoc_ID().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "natureOfDoc_ID"));
            }
            retBool = false;
        }

        if (obj.getsupportOfDoc() == null || obj.getsupportOfDoc().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "supportOfDoc"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String update_sql = String.format("%s %s %s %s ",
                String.format("UPDATE %s", "NatureOfDoc"),
                String.format("SET %s = ?", "natureOfDoc_ID"),
                String.format("SET %s = ?", "supportOfDoc"),
                String.format("WHERE %s = ?", "natureOfDoc_ID"));

        final PreparedStatementAware prepUpdate = new PreparedStatementAware(connectionHandle_.prepareStatement(update_sql));
        prepUpdate.setString(obj.getnatureOfDoc_ID());
        prepUpdate.setString(obj.getsupportOfDoc());

        retBool = prepUpdate.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Update a new %s : [%s]", "NatureOfDoc", prepUpdate.printSqlStatement(update_sql));

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
     * @param obj - {@link NatureOfDoc} - insert the job class.
     * @return - boolean - the state of the sql delete.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean delete(NatureOfDoc obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "NatureOfDoc"));
            }
            return false;
        }

        if (obj.getnatureOfDoc_ID() == null || obj.getnatureOfDoc_ID().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "natureOfDoc_ID"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String delete_sql = String.format("%s %s ",
                String.format("DELETE FROM %s", "NatureOfDoc"),
                String.format("WHERE %s = ?", "natureOfDoc_ID"));

        final PreparedStatementAware prepDelete = new PreparedStatementAware(connectionHandle_.prepareStatement(delete_sql));
        prepDelete.setString(obj.getnatureOfDoc_ID());

        retBool = prepDelete.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Delete a new %s : [%s]", "NatureOfDoc", prepDelete.printSqlStatement(delete_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }

        return retBool;
    }
}
