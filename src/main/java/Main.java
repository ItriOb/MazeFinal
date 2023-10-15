public class Main {
    public static void main(String[] args) {

        int size = 4;
        Maze labyrinth = new Maze(size);
        labyrinth.generateSVG("labyrinth.svg");
    }
}
