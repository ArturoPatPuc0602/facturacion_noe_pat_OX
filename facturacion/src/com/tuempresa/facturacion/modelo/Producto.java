package com.tuempresa.facturacion.modelo;
import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity @Getter @Setter
public class Producto {
	
	@Id @Column(length=9)
	 int numero;
	 @Column(length=50) @Required
	 String descripcion;
	 
	 @ManyToOne(
			 fetch=FetchType.LAZY,
			  optional=true)
			  @DescriptionsList
			  Categoria categoria; 

	 @Money
	 BigDecimal precio;
	 
	 @Files
	 
	 @Column(length=32)
	 String fotos;
	 
	 @TextArea
	 String observaciones;
<<<<<<< HEAD
=======
	 
	 @ManyToOne(fetch=FetchType.LAZY)
	 @DescriptionsList
	 Autor autor;
>>>>>>> e169458 (LECCION 1,2,3,4 Y 5_FINAL)
}
