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
    T findById(ID id, boolean lock);

    List<T> findAll();

    //List<T> findByExample(T exampleInstance, String... excludeProperty);

    T save(T entity);

    void delete(T entity);
}
