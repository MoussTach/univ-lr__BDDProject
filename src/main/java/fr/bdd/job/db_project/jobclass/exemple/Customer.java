package fr.bdd.job.db_project.jobclass.exemple;

import com.walter.savprogram.application.job.db_sav.jobclass.Risk;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class Customer {

    private final StringProperty client_CodeClient = new SimpleStringProperty("n/a");
    private final StringProperty Client_RaisonSociale = new SimpleStringProperty("n/a");
    private final StringProperty client_denomination = new SimpleStringProperty("n/a");
    private final StringProperty client_representant = new SimpleStringProperty("n/a");
    private final StringProperty client_codePostal = new SimpleStringProperty("n/a");
    private final StringProperty client_ville = new SimpleStringProperty("n/a");
    private final StringProperty client_pays = new SimpleStringProperty("n/a");
    private final ObjectProperty<CBilling> client_codePayeur = new SimpleObjectProperty<>(null);
    private final StringProperty client_CA_Num = new SimpleStringProperty("n/a");
    private final StringProperty client_telephone = new SimpleStringProperty("n/a");
    private final StringProperty client_telecopie = new SimpleStringProperty("n/a");
    private final StringProperty client_email = new SimpleStringProperty("n/a");
    private final StringProperty client_site = new SimpleStringProperty("n/a");
    private final ObjectProperty<Commercial> client_Commercial = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Risk> client_risque = new SimpleObjectProperty<>(null);
    private final StringProperty client_codeAffaire = new SimpleStringProperty("n/a");

    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public Customer() {
        this.client_codePayeur.set(new CBilling());
    }

    /**
     * Clone constructor
     *
     * @param clone - {@link Customer}
     */
    public Customer(Customer clone) {

        if (clone != null) {

            this.client_CodeClient.set(clone.client_CodeClient.get());

            this.Client_RaisonSociale.set(clone.Client_RaisonSociale.get());
            this.client_denomination.set(clone.client_denomination.get());
            this.client_representant.set(clone.client_representant.get());
            this.client_codePostal.set(clone.client_codePostal.get());
            this.client_ville.set(clone.client_ville.get());
            this.client_pays.set(clone.client_pays.get());
            this.client_codePayeur.set(new CBilling(clone.client_codePayeur.get()));
            this.client_CA_Num.set(clone.client_CA_Num.get());
            this.client_telephone.set(clone.client_telephone.get());
            this.client_telecopie.set(clone.client_telecopie.get());
            this.client_email.set(clone.client_email.get());
            this.client_site.set(clone.client_site.get());
            this.client_Commercial.set(clone.client_Commercial.get());
            this.client_risque.set(clone.client_risque.get());
            this.client_codeAffaire.set(clone.client_codeAffaire.get());
        }
    }

    /**
     * Getter for the variable client_CodeClient.
     *
     * @return {@link Long} - return the variable client_CodeClient.
     */
    public String getClient_CodeClient() {
        return client_CodeClient.get();
    }

    /**
     * Getter for the variable Client_RaisonSociale.
     *
     * @return {@link String} - return the variable Client_RaisonSociale.
     */
    public String getClient_RaisonSociale() {
        return Client_RaisonSociale.get();
    }

    /**
     * Getter for the variable client_denomination.
     *
     * @return {@link String} - return the variable client_denomination.
     */
    public String getClient_denomination() {
        return client_denomination.get();
    }

    /**
     * Getter for the variable client_representant.
     *
     * @return {@link String} - return the variable client_representant.
     */
    public String getClient_representant() {
        return client_representant.get();
    }

    /**
     * Getter for the variable client_codePostal.
     *
     * @return int - return the variable client_codePostal.
     */
    public String getClient_codePostal() {
        return client_codePostal.get();
    }

    /**
     * Getter for the variable client_ville.
     *
     * @return {@link String} - return the variable client_ville.
     */
    public String getClient_ville() {
        return client_ville.get();
    }

    /**
     * Getter for the variable client_pays.
     *
     * @return {@link String} - return the variable client_pays.
     */
    public String getClient_pays() {
        return client_pays.get();
    }

    /**
     * Getter for the variable client_codePayeur.
     *
     * @return {@link CBilling} - return the variable client_codePayeur.
     */
    public CBilling getClient_codePayeur() {
        return client_codePayeur.get();
    }

    /**
     * Getter for the variable client_codePayeur.
     *
     * @return {@link CBilling} - return the variable client_codePayeur.
     */
    public String getClient_CA_Num() {
        return client_CA_Num.get();
    }

    /**
     * Getter for the variable client_telephone.
     *
     * @return {@link String} - return the variable client_telephone.
     */
    public String getClient_telephone() {
        return client_telephone.get();
    }

    /**
     * Getter for the variable client_telecopie.
     *
     * @return {@link String} - return the variable client_telecopie.
     */
    public String getClient_telecopie() {
        return client_telecopie.get();
    }

    /**
     * Getter for the variable client_email.
     *
     * @return {@link String} - return the variable client_email.
     */
    public String getClient_email() {
        return client_email.get();
    }

    /**
     * Getter for the variable client_site.
     *
     * @return {@link String} - return the variable client_site.
     */
    public String getClient_site() {
        return client_site.get();
    }

    /**
     * Getter for the variable client_Commercial.
     *
     * @return {@link Commercial} - return the variable client_Commercial.
     */
    public Commercial getClient_Commercial() {
        return client_Commercial.get();
    }

    /**
     * Getter for the variable client_risque.
     *
     * @return {@link Risk} - return the variable client_risque.
     */
    public Risk getClient_Risque() {
        return client_risque.get();
    }

    /**
     * Getter for the variable client_codeAffaire.
     *
     * @return {@link String} - return the variable client_codeAffaire.
     */
    public String getClient_codeAffaire() {
        return client_codeAffaire.get();
    }



    /**
     * Setter for the variable client_CodeClient.
     *
     * @param client_CodeClient - {@link String} - index of the job class.
     * @return - {@link Customer} - builder pattern return
     */
    public Customer setClient_CodeClient(String client_CodeClient) {
        this.client_CodeClient.set(client_CodeClient);
        return this;
    }

    /**
     * Setter for the variable Client_RaisonSociale.
     *
     * @param client_RaisonSociale - {@link String} - Client_RaisonSociale of this class.
     * @return - {@link Customer} - builder pattern return
     */
    public Customer setClient_RaisonSociale(String client_RaisonSociale) {
        this.Client_RaisonSociale.set(client_RaisonSociale);
        return this;
    }

    /**
     * Setter for the variable client_denomination.
     *
     * @param client_denomination - {@link String} - client_denomination of this class.
     * @return - {@link Customer} - builder pattern return
     */
    public Customer setClient_denomination(String client_denomination) {
        this.client_denomination.set(client_denomination);
        return this;
    }

    /**
     * Setter for the variable client_representant.
     *
     * @param client_representant - {@link String} - client_representant of this class.
     * @return - {@link Customer} - builder pattern return
     */
    public Customer setClient_representant(String client_representant) {
        this.client_representant.set(client_representant);
        return this;
    }

    /**
     * Setter for the variable client_codePostal.
     *
     * @param client_codePostal - {@link String} - client_codePostal of this class.
     * @return - {@link Customer} - builder pattern return
     */
    public Customer setClient_codePostal(String client_codePostal) {
        this.client_codePostal.set(client_codePostal);
        return this;
    }

    /**
     * Setter for the variable client_ville.
     *
     * @param client_ville - {@link String} - client_ville of this class.
     * @return - {@link Customer} - builder pattern return
     */
    public Customer setClient_ville(String client_ville) {
        this.client_ville.set(client_ville);
        return this;
    }

    /**
     * Setter for the variable client_pays.
     *
     * @param client_pays - {@link String} - client_pays of this class.
     * @return - {@link Customer} - builder pattern return
     */
    public Customer setClient_pays(String client_pays) {
        this.client_pays.set(client_pays);
        return this;
    }

    /**
     * Setter for the variable client_codePayeur.
     *
     * @param client_codePayeur - {@link CBilling} - client_codePayeur of this class.
     * @return - {@link Customer} - builder pattern return
     */
    public Customer setClient_codePayeur(CBilling client_codePayeur) {
        this.client_codePayeur.set(client_codePayeur);
        return this;
    }

    /**
     * Setter for the variable client_CA_Num.
     *
     * @param client_CA_Num - {@link String} - client_CA_Num of this class.
     * @return - {@link Customer} - builder pattern return
     */
    public Customer setClient_CA_Num(String client_CA_Num) {
        this.client_CA_Num.set(client_CA_Num);
        return this;
    }

    /**
     * Setter for the variable client_telephone.
     *
     * @param client_telephone - {@link String} - client_telephone of this class.
     * @return - {@link Customer} - builder pattern return
     */
    public Customer setClient_telephone(String client_telephone) {
        this.client_telephone.set(client_telephone);
        return this;
    }

    /**
     * Setter for the variable client_telecopie.
     *
     * @param client_telecopie - {@link String} - client_telecopie of this class.
     * @return - {@link Customer} - builder pattern return
     */
    public Customer setClient_telecopie(String client_telecopie) {
        this.client_telecopie.set(client_telecopie);
        return this;
    }

    /**
     * Setter for the variable client_email.
     *
     * @param client_email - {@link String} - client_email of this class.
     * @return - {@link Customer} - builder pattern return
     */
    public Customer setClient_email(String client_email) {
        this.client_email.set(client_email);
        return this;
    }

    /**
     * Setter for the variable client_site.
     *
     * @param client_site - {@link String} - client_site of this class.
     * @return - {@link Customer} - builder pattern return
     */
    public Customer setClient_site(String client_site) {
        this.client_site.set(client_site);
        return this;
    }

    /**
     * Setter for the variable client_Commercial.
     *
     * @param client_Commercial - {@link Commercial} - client_Commercial of this class.
     * @return - {@link Customer} - builder pattern return
     */
    public Customer setClient_Commercial(Commercial client_Commercial) {
        this.client_Commercial.set(client_Commercial);
        return this;
    }

    /**
     * Setter for the variable client_risque.
     *
     * @param client_risque - {@link Risk} - client_risque of this class.
     * @return - {@link Customer} - builder pattern return
     */
    public Customer setClient_Risque(Risk client_risque) {
        this.client_risque.set(client_risque);
        return this;
    }

    /**
     * Setter for the variable client_codeAffaire.
     *
     * @param client_codeAffaire - {@link Risk} - client_codeAffaire of this class.
     * @return - {@link Customer} - builder pattern return
     */
    public Customer setClient_codeAffaire(String client_codeAffaire) {
        this.client_codeAffaire.set(client_codeAffaire);
        return this;
    }


    /**
     * Property of the variable client_CodeClient.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable client_CodeClient.
     */
    public StringProperty client_CodeClientProperty() {
        return client_CodeClient;
    }

    /**
     * Property of the variable Client_RaisonSociale.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable Client_RaisonSociale.
     */
    public StringProperty client_RaisonSocialeProperty() {
        return Client_RaisonSociale;
    }

    /**
     * Property of the variable client_denomination.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable client_denomination.
     */
    public StringProperty client_denominationProperty() {
        return client_denomination;
    }

    /**
     * Property of the variable client_representant.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable client_representant.
     */
    public StringProperty client_representantProperty() {
        return client_representant;
    }

    /**
     * Property of the variable client_codePostal.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable client_codePostal.
     */
    public StringProperty client_codePostalProperty() {
        return client_codePostal;
    }

    /**
     * Property of the variable client_ville.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable client_ville.
     */
    public StringProperty client_villeProperty() {
        return client_ville;
    }

    /**
     * Property of the variable client_pays.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable client_pays.
     */
    public StringProperty client_paysProperty() {
        return client_pays;
    }

    /**
     * Property of the variable client_codePayeur.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty} - return the property of the variable client_codePayeur.
     */
    public ObjectProperty<CBilling> client_codePayeurProperty() {
        return client_codePayeur;
    }

    /**
     * Property of the variable client_CA_Num.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable client_CA_Num.
     */
    public StringProperty client_CA_NumProperty() {
        return client_CA_Num;
    }

    /**
     * Property of the variable client_telephone.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable client_telephone.
     */
    public StringProperty client_telephoneProperty() {
        return client_telephone;
    }

    /**
     * Property of the variable client_telecopie.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable client_telecopie.
     */
    public StringProperty client_telecopieProperty() {
        return client_telecopie;
    }

    /**
     * Property of the variable client_email.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable client_email.
     */
    public StringProperty client_emailProperty() {
        return client_email;
    }

    /**
     * Property of the variable client_site.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable client_site.
     */
    public StringProperty client_siteProperty() {
        return client_site;
    }

    /**
     * Property of the variable client_Commercial.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty} - return the property of the variable client_Commercial.
     */
    public ObjectProperty<Commercial> client_CommercialProperty() {
        return client_Commercial;
    }

    /**
     * Property of the variable client_risque.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty} - return the property of the variable client_risque.
     */
    public ObjectProperty<Risk> client_risqueProperty() {
        return client_risque;
    }

    /**
     * Property of the variable client_codeAffaire.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable client_codeAffaire.
     */
    public StringProperty client_codeAffaireProperty() {
        return client_codeAffaire;
    }


    /**
     * ToString methods, created when this class is used on listing
     *
     * @return - {@link String} - the builded list
     */
    @Override
    public String toString() {
        return String.format("%s", client_CodeClient.get());
    }
}
