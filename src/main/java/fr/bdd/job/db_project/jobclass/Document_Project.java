package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class Document_Project {

    private final ObjectProperty<Document> document_ID = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Project> project_ID = new SimpleObjectProperty<>(null);
    private final ObjectProperty<AAP> AAP_ID = new SimpleObjectProperty<>(null);
    private final ObjectProperty<RespGroup> respGroup_ID = new SimpleObjectProperty<>(null);
   
    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public Document_Project() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link Document_Project}
     */
    public Document_Project(Document_Project clone) {

        if (clone != null) {

            this.document_ID.set(clone.document_ID.get());

            this.project_ID.set(clone.project_ID.get());
            this.AAP_ID.set(clone.AAP_ID.get());
            this.respGroup_ID.set(clone.respGroup_ID.get());
        }
    }

    /**
     * Getter for the variable document_ID.
     *
     * @return {@link Document} - return the variable document_ID.
     */
    public Document getdocument_ID() {
        return document_ID.get();
    }

    /**
     * Getter for the variable project_ID.
     *
     * @return {@link Project} - return the variable project_ID.
     */
    public Project getproject_ID() {
        return project_ID.get();
    }

    /**
     * Getter for the variable AAP_ID.
     *
     * @return {@link AAP} - return the variable AAP_ID.
     */
    public AAP getAAP_ID() {
        return AAP_ID.get();
    }

    /**
     * Getter for the variable respGroup_ID.
     *
     * @return {@link RespGroup} - return the variable respGroup_ID.
     */
    public RespGroup getrespGroup_ID() {
        return respGroup_ID.get();
    }

    /**
     * Setter for the variable document_ID.
     *
     * @param document_ID - {@link Document} - index of the Document_Project class.
     * @return - {@link Document_Project} - builder pattern return
     */
    public Document_Project setdocument_ID(Document document_ID) {
        this.document_ID.set(document_ID);
        return this;
    }

    /**
     * Setter for the variable project_ID.
     *
     * @param project_ID - {@link Project} - project_ID of this class.
     * @return - {@link Document_Project} - builder pattern return
     */
    public Document_Project setproject_ID(Project project_ID) {
        this.project_ID.set(project_ID);
        return this;
    }

    /**
     * Setter for the variable AAP_ID.
     *
     * @param AAP_ID - {@link AAP} - AAP_ID of this class.
     * @return - {@link Document_Project} - builder pattern return
     */
    public Document_Project setAAP_ID(AAP AAP_ID) {
        this.AAP_ID.set(AAP_ID);
        return this;
    }

    /**
     * Setter for the variable respGroup_ID.
     *
     * @param respGroup_ID - {@link RespGroup} - respGroup_ID of this class.
     * @return - {@link Document_Project} - builder pattern return
     */
    public Document_Project setrespGroup_ID(RespGroup respGroup_ID) {
        this.respGroup_ID.set(respGroup_ID);
        return this;
    }

    /**
     * Property of the variable document_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<Document>} - return the property of the variable document_ID.
     */
    public ObjectProperty<Document> document_IDProperty() {
        return document_ID;
    }

    /**
     * Property of the variable project_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<Project>} - return the property of the variable project_ID.
     */
    public ObjectProperty<Project> project_IDProperty() {
        return project_ID;
    }

    /**
     * Property of the variable AAP_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<AAP>} - return the property of the variable AAP_ID.
     */
    public ObjectProperty<AAP> AAP_IDProperty() {
        return AAP_ID;
    }

    /**
     * Property of the variable respGroup_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<RespGroup>} - return the property of the variable respGroup_ID.
     */
    public ObjectProperty<RespGroup> respGroup_IDProperty() {
        return respGroup_ID;
    }

    /**
     * ToString methods, created when this class is used on listing
     *
     * @return - {@link String} - the builded list
     */
    @Override
    public String toString() {
        return String.format("%s", document_ID.get());
    }
}
