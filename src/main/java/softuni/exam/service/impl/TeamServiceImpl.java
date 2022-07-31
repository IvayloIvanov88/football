package softuni.exam.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.domain.dtos.TeamSeedRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.TeamRepository;
import softuni.exam.service.PictureService;
import softuni.exam.service.TeamService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XMLParser;


import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final ModelMapper modelMapper;
    private final TeamRepository teamRepository;
    private final ValidationUtil validationUtil;
    private final XMLParser xmlParser;
    private final PictureService pictureService;

    @Autowired
    public TeamServiceImpl(ModelMapper modelMapper, TeamRepository teamRepository, ValidationUtil validationUtil, XMLParser xmlParser, PictureService pictureService) {
        this.modelMapper = modelMapper;
        this.teamRepository = teamRepository;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.pictureService = pictureService;
    }

    @Override
    public String importTeams() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        TeamSeedRootDto teamSeedRootDto = this.xmlParser.unmarshalFromXML(GlobalConstants.TEAMS_FILE_PATH, TeamSeedRootDto.class);
        teamSeedRootDto.getTeams().forEach(tsd -> {
            if (this.validationUtil.isValid(tsd)) {
                if (this.teamRepository.findByName(tsd.getName()) == null) {
                    if (this.pictureService.getPictureByUrl(tsd.getPicture().getUrl()) == null) {
                        Team team = this.modelMapper.map(tsd, Team.class);
                        Picture picture = this.pictureService.getPictureByUrl(tsd.getPicture().getUrl());
                        team.setPicture(picture);
                        this.teamRepository.saveAndFlush(team);
                        sb.append("Successfully import team - ").append(tsd.getName());
                    } else {
                        sb.append("Invalid picture");
                    }
                } else {
                    sb.append("Already in DB");
                }
            } else {
                sb.append("Invalid team");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() == 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return Files.readString(Path.of(GlobalConstants.TEAMS_FILE_PATH));
    }

    @Override
    public Team getTeamByName(String name) {
        return this.teamRepository.findByName(name);
    }

}
