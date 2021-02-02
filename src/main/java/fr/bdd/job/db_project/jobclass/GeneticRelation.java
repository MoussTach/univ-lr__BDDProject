package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;

import java.util.Date;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class GeneticRelation {

    private final ObjectProperty<Document> document_ID_src = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Document> document_ID_dest = new SimpleObjectProperty<>(null);
    private final StringProperty geneticStatus = new SimpleStringProperty("n/a");

    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public GeneticRelation() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link GeneticRelation}
     */
    public GeneticRelation(GeneticRelation clone) {

        if (clone != null) {

            this.document_ID_src.set(clone.document_ID_src.get());

            this.document_ID_dest.set(clone.document_ID_dest.get());
            this.geneticStatus.set(clone.geneticStatus.get());
        }
    }

    /**
     * Getter for the variable document_ID_src.
     *
     * @return {@link Document} - return the variable document_ID_src.
     */
    public Document getdocument_ID_src() {
        return document_ID_src.get();
    }

    /**
     * Getter for the variable document_ID_dest.
     *
     * @return {@link Document} - return the variable document_ID_dest.
     */
    public Document getdocument_ID_dest() {
        return document_ID_dest.get();
    }

    /**
     * Getter for the variable geneticStatus.
     *
     * @return String - return the variable geneticStatus.
     */
    public String getgeneticStatus() {
        return geneticStatus.get();
    }

    /**
     * Setter for the variable document_ID_src.
     *
     * @param document_ID_src - {@link Document} - index of the GeneticRelation class.
     * @return - {@link GeneticRelation} - builder pattern return
     */
    public GeneticRelation setdocument_ID_src(Document document_ID_src) {
        this.document_ID_src.set(document_ID_src);
        return this;
    }

    /**
     * Setter for the variable document_ID_dest.
     *
     * @param document_ID_dest - {@link Document} - document_ID_dest of this class.
     * @return - {@link GeneticRelation} - builder pattern return
     */
    public GeneticRelation setdocument_ID_dest(Document document_ID_dest) {
        this.document_ID_dest.set(document_ID_dest);
        return this;
    }

    /**
     * Setter for the variable geneticStatus.
     *
     * @param geneticStatus - {@link String} - geneticStatus of this class.
     * @return - {@link GeneticRelation} - builder pattern return
     */
    public GeneticRelation setgeneticStatus(String geneticStatus) {
        this.geneticStatus.set(geneticStatus);
        return this;
    }

    /**
     * Property of the variable document_ID_src.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<Document>} - return the property of the variable document_ID_src.
     */
    public ObjectProperty<Document> document_ID_srcProperty() {
        return document_ID_src;
    }

    /**
     * Property of the variable document_ID_dest.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<Document>} - return the property of the variable document_ID_dest.
     */
    public ObjectProperty<Document> document_ID_destProperty() {
        return document_ID_dest;
    }

    /**
     * Property of the variable geneticStatus.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable geneticStatus.
     */
    public StringProperty geneticStatusProperty() {
        return geneticStatus;
    }

    /**
     * ToString methods, created when this class is used on listing
     *
     * @return - {@link String} - the builded list
     */
    @Override
    public String toString() {
        return String.format("%s", document_ID_src.get());
    }
}
