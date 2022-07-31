package softuni.exam.service.impl;


import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.domain.dtos.PlayerSeedDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.service.PictureService;
import softuni.exam.service.PlayerService;
import softuni.exam.service.TeamService;
import softuni.exam.util.ValidationUtil;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;



@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final PictureService pictureService;
    private final TeamService teamService;

    @Autowired
    public PlayerServiceImpl(PlayerRepository plauerRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, PictureService pictureService, TeamService teamService) {
        this.playerRepository = plauerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.pictureService = pictureService;
        this.teamService = teamService;
    }

    @Override
    public String importPlayers() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        PlayerSeedDto[] dtos = this.gson.fromJson(new FileReader(GlobalConstants.PLAYERS_FILE_PATH), PlayerSeedDto[].class);
        Arrays.stream(dtos).forEach(dto -> {
            if (this.validationUtil.isValid(dto)) {
                if (this.getPlayerByFirstNameAndLastNameAndPosition(dto.getFirstName(), dto.getLastName(), String.valueOf(dto.getPosition())) == null) {

                    Player player = this.modelMapper.map(dto, Player.class);
                    Team team = this.teamService.getTeamByName(dto.getTeam().getName());
                    Picture picture = this.pictureService.getPictureByUrl(dto.getPicture().getUrl());

                    player.setPicture(picture);
                    player.setTeam(team);
                    this.playerRepository.saveAndFlush(player);

                    sb.append(String.format("Successfully import player- %s %s ",
                            dto.getFirstName(), dto.getLastName()));

                }
                sb.append("Player Already in DB");
            } else {
                sb.append("Invalid player");
            }

            sb.append(System.lineSeparator());
        });
        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return Files.readString(Path.of(GlobalConstants.PLAYERS_FILE_PATH));
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();
        this.playerRepository.findAllBySalaryGreaterThanOrderBySalaryDesc(BigDecimal.valueOf(10000))
                .forEach(player -> {
                    sb.append(String.format("Player name: %s %s \n" +
                            "\tNumber: %d\n" +
                            "\tSalary: %.2f\n" +
                            "\tTeam: %s\n", player.getFirstName(), player.getLastName(), player.getNumber()
                            ,player.getTeam().getName()))
                            .append(System.lineSeparator());
                });
        return sb.toString();
    }

    @Override
    public Player getPlayerByFirstNameAndLastNameAndPosition(String firstName, String lastName, String position) {
        return this.playerRepository.findByFirstNameAndLastNameAndPosition(firstName, lastName, position);
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();
        List<Player> players = this.playerRepository.findAllByTeamName("North hub");
        players.forEach(player -> {
            sb.append(String.format("Player name: %s %s - %s\n" +
                            "Number: %d\n", player.getFirstName(), player.getLastName(), player.getPosition(), player.getNumber()))
                    .append(System.lineSeparator());
        });
        return sb.toString();
    }


}
