import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * An implementation of the IMDBGraph interface, used to parse an IMDB actor file into a list of movies and actors.
 * @author Ravi Kirschner
 *
 */
public class IMDBGraphImpl implements IMDBGraph{
	final private Map<String, IMDBNode> _movies;
	final private Map<String, IMDBNode> _actors;

	/**
	 * Constructs a new IMDBGraph
	 * @param actorsFilename the file that contains the actors names and the movies they starred in
	 * @param actressesFilename the file that contains the actresses names and the movies they starred in
	 * @throws IOException if the file path does not exist
	 */
	public IMDBGraphImpl (String actorsFilename, String actressesFilename) throws IOException {
		_movies = new HashMap<String, IMDBNode>();
		_actors = new HashMap<String, IMDBNode>();
		parse(actorsFilename);
		parse(actressesFilename);
	}
	/**
	 * Returns the collection of actor nodes
	 * @return a collection of actor nodes
	 */
	@Override
	public Collection<? extends Node> getActors() {
		return _actors.values();
	}

	/**
	 * Returns the collection of movie nodes
	 * @return a collection of actor nodes
	 */
	@Override
	public Collection<? extends Node> getMovies() {
		return _movies.values();
	}

	/**
	 * Returns a movie node given the name of the movie
	 * @param the name of the movie
	 * @return the node associated with the movie's name
	 */
	@Override
	public Node getMovie(String name) {
		return _movies.get(name);
	}

	/**
	 * Returns an actor node given the name of the actor
	 * @param the name of the actor
	 * @return the node associated with the actor's name
	 */
	@Override
	public Node getActor(String name) {
		return _actors.get(name);
	}
	
	/**
	 * Parses through the given file and creates IMDBNodes for the actors and movies, and stores them in the HashMap _actors and _movies respectively.
	 * @param file the file to look through
	 * @throws IOException if the file does not exist
	 */
	private void parse(String file) throws IOException {
		final File scan = new File(file);
		final Scanner actorScan = new Scanner(scan, "ISO-8859-1");
		boolean startScanning = false; //boolean that tells us if we need to start scanning
		String lastActor = ""; //last actor added to the list
		while (actorScan.hasNextLine()) {
			String line = actorScan.nextLine(); //caches the next line
			if(!startScanning && line.equals("----			------")) {
				startScanning = true; //if that is the line, we start scanning.
			}
			if(startScanning) {
				if(line.equals("-----------------------------------------------------------------------------")) {
					break; //if that is the line, then we have reached the end
				}
				else if(line.indexOf(" ") > 0){ //if there is a space in the line
					final int blankSpace = line.indexOf("	"); //find index of tab
					final String actName = line.substring(0, blankSpace); //get actor name bc theres an actor there
					if(blankSpace != 0 && !actName.contains("	") && actName.length() != 0) {
						lastActor = actName.trim(); //if the actname isn't blank then its a hit
					}
					final boolean TV = line.contains("TV") || line.contains("\""); //check for quotations or TV
					final int endMovie = findYearIndex(line); //find the movie index
					if(endMovie != -1 && !TV) { //if not TV and there is actually a movie
						if (!_actors.containsKey(actName)) {
							_actors.put(actName, new IMDBNode(actName)); //if actname isn't in there, put it in
						}
						if(!_actors.containsKey(lastActor)) {
							_actors.put(lastActor, new IMDBNode(lastActor)); //if lastActor isn't in there already, put it in
						}
						String movieName = line.substring(blankSpace, endMovie).trim().concat(")"); //find movie name
						if(!_movies.containsKey(movieName)) {
							_movies.put(movieName, new IMDBNode(movieName)); //if not there, put it in
						}
						if(movieName.equals("")) {
							_movies.remove(movieName); //but if it's empty, remove it
						}
						else {
							_movies.get(movieName).addEdge(_actors.get(lastActor));
							_actors.get(lastActor).addEdge(_movies.get(movieName));
							//add edges to movie & actor
						}
					}
					if(actName.equals("")) {
						_actors.remove(actName); //if actname blank, remove it
					}
				}
			}
		} 
		actorScan.close();
	}
	
	/**
	 * In a given string, looks for the year of the movie by finding the first index with a digit and then a ")".
	 * @param x the string to look through
	 * @return the index of the parenthesis or -1 if it is not found
	 */
	private int findYearIndex(String x) {
		for(int i = 1; i < x.length(); i++) {
			if(x.substring(i, i+1).equals(")") && Character.isDigit(x.charAt(i-1))) return i;
		}
		return -1;
	}
	

}
