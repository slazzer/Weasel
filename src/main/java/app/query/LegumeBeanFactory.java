package app.query;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import app.domain.Legume;
import app.query.beans.LegumeBean;

@Service
public class LegumeBeanFactory {

	public List<LegumeBean> convert(List<Legume> listeDeLegume) {
		List<LegumeBean> listedeLegumeBean = new ArrayList<LegumeBean>();
		LegumeBean bean ;
		for(Legume l : listeDeLegume){
			bean = convert(l);
			listedeLegumeBean.add(bean);
		}
		return listedeLegumeBean;
	}

	public LegumeBean convert(Legume l) {
		LegumeBean bean;
		bean = new LegumeBean();
		bean.setId(l.getDb_identifier());
		bean.setNom(l.getNom());
		return bean;
	}
}