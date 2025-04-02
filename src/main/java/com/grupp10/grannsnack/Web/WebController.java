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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Controller
public class WebController {

    private final NeighbourService neighbourService;

    public WebController(NeighbourService neighbourService) {
        this.neighbourService = neighbourService;
    }

    @GetMapping("/home")
    public String helloWorld() {
        return "home";
    }

    @GetMapping("/user/home")
    public String handleUserHome() {
        return "home_user";
    }

    @GetMapping("/admin/home")
    public String handleAdminHome() {
        return "home_admin";
    }

    @GetMapping("user/neighbours")
    public Iterable<Neighbour> listNB() {
        return neighbourService.getNeighbours();
    }

    @DeleteMapping("admin/neighbours/{id}")
    public ResponseEntity<String> removeNeighbour(@PathVariable Integer id) {
        ResponseEntity<String> response;
        System.out.println("Debugging 1");
        Neighbour neighbour = neighbourService.removeNeighbour(id);
        response = ResponseEntity.ok("Neighbour removed successfully");
        if(neighbour == null) {
            response = ResponseEntity.notFound().build();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping("admin/neighbours")
    public ResponseEntity<String> addNeighbour(@RequestBody NeighbourOTD neighbour) {
        try {
            if (neighbour == null || neighbour.getName() == null || neighbour.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid neighbour data");
            }

            neighbourService.saveNeighbour(new Neighbour(neighbour.getName(), neighbour.getAge()));

            return ResponseEntity.ok("Neighbour added successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error adding neighbour: " + e.getMessage());
        }
    }
}
