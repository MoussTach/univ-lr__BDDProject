package fr.bdd.job.db_project.dao.exemple;

import fr.bdd.dataconnection.DataConnection;
import fr.bdd.job.dao.Dao;
import fr.bdd.job.db_project.jobclass.exemple.Customer;
import fr.bdd.log.generate.CustomLogger;
import org.apache.logging.log4j.Level;

import java.security.InvalidKeyException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pattern class.
 * Used for the job class {@link Customer}
 *
 * @author Gaetan Brenckle
 */
public class DAO_Customer implements Dao<Customer> {

    private static final CustomLogger LOGGER = CustomLogger.create(DAO_Customer.class.getName());
    private DataConnection connectionHandle_ = null;

    public enum info {
        DEFAULTDB("DB_SAV_SAGE"),

        TABLENAME("Client_Contact"),
        TABLESAGE("dbo_F_COMPTET"),

        CLIENT_CODECLIENT(String.format("%s", "CT_Num")),
        CLIENT_RAISONSOCIALE(String.format("%s", "CT_Intitule")),
        CLIENT_DENOMINATION(String.format("%s", "CT_Qualite")),
        CLIENT_REPRESENTANT(String.format("%s", "CT_Contact")),
        CLIENT_CODEPOSTAL(String.format("%s", "CT_CodePostal")),
        CLIENT_VILLE(String.format("%s", "CT_Ville")),
        CLIENT_PAYS(String.format("%s", "CT_Pays")),
        CLIENT_CODEPAYEUR(String.format("%s", "CT_NumPayeur")),
        CLIENT_CANUM(String.format("%s", "CA_Num")),
        CLIENT_TELEPHONE(String.format("%s", "CT_Telephone")),
        CLIENT_TELECOPIE(String.format("%s", "CT_Telecopie")),
        CLIENT_EMAIL(String.format("%s", "CT_EMail")),
        CLIENT_SITE(String.format("%s", "CT_Site")),
        CLIENT_COMMERCIAL(String.format("%s", "CO_No")),
        CLIENT_RISQUE(String.format("%s", "N_Risque")),
        CLIENT_CODEAFFAIRE(String.format("%s", "CA_Num")),

        CT_TYPE(String.format("%s", "CT_Type")),
        CT_SOMMEIL(String.format("%s", "CT_Sommeil"))
        ;

        private final String info;

        info(String info) {
            this.info = info;
        }

        @Override
        public String toString() {
            return info;
        }
    }

    public enum infoOld {
        OLDDB("DB_SAV_OLDVERSION"),

        TABLENAME("Client"),

        CODECLIENT(String.format("%s", "CodeClient")),
        RAISONSOCIALE(String.format("%s", "RaisonSociale")),
        CODEPAYEUR(String.format("%s", "CodePayeur")),
        CO_NO(String.format("%s", "CO_No")),
        ;

        private final String info;

        infoOld(String info) {
            this.info = info;
        }

        @Override
        public String toString() {
            return info;
        }
    }


    /**
     * Default constructor.
     * Need to call {@link DAO_Customer#setConnection(DataConnection)} before any other function.
     *
     * @author Gaetan Brenckle
     */
    public DAO_Customer() {
    }

