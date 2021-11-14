package com.molinari.cercalezione.domain;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-11-14T16:51:27.186+0100")
@StaticMetamodel(Tag.class)
public class Tag_ {
	public static volatile SingularAttribute<Tag, String> descrizione;
	public static volatile SingularAttribute<Tag, Integer> idTag;
	public static volatile ListAttribute<Tag, Lezione> leziones;
}
