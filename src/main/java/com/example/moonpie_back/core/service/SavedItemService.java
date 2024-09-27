package com.example.moonpie_back.core.service;

import com.example.moonpie_back.api.dto.ItemForCatalogDto;
import com.example.moonpie_back.core.entity.Client;
import com.example.moonpie_back.core.entity.Color;
import com.example.moonpie_back.core.entity.Item;
import com.example.moonpie_back.core.entity.Size;
import com.example.moonpie_back.core.repository.ClientRepository;
import com.example.moonpie_back.core.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedItemService {

    private final ClientRepository clientRepository;

    private final ItemRepository itemRepository;

    public List<ItemForCatalogDto> getSavedItems(Long clientId) {
        Client client = clientRepository.findClientById(clientId);

        return client.getSavedItems().stream()
                .map(item -> ItemForCatalogDto.builder()
                        .name(item.getName())
                        .size(item.getSizes().stream().map(Size::getValue).toList())
                        .color(item.getColors().stream().map(Color::getValue).toList())
                        .price(item.getPrice().toString()).build())
                .toList();

    }

    public void saveItem(Long clientId, String itemName) {
        Item item = itemRepository.findByName(itemName);

        Client client = clientRepository.findClientById(clientId);

        client.getSavedItems().add(item);

        clientRepository.save(client);
    }

    public void deleteFromSavedItems(Long clientId, String itemName) {
        Item item = itemRepository.findByName(itemName);

        Client client = clientRepository.findClientById(clientId);

        client.getSavedItems().remove(item);

        clientRepository.save(client);
    }
}