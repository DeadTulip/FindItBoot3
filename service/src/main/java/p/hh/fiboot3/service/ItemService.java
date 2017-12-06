package p.hh.fiboot3.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.hh.fiboot3.dao.ItemDao;
import p.hh.fiboot3.dao.TeamDao;
import p.hh.fiboot3.dao.UserDao;
import p.hh.fiboot3.domain.*;
import p.hh.fiboot3.dto.DigitalItemDto;
import p.hh.fiboot3.dto.ItemDto;
import p.hh.fiboot3.dto.MappingUtil;
import p.hh.fiboot3.dto.PhysicalItemDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    public ItemDto getItem(Long itemId) {
        Item item = itemDao.findOne(itemId);
        if (item == null) {
            return null;
        } else if (item instanceof DigitalItem) {
            return modelMapper.map((DigitalItem)item, DigitalItemDto.class);
        } else {
            return modelMapper.map((PhysicalItem)item, PhysicalItemDto.class);
        }
    }

    public DigitalItemDto createDigitalItem(DigitalItemDto itemDto) {
        User owner = userDao.findOne(itemDto.getOwner().getUserId());
        DigitalItem di = modelMapper.map(itemDto, DigitalItem.class);
        di.setOwner(owner);
        di.setDateCreated(new Date());
        di.setDateUpdated(new Date());
        DigitalItem createdDi = itemDao.save(di);
        return modelMapper.map(createdDi, DigitalItemDto.class);
    }

    public PhysicalItemDto createPhysicalItem(PhysicalItemDto itemDto) {
        User owner = userDao.findOne(itemDto.getOwner().getUserId());
        PhysicalItem pi = modelMapper.map(itemDto, PhysicalItem.class);
        pi.setOwner(owner);
        pi.setDateCreated(new Date());
        pi.setDateUpdated(new Date());
        PhysicalItem createdPi = itemDao.save(pi);
        return modelMapper.map(createdPi, PhysicalItemDto.class);
    }

    public ItemDto updateItem(ItemDto itemDto) {
        Item item = itemDao.findOne(itemDto.getItemId());
        Date dateCreated = item.getDateCreated();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        item.setName(itemDto.getName());
        try {
            item.setEventStartTime(formatter.parse(itemDto.getEventStartTime()));
        } catch (ParseException pe) {
            // do nothing
        }
        try {
            item.setEventEndTime(formatter.parse(itemDto.getEventEndTime()));
        } catch (ParseException pe) {
            // do nothing
        }
        item.setInvolvedPeople(itemDto.getInvolvedPeople());
        item.setInvolvedPlaces(itemDto.getInvolvedPlaces());
        item.setDescription(itemDto.getDescription());

        item.setDateCreated(dateCreated);
        item.setDateUpdated(new Date());
        Item savedItem = itemDao.save(item);

        if (item instanceof DigitalItem) {
            DigitalItem di = (DigitalItem)item;
            DigitalItemDto diDto = (DigitalItemDto) itemDto;
            di.setFileName(diDto.getFileName());
            di.setOriginalFileName(diDto.getOriginalFileName());
            di.setFileSize(diDto.getFileSize());
            di.setFileType(diDto.getFileType());
            return modelMapper.map(di, DigitalItemDto.class);
        } else {
            return modelMapper.map((PhysicalItem)item, PhysicalItemDto.class);
        }
    }

    public List<ItemDto> getAllItemsCreatedByUser(Long userId) {
        User user = userDao.findOne(userId);
        List<Item> items = itemDao.findAllByOwner(user);
        return MappingUtil.mapItemList(modelMapper, items);
    }

    public List<ItemDto> getAllShareItemsByUser(Long userId) {
        User user = userDao.findOne(userId);
        List<Item> joinedItemList =
                user.getJoinedTeams().stream()
                        .flatMap(t -> t.getItems().stream())
                        .distinct()
                        .collect(Collectors.toList());
        return MappingUtil.mapItemList(modelMapper, joinedItemList);
    }

    public void deleteAllItemsCreatedByUser(Long userId) {
        User user = userDao.findOne(userId);
        List<Item> items = itemDao.findAllByOwner(user);
        itemDao.delete(items);
    }

    public void deleteItem(Long itemId) {
        itemDao.delete(itemId);
    }

    public void shareItemWithTeams(Long itemId, List<String> teamNames) {
        List<Team> teams = teamNames.stream()
                .map(it -> teamDao.findByTeamName(it)).collect(Collectors.toList());
        Item item = itemDao.getOne(itemId);
        teams.stream().forEach(it -> {
            it.getItems().add(item);
            teamDao.save(it);
        });
    }
}
