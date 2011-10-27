package app.domain;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Prix {

	public Prix() {
	}

	@Id
	@GeneratedValue
	private Long db_identifier;

	public Long getDb_identifier() {
		return db_identifier;
	}

	@Basic
	BigDecimal valeur;

	public Prix(BigDecimal valeur) {
		super();
		this.valeur = valeur;
	}

	public BigDecimal getValeur() {
		return valeur;
	}

}
