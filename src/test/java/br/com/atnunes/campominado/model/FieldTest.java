package br.com.atnunes.campominado.model;


import br.com.atnunes.campominado.exception.ExplodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class FieldTest {
    private Field field;

    @BeforeEach
    void fieldInit() {
        field = new Field(3, 3);
    }

    @Test
    void testNeighborRealEsquerda() {
        Field neighbor = new Field(3, 2);
        boolean result = field.addNeighbor(neighbor);
        assertTrue(result);

    }

    @Test
    void testNeighborRealDireita() {
        Field neighbor = new Field(3, 4);
        boolean result = field.addNeighbor(neighbor);
        assertTrue(result);

    }

    @Test
    void testNeighborRealCima() {
        Field neighbor = new Field(2, 3);
        boolean result = field.addNeighbor(neighbor);
        assertTrue(result);

    }

    @Test
    void testNeighborRealBaixo() {
        Field neighbor = new Field(4, 3);
        boolean result = field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    void testNeighborRealDiagonalSuperiorEquerda() {
        Field neighbor = new Field(2, 2);
        boolean result = field.addNeighbor(neighbor);
        assertTrue(result);

    }

    @Test
    void testNeighborRealDiagonalSuperiorDireita() {
        Field neighbor = new Field(2, 4);
        boolean result = field.addNeighbor(neighbor);
        assertTrue(result);

    }

    @Test
    void testNeighborRealDiagonalInferiorEsquerda() {
        Field neighbor = new Field(2, 4);
        boolean result = field.addNeighbor(neighbor);
        assertTrue(result);

    }

    @Test
    void testNeighborRealDiagonalInferiorDireita() {
        Field neighbor = new Field(2, 4);
        boolean result = field.addNeighbor(neighbor);
        assertTrue(result);

    }

    @Test
    void testNotNeighbor() {
        Field neighbor = new Field(1, 1);
        boolean result = field.addNeighbor(neighbor);
        assertFalse(result);

    }

    @Test
    void testValueCheckedDefault() {
        assertFalse(field.isChecked());
    }

    @Test
    void testAlternateChecked() {
        field.switchChecked();
        assertTrue(field.isChecked());
    }

    @Test
    void testAlternateCheckedTwoTimes() {
        field.switchChecked();
        field.switchChecked();
        assertFalse(field.isChecked());
    }

    @Test
    void testOpenNothingCheckedAndPumped() {
        assertTrue(field.open());
    }

    @Test
    void testOpenPumpedAndChecked() {
        field.switchChecked();
        field.undermine();
        assertFalse(field.open());
    }

    @Test
    void testOpenNothingPumpedButChecked() {
        field.switchChecked();
        assertFalse(field.open());
    }

    @Test
    void testOpenNothingCheckedButPumped() {
        field.undermine();
        assertThrows(ExplodeException.class, () -> {
            field.open();
        });
    }

    @Test
    void testOpenWithNeighbor(){
        Field field11 = new Field(1, 1);
        Field field22 = new Field(2, 2);
        field22.addNeighbor(field11);
        field.addNeighbor(field22);
        field.open();

        assertTrue(field22.isOpen() && field11.isOpen());
    }

    @Test
    void testOpenWithNeighborPumped(){
        Field field11 = new Field(1, 1);
        Field field12 = new Field(1, 2);
        field12.undermine();
        Field field22 = new Field(2, 2);
        field22.addNeighbor(field11);
        field22.addNeighbor(field12);
        field.addNeighbor(field22);
        field.open();

        assertTrue(field22.isOpen() && field11.isClosed());
    }

    @Test
    void testStringChecked(){
       field.switchChecked();
        assertTrue(field.toString().equals("x"));
    }

    @Test
    void testStringOpenAndPumped(){
        field.open();
        field.undermine();
        assertTrue(field.toString().equals("*"));
    }

    @Test
    void testStringOpenAndNeighborhoodPumped(){
        field.open();
     assertTrue(field.toString().equals(" "));
    }

    @Test
    void testStringClosed(){
        field.isClosed();
        assertTrue(field.toString().equals("?"));
    }





}


