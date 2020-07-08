package Model;

import Client.IClientStrategy;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ClientStrategySolve implements IClientStrategy {
    private Solution solution;
    private Maze maze;

    public Solution getSolution() {
        return solution;
    }

    public ClientStrategySolve(Maze maze) {
        this.maze=maze;
    }

    @Override
    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
        try {
            ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
            ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
            toServer.flush();
            toServer.writeObject(this.maze);
            toServer.flush();
             this.solution = (Solution)fromServer.readObject();
        } catch (Exception var10) {
            var10.printStackTrace();
        }

    }
}


