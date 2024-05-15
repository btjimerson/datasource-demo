package com.yugabyte.datasource;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyValueRepository extends JpaRepository<KeyValue, String> {

}
