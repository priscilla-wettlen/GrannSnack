package com.grupp10.grannsnack.Controller;

import com.grupp10.grannsnack.Model.Neighbour;
import org.springframework.stereotype.Service;

@Service
public class NeighbourService {

    DBInterface db;

    public NeighbourService(DBInterface db) {
        this.db = db;
    }

    public Iterable<Neighbour> getNeighbours() {
        return db.findAll();
    }

    public Neighbour getNeighbour(Integer id) {
        return db.findById(id).get();
    }

    public Neighbour removeNeighbour(Integer id) {
        if(!db.existsById(id)) {
            return null;
        }
        Neighbour neighbour = db.findById(id).get();
        db.deleteById(id);
        return neighbour;
    }

    public void saveNeighbour(Neighbour neighbour) {
        db.save(neighbour);
    }
}
