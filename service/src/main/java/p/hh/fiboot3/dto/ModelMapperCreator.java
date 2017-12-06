package p.hh.fiboot3.dto;


import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import p.hh.fiboot3.domain.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModelMapperCreator {

    private ModelMapper modelMapper = new ModelMapper();

    public ModelMapper create() {
        addDigitalItem2DtoMapping();
        addPhysicalItem2DtoMapping();
        addUser2DtoMapping();
        modelMapper.createTypeMap(String.class, Date.class);
        modelMapper.addConverter(stringToDate);
        modelMapper.createTypeMap(Date.class, String.class);
        modelMapper.addConverter(dateToString);
        return modelMapper;
    }

    Converter<String, Date> stringToDate = new AbstractConverter<String, Date>() {
        @Override
        protected Date convert(String s) {
            try {
                if (s != null) {
                    return new SimpleDateFormat("yyyy-MM-dd").parse(s);
                } else {
                    return null;
                }
            } catch (ParseException pe) {
                return null;
            }
        }
    };

    Converter<Date, String> dateToString = new AbstractConverter<Date, String>() {
        @Override
        protected String convert(Date s) {
            if (s != null) {
                return new SimpleDateFormat("yyyy-MM-dd").format(s);
            } else {
                return null;
            }
        }
    };


    private void addDigitalItem2DtoMapping() {
        modelMapper.addMappings(
            new PropertyMap<DigitalItem, DigitalItemDto>() {
                @Override
                protected void configure() {
                    map().setItemType("Digital");
                }
            }
        );
    }

    private void addPhysicalItem2DtoMapping() {
        modelMapper.addMappings(
            new PropertyMap<PhysicalItem, PhysicalItemDto>() {
                @Override
                protected void configure() {
                    map().setItemType("Physical");
                }
            }
        );
    }

    private void addUser2DtoMapping() {
        modelMapper.addMappings(
            new PropertyMap<User, UserDto>() {
                @Override
                protected void configure() {
                    map().setPassword(null);
                }
            }
        );
    }

}
