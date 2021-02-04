package fr.bdd.job.db_project.dao;

import fr.bdd.custom.sql.PreparedStatementAware;
import fr.bdd.job.dao.Dao;
import fr.bdd.job.db_project.jobclass.Language;
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
 * Used for the job class {@link Language}
 *
 * @author Gaetan Brenckle
 */
public class DAO_Language implements Dao<Language> {

    private static final CustomLogger LOGGER = CustomLogger.create(DAO_Language.class.getName());
    private Connection connectionHandle_ = null;

    /**
     * Default constructor.
     * Need to call {@link DAO_Language#setConnection(Connection)} before any other function.
     *
     * @author Gaetan Brenckle
     */
    public DAO_Language() {
    }

    /**
     * Constructor with the Connection argument.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link Connection} - connection used.
     */
    public DAO_Language(Connection conn)  {
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
     * @param id - {@link String} - index of the associate job class. Can handle null.
     * @return - {@link Language} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    public final Language select(String id) throws SQLException {
        Language retLanguage = null;

        if (id == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Language select when the given id is null");
            }
            return retLanguage;
        }

        final String select_sql = String.format("%s %s %s",
                String.format("SELECT %s, %s", "language_ID", "country"),
                String.format("FROM %s ", "Language"),
                String.format("WHERE language_ID = ?"));

        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.prepareStatement(select_sql));
        prepSelect.setString(id);

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            if (resultSelect.next()) {
                retLanguage =
                        new Language()
                                .setlanguage_ID(resultSelect.getString("language_ID"))
                                .setcountry(resultSelect.getString("country"));

            }
        }
        return retLanguage;
    }

    /**
     * SELECT with the index of the associate job class.
     *
     * @author Gaetan Brenckle
     *
     * @param map - {@link HashMap<String, String>} - index of the associate job class. Can handle null.
     * @return - {@link Language} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
*/
    public final List<Language> selectByMultiCondition(HashMap<String, String> map) throws SQLException {
        final List<Language> retLanguages = new ArrayList<>();

        if (map == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Language select when the given id is null");
            }
            return retLanguages;
        }


        StringBuilder select_sql = new StringBuilder(String.format("%s %s %s",
                String.format("SELECT %s, %s", "language_ID", "country"),
                String.format("FROM %s ", "Language"),
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
                Language Language =
                        new Language()
                                    .setlanguage_ID(resultSelect.getString("language_ID"))
                                    .setcountry(resultSelect.getString("country"));
                retLanguages.add(Language);
            }
        }
        return retLanguages;
    }

    /**
     * SELECT of all occurance of the Language class.
     *
     * @author Gaetan Brenckle
     *
     * @return - {@link List} - a list that contain all occurance of {@link Language}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
*/
    @Override
    public List<Language> selectAll() throws SQLException {
        final List<Language> retLanguages = new ArrayList<>();

        String format = String.format("%s %s",
                String.format("SELECT %s, %s", "language_ID", "country"),
                String.format("FROM %s ", "Language"));
        final String selectAll_sql = format;

        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(connectionHandle_.prepareStatement(selectAll_sql));

        try(final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
            while (resultSelectAll.next()) {
                Language Language =
                        new Language()
                                .setlanguage_ID(resultSelectAll.getString("language_ID"))
                                .setcountry(resultSelectAll.getString("country"));
                retLanguages.add(Language);
            }
        }

        return retLanguages;
    }

    @Override
    public List<Language> selectAll(List<Language> excludeList) throws SQLException {
        return null;
    }

    /**
     * INSERT the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link Language} - insert the job class.
     * @return - boolean - the state of the sql insert.
     * @throws SQLException - throw the exception to force a try catch when used.
*/
    @Override
    public boolean insert(Language obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Language"));
            }
            return false;
        }

        if (obj.getlanguage_ID() == null || obj.getlanguage_ID().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "language_ID"));
            }
            retBool = false;
        }

        if (obj.getcountry() == null || obj.getcountry().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "country"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String insert_sql = String.format("%s %s ",
                String.format("INSERT INTO %s (%s, %s)", "Language", "language_ID", "country"),
                "VALUES (?, ?)");

        final PreparedStatementAware prepInsert = new PreparedStatementAware(connectionHandle_.prepareStatement(insert_sql));
        prepInsert.setString(obj.getlanguage_ID());
        prepInsert.setString(obj.getcountry());

        retBool = prepInsert.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Insert a new %s : [%s]", "Language", prepInsert.printSqlStatement(insert_sql));

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
     * @param obj - {@link Language} - insert the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
*/
    @Override
    public boolean update(Language obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Language"));
            }
            return false;
        }

        if (obj.getlanguage_ID() == null || obj.getlanguage_ID().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "language_ID"));
            }
            retBool = false;
        }

        if (obj.getcountry() == null || obj.getcountry().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "country"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String update_sql = String.format("%s %s %s %s ",
                String.format("UPDATE %s", "Language"),
                String.format("SET %s = ?", "language_ID"),
                String.format("SET %s = ?", "country"),
                String.format("WHERE %s = ?", "language_ID"));

        final PreparedStatementAware prepUpdate = new PreparedStatementAware(connectionHandle_.prepareStatement(update_sql));
        prepUpdate.setString(obj.getlanguage_ID());
        prepUpdate.setString(obj.getcountry());

        retBool = prepUpdate.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Update a new %s : [%s]", "Language", prepUpdate.printSqlStatement(update_sql));

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
     * @param obj - {@link Language} - insert the job class.
     * @return - boolean - the state of the sql delete.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean delete(Language obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Language"));
            }
            return false;
        }

        if (obj.getlanguage_ID() == null || obj.getlanguage_ID().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "language_ID"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String delete_sql = String.format("%s %s ",
                String.format("DELETE FROM %s", "Language"),
                String.format("WHERE %s = ?", "language_ID"));

        final PreparedStatementAware prepDelete = new PreparedStatementAware(connectionHandle_.prepareStatement(delete_sql));
        prepDelete.setString(obj.getlanguage_ID());

        retBool = prepDelete.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Delete a new %s : [%s]", "Language", prepDelete.printSqlStatement(delete_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }

        return retBool;
    }
}
