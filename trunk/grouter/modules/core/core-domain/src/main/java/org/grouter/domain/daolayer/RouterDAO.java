package org.grouter.domain.daolayer;

import org.grouter.domain.entities.Router;

import java.util.List;

/**
 * @author Georges Polyzois
 */
public interface RouterDAO extends GenericDAO<Router, String>
{


    List<Router> findAllDistinct();


    //List<Router> findByProjection();




}
