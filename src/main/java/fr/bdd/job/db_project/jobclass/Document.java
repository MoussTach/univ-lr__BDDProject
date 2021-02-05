package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Date;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class Document {

    private final StringProperty document_ID = new SimpleStringProperty("n/a");
    private final ObjectProperty<Category> category_ID = new SimpleObjectProperty<>(null);
    private final ObjectProperty<LocalDate> dateCreation_start = new SimpleObjectProperty<>(null);
    private final ObjectProperty<LocalDate> dateCreation_end = new SimpleObjectProperty<>(null);
    private final ObjectProperty<NatureOfDoc> natureOfDoc_ID = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Condition> condition_ID = new SimpleObjectProperty<>(null);
    private final StringProperty format = new SimpleStringProperty("n/a");
    private final StringProperty location = new SimpleStringProperty("n/a");
    private final StringProperty geographicLocation = new SimpleStringProperty("n/a");
    private final StringProperty rights = new SimpleStringProperty("n/a");
    private final StringProperty copyright = new SimpleStringProperty("n/a");
    private final StringProperty shippingLocation = new SimpleStringProperty("n/a");
    private final ObjectProperty<Person> Recipient = new SimpleObjectProperty<>(null);
    private final StringProperty representation = new SimpleStringProperty("n/a");
    private final StringProperty otherRelatedResources = new SimpleStringProperty("n/a");

    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public Document() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link Document}
     */
    public Document(Document clone) {

        if (clone != null) {
            this.document_ID.set(clone.document_ID.get());

            this.category_ID.set(clone.category_ID.get());
            this.dateCreation_start.set(clone.dateCreation_start.get());
            this.dateCreation_end.set(clone.dateCreation_end.get());
            this.natureOfDoc_ID.set(clone.natureOfDoc_ID.get());
            this.condition_ID.set(clone.condition_ID.get());
            this.format.set(clone.format.get());
            this.location.set(clone.location.get());
            this.geographicLocation.set(clone.geographicLocation.get());
            this.rights.set(clone.rights.get());
            this.copyright.set(clone.copyright.get());
            this.shippingLocation.set(clone.shippingLocation.get());
            this.Recipient.set(clone.Recipient.get());
            this.representation.set(clone.representation.get());
            this.otherRelatedResources.set(clone.otherRelatedResources.get());
        }
    }

    /**
     * Getter for the variable document_ID.
     *
     * @return {@link String} - return the variable document_ID.
     */
    public String getdocument_ID() {
        return document_ID.get();
    }

    /**
     * Getter for the variable category_ID.
     *
     * @return {@link Category} - return the variable category_ID.
     */
    public Category getcategory_ID() {
        return category_ID.get();
    }

    /**
     * Getter for the variable dateCreation_start.
     *
     * @return {@link LocalDate} - return the variable dateCreation_start.
     */
    public LocalDate getdateCreation_start() {
        return dateCreation_start.get();
    }

    /**
     * Getter for the variable dateCreation_end.
     *
     * @return {@link LocalDate} - return the variable dateCreation_end.
     */
    public LocalDate getdateCreation_end() {
        return dateCreation_end.get();
    }

    /**
     * Getter for the variable natureOfDoc_ID.
     *
     * @return {@link NatureOfDoc} - return the variable natureOfDoc_ID.
     */
    public NatureOfDoc getnatureOfDoc_ID() {
        return natureOfDoc_ID.get();
    }

    /**
     * Getter for the variable condition_ID.
     *
     * @return {@link Condition} - return the variable condition_ID.
     */
    public Condition getcondition_ID() {
        return condition_ID.get();
    }

    /**
     * Getter for the variable format.
     *
     * @return {@link String} - return the variable format.
     */
    public String getformat() {
        return format.get();
    }

    /**
     * Getter for the variable location.
     *
     * @return {@link String} - return the variable location.
     */
    public String getlocation() {
        return location.get();
    }

    /**
     * Getter for the variable location.
     *
     * @return {@link String} - return the variable location.
     */
    public String getgeographicLocation() {
        return geographicLocation.get();
    }

    /**
     * Getter for the variable rights.
     *
     * @return {@link String} - return the variable rights.
     */
    public String getrights() {
        return rights.get();
    }

    /**
     * Getter for the variable copyright.
     *
     * @return {@link String} - return the variable copyright.
     */
    public String getcopyright() {
        return copyright.get();
    }

    /**
     * Getter for the variable shippingLocation.
     *
     * @return {@link String} - return the variable shippingLocation.
     */
    public String getshippingLocation() {
        return shippingLocation.get();
    }

    /**
     * Getter for the variable Recipient.
     *
     * @return {@link Person} - return the variable Recipient.
     */
    public Person getRecipient() {
        return Recipient.get();
    }

    /**
     * Getter for the variable representation.
     *
     * @return {@link String} - return the variable representation.
     */
    public String getrepresentation() {
        return representation.get();
    }

    /**
     * Getter for the variable otherRelatedResources.
     *
     * @return {@link String} - return the variable otherRelatedResources.
     */
    public String getotherRelatedResources() {
        return otherRelatedResources.get();
    }



    /**
     * Setter for the variable document_ID.
     *
     * @param document_ID - {@link String} - index of the Document class.
     * @return - {@link Document} - builder pattern return
     */
    public Document setdocument_ID(String document_ID) {
        this.document_ID.set(document_ID);
        return this;
    }

    /**
     * Setter for the variable category_ID.
     *
     * @param category_ID - {@link Category} - category_ID of this class.
     * @return - {@link Document} - builder pattern return
     */
    public Document setcategory_ID(Category category_ID) {
        this.category_ID.set(category_ID);
        return this;
    }

    /**
     * Setter for the variable dateCreation_start.
     *
     * @param dateCreation_start - {@link LocalDate} - dateCreation_start of this class.
     * @return - {@link Document} - builder pattern return
     */
    public Document setdateCreation_start(LocalDate dateCreation_start) {
        this.dateCreation_start.set(dateCreation_start);
        return this;
    }

    /**
     * Setter for the variable dateCreation_end.
     *
     * @param dateCreation_end - {@link LocalDate} - dateCreation_end of this class.
     * @return - {@link Document} - builder pattern return
     */
    public Document setdateCreation_end(LocalDate dateCreation_end) {
        this.dateCreation_end.set(dateCreation_end);
        return this;
    }

    /**
     * Setter for the variable natureOfDoc_ID.
     *
     * @param natureOfDoc_ID - {@link NatureOfDoc} - natureOfDoc_ID of this class.
     * @return - {@link Document} - builder pattern return
     */
    public Document setnatureOfDoc_ID(NatureOfDoc natureOfDoc_ID) {
        this.natureOfDoc_ID.set(natureOfDoc_ID);
        return this;
    }

    /**
     * Setter for the variable condition_ID.
     *
     * @param condition_ID - {@link Condition} - condition_ID of this class.
     * @return - {@link Document} - builder pattern return
     */
    public Document setcondition_ID(Condition condition_ID) {
        this.condition_ID.set(condition_ID);
        return this;
    }

    /**
     * Setter for the variable format.
     *
     * @param format - {@link String} - format of this class.
     * @return - {@link Document} - builder pattern return
     */
    public Document setformat(String format) {
        this.format.set(format);
        return this;
    }

    /**
     * Setter for the variable location.
     *
     * @param location - {@link String} - location of this class.
     * @return - {@link Document} - builder pattern return
     */
    public Document setlocation(String location) {
        this.location.set(location);
        return this;
    }

    /**
     * Setter for the variable geographicLocation.
     *
     * @param geographicLocation - {@link String} - geographicLocation of this class.
     * @return - {@link Document} - builder pattern return
     */
    public Document setgeographicLocation(String geographicLocation) {
        this.geographicLocation.set(geographicLocation);
        return this;
    }

    /**
     * Setter for the variable rights.
     *
     * @param rights - {@link String} - rights of this class.
     * @return - {@link Document} - builder pattern return
     */
    public Document setrights(String rights) {
        this.rights.set(rights);
        return this;
    }

    /**
     * Setter for the variable copyright.
     *
     * @param copyright - {@link String} - copyright of this class.
     * @return - {@link Document} - builder pattern return
     */
    public Document setcopyright(String copyright) {
        this.copyright.set(copyright);
        return this;
    }

    /**
     * Setter for the variable shippingLocation.
     *
     * @param shippingLocation - {@link String} - shippingLocation of this class.
     * @return - {@link Document} - builder pattern return
     */
    public Document setshippingLocation(String shippingLocation) {
        this.shippingLocation.set(shippingLocation);
        return this;
    }

    /**
     * Setter for the variable Recipient.
     *
     * @param Recipient - {@link Person} - Recipient of this class.
     * @return - {@link Document} - builder pattern return
     */
    public Document setRecipient(Person Recipient) {
        this.Recipient.set(Recipient);
        return this;
    }

    /**
     * Setter for the variable representation.
     *
     * @param representation - {@link String} - representation of this class.
     * @return - {@link Document} - builder pattern return
     */
    public Document setrepresentation(String representation) {
        this.representation.set(representation);
        return this;
    }

    /**
     * Setter for the variable otherRelatedResources.
     *
     * @param otherRelatedResources - {@link String} - otherRelatedResources of this class.
     * @return - {@link Document} - builder pattern return
     */
    public Document setotherRelatedResources(String otherRelatedResources) {
        this.otherRelatedResources.set(otherRelatedResources);
        return this;
    }


    /**
     * Property of the variable document_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable document_ID.
     */
    public StringProperty document_IDProperty() {
        return document_ID;
    }

    /**
     * Property of the variable category_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<Category>} - return the property of the variable category_ID.
     */
    public ObjectProperty<Category> category_IDProperty() {
        return category_ID;
    }

    /**
     * Property of the variable dateCreation_start.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<LocalDate>} - return the property of the variable dateCreation_start.
     */
    public ObjectProperty<LocalDate> dateCreation_startProperty() {
        return dateCreation_start;
    }

    /**
     * Property of the variable dateCreation_end.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<LocalDate>} - return the property of the variable dateCreation_end.
     */
    public ObjectProperty<LocalDate> dateCreation_endProperty() {
        return dateCreation_end;
    }

    /**
     * Property of the variable natureOfDoc_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<NatureOfDoc>} - return the property of the variable natureOfDoc_ID.
     */
    public ObjectProperty<NatureOfDoc> natureOfDoc_IDProperty() {
        return natureOfDoc_ID;
    }

    /**
     * Property of the variable condition_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<Condition>} - return the property of the variable condition_ID.
     */
    public ObjectProperty<Condition> condition_IDProperty() {
        return condition_ID;
    }

    /**
     * Property of the variable format.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable format.
     */
    public StringProperty formatProperty() {
        return format;
    }

    /**
     * Property of the variable location.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable location.
     */
    public StringProperty locationProperty() {
        return location;
    }

    /**
     * Property of the variable geographicLocation.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable geographicLocation.
     */
    public StringProperty geographicLocationProperty() {
        return geographicLocation;
    }

    /**
     * Property of the variable rights.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable rights.
     */
    public StringProperty rightsProperty() {
        return rights;
    }

    /**
     * Property of the variable copyright.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable copyright.
     */
    public StringProperty copyrightProperty() {
        return copyright;
    }

    /**
     * Property of the variable shippingLocation.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable shippingLocation.
     */
    public StringProperty shippingLocationProperty() {
        return shippingLocation;
    }

    /**
     * Property of the variable Recipient.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<Person>} - return the property of the variable Recipient.
     */
    public ObjectProperty<Person> RecipientProperty() {
        return Recipient;
    }

    /**
     * Property of the variable representation.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable representation.
     */
    public StringProperty representationProperty() {
        return representation;
    }

    /**
     * Property of the variable otherRelatedResources.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable otherRelatedResources.
     */
    public StringProperty otherRelatedResourcesProperty() {
        return otherRelatedResources;
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
