package com.yugabyte.datasource.common;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMappingRepository extends JpaRepository<UserMapping, Integer> {

}
