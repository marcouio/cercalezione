package com.molinari.cercalezione.domain;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-11-14T16:51:27.178+0100")
@StaticMetamodel(Lezione.class)
public class Lezione_ {
	public static volatile SingularAttribute<Lezione, Integer> anno;
	public static volatile SingularAttribute<Lezione, String> data;
	public static volatile SingularAttribute<Lezione, Integer> idLezione;
	public static volatile SingularAttribute<Lezione, String> nome;
	public static volatile SingularAttribute<Lezione, Integer> numero;
	public static volatile ListAttribute<Lezione, File> files;
	public static volatile ListAttribute<Lezione, Tag> tags;
}
