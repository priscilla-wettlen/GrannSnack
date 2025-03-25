package com.grupp10.grannsnack.Web;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupp10.grannsnack.Controller.NeighbourService;
import com.grupp10.grannsnack.Model.Neighbour;
import com.grupp10.grannsnack.Model.NeighbourOTD;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
public class WebController {

    private final NeighbourService neighbourService;

    public WebController(NeighbourService neighbourService) {
        this.neighbourService = neighbourService;
    }

    @GetMapping("/")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/neighbours")
    public Iterable<Neighbour> listNB() {
        return neighbourService.getNeighbours();
    }

    @DeleteMapping("/neighbours/remove/{id}")
    public void removeNeighbour(@PathVariable Integer id) {
        Neighbour neighbour = neighbourService.removeNeighbour(id);
        if(neighbour == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/neighbours")
    public ResponseEntity<String> addNeighbour(@RequestBody NeighbourOTD neighbour) {
        try {
            // Validate input
            if (neighbour == null || neighbour.getName() == null || neighbour.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid neighbour data");
            }

            // Save neighbour
            neighbourService.saveNeighbour(new Neighbour(neighbour.getName(), neighbour.getAge()));

            return ResponseEntity.ok("Neighbour added successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error adding neighbour: " + e.getMessage());
        }
    }
}
