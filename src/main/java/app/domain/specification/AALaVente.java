package app.domain.specification;

import app.domain.Agriculteur;
import app.domain.Legume;

public class AALaVente {

	public static boolean isSatisfiedBy(Agriculteur agriculteur, Legume legume) {
		if(agriculteur.getALaVente()!=null){
			for(Legume legumeDeLaGriculteur : agriculteur.getALaVente().keySet()){
				if(legume.getNom().equals(legumeDeLaGriculteur.getNom())){
					return true;
				}
			}
		}
		return false;
	}

}
