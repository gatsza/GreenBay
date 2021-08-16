package org.gfa.greenbay.services;

import org.gfa.greenbay.dtos.AuctionCreationRequestDTO;
import org.gfa.greenbay.dtos.AuctionCreationResponseDTO;
import org.gfa.greenbay.models.Item;
import org.gfa.greenbay.repositories.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

  private final ItemRepository itemRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public ItemServiceImpl(ItemRepository itemRepository,
      ModelMapper modelMapper) {
    this.itemRepository = itemRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public AuctionCreationResponseDTO addItem(AuctionCreationRequestDTO auctionCreationRequest) {
    Item newItem = modelMapper.map(auctionCreationRequest, Item.class);
    Item savedItem = itemRepository.save(newItem);
    return modelMapper.map(savedItem, AuctionCreationResponseDTO.class);
  }
}
