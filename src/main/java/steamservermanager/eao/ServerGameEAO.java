package steamservermanager.eao;

import java.util.List;

import javax.persistence.TypedQuery;

import steamservermanager.models.ServerGame;

public class ServerGameEAO extends AbstractEAO<ServerGame> {
	
	public ServerGameEAO() {
		super(ServerGame.class);
	}

	public synchronized List<ServerGame> findAllServerGame(){
		StringBuilder sb = new StringBuilder();
		sb.append(" select sg from ServerGame sg ");
		
		TypedQuery<ServerGame> query = createQuery(sb, ServerGame.class);

		return getResultList(query);
	}
	
	public ServerGame findServerGameById(Long idServerGame) {
		return find(idServerGame);
	}
	
	public ServerGame findServerGameByLocalName(String localName) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select sg from ServerGame sg ");
		sb.append(" where sg.localName = :localName ");
		
		TypedQuery<ServerGame> query = createQuery(sb, ServerGame.class);
		query.setParameter("localName", localName);
		
		return getSingleResult(query);
	}
}
