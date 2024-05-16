package com.yugabyte.datasource.plant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyValueRepository extends JpaRepository<KeyValue, String> {

}
