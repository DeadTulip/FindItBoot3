package p.hh.fiboot3.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import p.hh.fiboot3.dto.*;
import p.hh.fiboot3.service.ItemService;
import p.hh.fiboot3.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/toAdd")
    public ItemAddInfoDto toAddItem(@RequestBody UserDto userDto) {
        return userService.toAddItem(userDto.getUserId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createItem(HttpEntity<String> httpEntity) {
        String json = httpEntity.getBody();
        Map jsonMap = JsonParserFactory.getJsonParser().parseMap(json);
        if("Digital".equals(jsonMap.get("itemType"))) {
            DigitalItemDto diDto = new DigitalItemDto();
            modelMapper.map(jsonMap, diDto);
            diDto.setInvolvedPeople(diDto.getInvolvedPeople().replace("[", "").replace("]", ""));
            diDto.setInvolvedPlaces(diDto.getInvolvedPlaces().replace("[", "").replace("]", ""));

            return itemService.createDigitalItem(diDto);
        } else {
            PhysicalItemDto piDto = new PhysicalItemDto();
            modelMapper.map(jsonMap, piDto);
            return itemService.createPhysicalItem(piDto);
        }
    }

    @GetMapping("/{itemId}")
    public ItemDto readItem(@PathVariable Long itemId, HttpServletResponse response) {
        ItemDto itemDto = itemService.getItem(itemId);
        if (itemDto != null) {
            return itemDto;
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @PutMapping
    public ItemDto updateItem(HttpEntity<String> httpEntity) {
        String json = httpEntity.getBody();
        Map jsonMap = JsonParserFactory.getJsonParser().parseMap(json);
        if("Digital".equals(jsonMap.get("itemType"))) {
            DigitalItemDto diDto = new DigitalItemDto();
            modelMapper.map(jsonMap, diDto);
            diDto.setInvolvedPeople(diDto.getInvolvedPeople().replace("[", "").replace("]", ""));
            diDto.setInvolvedPlaces(diDto.getInvolvedPlaces().replace("[", "").replace("]", ""));

            ItemDto updatedItem = itemService.updateItem(diDto);

            List<String> sharedTeams = (List) jsonMap.get("sharedTeams");

            itemService.shareItemWithTeams(updatedItem.getItemId(), sharedTeams);

            return updatedItem;
        } else {
            PhysicalItemDto piDto = new PhysicalItemDto();
            modelMapper.map(jsonMap, piDto);
            return itemService.updateItem(piDto);
        }
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
    }

}
