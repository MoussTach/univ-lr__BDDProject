package fr.bdd.job.db_project.dao;

import fr.bdd.custom.sql.PreparedStatementAware;
import fr.bdd.job.dao.Dao;
import fr.bdd.job.db_project.jobclass.Document;
import fr.bdd.job.db_project.jobclass.Document_Language;
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
 * Used for the job class {@link Document_Language}
 *
 * @author Gaetan Brenckle
 */
public class DAO_Document_Language implements Dao<Document_Language> {

    private static final CustomLogger LOGGER = CustomLogger.create(DAO_Document_Language.class.getName());
    private Connection connectionHandle_ = null;

    /**
     * Default constructor.
     * Need to call {@link DAO_Document_Language#setConnection(Connection)} before any other function.
     *
     * @author Gaetan Brenckle
     */
    public DAO_Document_Language() {
    }

    /**
     * Constructor with the Connection argument.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link Connection} - connection used.
     */
    public DAO_Document_Language(Connection conn) {
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
     * @return - {@link Document_Language} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    public final Document_Language select(String id) throws SQLException {
        Document_Language retDocument_Language = null;
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Language dao_language = new DAO_Language(this.connectionHandle_);

        if (id == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Document_Language select when the given id is null");
            }
            return retDocument_Language;
        }

        final String select_sql = String.format("%s %s %s",
                String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s", "document_ID", "language_ID", "title", "subTitle", "description", "resume", "notes"),
                String.format("FROM %s ", "Document_Language"),
                String.format("WHERE id = ?"));

        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.prepareStatement(select_sql));
        prepSelect.setString(id);

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            if (resultSelect.next()) {
                retDocument_Language =
                        new Document_Language()
                                .setdocument_ID(dao_document.select(resultSelect.getString("document_ID")))
                                .setlanguage_ID(dao_language.select(resultSelect.getString("language_ID")))
                                .settitle(resultSelect.getString("title"))
                                .setsubTitle(resultSelect.getString("subTitle"))
                                .setsubject(resultSelect.getString("subject"))
                                .setdescription(resultSelect.getString("description"))
                                .setresume(resultSelect.getString("resume"))
                                .setnotes(resultSelect.getString("notes"));

            }
        }
        return retDocument_Language;
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
    public final List<Document_Language> selectByMultiCondition(HashMap<String, String> map) throws SQLException {
        final List<Document_Language> retCategories = new ArrayList<>();
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Language dao_language = new DAO_Language(this.connectionHandle_);

        if (map == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Document_Language select when the given id is null");
            }
            return retCategories;
        }


        StringBuilder select_sql = new StringBuilder(String.format("%s %s %s",
                String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s", "document_ID", "language_ID", "title", "subTitle", "description", "resume", "notes"),
                String.format("FROM %s ", "Document_Language"),
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
                Document_Language Document_Language =
                        new Document_Language()
                                .setdocument_ID(dao_document.select(resultSelect.getString("document_ID")))
                                .setlanguage_ID(dao_language.select(resultSelect.getString("language_ID")))
                                .settitle(resultSelect.getString("title"))
                                .setsubTitle(resultSelect.getString("subTitle"))
                                .setsubject(resultSelect.getString("subject"))
                                .setdescription(resultSelect.getString("description"))
                                .setresume(resultSelect.getString("resume"))
                                .setnotes(resultSelect.getString("notes"));
                retCategories.add(Document_Language);
            }
        }
        return retCategories;
    }

    /**
     * SELECT of all occurance of the Document_Language class.
     *
     * @author Gaetan Brenckle
     *
     * @param id - {@link Document} - index of the associate job class. Can handle null.
     * @return - {@link List} - a list that contain all occurance of {@link Document_Language}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    public List<Document_Language> selectAll_document(Document id) throws SQLException {
        final List<Document_Language> retDocumentLanguage = new ArrayList<>();
        DAO_Language dao_language = new DAO_Language(this.connectionHandle_);

        if (id == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s select when the given id is null", "Document_Language"));
            }
            return retDocumentLanguage;
        }

        final String selectAll_sql = String.format("%s %s %s",
                String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s", "document_ID", "language_ID", "title", "subTitle", "subject", "description", "resume", "notes"),
                String.format("FROM %s ", "Document_Language"),
                String.format("WHERE %s = ? ", "document_ID"));;

        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(connectionHandle_.prepareStatement(selectAll_sql));
        prepSelectAll.setString(1, id.getdocument_ID());


        try(final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
            while (resultSelectAll.next()) {
                Document_Language document_Language =
                        new Document_Language()
                                .setlanguage_ID(dao_language.select(resultSelectAll.getString("language_ID")))
                                .settitle(resultSelectAll.getString("title"))
                                .setsubTitle(resultSelectAll.getString("subTitle"))
                                .setsubject(resultSelectAll.getString("subject"))
                                .setdescription(resultSelectAll.getString("description"))
                                .setresume(resultSelectAll.getString("resume"))
                                .setnotes(resultSelectAll.getString("notes"));

                document_Language.setdocument_ID(id);
                retDocumentLanguage.add(document_Language);
            }
        }

        return retDocumentLanguage;
    }

    /**
     * SELECT of all occurance of the Document_Language class.
     *
     * @author Gaetan Brenckle
     *
     * @return - {@link List} - a list that contain all occurance of {@link Document_Language}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public List<Document_Language> selectAll() throws SQLException {
        final List<Document_Language> retDocumentLanguage = new ArrayList<>();
        DAO_Document dao_document = new DAO_Document(this.connectionHandle_);
        DAO_Language dao_language = new DAO_Language(this.connectionHandle_);

        String format = String.format("%s %s",
                String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s", "document_ID", "language_ID", "title", "subTitle", "description", "resume", "notes"),
                String.format("FROM %s ", "Document_Language"));
        final String selectAll_sql = format;

        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(connectionHandle_.prepareStatement(selectAll_sql));


        try(final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
            while (resultSelectAll.next()) {
                Document_Language Document_Language =
                        new Document_Language()
                                .setdocument_ID(dao_document.select(resultSelectAll.getString("document_ID")))
                                .setlanguage_ID(dao_language.select(resultSelectAll.getString("language_ID")))
                                .settitle(resultSelectAll.getString("title"))
                                .setsubTitle(resultSelectAll.getString("subTitle"))
                                .setsubject(resultSelectAll.getString("subject"))
                                .setdescription(resultSelectAll.getString("description"))
                                .setresume(resultSelectAll.getString("resume"))
                                .setnotes(resultSelectAll.getString("notes"));
                retDocumentLanguage.add(Document_Language);
            }
        }

        return retDocumentLanguage;
    }

    @Override
    public List<Document_Language> selectAll(List<Document_Language> excludeList) throws SQLException {
        return null;
    }

    /**
     * INSERT the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link Document_Language} - insert the job class.
     * @return - boolean - the state of the sql insert.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean insert(Document_Language obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Document_Language"));
            }
            return false;
        }

        if (obj.getdocument_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "document_ID"));
            }
            retBool = false;
        }

        if (obj.getlanguage_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "language_ID"));
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

        String insert_sql = String.format("%s %s ",
                String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s)", "Document_Language", "document_ID", "language_ID", "title", "subTitle", "description", "resume", "notes"),
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

        final PreparedStatementAware prepInsert = new PreparedStatementAware(connectionHandle_.prepareStatement(insert_sql));
        prepInsert.setString(obj.getdocument_ID().getdocument_ID());
        prepInsert.setString(obj.getlanguage_ID().getlanguage_ID());
        prepInsert.setString(obj.gettitle());
        prepInsert.setString(obj.getsubTitle());
        prepInsert.setString(obj.getsubject());
        prepInsert.setString(obj.getdescription());
        prepInsert.setString(obj.getresume());
        prepInsert.setString(obj.getnotes());

        retBool = prepInsert.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Insert a new %s : [%s]", "Document_Language", prepInsert.printSqlStatement(insert_sql));

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
     * @param obj - {@link Document_Language} - insert the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean update(Document_Language obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Document_Language"));
            }
            return false;
        }

        if (obj.getdocument_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "document_ID"));
            }
            retBool = false;
        }

        if (obj.getlanguage_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "language_ID"));
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

        String update_sql = String.format("%s %s %s %s %s %s %s %s %s %s ",
                String.format("UPDATE %s", "Document_Language"),
                String.format("SET %s = ?", "document_ID"),
                String.format("SET %s = ?", "language_ID"),
                String.format("SET %s = ?", "title"),
                String.format("SET %s = ?", "subTitle"),
                String.format("SET %s = ?", "subject"),
                String.format("SET %s = ?", "description"),
                String.format("SET %s = ?", "resume"),
                String.format("SET %s = ?", "notes"),
                String.format("WHERE %s = ?", "document_ID"));

        final PreparedStatementAware prepUpdate = new PreparedStatementAware(connectionHandle_.prepareStatement(update_sql));
        prepUpdate.setString(obj.getdocument_ID().getdocument_ID());
        prepUpdate.setString(obj.getlanguage_ID().getlanguage_ID());
        prepUpdate.setString(obj.gettitle());
        prepUpdate.setString(obj.getsubTitle());
        prepUpdate.setString(obj.getsubject());
        prepUpdate.setString(obj.getdescription());
        prepUpdate.setString(obj.getresume());
        prepUpdate.setString(obj.getnotes());

        retBool = prepUpdate.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Update a new %s : [%s]", "Document_Language", prepUpdate.printSqlStatement(update_sql));

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
     * @param obj - {@link Document_Language} - insert the job class.
     * @return - boolean - the state of the sql delete.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean delete(Document_Language obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Document_Language"));
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
                String.format("DELETE FROM %s", "Document_Language"),
                String.format("WHERE %s = ?", "document_ID"));

        final PreparedStatementAware prepDelete = new PreparedStatementAware(connectionHandle_.prepareStatement(delete_sql));
        prepDelete.setString(obj.getdocument_ID().getdocument_ID());

        retBool = prepDelete.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Delete a new %s : [%s]", "Document_Language", prepDelete.printSqlStatement(delete_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }

        return retBool;
    }
}
