package com.yugabyte.datasource.plant;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for the keyvalue table
 */
public interface KeyValueRepository extends JpaRepository<KeyValue, String> {

}
