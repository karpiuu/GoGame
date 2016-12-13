package Source.Signal;

import Source.Connection.Server;
import Source.Exception.UnknownTableIdException;
import Source.Exception.UnknownUserIdException;
import Source.Game.Stone;
import Source.Game.Table;
import Source.Manager.TableManager;

public class StoneSignal extends Signal {

    private TableManager tableManager;
    int move;
    Stone type;

    public StoneSignal(Server server, int newId, String type, String move) {
        setUserManager(server.getUserManager());
        tableManager = server.getTableManager();
        id = newId;

        this.type = Stone.getTypeFromString( type );
        this.move = Integer.parseInt( move );

        setOwner();
    }

    @Override
    public void execute() {

        Table table;

        try {
            table = tableManager.getTable( owner.getTableId() );
        } catch (UnknownTableIdException e) {
            // Unknown table id
            System.out.println("[ERROR] Can't stand up, table don't exists");
            return;
        }
        if( table.getGameStart() ) {

            if( table.getActivePlayer().equals(id) ) {

                int moveXY[] = table.getGameEngine().convertMove(move);

                if (table.getGameEngine().checkMove(moveXY[0], moveXY[1], type)) {
                    owner.sendMessageToUser("OK");

                    try {
                        userManager.getUser( table.getUnactivePlayer() ).sendMessageToUser( "Stone;" + type.toString() + ";" + Integer.toString(move) );
                    } catch (UnknownUserIdException e) {
                        // This user might be deleted
                        System.out.println("[ERROR] User " + id + " don't exists anymore");
                    }

                } else {
                    owner.sendMessageToUser("Invalid move.");
                }
            }
            else {
                owner.sendMessageToUser("Wait for your turn.");
            }
        }
        else {
            owner.sendMessageToUser("Can't move, please start game!");
        }
    }
}
