package com.yujo.dao;

import java.util.List;

import com.yujo.model.Option;

public interface IDaoOption {

	List<Option> options(int id_post);

    boolean add(Option o);
    
    //aggiunge l'id di un utente che ha messo un nuovo voto
    boolean add(Option o, int id);

    boolean delete(int id);
    
    boolean update(Option o);
	
}
