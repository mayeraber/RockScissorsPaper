import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by MNA on 7/17/2017.
 */
import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by MNA on 7/17/2017.
 */
public class RockScissorsPaper
{
    static Kattio kattio = new Kattio(System.in, System.out);

    public static void main(String[] args)
    {
        int numCases, numRows, numCol, numDays;
        //'grid' to store the grid, 'corrections' to store each day's changes until they are made in the grid
        char[] grid, corrections;
        String gridLine;

        numCases = kattio.getInt();

        for(int x=0; x<numCases; x++)
        {
            numRows = kattio.getInt();
            numCol = kattio.getInt();
            numDays = kattio.getInt();
            grid = new char[numRows*numCol];

            corrections = new char[numRows*numCol];

            for(int b=0; b<corrections.length;b++)
            {
                corrections[b]=' ';
            }

            //process the input grid and store it in 'grid' array
            int gridIndex=0;
            for(int i=0; i<numRows; i++)
            {
                gridLine = kattio.nextToken();
                for(int y=0; y<numCol; y++)
                {
                    grid[gridIndex++] = gridLine.charAt(y);
                }
            }

            //for each day figure out what changes should be made and temorarily store them in array 'corrections'
            for(int k=0; k<numDays; k++)
            {
                int gridLocation = 0;
                while(gridLocation<grid.length)
                {
                    if(numCol==1)
                    {
                        if(numRows>1)
                        {
                            if(gridLocation+1!=grid.length)
                                warResults(grid,corrections, gridLocation, gridLocation+1);
                        }
                        gridLocation++;
                        continue;
                    }

                    if((gridLocation+1) %numCol != 1 && gridLocation!=0)//numCol!=1)
                    {
                        //check left
                        warResults(grid, corrections, gridLocation, gridLocation-1);
                    }

                    if((gridLocation+1) % numCol != 0)
                    {
                        //check right
                        warResults(grid, corrections, gridLocation, gridLocation+1);
                    }

                    if(gridLocation+1 > numCol)
                    {
                        //check up
                        warResults(grid, corrections, gridLocation, gridLocation-numCol);
                    }

                    if(gridLocation+1 <= ((numCol*numRows)-numCol))
                    {
                        //check down
                        warResults(grid, corrections, gridLocation, gridLocation+numCol);
                    }

/*
                    //if 1 row, check every other cell
                    else if(numRows==1)
                        gridLocation+=2;

                    //if 2 rows, check diagonally adjacent cells, starting from zero, i.e 0,3,4,7,8
                    if(numRows==2)
                    {
                        if(gridLocation==0 || gridLocation%2==0)
                            gridLocation+=3;
                        else
                            gridLocation++;
                    }

                    //if even number of rows, check all diagonally adjacent cells
                    else if(numRows%2==0)
                    {
                        if(gridLocation==0)
                            gridLocation+=2;
                        //if second to last cell in row
                        if((gridLocation+2)%numCol==0)
                            gridLocation+=3;
                        //if last cell in row
                        if((gridLocation+1)%numCol==0)
                            gridLocation++;
                        //if in middle or row
                        else
                            gridLocation+=2;
                    }

                    //if odd number of rows check every other cell
                    else if(numRows%2!=0)
                        gridLocation+=2;
*/
                    gridLocation++;
                }
                //call method to make the changes to the grid based on the 'corrections' array
                movePositions(grid, corrections);
            }

            //print resulting board
            int counter=0;
            for(int j=0; j<numRows; j++)
            {
                for(int l=0; l<numCol; l++)
                {
                    System.out.print(grid[counter++]);
                }
                System.out.print("\n");
            }
            //print a blank line before the results if there's another case following
            if(x<numCases-1)
                System.out.print("\n");
        }
    }

    //method to check if, and if yes then which, 'life form' should take the another
    public static void warResults(char[] grid, char[] corrections,  int gridLocA, int gridLocB)
    {
        if(grid[gridLocA] == 'R' && grid[gridLocB] == 'S')
            corrections[gridLocB] = 'R';
        else if(grid[gridLocB] == 'R' && grid[gridLocA] == 'S')
            corrections[gridLocA] = 'R';

        if(grid[gridLocA] == 'S' && grid[gridLocB] == 'P')
            corrections[gridLocB] = 'S';
        else if(grid[gridLocB] == 'S' && grid[gridLocA] == 'P')
            corrections[gridLocA] = 'S';

        if(grid[gridLocA] == 'P' && grid[gridLocB] == 'R')
            corrections[gridLocB] = 'P';
        else if(grid[gridLocB] == 'P' && grid[gridLocA] == 'R')
            corrections[gridLocA] = 'P';
    }

    //method to actually mak the changes in the grid after all reults were gathered
    public static void movePositions(char[] grid, char[] corrections)
    {
        for(int x=0; x<grid.length; x++)
        {
            if(corrections[x] != ' ')
            {
                grid[x] = corrections[x];
                corrections[x] = ' ';
            }
        }
    }


    //taken from Kattis, for enhanced i/o performance
    private static class Kattio extends PrintWriter {
        public Kattio(InputStream i) {
            super(new BufferedOutputStream(System.out));
            r = new BufferedReader(new InputStreamReader(i));
        }
        public Kattio(InputStream i, OutputStream o) {
            super(new BufferedOutputStream(o));
            r = new BufferedReader(new InputStreamReader(i));
        }

        /*public boolean hasMoreTokens() {
            return peekToken() != null;
        }
        */
        public int getInt() {
            return Integer.parseInt(nextToken());
        }

        private BufferedReader r;
        private String line;
        private StringTokenizer st;
        private String token;

        private String peekToken() {
            if (token == null) {
                try {
                    while (st == null || !st.hasMoreTokens()) {
                        line = r.readLine();
                        if (line == null) return null;
                        st = new StringTokenizer(line);
                    }
                    token = st.nextToken();
                } catch (IOException e) {
                }
            }
            return token;
        }

        private String nextToken() {
            String ans = peekToken();
            token = null;
            return ans;
        }
    }
}
