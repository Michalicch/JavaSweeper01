package sweeper;

public class Bomb {
    private Matrix bombMap;
    private int totalBombs;

    Bomb (int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }
    void start (){
        bombMap = new Matrix(Box.ZERO);
        for (int j = 0; j < totalBombs; j++) // рандомно размещаем 10 бомб
            placeBomb ();
    }

    Box get (Coord coord) {
        return bombMap.get(coord);
    }

    private void fixBombsCount() {
        int maxBombs = Ranges.getSize().x * Ranges.getSize().y / 2;
        if (totalBombs > maxBombs)
            totalBombs = maxBombs;
    }
    private void placeBomb() {
        while (true){
            Coord coord = Ranges.getRandomCoord(); //Выбираем координату
            if (Box.BOMB == bombMap.get(coord)) // Если в координате бомба уже есть то пропускаем
                continue;
            bombMap.set(coord, Box.BOMB);
            incNumberAroundBomb(coord);
            break;
        }


    }
    private void incNumberAroundBomb (Coord coord){
        for (Coord around : Ranges.getCoordsAround(coord)) {
            if (Box.BOMB != bombMap.get(around));
            bombMap.set(around, bombMap.get(around).getNextNumberBox());
        }
    }

    int getTotalBombs() {
        return totalBombs;
    }
}
