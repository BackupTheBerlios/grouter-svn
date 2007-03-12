package org.grouter.domain.daolayer;


import java.io.Serializable;
import java.util.List;

/**
 * Business data access objects share this interface.
 * <br/>
 * All CRUD (create, read, update, delete) basic data access operations are
 * isolated in this interface and shared accross all DAO implementations.
 * The current design is for a state-management oriented persistence layer
 * (for example, there is no UDPATE statement function) that provides
 * automatic transactional dirty checking of business objects in persistent
 * state.
 * <p/>
 * See the Hibernate Caveat tutorial and complementary code by Christian Bauer @ jboss )
 *
 * @author Georges Polyzois
 */
public interface GenericDAO<T, ID extends Serializable>
{
    /**
     * Find Entity by id.
     *
     * @param id the id of the entity
     * @return an entity
     */
    T findById(ID id);

    /**
     * Find entity by id, join using properties of entity class (optionally).
     *
     * @param id the id of the entity to search for
     * @param joinProps properties on the entity class we should join in
     * @return
     */
    T findById(ID id, String... joinProps);

    /**
     * Find entity by id and clazz, join using properties of entity class (optionally).
     *
     * @param clazz entity class
     * @param id the id of the entity to search for
     * @param joinProps properties on the entity class we should join in
     * @return
     */
    T findById(Class clazz, T id, String... joinProps);

    /**
     * Get all entities.
     * @return list of entitites.
     */
    List<T> findAll();

    /**
     * Find entities, excluding properties.
     * @param exampleInstance
     * @param excludeProperty
     * @return
     */
    List<T> findByExample(T exampleInstance, String... excludeProperty);

    T save(T entity);

    void delete(T entity);

    void delete( String id );

}
