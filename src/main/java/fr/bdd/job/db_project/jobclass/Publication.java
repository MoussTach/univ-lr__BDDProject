package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class Publication {

    private final IntegerProperty publication_ID = new SimpleIntegerProperty(0);
    private final StringProperty numPublication = new SimpleStringProperty("n/a");
    private final StringProperty publication = new SimpleStringProperty("n/a");
    private final StringProperty title = new SimpleStringProperty("n/a");
    private final StringProperty place = new SimpleStringProperty("n/a");
    private final StringProperty type = new SimpleStringProperty("n/a");
    private final StringProperty periodicity = new SimpleStringProperty("n/a");
    private final ObjectProperty<Person> director = new SimpleObjectProperty<>(null);
    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public Publication() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link Publication}
     */
    public Publication(Publication clone) {

        if (clone != null) {

            this.publication_ID.set(clone.publication_ID.get());

            this.numPublication.set(clone.numPublication.get());
            this.publication.set(clone.publication.get());
            this.title.set(clone.title.get());
            this.place.set(clone.place.get());
            this.type.set(clone.type.get());
            this.periodicity.set(clone.periodicity.get());
            this.director.set(clone.director.get());
        }
    }

    /**
     * Getter for the variable publication_ID.
     *
     * @return {@link Integer} - return the variable publication_ID.
     */
    public Integer getpublication_ID() {
        return publication_ID.get();
    }

    /**
     * Getter for the variable numPublication.
     *
     * @return {@link String} - return the variable numPublication.
     */
    public String getnumPublication() {
        return numPublication.get();
    }

    /**
     * Getter for the variable publication.
     *
     * @return String - return the variable publication.
     */
    public String getpublication() {
        return publication.get();
    }

    /**
     * Getter for the variable title.
     *
     * @return {@link String} - return the variable title.
     */
    public String gettitle() {
        return title.get();
    }

    /**
     * Getter for the variable place.
     *
     * @return {@link String} - return the variable place.
     */
    public String getplace() {
        return place.get();
    }

    /**
     * Getter for the variable type.
     *
     * @return {@link String} - return the variable type.
     */
    public String gettype() {
        return type.get();
    }

    /**
     * Getter for the variable type.
     *
     * @return {@link String} - return the variable type.
     */
    public String getperiodicity() {
        return periodicity.get();
    }

    /**
     * Getter for the variable director.
     *
     * @return {@link Person} - return the variable director.
     */
    public Person getdirector() {
        return director.get();
    }

    /**
     * Setter for the variable publication_ID.
     *
     * @param publication_ID - {@link Integer} - index of the Publication class.
     * @return - {@link Publication} - builder pattern return
     */
    public Publication setpublication_ID(Integer publication_ID) {
        this.publication_ID.set(publication_ID);
        return this;
    }

    /**
     * Setter for the variable numPublication.
     *
     * @param numPublication - {@link String} - numPublication of this class.
     * @return - {@link Publication} - builder pattern return
     */
    public Publication setnumPublication(String numPublication) {
        this.numPublication.set(numPublication);
        return this;
    }

    /**
     * Setter for the variable publication.
     *
     * @param publication - {@link String} - publication of this class.
     * @return - {@link Publication} - builder pattern return
     */
    public Publication setpublication(String publication) {
        this.publication.set(publication);
        return this;
    }

    /**
     * Setter for the variable title.
     *
     * @param title - {@link String} - title of this class.
     * @return - {@link Publication} - builder pattern return
     */
    public Publication settitle(String title) {
        this.title.set(title);
        return this;
    }

    /**
     * Setter for the variable place.
     *
     * @param place - {@link String} - place of this class.
     * @return - {@link Publication} - builder pattern return
     */
    public Publication setplace(String place) {
        this.place.set(place);
        return this;
    }

    /**
     * Setter for the variable type.
     *
     * @param type - {@link String} - type of this class.
     * @return - {@link Publication} - builder pattern return
     */
    public Publication settype(String type) {
        this.type.set(type);
        return this;
    }

    /**
     * Setter for the variable periodicity.
     *
     * @param periodicity - {@link String} - periodicity of this class.
     * @return - {@link Publication} - builder pattern return
     */
    public Publication setperiodicity(String periodicity) {
        this.periodicity.set(periodicity);
        return this;
    }

    /**
     * Setter for the variable director.
     *
     * @param director - {@link Person} - director of this class.
     * @return - {@link Publication} - builder pattern return
     */
    public Publication setdirector(Person director) {
        this.director.set(director);
        return this;
    }

    /**
     * Property of the variable publication_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link IntegerProperty} - return the property of the variable publication_ID.
     */
    public IntegerProperty publication_IDProperty() {
        return publication_ID;
    }

    /**
     * Property of the variable numPublication.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable numPublication.
     */
    public StringProperty numPublicationProperty() {
        return numPublication;
    }

    /**
     * Property of the variable publication.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable publication.
     */
    public StringProperty publicationProperty() {
        return publication;
    }

    /**
     * Property of the variable title.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable title.
     */
    public StringProperty titleProperty() {
        return title;
    }

    /**
     * Property of the variable place.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable place.
     */
    public StringProperty placeProperty() {
        return place;
    }

    /**
     * Property of the variable type.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable type.
     */
    public StringProperty typeProperty() {
        return type;
    }

    /**
     * Property of the variable periodicity.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable periodicity.
     */
    public StringProperty periodicityProperty() {
        return periodicity;
    }

    /**
     * Property of the variable director.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<Person>} - return the property of the variable director.
     */
    public ObjectProperty<Person> directorProperty() {
        return director;
    }

    /**
     * ToString methods, created when this class is used on listing
     *
     * @return - {@link String} - the builded list
     */
    @Override
    public String toString() {
        return String.format("%s", publication_ID.get());
    }
}
