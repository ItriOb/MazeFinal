import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;

public class Maze {
    private int size;
    private int row;
    private int column;
    private Cell[][] cells;
    private Cell ongoing;
    private Stack<Cell> cellsStack ;
    private Cell entry;
    private Cell exit;

    public Maze(int size) {
        this.size = size;
        this.row = size;
        this.column = size;
        this.cellsStack = new Stack<Cell>();
        this.cells = new Cell[this.row][this.column];
        for(int i=0;i<this.row;i++){
            for(int j=0;j<this.column;j++){
                this.cells[i][j]=new Cell(i,j);
            }
        }

        for(int i=0;i<this.row;i++){
            for(int j=0;j<this.column;j++){
                this.addNeighbors(this.cells[i][j]);
            }
        }

        Random rand = new Random();
        int max =this.size-1;
        int min =0;
        int i=rand.nextInt((max-min)+1)+min;
        this.ongoing = this.cells[0][i];
        this.entry = this.cells[0][i];

        this.ongoing.setWall(3,false);

        int maxS =this.size-1;
        int minS =0;
        int j = rand.nextInt((maxS-minS)+1)+minS;
        this.exit = this.cells[this.size-1][j];
        this.cells[this.size-1][j].setWall(1,false);
        this.ongoing.setChecked(true);

        createPath(this.ongoing);

    }

    private void removeWall(Cell ongoing,Cell next){
        int x = ongoing.getRow()-next.getRow();
        int y = ongoing.getColumn()-next.getColumn();

        if(x==-1){
            ongoing.setWall(1,false);
            next.setWall(3,false);
        }
        else if(x==1){
            ongoing.setWall(3,false);
            next.setWall(1,false);
        }
        else if(y==-1){
            ongoing.setWall(2,false);
            next.setWall(0,false);
        }
        else if(y==1){
            ongoing.setWall(0,false);
            next.setWall(2,false);
        }
    }

    private void createPath(Cell ongoing){
        do{
            if(ongoing.noCheckedNeighbors()){
                Cell next = ongoing.getRandomNeighbor();
                this.cellsStack.add(ongoing);
                this.removeWall(ongoing,next);
                ongoing=next;
            }
            else if(!cellsStack.isEmpty()){
                Cell next = this.cellsStack.get(this.cellsStack.size()-1);
                this.cellsStack.remove(next);
                ongoing=next;
            }
        }while(!this.cellsStack.isEmpty());
    }

    private void addNeighbors(Cell ongoing){
        int x = ongoing.getRow();
        int y = ongoing.getColumn();
        if(x>0){
            ongoing.neighbors.add(this.cells[x-1][y]);
        }
        if(x<this.row-1){
            ongoing.neighbors.add(this.cells[x+1][y]);
        }
        if(y>0){
            ongoing.neighbors.add(this.cells[x][y-1]);
        }
        if(y<this.column-1){
            ongoing.neighbors.add(this.cells[x][y+1]);
        }
    }


    public void generateSVG(String filename){
        try{
            FileWriter writer =new FileWriter(filename);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" + (size *40 ) + "\" height=\"" + (size * 40) + "\">\n");

            for (int i = 0; i < this.row; i++) {
                for (int j = 0; j < this.column; j++) {
                    Cell cell = cells[i][j];
                    int x = cell.getX();
                    int y = cell.getY();
                    if (cell.getWall(0)) // Top wall
                        writer.write("<line x1=\"" + x + "\" y1=\"" + y + "\" x2=\"" + (x + 40) + "\" y2=\"" + y + "\" stroke=\"black\"/>\n");
                    if(cell==this.exit){
                        writer.write("<line x1=\"" + (x + 40) + "\" y1=\"" + y + "\" x2=\"" + (x + 40) + "\" y2=\"" + (y + 40) + "\" stroke=\"red\"/>\n");
                    }else{
                        if (cell.getWall(1)) // Right wall
                            writer.write("<line x1=\"" + (x + 40) + "\" y1=\"" + y + "\" x2=\"" + (x + 40) + "\" y2=\"" + (y + 40) + "\" stroke=\"black\"/>\n");

                    }
                    if (cell.getWall(2)) // Bottom wall
                        writer.write("<line x1=\"" + (x + 40) + "\" y1=\"" + (y + 40) + "\" x2=\"" + x + "\" y2=\"" + (y + 40) + "\" stroke=\"black\"/>\n");
                    if(cell==this.entry){
                        writer.write("<line x1=\"" + x + "\" y1=\"" + (y +40) + "\" x2=\"" + x + "\" y2=\"" + y + "\" stroke=\"green\"/>\n");
                    }else {
                        if (cell.getWall(3)) // Left wall
                            writer.write("<line x1=\"" + x + "\" y1=\"" + (y + 40) + "\" x2=\"" + x + "\" y2=\"" + y + "\" stroke=\"black\"/>\n");
                    }

                }
            }

            writer.write("</svg>");
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
