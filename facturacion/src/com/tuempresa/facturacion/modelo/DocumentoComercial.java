package com.tuempresa.facturacion.modelo;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;

import org.openxava.annotations.*;
import org.openxava.calculators.*;

import com.tuempresa.facturacion.calculadores.*;

import lombok.*;

@Entity @Getter @Setter
@View(members=
"anyo, numero, fecha;" +
     "datos{"+
       "cliente;" +
       "detalles;" +
       "observaciones"+
"}"
)
abstract public class DocumentoComercial extends Identificable {
	
	 
	@Column(length=4)
	 @DefaultValueCalculator(CurrentYearCalculator.class)
	 int anyo;
	 
	@Column(length=6)
	@DefaultValueCalculator(value = CalculadorSiguienteNumeroParaAnyo.class,
	 properties = @PropertyValue(name="anyo"))
	 int numero;
	 
	@Required
	@DefaultValueCalculator(CurrentLocalDateCalculator.class)
	LocalDate fecha;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@ReferenceView("Simple")
	Cliente cliente;
	
	@ElementCollection
	@ListProperties("producto.numero, producto.descripcion, cantidad, precioPorUnidad, "
			+"importe+[" 
			+"documentoComercial.porcentajeIVA," 
			+"documentoComercial.iva," 
			+"documentoComercial.importeTotal" 
			+"]"
			 ) 
	
	Collection<Detalle> detalles;
	
	@DefaultValueCalculator(CalculadorPorcentajeIVA.class)
	@Digits(integer=2, fraction=0)
	BigDecimal porcentajeIVA;
	
	@ReadOnly
	@Money
	@Calculation("sum(detalles.importe) * porcentajeIVA / 100")
	BigDecimal iva;
	
	@ReadOnly
	@Money
	@Calculation("sum(detalles.importe) + iva")
	BigDecimal importeTotal;
	
	
	@Stereotype("MEMO")
	 String observaciones;
	
	

}
