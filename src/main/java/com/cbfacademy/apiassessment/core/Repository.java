package com.cbfacademy.apiassessment.core;

import java.io.Serializable;
import java.util.List;

/**
 * The Repository interface defines the basic operations for managing entities in a repository.
 *
 * @param <T>  the type of entities in the repository
 * @param <ID> the type of unique identifier for the entities
 */
public interface Repository<T, ID extends Serializable> {

    /**
     * Retirieves all entities from the repository.
     *
     * @return a list of all entities
     */

    List<T> retrieveAll() throws PersistenceException;

    /**
     * Finds an entity by its unique identifier.
     *
     * @param id the identifier of the entity
     * @return the found entity, or null if no such entity exists
     */
    T findById(ID id);
    T save(T entity) throws IllegalArgumentException, PersistenceException;

    /**
     * Deletes an entity from the repository based on its unique identifier.
     *
     * @param id the unique identifier of the entity to delete
     * @throws IllegalArgumentException if the argument is not valid
     * @throws PersistenceException if there is an issue with the persistence layer
     */

    void delete(ID id) throws IllegalArgumentException, PersistenceException;

    /**x
     * Updates an existing entity in the repository.
     *
     * @param entity the entity to update
     * @return the updated entity
     */
    T update(T entity) throws IllegalArgumentException, PersistenceException;
}
