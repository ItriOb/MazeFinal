import java.util.ArrayList;
import java.util.Random;

public class Cell {
    private int x;
    private int y;
    private int row;
    private int column;
    private  int volume;//
    private boolean checked=false;// etat de le carre; visite ou non
    private boolean[] walls={true,true,true,true}; //murs qui entourent le carre
    ArrayList<Cell> neighbors =new ArrayList<>();

    public Cell(int row, int column) {
        this.volume =40;
        this.checked=false;
        this.x=row* volume;
        this.y=column* volume;
        this.row = row;
        this.column = column;
    }

    //retourne un voisin aleatoire du carre
    public Cell getRandomNeighbor(){
        Random random = new Random();
        Cell neighbor =neighbors.get(random.nextInt((neighbors.size()-1)+1));


        while(neighbor.checked){
            neighbors.remove(neighbor);
            neighbor = neighbors.get(random.nextInt((neighbors.size()-1)+1));
        }
        neighbor.checked=true;
        neighbors.remove(neighbor);
        return neighbor;
    }


    //verifie si le carre a des voisins non-visite
    public boolean noCheckedNeighbors(){
        for(Cell neighbor:neighbors){
            if(!neighbor.checked){
                return true;
            }
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean[] getWalls() {
        return walls;
    }

    public void setWalls(boolean[] walls) {
        this.walls = walls;
    }

    public ArrayList<Cell> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<Cell> neighbors) {
        this.neighbors = neighbors;
    }

    public void setWall(int index,boolean value){
        this.walls[index]=value;
    }

    public boolean getWall(int index){
        return this.walls[index];
    }
}
