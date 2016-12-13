package Source.Signal;

import Source.Connection.Server;
import Source.Exception.UnknownTableIdException;
import Source.Exception.UnknownUserIdException;
import Source.Game.Table;
import Source.Manager.TableManager;

public class StartGameSignal extends Signal {

    private TableManager tableManager;

    public StartGameSignal(Server server, int newId) {
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
                owner.sendMessageToUser( "OK" );

                table.startGame();

                if (table.getIdUserWhite() == id) {
                    owner.sendMessageToUser("White");
                } else if (table.getIdUserBlack() == id) {
                    owner.sendMessageToUser("Black");
                }
            }
            else {
                owner.sendMessageToUser( "You need opponent to start game." );
            }
        }
    }
}
