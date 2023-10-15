public class Main {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java MazeGenerator <maze_size>");
            System.exit(1);
        }
        int size = Integer.parseInt(args[0]);
        Labyrinthe labyrinth = new Labyrinthe(size);
        labyrinth.generateSVG("labyrinthe.svg");
    }
}
