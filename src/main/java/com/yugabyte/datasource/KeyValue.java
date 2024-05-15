/**
 * 
 */
package com.yugabyte.datasource;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
