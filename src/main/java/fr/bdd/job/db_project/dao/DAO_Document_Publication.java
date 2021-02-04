package fr.bdd.job.db_project.dao;

import fr.bdd.custom.sql.PreparedStatementAware;
import fr.bdd.job.dao.Dao;
import fr.bdd.job.db_project.jobclass.Document_Publication;
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
 * Used for the job class {@link Document_Publication}
 *
 * @author Gaetan Brenckle
 */
public class DAO_Document_Publication implements Dao<Document_Publication> {

    private static final CustomLogger LOGGER = CustomLogger.create(DAO_Document_Publication.class.getName());
    private Connection connectionHandle_ = null;

    /**
     * Default constructor.
     * Need to call {@link DAO_Document_Publication#setConnection(Connection)} before any other function.
     *
     * @author Gaetan Brenckle
     */
    public DAO_Document_Publication() {
    }

    /**
     * Constructor with the Connection argument.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link Connection} - connection used.
     */
    public DAO_Document_Publication(Connection conn) {
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
     * @return - {@link Document_Publication} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    public final Document_Publication select(String id) throws SQLException {
        Document_Publication retDocument_Publication = null;
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Publication dao_publication = new DAO_Publication(this.connectionHandle_);
        DAO_Language dao_language = new DAO_Language(this.connectionHandle_);

        if (id == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Document_Publication select when the given id is null");
            }
            return retDocument_Publication;
        }

        final String select_sql = String.format("%s %s %s",
                String.format("SELECT %s, %s, %s", "document_ID", "publication_ID", "language_ID"),
                String.format("FROM %s ", "Document_Publication"),
                String.format("WHERE id = ?"));

        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.prepareStatement(select_sql));
        prepSelect.setString(id);

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            if (resultSelect.next()) {
                retDocument_Publication =
                        new Document_Publication()
                                .setdocument_ID(dao_document.select(resultSelect.getString("document_ID")))
                                .setpublication_ID(dao_publication.select(resultSelect.getInt("publication_ID")))
                                .setlanguage_ID(dao_language.select(resultSelect.getString("language_ID")));

            }
        }
        return retDocument_Publication;
    }

    /**
     * SELECT with the index of the associate job class.
     *
     * @author Gaetan Brenckle
     *
     * @param map - {@link HashMap<String, String>} - index of the associate job class. Can handle null.
     * @return - {@link Document_Publication} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    public final List<Document_Publication> selectByMultiCondition(HashMap<String, String> map) throws SQLException {
        final List<Document_Publication> retCategories = new ArrayList<>();
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Publication dao_publication = new DAO_Publication(this.connectionHandle_);
        DAO_Language dao_language = new DAO_Language(this.connectionHandle_);

        if (map == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Document_Publication select when the given id is null");
            }
            return retCategories;
        }


        StringBuilder select_sql = new StringBuilder(String.format("%s %s %s",
                String.format("SELECT %s, %s, %s", "document_ID", "publication_ID", "language_ID"),
                String.format("FROM %s ", "Document_Publication"),
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
                Document_Publication Document_Publication =
                        new Document_Publication()
                                        .setdocument_ID(dao_document.select(resultSelect.getString("document_ID")))
                                        .setpublication_ID(dao_publication.select(resultSelect.getInt("publication_ID")))
                                        .setlanguage_ID(dao_language.select(resultSelect.getString("language_ID")));
                retCategories.add(Document_Publication);
            }
        }
        return retCategories;
    }

    /**
     * SELECT of all occurance of the Document_Publication class.
     *
     * @author Gaetan Brenckle
     *
     * @return - {@link List} - a list that contain all occurance of {@link Document_Publication}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public List<Document_Publication> selectAll() throws SQLException {
        final List<Document_Publication> retCategories = new ArrayList<>();
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Publication dao_publication = new DAO_Publication(this.connectionHandle_);
        DAO_Language dao_language = new DAO_Language(this.connectionHandle_);

        String format = String.format("%s %s",
                String.format("SELECT %s, %s, %s", "document_ID", "publication_ID", "language_ID"),
                String.format("FROM %s ", "Document_Publication"));
        final String selectAll_sql = format;

        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(connectionHandle_.prepareStatement(selectAll_sql));


        try(final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
            while (resultSelectAll.next()) {
                Document_Publication Document_Publication =
                        new Document_Publication()
                                .setdocument_ID(dao_document.select(resultSelectAll.getString("document_ID")))
                                .setpublication_ID(dao_publication.select(resultSelectAll.getInt("publication_ID")))
                                .setlanguage_ID(dao_language.select(resultSelectAll.getString("language_ID")));
                retCategories.add(Document_Publication);
            }
        }

        return retCategories;
    }

    @Override
    public List<Document_Publication> selectAll(List<Document_Publication> excludeList) throws SQLException {
        return null;
    }

    /**
     * INSERT the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link Document_Publication} - insert the job class.
     * @return - boolean - the state of the sql insert.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean insert(Document_Publication obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Document_Publication"));
            }
            return false;
        }

        if (obj.getdocument_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "document_ID"));
            }
            retBool = false;
        }

        if (obj.getpublication_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "publication_ID"));
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
                String.format("INSERT INTO %s (%s, %s, %s)", "Document_Publication", "document_ID", "publication_ID", "language_ID"),
                "VALUES (?, ?, ?)");

        final PreparedStatementAware prepInsert = new PreparedStatementAware(connectionHandle_.prepareStatement(insert_sql));
        prepInsert.setString(obj.getdocument_ID().getdocument_ID());
        prepInsert.setInt(obj.getpublication_ID().getpublication_ID());
        prepInsert.setString(obj.getlanguage_ID().getlanguage_ID());

        retBool = prepInsert.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Insert a new %s : [%s]", "Document_Publication", prepInsert.printSqlStatement(insert_sql));

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
     * @param obj - {@link Document_Publication} - insert the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean update(Document_Publication obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Document_Publication"));
            }
            return false;
        }

        if (obj.getdocument_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "document_ID"));
            }
            retBool = false;
        }

        if (obj.getpublication_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "publication_ID"));
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

        String update_sql = String.format("%s %s %s %s %s ",
                String.format("UPDATE %s", "Document_Publication"),
                String.format("SET %s = ?", "document_ID"),
                String.format("SET %s = ?", "publication_ID"),
                String.format("SET %s = ?", "language_ID"),
                String.format("WHERE %s = ?", "document_ID"));

        final PreparedStatementAware prepUpdate = new PreparedStatementAware(connectionHandle_.prepareStatement(update_sql));
        prepUpdate.setString(obj.getdocument_ID().getdocument_ID());
        prepUpdate.setInt(obj.getpublication_ID().getpublication_ID());
        prepUpdate.setString(obj.getlanguage_ID().getlanguage_ID());

        retBool = prepUpdate.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Update a new %s : [%s]", "Document_Publication", prepUpdate.printSqlStatement(update_sql));

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
     * @param obj - {@link Document_Publication} - insert the job class.
     * @return - boolean - the state of the sql delete.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean delete(Document_Publication obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Document_Publication"));
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
                String.format("DELETE FROM %s", "Document_Publication"),
                String.format("WHERE %s = ?", "document_ID"));

        final PreparedStatementAware prepDelete = new PreparedStatementAware(connectionHandle_.prepareStatement(delete_sql));
        prepDelete.setString(obj.getdocument_ID().getdocument_ID());

        retBool = prepDelete.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Delete a new %s : [%s]", "Document_Publication", prepDelete.printSqlStatement(delete_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }

        return retBool;
    }
}
