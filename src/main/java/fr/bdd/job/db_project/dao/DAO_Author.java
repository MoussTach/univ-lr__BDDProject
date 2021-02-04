package fr.bdd.job.db_project.dao;

import fr.bdd.custom.sql.PreparedStatementAware;
import fr.bdd.dataconnection.DataConnection;
import fr.bdd.job.dao.Dao;
import fr.bdd.job.db_project.jobclass.Author;
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
 * Used for the job class {@link Author}
 *
 * @author Gaetan Brenckle
 */
public class DAO_Author implements Dao<Author> {

    private static final CustomLogger LOGGER = CustomLogger.create(DAO_Author.class.getName());
    private DataConnection connectionHandle_ = null;

    /**
     * Default constructor.
     * Need to call {@link DAO_Author#setConnection(DataConnection)} before any other function.
     *
     * @author Gaetan Brenckle
     */
    public DAO_Author() {
    }

    /**
     * Constructor with the Connection argument.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link DataConnection} - connection used.
     */
    public DAO_Author(DataConnection conn) throws InvalidKeyException {
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
     * @return - {@link Author} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    public final Author select(Integer id) throws SQLException, InvalidKeyException {
        Author retAuthor = null;

        if (id == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Author select when the given id is null");
            }
            return retAuthor;
        }

        final String select_sql = String.format("%s %s %s",
                String.format("SELECT %s, %s", "author_ID", "name"),
                String.format("FROM %s ", "Author"),
                String.format("WHERE author_ID = ?"));

        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.getConnection().prepareStatement(select_sql));
        prepSelect.setInt(id);

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            if (resultSelect.next()) {
                retAuthor =
                        new Author()
                                .setauthor_ID(resultSelect.getInt("author_ID"))
                                .setname(resultSelect.getString("name"));

            }
        }
        return retAuthor;
    }

    /**
     * SELECT with the index of the associate job class.
     *
     * @author Gaetan Brenckle
     *
     * @param map - {@link HashMap<String, String>} - index of the associate job class. Can handle null.
     * @return - {@link Author} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    public final List<Author> selectByMultiCondition(HashMap<String, String> map) throws SQLException, InvalidKeyException {
        final List<Author> retAuthors = new ArrayList<>();

        if (map == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Author select when the given id is null");
            }
            return retAuthors;
        }


        StringBuilder select_sql = new StringBuilder(String.format("%s %s %s",
                String.format("SELECT %s, %s", "author_ID", "name"),
                String.format("FROM %s ", "Author"),
                String.format("WHERE 1=1")
        ));

        for (Map.Entry<String,String> entry : map.entrySet()) {
            select_sql.append(String.format(" %s ", entry.getKey()));
        }


        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.getConnection().prepareStatement(select_sql.toString()));
        for (Map.Entry<String,String> entry : map.entrySet()) {
            prepSelect.setString(entry.getValue());
        }

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            if (resultSelect.next()) {
                Author Author =
                        new Author()
                                    .setauthor_ID(resultSelect.getInt("author_ID"))
                                    .setname(resultSelect.getString("name"));
                retAuthors.add(Author);
            }
        }
        return retAuthors;
    }

    /**
     * SELECT of all occurance of the Author class.
     *
     * @author Gaetan Brenckle
     *
     * @return - {@link List} - a list that contain all occurance of {@link Author}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    @Override
    public List<Author> selectAll() throws SQLException, InvalidKeyException {
        final List<Author> retAuthors = new ArrayList<>();

        String format = String.format("%s %s",
                String.format("SELECT %s, %s", "author_ID", "name"),
                String.format("FROM %s ", "Author"));
        final String selectAll_sql = format;

        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(connectionHandle_.getConnection().prepareStatement(selectAll_sql));


        try(final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
            if (resultSelectAll.next()) {
                Author Author =
                        new Author()
                                .setauthor_ID(resultSelectAll.getInt("author_ID"))
                                .setname(resultSelectAll.getString("name"));
                retAuthors.add(Author);
            }
        }

        return retAuthors;
    }

    @Override
    public List<Author> selectAll(List<Author> excludeList) throws SQLException, InvalidKeyException {
        return null;
    }

    /**
     * INSERT the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link Author} - insert the job class.
     * @return - boolean - the state of the sql insert.
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    @Override
    public boolean insert(Author obj) throws SQLException, InvalidKeyException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Author"));
            }
            return false;
        }

        if (obj.getauthor_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "author_ID"));
            }
            retBool = false;
        }

        if (obj.getname() == null || obj.getname().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "name"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String insert_sql = String.format("%s %s ",
                String.format("INSERT INTO %s (%s, %s)", "Author", "author_ID", "name"),
                "VALUES (?, ?)");

        final PreparedStatementAware prepInsert = new PreparedStatementAware(connectionHandle_.getConnection().prepareStatement(insert_sql));
        prepInsert.setInt(obj.getauthor_ID());
        prepInsert.setString(obj.getname());

        retBool = prepInsert.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Insert a new %s : [%s]", "Author", prepInsert.printSqlStatement(insert_sql));

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
     * @param obj - {@link Author} - insert the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    @Override
    public boolean update(Author obj) throws SQLException, InvalidKeyException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Author"));
            }
            return false;
        }

        if (obj.getauthor_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "author_ID"));
            }
            retBool = false;
        }

        if (obj.getname() == null || obj.getname().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "name"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String update_sql = String.format("%s %s %s %s ",
                String.format("UPDATE %s", "Author"),
                String.format("SET %s = ?", "author_ID"),
                String.format("SET %s = ?", "name"),
                String.format("WHERE %s = ?", "author_ID"));

        final PreparedStatementAware prepUpdate = new PreparedStatementAware(connectionHandle_.getConnection().prepareStatement(update_sql));
        prepUpdate.setInt(obj.getauthor_ID());
        prepUpdate.setString(obj.getname());

        retBool = prepUpdate.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Update a new %s : [%s]", "Author", prepUpdate.printSqlStatement(update_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }
        return retBool;
    }

    @Override
    public boolean upsert(Author obj) throws SQLException, InvalidKeyException {
        return false;
    }

    /**
     * DELETE the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link Author} - insert the job class.
     * @return - boolean - the state of the sql delete.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean delete(Author obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Author"));
            }
            return false;
        }

        if (obj.getauthor_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "author_ID"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String delete_sql = String.format("%s %s ",
                String.format("DELETE FROM %s", "Author"),
                String.format("WHERE %s = ?", "author_ID"));

        final PreparedStatementAware prepDelete = new PreparedStatementAware(connectionHandle_.getConnection().prepareStatement(delete_sql));
        prepDelete.setInt(obj.getauthor_ID());

        retBool = prepDelete.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Delete a new %s : [%s]", "Author", prepDelete.printSqlStatement(delete_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }

        return retBool;
    }
}
