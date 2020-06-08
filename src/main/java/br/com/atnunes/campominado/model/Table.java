package br.com.atnunes.campominado.model;

import br.com.atnunes.campominado.exception.ExplodeException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Table {

    private int lines;
    private int columns;
    private int mines;

    private final List<Field> fields = new ArrayList<>();

    public Table(int line, int column, int mines) {
        this.lines = line;
        this.columns = column;
        this.mines = mines;

        crateFields();
        associateNeighborHood();
        sortMines();
    }

    public void open(int line, int column) {
        try {
            fields.parallelStream()
                    .filter(c -> c.getLine() == line && c.getColumn() == column)
                    .findFirst()
                    .ifPresent(c -> c.open());
        } catch (ExplodeException e) {
            fields.forEach(f -> f.setOpen(true));
            throw e;

        }
    }

    public void switchChecked(int line, int column) {
        fields.parallelStream()
                .filter(c -> c.getLine() == line && c.getColumn() == column)
                .findFirst()
                .ifPresent(c -> c.switchChecked());

    }

    private void crateFields() {
        for (int l = 0; l < lines; l++) {
            for (int c = 0; c < columns; c++) {
                fields.add(new Field(l, c));
            }
        }
    }

    private void associateNeighborHood() {
        for (Field f1 : fields) {
            for (Field f2 : fields) {
                f1.addNeighbor(f2);
            }
        }
    }

    private void sortMines() {
        long pumpedMines = 0;
        Predicate<Field> pumped = f -> f.isPumped();

        do {
            int random = (int) (Math.random() * fields.size());
            fields.get(random).undermine();
            pumpedMines = fields.stream().filter(pumped).count();
        } while (pumpedMines < mines);

    }

    public boolean targetArchieved() {
        return fields.stream().allMatch(f -> f.targetArchieved());
    }

    public void restart() {
        fields.stream().forEach(f -> f.restart());
        sortMines();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for (int c = 0; c < columns; c++) {
            sb.append(" ");
            sb.append(c);
            sb.append(" ");
        }
        sb.append("\n");
        int i = 0;
        for (int l = 0; l < lines; l++) {
            sb.append(l);
            sb.append(" ");
            for (int c = 0; c < columns; c++) {
                sb.append(" ");
                sb.append(fields.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
