/**
 * 
 */
package com.yugabyte.datasource.plant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity for the keyvalue table
 */
@Entity
@Getter
@Setter
@ToString
public class KeyValue {

	@Id
	@Column(name = "k")
	private String k;

	@Column(name = "v")
	private String v;

}
