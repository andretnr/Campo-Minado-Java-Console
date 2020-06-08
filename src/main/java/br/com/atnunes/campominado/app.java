package br.com.atnunes.campominado;

import br.com.atnunes.campominado.model.Table;
import br.com.atnunes.campominado.view.TableConsole;

public class app {
    public static void main(String[] args) {
        Table table = new Table(10, 10, 6);
        new TableConsole(table);
    }
}
