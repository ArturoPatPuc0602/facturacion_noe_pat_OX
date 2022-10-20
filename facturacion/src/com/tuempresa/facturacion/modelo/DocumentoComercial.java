package com.tuempresa.facturacion.modelo;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;

import org.openxava.annotations.*;
import org.openxava.calculators.*;
import org.openxava.jpa.XPersistence;

import com.tuempresa.facturacion.calculadores.*;

import lombok.*;

@Entity @Getter @Setter
@View(members=
"anyo, numero, fecha," +
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
	@ReadOnly
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
	
	@PrePersist
	private void calcularNumero() {
		 Query query = XPersistence.getManager().createQuery(
		"select max(f.numero) from " +
		getClass().getSimpleName() +
		" f where f.anyo = :anyo");
		 query.setParameter("anyo", anyo);
		 Integer ultimoNumero = (Integer) query.getSingleResult();
		 this.numero = ultimoNumero == null ? 1 : ultimoNumero + 1;
		}
	
	
	@Stereotype("MEMO")
	 String observaciones;
	
	@org.hibernate.annotations.Formula("IMPORTETOTAL * 0.10")
	@Setter(AccessLevel.NONE)
	@Money
	BigDecimal beneficioEstimado; 
	
	

}
