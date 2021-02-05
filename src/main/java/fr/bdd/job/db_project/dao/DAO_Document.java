package fr.bdd.job.db_project.dao;

import fr.bdd.custom.sql.PreparedStatementAware;
import fr.bdd.job.dao.Dao;
import fr.bdd.job.db_project.jobclass.Document;
import fr.bdd.log.generate.CustomLogger;
import javafx.util.Pair;

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
 * Used for the job class {@link Document}
 *
 * @author Gaetan Brenckle
 */
public class DAO_Document implements Dao<Document> {

    private static final CustomLogger LOGGER = CustomLogger.create(DAO_Document.class.getName());
    private Connection connectionHandle_ = null;

    /**
     * Default constructor.
     * Need to call {@link DAO_Document#setConnection(Connection)} before any other function.
     *
     * @author Gaetan Brenckle
     */
    public DAO_Document() {
    }

    /**
     * Constructor with the Connection argument.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link Connection} - connection used.
     */
    public DAO_Document(Connection conn)  {
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
     * @return - {@link Document} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    public final Document select(String id) throws SQLException {
        Document retDocument = null;
        DAO_Category dao_category = new DAO_Category(this.connectionHandle_);
        DAO_NatureOfDoc dao_natureOfDoc = new DAO_NatureOfDoc(this.connectionHandle_);
        DAO_Condition dao_condition = new DAO_Condition(this.connectionHandle_);
        DAO_Person dao_person = new DAO_Person(this.connectionHandle_);

        DAO_Document_Language dao_document_language = new DAO_Document_Language(this.connectionHandle_);
        //DAO_Publication dao_publication = new DAO_Publication(this.connectionHandle_);
        DAO_Document_Author dao_document_author = new DAO_Document_Author(this.connectionHandle_);

        //DAO_AuthDesc dao_authDesc = new DAO_AuthDesc(this.connectionHandle_);
        //DAO_AuthAnalyst dao_authAnalyst = new DAO_AuthAnalyst(this.connectionHandle_);
        //DAO_AuthTranscript dao_authTranscript = new DAO_AuthTranscript(this.connectionHandle_);
        //DAO_Document_Project dao_document_project = new DAO_Document_Project(this.connectionHandle_);

        if (id == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Document select when the given id is null");
            }
            return retDocument;
        }

        final String select_sql = String.format("%s %s %s",
                String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s", "document_ID", "category_ID", "dateCreation_start", "dateCreation_end", "natureOfDoc_ID", "condition_ID", "format", "location", "geographicLocation", "rights", "copyright", "shippingLocation", "recipient", "representation", "otherRelatedResources"),
                String.format("FROM %s ", "Document"),
                String.format("WHERE document_ID = ?"));

        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.prepareStatement(select_sql));
        prepSelect.setString(id);

        try (final ResultSet resultSelect = prepSelect.executeQuery()) {
            if (resultSelect.next()) {
                retDocument =
                        new Document()
                                .setdocument_ID(resultSelect.getString("document_ID"))
                                .setcategory_ID(dao_category.select(resultSelect.getString("category_ID")))
                                .setdateCreation_start(resultSelect.getDate("dateCreation_start").toLocalDate())
                                .setdateCreation_end(resultSelect.getDate("dateCreation_end").toLocalDate())
                                .setnatureOfDoc_ID(dao_natureOfDoc.select(resultSelect.getString("natureOfDoc_ID")))
                                .setcondition_ID(dao_condition.select(resultSelect.getString("condition_ID")))
                                .setformat(resultSelect.getString("format"))
                                .setlocation(resultSelect.getString("location"))
                                .setgeographicLocation(resultSelect.getString("geographicLocation"))
                                .setrights(resultSelect.getString("rights"))
                                .setcopyright(resultSelect.getString("copyright"))
                                .setshippingLocation(resultSelect.getString("shippingLocation"))
                                .setRecipient(dao_person.select(resultSelect.getInt("recipient")))
                                .setrepresentation(resultSelect.getString("representation"))
                                .setotherRelatedResources(resultSelect.getString("otherRelatedResources"));

                retDocument.document_LanguageProperty().addAll(dao_document_language.selectAll_document(retDocument));
                retDocument.document_AuthorProperty().addAll(dao_document_author.selectAll_document(retDocument));

            }
            return retDocument;
        }
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
    public final List<Document> selectByMultiCondition(HashMap<String, Pair<?, PreparedStatementAware.listType>> map) throws SQLException {
        final List<Document> retCategories = new ArrayList<>();
        DAO_Category dao_category = new DAO_Category(this.connectionHandle_);
        DAO_NatureOfDoc dao_natureOfDoc = new DAO_NatureOfDoc(this.connectionHandle_);
        DAO_Condition dao_condition = new DAO_Condition(this.connectionHandle_);
        DAO_Person dao_person = new DAO_Person(this.connectionHandle_);

        DAO_Document_Language dao_document_language = new DAO_Document_Language(this.connectionHandle_);
        DAO_Document_Author dao_document_author = new DAO_Document_Author(this.connectionHandle_);


        if (map == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Document select when the given id is null");
            }
            return retCategories;
        }


