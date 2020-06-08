package br.com.atnunes.campominado.view;

import br.com.atnunes.campominado.exception.ExitException;
import br.com.atnunes.campominado.exception.ExplodeException;
import br.com.atnunes.campominado.model.Table;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class TableConsole {
    private Table table;
    private Scanner entry = new Scanner(System.in);

    public TableConsole(Table table) {
        this.table = table;
        runGame();
    }

    private void runGame() {
        try {
            boolean continues = true;
            while (continues) {
                loopingGame();

                System.out.println("Try Again? (Y/n) ");
                String answer = entry.nextLine();
                if ("n".equalsIgnoreCase(answer)) {
                    continues = false;
                } else {
                    table.restart();
                }
            }
        } catch (ExitException e) {
            System.out.println("Byee!!!");
        } finally {
            entry.close();
        }
    }

    private void loopingGame() {
        try {
            while (!table.targetArchieved()) {
                System.out.println(table);
                String typed = getTypedValue("Enter the coordinates (X/Y): ");

                Iterator<Integer> xy = Arrays.stream(typed.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();

                typed = getTypedValue("1 - Open Field \n 2 - (Un)Check Field:  ");

                if ("1".equals(typed)) {
                    table.open(xy.next(), xy.next());
                } else if ("2".equals(typed)) {
                    table.switchChecked(xy.next(), xy.next());
                }

            }
            System.out.println(table);
            System.out.println("YOU WIN!!!");
        } catch (ExplodeException e) {
            System.out.println(table);
            System.out.println("YOU LOOSE!!!");
        }
    }

    private String getTypedValue(String text) {
        System.out.println(text);
        String typed = entry.nextLine();

        if ("exit".equalsIgnoreCase(typed)) {
            throw new ExitException();
        }
        return typed;
    }
}
