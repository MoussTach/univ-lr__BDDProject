package fr.bdd.job.dao;

import fr.bdd.dataconnection.DataConnection;

import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface for the pattern DAO.
 *
 * @author Gaetan Brenckle
 *
 * @param <T> - the associate job class of the class that implement this interface.
 */
public interface Dao<T> {

    /**
     * Setter of the connection.
     *
     * @author Gaetan Brenckle
     *
     * @param conn - {@link DataConnection} - Connection used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    void setConnection(DataConnection conn) throws InvalidKeyException;

    /**
     * SELECT of all occurance of the job class.
     *
     * @author Gaetan Brenckle
     *
     * @return - {@link List} - a list that contain all occurance of {@link T}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    List<T> selectAll() throws SQLException, InvalidKeyException;

    /**
     * SELECT of all occurance of the job class.
     * Use a list of key to exclude them from the select
     *
     * @author Gaetan Brenckle
     *
     * @param excludeList - {@link List} - list of key
     * @return - {@link List} - a list that contain all occurance of {@link T}, the job class associate.
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    List<T> selectAll(List<T> excludeList) throws SQLException, InvalidKeyException;

    /**
     * INSERT the job class.
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link T} - insert the job class.
     * @return - boolean - the state of the sql insert.
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    boolean insert(T obj) throws SQLException, InvalidKeyException;

    /**
     * UPDATE the job class.
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link T} - update the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    boolean update(T obj) throws SQLException, InvalidKeyException;

    /**
     * INSERT OR UPDATE the job class depending if it exists.
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link T} - update the job class.
     * @return - boolean - the state of the sql update.
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw the exception to force a try catch when used.
     */
    boolean upsert(T obj) throws SQLException, InvalidKeyException;

    /**
     * DELETE the job class.
     *
     * @author Gaetan Brenckle
     *
     * @param obj - {@link T} - delete the job class.
     * @return - boolean - the state of the sql delete.
     * @throws SQLException - throw the exception to force a try catch when used.
     * @throws InvalidKeyException - throw this exception when the given list dont have the key wanted
     */
    boolean delete(T obj) throws SQLException, InvalidKeyException;
}
