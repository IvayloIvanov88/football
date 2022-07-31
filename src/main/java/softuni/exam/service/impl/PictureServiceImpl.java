package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.domain.dtos.PictureSeedRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XMLParser;


import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
@Transactional
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final XMLParser xmlParser;
    private final ValidationUtil validationUtil;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, XMLParser xmlParser, ValidationUtil validationUtil) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public String importPictures() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        PictureSeedRootDto pictureSeedRootDto = this.xmlParser.unmarshalFromXML(GlobalConstants.PICTURE_FILE_PATH, PictureSeedRootDto.class);
        pictureSeedRootDto.getPictures()
                .forEach(psd -> {
                    if (this.validationUtil.isValid(psd)) {
                        if (this.pictureRepository.findByUrl(psd.getUrl()) == null) {
                            this.pictureRepository.saveAndFlush(this.modelMapper.map(psd, Picture.class));
                            sb.append("Successfully import picture - ").append(psd.getUrl());
                        } else {
                            sb.append("Already in DB ");

                        }
                    } else {
                        sb.append("Invalid picture ");
                    }
                    sb.append(System.lineSeparator());
                });
        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return Files.readString(Path.of(GlobalConstants.PICTURE_FILE_PATH));
    }

    @Override
    public Picture getPictureByUrl(String url) {
        return this.pictureRepository.findByUrl(url);
    }


}
