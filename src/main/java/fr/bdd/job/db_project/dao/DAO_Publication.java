package fr.bdd.job.db_project.dao;

import fr.bdd.custom.sql.PreparedStatementAware;
import fr.bdd.dataconnection.DataConnection;
import fr.bdd.job.dao.Dao;
import fr.bdd.job.db_project.jobclass.Publication;
import fr.bdd.log.generate.CustomLogger;

import java.security.InvalidKeyException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO pattern class.
 * Used for the job class {@link Publication}
 *
 * @author Gaetan Brenckle
 */
public class DAO_Publication implements Dao<Publication> {

    private static final CustomLogger LOGGER = CustomLogger.create(DAO_Publication.class.getName());
    private DataConnection connectionHandle_ = null;

    /**
     * Default constructor.
     * Need to call {@link DAO_Publication#setConnection(DataConnection)} before any other function.
     *
     * @author Gaetan Brenckle
     */
    public DAO_Publication() {
    }

    /**
     * Constructor with the Connection argument.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link DataConnection} - connection used.
     */
    public DAO_Publication(DataConnection conn) throws InvalidKeyException {
        this.connectionHandle_ = conn;
    }

    /**
     * Setter of the connection.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link DataConnection} - Connection used.
     */
    @Override
    public void setConnection(DataConnection conn) throws InvalidKeyException {
        this.connectionHandle_ = conn;
    }



