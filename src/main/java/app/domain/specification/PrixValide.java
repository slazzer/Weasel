package app.domain.specification;

import java.math.BigDecimal;

import app.domain.Prix;

public class PrixValide {
	public static boolean isSatisfiedBy(Prix prix) {
		return prix.getValeur() != null
				&& prix.getValeur().compareTo(BigDecimal.ZERO) > 0;
	}
}
