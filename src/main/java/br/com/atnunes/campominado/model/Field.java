package br.com.atnunes.campominado.model;

import br.com.atnunes.campominado.exception.ExplodeException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author atnunes
 * @version 0.1
 * @since 05/06/2020
 **/

public class Field {
    private final int line;
    private final int column;

    private boolean open = false;
    private boolean pumped = false;
    private boolean checked = false;

    private List<Field> neighborhood = new ArrayList<>();

    Field(int line, int column) {
        this.line = line;
        this.column = column;
    }

    boolean addNeighbor(Field neighbor) {
        boolean differentLine = line != neighbor.line;
        boolean differentColumn = column != neighbor.column;
        boolean diagonal = differentLine && differentColumn;

        int deltaLine = Math.abs(line - neighbor.line);
        int deltaColumn = Math.abs(column - neighbor.column);
        int deltaAll = deltaColumn + deltaLine;

        if (deltaAll == 1 && !diagonal) {
            neighborhood.add(neighbor);
            return true;
        } else if (deltaAll == 2 && diagonal) {
            neighborhood.add(neighbor);
            return true;
        } else {
            return false;
        }


    }

    void switchChecked() {
        if (!open) {
            checked = !checked;
        }
    }

    boolean open() {
        if (!open && !checked) {
            open = true;
            if (pumped) {
                throw new ExplodeException();
            }
            if (neighborHoodSecure()) {
                neighborhood.forEach(n -> n.open());
            }
            return true;
        } else {
            return false;
        }
    }

    boolean neighborHoodSecure() {
        return neighborhood.stream().noneMatch(n -> n.pumped);
    }

    void undermine() {
        pumped = true;
    }

    public boolean isPumped() {
        return pumped;
    }

    public boolean isChecked() {
        return checked;
    }

    void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isClosed() {
        return !isOpen();
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    boolean targetArchieved() {
        boolean unveiled = !pumped && open;
        boolean protect = pumped && checked;
        return unveiled || protect;
    }

    long neighborhoodPumped() {
        return neighborhood.stream().filter(n -> n.pumped).count();

    }

    void restart() {
        open = false;
        pumped = false;
        checked = false;

    }

    public String toString() {
        if (checked) {
            return "x";
        } else if (open && pumped) {
            return "*";
        } else if (open && neighborhoodPumped() > 0) {
            return Long.toString(neighborhoodPumped());
        } else if (open) {
            return " ";
        } else {
            return "?";
        }
    }
}
