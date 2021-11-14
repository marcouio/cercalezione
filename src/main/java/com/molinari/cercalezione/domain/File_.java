package com.molinari.cercalezione.domain;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-11-14T16:51:27.171+0100")
@StaticMetamodel(File.class)
public class File_ {
	public static volatile SingularAttribute<File, Integer> idFile;
	public static volatile SingularAttribute<File, String> nome;
	public static volatile SingularAttribute<File, String> path;
	public static volatile ListAttribute<File, Lezione> leziones;
}
