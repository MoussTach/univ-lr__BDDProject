package fr.bdd.job.db_project.dao;

import fr.bdd.custom.sql.PreparedStatementAware;
import fr.bdd.job.dao.Dao;
import fr.bdd.job.db_project.jobclass.RespGroup;
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
 * Used for the job class {@link RespGroup}
 *
 * @author Gaetan Brenckle
 */
public class DAO_RespGroup implements Dao<RespGroup> {

    private static final CustomLogger LOGGER = CustomLogger.create(DAO_RespGroup.class.getName());
    private Connection connectionHandle_ = null;

    /**
     * Default constructor.
     * Need to call {@link DAO_RespGroup#setConnection(Connection)} before any other function.
     *
     * @author Gaetan Brenckle
     */
    public DAO_RespGroup() {
    }

    /**
     * Constructor with the Connection argument.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link Connection} - connection used.
     */
    public DAO_RespGroup(Connection conn) {
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
     * @return - {@link RespGroup} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     
     */
    public final RespGroup select(String id) throws SQLException {
        RespGroup retRespGroup = null;

        if (id == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("RespGroup select when the given id is null");
            }
            return retRespGroup;
        }

        final String select_sql = String.format("%s %s %s",
                String.format("SELECT %s", "respGroup_ID"),
                String.format("FROM %s ", "RespGroup"),
                String.format("WHERE id = ?"));

        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.prepareStatement(select_sql));
        prepSelect.setString(id);

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            if (resultSelect.next()) {
                retRespGroup =
                        new RespGroup()
                                .setrespGroup_ID(resultSelect.getString("respGroup_ID"));

            }
        }
        return retRespGroup;
    }

    /**
     * SELECT with the index of the associate job class.
     *
     * @author Gaetan Brenckle
     *
     * @param map - {@link HashMap<String, String>} - index of the associate job class. Can handle null.
     * @return - {@link RespGroup} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     
     */
    public final List<RespGroup> selectByMultiRespGroup(HashMap<String, String> map) throws SQLException {
        final List<RespGroup> retRespGroups = new ArrayList<>();

        if (map == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("RespGroup select when the given id is null");
            }
            return retRespGroups;
        }


        StringBuilder select_sql = new StringBuilder(String.format("%s %s %s",
                String.format("SELECT %s", "respGroup_ID"),
                String.format("FROM %s ", "RespGroup"),
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
                RespGroup RespGroup =
                        new RespGroup().setrespGroup_ID(resultSelect.getString("respGroup_ID"));
                retRespGroups.add(RespGroup);
            }
        }
        return retRespGroups;
    }

    /**
     * SELECT of all occurance of the RespGroup class.
     *
     * @author Gaetan Brenckle
     *
     * @return - {@link List} - a list that contain all occurance of {@link RespGroup}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public List<RespGroup> selectAll() throws SQLException {
        final List<RespGroup> retRespGroups = new ArrayList<>();

        String format = String.format("%s %s %s",
                String.format("SELECT %s", "respGroup_ID"),
                String.format("FROM %s ", "RespGroup"));
        final String selectAll_sql = format;

        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(connectionHandle_.prepareStatement(selectAll_sql));

        try(final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
            while (resultSelectAll.next()) {
                RespGroup RespGroup =
                        new RespGroup()
                                .setrespGroup_ID(resultSelectAll.getString("respGroup_ID"));
                retRespGroups.add(RespGroup);
            }
        }

        return retRespGroups;
    }

    @Override
    public List<RespGroup> selectAll(List<RespGroup> excludeList) throws SQLException {
        return null;
    }

    /**
     * INSERT the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link RespGroup} - insert the job class.
     * @return - boolean - the state of the sql insert.
     * @throws SQLException - throw the exception to force a try catch when used.
     
     */
    @Override
    public boolean insert(RespGroup obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "RespGroup"));
            }
            return false;
        }

        if (obj.getrespGroup_ID() == null || obj.getrespGroup_ID().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "respGroup_ID"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String insert_sql = String.format("%s %s ",
                String.format("INSERT INTO %s (%s)", "RespGroup", "respGroup_ID"),
                "VALUES (?)");

        final PreparedStatementAware prepInsert = new PreparedStatementAware(connectionHandle_.prepareStatement(insert_sql));
        prepInsert.setString(obj.getrespGroup_ID());

        retBool = prepInsert.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Insert a new %s : [%s]", "RespGroup", prepInsert.printSqlStatement(insert_sql));

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
     * @param obj - {@link RespGroup} - insert the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
     
     */
    @Override
    public boolean update(RespGroup obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "RespGroup"));
            }
            return false;
        }

        if (obj.getrespGroup_ID() == null || obj.getrespGroup_ID().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "respGroup_ID"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String update_sql = String.format("%s %s %s ",
                String.format("UPDATE %s", "RespGroup"),
                String.format("SET %s = ?", "respGroup_ID"),
                String.format("WHERE %s = ?", "respGroup_ID"));

        final PreparedStatementAware prepUpdate = new PreparedStatementAware(connectionHandle_.prepareStatement(update_sql));
        prepUpdate.setString(obj.getrespGroup_ID());

        retBool = prepUpdate.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Update a new %s : [%s]", "RespGroup", prepUpdate.printSqlStatement(update_sql));

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
     * @param obj - {@link RespGroup} - insert the job class.
     * @return - boolean - the state of the sql delete.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean delete(RespGroup obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "RespGroup"));
            }
            return false;
        }

        if (obj.getrespGroup_ID() == null || obj.getrespGroup_ID().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "respGroup_ID"));
            }
            return false;
        }

        if (!retBool) {
            return false;
        }

        String delete_sql = String.format("%s %s ",
                String.format("DELETE FROM %s", "RespGroup"),
                String.format("WHERE %s = ?", "respGroup_ID"));

        final PreparedStatementAware prepDelete = new PreparedStatementAware(connectionHandle_.prepareStatement(delete_sql));
        prepDelete.setString(obj.getrespGroup_ID());

        retBool = prepDelete.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Delete a new %s : [%s]", "RespGroup", prepDelete.printSqlStatement(delete_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }

        return retBool;
    }
}
