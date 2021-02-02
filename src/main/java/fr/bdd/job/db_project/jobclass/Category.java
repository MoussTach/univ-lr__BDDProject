package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class Category {

    private final StringProperty category_ID = new SimpleStringProperty("n/a");
    private final StringProperty dataCategory = new SimpleStringProperty("n/a");
    private final StringProperty prefix = new SimpleStringProperty("n/a");
    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public Category() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link Category}
     */
    public Category(Category clone) {

        if (clone != null) {

            this.category_ID.set(clone.category_ID.get());

            this.dataCategory.set(clone.dataCategory.get());
            this.prefix.set(clone.prefix.get());
        }
    }

    /**
     * Getter for the variable category_ID.
     *
     * @return {@link String} - return the variable category_ID.
     */
    public String getcategory_ID() {
        return category_ID.get();
    }

    /**
     * Getter for the variable dataCategory.
     *
     * @return {@link String} - return the variable dataCategory.
     */
    public String getdataCategory() {
        return dataCategory.get();
    }

    /**
     * Getter for the variable prefix.
     *
     * @return String - return the variable prefix.
     */
    public String getprefix() {
        return prefix.get();
    }

    /**
     * Setter for the variable category_ID.
     *
     * @param category_ID - {@link String} - index of the Category class.
     * @return - {@link Category} - builder pattern return
     */
    public Category setcategory_ID(String category_ID) {
        this.category_ID.set(category_ID);
        return this;
    }

    /**
     * Setter for the variable dataCategory.
     *
     * @param dataCategory - {@link String} - dataCategory of this class.
     * @return - {@link Category} - builder pattern return
     */
    public Category setdataCategory(String dataCategory) {
        this.dataCategory.set(dataCategory);
        return this;
    }

    /**
     * Setter for the variable prefix.
     *
     * @param prefix - {@link String} - prefix of this class.
     * @return - {@link Category} - builder pattern return
     */
    public Category setprefix(String prefix) {
        this.prefix.set(prefix);
        return this;
    }

    /**
     * Property of the variable category_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable category_ID.
     */
    public StringProperty category_IDProperty() {
        return category_ID;
    }

    /**
     * Property of the variable dataCategory.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable dataCategory.
     */
    public StringProperty dataCategoryProperty() {
        return dataCategory;
    }

    /**
     * Property of the variable prefix.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable prefix.
     */
    public StringProperty prefixProperty() {
        return prefix;
    }

    /**
     * ToString methods, created when this class is used on listing
     *
     * @return - {@link String} - the builded list
     */
    @Override
    public String toString() {
        return String.format("%s", category_ID.get());
    }
}
