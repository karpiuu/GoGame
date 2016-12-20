package Source.Signal;

import Source.Connection.Server;
import Source.Exception.UnknownTableIdException;
import Source.Exception.UnknownUserIdException;
import Source.Game.Stone;
import Source.Game.StoneType;
import Source.Game.Table;
import Source.Manager.TableManager;

public class StoneSignal extends Signal {

    private TableManager tableManager;
    private int move;
    private StoneType type;

    public StoneSignal(Server server, int newId, String type, String move) {
        setUserManager(server.getUserManager());
        tableManager = server.getTableManager();
        id = newId;

        this.type = StoneType.getTypeFromString( type );
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

        if( table.isGameEnd() ) {
            owner.sendMessageToUser("Can't place stone in end phase");
        }
        else if( table.getGameStart() ) {

            if( table.getActivePlayer().equals(id) ) {

                String command = table.getGameEngine().checkMove(move, type);

                if ( command.contains("YourMove") ) {

                    owner.sendMessageToUser(command);

                    command = "Stone" + command.substring( command.indexOf(";") );

                    try {
                        userManager.getUser( table.getUnactivePlayer() ).sendMessageToUser( command );
                    } catch (UnknownUserIdException e) {
                        // This user might be deleted
                        System.out.println("[ERROR] User " + id + " don't exists anymore");
                    }

                    table.setActivePlayer( table.getUnactivePlayer() );

                }
                else {
                    owner.sendMessageToUser(command);
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
