package highScore;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoreModel {
    private static final String highScoreFile = "high_scores.ser";
    private static List<HighScore> highScores;

    public HighScoreModel() {
        highScores = loadHighScores();
    }

    public void addHighScore(String playerName, int score) {
        highScores.add(new HighScore(playerName, score));
        Collections.sort(highScores);
        saveHighScores();
    }

    public List<HighScore> getHighScores() {
        return highScores;
    }

    private void saveHighScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(highScoreFile))) {
            oos.writeObject(highScores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<HighScore> loadHighScores() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(highScoreFile))) {
            return (List<HighScore>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static class HighScore implements Serializable, Comparable<HighScore> {
        private String name;
        private int score;

        public HighScore(String playerName, int score) {
            this.name = playerName;
            this.score = score;
        }

        public String getPlayerName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        @Override
        public int compareTo(HighScore o) {
            return Integer.compare(o.score, this.score);
        }
    }
}