    /**
     * Constructor with the Connection argument.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link DataConnection} - connection used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    public DAO_Customer(DataConnection conn) throws InvalidKeyException {
        this.connectionHandle_ = conn;

        if (!this.connectionHandle_.getConnectionHandleList().containsKey(info.DEFAULTDB.toString())) {
            throw new InvalidKeyException(String.format("The db key that this class use is not listed -> [%s]", info.DEFAULTDB));
        }
    }

    /**
     * Setter of the connection.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link DataConnection} - Connection used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    @Override
    public void setConnection(DataConnection conn) throws InvalidKeyException {
        this.connectionHandle_ = conn;

        if (!this.connectionHandle_.getConnectionHandleList().containsKey(info.DEFAULTDB.toString())) {
            throw new InvalidKeyException(String.format("The db key that this class use is not listed -> [%s]", info.DEFAULTDB));
        }
    }

    /**
     * SELECT with the index of the associate job class.
     *
     * @author Gaetan Brenckle
     *
     * @param id - {@link String} - index of the associate job class. Can handle null.
     * @return - {@link Customer} - the job class that can be found with the index
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    public final Customer select(String id) throws SQLException, InvalidKeyException {
        DAO_CBilling dao_cBilling = new DAO_CBilling(this.connectionHandle_);
        DAO_Commercial dao_commercial = new DAO_Commercial(this.connectionHandle_);
        DAO_Risk dao_risk = new DAO_Risk(this.connectionHandle_);
        Customer retCustomer = null;

        if (id == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Customer select when the given id is null");
            }
            return retCustomer;
        }

        final String select_sql = String.format("%s %s %s %s %s ",
                String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s ", info.CLIENT_CODECLIENT, info.CLIENT_RAISONSOCIALE, info.CLIENT_DENOMINATION, info.CLIENT_REPRESENTANT, info.CLIENT_CODEPOSTAL, info.CLIENT_VILLE, info.CLIENT_PAYS, info.CLIENT_CODEPAYEUR, info.CLIENT_CANUM, info.CLIENT_TELEPHONE, info.CLIENT_TELECOPIE, info.CLIENT_EMAIL, info.CLIENT_SITE, info.CLIENT_COMMERCIAL, info.CLIENT_RISQUE, info.CLIENT_CODEAFFAIRE),
                String.format("FROM %s ", info.TABLESAGE),
                String.format("WHERE %s = 0", info.CT_TYPE),
                String.format("AND %s = 0", info.CT_SOMMEIL),
                String.format("AND %s = ? ", info.CLIENT_CODECLIENT));

        final PreparedStatementAware prepSelect = new PreparedStatementAware(connectionHandle_.getConnectionHandleList().get(info.DEFAULTDB.toString()).prepareStatement(select_sql));
        prepSelect.setString(id);

        try(final ResultSet resultSelect = prepSelect.executeQuery()) {
            if (resultSelect.next()) {
                retCustomer =
                        new Customer()
                                .setClient_CodeClient(resultSelect.getString(info.CLIENT_CODECLIENT.toString()))
                                .setClient_RaisonSociale(resultSelect.getString(info.CLIENT_RAISONSOCIALE.toString()))
                                .setClient_denomination(resultSelect.getString(info.CLIENT_DENOMINATION.toString()))
                                .setClient_representant(resultSelect.getString(info.CLIENT_REPRESENTANT.toString()))
                                .setClient_codePostal(resultSelect.getString(info.CLIENT_CODEPOSTAL.toString()))
                                .setClient_ville(resultSelect.getString(info.CLIENT_VILLE.toString()))
                                .setClient_pays(resultSelect.getString(info.CLIENT_PAYS.toString()))
                                .setClient_codePayeur(dao_cBilling.select(resultSelect.getString(info.CLIENT_CODEPAYEUR.toString())))
                                .setClient_CA_Num(resultSelect.getString(info.CLIENT_CANUM.toString()))
                                .setClient_telephone(resultSelect.getString(info.CLIENT_TELEPHONE.toString()))
                                .setClient_telecopie(resultSelect.getString(info.CLIENT_TELECOPIE.toString()))
                                .setClient_email(resultSelect.getString(info.CLIENT_EMAIL.toString()))
                                .setClient_site(resultSelect.getString(info.CLIENT_SITE.toString()))
                                .setClient_Commercial(dao_commercial.select(resultSelect.getLong(info.CLIENT_COMMERCIAL.toString())))
                                .setClient_Risque(dao_risk.select(resultSelect.getLong(info.CLIENT_RISQUE.toString())))
                                .setClient_codeAffaire(resultSelect.getString(info.CLIENT_CODEAFFAIRE.toString()));
            }
        }

        return retCustomer;
    }

    /**
     * SELECT of all occurance of the job class.
     *
     * @author Gaetan Brenckle
     *
     * @return - {@link List} - a list that contain all occurance of {@link Customer}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    @Override
    public List<Customer> selectAll() throws SQLException, InvalidKeyException {
        DAO_CBilling dao_cBilling = new DAO_CBilling(this.connectionHandle_);
        DAO_Commercial dao_commercial = new DAO_Commercial(this.connectionHandle_);
        DAO_Risk dao_risk = new DAO_Risk(this.connectionHandle_);
        final List<Customer> retCustomers = new ArrayList<>();

        final String selectAll_sql = String.format("%s %s %s %s ",
                String.format("SELECT  %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s ", info.CLIENT_CODECLIENT, info.CLIENT_RAISONSOCIALE, info.CLIENT_DENOMINATION, info.CLIENT_REPRESENTANT, info.CLIENT_CODEPOSTAL, info.CLIENT_VILLE, info.CLIENT_PAYS, info.CLIENT_CODEPAYEUR, info.CLIENT_CANUM, info.CLIENT_TELEPHONE, info.CLIENT_TELECOPIE, info.CLIENT_EMAIL, info.CLIENT_SITE, info.CLIENT_COMMERCIAL, info.CLIENT_RISQUE, info.CLIENT_CODEAFFAIRE),
                String.format("FROM %s", info.TABLESAGE),
                String.format("WHERE %s = 0", info.CT_TYPE),
                String.format("AND %s = 0", info.CT_SOMMEIL));

        final PreparedStatement prepSelectAll = connectionHandle_.getConnectionHandleList().get(info.DEFAULTDB.toString()).prepareStatement(selectAll_sql);
        try (final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
            while (resultSelectAll.next()) {
                Customer customer = new Customer()
                        .setClient_CodeClient(resultSelectAll.getString(info.CLIENT_CODECLIENT.toString()))
                        .setClient_RaisonSociale(resultSelectAll.getString(info.CLIENT_RAISONSOCIALE.toString()))
                        .setClient_denomination(resultSelectAll.getString(info.CLIENT_DENOMINATION.toString()))
                        .setClient_representant(resultSelectAll.getString(info.CLIENT_REPRESENTANT.toString()))
                        .setClient_codePostal(resultSelectAll.getString(info.CLIENT_CODEPOSTAL.toString()))
                        .setClient_ville(resultSelectAll.getString(info.CLIENT_VILLE.toString()))
                        .setClient_pays(resultSelectAll.getString(info.CLIENT_PAYS.toString()))
                        .setClient_codePayeur(dao_cBilling.select(resultSelectAll.getString(info.CLIENT_CODEPAYEUR.toString())))
                        .setClient_CA_Num(resultSelectAll.getString(info.CLIENT_CANUM.toString()))
                        .setClient_telephone(resultSelectAll.getString(info.CLIENT_TELEPHONE.toString()))
                        .setClient_telecopie(resultSelectAll.getString(info.CLIENT_TELECOPIE.toString()))
                        .setClient_email(resultSelectAll.getString(info.CLIENT_EMAIL.toString()))
                        .setClient_site(resultSelectAll.getString(info.CLIENT_SITE.toString()))
                        .setClient_Commercial(dao_commercial.select(resultSelectAll.getLong(info.CLIENT_COMMERCIAL.toString())))
                        .setClient_Risque(dao_risk.select(resultSelectAll.getLong(info.CLIENT_RISQUE.toString())))
                        .setClient_codeAffaire(resultSelectAll.getString(info.CLIENT_CODEAFFAIRE.toString()));
                retCustomers.add(customer);
            }
        }
        return retCustomers;
    }

    /**
     * SELECT of all occurance of the job class.
     * Use a list of key to exclude them from the select
     *
     * @author Gaetan Brenckle
     *
     * @param excludeList - {@link List} - list of key
     * @return - {@link List} - a list that contain all occurance of {@link Customer}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    public List<Customer> selectAll(List<Customer> excludeList) throws SQLException, InvalidKeyException {
        DAO_CBilling dao_cBilling = new DAO_CBilling(this.connectionHandle_);
        DAO_Commercial dao_commercial = new DAO_Commercial(this.connectionHandle_);
        DAO_Risk dao_risk = new DAO_Risk(this.connectionHandle_);
        final List<Customer> retCustomers = new ArrayList<>();

        if (excludeList == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("The parameter of this method is null");
            }
            return null;
        }

        final List<String> listKey = new ArrayList<>();
        excludeList.forEach(data -> listKey.add(data.getClient_CodeClient()));

        final String selectAll_sql = String.format("%s %s %s %s %s %s ",
                String.format("SELECT  %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s ", info.CLIENT_CODECLIENT, info.CLIENT_RAISONSOCIALE, info.CLIENT_DENOMINATION, info.CLIENT_REPRESENTANT, info.CLIENT_CODEPOSTAL, info.CLIENT_VILLE, info.CLIENT_PAYS, info.CLIENT_CODEPAYEUR, info.CLIENT_CANUM, info.CLIENT_TELEPHONE, info.CLIENT_TELECOPIE, info.CLIENT_EMAIL, info.CLIENT_SITE, info.CLIENT_COMMERCIAL, info.CLIENT_RISQUE, info.CLIENT_CODEAFFAIRE),
                String.format("FROM %s", info.TABLESAGE),
                String.format("WHERE %s = 0", info.CT_TYPE),
                String.format("AND %s = 0", info.CT_SOMMEIL),
                (PreparedStatementAware.prepareList(listKey) == null) ?
                        "" : String.format("AND %s NOT IN (%s)", info.CLIENT_CODECLIENT, PreparedStatementAware.prepareList(listKey)),
                String.format("ORDER BY %s ", info.CLIENT_CODECLIENT));

        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(connectionHandle_.getConnectionHandleList().get(info.DEFAULTDB.toString()).prepareStatement(selectAll_sql));
        prepSelectAll.setList(listKey, PreparedStatementAware.listType.STRING);

        try (final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
            while (resultSelectAll.next()) {
                Customer customer = new Customer()
                        .setClient_CodeClient(resultSelectAll.getString(info.CLIENT_CODECLIENT.toString()))
                        .setClient_RaisonSociale(resultSelectAll.getString(info.CLIENT_RAISONSOCIALE.toString()))
                        .setClient_denomination(resultSelectAll.getString(info.CLIENT_DENOMINATION.toString()))
                        .setClient_representant(resultSelectAll.getString(info.CLIENT_REPRESENTANT.toString()))
                        .setClient_codePostal(resultSelectAll.getString(info.CLIENT_CODEPOSTAL.toString()))
                        .setClient_ville(resultSelectAll.getString(info.CLIENT_VILLE.toString()))
                        .setClient_pays(resultSelectAll.getString(info.CLIENT_PAYS.toString()))
                        .setClient_codePayeur(dao_cBilling.select(resultSelectAll.getString(info.CLIENT_CODEPAYEUR.toString())))
                        .setClient_CA_Num(resultSelectAll.getString(info.CLIENT_CANUM.toString()))
                        .setClient_telephone(resultSelectAll.getString(info.CLIENT_TELEPHONE.toString()))
                        .setClient_telecopie(resultSelectAll.getString(info.CLIENT_TELECOPIE.toString()))
                        .setClient_email(resultSelectAll.getString(info.CLIENT_EMAIL.toString()))
                        .setClient_site(resultSelectAll.getString(info.CLIENT_SITE.toString()))
                        .setClient_Commercial(dao_commercial.select(resultSelectAll.getLong(info.CLIENT_COMMERCIAL.toString())))
                        .setClient_Risque(dao_risk.select(resultSelectAll.getLong(info.CLIENT_RISQUE.toString())))
                        .setClient_codeAffaire(resultSelectAll.getString(info.CLIENT_CODEAFFAIRE.toString()));
                retCustomers.add(customer);
            }
        }
        return retCustomers;
    }

    /**
     * INSERT the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link Customer} - insert the job class.
     * @return - boolean - the state of the sql insert.
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    @Override
    public boolean insert(Customer obj) throws SQLException, InvalidKeyException {
        DAO_CBilling dao_infoMachine = new DAO_CBilling(this.connectionHandle_);
        DAO_Commercial dao_commercial = new DAO_Commercial(this.connectionHandle_);
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", infoOld.TABLENAME));
            }
            return false;
        }

        if (obj.getClient_CodeClient() == null || obj.getClient_CodeClient().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", infoOld.CODECLIENT));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        if (obj.getClient_codePayeur().getCf_CodePayeur() != null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is already insert", infoOld.CODEPAYEUR));
            }
        } else {
            if (!dao_infoMachine.insert(obj.getClient_codePayeur()) || obj.getClient_codePayeur() == null || obj.getClient_codePayeur().getCf_CodePayeur().isEmpty()) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(String.format("The insertion of the %s has failed.", infoOld.CODEPAYEUR));
                }
                return false;
            }
        }

        if (obj.getClient_Commercial().getCommercial_Id() != null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is already insert", infoOld.CO_NO));
            }
        } else {
            if (!dao_commercial.insert(obj.getClient_Commercial()) || obj.getClient_Commercial() == null) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(String.format("The insertion of the %s has failed.", infoOld.CO_NO));
                }
                return false;
            }
        }

        String insert_sql = String.format("%s %s ",
                String.format("INSERT INTO %s (%s, %s, %s, %s)", info.TABLENAME, infoOld.CODECLIENT, infoOld.RAISONSOCIALE, infoOld.CODEPAYEUR, infoOld.CO_NO),
                "VALUES (?, ?, ?, ?)");

        final PreparedStatementAware prepInsert = new PreparedStatementAware(connectionHandle_.getConnectionHandleList().get(infoOld.OLDDB.toString()).prepareStatement(insert_sql));
        prepInsert.setString(obj.getClient_CodeClient());
        prepInsert.setString(obj.getClient_RaisonSociale());
        prepInsert.setString(obj.getClient_codePayeur().getCf_CodePayeur());
        prepInsert.setLong(obj.getClient_Commercial().getCommercial_Id());

        retBool = prepInsert.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Insert a new %s : [%s]", infoOld.TABLENAME, prepInsert.printSqlStatement(insert_sql));

            LOGGER.info(printedSql);
            DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }
        return retBool;
    }

    /**
     * UPDATE the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link Customer} - insert the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    @Override
    public boolean update(Customer obj) throws SQLException, InvalidKeyException {
        DAO_CBilling dao_cBilling = new DAO_CBilling(this.connectionHandle_);
        DAO_Commercial dao_commercial = new DAO_Commercial(this.connectionHandle_);
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", infoOld.TABLENAME));
            }
            return false;
        }

        if (obj.getClient_CodeClient() == null || obj.getClient_CodeClient().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", infoOld.CODECLIENT));
            }
            retBool = false;
        }

        if (!retBool) {
            return false;
        }

        if (obj.getClient_codePayeur().getCf_CodePayeur() != null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is already insert", infoOld.CODEPAYEUR));
            }
        } else {
            if (!dao_cBilling.insert(obj.getClient_codePayeur()) || obj.getClient_codePayeur() == null || obj.getClient_codePayeur().getCf_CodePayeur().isEmpty()) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(String.format("The insertion of the %s has failed.", infoOld.CODEPAYEUR));
                }
                return false;
            }
        }

        if (obj.getClient_Commercial().getCommercial_Id() != null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is already insert", infoOld.CO_NO));
            }
        } else {
            if (!dao_commercial.insert(obj.getClient_Commercial()) || obj.getClient_Commercial() == null) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(String.format("The insertion of the %s has failed.", infoOld.CO_NO));
                }
                return false;
            }
        }

        String update_sql = String.format("%s %s %s ",
                String.format("UPDATE %s", infoOld.TABLENAME),
                String.format("SET %s = ?, %s = ?, %s = ?", infoOld.RAISONSOCIALE, infoOld.CODEPAYEUR, infoOld.CO_NO),
                String.format("WHERE %s = ?", infoOld.CODECLIENT));

        final PreparedStatementAware prepUpdate = new PreparedStatementAware(connectionHandle_.getConnectionHandleList().get(infoOld.OLDDB.toString()).prepareStatement(update_sql));
        prepUpdate.setString(obj.getClient_RaisonSociale());
        prepUpdate.setString(obj.getClient_codePayeur().getCf_CodePayeur());
        prepUpdate.setLong(obj.getClient_Commercial().getCommercial_Id());

        prepUpdate.setString(obj.getClient_CodeClient());

        retBool = prepUpdate.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Update a new %s : [%s]", infoOld.TABLENAME, prepUpdate.printSqlStatement(update_sql));

            LOGGER.info(printedSql);
            DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }
        return retBool;
    }

    /**
     * INSERT OR UPDATE the job class depending if it exists.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link Customer} - insert the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw when the object needed to be insert
     */
    @Override
    public boolean upsert(Customer obj) throws SQLException, InvalidKeyException {
        DAO_CBilling dao_cBilling = new DAO_CBilling(this.connectionHandle_);
        DAO_Commercial dao_commercial = new DAO_Commercial(this.connectionHandle_);
        boolean updateSuccess = false;
        boolean insertSuccess = false;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", infoOld.TABLENAME));
            }
            return false;
        }

        if (obj.getClient_CodeClient() == null || obj.getClient_CodeClient().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", infoOld.CODECLIENT));
            }
            return false;
        }

        if (!dao_cBilling.upsert(obj.getClient_codePayeur())) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The insertion of the %s has failed.", infoOld.CODECLIENT));
            }
            return false;
        }

        if (!dao_commercial.upsert(obj.getClient_Commercial())) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The insertion of the %s has failed.", infoOld.CO_NO));
            }
            return false;
        }

        if (update(obj)) {
            updateSuccess = true;
        }

        String insert_sql = String.format("%s %s %s %s ",
                String.format("INSERT INTO %s( %s, %s, %s, %s)", infoOld.TABLENAME, infoOld.CODECLIENT, infoOld.RAISONSOCIALE, infoOld.CODEPAYEUR, infoOld.CO_NO),
                "SELECT DISTINCT ?, ?, ?, ? ",
                "FROM (SELECT First(ID) From DUMMY) dummy",
                String.format("WHERE NOT EXISTS (%s %s %s)",
                        "SELECT 1 ",
                        String.format("FROM %s", infoOld.TABLENAME),
                        String.format("WHERE %s = ? ", infoOld.CODECLIENT)
                )
        );

        final PreparedStatementAware prepInsert = new PreparedStatementAware(connectionHandle_.getConnectionHandleList().get(infoOld.OLDDB.toString()).prepareStatement(insert_sql));
        prepInsert.setString(obj.getClient_CodeClient());
        prepInsert.setString(obj.getClient_RaisonSociale());
        prepInsert.setString(obj.getClient_codePayeur().getCf_CodePayeur());
        prepInsert.setLong(obj.getClient_Commercial().getCommercial_Id());

        prepInsert.setString(obj.getClient_CodeClient());

        if (prepInsert.executeUpdate() > 0) {
            insertSuccess = true;
        }

        if (LOGGER.isInfoEnabled() && insertSuccess) {
            String printedSql = String.format("Upsert a new %s : [%s]", infoOld.TABLENAME, prepInsert.printSqlStatement(insert_sql));

            LOGGER.info(printedSql);
            DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }
        return updateSuccess || insertSuccess;
    }

    /**
     * DELETE the job class.
     * Cannot use (ODBC table)
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link Customer} - insert the job class.
     * @return - boolean - the state of the sql delete.
     * @throws SQLException - throw the exception to force a try catch when used.
     */
    @Override
    public boolean delete(Customer obj) throws SQLException {
        boolean retBool = true;

        if (obj == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("%s on parameter to insert is null.", info.TABLENAME));
            }
            return false;
        }

        if (obj.getClient_CodeClient() == null || obj.getClient_CodeClient().isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("The parameter %s on the class is null", infoOld.CODECLIENT));
            }
            return false;
        }

        if (!retBool) {
            return false;
        }

        String delete_sql = String.format("%s %s ",
                String.format("DELETE FROM %s", infoOld.TABLENAME),
                String.format("WHERE %s = ?", infoOld.CODECLIENT));

        final PreparedStatementAware prepDelete = new PreparedStatementAware(connectionHandle_.getConnectionHandleList().get(infoOld.OLDDB.toString()).prepareStatement(delete_sql));
        prepDelete.setString(obj.getClient_CodeClient());

        retBool = prepDelete.executeUpdate() > 0;

        if (LOGGER.isInfoEnabled() && retBool) {
            String printedSql = String.format("Delete a new %s : [%s]", infoOld.TABLENAME, prepDelete.printSqlStatement(delete_sql));

            LOGGER.info(printedSql);
            DbLogger.getInstance().dbLog(Level.INFO, printedSql);
        }

        return retBool;
    }
}
