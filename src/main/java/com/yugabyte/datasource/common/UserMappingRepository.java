package com.yugabyte.datasource.common;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for the user_mapping table
 */
public interface UserMappingRepository extends JpaRepository<UserMapping, Integer> {

}
