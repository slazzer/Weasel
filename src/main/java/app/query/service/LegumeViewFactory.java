package app.query.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import app.domain.Legume;
import app.query.view.LegumeView;

@Service
public class LegumeViewFactory {

	public List<LegumeView> convert(List<Legume> legumes) {
		List<LegumeView> legumeViews = new ArrayList<LegumeView>();
		LegumeView legumeView;
		for (Legume l : legumes) {
			legumeView = convert(l);
			legumeViews.add(legumeView);
		}
		return legumeViews;
	}

	public LegumeView convert(Legume legume) {
		return new LegumeView(legume.getDb_identifier(), legume.getNom());
	}
	
}