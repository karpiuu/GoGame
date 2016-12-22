package Source.Signal;

import Source.Connection.Server;
import Source.Exception.UnknownTableIdException;
import Source.Exception.UnknownUserIdException;
import Source.Game.Table;
import Source.Manager.TableManager;

/**
 * Signal execute when user stats his game
 */
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
        boolean type = false;

        if(owner.getTableId() != null) {
            try { table = tableManager.getTable( owner.getTableId() ); }
            catch (UnknownTableIdException e) {
                // Unknown table id
                System.out.println("[ERROR] Can't start game, table don't exists");
                return;
            }

            if( table.startGame() ) {

                table.startGame();

                owner.sendMessageToUser( "OK" );

                if (table.getIdUserWhite() == id) {
                    owner.sendMessageToUser("White");
                    type = true;
                } else if (table.getIdUserBlack() == id) {
                    owner.sendMessageToUser("Black");
                    type = false;
                }

                if( table.getActivePlayer().equals(id) ) {
                    try {
                        userManager.getUser(table.getUnactivePlayer()).sendMessageToUser("StartGame;" + (type ? "Black;" : "White;"));
                    } catch (UnknownUserIdException e) {
                        // Unknown user
                        System.out.println("[ERROR] User don't exists");
                        owner.sendMessageToUser("You don't have opponent");
                        return;
                    }
                }
                else {
                    try {
                        userManager.getUser(table.getActivePlayer()).sendMessageToUser("StartGame;" + (type ? "Black;" : "White;"));
                    } catch (UnknownUserIdException e) {
                        // Unknown user
                        System.out.println("[ERROR] User don't exists");
                        owner.sendMessageToUser("You don't have opponent");
                        return;
                    }
                }
            }
            else {
                owner.sendMessageToUser( "You need opponent to start game." );
            }
        }
    }
}
