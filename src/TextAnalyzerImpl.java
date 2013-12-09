package src;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Implements the interface TextAnalyzer.
 * 
 * @author Diego
 */
public class TextAnalyzerImpl extends UnicastRemoteObject implements
		TextAnalyzer {

	private long lastProcessedTextID;
	private int qtdWords, qtdUniqueWwords;
	private long textID;	
	private HashMap<Long, String> hmTexts;
	private List<WordCount> wordList;
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @throws RemoteException
	 */
	protected TextAnalyzerImpl() throws RemoteException {
		super();
		hmTexts = new HashMap<Long, String>();
		wordList = new ArrayList<WordCount>();
		lastProcessedTextID = -1;
		qtdWords = 0;
		qtdUniqueWwords = 0;
		textID = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long storeText(String text) throws RemoteException {
		textID++;
		String filtratedText = text.toLowerCase();
		filtratedText = filtratedText.replaceAll("[^a-zA-Z0-9ç-ó\\s\\-]+", "");
		filtratedText = filtratedText.replaceAll("\\n", " ");

		hmTexts.put(textID, filtratedText);

		return textID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeText(long id) throws RemoteException {
		hmTexts.remove(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int countWords(long id) throws RemoteException {
		int ret = -1;
		if (hmTexts.containsKey(id)) {
			process(id);
			ret = qtdWords;
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int countUniqueWords(long id) throws RemoteException {
		int ret = -1;
		if (hmTexts.containsKey(id)) {
			process(id);
			ret = qtdUniqueWwords;
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<WordCount> wordUsage(long id) throws RemoteException {
		List<WordCount> ret = null;
		if (hmTexts.containsKey(id)) {
			process(id);
			ret = wordList;
		}
		return ret;
	}

	/**
	 * 
	 * @param id
	 * stored text's indentifier.
	 */
	private void process(long id) {
		// compares the text was already processed
		if (id != lastProcessedTextID) {
			HashMap<String, Integer> hmWord = new HashMap<String, Integer>();

			String txt = hmTexts.get(id);
			String[] words = txt.split(" ");
			qtdWords = 0;

			int count = 1;
			for (String word : words) {
				if (word != "") {
					if (hmWord.containsKey(word)) {						
						count = hmWord.get(word) + 1;
						hmWord.remove(word);
					}
					hmWord.put(word, count);
					qtdWords++;
				}
			}
			qtdUniqueWwords = hmWord.size();

			wordList.clear();
			hmWord.keySet();
			for (String key : hmWord.keySet()) {
				if (key != null) {
					WordCount w = new WordCount();
					w.word = key;
					w.count = hmWord.get(key);
					wordList.add(w);
				}
			}
			
			lastProcessedTextID = id;
		}
	}
}
