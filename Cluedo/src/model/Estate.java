package model;

import java.util.ArrayList;
import java.util.List;
/**
 * An Estate object is made up of its array of RoomCells. Each estate
 * has its own unique name and will always start with one Weapon object in it
 * When a player's piece has occupied a RoomCell they can choose to suggest or accuse.
 */
public class Estate {
    public String estateName = "";
    public List<RoomCell> roomCells = new ArrayList<>();


    public Estate(String name) {
        this.estateName = name;
    }

    // Change the necessary default cells to specific room cells
    public void setRoomCells(RoomCell roomCell) {
        roomCells.add(roomCell);
    }

    public String getEstateName(){
        return this.estateName;
    }
}
