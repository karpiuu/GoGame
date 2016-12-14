package Source.Signal;

import Source.Connection.Server;
import Source.Exception.UnknownTableIdException;
import Source.Exception.UnknownUserIdException;
import Source.Game.Table;
import Source.Manager.TableManager;

/**
 * Created by SZYMON on 14.12.2016.
 */
public class PassSignal extends Signal {

    private TableManager tableManager;

    public PassSignal(Server server, int newId) {
        setUserManager(server.getUserManager());
        tableManager = server.getTableManager();
        id = newId;

        setOwner();
    }

    @Override
    public void execute() {
        Table table;

        if(owner.getTableId() != null) {
            try { table = tableManager.getTable( owner.getTableId() ); }
            catch (UnknownTableIdException e) {
                // Unknown table id
                System.out.println("[ERROR] Can't start game, table don't exists");
                return;
            }

            if( table.startGame() ) {
                if( table.getActivePlayer().equals(id) ){

                    owner.sendMessageToUser("OK");

                    try {
                        userManager.getUser( table.getUnactivePlayer() ).sendMessageToUser( "Pass;" );
                    } catch (UnknownUserIdException e) {
                        // This user might be deleted
                        System.out.println("[ERROR] User " + id + " don't exists anymore");
                    }
                }
            }
            else {
                owner.sendMessageToUser( "You need start the game." );
            }
        }
    }
}
