package com.tuempresa.facturacion.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.logging.*;
import org.openxava.util.*;

public class PreferenciasFacturacion {
	
	private final static String ARCHIVO_PROPIEDADES="facturacion.properties";
	private static Log log = LogFactory.getLog(PreferenciasFacturacion.class);
	
	
	private static Properties propiedades;
	
	private static Properties getPropiedades() {
	 if (propiedades == null) {
	      PropertiesReader reader = 
	         new PropertiesReader(
	                PreferenciasFacturacion.class, ARCHIVO_PROPIEDADES);
	
	      try {
	          propiedades = reader.get();
	
	      } catch (IOException ex) {
	              log.error(
	                      XavaResources.getString( 
	                              "properties_file_error",
	                               ARCHIVO_PROPIEDADES),
	                      ex);
	             propiedades = new Properties();
	     }
	}
	 return propiedades;
  }
	public static BigDecimal getPorcentajeIVADefecto() { // El ?nico m?todo p?blico
		 return new BigDecimal(getPropiedades().getProperty("porcentajeIVADefecto"));
 }
}
