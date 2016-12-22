package Source.Signal;

import Source.Connection.Server;
import Source.Exception.UnknownTableIdException;
import Source.Exception.UnknownUserIdException;
import Source.Game.Table;
import Source.Manager.SignalManager;
import Source.Manager.TableManager;

import java.util.ArrayList;

/**
 * Signal executed when user don't agree to given dead stones
 */
public class NoDeadStoneSignal extends Signal {

        private TableManager tableManager;
        private String line;

        public NoDeadStoneSignal(Server server, int newId, String line) {
            setUserManager(server.getUserManager());
            tableManager = server.getTableManager();
            id = newId;
            this.line = line;

            setOwner();
        }

        @Override
        public void execute() {
            Table table;

            if(owner.getTableId() != null) {
                try {
                    table = tableManager.getTable(owner.getTableId());
                } catch (UnknownTableIdException e) {
                    // Unknown table id
                    System.out.println("[ERROR] Can't start game, table don't exists");
                    return;
                }

                owner.sendMessageToUser("OK");

                int opponentId;

                if( table.getIdUserBlack() == id ) {
                    opponentId = table.getIdUserWhite();
                }
                else {
                    opponentId = table.getIdUserBlack();
                }

                try { userManager.getUser( opponentId ).sendMessageToUser("NoDeadStone;"); }
                catch (UnknownUserIdException e) {
                    // This user might be deleted
                    System.out.println("[ERROR] User " + id + " don't exists anymore");
                }

            }
            else {
                owner.sendMessageToUser("Your don't sit in any table.");
            }
        }
    }

