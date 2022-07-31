package softuni.exam.service;


import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface PlayerService {
    String importPlayers() throws FileNotFoundException;

    boolean areImported();

    String readPlayersJsonFile() throws IOException;

    String exportPlayersInATeam();

    public String exportPlayersWhereSalaryBiggerThan();

    Player getPlayerByFirstNameAndLastNameAndPosition(String firstName, String lastName,String position);
}
