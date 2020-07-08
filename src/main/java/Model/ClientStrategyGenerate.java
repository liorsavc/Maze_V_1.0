package Model;

import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;

import java.io.*;

public class ClientStrategyGenerate implements IClientStrategy {
    int numOfRows,getNumOfCols;
    Maze maze;

    public ClientStrategyGenerate(int numOfRows, int getNumOfCols) {
        this.numOfRows = numOfRows;
        this.getNumOfCols = getNumOfCols;

    }

    public Maze getMaze() {
        return maze;
    }

    @Override
        public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
        try {
            ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
            ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
            toServer.flush();
            int[] mazeDimensions = new int[]{this.numOfRows, this.getNumOfCols};
            toServer.writeObject(mazeDimensions);
            toServer.flush();
            byte[] compressedMaze = ((byte[])fromServer.readObject());
            InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
            byte[] decompressedMaze = new byte[10000000];
            is.read(decompressedMaze);
            this.maze = new Maze(decompressedMaze);
        } catch (Exception var10) {
            var10.printStackTrace();
        }
    }
}
