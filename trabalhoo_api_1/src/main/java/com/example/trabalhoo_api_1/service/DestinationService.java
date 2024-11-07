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

    // Método para adicionar um destino
    public Destination addDestination(Destination destination) {
        // Encontra o maior ID atual e define o próximo ID manualmente
        Long nextId = destinations.stream()
                .mapToLong(Destination::getId)
                .max()
                .orElse(0L) + 1;
        destination.setId(nextId);
        destinations.add(destination);
        return destination;
    }

    // Listar todos os destinos
    public List<Destination> getAllDestinations() {
        return new ArrayList<>(destinations);
    }

    // Buscar destinos por nome ou localização
    public List<Destination> searchDestinations(String query) {
        return destinations.stream()
                .filter(d -> d.getNome().toLowerCase().contains(query.toLowerCase()) ||
                        d.getLocalizacao().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Visualizar informações de um destino específico
    public Optional<Destination> getDestinationById(Long id) {
        return destinations.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }

    // Reservar pacote de viagem (marcar como indisponível)
    public Optional<Destination> reservePackage(Long id) {
        Optional<Destination> destination = getDestinationById(id);
        destination.ifPresent(d -> d.setDisponivel(false));
        return destination;
    }

    // Excluir um destino
    public boolean deleteDestination(Long id) {
        return destinations.removeIf(d -> d.getId().equals(id));
    }

    public List<Destination> getReservedDestinations() {
        return destinations.stream()
                .filter(destination -> !destination.isDisponivel())
                .collect(Collectors.toList());
    }

}
