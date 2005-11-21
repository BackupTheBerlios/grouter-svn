package org.grouter.domain.dao;


import java.util.List;
import java.io.Serializable;

/**
 * Business data access objects share this interface.
 * <br/>
 * All CRUD (create, read, update, delete) basic data access operations are
 * isolated in this interface and shared accross all DAO implementations.
 * The current design is for a state-management oriented persistence layer
 * (for example, there is no UDPATE statement function) that provides
 * automatic transactional dirty checking of business objects in persistent
 * state.
 *
* See the Hibernate Caveat tutorial and complementary code by Christian Bauer @ jboss )
 *
 * @author Georges Polyzois
 */
public interface GenericDAO<T, ID extends Serializable>
{
    T findById(ID id, boolean lock);
    List<T> findAll();
    List<T> findByExample(T exampleInstance);
    T saveOrUpdate(T entity);
    void delete(T entity);
}
