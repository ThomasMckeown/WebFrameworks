package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Locale;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name="LocaleBean")
@SessionScoped
public class locale_bean {
	
	private static final long serialVersionUID = 2L;

	
	//############################################	
	//# Properties	
	//############################################	
	
	private String locale;
	private Map<String,Object> countryMap;
	
	//############################################	
	//# Constructors	
	//############################################	
	
	public locale_bean(){
		// Creates a Map of supported languages and their locales
		this.countryMap = new LinkedHashMap<String,Object>();
		this.countryMap.put("English", new Locale("en"));
		this.countryMap.put("French",  new Locale("fr","FR"));
	}
	
	//############################################	
	//# Getters	and Setters
	//############################################

	public Map<String, Object> getCountries() {
		return this.countryMap;
	}
	
	public String getLocale() {
		return this.locale;
	}
	
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public String getObjectHash() {
		return this.toString();
	}
	
	//############################################	
	//# Event Handlers
	//############################################
	
	/**
	 * Executed when the locale changes
	 */
	public void localeChanged(ValueChangeEvent e){
		
		String localeStr = e.getNewValue().toString();		
		
    for (Map.Entry<String, Object> entry : countryMap.entrySet()) {        
      if(entry.getValue().toString().equals(localeStr)){
				Locale locale = (Locale)entry.getValue();
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);        		
      }
    }
	}
	
}
