package com.tuempresa.facturacion.calculadores;

import static org.openxava.jpa.XPersistence.getManager;

import org.openxava.calculators.*;

import lombok.*;

import com.tuempresa.facturacion.modelo.*;

public class CalculadorPrecioPorUnidad implements ICalculator {
	
	@Getter @Setter
	 int numeroProducto;
	
	@Override
	public Object calculate() throws Exception {
	    Producto producto = getManager()
	             .find(Producto.class, numeroProducto);
	 return producto.getPrecio();
	 
	}
	
}
