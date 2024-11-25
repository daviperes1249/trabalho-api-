package com.example.trabalhoo_api_1.service;

import com.example.trabalhoo_api_1.entity.Destination;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DestinationService {
    private final List<Destination> destinations = new ArrayList<>();

   
    public Destination addDestination(Destination destination) {
       
        Long nextId = destinations.stream()
                .mapToLong(Destination::getId)
                .max()
                .orElse(0L) + 1;
        destination.setId(nextId);
        destinations.add(destination);
        return destination;
    }

  
    public List<Destination> getAllDestinations() {
        return new ArrayList<>(destinations);
    }

    
    public List<Destination> searchDestinations(String query) {
        return destinations.stream()
                .filter(d -> d.getNome().toLowerCase().contains(query.toLowerCase()) ||
                        d.getLocalizacao().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    
    public Optional<Destination> getDestinationById(Long id) {
        return destinations.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }

    
    public Optional<Destination> reservePackage(Long id) {
        Optional<Destination> destination = getDestinationById(id);
        destination.ifPresent(d -> d.setDisponivel(false));
        return destination;
    }

    
    public boolean deleteDestination(Long id) {
        return destinations.removeIf(d -> d.getId().equals(id));
    }

    public List<Destination> getReservedDestinations() {
        return destinations.stream()
                .filter(destination -> !destination.isDisponivel())
                .collect(Collectors.toList());
    }

}
