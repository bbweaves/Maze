// Names: Ben Weaver
// x500s: weave385

import java.util.Random;

public class MyMaze{
    Cell[][] maze;
    int startRow;
    int endRow;

    public MyMaze(int rows, int cols, int startRow, int endRow) {
        maze = new Cell[rows][cols];
        this.startRow = startRow;
        this.endRow = endRow;
        for(int i = 0; i < rows; i++){
            for (int k = 0; k < cols; k++){
                maze[i][k] = new Cell();
            }
        }
    }


    public static MyMaze makeMaze(int rows, int cols, int startRow, int endRow) {
        int[] current;
        int [] choice;
        int rand = 0;
        boolean chosen;

        // creates necessary items and initializes stack
        Random random = new Random();
        MyMaze newMaze = new MyMaze(rows, cols, startRow, endRow);
        Stack1Gen stack = new Stack1Gen<>();

        stack.push(new int[]{startRow, 0});
        newMaze.maze[startRow][0].setVisited(true);

        while(!stack.isEmpty()){
            current = (int[]) stack.top();
            chosen = false;

            while (!chosen){
                choice = new int[]{1, 2, 3, 4};

                // removes unavailable cells from selection array
                // 1 - left
                if (current[1] - 1 < 0 || newMaze.maze[current[0]][current[1] - 1].getVisited()){
                    choice[0] = 0;
                }
                // 2 - up
                if(current[0] - 1 < 0 || newMaze.maze[current[0] - 1][current[1]].getVisited()) {
                    choice[1] = 0;
                }
                // 3 - right
                if(current[1] + 1 >= newMaze.maze[0].length || newMaze.maze[current[0]][current[1] + 1].getVisited()) {
                    choice[2] = 0;
                }
                // 4 - down
                if(current[0] + 1 >= newMaze.maze.length || newMaze.maze[current[0] + 1][current[1]].getVisited()) {
                    choice[3] = 0;
                }

                // pops stack if all neighbors are unavailable
                if (choice[0] == 0 & choice[1] == 0 & choice[2] == 0 & choice[3] == 0){
                    stack.pop();
                    break;
                }

                // ensures that chosen cell is available
                while(!chosen){
                    rand = random.nextInt(choice.length);
                    if (choice[rand] != 0){
                        chosen = true;
                    }
                }


                // switch statement to change whatever cell is necessary
                switch (choice[rand]){
                    case 1:
                        chosen = true;
                        newMaze.maze[current[0]][current[1] - 1].setRight(false);
                        newMaze.maze[current[0]][current[1] - 1].setVisited(true);
                        stack.push(new int[]{current[0], current[1] - 1});
                        break;
                    case 2:
                        chosen = true;
                        newMaze.maze[current[0] - 1][current[1]].setBottom(false);
                        newMaze.maze[current[0] - 1][current[1]].setVisited(true);
                        stack.push(new int[]{current[0] - 1, current[1]});
                        break;
                    case 3:
                        chosen = true;
                        newMaze.maze[current[0]][current[1]].setRight(false);
                        newMaze.maze[current[0]][current[1] + 1].setVisited(true);
                        stack.push(new int[]{current[0], current[1] + 1});
                        break;
                    case 4:
                        newMaze.maze[current[0]][current[1]].setBottom(false);
                        newMaze.maze[current[0] + 1][current[1]].setVisited(true);
                        stack.push(new int[]{current[0] + 1, current[1]});
                        break;

                }

            }

         }

        // resets all to unvisited
        for(int i = 0; i < newMaze.maze.length; i++){
            for (int k = 0; k < newMaze.maze[0].length; k++) {
                newMaze.maze[i][k].setVisited(false);
                }
            }
        return newMaze;
    } // makeMaze

    public void printMaze() {

        //prints top boundary
        for (int i = 0; i < maze[0].length; i ++){
            System.out.print("|---");
        }
        System.out.print("|\n");


        // prints right boundaries and middle sections of each cell
        for(int i = 0; i < maze.length; i++){
            for (int k = 0; k < maze[0].length; k++) {
                // special cases for left of board boundary
                if (k == 0 & i == 0){
                    System.out.print(" ");
                }else if(k == 0 & i != 0){
                    System.out.print("|");
                }

                // special case for bottom right element
                if(k == maze[i].length - 1 & i == maze.length - 1){
                    if (maze[i][k].getVisited())
                    System.out.print(" *  ");
                    else{
                        System.out.print("    ");
                    }
                }
                else if (maze[i][k].getRight() & maze[i][k].getVisited()){
                    System.out.print(" * |");
                }else if (!maze[i][k].getRight() & maze[i][k].getVisited()){
                    System.out.print(" *  ");
                }else if (maze[i][k].getRight() & !maze[i][k].getVisited()){
                    System.out.print("   |");
                }else {
                    System.out.print("    ");
                }
            }

            System.out.print('\n');
            for (int j = 0; j < maze[0].length; j ++){
                // right boundary creator for first cell
                if (j == 0){
                    System.out.print("|");
                }
                //bottom boundary printer
                if (maze[i][j].getBottom()){
                    System.out.print("---|");
                }else {
                    System.out.print("   |");
                }
            }
            System.out.print('\n');
            }
        } // printMaze


    public void solveMaze() {

        int[] current;


        // initializes queue
        Q2Gen queue = new Q2Gen();
        queue.add(new int[]{startRow, 0});

        // goes through entire maze until exit is found
        while (queue.length() > 0) {
            current = (int[]) queue.remove();
            maze[current[0]][current[1]].setVisited(true);

            // if cell is exit, stop
            if (current[0] == maze.length - 1 & current[1] == maze[0].length - 1){
                break;
            }


            // determines what cells are available and queues them up
            // preference for right and down

            // 4 - down
            if (current[0] + 1 < maze.length && !maze[current[0] + 1][current[1]].getVisited()) {
                if (!maze[current[0]][current[1]].getBottom()){
                    queue.add(new int[] {current[0] + 1,current[1]});
                }
            }
            // 3 - right
            if (current[1] + 1 < maze[0].length && !maze[current[0]][current[1] + 1].getVisited()) {
                if (!maze[current[0]][current[1]].getRight()){
                    queue.add(new int[]{current[0], current[1] + 1});
                }
            }
            // 2 - up
            if (current[0] - 1 >= 0 && !maze[current[0] - 1][current[1]].getVisited()) {
                if (!maze[current[0] - 1][current[1]].getBottom()){
                    queue.add(new int[] {current[0] - 1,current[1]});
                }
            }
            // 1 - left
            if (current[1] - 1 >= 0 && !maze[current[0]][current[1] - 1].getVisited()) {
                if (!maze[current[0]][current[1] - 1].getRight()) {
                    queue.add(new int[] {current[0],current[1] - 1});
                }
            }
        }
    } //solveMaze




    public static void main(String[] args){
        /* Any testing can be put in this main function */

        MyMaze yeet;

        yeet = makeMaze(10,40,0,40);

        yeet.printMaze();

        yeet.solveMaze();

        yeet.printMaze();

        System.out.println("finished");
    } // main
}// MyMaze