package fr.bdd.job.db_project.dao;

import fr.bdd.custom.sql.PreparedStatementAware;
import fr.bdd.job.dao.Dao;
import fr.bdd.job.db_project.jobclass.Document_Author;
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
 * Used for the job class {@link Document_Author}
 *
 * @Document_Author Gaetan Brenckle
 */
public class DAO_Document_Author implements Dao<Document_Author> {

    private static final CustomLogger LOGGER = CustomLogger.create(DAO_Document_Author.class.getName());
    private Connection connectionHandle_ = null;

    /**
     * Default constructor.
     * Need to call {@link DAO_Document_Author#setConnection(Connection)} before any other function.
     *
     * @Document_Author Gaetan Brenckle
     */
    public DAO_Document_Author() {
    }

    /**
     * Constructor with the Connection argument.
     *
     * @Document_Author Gaetan Brenckle
     *
     * @param conn - {@link Connection} - connection used.
     */
    public DAO_Document_Author(Connection conn)  {
        this.connectionHandle_ = conn;
    }

    /**
     * Setter of the connection.
     *
     * @Document_Author Gaetan Brenckle
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
     * @Document_Author Gaetan Brenckle
     *
     * @param id - {@link Integer} - index of the associate job class. Can handle null.
     * @return - {@link Document_Author} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    public final Document_Author select(Integer id) throws SQLException {
        Document_Author retDocument_Author = null;
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Author dao_author = new DAO_Author(this.connectionHandle_);

        if (id == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Document_Author select when the given id is null");
            }
            return retDocument_Author;
        }

        final String select_sql = String.format("%s %s %s",
                String.format("SELECT %s, %s", "document_ID", "author_ID"),
                String.format("FROM %s ", "Document_Author"),
                String.format("WHERE document_ID = ?"));

        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.prepareStatement(select_sql));
        prepSelect.setInt(id);

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            if (resultSelect.next()) {
                retDocument_Author =
                        new Document_Author()
                                .setdocument_ID(dao_document.select(resultSelect.getString("document_ID")))
                                .setauthor_ID(dao_author.select(resultSelect.getInt("author_ID")));

            }
        }
        return retDocument_Author;
    }

    /**
     * SELECT with the index of the associate job class.
     *
     * @Document_Author Gaetan Brenckle
     *
     * @param map - {@link HashMap<String, String>} - index of the associate job class. Can handle null.
     * @return - {@link Document_Author} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
    */
    public final List<Document_Author> selectByMultiCondition(HashMap<String, String> map) throws SQLException {
        final List<Document_Author> retDocument_Authors = new ArrayList<>();
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Author dao_author = new DAO_Author(this.connectionHandle_);

        if (map == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Document_Author select when the given id is null");
            }
            return retDocument_Authors;
        }


        StringBuilder select_sql = new StringBuilder(String.format("%s %s %s",
                String.format("SELECT %s, %s", "document_ID", "author_ID"),
                String.format("FROM %s ", "Document_Author"),
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
                Document_Author Document_Author =
                        new Document_Author()
                                    .setdocument_ID(dao_document.select(resultSelect.getString("document_ID")))
                                    .setauthor_ID(dao_author.select(resultSelect.getInt("author_ID")));
                retDocument_Authors.add(Document_Author);
            }
        }
        return retDocument_Authors;
    }

    /**
     * SELECT of all occurance of the Document_Author class.
     *
     * @Document_Author Gaetan Brenckle
     *
     * @return - {@link List} - a list that contain all occurance of {@link Document_Author}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
    */
    @Override
    public List<Document_Author> selectAll() throws SQLException {
        final List<Document_Author> retDocument_Authors = new ArrayList<>();
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Author dao_author = new DAO_Author(this.connectionHandle_);

        String format = String.format("%s %s",
                String.format("SELECT %s, %s", "document_ID", "author_ID"),
                String.format("FROM %s ", "Document_Author"));
        final String selectAll_sql = format;

        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(connectionHandle_.prepareStatement(selectAll_sql));

        try(final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
            while (resultSelectAll.next()) {
                Document_Author Document_Author =
                        new Document_Author()
                                .setdocument_ID(dao_document.select(resultSelectAll.getString("document_ID")))
                                .setauthor_ID(dao_author.select(resultSelectAll.getInt("author_ID")));
                retDocument_Authors.add(Document_Author);
            }
        }

        return retDocument_Authors;
    }

    @Override
    public List<Document_Author> selectAll(List<Document_Author> excludeList) throws SQLException {
        return null;
    }

    /**
     * INSERT the job class.
     * Cannot use (ODBC table)
     *
     * @Document_Author Gaetan Brenckle
     *
     * @param obj - {@link Document_Author} - insert the job class.
     * @return - boolean - the state of the sql insert.
     * @throws SQLException - throw the exception to force a try catch when used.
*/
    @Override
    public boolean insert(Document_Author obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Document_Author"));
            }
            return false;
        }

        if (obj.getdocument_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "document_ID"));
            }
            retBool = false;
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

        String insert_sql = String.format("%s %s ",
                String.format("INSERT INTO %s (%s, %s)", "Document_Author", "document_ID", "author_ID"),
                "VALUES (?, ?)");

        final PreparedStatementAware prepInsert = new PreparedStatementAware(connectionHandle_.prepareStatement(insert_sql));
        prepInsert.setString(obj.getdocument_ID().getdocument_ID());
        prepInsert.setInt(obj.getauthor_ID().getauthor_ID());

        retBool = prepInsert.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Insert a new %s : [%s]", "Document_Author", prepInsert.printSqlStatement(insert_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }
        return retBool;
    }

    /**
     * UPDATE the job class.
     * Cannot use (ODBC table)
     *
     * @Document_Author Gaetan Brenckle
     *
     * @param obj - {@link Document_Author} - insert the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
*/
    @Override
    public boolean update(Document_Author obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Document_Author"));
            }
            return false;
        }

        if (obj.getdocument_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "document_ID"));
            }
            retBool = false;
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

        String update_sql = String.format("%s %s %s %s ",
                String.format("UPDATE %s", "Document_Author"),
                String.format("SET %s = ?", "document_ID"),
                String.format("SET %s = ?", "author_ID"),
                String.format("WHERE %s = ?", "document_ID"));

        final PreparedStatementAware prepUpdate = new PreparedStatementAware(connectionHandle_.prepareStatement(update_sql));
        prepUpdate.setString(obj.getdocument_ID().getdocument_ID());
        prepUpdate.setInt(obj.getauthor_ID().getauthor_ID());

        retBool = prepUpdate.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Update a new %s : [%s]", "Document_Author", prepUpdate.printSqlStatement(update_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }
        return retBool;
    }


    /**
     * DELETE the job class.
     * Cannot use (ODBC table)
     *
     * @Document_Author Gaetan Brenckle
     *
     * @param obj - {@link Document_Author} - insert the job class.
     * @return - boolean - the state of the sql delete.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean delete(Document_Author obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Document_Author"));
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
                String.format("DELETE FROM %s", "Document_Author"),
                String.format("WHERE %s = ?", "document_ID"));

        final PreparedStatementAware prepDelete = new PreparedStatementAware(connectionHandle_.prepareStatement(delete_sql));
        prepDelete.setString(obj.getdocument_ID().getdocument_ID());

        retBool = prepDelete.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Delete a new %s : [%s]", "Document_Author", prepDelete.printSqlStatement(delete_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }

        return retBool;
    }
}
