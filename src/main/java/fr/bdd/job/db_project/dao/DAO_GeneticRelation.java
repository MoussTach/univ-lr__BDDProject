package fr.bdd.job.db_project.dao;

import fr.bdd.custom.sql.PreparedStatementAware;
import fr.bdd.job.dao.Dao;
import fr.bdd.job.db_project.jobclass.GeneticRelation;
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
 * Used for the job class {@link GeneticRelation}
 *
 * @author Gaetan Brenckle
 */
public class DAO_GeneticRelation implements Dao<GeneticRelation> {

    private static final CustomLogger LOGGER = CustomLogger.create(DAO_GeneticRelation.class.getName());
    private Connection connectionHandle_ = null;

    /**
     * Default constructor.
     * Need to call {@link DAO_GeneticRelation#setConnection(Connection)} before any other function.
     *
     * @author Gaetan Brenckle
     */
    public DAO_GeneticRelation() {
    }

    /**
     * Constructor with the Connection argument.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link Connection} - connection used.
     */
    public DAO_GeneticRelation(Connection conn) {
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
     * @return - {@link GeneticRelation} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    public final GeneticRelation select(String id) throws SQLException {
        GeneticRelation retGeneticRelation = null;
        DAO_Document dao_document_src = new DAO_Document(this.connectionHandle_);
        DAO_Document dao_document_dest = new DAO_Document(this.connectionHandle_);

        if (id == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("GeneticRelation select when the given id is null");
            }
            return retGeneticRelation;
        }

        final String select_sql = String.format("%s %s %s",
                String.format("SELECT %s, %s, %s", "document_ID_src", "document_ID_dest", "geneticStatus"),
                String.format("FROM %s ", "GeneticRelation"),
                String.format("WHERE id = ?"));

        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.prepareStatement(select_sql));
        prepSelect.setString(id);

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            if (resultSelect.next()) {
                retGeneticRelation =
                        new GeneticRelation()
                                .setdocument_ID_src(dao_document_src.select(resultSelect.getString("document_ID_src")))
                                .setdocument_ID_dest(dao_document_dest.select(resultSelect.getString("document_ID_dest")))
                                .setgeneticStatus(resultSelect.getString("geneticStatus"));

            }
        }
        return retGeneticRelation;
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
    public final List<GeneticRelation> selectByMultiCondition(HashMap<String, String> map) throws SQLException {
        final List<GeneticRelation> retCategories = new ArrayList<>();
        DAO_Document dao_document_src = new DAO_Document(this.connectionHandle_);
        DAO_Document dao_document_dest = new DAO_Document(this.connectionHandle_);

        if (map == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("GeneticRelation select when the given id is null");
            }
            return retCategories;
        }


        StringBuilder select_sql = new StringBuilder(String.format("%s %s %s",
                String.format("SELECT %s, %s, %s", "document_ID_src", "document_ID_dest", "geneticStatus"),
                String.format("FROM %s ", "GeneticRelation"),
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
                GeneticRelation GeneticRelation =
                        new GeneticRelation()
                                        .setdocument_ID_src(dao_document_src.select(resultSelect.getString("document_ID_src")))
                                        .setdocument_ID_dest(dao_document_dest.select(resultSelect.getString("document_ID_dest")))
                                        .setgeneticStatus(resultSelect.getString("geneticStatus"));
                retCategories.add(GeneticRelation);
            }
        }
        return retCategories;
    }

    /**
     * SELECT of all occurance of the GeneticRelation class.
     *
     * @author Gaetan Brenckle
     *
     * @return - {@link List} - a list that contain all occurance of {@link GeneticRelation}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public List<GeneticRelation> selectAll() throws SQLException {
        final List<GeneticRelation> retCategories = new ArrayList<>();
        DAO_Document dao_document_src = new DAO_Document(this.connectionHandle_);
        DAO_Document dao_document_dest = new DAO_Document(this.connectionHandle_);

        String format = String.format("%s %s",
                String.format("SELECT %s, %s, %s", "document_ID_src", "document_ID_dest", "geneticStatus"),
                String.format("FROM %s ", "GeneticRelation"));
        final String selectAll_sql = format;

        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(connectionHandle_.prepareStatement(selectAll_sql));


        try(final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
            while (resultSelectAll.next()) {
                GeneticRelation GeneticRelation =
                        new GeneticRelation()
                                .setdocument_ID_src(dao_document_src.select(resultSelectAll.getString("document_ID_src")))
                                .setdocument_ID_dest(dao_document_dest.select(resultSelectAll.getString("document_ID_dest")))
                                .setgeneticStatus(resultSelectAll.getString("geneticStatus"));
                retCategories.add(GeneticRelation);
            }
        }

        return retCategories;
    }

    @Override
    public List<GeneticRelation> selectAll(List<GeneticRelation> excludeList) throws SQLException {
        return null;
    }

    /**
     * INSERT the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link GeneticRelation} - insert the job class.
     * @return - boolean - the state of the sql insert.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean insert(GeneticRelation obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "GeneticRelation"));
            }
            return false;
        }

        if (obj.getdocument_ID_src() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "document_ID_src"));
            }
            retBool = false;
        }

        if (obj.getdocument_ID_dest() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "document_ID_dest"));
            }
            retBool = false;
        }

        if (obj.getgeneticStatus() == null || obj.getgeneticStatus().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "geneticStatus"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String insert_sql = String.format("%s %s ",
                String.format("INSERT INTO %s (%s, %s, %s)", "GeneticRelation", "document_ID_src", "document_ID_dest", "geneticStatus"),
                "VALUES (?, ?, ?)");

        final PreparedStatementAware prepInsert = new PreparedStatementAware(connectionHandle_.prepareStatement(insert_sql));
        prepInsert.setString(obj.getdocument_ID_src().getdocument_ID());
        prepInsert.setString(obj.getdocument_ID_dest().getdocument_ID());
        prepInsert.setString(obj.getgeneticStatus());

        retBool = prepInsert.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Insert a new %s : [%s]", "GeneticRelation", prepInsert.printSqlStatement(insert_sql));

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
     * @param obj - {@link GeneticRelation} - insert the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean update(GeneticRelation obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "GeneticRelation"));
            }
            return false;
        }

        if (obj.getdocument_ID_src() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "document_ID_src"));
            }
            retBool = false;
        }

        if (obj.getdocument_ID_dest() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "document_ID_dest"));
            }
            retBool = false;
        }

        if (obj.getgeneticStatus() == null || obj.getgeneticStatus().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "geneticStatus"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String update_sql = String.format("%s %s %s %s %s ",
                String.format("UPDATE %s", "GeneticRelation"),
                String.format("SET %s = ?", "document_ID_src"),
                String.format("SET %s = ?", "document_ID_dest"),
                String.format("SET %s = ?", "geneticStatus"),
                String.format("WHERE %s = ?", "document_ID_src"));

        final PreparedStatementAware prepUpdate = new PreparedStatementAware(connectionHandle_.prepareStatement(update_sql));
        prepUpdate.setString(obj.getdocument_ID_src().getdocument_ID());
        prepUpdate.setString(obj.getdocument_ID_dest().getdocument_ID());
        prepUpdate.setString(obj.getgeneticStatus());

        retBool = prepUpdate.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Update a new %s : [%s]", "GeneticRelation", prepUpdate.printSqlStatement(update_sql));

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
     * @param obj - {@link GeneticRelation} - insert the job class.
     * @return - boolean - the state of the sql delete.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean delete(GeneticRelation obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "GeneticRelation"));
            }
            return false;
        }

        if (obj.getdocument_ID_src() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "document_ID_src"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String delete_sql = String.format("%s %s ",
                String.format("DELETE FROM %s", "GeneticRelation"),
                String.format("WHERE %s = ?", "document_ID_src"));

        final PreparedStatementAware prepDelete = new PreparedStatementAware(connectionHandle_.prepareStatement(delete_sql));
        prepDelete.setString(obj.getdocument_ID_src().getdocument_ID());

        retBool = prepDelete.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Delete a new %s : [%s]", "GeneticRelation", prepDelete.printSqlStatement(delete_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }

        return retBool;
    }
}
