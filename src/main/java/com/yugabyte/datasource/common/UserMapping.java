package com.yugabyte.datasource.common;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity for the user_mapping table
 */
@Entity
@Table(name = "user_mapping")
@Getter
@Setter
@ToString
public class UserMapping {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "targetdb")
    private String targetdb;

    @Column(name = "last_update")
    private Date lastUpdate;
}
