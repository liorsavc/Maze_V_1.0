package View;

import ViewModel.MyViewModel;

import java.util.Observer;

public interface IView extends Observer  {
    void SetViewModelConnection(MyViewModel vm);

    //void GenerateMaze() throws FileNotFoundException, UnknownHostException;
}
