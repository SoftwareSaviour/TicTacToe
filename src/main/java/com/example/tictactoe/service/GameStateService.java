package com.example.tictactoe.service;

import greet.Player;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Component
public class GameStateService {
    private Map<String, String> board;
    private Boolean isGameOver;

    public GameStateService() {
        createNewSession();
    }

    public Boolean isGameOver() {
        return isGameOver;
    }

    public void endGame(Boolean gameOver) {
        this.isGameOver = gameOver;
    }

    public void updateState(Player player) {
        if (board.get(player.getPosition()) != null) {
            throw new InvalidParameterException("The position has already been taken");
        }
        board.put(player.getPosition(), player.getId());
    }

    public String checkWinner() {
        for (int a = 0; a < 8; a++) {
            String line = null;
            switch (a) {
                case 0:
                    line = board.get("0") + board.get("1") + board.get("2");
                    break;
                case 1:
                    line = board.get("3") + board.get("4") + board.get("5");
                    break;
                case 2:
                    line = board.get("6") + board.get("7") + board.get("8");
                    break;
                case 3:
                    line = board.get("0") + board.get("3") + board.get("6");
                    break;
                case 4:
                    line = board.get("1") + board.get("4") + board.get("7");
                    break;
                case 5:
                    line = board.get("2") + board.get("5") + board.get("8");
                    break;
                case 6:
                    line = board.get("0") + board.get("4") + board.get("8");
                    break;
                case 7:
                    line = board.get("2") + board.get("4") + board.get("6");
                    break;
            }
            if (line.equals("XXX")) {
                return "X";
            } else if (line.equals("OOO")) {
                return "O";
            }
        }

        if(board.values().stream().filter(Objects::nonNull).collect(Collectors.toList()).size() == 8) {
            return "draw";
        }

        return null;
    }

    public void createNewSession(){
        this.board = new HashMap<>();
        this.board.put("0", null);
        this.board.put("1", null);
        this.board.put("2", null);
        this.board.put("3", null);
        this.board.put("4", null);
        this.board.put("5", null);
        this.board.put("6", null);
        this.board.put("7", null);
        this.board.put("8", null);
        this.board.put("9", null);
        this.isGameOver = false;
    }
}
