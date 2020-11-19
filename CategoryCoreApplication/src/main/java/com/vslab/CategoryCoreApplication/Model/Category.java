package com.vslab.UserSrvApplication.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Catgory")
public class Category {

    //Primärschlüssel, dessen ID automatische erzeugt wird
    @Id
    @GeneratedValue
    private Long id;




}
