package com.example.orderservice.util;

import com.example.orderservice.dto.ItemDto;
import com.example.orderservice.entity.Item;

import java.util.List;
import java.util.stream.Collectors;

public class ItemUtil {
    public static Item itemDtoToItem(ItemDto itemDto) {
        Item item = new Item();
        item.setCount(itemDto.getCount());
        item.setProductId(itemDto.getProductId());
        return item;
    }

    public static List<Item> listOfItemDtoToItems(List<ItemDto> itemDtos) {
        return itemDtos.stream().map(ItemUtil::itemDtoToItem).collect(Collectors.toList());
    }

    public static ItemDto itemToItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setCount(item.getCount());
        itemDto.setProductId(item.getProductId());
        itemDto.setId(item.getId());
        itemDto.setStatus(item.getStatus());
        itemDto.setOrderId(item.getOrder().getId());
        return itemDto;
    }

    public static List<ItemDto> listOfItemsToItemDtos(List<Item> items) {
        return items.stream().map(ItemUtil::itemToItemDto).collect(Collectors.toList());
    }


}