    /**
     * SELECT with the index of the associate job class.
     *
     * @author Gaetan Brenckle
     *
     * @param id - {@link Integer} - index of the associate job class. Can handle null.
     * @return - {@link Publication} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    public final Publication select(Integer id) throws SQLException, InvalidKeyException {
        Publication retPublication = null;
        DAO_Person dao_person = new DAO_Person(this.connectionHandle_);

        if (id == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Publication select when the given id is null");
            }
            return retPublication;
        }

        final String select_sql = String.format("%s %s %s",
                String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s", "publication_ID", "numPublication", "publication", "title", "place", "type", "periodicity", "director"),
                String.format("FROM %s ", "Publication"),
                String.format("WHERE publication_ID = ?"));

        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.getConnection().prepareStatement(select_sql));
        prepSelect.setInt(id);

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            if (resultSelect.next()) {
                retPublication =
                        new Publication()
                                .setpublication_ID(resultSelect.getInt("publication_ID"))
                                .setnumPublication(resultSelect.getString("numPublication"))
                                .setpublication(resultSelect.getString("publication"))
                                .settitle(resultSelect.getString("title"))
                                .setplace(resultSelect.getString("place"))
                                .settype(resultSelect.getString("type"))
                                .setperiodicity(resultSelect.getString("periodicity"))
                                .setdirector(dao_person.select(resultSelect.getInt("director")));

            }
        }
        return retPublication;
    }

    /**
     * SELECT with the index of the associate job class.
     *
     * @author Gaetan Brenckle
     *
     * @param map - {@link HashMap<String, String>} - index of the associate job class. Can handle null.
     * @return - {@link Publication} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    public final List<Publication> selectByMultiCondition(HashMap<String, String> map) throws SQLException, InvalidKeyException {
        final List<Publication> retCategories = new ArrayList<>();
        DAO_Person dao_person = new DAO_Person(this.connectionHandle_);

        if (map == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Publication select when the given id is null");
            }
            return retCategories;
        }


        StringBuilder select_sql = new StringBuilder(String.format("%s %s %s",
                String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s", "publication_ID", "numPublication", "publication", "title", "place", "type", "periodicity", "director"),
                String.format("FROM %s ", "Publication"),
                String.format("WHERE 1=1")
        ));

        for (Map.Entry<String,String> entry : map.entrySet()) {
            select_sql.append(String.format(" AND %s = ?", entry.getKey()));
        }


        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.getConnection().prepareStatement(select_sql.toString()));
        for (Map.Entry<String,String> entry : map.entrySet()) {
            prepSelect.setString(entry.getValue());
        }

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            if (resultSelect.next()) {
                Publication Publication =
                        new Publication()
                                .setpublication_ID(resultSelect.getInt("publication_ID"))
                                .setnumPublication(resultSelect.getString("numPublication"))
                                .setpublication(resultSelect.getString("publication"))
                                .settitle(resultSelect.getString("title"))
                                .setplace(resultSelect.getString("place"))
                                .settype(resultSelect.getString("type"))
                                .setperiodicity(resultSelect.getString("periodicity"))
                                .setdirector(dao_person.select(resultSelect.getInt("director")));

                retCategories.add(Publication);
            }
        }
        return retCategories;
    }

    /**
     * SELECT of all occurance of the Publication class.
     *
     * @author Gaetan Brenckle
     *
     * @return - {@link List} - a list that contain all occurance of {@link Publication}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    @Override
    public List<Publication> selectAll() throws SQLException, InvalidKeyException {
        final List<Publication> retCategories = new ArrayList<>();
        DAO_Person dao_person = new DAO_Person(this.connectionHandle_);

        String format = String.format("%s %s",
                String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s", "publication_ID", "numPublication", "publication", "title", "place", "type", "periodicity", "director"),
                String.format("FROM %s ", "Publication"));
        final String selectAll_sql = format;

        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(connectionHandle_.getConnection().prepareStatement(selectAll_sql));


        try(final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
            if (resultSelectAll.next()) {
                Publication Publication =
                        new Publication()
                                .setpublication_ID(resultSelectAll.getInt("publication_ID"))
                                .setnumPublication(resultSelectAll.getString("numPublication"))
                                .setpublication(resultSelectAll.getString("publication"))
                                .settitle(resultSelectAll.getString("title"))
                                .setplace(resultSelectAll.getString("place"))
                                .settype(resultSelectAll.getString("type"))
                                .setperiodicity(resultSelectAll.getString("periodicity"))
                                .setdirector(dao_person.select(resultSelectAll.getInt("director")));
                retCategories.add(Publication);
            }
        }

        return retCategories;
    }

    @Override
    public List<Publication> selectAll(List<Publication> excludeList) throws SQLException, InvalidKeyException {
        return null;
    }

    /**
     * INSERT the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link Publication} - insert the job class.
     * @return - boolean - the state of the sql insert.
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    @Override
    public boolean insert(Publication obj) throws SQLException, InvalidKeyException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Publication"));
            }
            return false;
        }

        if (obj.getpublication_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "publication_ID"));
            }
            retBool = false;
        }

        if (obj.getnumPublication() == null || obj.getnumPublication().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "numPublication"));
            }
            retBool = false;
        }

        if (obj.gettitle() == null || obj.gettitle().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "publication", "title", "place", "type", "periodicity", "director"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String insert_sql = String.format("%s %s ",
                String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s)", "Publication", "publication_ID", "numPublication", "publication", "title", "place", "type", "periodicity", "director"),
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

        final PreparedStatementAware prepInsert = new PreparedStatementAware(connectionHandle_.getConnection().prepareStatement(insert_sql));
        prepInsert.setInt(obj.getpublication_ID());
        prepInsert.setString(obj.getnumPublication());
        prepInsert.setString(obj.getpublication());
        prepInsert.setString(obj.gettitle());
        prepInsert.setString(obj.getplace());
        prepInsert.setString(obj.gettype());
        prepInsert.setString(obj.getperiodicity());
        prepInsert.setInt(obj.getdirector().getperson_ID());

        retBool = prepInsert.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Insert a new %s : [%s]", "Publication", prepInsert.printSqlStatement(insert_sql));

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
     * @param obj - {@link Publication} - insert the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    @Override
    public boolean update(Publication obj) throws SQLException, InvalidKeyException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Publication"));
            }
            return false;
        }

        if (obj.getpublication_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "publication_ID"));
            }
            retBool = false;
        }

        if (obj.getnumPublication() == null || obj.getnumPublication().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "numPublication"));
            }
            retBool = false;
        }

        if (obj.gettitle() == null || obj.gettitle().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "title"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }
        String update_sql = String.format("%s %s %s %s %s ",
                String.format("UPDATE %s", "Publication"),
                String.format("SET %s = ?", "publication_ID"),
                String.format("SET %s = ?", "numPublication"),
                String.format("SET %s = ?", "publication"),
                String.format("SET %s = ?", "title"),
                String.format("SET %s = ?", "place"),
                String.format("SET %s = ?", "type"),
                String.format("SET %s = ?", "periodicity"),
                String.format("SET %s = ?", "director"),
                String.format("WHERE %s = ?", "publication_ID"));

        final PreparedStatementAware prepUpdate = new PreparedStatementAware(connectionHandle_.getConnection().prepareStatement(update_sql));
        prepUpdate.setInt(obj.getpublication_ID());
        prepUpdate.setString(obj.getnumPublication());
        prepUpdate.setString(obj.getpublication());
        prepUpdate.setString(obj.gettitle());
        prepUpdate.setString(obj.getplace());
        prepUpdate.setString(obj.gettype());
        prepUpdate.setString(obj.getperiodicity());
        prepUpdate.setInt(obj.getdirector().getperson_ID());

        retBool = prepUpdate.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Update a new %s : [%s]", "Publication", prepUpdate.printSqlStatement(update_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }
        return retBool;
    }

    @Override
    public boolean upsert(Publication obj) throws SQLException, InvalidKeyException {
        return false;
    }

    /**
     * DELETE the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link Publication} - insert the job class.
     * @return - boolean - the state of the sql delete.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean delete(Publication obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Publication"));
            }
            return false;
        }

        if (obj.getpublication_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "publication_ID"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String delete_sql = String.format("%s %s ",
                String.format("DELETE FROM %s", "Publication"),
                String.format("WHERE %s = ?", "publication_ID"));

        final PreparedStatementAware prepDelete = new PreparedStatementAware(connectionHandle_.getConnection().prepareStatement(delete_sql));
        prepDelete.setInt(obj.getpublication_ID());

        retBool = prepDelete.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Delete a new %s : [%s]", "Publication", prepDelete.printSqlStatement(delete_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }

        return retBool;
    }
}
