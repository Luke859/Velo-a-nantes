package com.formation.velo.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "stations")
public class Station implements Serializable {

	private static final long serialVersionUID = -767070904974486422L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private String name;
    private String address;
	private Double lattitude;
    private Double longitude;
    private Integer status;
    private Integer bike_stands;
    private Integer available_bikes;
    private Integer available_bikes_stands;
    private String recordid;

	// @Override
	// public boolean equals(Object o) {
	// 	if (this == o) return true;
	// 	if (o == null || getClass() != o.getClass()) return false;
	// 	return Objects.equals(id, station.id) && Objects.equals(name, velo.name) && Objects.equals(surname, user.surname);
	// }

	@Override
	public int hashCode() {
		return Objects.hash(id, name, address, lattitude, longitude, status, bike_stands, available_bikes, available_bikes_stands, recordid);
	}
}