        StringBuilder select_sql = new StringBuilder(String.format("%s %s %s",
                String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s", "document_ID", "category_ID", "dateCreation_start", "dateCreation_end", "natureOfDoc_ID", "condition_ID", "format", "location", "geographicLocation", "rights", "copyright", "shippingLocation", "recipient", "representation", "otherRelatedResources"),
                String.format("FROM %s ", "Document"),
                String.format("WHERE 1=1")
        ));

        for (Map.Entry<String,Pair<?, PreparedStatementAware.listType>> entry : map.entrySet()) {
            select_sql.append(String.format(" %s ", entry.getKey()));
        }


        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.prepareStatement(select_sql.toString()));
        for (Map.Entry<String, Pair<?, PreparedStatementAware.listType>> entry : map.entrySet()) {

            switch (entry.getValue().getValue()) {
                case STRING:
                    prepSelect.setString(String.valueOf(entry.getValue().getKey()));
                    break;
                case INT:
                    prepSelect.setInt(Integer.parseInt(String.valueOf(entry.getValue().getKey())));
                    break;
                case DOUBLE:
                    prepSelect.setDouble(Double.parseDouble(String.valueOf(entry.getValue().getKey())));
                    break;
                case LONG:
                    prepSelect.setLong(Long.parseLong(String.valueOf(entry.getValue().getKey())));
                    break;
                case BOOLEAN:
                    prepSelect.setBoolean(Boolean.parseBoolean(String.valueOf(entry.getValue().getKey())));
                    break;
                case DATE:
                    prepSelect.setDate(java.sql.Date.valueOf(String.valueOf(entry.getValue().getKey())));
                    break;
            }
        }

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            while (resultSelect.next()) {
                Document document =
                        new Document()
                                .setdocument_ID(resultSelect.getString("document_ID"))
                                .setcategory_ID(dao_category.select(resultSelect.getString("category_ID")))
                                .setnatureOfDoc_ID(dao_natureOfDoc.select(resultSelect.getString("natureOfDoc_ID")))
                                .setcondition_ID(dao_condition.select(resultSelect.getString("condition_ID")))
                                .setformat(resultSelect.getString("format"))
                                .setlocation(resultSelect.getString("location"))
                                .setgeographicLocation(resultSelect.getString("geographicLocation"))
                                .setrights(resultSelect.getString("rights"))
                                .setcopyright(resultSelect.getString("copyright"))
                                .setshippingLocation(resultSelect.getString("shippingLocation"))
                                .setRecipient(dao_person.select(resultSelect.getInt("recipient")))
                                .setrepresentation(resultSelect.getString("representation"))
                                .setotherRelatedResources(resultSelect.getString("otherRelatedResources"));

                if (resultSelect.getDate("dateCreation_start") != null)
                    document.setdateCreation_start(resultSelect.getDate("dateCreation_start").toLocalDate());
                else
                    document.setdateCreation_start(null);

                if (resultSelect.getDate("dateCreation_end") != null)
                    document.setdateCreation_end(resultSelect.getDate("dateCreation_end").toLocalDate());
                else
                    document.setdateCreation_end(null);

                document.document_LanguageProperty().addAll(dao_document_language.selectAll_document(document));
                document.document_AuthorProperty().addAll(dao_document_author.selectAll_document(document));
                retCategories.add(document);
            }
        }
        return retCategories;
    }

    /**
     * SELECT of all occurance of the Document class.
     *
     * @author Gaetan Brenckle
     *
     * @return - {@link List} - a list that contain all occurance of {@link Document}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public List<Document> selectAll() throws SQLException {
        final List<Document> retCategories = new ArrayList<>();
        DAO_Category dao_category = new DAO_Category(this.connectionHandle_);
        DAO_NatureOfDoc dao_natureOfDoc = new DAO_NatureOfDoc(this.connectionHandle_);
        DAO_Condition dao_condition = new DAO_Condition(this.connectionHandle_);
        DAO_Person dao_person = new DAO_Person(this.connectionHandle_);

        String format = String.format("%s %s",
                String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s", "document_ID", "category_ID", "dateCreation_start", "dateCreation_end", "natureOfDoc_ID", "condition_ID", "format", "location", "geographicLocation", "rights", "copyright", "shippingLocation", "recipient", "representation", "otherRelatedResources"),
                String.format("FROM %s ", "Document"));
        final String selectAll_sql = format;

        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(connectionHandle_.prepareStatement(selectAll_sql));


        try(final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
            while (resultSelectAll.next()) {
                Document document =
                        new Document()
                                .setdocument_ID(resultSelectAll.getString("document_ID"))
                                .setcategory_ID(dao_category.select(resultSelectAll.getString("category_ID")))
                                .setnatureOfDoc_ID(dao_natureOfDoc.select(resultSelectAll.getString("natureOfDoc_ID")))
                                .setcondition_ID(dao_condition.select(resultSelectAll.getString("condition_ID")))
                                .setformat(resultSelectAll.getString("format"))
                                .setlocation(resultSelectAll.getString("location"))
                                .setgeographicLocation(resultSelectAll.getString("geographicLocation"))
                                .setrights(resultSelectAll.getString("rights"))
                                .setcopyright(resultSelectAll.getString("copyright"))
                                .setshippingLocation(resultSelectAll.getString("shippingLocation"))
                                .setRecipient(dao_person.select(resultSelectAll.getInt("recipient")))
                                .setrepresentation(resultSelectAll.getString("representation"))
                                .setotherRelatedResources(resultSelectAll.getString("otherRelatedResources"));

                if (resultSelectAll.getDate("dateCreation_start") != null)
                    document.setdateCreation_start(resultSelectAll.getDate("dateCreation_start").toLocalDate());
                else
                    document.setdateCreation_start(null);

                if (resultSelectAll.getDate("dateCreation_end") != null)
                    document.setdateCreation_end(resultSelectAll.getDate("dateCreation_end").toLocalDate());
                else
                    document.setdateCreation_end(null);
                retCategories.add(document);
            }
        }

        return retCategories;
    }

    @Override
    public List<Document> selectAll(List<Document> excludeList) throws SQLException {
        return null;
    }

    /**
     * INSERT the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link Document} - insert the job class.
     * @return - boolean - the state of the sql insert.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean insert(Document obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Document"));
            }
            return false;
        }

        if (obj.getdocument_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "document_ID"));
            }
            retBool = false;
        }

        if (obj.getcategory_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "category_ID"));
            }
            retBool = false;
        }

        if (obj.getdateCreation_end() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "Document", "dateCreation_end", "natureOfDoc_ID", "condition_ID", "format", "location"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        String insert_sql = String.format("%s %s ",
                String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s)", "Document", "document_ID", "category_ID", "Document", "dateCreation_end", "natureOfDoc_ID", "condition_ID", "format", "location"),
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        final PreparedStatementAware prepInsert = new PreparedStatementAware(connectionHandle_.prepareStatement(insert_sql));
        prepInsert.setString(obj.getdocument_ID());
        prepInsert.setString(obj.getcategory_ID().getcategory_ID());
        prepInsert.setDate(java.sql.Date.valueOf(obj.getdateCreation_start()));
        prepInsert.setDate(java.sql.Date.valueOf(obj.getdateCreation_end()));
        prepInsert.setString(obj.getnatureOfDoc_ID().getnatureOfDoc_ID());
        prepInsert.setString(obj.getcondition_ID().getcondition_ID());
        prepInsert.setString(obj.getformat());
        prepInsert.setString(obj.getlocation());
        prepInsert.setString(obj.getgeographicLocation());
        prepInsert.setString(obj.getrights());
        prepInsert.setString(obj.getcopyright());
        prepInsert.setString(obj.getshippingLocation());
        prepInsert.setInt(obj.getRecipient().getperson_ID());
        prepInsert.setString(obj.getrepresentation());
        prepInsert.setString(obj.getotherRelatedResources());

        retBool = prepInsert.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Insert a new %s : [%s]", "Document", prepInsert.printSqlStatement(insert_sql));

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
     * @param obj - {@link Document} - insert the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean update(Document obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Document"));
            }
            return false;
        }

        if (obj.getdocument_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "document_ID"));
            }
            retBool = false;
        }

        if (obj.getcategory_ID() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "category_ID"));
            }
            retBool = false;
        }

        if (obj.getdateCreation_end() == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", "dateCreation_end"));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }
        String update_sql = String.format("%s %s %s %s %s ",
                String.format("UPDATE %s", "Document"),
                String.format("SET %s = ?", "document_ID"),
                String.format("SET %s = ?", "category_ID"),
                String.format("SET %s = ?", "Document"),
                String.format("SET %s = ?", "dateCreation_end"),
                String.format("SET %s = ?", "natureOfDoc_ID"),
                String.format("SET %s = ?", "condition_ID"),
                String.format("SET %s = ?", "format"),
                String.format("SET %s = ?", "location"),
                String.format("SET %s = ?", "geographicLocation"),
                String.format("SET %s = ?", "rights"),
                String.format("SET %s = ?", "copyright"),
                String.format("SET %s = ?", "shippingLocation"),
                String.format("SET %s = ?", "Recipient"),
                String.format("SET %s = ?", "representation"),
                String.format("SET %s = ?", "otherRelatedResources"),
                String.format("WHERE %s = ?", "document_ID"));

        final PreparedStatementAware prepUpdate = new PreparedStatementAware(connectionHandle_.prepareStatement(update_sql));
        prepUpdate.setString(obj.getdocument_ID());
        prepUpdate.setString(obj.getcategory_ID().getcategory_ID());
        prepUpdate.setDate(java.sql.Date.valueOf(obj.getdateCreation_start()));
        prepUpdate.setDate(java.sql.Date.valueOf(obj.getdateCreation_end()));
        prepUpdate.setString(obj.getnatureOfDoc_ID().getnatureOfDoc_ID());
        prepUpdate.setString(obj.getcondition_ID().getcondition_ID());
        prepUpdate.setString(obj.getformat());
        prepUpdate.setString(obj.getlocation());
        prepUpdate.setString(obj.getgeographicLocation());
        prepUpdate.setString(obj.getrights());
        prepUpdate.setString(obj.getcopyright());
        prepUpdate.setString(obj.getshippingLocation());
        prepUpdate.setInt(obj.getRecipient().getperson_ID());
        prepUpdate.setString(obj.getrepresentation());
        prepUpdate.setString(obj.getotherRelatedResources());

        retBool = prepUpdate.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Update a new %s : [%s]", "Document", prepUpdate.printSqlStatement(update_sql));

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
     * @param obj - {@link Document} - insert the job class.
     * @return - boolean - the state of the sql delete.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean delete(Document obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", "Document"));
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
                String.format("DELETE FROM %s", "Document"),
                String.format("WHERE %s = ?", "document_ID"));

        final PreparedStatementAware prepDelete = new PreparedStatementAware(connectionHandle_.prepareStatement(delete_sql));
        prepDelete.setString(obj.getdocument_ID());

        retBool = prepDelete.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Delete a new %s : [%s]", "Document", prepDelete.printSqlStatement(delete_sql));

            LOGGER.info(printedSql);
            // DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }

        return retBool;
    }
}
