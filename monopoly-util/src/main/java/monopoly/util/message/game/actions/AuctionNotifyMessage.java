/**
 * 
 */
package monopoly.util.message.game.actions;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class AuctionNotifyMessage implements Serializable {
	
	private static final long serialVersionUID = 7299914587162628735L;
	
	public final Object historyList;
	
	public AuctionNotifyMessage(Object pHistoryList){
		historyList = pHistoryList;
	}

}
