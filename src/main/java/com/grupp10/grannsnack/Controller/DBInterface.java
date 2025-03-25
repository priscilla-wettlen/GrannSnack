package com.grupp10.grannsnack.Controller;

import com.grupp10.grannsnack.Model.Neighbour;
import org.springframework.data.repository.CrudRepository;

public interface DBInterface extends CrudRepository<Neighbour, Integer> {
}
