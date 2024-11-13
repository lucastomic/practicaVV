package mocks;

import java.util.Random;

public class GeneratorMock extends Random {
    private final int numberToReturn;
    public GeneratorMock(int numberToReturn) {
        this.numberToReturn = numberToReturn;
    }
    @Override
    public int nextInt(int n) {
        return numberToReturn;
    }
}
